<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Ladylike.gr | Celebrities, Μόδα, Ομορφιά, Σινεμά, Τηλεόραση, Παιδί, Fitness</title>
        <meta name="description" content="Ladylike.gr | Celebrities, Μόδα, Ομορφιά, Σινεμά, Τηλεόραση, Παιδί, Fitness" />
        <link rel="canonical" href="http://ladylike.gr/" />
        <jsp:include page="../common/head.jsp"/>
        <jsp:include page="../analytics/parsely-header.jsp"/><%-- parse.ly Header --%>
        <link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
        <link href="/static/ladylike/css/articles/big.css" rel="stylesheet">
        <link href="/static/ladylike/css/articles/circle.css" rel="stylesheet">
        <link href="/static/ladylike/css/articles/generic.css" rel="stylesheet">
        <link href="/static/ladylike/css/articles/bestof.css" rel="stylesheet">
        <link href="/static/ladylike/css/carousels/opinions.css" rel="stylesheet">
        <link href="/static/ladylike/css/carousels/shopping.css" rel="stylesheet">
        <link href="/static/ladylike/css/common/newsletter.css" rel="stylesheet">
        <link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
        <jsp:include page="../advertisements/DFP_HP_Head.jsp"/>
        <!-- Default Image URL In Application Scope Parameter -->
        <c:set var="defaultImage" value="http://www.ladylike.gr/incoming/article3565583.ece/ALTERNATES/w460/default.png" scope="application"/>
    </head>
    <body>
        <jsp:include page="../common/navbar.jsp"/>
        <div class="container-fluid">
            <%-- Read Sections That Will Be Used In Various Parts Of The Page --%>
            <cms:SectionService id="main1" name="ece_frontpage@main1" />
            <cms:SectionService id="main2" name="ece_frontpage@main2" />
            <cms:SectionService id="main3" name="ece_frontpage@main3" />
            <cms:SectionService id="main4" name="ece_frontpage@main4" />
            <cms:SectionService id="main5" name="ece_frontpage@main5" maxItems="1" />
            <%-- Big Story : ece_frontpage@main5 --%>
            <c:forEach items="${main5}" var="article" begin="0" end="1">
                <cms:ArticleUrl id="url" article="${article}" />
                <cms:GetRelatedImage  var="main" version="w540" article="${article}" />
                <jsp:include page="../articles/big.jsp">
                    <jsp:param name="url" value="${url}" />
                    <jsp:param name="image" value="${main}" />
                    <jsp:param name="category" value="${article.categories[0].name}" />
                    <jsp:param name="title" value="${article.title}" />
                    <jsp:param name="leadText" value="${article.leadText}" />
                </jsp:include>
            </c:forEach>
            <%-- Advertisement --%>
            <c:if test="${fn:length(main5)==1}">
            	<jsp:include page="../advertisements/HP_320x50_B.jsp"/>    
            </c:if>
            <%-- Top Story : ece_frontpage@main1 --%>
            <c:forEach items="${main1}" var="article" begin="0" end="1">
                <cms:ArticleUrl id="url" article="${article}" />
                <cms:GetRelatedImage var="main" version="w540" article="${article}" />
                <jsp:include page="../articles/big.jsp">
                    <jsp:param name="url" value="${url}" />
                    <jsp:param name="image" value="${main}" />
                    <jsp:param name="category" value="${article.categories[0].name}" />
                    <jsp:param name="title" value="${article.title}" />
                    <jsp:param name="leadText" value="${article.leadText}" />
                </jsp:include>
            </c:forEach>
            <%-- Advertisement --%>
            <c:if test="${fn:length(main1)==1 and fn:length(main5)==0}">
            	<jsp:include page="../advertisements/HP_320x50_B.jsp"/>    
           	</c:if>
            <%-- Circle Articles : 1st Articles Of ece_frontpage@main2, ece_frontpage@main3, ece_frontpage@main4 --%>
            <c:forEach begin="2" end="4" varStatus="loop">
                <c:set var="group" value="main${loop.index}" />
                <c:set var="article" value="${requestScope[group][0]}" />
                <c:if test="${not empty article}">
                    <cms:ArticleUrl id="url" article="${article}" />
                    <cms:GetRelatedImage var="main" version="w380" article="${article}" />
                    <jsp:include page="../articles/circle.jsp">
                        <jsp:param name="url" value="${url}" />
                        <jsp:param name="image" value="${main}" />
                        <jsp:param name="category" value="${article.categories[0].name}" />
                        <jsp:param name="title" value="${article.title}" />
                        <jsp:param name="leadText" value="${article.leadText}" />
                    </jsp:include>
                </c:if>
            </c:forEach>
            <%-- Position Titles In Bottom Of Circle Images --%>
            <script>
	            $(document).ready(function() { /*iPhone Chrome : You Have To Wait For The document To Load*/
	            	$(".circle .info .padding").each(function() {
	                	var height = $(this).height();
	                	var line_height = $(this).css('line-height');
	                	line_height = parseFloat(line_height);
						var rows = height / line_height;
						if(Math.round(rows)==1) {
							$(this).parent('div.info').css("margin-top", "-45px");
						}
	                });
	            });
            </script>
            <%-- Opinions Carousel --%>           
            <%--<jsp:include page="../common/hayia.jsp"/>--%>
            <%-- Advertisements --%>
            <jsp:include page="../advertisements/HP_320x50_A.jsp"/>
            <jsp:include page="../advertisements/HP_300x250_A.jsp"/>
            <%-- 2nd Articles Of ece_frontpage@main2, ece_frontpage@main3, ece_frontpage@main4 --%>
            <c:forEach begin="2" end="4" varStatus="loop">
                <c:set var="group" value="main${loop.index}" />
                <c:set var="article" value="${requestScope[group][1]}" />
                <c:if test="${not empty article}">
                    <cms:ArticleUrl id="url" article="${article}" />
                    <cms:GetRelatedImage var="main" version="w460" article="${article}" />
                    <jsp:include page="../articles/generic.jsp">
                        <jsp:param name="url" value="${url}" />
                        <jsp:param name="image" value="${main}" />
                        <jsp:param name="category" value="${article.categories[0].name}" />
                        <jsp:param name="section" value="${article.categories[0].sectionUniqueName}" />
                        <jsp:param name="eceArticleId" value="${article.eceArticleId}" />
                        <jsp:param name="title" value="${article.title}" />
                        <jsp:param name="supertitle" value="${article.supertitle}" />
                        <jsp:param name="leadText" value="${article.leadText}" />
                    </jsp:include>
                </c:if>
            </c:forEach>
                     <jsp:include page="../carousels/opinions.jsp" />
            <%-- 3rd Articles Of ece_frontpage@main2, ece_frontpage@main3, ece_frontpage@main4 --%>
            <c:forEach begin="2" end="4" varStatus="loop">
                <c:set var="group" value="main${loop.index}" />
                <c:set var="article" value="${requestScope[group][2]}" />
                <c:if test="${not empty article}">
                    <cms:ArticleUrl id="url" article="${article}" />
                    <c:choose>
                    	<c:when test="${group=='main2' || group=='main4'}">
                            <cms:GetRelatedImage var="main" version="w80" article="${article}" />
                            <jsp:include page="../articles/mini.jsp">
                                <jsp:param name="url" value="${url}" />
                                <jsp:param name="image" value="${main}" />
                                <jsp:param name="title" value="${article.title}" />
                            </jsp:include>
                        </c:when>
                        <c:otherwise>
                        	<cms:GetRelatedImage var="main" version="w460" article="${article}" />
                            <jsp:include page="../articles/generic.jsp">
                                <jsp:param name="url" value="${url}" />
                                <jsp:param name="image" value="${main}" />
                                <jsp:param name="category" value="${article.categories[0].name}" />
                                <jsp:param name="section" value="${article.categories[0].sectionUniqueName}" />
                                <jsp:param name="eceArticleId" value="${article.eceArticleId}" />
                                <jsp:param name="title" value="${article.title}" />
                                <jsp:param name="supertitle" value="${article.supertitle}" />
                                <jsp:param name="leadText" value="${article.leadText}" />
                            </jsp:include>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>
            <%-- Shopping List Carousel --%>
            <jsp:include page="../carousels/shopping.jsp"/>
            <%-- 4th Articles Of ece_frontpage@main2, ece_frontpage@main3, ece_frontpage@main4 --%>
            <c:forEach begin="2" end="4" varStatus="loop">
                <c:set var="group" value="main${loop.index}" />
                <c:set var="article" value="${requestScope[group][3]}" />
                <c:if test="${not empty article}">
                    <cms:ArticleUrl id="url" article="${article}" />
                    <cms:GetRelatedImage var="main" version="w460" article="${article}" />
                    <jsp:include page="../articles/generic.jsp">
                        <jsp:param name="url" value="${url}" />
                        <jsp:param name="image" value="${main}" />
                        <jsp:param name="category" value="${article.categories[0].name}" />
                        <jsp:param name="section" value="${article.categories[0].sectionUniqueName}" />
                        <jsp:param name="eceArticleId" value="${article.eceArticleId}" />
                        <jsp:param name="title" value="${article.title}" />
                        <jsp:param name="supertitle" value="${article.supertitle}" />
                        <jsp:param name="leadText" value="${article.leadText}" />
                    </jsp:include>
                </c:if>
            </c:forEach>
            <%-- 5th Articles Of ece_frontpage@main2, ece_frontpage@main3, ece_frontpage@main4 --%>
            <c:forEach begin="2" end="4" varStatus="loop">
                <c:set var="group" value="main${loop.index}" />
                <c:set var="article" value="${requestScope[group][4]}" />
                <c:if test="${not empty article}">
                    <cms:ArticleUrl id="url" article="${article}" />
                    <c:choose>
                        <c:when test="${group=='main2'}">
                    	    <cms:GetRelatedImage var="main" version="w80" article="${article}" /> 
                            <jsp:include page="../articles/mini.jsp">
                                <jsp:param name="url" value="${url}" />
                                <jsp:param name="image" value="${main}" />
                                <jsp:param name="title" value="${article.title}" />
                            </jsp:include>
                        </c:when>
                        <c:otherwise>
                        	<cms:GetRelatedImage var="main" version="w460" article="${article}" />
                            <jsp:include page="../articles/generic.jsp">
                                <jsp:param name="url" value="${url}" />
                                <jsp:param name="image" value="${main}" />
                                <jsp:param name="category" value="${article.categories[0].name}" />
                                <jsp:param name="section" value="${article.categories[0].sectionUniqueName}" />
                                <jsp:param name="eceArticleId" value="${article.eceArticleId}" />
                                <jsp:param name="title" value="${article.title}" />
                                <jsp:param name="supertitle" value="${article.supertitle}" />
                                <jsp:param name="leadText" value="${article.leadText}" />
                            </jsp:include>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>
            <%-- Advertisement --%>
            <jsp:include page="../advertisements/HP_300x250_B.jsp"/>
            <%-- Best Of : articles@main2 --%>
            <div class="bestofLogo"></div>
            <cms:SectionService id="best" name="articles@main2" maxItems="5" />
            <c:forEach var="article" items="${best}">
                <cms:ArticleUrl id="url" article="${article}" />
                <cms:GetRelatedImage var="main" version="w220" article="${article}" />
                <jsp:include page="../articles/bestof.jsp">
                    <jsp:param name="url" value="${url}" />
                    <jsp:param name="image" value="${main}" />
                    <jsp:param name="category" value="${article.categories[0].name}" />
                    <jsp:param name="title" value="${article.title}" />
                </jsp:include>
            </c:forEach>
            <%-- Newsletter --%>
            <jsp:include page="../common/newsletter.jsp"/>
            <%-- Footer --%>
            <jsp:include page="../advertisements/HP_320x50_A.jsp"/>
            <jsp:include page="../common/follow.jsp"/>
            <jsp:include page="../common/footer.jsp"/>
            <jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
            <%-- Swipe Event For Carousels --%>
            <script>
                $(".carousel-inner").swipe({
                    swipeRight: function(event, direction, distance, duration, fingerCount) {
                        $(this).parent().carousel('prev');
                    },
                    swipeLeft: function() {
                        $(this).parent().carousel('next');
                    },
                    //Default Is 75px, Set To 0 So Any Distance Triggers Swipe 
                    threshold: 0
                });
            </script>
        </div>
    </body>
</html>