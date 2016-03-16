<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Φόρμα Επικοινωνίας | Ladylike.gr</title>
	    <jsp:include page="../common/head.jsp"/>
	    <link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
	    <link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
	    <style>
	   		.warning.alert{
	    		color:#ff5050;
	    	}
	    	form {
	    		padding-bottom: 30px;
	    	}
	        .form-control {
	            position:relative;
	            z-index: 10000;
	        }
	    </style>
	</head>
	<body>
		<jsp:include page="../common/navbar.jsp"/>
	    <div class="container-fluid">
	    	<div class="row">
				<div class="col-xs-12">
					<h1>Form</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<form:form action="${contactUrl}" modelAttribute="userMessage" >
						<form:errors path="*">
							<div class="warning alert">
								<spring:message code="error.global" />
							</div>
						</form:errors>
						<div class="form-group">
							<form:label path="email" class="control-label">ΑΠΟ (Συμπλήρωσε το e-mail σου) :</form:label>
							<form:input path="email" class="form-control"/>
							<div class="warning alert">${emailValidMessage} <form:errors path="email"><spring:message code="userMessage.label.email" /></form:errors></div>
						</div>
						<div class="form-group">
							<form:label path="departmentsEmail" class="control-label">ΠΡΟΣ :</form:label>
							<select name="departmentsEmail" class="form-control">
								<option value="1">Συντακτική Ομάδα</option>
								<option value="2">Τμήμα Marketing Ladylike</option>
								<option value="3">Τεχνικό Τμήμα</option>
								<option value="4">Εσύ το είπες</option>
								<option value="5">Άλλο</option>
							</select>
							<div class="warning alert"></div><%--Dummy--%>
						</div>
						<div class="form-group">
						<form:label path="name" class="control-label">Ονοματεπώνυμο :</form:label>
							<form:input path="name" class="form-control"/>
							<div class="warning alert"><form:errors path="name"><spring:message code="userMessage.label.name" /></form:errors></div>
						</div>
						<div class="form-group">
						<form:label path="address" class="control-label">Διεύθυνση :</form:label>
							<div><form:input path="address" class="form-control"/></div>
							<div class="warning alert"><form:errors path="address"><spring:message code="userMessage.label.address" /></form:errors></div>
						</div>
						<div class="form-group">
						<form:label path="telephone" class="control-label">Τηλέφωνο :</form:label>
							<div><form:input path="telephone" class="form-control"/></div>
							<div class="warning alert"><form:errors path="telephone"><spring:message code="userMessage.label.telephone" /></form:errors></div>
						</div>
						<div class="form-group">
							<form:label path="subject" class="control-label">Θέμα :</form:label>
							<div><form:input path="subject" class="form-control"/></div>
							<div class="warning alert"><form:errors path="subject"><spring:message code="userMessage.label.subject" /></form:errors></div>
						</div>
						<div class="form-group">
							<form:label path="text" class="control-label">Κείμενο :</form:label>
							<textarea rows="3" cols="20" name="text" class="form-control"></textarea>
							<div class="warning alert"><form:errors path="text"><spring:message code="userMessage.label.text" /></form:errors></div>
						</div>
						<div class="">
							<input type="submit" value="Submit" class="btn btn-default"></input>
						</div>
					</form:form>
				</div>
			</div>
			<jsp:include page="../common/follow.jsp"/>
			<jsp:include page="../common/footer.jsp"/>
		</div>
	</body>
</html>