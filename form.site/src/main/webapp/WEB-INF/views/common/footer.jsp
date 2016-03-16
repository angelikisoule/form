<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Bootstrap JS -->
<script src="/static/ladylike/bootstrap/js/bootstrap.min.js"></script>
<!-- Touch Swipe For Carousels And Menu -->
<script type="text/javascript" src="/static/admin/touchswipe-1.6/jquery.touchSwipe.min.js"></script>
<!-- Menu -->
<script>
	$(window).load(function(){
    	$("[data-toggle-menu]").click(function() {
        	$("#sidebar").toggleClass("open-sidebar");
          	$(".brand").toggleClass("open-sidebar");
          	$(".navbar").toggleClass("open-sidebar");
          	$(".xartaki").toggleClass("open-sidebar");
          	/*when menu is open body doesn't scroll and doesn't go on top*/
          	var pixelFromTop;
          	/*close*/
          	if($("body").hasClass("open-sidebar")) {
              	pixelFromTop=$("body.open-sidebar").css("top").replace(/[^0-9]/g, '');
          		$("body").css("top",-pixelFromTop);
          		$("body").removeClass("open-sidebar");
          		$("body").css("top","initial");
           		$(window).scrollTop(pixelFromTop);
          	}
          	/*open*/
          	else {
	          	pixelFromTop=$(window).scrollTop();
	          	$("body").css("top",-pixelFromTop);
          		$("body").addClass("open-sidebar");
          	}
        });
       	$(".swipe-area").swipe({
        	swipeStatus:function(event, phase, direction, distance, duration, fingers) {
            	if(phase=="move" && direction =="left") {
                	$("#sidebar").addClass("open-sidebar");
                	$(".navbar").addClass("open-sidebar");
                 	$(".brand").addClass("open-sidebar");
                 	$(".xartaki").addClass("open-sidebar");
					var pixelFromTop=$(window).scrollTop();
		          	$("body").css("top",-pixelFromTop);
	          		$("body").addClass("open-sidebar");
                	return false;
              	}
              	if(phase=="move" && direction =="right") {
                	$("#sidebar").removeClass("open-sidebar");
                	$(".navbar").removeClass("open-sidebar");
                	$(".brand").removeClass("open-sidebar");
                	$(".xartaki").removeClass("open-sidebar");
					var pixelFromTop=$("body.open-sidebar").css("top").replace(/[^0-9]/g, '');
	          		$("body").css("top",-pixelFromTop);
	          		$("body").removeClass("open-sidebar");
	          		$("body").css("top","initial");
	           		$(window).scrollTop(pixelFromTop);
                  	return false;
               	}
       		}
     	});
	});
  	$(document).ready(function(){
		if($('html').hasClass('no-positionfixed')) $('#sidebar').css('height','1150px !important');
	  		else $('#sidebar').css('height',$(window).outerHeight()-51); //screen.height 
  	});
  	var test = window.matchMedia("(orientation: portrait)");
  	test.addListener(function(m) {
    	if(m.matches) { //Changed To Portrait 
      		$('#sidebar').css('height',$(window).outerHeight()-51); //screen.height 
    	}
    	else { //Changed To Landscape 
      		$('#sidebar').css('height',$(window).outerHeight()-51); //screen.height 
    	}
  	});
</script>
<!-- Lazyload Images -->
<script src="/static/admin/lazyload-1.9.5/jquery.lazyload.min.js"></script>
<script>
	$(function() {
		$(".lazyload").lazyload({
			threshold : 500,
   			effect : 'fadeIn',
   			placeholder: "${defaultImage}" /*Application Scope Parameter*/
   		});
	});
</script>
<!--Advertising Titles Script-->
<script>
	$(window).load(function() {
		if($('.advertisementContent.HP_300_250').height()>10) { /*Check If Div Got A Height*/
			$('.advertisementTitle.HP_300_250').css('display','block');
			$('.advertisementContent.HP_300_250').css('margin-bottom','34px');
		}
		if($('.advertisementContent.HP_300_250_b').height()>10) {
			$('.advertisementTitle.HP_300_250_b').css('display','block');
			$('.advertisementContent.HP_300_250_b').css('margin-bottom','34px');
		}
		if($('.advertisementContent.ROS_300_250').height()>10) {
			$('.advertisementTitle.ROS_300_250').css('display','block');
			$('.advertisementContent.ROS_300_250').css('margin-bottom','34px');
		}
		if($('.advertisementContent.ROS_300_250_B').height()>10) {
			$('.advertisementTitle.ROS_300_250_B').css('display','block');
			$('.advertisementContent.ROS_300_250_B').css('margin-bottom','34px');
		}
	});
</script>
<!--Disable Right Click On Images-->
<script>
	$(document).ready(function() {
		$("body").on("contextmenu", "img.img-responsive, .article.mini img, #carousel-shopping img", function(event) {
			return false;
		});
	});
</script>
<!--Google Analytics, Chartbeat et al.-->
<jsp:include page="../analytics/analytics.jsp"/>
<jsp:include page="../analytics/nuggad.jsp"/>
<jsp:include page="../analytics/atinternet.jsp"/>
<jsp:include page="../analytics/chartbeat.jsp"/>