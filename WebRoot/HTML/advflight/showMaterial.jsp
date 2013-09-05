<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="../../ExtJS/resources/css/ext-all.css"/>
		<link rel="stylesheet" type="text/css" href="../../ThirdPartyJS/style.css"> 
		<script type="text/javascript" src="../../ExtJS/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../../ExtJS/ext-all.js"></script>
		<script type="text/javascript" src="../../ThirdPartyJS/flowplayer-3.2.4.min.js"></script> 
		
		<script type="text/javascript">
			function goBack(){
				window.history.go(-1);
			}
		</script>
		 
	</head>
	<body>
		<input type="hidden" id="path" name="path" value="${param.path}">
		<div id="north-div"></div>
		<div id="grid"></div>
		<table>
			<tr>
				<td>
					<!-- 
					<input type="button" id="back" value="返回" onclick="goBack()"/>
					 -->
				</td>
			</tr>
		
			<tr>
				<td>
					<a  
						 href="${param.path}"  
						 style="display:block;width:520px;height:330px"  
						 id="player"> 
					</a> 
				
					<!-- this will install flowplayer inside previous A- tag. --> 
					<script> 
						flowplayer("player", "../../ThirdPartyJS/flowplayer-3.2.5.swf");
					</script> 
				</td>
			</tr>
			<tr>
			</tr>
		</table>
	</body>
</html>