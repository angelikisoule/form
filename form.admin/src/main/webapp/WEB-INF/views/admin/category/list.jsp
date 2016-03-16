<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>List Of Categories</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li class="active">Categories</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>List Of Categories</h3>
					</div>				
				</div>
			</div>
			<!-- Categories List -->		
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
									<th>Section Unique Name</th>
									<th>Group Name</th>
									<th>Publication</th>
									<th class="t-wider hidden-xs">Date Updated</th>
									<th class="t-narrow">Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${categories}" var="category">
									<tr>
										<td class="hidden-xs">${category.id}</td>
										<td>${category.name}</td>
										<td>${category.sectionUniqueName}</td>
										<td>
											<c:choose>
												<c:when test="${not empty category.groupName}">
													${category.groupName}
												</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</td>
										<td>${category.publication.name}</td>
										<td class="t-wider hidden-xs text-nowrap"><spring:eval expression="category.dateUpdated" /></td>
										<td class="t-narrow">
											<a href="<c:url value='/admin/category/update/${category.id}' />" title="Edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
											<a class="confirm-dialog" dialog="Are You Sure You Want To Delete This Category?" href="<c:url value='/admin/category/delete/${category.id}' />" title="Delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
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
					<a class="btn btn-lg btn-primary" href="<c:url value='/admin/category/create' />" role="button">Create Category</a>
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