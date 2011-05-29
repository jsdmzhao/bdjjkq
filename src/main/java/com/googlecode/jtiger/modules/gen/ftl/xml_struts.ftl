<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts PUBLIC
    "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN"
    "http://struts.apache.org/dtds/struts-2.0.dtd">
<struts>
	<package name="${packageName ? default('')}.${className?lower_case}" extends="default" namespace="/${className?lower_case}">
		<action name="*" class="${className[0]?lower_case}${className[1..]}Action" method="{1}">
		    <result name="index">/WEB-INF/views/${className?lower_case}/index.jsp</result>
		    <result name="input">/WEB-INF/views/${className?lower_case}/edit.jsp</result>
		    <result name="success"  type="redirect">index.htm</result>
		 </action>
	</package>
</struts>