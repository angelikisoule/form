<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${article.title}</title>
		<meta name="description" content="${article.title} - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
		<link rel="canonical" href="${article.alternate}" />
		<jsp:include page="../common/head.jsp"/>
		<jsp:include page="../analytics/parsely-header.jsp"/><%-- parse.ly Header --%>
		<jsp:include page="/WEB-INF/views/common/social-meta.jsp"/>
		<link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
		<link href="/static/ladylike/css/templates/galleries.css" rel="stylesheet">
		<link href="/static/ladylike/css/carousels/generic.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/socials.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
	</head>
	<body class="galleryBody">
		<jsp:include page="../common/navbar.jsp"/>
		<div class="container-fluid">
			<div class="row gallery">
				<div class="col-xs-12">
					<cms:ToUpperCase input="${article.title}" output="titleToUpperCase"/>
					<h1>${titleToUpperCase}</h1>
					<c:forEach items="${article.authors}" var="author" begin="0" end="0">
						<c:set var="authorName" value="${author.name}" />
					</c:forEach>
					<c:if test="${not empty authorName}">
						<div class="author">Από την ${authorName}</div>
					</c:if>
					<c:if test="${not empty leadText}">
						<div class="leadText">${article.leadText}</div>
					</c:if>
					<div id="carousel-generic" class="carousel slide" data-ride="carousel" data-interval="false">
						<%-- Wrapper for slides --%>
  						<div class="carousel-inner" role="listbox">
  							<!-- The .active class needs to be added to one of the slides. Otherwise, the carousel will not be visible -->
  							<c:forEach var="picture" items="${relatedPictures}" varStatus="loop">
	    						<div class="item <c:if test='${loop.index==0}'>active</c:if>">
                                    <cms:GetImageVersion id="portraitImage" input="${picture.alternate}" width="w540" cropped="true" />
                                    <img class="img-responsive" src="${portraitImage}" alt="Carousel Picture"/>
                                    <div class="captions">
	      								<p>${picture.caption}</p>
	      							</div>
	    						</div>
  							</c:forEach>
    					</div>
						<%-- Controls --%>
  						<a class="left carousel-control" href="#carousel-generic" role="button" data-slide="prev" ontouchstart="">
    						<span class="arrowLeft"></span>
							<span class="sr-only">Previous</span>
  						</a>
  						<a class="right carousel-control" href="#carousel-generic" role="button" data-slide="next" ontouchstart="">
    						<span class="arrowRight"></span>
    						<span class="sr-only">Next</span>
  						</a>
					</div>
				</div>
			</div>
			<jsp:include page="../common/socials.jsp"/>
			<jsp:include page="../common/follow.jsp"/>
			<jsp:include page="../common/footer.jsp"/>
			<jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
            <script>
                /*Touch Swipe For Carousel*/
				$(".carousel-inner").swipe({
					swipeRight:function(event, direction, distance, duration, fingerCount) {
						$(this).parent().carousel('prev');
					},
					swipeLeft:function() {
						$(this).parent().carousel('next');
					},
					/*Default Is 75px, Set To 0 So Any Distance Triggers Swipe*/
					threshold:0
				});
			</script>                  
		</div>
	</body>
</html>