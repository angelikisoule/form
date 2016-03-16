<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="row">
	<div class="col-xs-12">
		<div class="article big">
            <div class="picture text-center">
				<a href="${param.url}" class="portraitContainer">
					<c:choose>
						<c:when test="${not empty param.image}">
							<c:set var="image" value="${param.image}"/>
						</c:when>
						<c:otherwise>
							<c:set var="image" value="${defaultImage}"/>
						</c:otherwise>
					</c:choose>
					<img src="${image}" class="img-responsive" alt="Article Image" onError="this.src='${defaultImage}'" />
				</a>
				<cms:ToUpperCase input="${param.title}" output="titleToUpperCase"/>
				<h1><a href="${param.url}">${titleToUpperCase}</a></h1>
				<a href="${param.url}" class="shadow"></a><!-- The Shadow Will Also Hold The URL -->
			</div>
			<div class="info text-center">
				<cms:ToUpperCase input="${param.category}" output="categoryToUpperCase"/>
				<span class="banner">${categoryToUpperCase}</span>
				<p>${param.leadText}</p>
			</div>
		</div>
	</div>
</div>