<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Socials -->
<div class="row social text-center">
	<div class="col-xs-12 logo">
		<a href="/"></a>
		<hr class="border" />
	</div>
	<div class="col-xs-12 follow">
		<span>FOLLOW US</span>
	</div>
	<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
		<div class="row">
			<div class="col-xs-2 col-xs-offset-1 followFacebook"><a href="https://www.facebook.com/LadyLikegr" target="_blank"></a></div>
			<div class="col-xs-2 followTwitter"><a href="https://twitter.com/ladylikegr" target="_blank"></a></div>
			<div class="col-xs-2 followPinterest"><a href="https://www.pinterest.com/ladylikegr/" target="_blank"></a></div>
			<div class="col-xs-2 followInstagram"><a href="https://instagram.com/ladylike.gr/" target="_blank"></a></div>
			<div class="col-xs-2 followYouTube"><a href="https://www.youtube.com/user/CosmoGreece" target="_blank"></a></div>
		</div>
	</div>
</div>
<!-- Terms And Conditions -->
<div class="row terms text-center">
	<div class="goToTop"><a href="#goToTop"></a></div>
	<div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 contact">
		<div class="row">
			<div class="col-xs-3 col-sm-4">
				<a href="/contact/form">ΕΠΙΚΟΙΝΩΝΙΑ</a>
			</div>
			<div class="col-xs-6 col-sm-4">
				<a href="/incoming/article2894156.ece">ΠΟΛΙΤΙΚΗ ΑΠΟΡΡΗΤΟΥ</a>
			</div>
			<div class="col-xs-3 col-sm-4">
				<a href="/incoming/article2894036.ece">ΟΡΟΙ ΧΡΗΣΗΣ</a>
			</div>
		</div>
	</div>
	<div class="col-xs-12 copyrights">
		<hr /><%--Border--%>
		<a class="company" href="http://www.24media.gr/about" target="_blank"></a>
		<div>SITE ΤΟΥ ΟΜΙΛΟΥ 24MEDIA</div>
		<jsp:useBean id="date" class="java.util.Date" />
		<div>&copy; <fmt:formatDate value="${date}" pattern="yyyy" /> LADYLIKE. All Rights Reserved</div>

        <a class="ened" href="http://www.ened.gr/"></a>
	</div>
</div>
        <div class="row text-center ened_bar"></div>