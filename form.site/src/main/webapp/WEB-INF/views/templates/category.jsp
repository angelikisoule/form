<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
    <head>
        <c:choose>
        	<c:when test="${not empty tag}">
        		<title>${tag.displayName} - Άρθρα | Ladylike.gr</title>
        		<meta name="description" content="${tag.displayName} - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />		
        	</c:when>
        	<c:otherwise>
        		<title>${category.name} - Άρθρα | Ladylike.gr</title>
        		<meta name="description" content="${category.name} - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
        	</c:otherwise>
        </c:choose>
        <link rel="canonical" href="http://ladylike.gr${requestScope['javax.servlet.forward.request_uri']}" />
        <jsp:include page="../common/head.jsp"/>
        <jsp:include page="../analytics/parsely-header.jsp"/><%--parse.ly Header--%>
        <link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
        <link href="/static/ladylike/css/articles/generic.css" rel="stylesheet">
        <link href="/static/ladylike/css/common/editor.css" rel="stylesheet">
        <link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
        <%-- Manipulation Of Request Parameters --%>
        <c:set var="page" value='<%= request.getParameter("page") %>' scope="request" />
        <c:if test="${empty page}">
            <c:set var="page" value="0" scope="request" />
        </c:if>
        <c:set var="maxArticles" value="15" scope="request" />
        <c:set var="offset" value="${maxArticles*page}" scope="request" />
        <jsp:include page="../advertisements/DFP_ROS_Head.jsp"/>
    </head>
    <body>
        <jsp:include page="../common/navbar.jsp"/>
        <div class="container-fluid">
            <!-- If tag Is Set Read Tag Articles, Otherwise Category Articles -->
            <c:choose>
                <c:when test="${not empty tag}">
                    <c:choose>
                        <c:when test="${tagSection!='ece_frontpage'}">                    
                            <cms:ArticleService var="articles" category="${category.sectionUniqueName}" tag="${tag.name}" maxItems="${maxArticles}" offset="${offset}" />
                        </c:when>
                        <c:otherwise>                    
                            <cms:ArticleService var="articles" tag="${tag.name}" maxItems="${maxArticles}" offset="${offset}" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>                    
                    <cms:ArticleService var="articles" category="${category.sectionUniqueName}" articleTypes="STORY" maxItems="${maxArticles}" offset="${offset}" />
                </c:otherwise>
            </c:choose>
            <div id="st_container">
                <c:forEach items="${articles}" var="article" varStatus="loop">
                    <!-- Advertisements -->
                    <c:if test="${loop.index==1}">
                        <jsp:include page="../advertisements/ROS_320x50_B.jsp"/>
                    </c:if>
                    <c:if test="${loop.index==5}">
                        <jsp:include page="../advertisements/ROS_300x250_A.jsp"/>
                    </c:if>
                    <c:if test="${loop.index==10}">
                        <jsp:include page="../advertisements/ROS_300x250_B.jsp"/>
                    </c:if>
                    <!-- Articles -->
                    <cms:ArticleUrl id="url" article="${article}" />
                    <cms:GetRelatedImage var="main" version="w460" article="${article}" />
                    <c:forEach items="${article.authors}" var="author" begin="0" end="0">
                        <c:set var="authorName" value="${author.name}" />
                    </c:forEach>
                    <jsp:include page="../articles/generic.jsp">
                        <jsp:param name="url" value="${url}" />
                        <jsp:param name="image" value="${main}" />
                        <jsp:param name="category" value="${category.name}" />
                        <jsp:param name="section" value="${category.sectionUniqueName}" />
                        <jsp:param name="eceArticleId" value="${article.eceArticleId}" />
                        <jsp:param name="title" value="${article.title}" />
                        <jsp:param name="supertitle" value="${article.supertitle}" />
                        <jsp:param name="leadText" value="${article.leadText}" />
                        <jsp:param name="author" value="${authorName}" />
                    </jsp:include>
                </c:forEach>
            </div>
			<!-- Pagination -->
        	<script type="text/javascript" src="/static/ladylike/js/pagination.js"></script>
        	<jsp:include page="../advertisements/ROS_320x50_A.jsp"/>
        	<jsp:include page="../common/follow.jsp"/>
        	<jsp:include page="../common/footer.jsp"/>
        	<jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
        </div>
    </body>
</html>
