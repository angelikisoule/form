<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>Feed Form</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li><a href="<c:url value='/admin/feed' />">Feed</a></li>
  				<li class="active">Feed Form</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>Feed Form</h3>
					</div>			
				</div>
			</div>
			<!-- Feed Form -->
			<div class="row">
				<div class="col-xs-12">
					<form:form action="." modelAttribute="feed" class="form-horizontal">
						<form:hidden path="id" /><!-- In Order To persist or merge Using The Same Controller Method -->
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.feed'].hasFieldErrors('url') ? 'has-error has-feedback' : ''}">
							<form:label path="url" class="col-xs-2 control-label">URL</form:label>
							<div class="col-xs-10">
								<form:input path="url" placeholder="URL" maxlength="255" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.feed'].hasFieldErrors('url')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="url" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.feed'].hasFieldErrors('params') ? 'has-error has-feedback' : ''}">
							<form:label path="params" class="col-xs-2 control-label">Params</form:label>
							<div class="col-xs-10">
								<form:input path="params" placeholder="Params" maxlength="255" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.feed'].hasFieldErrors('params')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="params" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.feed'].hasFieldErrors('profile') ? 'has-error has-feedback' : ''}">
							<form:label path="profile" class="col-xs-2 control-label">Profile</form:label>
							<div class="col-xs-10">
								<form:input path="profile" placeholder="Profile" maxlength="45" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.feed'].hasFieldErrors('profile')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="profile" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.feed'].hasFieldErrors('category') ? 'has-error' : ''}">
							<form:label path="category" class="col-xs-2 control-label">Category</form:label>
							<div class="col-xs-10">
								<form:select path="category" class="form-control">
									<form:option value="" label="Select Category" />			
									<form:options items="${selectCategory}" itemValue="id" itemLabel="name" />
								</form:select>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="category" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.feed'].hasFieldErrors('feedFlag') ? 'has-error' : ''}">
							<form:label path="feedFlag" class="col-xs-2 control-label">FeedFlag</form:label>
							<div class="col-xs-10">
								<form:select path="feedFlag" class="form-control">
									<form:option value="" label="Select Feed Flag" />			
									<form:options items="${selectFeedFlag}" />
								</form:select>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="feedFlag" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.feed'].hasFieldErrors('feedStatus') ? 'has-error' : ''}">
							<form:label path="feedStatus" class="col-xs-2 control-label">FeedStatus</form:label>
							<div class="col-xs-10">
								<form:select path="feedStatus" class="form-control">
									<form:option value="" label="Select Feed Status" />			
									<form:options items="${selectFeedStatus}" />
								</form:select>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="feedStatus" class="has-error help-block" />
							</div>
						</div>
						<div class="col-xs-10 col-xs-offset-2">
							<button type="submit" class="btn btn-lg btn-primary">Submit</button>
							<c:if test="${not empty feed.id}">
								<a class="btn btn-lg btn-primary confirm-dialog" dialog="Are You Sure You Want To Delete This Feed?" href="<c:url value='/admin/feed/delete/${feed.id}' />" role="button">Delete Feed</a>
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