<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>List Of Feeds</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li class="active">Feeds</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>List Of Feeds</h3>
					</div>
				</div>
			</div>
			<!-- Feeds List -->		
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
									<th>URL</th>
									<th class="t-limited">Params</th>
									<th class="hidden-xs">Profile</th>
									<th>Category</th>
									<th class="hidden-xs">Feed Flag</th>
									<th>Feed Status</th>
									<th class="t-wider hidden-xs">Date Updated</th>
									<th class="t-narrow">Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${feeds}" var="feed">
									<tr>
										<td class="hidden-xs">${feed.id}</td>
										<td>${feed.url}</td>
										<td class="t-limited">${feed.params}</td>
										<td class="hidden-xs">
											<c:choose>
												<c:when test="${not empty feed.profile}">
													${feed.profile}	
												</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>			
										</td>
										<td>
											<c:choose>
												<c:when test="${not empty feed.category.name}">
													${feed.category.name}
												</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</td>
										<td class="hidden-xs">${feed.feedFlag}</td>
										<td>${feed.feedStatus}</td>
										<td class="t-wider hidden-xs text-nowrap"><spring:eval expression="feed.dateUpdated" /></td>
										<td class="t-narrow">
											<a href="<c:url value='/admin/feed/update/${feed.id}' />" title="Edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
											<a class="confirm-dialog" dialog="Are You Sure You Want To Delete This Feed?" href="<c:url value='/admin/feed/delete/${feed.id}' />" title="Delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
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
					<a class="btn btn-lg btn-primary" href="<c:url value='/admin/feed/create' />" role="button">Create Feed</a>
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