<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="row">
	<%-- Performance-wise (And Not Only) It's Good To Limit The Query To A Specific Category --%>
	<cms:ArticleService var="articles" category="photostories" articleTypes="photostory" maxItems="5" />
	<c:if test="${fn:length(articles)>0}">
		<div class="col-xs-12">
            <div class="galleriesBanner"><a href="/articles/galleries/galleries/"></a></div>
			<div id="carousel-galleries" class="carousel slide" data-ride="carousel" data-interval="false">
				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">
					<!-- The .active class needs to be added to one of the slides. Otherwise, the carousel will not be visible -->
					<c:forEach var="article" items="${articles}" varStatus="loop">
   						<cms:GetRelatedImage cropped="true" var="main" version="w540" article="${article}" />
                        <cms:ArticleUrl id="url" article="${article}" />
   						<div class="item 
                        <c:if test='${loop.index==0}'>active</c:if>">
                            <div class="car-img"><a href="${url}"><img src="${main}" class="img-responsive"></a></div>
     						<div class="carousel-caption">
     							<p>${article.title}</p>
     						</div>
                            <a href="${url}" class="shadow"></a>
   						</div>
                    	<c:remove var="url"/>
					</c:forEach>
  				</div>                
				<!-- Controls -->
				<a class="left carousel-control" href="#carousel-galleries" role="button" data-slide="prev" ontouchstart="">
  					<span class="arrowLeft"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="right carousel-control" href="#carousel-galleries" role="button" data-slide="next" ontouchstart="">
  					<span class="arrowRight"></span>
  					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
	</c:if>
</div>