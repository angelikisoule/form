<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="row">
	<cms:SectionService id="articles" name="ece_frontpage@topStories1" />
	<c:if test="${fn:length(articles)>0}">
		<div id="carousel-opinions" class="carousel slide" data-ride="carousel" data-interval="false">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<!-- Articles Must Have An Author -->
				<c:set var="indicatorCounter" value="0" />
				<c:forEach items="${articles}" var="article" varStatus="loop">
					<c:forEach items="${article.authors}" var="author" begin="0" end="0">
						<c:set var="authorNameOuter" value="${author.name}" />
					</c:forEach>
					<c:if test="${not empty authorNameOuter}">
						<li data-target="#carousel-opinions" data-slide-to="${indicatorCounter}" <c:if test="${indicatorCounter==0}"> class='active'</c:if>></li>
						<c:set var="indicatorCounter" value="${indicatorCounter+1}" />
					</c:if>
				</c:forEach>
			</ol>
			<!-- Wrapper for slides -->
		  	<div class="carousel-inner" role="listbox">
		  		<a href="/articles/opinions/" class="header"></a>
				<!-- Articles Must Have An Author -->
				<c:set var="itemCounter" value="0" />
				<c:forEach items="${articles}" var="article" varStatus="loop">
					<cms:ArticleUrl id="url" article="${article}" />
					<c:forEach items="${article.authors}" var="author" begin="0" end="0">
						<c:set var="authorNameInner" value="${author.name}" />
					</c:forEach>
					<c:if test="${not empty authorNameInner}">
		  				<div class="item <c:if test='${itemCounter==0}'>active</c:if>">
							<div class="carousel-caption">
								<c:choose>
									<c:when test="${authorNameInner=='Σάββια Σιάτη'}">
										<c:set var="cl" value="siati" />
										<c:set var="hr" value="/articles/opinions/siati/" />
									</c:when>
									<c:when test="${authorNameInner=='Νίκη Χάγια'}">
										<c:set var="cl" value="xagia" />
										<c:set var="hr" value="/articles/opinions/hagia/" />
									</c:when>
									<c:when test="${authorNameInner=='Ναταλί Σαϊτάκη'}">
										<c:set var="cl" value="saitaki" />
										<c:set var="hr" value="/articles/opinions/natali-saitaki/" />
									</c:when>
									<c:when test="${authorNameInner=='Δέσποινα Δημά'}">
										<c:set var="cl" value="despoina" />
										<c:set var="hr" value="/articles/opinions/dima/" />
									</c:when>
									<c:when test="${authorNameInner=='Ιωάννα Μαμάη'}">
										<c:set var="cl" value="mamai" />
										<c:set var="hr" value="/articles/opinions/mamai/" />
									</c:when>
									<c:when test="${authorNameInner=='Μαριέτα Χριστοπούλου'}">
										<c:set var="cl" value="marietta" />
										<c:set var="hr" value="/articles/opinions/marieta-xristopoulou/" />
									</c:when>
									<c:when test="${authorNameInner=='Μάριον Παλιούρα'}">
										<c:set var="cl" value="marion" />
										<c:set var="hr" value="/articles/opinions/marion-palioura/" />
									</c:when>
									<c:otherwise>
										<c:set var="cl" value="" />
										<c:set var="hr" value="" />
									</c:otherwise>
								</c:choose>
								<a href="${hr}" class="carousel-author-picture ${cl}"></a>
								<div class="carousel-author-name">
									<div class="border">
										<cms:ToUpperCase input="${authorNameInner}" output="authorToUpperCase"/>
										<a href="${hr}">
											<span>${authorToUpperCase}</span>
										</a>
									</div>
								</div>
								<cms:ToUpperCase input="${article.title}" output="titleToUpperCase"/>
								<a href="${url}" class="carousel-title">	
									<span>${titleToUpperCase}</span>
								</a>
							</div>
						</div>
						<c:set var="itemCounter" value="${itemCounter+1}" />
					</c:if>
		  		</c:forEach>
		  	</div>
		  	<!-- Controls -->
			<a class="left carousel-control" href="#carousel-opinions" role="button" data-slide="prev" ontouchstart="">
				<span class="arrowLeft"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="#carousel-opinions" role="button" data-slide="next" ontouchstart="">
				<span class="arrowRight"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</c:if>
</div>