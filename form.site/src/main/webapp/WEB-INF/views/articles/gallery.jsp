<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="row">
	<div class="col-xs-12">
		<div class="article gallery text-center">
			<a href="${param.url}" class="galleryContainer">
				<c:choose>
					<c:when test="${not empty param.image}">
						<c:set var="image" value="${param.image}"/>
					</c:when>
					<c:otherwise>
						<c:set var="image" value="${defaultImage}"/>
					</c:otherwise>
				</c:choose>
				<img src="${image}" class="img-responsive" alt="Gallery Image" onError="this.src='${defaultImage}'" />
			</a>
			<a href="${param.url}" class="shadow"><span>${param.title}</span></a>
		</div>
	</div>
</div>