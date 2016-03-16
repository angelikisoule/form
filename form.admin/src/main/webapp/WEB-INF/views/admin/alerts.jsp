<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${not empty successMessage}">
		<div class="col-xs-12">
			<div class="alert alert-success alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				${successMessage}
			</div>
		</div>
	</c:when>
	<c:when test="${not empty warningMessage}">
		<div class="col-xs-12">
			<div class="alert alert-warning alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				${warningMessage}
			</div>
		</div>
	</c:when>
	<c:when test="${not empty errorMessage}">
		<div class="col-xs-12">
			<div class="alert alert-danger alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				${errorMessage}
			</div>
		</div>
	</c:when>
</c:choose>


