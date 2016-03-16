<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Photos - Άρθρα | Ladylike.gr</title>
		<meta name="description" content="Photos - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
		<link rel="canonical" href="http://ladylike.gr${requestScope['javax.servlet.forward.request_uri']}" />
		<jsp:include page="../common/head.jsp"/>
		<%-- parse.ly Header --%>
		<jsp:include page="../analytics/parsely-header.jsp">
			<jsp:param name="headline" value="Galleries" />
		</jsp:include>
		<link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
		<link href="/static/ladylike/css/templates/galleries.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
		
		<%-- Manipulation Of Request Parameters --%>
		<c:set var="page" value='<%= request.getParameter("page") %>' scope="request" />
		<c:if test="${empty page}">
		    <c:set var="page" value="0" scope="request" />
		</c:if>
		<c:set var="maxArticles" value="15" scope="request" />
		<c:set var="offset" value="${maxArticles*page}" scope="request" />
	</head>
	<body>
		<jsp:include page="../common/navbar.jsp"/>
		<div class="container-fluid">
			<cms:ArticleService var="articles" category="photostories" articleTypes="photostory" doCountOnly="true" />
			<cms:ArticleService var="articles" category="photostories" articleTypes="photostory" maxItems="${maxArticles}" offset="${offset}" />
			<div class="galleriesBanner"></div>
			<c:forEach items="${articles}" var="article" varStatus="loop">
				<cms:ArticleUrl id="url" article="${article}" />
				<cms:GetRelatedImage var="main" version="w540" article="${article}" />
				<jsp:include page="../articles/gallery.jsp">
					<jsp:param name="url" value="${url}" />
					<jsp:param name="image" value="${main}" />
					<jsp:param name="title" value="${article.title}" />
				</jsp:include>
			</c:forEach>
			<!-- Pagination -->
			<div class="row">
				<div class="col-xs-12">
					<c:if test="${page+1<=(totalArticles/maxArticles-(totalArticles/maxArticles%1))}">
						<a class="btn btn-default btn-lg more" role="button" href="/category/galleries?page=${page+1}">Περισσότερα</a>
					</c:if>
				</div>
			</div>
			<jsp:include page="../common/follow.jsp"/>
			<jsp:include page="../common/footer.jsp"/>
			<jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
		</div>
	</body>
</html>