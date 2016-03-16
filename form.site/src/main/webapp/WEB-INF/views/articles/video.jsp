<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="row">
	<div class="col-xs-12">
		<div class="article videos">
			<c:choose>
				<%-- YouTube And Dailymotion iFrames --%>
				<c:when test="${fn:contains(param.videoUrl, 'www.dailymotion.com')}">
					<div class="embed-responsive embed-responsive-16by9">
						<iframe class="embed-responsive-item" src="${param.videoUrl}"></iframe>
					</div>
				</c:when>
				<c:when test="${fn:contains(param.videoUrl, 'www.youtube.com')}">
					<div class="embed-responsive embed-responsive-16by9">
						<iframe class="embed-responsive-item" 
								src="${param.videoUrl}" 
								frameborder="0" 
								allowfullscreen>
						</iframe>
					</div>
				</c:when>
				<c:otherwise>
					<%-- You Have To Build A Custom Alternate For ADVANCEDCODE Videos --%>
					<c:set var="url" value="videos/advancedcode.${param.eceArticleId}.html" />
					<a href="${url}" class="title">
						<span class="banner">BINTEO</span>
						<c:choose>
							<c:when test="${not empty param.image}">
								<c:set var="image" value="${param.image}"/>
							</c:when>
							<c:when test="${not empty param.videoThumbnail}">
								<c:set var="image" value="${param.videoThumbnail}"/>
							</c:when>
							<c:otherwise>
								<c:set var="image" value="${defaultImage}"/>
							</c:otherwise>
						</c:choose>
						<img class="img-responsive lazyload" data-original="${image}" onError="this.src='${defaultImage}'" />
					</a>
				</c:otherwise>
			</c:choose>
			<cms:ToUpperCase input="${param.title}" output="titleToUpperCase"/>
			<h1>
				<span class="arrow"></span>
				${titleToUpperCase}
			</h1>
		</div>
	</div>
</div>