<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>Video View</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li><a href="<c:url value='/admin/article' />">Articles</a></li>
  				<li><a href="<c:url value='/admin/video?type=video' />">Videos</a></li>
  				<li class="active">Video View</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>Video View <small>[ Responsive iFrames Demonstration ]</small></h3>
					</div>
				</div>
			</div>
			<!-- Video -->
			<div class="row">
				<div class="col-xs-12">
					<!-- Don't Expect Too Much Here, Suppose That We Have A 16:9 iFrame -->
					<div class="embed-responsive embed-responsive-16by9">
						<c:choose>
							<c:when test="${video.videoType=='DAILYMOTION'}">
								<iframe class="embed-responsive-item" src="http://www.dailymotion.com/embed/video/${video.videoId}?rel=0"></iframe>			
							</c:when>
							<c:when test="${video.videoType=='YOUTUBE'}">
								<iframe class="embed-responsive-item" src="http://www.youtube.com/embed/${video.videoId}?rel=0"></iframe>
							</c:when>
							<c:otherwise>
								${video.embeddedCode}
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div><!-- container-fluid -->
		<jsp:include page="../footer.jsp"/>
	</body>
</html>