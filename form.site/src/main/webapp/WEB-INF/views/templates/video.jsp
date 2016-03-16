<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${article.title} | Ladylike.gr</title>
		<meta name="description" content="${article.title} - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
		<link rel="canonical" href="${article.alternate}" />
		<jsp:include page="../common/head.jsp"/>
		<jsp:include page="../analytics/parsely-header.jsp"/><%-- parse.ly Header --%>
		<link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
		<link href="/static/ladylike/css/articles/video.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
		<script type="text/javascript" src="/static/ladylike/js/reEmbed.js"></script>
	</head>
	<body>
		<%-- Single Video View Page. It's Good To Have In Case Someone Request A Video URL, And We May Be Use It As A Fallback In The Future --%>
		<jsp:include page="../common/navbar.jsp"/>
		<div class="container-fluid">
			<div class="row video">
				<div class="col-xs-12">
					<c:choose>
						<c:when test="${fn:contains(videoUrl, 'www.dailymotion.com')}">
							<div class="embed-responsive embed-responsive-16by9">
								<iframe class="embed-responsive-item" src="${videoUrl}"></iframe>
							</div>
						</c:when>
						<c:when test="${fn:contains(videoUrl, 'www.youtube.com')}">
							<div class="embed-responsive embed-responsive-16by9">
								<iframe class="embed-responsive-item" 
										src="${videoUrl}" 
										frameborder="0" 
										allowfullscreen>
								</iframe>
							</div>
						</c:when>
						<c:otherwise>
							<%-- Godspeed --%>
							${embeddedCode}
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<jsp:include page="../common/follow.jsp"/>
			<jsp:include page="../common/footer.jsp"/>
			<jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
		</div>
	</body>
</html>