package com.xz.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * falcon -- 2016/12/26.
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(SpringContextHolder.class);
    private static ApplicationContext applicationContext = null;

    public SpringContextHolder() {
    }

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        if(LOG.isDebugEnabled()) {
            LOG.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);
        }

        if(applicationContext != null) {
            LOG.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + applicationContext);
        }
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T)applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBeansOfType(requiredType);
    }

    public static void clear() {
        if(LOG.isDebugEnabled()) {
            LOG.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        }

        applicationContext = null;
    }

    private static void assertContextInjected() {
        if(applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }

    public void destroy() throws Exception {
        clear();
    }

}
