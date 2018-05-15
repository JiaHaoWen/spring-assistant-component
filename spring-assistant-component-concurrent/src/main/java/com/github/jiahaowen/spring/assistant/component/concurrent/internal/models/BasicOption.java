package com.github.jiahaowen.spring.assistant.component.concurrent.internal.models;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 业务请求基类，其中uniqueId为必填
 *
 * @author chuanmu
 * @since 2017/11/17
 */
public class BasicOption implements Serializable {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 673836422357896484L;

    /**
     * 操作唯一性标志，标志单一业务逻辑入参的唯一性。
     *
     * <p>例如：
     *
     * <p>某次批量并发操作存在20条请求，则认为这20条请求应均不相同。 同时进行多次相同的请求，认为违反业务幂等语义。
     *
     * <p><b>必填</b>
     */
    protected String uniqueId;

    /** 构造函数 */
    public BasicOption() {}

    public BasicOption(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /** @see Object#toString() */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
