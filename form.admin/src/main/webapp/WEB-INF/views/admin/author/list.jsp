<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>List Of Authors</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li class="active">Authors</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>List Of Authors</h3>
					</div>				
				</div>
			</div>
			<!-- Authors List -->
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
								<c:forEach items="${authors}" var="author">
									<tr>
										<td class="hidden-xs">${author.id}</td>
										<td>${author.name}</td>
										<td class="t-wider hidden-xs text-nowrap"><spring:eval expression="author.dateUpdated" /></td>
										<td class="t-narrow">
											<a href="<c:url value='/admin/author/update/${author.id}' />" title="Edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
											<a class="confirm-dialog" dialog="Are You Sure You Want To Delete This Author?" href="<c:url value='/admin/author/delete/${author.id}' />" title="Delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
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
					<a class="btn btn-lg btn-primary" href="<c:url value='/admin/author/create' />" role="button">Create Author</a>
				</div>
				<div class="col-xs-12 col-md-6">
					<nav>
						<ul class="pagination pagination-lg pull-right">
							<jsp:include page="../pagination.jsp" />
						</ul>
					</nav>
				</div>
			</div>
		</div><!-- container-fluid -->
		<jsp:include page="../footer.jsp"/>	
		<!-- BootBox Confirm Dialogs -->
		<script src="<c:url value='/static/admin/bootbox-4.4.0/bootbox.min.js' />"></script>
		<script src="<c:url value='/static/admin/admin.js' />"></script>
	</body>
</html>