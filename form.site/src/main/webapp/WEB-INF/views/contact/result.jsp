<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ladylike.gr | Φόρμα Επικοινωνίας</title>
	    <jsp:include page="../common/head.jsp"/>
	    <link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
	    <link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
	</head>
	<body>
	<jsp:include page="../common/navbar.jsp"/>
	    <div class="container-fluid">
			<div class="row">
				<div class="col-xs-12">
					<h1>Το email σας στάλθηκε με επιτυχία</h1>
				</div>
			</div>
	    	<div class="row"> 	
	    		<div class="col-xs-12">
				    <p>Όνομα : ${userMessage.name}</p>
				    <p>Email : ${userMessage.email}</p>
				    <p>Προς : ${emailTo}</p>
				    <p>Διεύθυνση : ${userMessage.address}</p>
				    <p>Θέμα : ${userMessage.subject}</p>
				    <p>Κείμενο : ${userMessage.text}</p>
			    </div>
	    	</div>
	    	<jsp:include page="../common/follow.jsp"/>
	        <jsp:include page="../common/footer.jsp"/>
	    	<a href="/contact/form">Submit another message</a>
	    </div>
	</body>
</html>