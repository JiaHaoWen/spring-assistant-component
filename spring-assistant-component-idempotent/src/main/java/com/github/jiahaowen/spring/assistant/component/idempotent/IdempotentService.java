package com.github.jiahaowen.spring.assistant.component.idempotent;

import static com.google.common.base.Preconditions.checkArgument;

import com.github.jiahaowen.spring.assistant.component.migration.common.error.ServiceException;
import com.google.common.base.Throwables;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.KeeperException;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 幂等执行器。需要 spring 容器中定义：
 *
 * <ul>
 *   <li><code>CuratorFramework idempotentCuratorFramework</code>
 * </ul>
 *
 * ZooKeeper 用来做分布式锁。
 *
 * @author jiahaowen
 * @since 2016/10/14
 */
@Component
public class IdempotentService {
    /** 获取锁失败后总共的重试时间。 */
    private int retryTimeoutSeconds = 3;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("idempotentCuratorFramework")
    private CuratorFramework idempotentCuratorFramework;

    /**
     * 执行需要幂等的逻辑。需要传入判断是否幂等的 key。如果针对同一个 idempotentKey 出现并发，后进入的请求会被阻塞。
     *
     * <p>节点清理的时候只清理到 idempotentKey 一层。因为 bizId 的路径下面会有多个请求对应的 idempotentKey 的节点存在，删除会导致其它请求受影响。所以
     * bizId 里面不能包含 uid, id 这种跟单一请求相关的数据，要保持稳定，需要是一个写死的值。
     *
     * @param bizId 业务标识。主要是防止同一个应用内使用幂等的业务太多导致 key 冲突
     * @param idempotentKey 幂等号
     * @param callback 业务逻辑回调。参数是 fencing token
     * @param resultType 业务逻辑的返回值类型
     */
    @SuppressWarnings({"WeakerAccess", "unused"})
    public <R> R execute(
            String bizId,
            String idempotentKey,
            @SuppressWarnings("unused") Class<R> resultType,
            Function<String, R> callback) {
        return execute(bizId, idempotentKey, callback);
    }

    /**
     * 执行需要幂等的逻辑。需要传入判断是否幂等的 key。如果针对同一个 idempotentKey 出现并发，后进入的请求会被阻塞。
     *
     * <p>节点清理的时候只清理到 idempotentKey 一层。因为 bizId 的路径下面会有多个请求对应的 idempotentKey 的节点存在，删除会导致其它请求受影响。所以
     * bizId 里面不能包含 uid, id 这种跟单一请求相关的数据，要保持稳定，需要是一个写死的值。
     *
     * @param bizId 业务标识。主要是防止同一个应用内使用幂等的业务太多导致 key 冲突
     * @param idempotentKey 幂等号
     * @param callback 业务逻辑回调。参数是 fencing token
     */
    @SuppressWarnings("WeakerAccess")
    public <R> R execute(String bizId, String idempotentKey, Function<String, R> callback) {
        checkArgument(bizId != null && !bizId.isEmpty(), "bizId is required");
        checkArgument(!bizId.contains("/"), "bizId cannot contain /");
        checkArgument(
                idempotentKey != null && !idempotentKey.isEmpty(), "idempotentKey is required");
        checkArgument(!idempotentKey.contains("/"), "idempotentKey cannot contain /");

        try {
            logger.debug("Get mutex");
            InterProcessMutex mutex = getLock(bizId, idempotentKey);
            logger.debug("Got mutex");
            try {
                return callback.apply(idempotentKey);
            } finally {
                try {
                    mutex.release();
                } finally {
                    deleteLockNode(bizId, idempotentKey);
                }
            }
        } catch (Throwable ex) {
            Throwables.throwIfUnchecked(ex);
            throw ServiceException.buildInternalEx(ex);
        }
    }

    private void deleteLockNode(String bizId, String idempotentKey) throws Exception {
        String lockZkPath = getLockZkPath(bizId, idempotentKey);
        try {
            idempotentCuratorFramework.delete().deletingChildrenIfNeeded().forPath(lockZkPath);
        } catch (KeeperException.NoNodeException ignore) {
        }
    }

    private InterProcessMutex getLock(String bizId, String idempotentKey) throws Exception {
        String lockZkPath = getLockZkPath(bizId, idempotentKey);
        InterProcessMutex mutex = new InterProcessMutex(idempotentCuratorFramework, lockZkPath);
        if (!mutex.acquire(retryTimeoutSeconds, TimeUnit.SECONDS)) {
            throw ServiceException.buildInternalEx(
                    "Cannot get lock in time. Lock key: " + lockZkPath);
        }
        return mutex;
    }

    private String getLockZkPath(String bizId, String idempotentKey) {
        return "/bkk/idempotent/" + bizId + "/" + idempotentKey;
    }

    @SuppressWarnings({"WeakerAccess"})
    public void setRetryTimeoutSeconds(Period retryTimeoutSeconds) {
        this.retryTimeoutSeconds = retryTimeoutSeconds.getSeconds();
    }
}
