package com.tickets.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
 * <h1>Controller to shutdown the context</h1> 
 * Calling /shutDownContext will make the server port free to use
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-04-04 
 */
@RestController
public class ShutDownController implements ApplicationContextAware {

	private ApplicationContext context;
	
	@PostMapping("/shutDownContext")
	public String shutDownContext() {
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) context;
        ctx.close();
        return "context is shutdown";
    }
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;		
	}

}
