package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.rpc.proxy;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.github.jiahaowen.spring.assistant.component.migration.common.error.ServiceException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author chuanmu
 * @since 2018/5/15
 */
public class DubboServiceFactory {

    private ApplicationConfig application;
    private RegistryConfig registry;
    //    private ProtocolConfig protocol;

    /** 设置单例 */
    private static class SingletonHolder {
        private static DubboServiceFactory INSTANCE = new DubboServiceFactory();
    }

    public static DubboServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /** 初始化工厂 */
    private DubboServiceFactory() {
        Properties prop = new Properties();
        ClassLoader loader = DubboServiceFactory.class.getClassLoader();

        /**
         * generic-dubbo-conf.properties文件必须包含下面几项内容
         *
         * <p>generic.dubbo.application.monitor.address=dubbo监控地址
         *
         * <p>generic.dubbo.application.servers=zk注册集群地址
         *
         * <p>generic.dubbo.application.zk.group=dubbo集群类型
         *
         * <p>generic.dubbo.application.name=服务提供的应用名称
         *
         * <p>generic.dubbo.application.owner=应用owner
         *
         * <p>generic.dubbo.application.protocol.port=dubbo的服务协议端口号
         *
         * <p>generic.dubbo.application.protocol.threads=dubbo服务线程池大小
         */
        try {
            // 读取新服务的dubbo配置文件
            prop.load(loader.getResourceAsStream("generic-dubbo-conf.properties"));
        } catch (IOException e) {
            throw ServiceException.buildInternalEx("加载generic-dubbo-conf.properties文件出现异常", e);
        }

        ApplicationConfig applicationConfig = new ApplicationConfig();
        // 这里配置了dubbo的application信息
        // 应用名称
        applicationConfig.setName(prop.getProperty("generic.dubbo.application.name"));
        // owner
        applicationConfig.setOwner(prop.getProperty("generic.dubbo.application.owner"));
        // 监控地址
        applicationConfig.setMonitor(prop.getProperty("generic.dubbo.application.monitor.address"));

        // 这里配置dubbo的注册中心信息
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress(prop.getProperty("generic.dubbo.application.servers"));
        registryConfig.setClient("zkclient");
        registryConfig.setGroup(prop.getProperty("generic.dubbo.application.zk.group"));
        registryConfig.setFile("conf/dubbo-reg-zk.cache");

        // 这里配置dubbo的协议信息
        //        ProtocolConfig protocolConfig = new ProtocolConfig();
        //        protocolConfig.setName("dubbo");
        //        protocolConfig.setPort(
        //
        // Integer.parseInt(prop.getProperty("generic.dubbo.application.protocol.port")));
        //        protocolConfig.setThreadpool("cached");
        //        protocolConfig.setThreads(
        //                Integer.parseInt(prop.getProperty("generic.dubbo.application.zk.group")));

        this.application = applicationConfig;
        this.registry = registryConfig;
        //        this.protocol = protocolConfig;
    }

    /** 泛化调用方法 */
    public Object genericInvoke(
            String interfaceClassName, String methodName, String[] parameterTypes, Object[] args)
            throws Exception {

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        //        reference.setProtocol(protocol);
        // 接口名
        reference.setInterface(interfaceClassName);
        // 声明为泛化接口
        reference.setGeneric(true);

        // ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
        // 需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        // API方式编程时，容易忽略此问题。
        // 这里使用dubbo内置的简单缓存工具类进行缓存

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用

        return genericService.$invoke(methodName, parameterTypes, args);
    }
}
