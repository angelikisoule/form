<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<%-- Default Image For Circle Articles --%>
<c:set var="defaultCircle" value="http://www.ladylike.gr/incoming/article3565583.ece/ALTERNATES/w380/default.png" />
<div class="row text-center">
	<div class="article circle">
		<div class="col-xs-12">
			<a href="${param.url}" class="circleContainer">
				<c:choose>
					<c:when test="${not empty param.image}">
						<c:set var="image" value="${param.image}"/>
					</c:when>
					<c:otherwise>
						<c:set var="image" value="${defaultCircle}"/>
					</c:otherwise>
				</c:choose>
				<img src="${image}" class="img-responsive" alt="Article Image" onError="this.src='${defaultImage}'" />
			</a>
		</div>
		<div class="col-xs-12 info">
			<cms:ToUpperCase input="${param.category}" output="categoryToUpperCase"/>
			<span class="banner">${categoryToUpperCase}</span>
			<cms:ToUpperCase input="${param.title}" output="titleToUpperCase"/>
			<p class="title padding">
				<a href="${param.url}">${titleToUpperCase}</a>
			</p>
			<p class="leadText">${param.leadText}</p>
		</div>
	</div>
</div>