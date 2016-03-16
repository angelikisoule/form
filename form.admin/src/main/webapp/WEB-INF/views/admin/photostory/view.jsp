<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>Photostory</title>
		<style>
			/*Don't Expect Too Much Here, Fixed Widths Will Probably Result Stretched Images*/
			#carousel-container, .carousel-inner>.item>img {
				width:580px;
				height:360px;
			}
		</style>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li><a href="<c:url value='/admin/article' />">Articles</a></li>
  				<li><a href="<c:url value='/admin/photostory?type=photostory' />">Photostories</a></li>
  				<li class="active">Photostory View</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>Photostory View <small>[ Carousel Demonstration ]</small></h3>
					</div>
				</div>
			</div>
			<!-- Photostory -->
			<div class="row">
				<div class="col-xs-12">
					<div id="carousel-container" class="carousel slide" data-ride="carousel">
						<!-- Wrapper For Slides -->
		  				<div class="carousel-inner" role="listbox">
		    				<c:forEach items="${photostory.relatedArticles}" var="related" varStatus="loop">
		    					<div class="item <c:if test='${loop.index==0}'>active</c:if>">
		    						<img src="${related.related.alternate}" alt="Picture" class="carousel-image" />
		    					</div>
		    				</c:forEach>
		  				</div>
		  				<!-- Controls -->
		  				<a class="left carousel-control" href="#carousel-container" role="button" data-slide="prev">
		    				<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		    				<span class="sr-only">Previous</span>
		  				</a>
		  				<a class="right carousel-control" href="#carousel-container" role="button" data-slide="next">
		    				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		    				<span class="sr-only">Next</span>
		  				</a>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../footer.jsp"/>
	</body>
</html>