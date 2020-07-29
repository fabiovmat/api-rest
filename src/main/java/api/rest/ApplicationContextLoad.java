package api.rest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextLoad implements ApplicationContextAware{

	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		ApplicationContextLoad.applicationContext = applicationContext;
		
		
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	
}
