<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="row">
	<div class="col-xs-12">
		<div class="article mini">
			<div class="row">
				<div class="col-xs-12">
					<a href="${param.url}" class="picture">
						<c:choose>
							<c:when test="${not empty param.image}">
								<c:set var="image" value="${param.image}"/>
							</c:when>
							<c:otherwise>
								<c:set var="image" value="${defaultImage}"/>
							</c:otherwise>
						</c:choose>
						<img src="${image}" alt="Article Image" onError="this.src='${defaultImage}'" />
					</a>
					<cms:ToUpperCase input="${param.title}" output="titleToUpperCase"/>
					<a href="${param.url}" class="title">
						${titleToUpperCase}
					</a>
				</div>
			</div>
		</div>
	</div>
</div>