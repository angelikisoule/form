<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>Category Form</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li><a href="<c:url value='/admin/category' />">Categories</a></li>
  				<li class="active">Category Form</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>Category Form</h3>
					</div>			
				</div>
			</div>
			<!-- Category Form -->
			<div class="row">
				<div class="col-xs-12">
					<form:form action="." modelAttribute="category" class="form-horizontal">
						<form:hidden path="id" /><!-- In Order To persist or merge Using The Same Controller Method -->
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.category'].hasFieldErrors('name') ? 'has-error has-feedback' : ''}">
							<form:label path="name" class="col-xs-2 control-label">Name</form:label>
							<div class="col-xs-10">
								<form:input path="name" placeholder="Name" maxlength="45" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.category'].hasFieldErrors('name')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="name" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.category'].hasFieldErrors('sectionUniqueName') ? 'has-error has-feedback' : ''}">
							<form:label path="sectionUniqueName" class="col-xs-2 control-label">Section Unique Name</form:label>
							<div class="col-xs-10">
								<form:input path="sectionUniqueName" placeholder="Section Unique Name" maxlength="45" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.category'].hasFieldErrors('sectionUniqueName')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="sectionUniqueName" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.category'].hasFieldErrors('groupName') ? 'has-error has-feedback' : ''}">
							<form:label path="groupName" class="col-xs-2 control-label">Group Name</form:label>
							<div class="col-xs-10">
								<form:input path="groupName" placeholder="Group Name" maxlength="45" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.category'].hasFieldErrors('groupName')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="groupName" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.category'].hasFieldErrors('publication') ? 'has-error' : ''}">
							<form:label path="publication" class="col-xs-2 control-label">Publication</form:label>
							<div class="col-xs-10">
								<form:select path="publication" class="form-control">
									<form:option value="" label="Select Publication" />			
									<form:options items="${selectPublication}" itemValue="id" itemLabel="name" />
								</form:select>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="publication" class="has-error help-block" />
							</div>
						</div>
						<div class="col-xs-10 col-xs-offset-2">
							<button type="submit" class="btn btn-lg btn-primary">Submit</button>
							<c:if test="${not empty category.id}">
								<a class="btn btn-lg btn-primary confirm-dialog" dialog="Are You Sure You Want To Delete This Category?" href="<c:url value='/admin/category/delete/${category.id}' />" role="button">Delete Category</a>
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