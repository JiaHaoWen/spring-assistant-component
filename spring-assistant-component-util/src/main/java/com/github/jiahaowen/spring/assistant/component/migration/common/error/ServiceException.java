/** WaCai Inc. Copyright (c) 2009-2018 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.migration.common.error;

/**
 * @author chuanmu
 * @since 2018/5/14
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 7344741039484694436L;
    public String errorCode;
    public int intErrorCode;
    public String errorMsg;
    public String extStatus;
    public Boolean isNeedRetry;
    public boolean isNeedToLogTrace;
    public boolean shouldHideErrorMessageFromUser;

    /** @deprecated */
    @Deprecated
    public ServiceException(String errorMsg) {
        this("1", errorMsg);
    }

    /** @deprecated */
    @Deprecated
    public ServiceException(Throwable cause) {
        this("1", cause.getMessage(), cause);
    }

    /** @deprecated */
    @Deprecated
    public ServiceException(String errorCode, String errorMsg) {
        this(errorCode, errorMsg, (Throwable) null);
    }

    /** @deprecated */
    @Deprecated
    public ServiceException(String errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.isNeedToLogTrace = true;
        this.shouldHideErrorMessageFromUser = false;
        this.intErrorCode = Integer.parseInt(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static ServiceException buildInternalEx(String errorMsg, Throwable cause) {
        return (new ServiceExceptionBuilder())
                .setErrorMsg(errorMsg)
                .hideMsgFromUser()
                .setCause(cause)
                .build();
    }

    public static ServiceException buildInternalEx(String errorMsg) {
        return buildInternalEx(errorMsg, (Throwable) null);
    }

    public static ServiceException buildInternalEx(Throwable cause) {
        return buildInternalEx((String) null, cause);
    }

    public static ServiceException buildExternalEx(String errorMsg) {
        return (new ServiceExceptionBuilder())
                .setErrorMsg(errorMsg)
                .setNeedToLogTrace(false)
                .build();
    }

    public static ServiceException buildExternalEx(int errorCode) {
        return (new ServiceExceptionBuilder())
                .setErrorCode(errorCode)
                .setErrorMsg("Error code: " + errorCode)
                .setNeedToLogTrace(false)
                .build();
    }

    public static ServiceException buildExternalEx(int errorCode, String errorMsg) {
        return (new ServiceExceptionBuilder())
                .setErrorCode(errorCode)
                .setErrorMsg("Error code: " + errorCode)
                .setNeedToLogTrace(false)
                .build();
    }
}
