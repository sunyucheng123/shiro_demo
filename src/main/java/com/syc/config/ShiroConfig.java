package com.syc.config;

import com.syc.shiro.realm.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 配置SecurityManager对象(shiro核心安全管理器对象)
     * @param shiroRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //构建凭证匹配对象
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");//设置加密方式
        matcher.setHashIterations(1);//设置加密次数

        shiroRealm.setCredentialsMatcher(matcher);

        defaultWebSecurityManager.setRealm(shiroRealm);

        return defaultWebSecurityManager;
    }

    /**
     *      * 	 配置ShiroFilterFactoryBean对象，通过此对象
     *      * 	 创建过滤器工厂，并指定过滤规则
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> map=new LinkedHashMap<>();
        map.put("/index/login","anon");
        map.put("/user/login","anon");
        map.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //===========授权配置============
    /**
     * spring 框架管理此对象时,会基于此对象管理
     * Shiro框架中相关API对象的生命周期
     */
    @Bean //<bean id="" class="">
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     *    配置Advisor对象,此对象中会对切入点,通知等对象进行
     *    相关描述,后续DefaultAdvisorAutoProxyCreator对象
     *    会基于描述,为目标对象创建代理对象
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor
    authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor=
                new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
