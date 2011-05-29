package com.googlecode.jtiger.modules.ecside.common.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;

@SuppressWarnings("unchecked")
public class LogHandler {
	
//	private Log logger = LogFactory.getLog(Class.class);
	
//	public static boolean output=true;
	
	protected static Log createLogger(Class classType){
		return LogFactory.getLog(classType);
	}
	
	public static void errorLog(Log logger,String msg,Throwable t){
		 if (logger.isErrorEnabled()) {
			 logger.error(msg,t);
		 }
	}
	
	public static void warnLog(Log logger,String msg,Throwable t){
		if (logger.isWarnEnabled()) {
			logger.warn(msg,t);
		}
	}
	
	public static void infoLog(Log logger,String msg,Throwable t){
		if (logger.isInfoEnabled()) {
			logger.info(msg,t);
		}
	}
	
	public static void debugLog(Log logger,String msg,Throwable t){
		if (logger.isDebugEnabled()) {
			logger.debug(msg,t);
		}
	}
	

	public static void errorLog(Log logger,Class clazz,String msg){
		errorLog(logger," ===== "+clazz.getName()+" ===== \n"+msg,null);
	}
	public static void errorLog(Log logger,String msg){
		errorLog(logger,msg,null);
	}
	public static void errorLog(Log logger,Throwable t){
		errorLog(logger,ExceptionUtils.formatStackTrace(t),null);
	}

	public static void warnLog(Log logger,Class clazz,String msg){
		warnLog(logger," ===== "+clazz.getName()+" ===== \n"+msg,null);
	}
	public static void warnLog(Log logger,String msg){
		warnLog(logger,msg,null);
	}
	public static void warnLog(Log logger,Throwable t){
		warnLog(logger,ExceptionUtils.formatStackTrace(t),null);
	}
	
	public static void infoLog(Log logger,Class clazz,String msg){
		infoLog(logger," ===== "+clazz.getName()+" ===== \n"+msg,null);
	}
	public static void infoLog(Log logger,String msg){
		infoLog(logger,msg,null);
	}
	
	public static void debugLog(Log logger,Class clazz,String msg){
		debugLog(logger," ===== "+clazz.getName()+" ===== \n"+msg,null);
	}
	public static void debugLog(Log logger,String msg){
		debugLog(logger,msg,null);
	}

		
}
