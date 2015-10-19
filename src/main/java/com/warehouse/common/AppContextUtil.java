package com.warehouse.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContextUtil implements ApplicationContextAware {

	private static final Log LOG = LogFactory.getLog(AppContextUtil.class);

	private static ApplicationContext app;

	public static Object getBean(String name) {
		Object bean = null;
		try {
			bean = app.getBean(name);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	public static <T> T getBean(Class<T> clazz) {
		T bean = null;
		try {
			bean = app.getBean(clazz);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(String.format("获取bean: [%s]的时候出错，没有找到bean，"
					+ "请确定你是否在单元测试，如果是的话，没有问题，如果在web应用中"
					+ "看到这个错误，代表没有找到对应的实例。", clazz.getName()));

		}
		return bean;
	}

	public static ApplicationContext getApp() {
		return app;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		if (app == null)
			app = arg0;

		if (LOG.isDebugEnabled()) {
			LOG.debug("------ApplicationContext: " + arg0);
		}

	}

}
