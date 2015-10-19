package com.warehouse.common;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ContextLoader;

/**
 * 常用的全局属性，方便在界面中直接使用，存入application中
 */

public class InitGlobalProperty implements ApplicationListener<ContextRefreshedEvent> {
	private static final Log LOG = LogFactory.getLog(InitGlobalProperty.class);
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
			//在项目中存在两个容器，一个是root application context ,另一个就是我们自己的 projectName-servlet  context（作为root application context的子容器）。
			//这种情况下，就会造成onApplicationEvent方法被执行两次。
			//只在子 application context初始化完成后调用逻辑代码，因为root application context 没找到办法获取servletContext
		if(event.getApplicationContext().getParent() != null){//root application context 没有parent  
			ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			//存入工程根目录(工程名)
	        servletContext.setAttribute("rootPath", servletContext.getContextPath());
	        servletContext.setAttribute("theme","default");
	        LOG.debug("Init global properties completed.");
		} 
	}

}
