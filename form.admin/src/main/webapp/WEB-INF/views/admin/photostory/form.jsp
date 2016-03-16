<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>Photostory Form</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li><a href="<c:url value='/admin/article' />">Articles</a></li>
  				<li><a href="<c:url value='/admin/photostory?type=photostory' />">Photostories</a></li>
  				<li class="active">Photostory Form</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>Photostory Form</h3>
					</div>	
				</div>
			</div>
			<!-- Photostory Form -->
			<div class="row">
				<!-- Alert Messages -->
				<jsp:include page="../alerts.jsp"/>
				<div class="col-xs-12">
					<form:form action="." modelAttribute="photostory" class="form-horizontal">
						<form:hidden path="id" />
						<div class="form-group">
							<form:hidden path="eceArticleId" />
							<form:label path="eceArticleId" class="col-xs-2 control-label">EceId</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">${photostory.eceArticleId}</p>
							</div>
						</div>
						<div class="form-group">
							<form:hidden path="articleType" />
							<form:label path="articleType" class="col-xs-2 control-label">Type</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">${photostory.articleType}</p>
							</div>
						</div>
						<div class="form-group">
							<form:hidden path="articleState" />
							<form:label path="articleState" class="col-xs-2 control-label">State</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">${photostory.articleState}</p>
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.photostory'].hasFieldErrors('title') ? 'has-error has-feedback' : ''}">
							<form:label path="title" class="col-xs-2 control-label">Title</form:label>
							<div class="col-xs-10">
								<form:input path="title" placeholder="Title" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.photostory'].hasFieldErrors('title')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="title" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.photostory'].hasFieldErrors('supertitle') ? 'has-error has-feedback' : ''}">
							<form:label path="supertitle" class="col-xs-2 control-label">Supertitle</form:label>
							<div class="col-xs-10">
								<form:input path="supertitle" placeholder="Supertitle" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.photostory'].hasFieldErrors('supertitle')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="supertitle" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.photostory'].hasFieldErrors('leadText') ? 'has-error has-feedback' : ''}">
							<form:label path="leadText" class="col-xs-2 control-label">Lead Text</form:label>
							<div class="col-xs-10">
								<form:input path="leadText" placeholder="Lead Text" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.photostory'].hasFieldErrors('leadText')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="leadText" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group">
							<form:hidden path="alternate" />
							<form:label path="alternate" class="col-xs-2 control-label">Alternate</form:label>
							<div class="col-xs-10">
								<p class="form-control-static">${photostory.alternate}</p>
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
														<c:when test="${photostory.articleState!='ARCHIVED'}">
															<a class="pull-right delete-category" title="Delete" href="<c:url value='/admin/article/deleteCategory/?articleId=${photostory.id}&categoryId=${category.id}' />"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
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
						<!-- Related Articles -->
						<c:if test="${fn:length(relatedArticles)>0}">
							<div class="form-group related">
								<form:label path="relatedArticles" class="col-xs-2 control-label">Related Articles</form:label>
								<div class="col-xs-10">
									<!-- Only Related Pictures For PHOTOSTORY -->
									<c:forEach items="${relatedArticles}" var="related">
										<p class="form-control-static well well-sm">
											${related.related.eceArticleId} - ${related.related.title} <em>[ ${related.enclosureComment} ]</em>
											<c:choose>
												<c:when test="${photostory.articleState!='ARCHIVED'}">
													<a class="pull-right delete-related" title="Delete" href="<c:url value='/admin/article/deleteRelatedArticle/?articleId=${photostory.id}&relatedArticleId=${related.related.id}&enclosureComment=${related.enclosureComment}' />"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
												</c:when>
												<c:otherwise>
													<span class="pull-right" title="Archived"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></span>
												</c:otherwise>
											</c:choose>
											<a href="<c:url value='/admin/picture/update/${related.related.id}' />" class="pull-right pull-right-extra" title="Edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
											<a href="${related.related.alternate}" target="_blank" class="pull-right pull-right-extra" title="View"><span class="glyphicon glyphicon-picture" aria-hidden="true"></span></a>
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
												<c:when test="${photostory.articleState!='ARCHIVED'}">
													<a class="pull-right delete-author" title="Delete" href="<c:url value='/admin/article/deleteAuthor/?articleId=${photostory.id}&authorId=${author.id}' />"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
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
										<c:when test="${not empty photostory.datePublished}">
											<spring:eval expression="photostory.datePublished" />
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
										<c:when test="${not empty photostory.dateLastUpdated}">
											<spring:eval expression="photostory.dateLastUpdated" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</p>
							</div>
						</div>
						<div class="col-xs-10 col-xs-offset-2">
							<c:if test="${photostory.articleState!='ARCHIVED'}">
								<button type="submit" class="btn btn-lg btn-primary">Submit</button>
								<a class="btn btn-lg btn-primary confirm-dialog" dialog="Are You Sure You Want To Archive This Photostory?" accept="Archive" href="<c:url value='/admin/article/archive/${photostory.id}' />" role="button">Archive Photostory</a>
							</c:if>
							<security:authorize access="hasRole('Administrator')">
								<a class="btn btn-lg btn-danger confirm-dialog" dialog="Are You Sure You Want To Force Update Of This Photostory?" accept="Update" href="<c:url value='/admin/forced/${photostory.eceArticleId}' />" role="button">Update Photostory</a>
							</security:authorize>
							<a class="btn btn-lg btn-primary" href="<c:url value='/admin/photostory/view/${photostory.id}' />">View Photostory</a>
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