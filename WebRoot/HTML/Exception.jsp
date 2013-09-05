<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<body>
		异常错误！<br>
		<s:property value="exception.message"/><br>
		<s:property value="exceptionStack"/>
	</body>
</html>
