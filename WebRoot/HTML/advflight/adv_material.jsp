<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>关联物料</title>
		<link rel="stylesheet" type="text/css" href="../../ExtJS/resources/css/ext-all.css"/>
		<script type="text/javascript" src="../../ExtJS/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../../ExtJS/ext-all.js"></script>
		<script type="text/javascript" src="adv_material.js"></script>
	</head>
	
	<body>
		<%@ page language="java" contentType="text/html; charset=UTF-8"
		    pageEncoding="UTF-8"%>
		<%@ page import="java.net.*" %>
		<%
			String advName = URLDecoder.decode(request.getParameter("advName"),"utf-8");
			
		%>
		<input type="hidden" id="advId" name="advId" value="${param.advId}">
		<input type="hidden" id="advName" name="advName" value="<%=advName %>">
		<div id="north-div"></div>
		<div id="grid"></div>
	
		
	</body>
</html>