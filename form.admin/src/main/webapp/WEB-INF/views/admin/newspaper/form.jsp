<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>Newspaper Form</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li><a href="<c:url value='/admin/article' />">Articles</a></li>
  				<li><a href="<c:url value='/admin/newspaper?type=newspaper' />">Newspapers</a></li>
  				<li class="active">Newspaper Form</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>Newspaper Form</h3>
					</div>	
				</div>
			</div>
			<!-- Newspaper Form -->
			<div class="row">
				<!-- Alert Messages -->
				<jsp:include page="../alerts.jsp"/>
				<div class="col-xs-12">
					<form:form action="." modelAttribute="newspaper" class="form-horizontal">
						<form:hidden path="id" />
						<div class="form-group">
							<form:hidden path="eceArticleId" />
							<form:label path="eceArticleId" class="col-xs-2 control-label">EceId</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">${newspaper.eceArticleId}</p>
							</div>
						</div>
						<div class="form-group">
							<form:hidden path="articleType" />
							<form:label path="articleType" class="col-xs-2 control-label">Type</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">${newspaper.articleType}</p>
							</div>
						</div>
						<div class="form-group">
							<form:hidden path="articleState" />
							<form:label path="articleState" class="col-xs-2 control-label">State</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">${newspaper.articleState}</p>
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.newspaper'].hasFieldErrors('title') ? 'has-error has-feedback' : ''}">
							<form:label path="title" class="col-xs-2 control-label">Title</form:label>
							<div class="col-xs-10">
								<form:input path="title" placeholder="Title" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.newspaper'].hasFieldErrors('title')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="title" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group">
							<form:hidden path="alternate" />
							<form:label path="alternate" class="col-xs-2 control-label">Alternate</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">${newspaper.alternate}</p>
							</div>
						</div>
						<div class="form-group">
							<form:hidden path="link" />
							<form:label path="link" class="col-xs-2 control-label">Cover</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">${newspaper.link}</p>
							</div>
						</div>
						<!-- Thumbnail -->
						<div class="col-xs-10 col-xs-offset-2">
							<div class="col-xs-3">
								<a href="${newspaper.link}" target="_blank"> 
									<img src="${newspaper.link}" class="img-responsive img-thumbnail" />
								</a>
							</div>
						</div>
						<!-- Categories -->
						<c:if test="${fn:length(categories)>0}">
							<div class="form-group">
								<form:hidden path="categories" />
								<form:label path="categories" class="col-xs-2 control-label">Categories</form:label>
								<div class="col-xs-10">
									<c:forEach items="${categories}" var="category" varStatus="loop">
										<p class="form-control-static well well-sm">
											<strong>${category.name}</strong> [ ${category.sectionUniqueName}<c:if test="${not empty category.groupName}"> | ${category.groupName}</c:if> | ${category.publication.name} ]
											<c:choose>
												<c:when test="${loop.index==0}">
													<span class="pull-right" title="Home Category"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></span>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${newspaper.articleState!='ARCHIVED'}">
															<a class="pull-right delete-category" title="Delete" href="<c:url value='/admin/article/deleteCategory/?articleId=${newspaper.id}&categoryId=${category.id}' />"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
														</c:when>
														<c:otherwise>
															<span class="pull-right" title="Archived"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></span>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</p>
									</c:forEach>
								</div>
							</div>
						</c:if>
						<!-- Authors -->
						<c:if test="${fn:length(authors)>0}">
							<div class="form-group">
								<form:hidden path="authors" />
								<form:label path="authors" class="col-xs-2 control-label">Authors</form:label>
								<div class="col-xs-10">
									<c:forEach items="${authors}" var="author">
										<p class="form-control-static well well-sm">
											${author.name}
											<c:choose>
												<c:when test="${newspaper.articleState!='ARCHIVED'}">
													<a class="pull-right delete-author" title="Delete" href="<c:url value='/admin/article/deleteAuthor/?articleId=${newspaper.id}&authorId=${author.id}' />"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
												</c:when>
												<c:otherwise>
													<span class="pull-right" title="Archived"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></span>
												</c:otherwise>
											</c:choose>
										</p>
									</c:forEach>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<form:hidden path="datePublished" />
							<form:label path="datePublished" class="col-xs-2 control-label">Date Published</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">
									<c:choose>
										<c:when test="${not empty newspaper.datePublished}">
											<spring:eval expression="newspaper.datePublished" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</p>
							</div>
						</div>
						<div class="form-group">
							<form:hidden path="dateLastUpdated" />
							<form:label path="dateLastUpdated" class="col-xs-2 control-label">Date Last Updated</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">
									<c:choose>
										<c:when test="${not empty newspaper.dateLastUpdated}">
											<spring:eval expression="newspaper.dateLastUpdated" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</p>
							</div>
						</div>
						<div class="col-xs-10 col-xs-offset-2">
							<c:if test="${newspaper.articleState!='ARCHIVED'}">
								<button type="submit" class="btn btn-lg btn-primary">Submit</button>
								<a class="btn btn-lg btn-primary confirm-dialog" dialog="Are You Sure You Want To Archive This Newspaper" accept="Archive" href="<c:url value='/admin/article/archive/${newspaper.id}' />" role="button">Archive Newspaper</a>
							</c:if>
						</div>
					</form:form>
				</div>
			</div>
		</div><!-- container-fluid -->
		<jsp:include page="../footer.jsp"/>
		<!-- BootBox Confirm Dialogs -->
		<script src="<c:url value='/static/admin/bootbox-4.4.0/bootbox.min.js' />"></script>
		<script src="<c:url value='/static/admin/admin.js' />"></script>
	</body>
</html>