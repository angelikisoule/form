<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<cms:SectionService id="articles" name="articles@main1" maxItems="5" />
<div class="row readmore">
	<c:forEach items="${articles}" var="article" varStatus="loop">
		<c:if test="${loop.index==0}">
			<div class="col-xs-12">
				<span class="banner text-center">ΜΗΝ ΤΑ ΧΑΣΕΤΕ</span>
			</div>
		</c:if>
		<div class="col-xs-12">
			<cms:ArticleUrl id="url" article="${article}" />
			<cms:GetRelatedImage var="main" version="w460" article="${article}" />
			<div class="more text-center">
				<a href="${url}">
					<c:choose>
						<c:when test="${not empty main}">
							<c:set var="image" value="${main}"/>
						</c:when>
						<c:otherwise>
							<c:set var="image" value="${defaultImage}"/>
						</c:otherwise>
					</c:choose>
					<img class="img-responsive lazyload" data-original="${image}" onError="this.src='${defaultImage}'" />
				</a>
				<cms:ToUpperCase input="${article.title}" output="titleToUpperCase"/>
				<h1>
					<a href="${url}">
						<span>${titleToUpperCase}</span>
					</a>
				</h1>
				<span class="shadow"></span>
				<!-- Not All Article Types Have A body -->
				<c:if test="${article.articleType=='STORY'}">
					<cms:ArticleBodyContains id="contains" body="${article.body}" />
					<c:if test="${not empty contains}">
						<c:if test="${contains=='photostory'}">
							<span class="contains containsPhotostory"></span>
						</c:if>
						<c:if test="${contains=='video'}">
							<span class="contains containsVideo"></span>
						</c:if>
					</c:if>
				</c:if>
			</div>
		</div>
	</c:forEach>
</div>