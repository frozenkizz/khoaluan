<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- include taglib -->
<%@ include file="/common/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title><tiles:insertAttribute name="title" /></title>

<link href="<c:url value='/template/admin/dist/css/styles.css'/>" rel="stylesheet" />
<link href="<c:url value='/template/web/plugins/datatables/css/dataTables.bootstrap.min.css'/>" rel="stylesheet"/>
<link href="<c:url value='/template/web/plugins/datatables/css/dataTables.bootstrap4.min.css'/>" rel="stylesheet"/>
<link href="<c:url value='/template/web/plugins/datatables/css/buttons.dataTables.min.css'/>" rel="stylesheet"/>
<link href="<c:url value='/template/web/plugins/font-awesome/css/all.min.css'/>" rel="stylesheet"/>
<link href="<c:url value='/template/admin/dist/css/admin.css'/>" rel="stylesheet"/>

</head>
<body class="sb-nav-fixed">
	<!-- HEADER -->
	<tiles:insertAttribute name="header" />
	<!-- END HEADER -->

	<div id="layoutSidenav">
		<div id="layoutSidenav_nav">
			<!-- HEADER -->
			<tiles:insertAttribute name="menu" />
			<!-- END HEADER -->
		</div>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
					<!-- BODY -->
					<tiles:insertAttribute name="body" />
					<!-- END BODY -->
				</div>
			</main>
			<!-- FOOTER -->
			<tiles:insertAttribute name="footer" />
			<!-- END FOOTER -->
		</div>
	</div>

	<%-- 
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		crossorigin="anonymous"></script>
	<script src="/template/web/plugins/jquery/jquery-3.5.1.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="<c:url value='/template/admin/dist/js/scripts.js'/>"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"
		crossorigin="anonymous"></script>
	<script
		src="<c:url value='/template/admin/dist/assets/demo/chart-area-demo.js'/>"></script>
	<script
		src="<c:url value='/template/admin/dist/assets/demo/chart-bar-demo.js'/>"></script>
	
	<script
		src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"
		crossorigin="anonymous"></script>
	<script
		src="<c:url value='/template/admin/dist/assets/demo/datatables-demo.js'/>"></script> --%>
	<!-- Javascript render data  --> 
	<%--<script src="<c:url value='/template/web/plugins/jquery/jquery-3.5.1.min.js'/>"></script>
	<script src="<c:url value='/template/web/plugins/jquery/jquery.validate.min.js'/>"></script>
	<script src="<c:url value='/template/web/plugins/bootstrap/js/bootstrap.min.js'/>"></script>
	<script src="<c:url value='/template/web/plugins/datatables/js/jquery.dataTables.min.js'/>"></script>
	<script src="<c:url value='/template/web/plugins/datatables/js/dataTables.buttons.min.js'/>"></script>
	<script src="<c:url value='/template/web/plugins/datatables/js/dataTables.bootstrap4.min.js'/>"></script>
	<script src="<c:url value='/template/admin/dist/js/scripts.js'/>"></script>
	<script src="<c:url value='/template/admin/dist/assets/demo/category-admin.js'/>"></script> --%>
</body>
</html>
