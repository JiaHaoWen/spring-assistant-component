package com.github.jiahaowen.spring.assistant.component.rule.model;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * @author jiahaowen.jhw
 * @version $Id: ResultDO.java, v 0.1 2016-12-02 上午10:41 jiahaowen.jhw Exp $
 */
public class ResultDO<T> {

    private boolean success = false;

    private String errorMsg;

    private List<T> list = Lists.newArrayList();

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        if (StringUtils.isNotBlank(errorMsg)) {
            this.errorMsg = errorMsg;
            this.setSuccess(false);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setModuleList(List<T> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            this.list = list;
        }
    }
}
