/*** Go To Top Footer Link ***/
$(document).ready(function() {
	$("a[href='#goToTop']").click(function() {
		$("html, body").animate({ scrollTop: 0 }, 400);
	  	return false;
	});
});

$(".newsletter-button a").attr("href", "/newsletter/?back=" + window.location);

//Scroll up/down menu effect
var lastScrollTop = 0;
var delta = 5;
var navbarHeight = $('.navbar').outerHeight();
var didScroll;
//On scroll, let the interval function know the user has scrolled
$(window).scroll(function(event) {
	didScroll = true;
});
//Run hasScrolled() and reset didScroll status
setInterval(function() {
	if(didScroll) {
		hasScrolled();
		didScroll = false;
	}
}, 250);

function hasScrolled() {
    var st = $(this).scrollTop();
    /* dont scroll when the menu is open */
    if($('body').hasClass('open-sidebar') && $('.swipe-area').css('right')=='248px' && !$('nav.navbar').hasClass('nav-up')){
    	return;
    }
    // Make sure they scroll more than delta
    if(Math.abs(lastScrollTop - st) <= delta)
        return;
    // If they scrolled down and are past the navbar, add class .nav-up.                
    if(st > lastScrollTop && st > navbarHeight) {
        // Scroll Down
        $('.navbar').removeClass('nav-down').addClass('nav-up');
        $('#sidebar-toggle').hide();  
        /* for articles and galleries*/
        if(!$("body").hasClass("galleryBody"))
        	$('.row.socials').show();
    } else {
        //Scroll Up
        if (st + $(window).height() < $(document).height()) {
            $('.navbar').removeClass('nav-up').addClass('nav-down');
            $('#sidebar-toggle').show();
            /* for articles and galleries*/
            if(!$("body").hasClass("galleryBody"))
            	$('.row.socials').hide();
        }
    }
    lastScrollTop = st;
}