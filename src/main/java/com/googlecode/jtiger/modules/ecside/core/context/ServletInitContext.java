package com.googlecode.jtiger.modules.ecside.core.context;

import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

@SuppressWarnings("unchecked")
public class ServletInitContext  implements WebContext {
	
	private ServletContext servletContext;
	public ServletInitContext (ServletContext servletContext){
		this.servletContext=servletContext;
	}
	public String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}
	
	public Object getBackingObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLocale(Locale locale) {
		// TODO Auto-generated method stub
		
	}

	public void setParameterMap(Map parameterMap) {
		// TODO Auto-generated method stub
		
	}

	public Object getApplicationAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getApplicationInitParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getContextObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPageAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getRequestAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getSessionAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Writer getWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeApplicationAttribute(String name) {
		// TODO Auto-generated method stub
		
	}

	public void removePageAttribute(String name) {
		// TODO Auto-generated method stub
		
	}

	public void removeRequestAttribute(String name) {
		// TODO Auto-generated method stub
		
	}

	public void removeSessionAttribute(String name) {
		// TODO Auto-generated method stub
		
	}

	public void setApplicationAttribute(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	public void setPageAttribute(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	public void setRequestAttribute(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	public void setSessionAttribute(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

}
