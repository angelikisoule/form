<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../common/head.jsp"/>
		<link href="/static/ladylike/css/exceptions/exceptions.css" rel="stylesheet">
        <link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
		<title>Αυτή η σελίδα δεν υπάρχει πλέον - Σφάλμα 404</title>
	</head>
	<body>
		<jsp:include page="../common/navbar.jsp"/>
		<div class="container-fluid">
			<div class="row error text-center">
				<div class="col-xs-12">
					<h1>ΑΥΤΗ Η ΣΕΛΙΔΑ<br />ΔΕΝ ΥΠΑΡΧΕΙ ΠΛΕΟΝ.</h1>
					<div class="image"></div>
					<h2>404</h2>
					<p>
						Το πρόβλημα ίσως να προκύπτει απο<br />
						λάθος εισαγωγής της διεύθυνσης (URL)<br />
						ή σε κάποιο προσωρινό τεχνικό<br />
						πρόβλημα του Ladylike.
					</p>
					<a href="/">ΕΠΙΣΤΡΟΦΗ</a>
				</div>
			</div>
			<jsp:include page="../common/footer.jsp"/>
		</div>
	</body>
</html>