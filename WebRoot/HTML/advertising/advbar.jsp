<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>广告位下的广告条</title>
		<link rel="stylesheet" type="text/css" href="../../ExtJS/resources/css/ext-all.css"/>
		<script type="text/javascript" src="../../ExtJS/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../../ExtJS/ext-all.js"></script>
		<script type="text/javascript" src="advbar.js"></script>
	</head>
	<body>
		<input type="hidden" id="advpositionId" name="advpositionId" value="${param.advpositionId}">
		<input type="hidden" id="status" name="status" value="${param.status}">
		<input type="hidden" id="postemId" name="status" value="${param.postemId}">
		<div id="edit_win_div"></div>
		<div id='updatestate_win'></div>
		<div id="moduluse_win"></div>
		<div id="north-div"></div>
		<div id="grid"></div>
	</body>
</html>