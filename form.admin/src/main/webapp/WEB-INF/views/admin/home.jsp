<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="head.jsp"/>
		<title>Home Page</title>
	</head>
	<body>
		<jsp:include page="menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li>Home</li>
			</ol>
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>Home Page</h3>
					</div>
					<ul class="list-group">
					  	<li class="list-group-item">
					    	<span class="badge">${countArticles}</span>
					    	Articles
						</li>
						<li class="list-group-item">
					    	<span class="badge">${countAuthors}</span>
					    	Authors
						</li>
						<li class="list-group-item">
					    	<span class="badge">${countPublications}</span>
					    	Publications
						</li>
						<li class="list-group-item">
					    	<span class="badge">${countCategories}</span>
					    	Categories
						</li>
						<li class="list-group-item">
					    	<span class="badge">${countSections}</span>
					    	Sections
						</li>
						<li class="list-group-item">
					    	<span class="badge">${countTags}</span>
					    	Tags
						</li>
					</ul>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp"/>
	</body>
</html>