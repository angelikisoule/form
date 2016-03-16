<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>User Form</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li><a href="<c:url value='/admin/user' />">Users</a></li>
  				<li class="active">User Form</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>User Form</h3>
					</div>			
				</div>
			</div>
			<!-- User Form -->
			<div class="row">
				<div class="col-xs-12">
					<form:form action="." modelAttribute="user" class="form-horizontal">
						<form:hidden path="id" /><!-- In Order To persist or merge Using The Same Controller Method -->
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.user'].hasFieldErrors('username') ? 'has-error has-feedback' : ''}">
							<form:label path="username" class="col-xs-2 control-label">Username</form:label>
							<div class="col-xs-10">
								<form:input path="username" placeholder="Username" maxlength="45" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.user'].hasFieldErrors('username')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="username" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.user'].hasFieldErrors('password') ? 'has-error has-feedback' : ''}">
							<form:label path="password" class="col-xs-2 control-label">Password</form:label>
							<div class="col-xs-10">
								<form:password path="password" placeholder="Password" maxlength="45" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.user'].hasFieldErrors('password')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="password" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.user'].hasFieldErrors('confirmPassword') ? 'has-error has-feedback' : ''}">
							<form:label path="confirmPassword" class="col-xs-2 control-label">Confirm Password</form:label>
							<div class="col-xs-10">
								<form:password path="confirmPassword" placeholder="Confirm Password" maxlength="45" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.user'].hasFieldErrors('confirmPassword')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="confirmPassword" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.user'].hasFieldErrors('email') ? 'has-error has-feedback' : ''}">
							<form:label path="email" class="col-xs-2 control-label">E-mail</form:label>
							<div class="col-xs-10">
								<form:input path="email" placeholder="E-mail" maxlength="100" class="form-control" />
								<c:if test="${requestScope['org.springframework.validation.BindingResult.user'].hasFieldErrors('email')}">
									<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
								</c:if>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="email" class="has-error help-block" />
							</div>
						</div>
						<div class="form-group ${requestScope['org.springframework.validation.BindingResult.user'].hasFieldErrors('role') ? 'has-error' : ''}">
							<form:label path="role" class="col-xs-2 control-label">Role</form:label>
							<div class="col-xs-10">
								<form:select path="role" class="form-control">
									<form:option value="" label="Select Role" />			
									<form:options items="${selectRole}" itemValue="id" itemLabel="name" />
								</form:select>
							</div>
							<div class="col-xs-10 col-xs-offset-2">
								<form:errors path="role" class="has-error help-block" />
							</div>
						</div>
						<div class="col-xs-10 col-xs-offset-2">
							<button type="submit" class="btn btn-lg btn-primary">Submit</button>
							<c:if test="${not empty user.id}">
								<a class="btn btn-lg btn-primary confirm-dialog" dialog="Are You Sure You Want To Delete This User?" href="<c:url value='/admin/user/delete/${user.id}' />" role="button">Delete User</a>
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