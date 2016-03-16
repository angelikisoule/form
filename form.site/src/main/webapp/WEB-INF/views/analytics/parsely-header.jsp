<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<c:set var="parsely" value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:if test="${not(fn:contains(parsely, '.html') and fn:contains(parsely, '/newspapers/'))}">
	<c:choose>
	    <c:when test="${fn:contains(parsely, '.html')}">
	    	<%-- Article Page --%>
	    	<c:set var="headline" value="${article.title}"/>
	    	<c:set var="url" value="http://ladylike.gr${parsely}"/>
	     	<cms:GetRelatedImage var="main" comment="main" version="w620" article="${article}" />
	     	<c:set var="thumbnailUrl" value="${main}"/>
        	<fmt:formatDate var="dateCreated" value="${article.datePublished.time}" pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" scope="request" timeZone="UTC"/>
        	<c:set var="dateCreated" value="${dateCreated}"/>
	    	<c:set var="articleSection" value="${article.categories[0].sectionUniqueName}"/>
	    	<c:choose>
	    		<c:when test="${fn:length(article.authors) > 0}">
	    			<c:forEach items="${article.authors}" var="author" begin="0" end="0">
		    			<c:set var="creator" value="${author.name}"/>
					</c:forEach>
	    		</c:when>
	    		<c:otherwise>
	    			<c:set var="creator" value="ladylike"/>
	    		</c:otherwise>
	    	</c:choose>
	    	<%-- Article Tags As Keywords For parse.ly --%>
	    	<c:if test="${not empty article.tags}">
		      	<c:forEach var="tag" items="${article.tags}" varStatus="loop">
	            	<c:choose>
	            		<c:when test="${loop.index==0}">
	                		<c:set var="keywords" value='"${tag.displayName}"'/>
	            		</c:when>
	            		<c:otherwise>
	            			<c:set var="keywords" value='${keywords},"${tag.displayName}"'/>
	            		</c:otherwise>
	        		</c:choose>
	        	</c:forEach>
	    	</c:if>
	    </c:when>
	    <c:when test="${parsely ne '/'}">
	    	<%-- Category Page --%>
	    	<c:choose>
	    		<c:when test="${not empty param.headline}">
	    			<c:set var="headline" value="${param.headline}"/>
	    		</c:when>
	    		<c:when test="${not empty tag}">
	    			<c:set var="headline" value="${tag.displayName}"/>
	    		</c:when>
	    		<c:when test="${not empty category}">
	    			<c:set var="headline" value="${category.sectionUniqueName}"/>
	    		</c:when>
	    		<c:otherwise>
					<%-- A Fallback --%>
	    			<c:set var="headline" value="ladylike"/>
	    		</c:otherwise>
	    	</c:choose>
	    	<c:set var="headline" value="${headline}"/>
	    	<c:set var="url" value="http://ladylike.gr${parsely}"/>
	    	<c:set var="thumbnailUrl" value="http://www.ladylike.gr/incoming/article3652564.ece/BINARY/original/ladylike.png"/>
	    	<c:set var="dateCreated" value=""/>
	    	<c:set var="articleSection" value="${headline}"/>
	    	<c:set var="creator" value="ladylike"/>
	    </c:when>
	    <c:otherwise>
	    	<%-- Home Page --%>
	    	<c:set var="headline" value="ladylike"/>
	    	<c:set var="url" value="http://ladylike.gr"/>
	    	<c:set var="thumbnailUrl" value="http://www.ladylike.gr/incoming/article3652564.ece/BINARY/original/ladylike.png"/>
	    	<c:set var="dateCreated" value=""/>
	    	<c:set var="articleSection" value="ladylike"/>
	    	<c:set var="creator" value="ladylike"/>
	    </c:otherwise>
	</c:choose>
	<script type="application/ld+json">
	{
		"@context": "http://schema.org",
		"@type": "NewsArticle",
		"headline": "${headline}",
		"url": "${url}",
		"thumbnailUrl": "${thumbnailUrl}",
		"dateCreated": "${dateCreated}",
		"articleSection": "${articleSection}",
		"creator": "${creator}",
		"keywords": [ ${keywords} ]
	}
	</script>
</c:if>
<c:remove var="parsely"/>
<c:remove var="headline"/>
<c:remove var="url"/>
<c:remove var="thumbnailUrl"/>
<c:remove var="dateCreated"/>
<c:remove var="articleSection"/>
<c:remove var="creator"/>
<c:remove var="keywords"/>