<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="admin/head.jsp"/>
		<title>Login</title>
		<style>
			body {
				background-color:#f5f5f5;
			}
		</style>
	</head>
	<body>
		<div class="container-fluid">
			<div class="row">
				<c:url var="postLoginUrl" value="/j_spring_security_check" />
				<form action="${postLoginUrl}" method="post">		
					<div class="col-xs-4 col-xs-offset-4 form-group">
						<h2 class="form-signin-heading">Login</h2>
						<c:if test="${param.failed==true}">
							<small>Login Failed. Please Try Again.</small>
						</c:if>
					</div>				
					<div class="col-xs-4 col-xs-offset-4 form-group">
						<input type="text" name="j_username" placeholder="Username" class="form-control input-lg" /><br />
					</div>
					<div class="col-xs-4 col-xs-offset-4 form-group">
						<input type="password" name="j_password" placeholder="Password" class="form-control input-lg" /><br />
					</div>
					<div class="col-xs-4 col-xs-offset-4">
						<button type="submit" class="btn btn-lg btn-primary btn-block">Login</button>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>