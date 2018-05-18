package com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer;

import com.github.jiahaowen.spring.assistant.component.util.common.util.StringUtil;
import java.util.Set;
import javax.annotation.Nullable;
import org.springframework.util.CollectionUtils;

/**
 * 指定路径属性过滤校验器
 *
 * @author jiahaowen.jhw
 * @version $Id: PathInclusionChecker.java, v 0.1 2017-01-20 14:42 jeff.gf Exp $$
 */
public class PathInclusionChecker {

    /** 排除的路径 */
    private Set<String> excludedPaths;

    /**
     * 构造函数
     *
     * @param excludedPaths
     */
    public PathInclusionChecker(final Set<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

    /**
     * @param path
     * @return
     */
    public boolean apply(@Nullable String path) {

        if (StringUtil.isEmpty(path)) {
            return false;
        }

        if (CollectionUtils.isEmpty(excludedPaths)) {
            return false;
        } else {
            for (String endPath : excludedPaths) {
                if (path.endsWith(endPath)) {
                    return true;
                }
            }
            return false;
        }
    }
}
