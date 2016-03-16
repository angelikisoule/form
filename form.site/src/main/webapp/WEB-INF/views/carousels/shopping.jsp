<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="row">
	<cms:ArticleService var="articles" category="shopping_list" articleTypes="story" maxItems="5" />
	<c:if test="${fn:length(articles)>0}">
		<div id="carousel-shopping" class="carousel slide" data-ride="carousel" data-interval="false">
	  		<a href="/articles/shopping_list/" class="header"></a>
			<!-- Wrapper for slides -->
		  	<div class="carousel-inner" role="listbox">
				<c:forEach items="${articles}" var="article" varStatus="loop">
					<cms:ArticleUrl id="url" article="${article}" />
					<cms:GetRelatedImage var="main" version="w300" article="${article}" />
	  				<div class="item <c:if test='${loop.index==0}'>active</c:if>">
						<c:choose>
							<c:when test="${not empty main}">
								<c:set var="image" value="${main}"/>
							</c:when>
							<c:otherwise>
								<c:set var="image" value="${defaultImage}"/>
							</c:otherwise>
						</c:choose>
						<img src="${image}" alt="Product Image" onError="this.src='${defaultImage}'" />
						<!-- Do Not Use Bootstrap's Caption Class To Get The Captions Under The Image -->
						<div class="captions text-center">
							<a href="${url}" class="carousel-title">${article.title}</a>
							<span class="carousel-price">&euro; ${article.price}</span>
							<a href="${url}" class="carousel-button">ΔΕΣ ΤΟ</a>
						</div>
					</div>
		  		</c:forEach>
		  	</div>
		  	<!-- Controls -->
			<a class="left carousel-control" href="#carousel-shopping" role="button" data-slide="prev" ontouchstart="">
				<span class="arrowLeft"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="#carousel-shopping" role="button" data-slide="next" ontouchstart="">
				<span class="arrowRight"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
		<div class="col-xs-12 carousel-shopping-more">
			<a href="/articles/shopping_list/">ΠΕΡΙΣΣΟΤΕΡΟ SHOPPING<span></span></a>
		</div>
	</c:if>
</div>