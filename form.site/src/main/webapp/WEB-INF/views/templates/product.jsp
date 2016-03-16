<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${article.title} | Ladylike.gr</title>
		<meta name="description" content="${article.title} - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
		<link rel="canonical" href="${article.alternate}" />
		<jsp:include page="../common/head.jsp"/>
		<jsp:include page="../analytics/parsely-header.jsp"/><%-- parse.ly Header --%>
		<link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
		<link href="/static/ladylike/css/templates/shopping.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/socials.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
	</head>
	<body class="shoppingProduct">
		<jsp:include page="../common/navbar.jsp"/>
		<div class="container-fluid">
			<%--Tag Calls To Get Previous And Next URLs--%>
			<c:if test="${not empty article}">
				<cms:PreviousOrNextArticle id="previousUrl" article="${article}" next="false" criterion="byEceArticleId" />
				<cms:PreviousOrNextArticle id="nextUrl" article="${article}" next="true" criterion="byEceArticleId" />
			</c:if>
			<div class="row navigation">
				<div class="col-xs-12 text-center">
					<div class="previousProduct pull-left">
						<c:if test="${not empty previousUrl}">
							<a href="${previousUrl}" class="btn btn-default" role="button">
								<span></span>ΠΡΟΗΓΟΥΜΕΝΟ
							</a>
						</c:if>
					</div>
					<div id="selectProductCategory">
					    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
					    	ΟΛΑ<span></span>
					    </button>
					    <ul class="dropdown-menu option" role="menu">
					    	<c:forEach var="selectCategory" items="${selectCategory}" varStatus="loop">
					    		<c:choose>
					    			<c:when test="${selectCategory.key==article.categories[0].sectionUniqueName}">
					    				<c:set var="cl" value="selected" />
					    			</c:when>
					    			<c:otherwise>
					    				<c:set var="cl" value=""/>
					    			</c:otherwise>
					    		</c:choose>
					    		<li id="${loop.index}">
					    			<cms:ToUpperCase input="${selectCategory.value}" output="categoryToUpperCase"/>
					    			<a class="${cl}" href="/articles/shopping_list/${selectCategory.key}">${categoryToUpperCase}</a>
					    		</li>
					    	</c:forEach>
					    </ul>
					</div>
					<script>
						//Category Button Value In Case A Category Is Selected 
						if($('a.selected').length) {
							$('#selectProductCategory button').html($('a.selected').text() + '<span></span>');
						}
					</script>
					<div class="nextProduct pull-right">
						<c:if test="${not empty nextUrl}">
							<a href="${nextUrl}" class="btn btn-default" role="button">
								ΕΠΟΜΕΝΟ<span></span>
							</a>
						</c:if>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
				<cms:ArticleUrl id="url" article="${article}" />
				<cms:GetRelatedImage var="main" version="w620" article="${article}" cropped="false" />
				<jsp:include page="../articles/product.jsp">
					<jsp:param name="url" value="${url}" />
					<jsp:param name="image" value="${main}" />
					<jsp:param name="title" value="${article.title}" />
					<jsp:param name="body" value="${article.body}" />
					<jsp:param name="price" value="${article.price}" />
					<jsp:param name="link" value="${article.link}" />
					<jsp:param name="singleProduct" value="true" />
				</jsp:include>
				</div>
			</div>
			<jsp:include page="../common/socials.jsp"/>
			<jsp:include page="../common/follow.jsp"/>
			<jsp:include page="../common/footer.jsp"/>
			<jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
		</div>
	</body>
</html>