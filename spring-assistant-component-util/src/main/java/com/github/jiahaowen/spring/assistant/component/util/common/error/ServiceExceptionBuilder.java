package com.github.jiahaowen.spring.assistant.component.util.common.error;

/**
 * @author chuanmu
 * @since 2018/5/14
 */
public class ServiceExceptionBuilder {
    private int errorCode = 1;
    private String errorMsg = null;
    private String extStatus = null;
    private Boolean isNeedRetry = null;
    private Throwable cause;
    private boolean isNeedToLogTrace = true;
    public boolean shouldHideErrorMessageFromUser = false;

    public ServiceExceptionBuilder() {}

    public ServiceExceptionBuilder setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ServiceExceptionBuilder setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public ServiceExceptionBuilder setExtStatus(String extStatus) {
        this.extStatus = extStatus;
        return this;
    }

    public ServiceExceptionBuilder setNeedRetry(Boolean needRetry) {
        this.isNeedRetry = needRetry;
        return this;
    }

    public ServiceExceptionBuilder setNeedToLogTrace(boolean needToLogTrace) {
        this.isNeedToLogTrace = needToLogTrace;
        return this;
    }

    public ServiceExceptionBuilder setCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public ServiceExceptionBuilder hideMsgFromUser() {
        this.shouldHideErrorMessageFromUser = true;
        return this;
    }

    public ServiceException build() {
        ServiceException serviceException =
                new ServiceException(this.errorCode + "", this.errorMsg, this.cause);
        serviceException.intErrorCode = this.errorCode;
        serviceException.extStatus = this.extStatus;
        serviceException.isNeedRetry = this.isNeedRetry;
        serviceException.isNeedToLogTrace = this.isNeedToLogTrace;
        serviceException.shouldHideErrorMessageFromUser = this.shouldHideErrorMessageFromUser;
        return serviceException;
    }

    public void throwEx() {
        throw this.build();
    }
}
