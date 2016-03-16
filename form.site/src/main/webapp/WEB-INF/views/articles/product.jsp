<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="row article product">
	<div class="col-xs-12">
		<!-- At Single Product View, Show Product's Discount As A Banner And Not In The Title -->
		<c:if test="${param.singleProduct eq true}">
			<cms:ProductDiscount id="discount" input="${param.title}" />
		</c:if>
		<c:choose>
			<c:when test="${not empty discount}">
				<c:set var="discountString" value="${fn:replace(discount, '-', '')}" />
				<c:set var="titleString" value="${fn:trim(fn:replace(param.title, discount, ''))}" />
			</c:when>
			<c:otherwise>
				<c:set var="titleString" value="${param.title}" />
			</c:otherwise>
		</c:choose>
		<a class="image" href="${param.url}">
			<!-- Discount Banner -->
			<c:if test="${not empty discountString}">
				<span class="discount">${discountString}</span>
				<span class="corner"></span>
			</c:if>
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
		<c:if test="${param.singleProduct eq true}">
			<!-- Full Width Border -->
			<div class="row border"></div>
		</c:if>
		<div class="info text-center">
			<a class="title" href="${url}">${titleString}</a>
			<c:if test="${param.singleProduct eq true}">
				<div class="body text-center">
					<%-- ArticleBodyTag Is Not Needed For Now --%>
					${article.body}
				</div>
			</c:if>
			<span><span>&euro;</span>${param.price}</span>
			<c:choose>
				<c:when test="${param.singleProduct eq true}">
					<c:set var="buy" value="ΑΓΟΡΑΣΕ ΤΟ" />
				</c:when>
				<c:otherwise>
					<c:set var="buy" value="ΔΕΣ ΤΟ" />
				</c:otherwise>
			</c:choose>
			<a class="external" href="${param.link}" target="_blank">${buy}</a>
		</div>
	</div>
</div>