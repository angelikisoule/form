<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<nav class="navbar navbar-default navbar-fixed-top" >
	<div class="container-fluid">
		<a href="/" class="brand"></a>	
		<a href="javascript:void(0);" data-toggle-menu="#sidebar" id="sidebar-toggle" class="menu"></a>
		<form action="/search/" method="GET" id='searchForm'>
			<input type="text" class="searchname" name="q" /> 
			<a	class='searchbutton' href="javascript:document.forms['searchForm'].submit();"></a>
		</form>
		<div class="xartaki" ></div>
		<div id="sidebar">
			<ul>
				<li><a href="/">HOME</a></li>
				<li>
					<a href="/articles/style/" class="sub">STYLE</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-1" aria-expanded="false" aria-controls="collapse-1"></a>
					<ul class="collapse" id="collapse-menu-1">
						<li><a href="/articles/style/Kokkino_Chali/">ΚΟΚΚΙΝΟ ΧΑΛΙ</a></li>
						<li><a href="/articles/style/Moda/">ΜΟΔΑ</a></li>
						<li><a href="/articles/style/Celeb_Style/">CELEB STYLE</a></li>
					</ul>
				</li>
				<li>
					<a href="/articles/beauty/" class="sub">BEAUTY</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-11" aria-expanded="false" aria-controls="collapse-11"></a>
					<ul class="collapse" id="collapse-menu-11">
						<li><a href="/articles/style/Kokkino_/articles/beauty/mallia/">ΜΑΛΛΙΑ</a></li>
						<li><a href="/articles/beauty/Aroma/">ΑΡΩΜΑ</a></li>
						<li><a href="/articles/beauty/prosopo/">ΠΡΟΣΩΠΟ</a></li>
						<li><a href="/articles/beauty/soma/">ΣΩΜΑ</a></li>
						<li><a href="/articles/beauty/nychia/">ΝΥΧΙΑ</a></li>
					</ul>
				</li>
				<li>
					<a href="/articles/living/" class="sub">LIVING</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-2" aria-expanded="false" aria-controls="collapse-2"></a>
					<ul class="collapse" id="collapse-menu-2">
						<li><a href="/articles/living/exodos/">ΕΞΟΔΟΣ</a></li>
						<li><a href="/articles/living/Spiti/">ΣΠΙΤΙ</a></li>
						<li><a href="/articles/living/Taxidi/">ΤΑΞΙΔΙ</a></li>
						<li><a href="/articles/living/psychology/">ΨΥΧΟΛΟΓΙΑ</a></li>
					</ul>
				</li>
				<li>
					<a href="/articles/celebs/" class="sub">CELEBS</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-3" aria-expanded="false" aria-controls="collapse-3"></a>
					<ul class="collapse" id="collapse-menu-3">
						<li><a href="/articles/celebs/Hot_eidiseis/">HOT ΕΙΔΗΣΕΙΣ</a></li>
						<li><a href="/articles/celebs/Gossip/">GOSSIP</a></li>
						<li><a href="/articles/celebs/Zevgaria/">ΖΕΥΓΑΡΙΑ</a></li>
					</ul>
				</li>
				<li>
					<a href="/articles/pop_culture/" class="sub">POP CULTURE</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-4" aria-expanded="false" aria-controls="collapse-4"></a>
					<ul class="collapse" id="collapse-menu-4">
						<li><a href="/articles/pop_culture/Cinema/">CINEMA</a></li>
						<li><a href="/articles/pop_culture/Tileorasi/">ΤΗΛΕΟΡΑΣΗ</a></li>
						<li><a href="/articles/pop_culture/Mousiki/">ΜΟΥΣΙΚΗ</a></li>
						<li><a href="/articles/pop_culture/Theatro/">ΘΕΑΤΡΟ</a></li>
						<li><a href="/articles/pop_culture/Vivlia/">ΒΙΒΛΙΑ</a></li>
					</ul>
				</li>
				<li>
					<a href="/articles/love/" class="sub">LOVE</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-5" aria-expanded="false" aria-controls="collapse-5"></a>
					<ul class="collapse" id="collapse-menu-5">
						<li><a href="/articles/love/Scheseis/">ΣΧΕΣΕΙΣ</a></li>
						<li><a href="/articles/love/sex/ ">SEX</a></li>
					</ul>
				</li>
				<li>
					<a href="/articles/self_and_health/" class="sub">SELF & HEALTH</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-6" aria-expanded="false" aria-controls="collapse-6"></a>	
					<ul class="collapse" id="collapse-menu-6">
						<li><a href="/articles/self_and_health/gymnastiki/">ΓΥΜΝΑΣΤΙΚΗ</a></li>
						<li><a href="/articles/self_and_health/diatrofi/">ΔΙΑΤΡΟΦΗ</a></li>
						<li><a href="/articles/self_and_health/psychologia/">ΨΥΧΟΛΟΓΙΑ</a></li>
						<li><a href="/articles/self_and_health/ygeia/">ΥΓΕΙΑ</a></li>
						<li><a href="/articles/self_and_health/ygeia/rota_ton_giatro">ΡΩΤΑ ΤΟΝ ΓΙΑΤΡΟ</a></li>
					</ul>
				</li>
				<li><a href="/articles/food/">FOOD</a></li>
				<li><a href="/articles/moms/">MOMS</a></li>
				<!--  
				<li>
					<a href="/articles/moms/" class="sub">MOMS</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-7" aria-expanded="false" aria-controls="collapse-7"></a>
					<ul class="collapse" id="collapse-menu-7">
						<li><a href="/articles/moms/egkymosini/">ΕΓΚΥΜΟΣΥΝΗ</a></li>
						<li><a href="/articles/moms/paidi/">ΠΑΙΔΙ</a></li>
						<li><a href="/articles/moms/oikogeneia/">ΟΙΚΟΓΕΝΕΙΑ</a></li>
					</ul>
				</li>
				-->
				<li>
					<a href="/articles/shopping_list/" class="sub">SHOPPING</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-8" aria-expanded="false" aria-controls="collapse-8"></a>	
					<ul class="collapse" id="collapse-menu-8">
						<li><a href="/articles/shopping_list/gadgets_shopping/">GADGETS</a></li>
						<li><a href="/articles/shopping_list/gadgets_shopping/">KIDS</a></li>
						<li><a href="/articles/shopping_list/sport_shopping/">SPORTS</a></li>
						<li><a href="/articles/shopping_list/moda_shopping/">ΜΟΔΑ</a></li>
						<li><a href="/articles/shopping_list/accessories_shopping/">ΑΞΕΣΟΥΑΡ</a></li>
						<li><a href="/articles/shopping_list/kosmimata_shopping/">ΚΟΣΜΗΜΑΤΑ</a></li>
						<li><a href="/articles/shopping_list/omorfia_shopping/">ΟΜΟΡΦΙΑ</a></li>
						<li><a href="/articles/shopping_list/home_shopping/">ΗΟΜΕ</a></li>
					</ul>
				</li>
				<li class="white"><div><a href="/articles/synentefxeis/">ΣΥΝΕΝΤΕΥΞΕΙΣ</a></div></li>
                <li class="white"><div><a target="_blank" href="http://portfolio.ladylike.gr/">PORTFOLIOS</a></div></li>
				<!--<li class="white"><div><a href="/articles/galleries/galleries/">GALLERIES</a></div></li>-->
				<li class="white">
					<div>
						<a href="/articles/columns/" class="sub">ΣΤΗΛΕΣ</a>
						<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-9" aria-expanded="false" aria-controls="collapse-9"></a>
						<ul class="collapse" id="collapse-menu-9">
							<li><a href="/articles/columns/gynaikes-sti-thesi-tous/">ΕΡΓΑΖΟΜΕΝΗ ΓΥΝΑΙΚΑ</a></li>
							<li><a href="/articles/columns/men-we-love/">MEN WE LOVE</a></li>
							<!--<li><a href="/articles/bodylanguage">BODY LANGUAGE</a></li>-->
							<li><a href="/articles/columns/pantremeni-me-paidi/">ΠΑΝΤΡΕΜΕΝΗ ΜΕ ΠΑΙΔΙ</a></li>
							<!--<li><a href="/articles/ladystyle">LADY STYLE</a></li>-->
						</ul>
					</div>
				</li>
				<li class="white"><div>
					<a href="/articles/opinions/" class="sub">OPINIONS</a>
					<a class="btn btn-primary" data-toggle="collapse" href="#collapse-menu-10" aria-expanded="false" aria-controls="collapse-10"></a>
					<ul class="collapse" id="collapse-menu-10">
						<li><a href="/articles/opinions/hagia/">ΝΙΚΗ ΧΑΓΙΑ</a></li>
						<li><a href="/articles/opinions/dima/">ΔΕΣΠΟΙΝΑ ΔΗΜΑ</a></li>
						<li><a href="/articles/opinions/marieta-xristopoulou/">ΜΑΡΙΕΤΑ ΧΡΙΣΤΟΠΟΥΛΟΥ</a></li>
						<li><a href="/articles/opinions/marion-palioura/">ΜΑΡΙΟΝ ΠΑΛΙΟΥΡΑ</a></li>
						<li><a href="/articles/opinions/mamai/">ΙΩΑΝΝΑ ΜΑΜΑΗ</a></li>
						<li><a href="/articles/opinions/natali-saitaki/">ΝΑΤΑΛΙ ΣΑΪΤΑΚΗ</a></li>
						<li><a href="/articles/opinions/siati/">ΣΑΒΒΙΑ ΣΙΑΤΗ</a></li>
					</ul>
				</div>
				</li>
				<li class="white"><div><a href="/articles/zodia/">ΖΩΔΙΑ</a></div></li>
				<li class="white"><div><a href="/articles/quiz/">QUIZZES</a></div></li>
				<li class="white"><div><a href="/newspapers/">ΕΞΩΦΥΛΛΑ</a></div></li>
				<li class="white"><div><a href="/videos/">VIDEOS</a></div></li>
			</ul>
			<div class="bottom-menu" style="padding-bottom: 50px;">
				<div class="title">FOLLOW US</div>
				<div class="social">
					<div class="followFacebook"><a href="https://www.facebook.com/LadyLikegr" target="_blank"></a></div>
					<div class="followTwitter"><a href="https://twitter.com/ladylikegr" target="_blank"></a></div>
					<div class="followPinterest"><a href="https://www.pinterest.com/ladylikegr/" target="_blank"></a></div>
					<div class="followInstagram"><a href="https://instagram.com/ladylike.gr/" target="_blank"></a></div>
					<div class="followYouTube"><a href="https://www.youtube.com/channel/UCDxJFB3yCYpsRbuzyhw-uPg" target="_blank"></a></div>
				</div>
				<div class="newsletter-button"><a href="#">NEWSLETTER</a></div>
				<div class="full-site"><a href="http://www.ladylike.gr?stayOnDesktop=true">FULL SITE</a></div>
				<div class="copyrights">
					<hr /><%--Border--%>
					<a class="company" href="http://www.24media.gr/" target="_blank"></a>
					<p>SITE ΤΟΥ ΟΜΙΛΟΥ 24MEDIA</p>
					<p>&copy; 2015 LADYLIKE. All Rights Reserved</p>
				</div>
			</div>
		</div>
	</div>
</nav>
<div class="swipe-area"></div>
<script type="text/javascript" src="/static/ladylike/js/menu.js"></script>
