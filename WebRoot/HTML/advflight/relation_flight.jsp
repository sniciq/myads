<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="../../ExtJS/resources/css/ext-all.css"/>
		<script type="text/javascript" src="../../ExtJS/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../../ExtJS/ext-all.js"></script>
		<script type="text/javascript" src="relation_flight.js"></script>
	</head>
	<body>
		<input type="hidden" name="advertisementId" id="advertisementId" value="${param.advertisementId}"/>
		<input type="hidden" name="bartemplateId" id="bartemplateId" value="${param.bartemplateId}"/>
		<div id="tree_div"></div>
	</body>
</html>