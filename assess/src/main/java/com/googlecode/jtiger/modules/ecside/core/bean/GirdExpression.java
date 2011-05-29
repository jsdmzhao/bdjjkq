package com.googlecode.jtiger.modules.ecside.core.bean;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;


public class GirdExpression {
	private Context cx = ContextFactory.getGlobal().enterContext();
	private Scriptable scope = cx.initStandardObjects();
	private Scriptable scriptObject= cx.newObject(scope);
	
	private String argumentNames;
	
	private StringBuffer functionCode=new StringBuffer();

	
	private Function functionCore;
	
	public GirdExpression() {}
	public GirdExpression(String argumentNames) {
		setArgumentNames(argumentNames);
	}
	
	public void setArgument(String argumentName, Object argumentValue){
		scriptObject.put(argumentName, scriptObject,argumentValue);
	}
	
	public void build(String expression){
		build(argumentNames,expression);
	}
	
	public void build(String argumentNames, String expression){
		this.argumentNames=argumentNames;
		functionCode.append("function expressionFunction(").append(argumentNames).append(") { ");
		
		if (expression.toLowerCase().indexOf("return ")<0){
			functionCode.append(" return ( ").append(expression).append(" ); }");
		}else{
			functionCode.append(expression).append(" }");
		}
		
		functionCore = cx.compileFunction(scope,functionCode.toString(), "exfunction", 1,null);

	}
	
	public Object call(){
		return functionCore.call(cx, scope, scope, new Object[]{scriptObject});
	}


	public String getArgumentNames() {
		return argumentNames;
	}

	public void setArgumentNames(String argumentNames) {
		this.argumentNames = argumentNames;
	}

	public Scriptable getScriptObject() {
		return scriptObject;
	}
}
