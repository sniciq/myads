<%@ page pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>热播剧视频</title>
		<link rel="stylesheet" type="text/css" href="../../ExtJS/resources/css/ext-all.css"/>
		<script type="text/javascript" src="../../ExtJS/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../../ExtJS/ext-all.js"></script>
		<script language="text/javascript" src="../../ExtJS/layout.js"></script>
		<script type="text/javascript" src="report-hotfilm-video.js"></script>
	</head>
	<body>
		<input type="hidden" id="filmId" name="filmId" value="${param.filmId}">
		<div id="videoGrid"></div>
		<div id="north-div"></div>
		<div id="editFormWin"></div>
	</body>
</html>