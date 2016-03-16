<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Videos | Ladylike.gr</title>
		<meta name="description" content="Videos - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
		<link rel="canonical" href="http://ladylike.gr${requestScope['javax.servlet.forward.request_uri']}" />
		<jsp:include page="../common/head.jsp"/>
		<%-- parse.ly Header --%>
		<jsp:include page="../analytics/parsely-header.jsp">
			<jsp:param name="headline" value="Videos" />
		</jsp:include>
		<link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
		<link href="/static/ladylike/css/articles/video.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
		<script type="text/javascript" src="/static/ladylike/js/reEmbed.js"></script>
		<%-- Manipulation Of Request Parameters --%>
		<c:set var="page" value='<%= request.getParameter("page") %>' scope="request" />
		<c:if test="${empty page}">
		    <c:set var="page" value="0" scope="request" />
		</c:if>
		<c:set var="maxArticles" value="5" scope="request" />
		<c:set var="offset" value="${maxArticles*page}" scope="request" />
	</head>
	<body>
		<jsp:include page="../common/navbar.jsp"/>
		<div class="container-fluid">
			<cms:ArticleService var="totalArticles" category="videos" articleTypes="VIDEO" doCountOnly="true" />
			<cms:ArticleService var="articles" category="videos" articleTypes="VIDEO" maxItems="${maxArticles}" offset="${offset}" />
			<c:forEach items="${articles}" var="article">
				<cms:GetRelatedImage var="main" version="w460" article="${article}" />			
				<cms:VideoUrl id="videoUrl" article="${article}" autoplay="false" />
				<jsp:include page="../articles/video.jsp">
					<jsp:param name="image" value="${main}"/>
					<jsp:param name="title" value="${article.title}"/>
					<jsp:param name="eceArticleId" value="${article.eceArticleId}"/>
					<jsp:param name="videoUrl" value="${videoUrl}"/>
				</jsp:include>
			</c:forEach>
			<!-- Pagination -->
			<div class="row">
				<div class="col-xs-12">
					<c:if test="${page+1<=(totalArticles/maxArticles-(totalArticles/maxArticles%1))}">
						<div class="more">
							<a class='btn' href='/videos?page=${page+1}' ontouchstart="">ΠΕΡΙΣΣΟΤΕΡΑ</a>
						</div>
					</c:if>
				</div>
			</div>
			<jsp:include page="../common/follow.jsp"/>
			<jsp:include page="../common/footer.jsp"/>
			<jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
		</div>
	</body>
</html>