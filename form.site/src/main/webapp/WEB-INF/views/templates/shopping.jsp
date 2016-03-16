<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Shopping List | Ladylike.gr</title>
		<meta name="description" content="Shopping List - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
		<link rel="canonical" href="http://ladylike.gr${requestScope['javax.servlet.forward.request_uri']}" />
		<jsp:include page="../common/head.jsp"/>
		<%-- parse.ly Header --%>
		<jsp:include page="../analytics/parsely-header.jsp">
			<jsp:param name="headline" value="Shopping" />
		</jsp:include>
		<link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
		<link href="/static/ladylike/css/templates/shopping.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
		<%-- Manipulation Of Request Parameters --%>
		<c:set var="page" value='<%= request.getParameter("page") %>' scope="request" />
		<c:if test="${empty page}">
		    <c:set var="page" value="0" scope="request" />
		</c:if>
		<c:set var="maxArticles" value="20" scope="request" />
		<c:set var="offset" value="${maxArticles*page}" scope="request" />
	</head>
	<body class="shoppingList">
		<jsp:include page="../common/navbar.jsp"/>
		<div class="container-fluid">
			<div class="row navigation">
				<div class="col-xs-12">
					<div class="chooseView">
						<c:choose>
							<c:when test="${not empty param['view'] && param['view']=='grid'}">
								<c:set var="gv" value="gv" />
							</c:when>
							<c:otherwise>
								<c:set var="gv" value="" />
							</c:otherwise>
						</c:choose>
						<a href="/articles/shopping_list/<c:if test='${not empty category}'>${category.sectionUniqueName}/</c:if><c:if test="${page>0}">?page=${page}</c:if>" class="viewList ${gv}"></a>
						<a href="/articles/shopping_list/<c:if test='${not empty category}'>${category.sectionUniqueName}/</c:if>?view=grid<c:if test="${page>0}">&page=${page}</c:if>" class="viewGrid ${gv}"><span class="s1"></span><span class="s2"></span><span class="s3"></span><span class="s4"></span></a>
					</div>
					<div id="selectProductCategory" class="pull-right">
					    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
					    	ΟΛΑ<span></span>
					    </button>
					    <ul class="dropdown-menu option" role="menu">
					    	<c:forEach var="selectCategory" items="${selectCategory}" varStatus="loop">
					    		<c:choose>
					    			<c:when test="${not empty category && selectCategory.key==category.sectionUniqueName}">
					    				<c:set var="cl" value="selected" />
					    			</c:when>
					    			<c:otherwise>
					    				<c:set var="cl" value=""/>
					    			</c:otherwise>
					    		</c:choose>
					    		<li id="${loop.index}">
					    			<cms:ToUpperCase input="${selectCategory.value}" output="categoryToUpperCase"/>
					    			<a class="${cl}" href="/articles/shopping_list/${selectCategory.key}/">${categoryToUpperCase}</a>
					    		</li>
					    	</c:forEach>
					    </ul>
					</div>
				</div>
				<div class="border"></div>
			</div>
			<script>
				//Category Button Value In Case A Category Is Selected 
				if($('a.selected').length) {
					$('#selectProductCategory button').html($('a.selected').text() + '<span></span>');
				}
			</script>
			<div class="row">
				<%--
					Do Not Order Products By datePublished. The Editors Use The Mass Publishing Functionality
					Of Escenic's Studio So You Can Not Properly Navigate To Next Or Previous Articles
				--%>
				<c:choose>
					<c:when test="${not empty category}">
						<cms:ArticleService var="totalArticles" category="${category.sectionUniqueName}" articleTypes="story" doCountOnly="true" />
						<cms:ArticleService var="articles" category="${category.sectionUniqueName}" articleTypes="story" maxItems="${maxArticles}" offset="${offset}" orderBy="eceArticleId" />	
					</c:when>
					<c:otherwise>
						<cms:ArticleService var="totalArticles" category="shopping_list" articleTypes="story" doCountOnly="true" />
						<cms:ArticleService var="articles" category="shopping_list" articleTypes="story" maxItems="${maxArticles}" offset="${offset}" orderBy="eceArticleId" />
					</c:otherwise>
				</c:choose>
				<c:forEach items="${articles}" var="article" varStatus="loop">
					<%-- List Or Grid View --%>
					<c:choose>
						<c:when test="${not empty param['view'] && param['view']=='grid'}">
							<c:choose>
								<c:when test="${loop.index%2==0}">
									<c:set var="numbered" value="even"/>
								</c:when>
								<c:otherwise>
									<c:set var="numbered" value="odd"/>
								</c:otherwise>
							</c:choose>
							<c:set var="cl" value="col-xs-6 grid ${numbered}" />
						</c:when>
						<c:otherwise>
							<c:set var="cl" value="col-xs-12" />
						</c:otherwise>
					</c:choose>
					<div class="${cl} productContainer">
						<cms:ArticleUrl id="url" article="${article}" />
						<cms:GetRelatedImage var="main" version="w620" article="${article}" cropped="false" />
						<jsp:include page="../articles/product.jsp">
							<jsp:param name="url" value="${url}" />
							<jsp:param name="image" value="${main}" />
							<jsp:param name="title" value="${article.title}" />
							<jsp:param name="price" value="${article.price}" />
							<jsp:param name="link" value="${article.link}" />
						</jsp:include>
					</div>
				</c:forEach>
			</div>
			<%-- Pagination --%>
			<div class="row">
				<div class="col-xs-12 text-center">
					<c:if test="${page+1<=(totalArticles/maxArticles-(totalArticles/maxArticles%1))}">
						<c:set var="nextPage" value="/category/shopping_list/" />
						<c:if test="${not empty category}">
							<c:set var="nextPage" value="${nextPage}${category.sectionUniqueName}/" />
						</c:if>
						<c:choose>
							<c:when test="${not empty param['view'] && param['view']=='grid'}">
								<c:set var="nextPage" value="${nextPage}?view=grid&page=${page+1}" />
							</c:when>
							<c:otherwise>
								<c:set var="nextPage" value="${nextPage}?page=${page+1}" />
							</c:otherwise>
						</c:choose>
						<a class="btn btn-default btn-lg more" role="button" href="${nextPage}" ontouchstart="">ΠΕΡΙΣΣΟΤΕΡΑ</a>
					</c:if>
				</div>
			</div>
			<jsp:include page="../common/follow.jsp"/>
			<jsp:include page="../common/footer.jsp"/>
			<jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
			<%-- Category Selection --%>
			<script type="text/javascript">
				$('#selectProductCategory').on('show.bs.dropdown', function () {
					$(".row.navigation").addClass("category-open");
				});
				$('#selectProductCategory').on('hide.bs.dropdown', function () {
					$(".row.navigation").removeClass("category-open");
				});
			</script>
		</div>
	</body>
</html>
