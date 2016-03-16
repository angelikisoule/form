<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>List Of Roles</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li class="active">Roles</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>List Of Roles</h3>
					</div>				
				</div>
			</div>
			<!-- Roles List -->		
			<div class="row">
				<!-- Alert Messages -->
				<jsp:include page="../alerts.jsp"/>
				<!-- Table -->
				<div class="col-xs-12">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th class="hidden-xs">Id</th>
									<th>Name</th>
									<th class="t-wider hidden-xs">Date Updated</th>
									<th class="t-narrow">Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${roles}" var="role">
									<tr>
										<td class="hidden-xs">${role.id}</td>
										<td>${role.name}</td>
										<td class="t-wider hidden-xs text-nowrap"><spring:eval expression="role.dateUpdated" /></td>
										<td class="t-narrow">
											<a href="<c:url value='/admin/role/update/${role.id}' />" title="Edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
											<a class="confirm-dialog" dialog="Are You Sure You Want To Delete This Role?" href="<c:url value='/admin/role/delete/${role.id}' />" title="Delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- Pagination And Extra Actions -->
			<div class="row">
				<div class="col-xs-12 col-md-6">
					<a class="btn btn-lg btn-primary" href="<c:url value='/admin/role/create' />" role="button">Create Role</a>
				</div>
				<div class="col-xs-12 col-md-6">
					<!-- Nothing For Now -->
				</div>
			</div>
		</div><!-- container-fluid -->
		<jsp:include page="../footer.jsp"/>	
		<!-- BootBox Confirm Dialogs -->
		<script src="<c:url value='/static/admin/bootbox-4.4.0/bootbox.min.js' />"></script>
		<script src="<c:url value='/static/admin/admin.js' />"></script>
	</body>
</html>