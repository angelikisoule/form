<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<cms:SectionService id="main7" name="ece_frontpage@main7" />
<div class="row">
    <div class="col-xs-12">
        <div class="imerologio_cont">
        	<div class="imerologio"></div>
        </div>
        <div class="hayia">
	        <a class="hayia_image"> </a>
	        <c:forEach items="${main7}" var="article" varStatus="loop" begin="0" end="0">		
	            <span class="hayia_date text-center">${article.title}</span>
	        </c:forEach>
	        <c:forEach items="${main7}" var="article" varStatus="loop" begin="1" end="1">		
	            <cms:ArticleUrl id="url" article="${article}" />			
	            <div class="hayia_title text-center">
	                <cms:ToUpperCase input="${article.title}" output="titleToUpperCase"/>
	                <h2>
	                    <a href="${url}">
	                        <span>${titleToUpperCase}</span>
	                    </a>
	                </h2>
	            </div>
	        </c:forEach>
		    <div class="car_image_cont">
		    	<a href="http://www.nissan.gr/vehicles/city-cars/juke" target="_blank" class="car_image"></a>
		    </div>
    	</div>
    </div>
</div>