<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="row">
	<div class="col-xs-12">
		<!-- Generic Articles Can Be Sponsored If They Belong To A Specific Category -->
		<cms:SponsoredArticle sponsored="isSponsored" banner="sponsoredBanner" eceArticleId="${param.eceArticleId}" sectionUniqueName="sponsors" caption="sponsors" />
		<c:if test="${isSponsored eq true}">
			<hr class="sponsored" />
			<h2 class="sponsored">
				${param.supertitle}
				<c:if test="${not empty sponsoredBanner}">
					<span>PRESENTED BY</span>
					<img src="${sponsoredBanner}" class="sponsoredBanner" alt="Sponsor" />
				</c:if>
			</h2>
		</c:if>
		<div class="article generic <c:if test='${isSponsored eq true}'>sponsored</c:if>">
            <c:choose>
                <c:when test="${not empty tag}">
                    <cms:ToUpperCase input="${tag.displayName}" output="tagName"/>
                    <span class="banner">${tagName}</span>
                </c:when>
                <c:otherwise>
                    <cms:ToUpperCase input="${param.category}" output="categoryToUpperCase"/>
                    <span class="banner">${categoryToUpperCase}</span>    
                </c:otherwise>
            </c:choose>
			<a href="${param.url}" class="title">
				<span class="arrow"></span>
				<c:choose>
					<c:when test="${not empty param.image}">
						<c:set var="image" value="${param.image}"/>
					</c:when>
					<c:otherwise>
						<c:set var="image" value="${defaultImage}"/>
					</c:otherwise>
				</c:choose>
				<img class="img-responsive lazyload" data-original="${image}" onError="this.src='${defaultImage}'" />
			</a>
			<cms:ToUpperCase input="${param.title}" output="titleToUpperCase"/>
			<h1><a href="${param.url}">${titleToUpperCase}</a></h1>
			<p>${param.leadText}</p>
			<%-- Editor For Opinions --%>
			<c:if test="${isSponsored ne true && param.section=='opinions'}">
				<c:if test="${not empty param.author}">
					<jsp:include page="../common/editorCategory.jsp">
						<jsp:param name="name" value="${param.author}"/>
					</jsp:include>
				</c:if>
			</c:if>
		</div>
	</div>
</div>