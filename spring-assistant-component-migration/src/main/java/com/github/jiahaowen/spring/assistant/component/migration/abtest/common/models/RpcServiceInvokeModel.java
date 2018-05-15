package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models;

/**
 * rpc服务转调配置信息
 *
 * @author jiahaowen.jhw
 * @version $Id: RpcServiceInvokeModel.java, v 0.1 2017-02-06 下午3:29 jiahaowen.jhw Exp $
 */
public class RpcServiceInvokeModel {

    /** 接口名 */
    private String interfaceName;

    /** 方法名 */
    private String methodName;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
