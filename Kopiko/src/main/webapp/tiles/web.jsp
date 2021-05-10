<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- include taglib -->
<%@ include file="/common/taglib.jsp"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title" /></title>

<!-- CSS -->

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css">
<link rel="stylesheet"
	href="<c:url value = '/template/web/plugins/bootstrap-4.6.0-dist/css/bootstrap.min.css'/>">
<link rel="stylesheet"
	href="<c:url value = '/template/web/plugins/font-awesome/css/all.min.css'/>">
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="<c:url value = '/template/web/css/main.css'/>">
<link rel="stylesheet"
	href="<c:url value = '/template/web/css/base.css'/>">
<link rel="stylesheet"
	href="<c:url value = '/template/web/css/modal.css'/>">
<link rel="stylesheet"
	href="<c:url value = '/template/web/css/style.css'/>">
<link rel="stylesheet"
	href="<c:url value = '/template/web/css/reponsive.css'/>">
<link rel="stylesheet"
	href="<c:url value = '/template/web/css/user.css'/>">
</head>
<body>
	<div class="appkopiko">
		<!-- HEADER -->
		<tiles:insertAttribute name="header" />
		<!-- END HEADER -->

		<!-- CONTENT -->
		<tiles:insertAttribute name="body" />
		<!-- CONTENT -->

		<!-- FOOTER -->
		<tiles:insertAttribute name="footer" />
		<!-- END FOOTER -->
		
	</div>

<script>!function(s,u,b,i,z){var o,t,r,y;s[i]||(s._sbzaccid=z,s[i]=function(){s[i].q.push(arguments)},s[i].q=[],s[i]("setAccount",z),r=["widget.subiz.net","storage.googleapis"+(t=".com"),"app.sbz.workers.dev",i+"a"+(o=function(k,t){var n=t<=6?5:o(k,t-1)+o(k,t-3);return k!==t?n:n.toString(32)})(20,20)+t,i+"b"+o(30,30)+t,i+"c"+o(40,40)+t],(y=function(k){var t,n;s._subiz_init_2094850928430||r[k]&&(t=u.createElement(b),n=u.getElementsByTagName(b)[0],t.async=1,t.src="https://"+r[k]+"/sbz/app.js?accid="+z,n.parentNode.insertBefore(t,n),setTimeout(y,2e3,k+1))})(0))}(window,document,"script","subiz","acqypkewhmivapxhckcs")</script>
</body>
</html>