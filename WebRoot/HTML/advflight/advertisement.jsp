<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="../../ExtJS/resources/css/ext-all.css"/>
		<script type="text/javascript" src="../../ExtJS/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../../ExtJS/ext-all.js"></script>
		<script type="text/javascript" src="advertisement.js"></script>
	</head>
	<body>
		<input type="hidden" id="advActiveId" name="advActiveId" value="${param.advActiveId}">
		<div id="north-div"></div>
		<div id="grid"></div>
		<div id="relation-win"></div>
		<div id="relationProduct_win"></div>
		<div id="updateState_win"></div>
		<div id="code_win"></div>
	</body>
</html>