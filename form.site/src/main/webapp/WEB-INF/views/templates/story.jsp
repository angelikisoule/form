<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
    <head>
        <title>${article.title} | Ladylike.gr</title>
		<meta name="description" content="${article.title} - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
        <link rel="canonical" href="${article.alternate}" />
        <jsp:include page="../common/head.jsp"/>
        <jsp:include page="../analytics/parsely-header.jsp"/><%-- parse.ly Header--%>
        <jsp:include page="/WEB-INF/views/common/social-meta.jsp"/>
        <link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
        <link href="/static/ladylike/css/common/socials.css" rel="stylesheet">
        <link href="/static/ladylike/css/templates/story.css" rel="stylesheet">
        <link href="/static/ladylike/css/common/readmore.css" rel="stylesheet">
        <link href="/static/ladylike/css/common/newsletter.css" rel="stylesheet">
        <link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
        <link href="/static/ladylike/css/templates/galleries.css" rel="stylesheet">
        <link href="/static/ladylike/css/templates/galleries.css" rel="stylesheet">
        <link href="/static/ladylike/css/carousels/generic.css" rel="stylesheet">
        <jsp:include page="../advertisements/DFP_ROS_Head.jsp"/>
        <script type="text/javascript" src="/static/ladylike/js/reEmbed.js"></script>
        <fmt:setLocale value="el_GR" scope="session" />
    </head>
    <body>
    	<jsp:include page="../common/socials.jsp"/>
        <jsp:include page="../common/navbar.jsp"/>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="title">${article.title}</h1>
                   	<jsp:include page="../common/editorArticle.jsp">
                        <jsp:param name="name" value="${article.categories[0].name}" />
                        <jsp:param name="section" value="${article.categories[0].sectionUniqueName}" />
                    </jsp:include>
                    <%-- If There's A Video Relation With Type YOUTUBE | DAILYMOTION Show It Instead Of The Image --%>
                    <c:choose>
                    	<c:when test="${not empty relatedVideos && (relatedVideos[0].videoType=='YOUTUBE' || relatedVideos[0].videoType=='DAILYMOTION')}">
                    		<cms:VideoUrl id="videoUrl" article="${relatedVideos[0]}" autoplay="false" />
                    		<c:choose>
								<c:when test="${fn:contains(videoUrl, 'www.dailymotion.com')}">
									<div class="embed-responsive embed-responsive-16by9">
										<iframe class="embed-responsive-item" src="${videoUrl}"></iframe>
									</div>
								</c:when>
								<c:when test="${fn:contains(videoUrl, 'www.youtube.com')}">
									<div class="embed-responsive embed-responsive-16by9">
										<iframe class="embed-responsive-item" 
												src="${videoUrl}" 
												frameborder="0" 
												allowfullscreen>
										</iframe>
									</div>
								</c:when>
                    		</c:choose>
                    	</c:when>
                    	<c:otherwise>
                    		<cms:GetRelatedImage var="main" comment="main" version="w460" article="${article}" />
                    		<img src="${main}" class="img-responsive" alt="Main Image" onError="this.src='${defaultImage}'" />
                    	</c:otherwise>
                    </c:choose>
                    <div class="byline">
                        <c:choose>
                            <c:when test="${opinions_byline}">
                                <fmt:formatDate var="datte" value="${article.datePublished.time}" pattern="MMMM dd yyyy"/>
                                <cms:ToUpperCase input="${datte}" output="date"/>                                
                            	<span class="date_left">${date}</span>
                                <c:remove var="date"/>
                                <c:remove var="datte"/>                                
                            </c:when>
                            <c:otherwise>                                
                                <c:forEach items="${article.authors}" var="author" begin="0" end="0">
                                <cms:ToUpperCase input="${author.name}" output="editor"/>
                                    <span class="author">${editor}</span>
                                <c:remove var="editor"/>
                                </c:forEach>
                                <c:if test="${fn:length(article.authors) eq 0}">
                                    <span class="author">Ladylike.gr</span>
                                </c:if>
                                    <fmt:formatDate var="datte" value="${article.datePublished.time}" pattern="MMMM dd yyyy"/>
                                <cms:ToUpperCase input="${datte}" output="date"/>                                                            	
                                <span class="date">${date}</span>
                                <c:remove var="date"/>
                                <c:remove var="datte"/>                                
                            </c:otherwise>
                        </c:choose>
                   		<c:remove var="opinions_byline" scope="request"/>
                    </div>
                    <div class="body">
                        <c:if test="${not empty article.leadText}">
                        	<p class="leadtext">
          						${article.leadText}
                            </p>
                        </c:if>
                        <jsp:include page="../advertisements/ROS_320x50_B.jsp"/>
                        <cms:ArticleBody body="${article.body}" rosA="2" rosB="5" imageVersion="w460" capitalize="h2" />
                    </div>
                    <script>
                    	/*If .body h2 Contains One Of The Following Strings Apply Some Special Styles*/
                       	$(document).ready(function() {
                       		$("h2:contains('ΔΙΑΒΑΣΕ ΣΤΟ'), h2:contains('ΔΙΑΒΑΣΤΕ ΣΤΟ'), h2:contains('ΔΙΑΒΑΣΕ ΕΠΙΣΗΣ ΣΤΟ'), h2:contains('ΔΙΑΒΑΣΤΕ ΕΠΙΣΗΣ ΣΤΟ'), h2:contains('ΔΙΑΒΑΣΕ ΑΚΟΜΑ ΣΤΟ'), h2:contains('ΔΙΑΒΑΣΤΕ ΑΚΟΜΑ ΣΤΟ')" ).addClass("also").nextAll('.body p').addClass('also');
                       	});
                   	</script>
                   	<%-- Hide Disqus Comments When Articles Have Liveblog --%>
                    <c:if test="${empty liveblogId && empty publicationId}">
						<div class="disq_header">
                        	<div class="disq_left">
                           		<a class="disq" href="${article.alternate}#disqus_thread"></a>
                        	</div>
                        	<div class="disq_right">
                            	<div class="think">ΕΣΥ ΤΙ ΝΟΜΙΖΕΙΣ;</div>
                            	<div class="comm">ΑΦΗΣΕ ΤΟ ΣΧΟΛΙΟ ΣΟΥ</div>
                        	</div>
                    	</div>
	                    <div class="lineborder"></div>
	                    <jsp:include page="../common/disqus.jsp"/>
                	</c:if>
               	</div>                   
            </div>
             <%--<jsp:include page="../common/hayia.jsp"/>--%>
		   	<jsp:include page="../common/readmore.jsp"/>
		    <jsp:include page="../common/newsletter.jsp"/>
		    <jsp:include page="../common/follow.jsp"/>
		    <jsp:include page="../common/footer.jsp"/>
		    <jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
		    <jsp:include page="../advertisements/ROS_320x50_A.jsp"/>
			<c:if test="${not empty photostoryPictures && empty liveblogId && empty publicationId}">
		    	<%-- Carousel For Inline Photostories --%>
		        <div class="inline_ph">
		            <div class="container-fluid">
		                <div class="row gallery">
	                        <div id="carousel-generic" class="carousel slide" data-ride="carousel" data-interval="false">
	                            <%-- Wrapper for slides --%>
	                            <div class="carousel-inner" role="listbox">
	                                <!-- The .active class needs to be added to one of the slides. Otherwise, the carousel will not be visible -->
	                                <c:forEach var="picture" items="${photostoryPictures}" varStatus="loop">
	                                    <div class="item <c:if test='${loop.index==0}'>active</c:if>">
                                               <img class="img-responsive" src="${picture}" alt="Carousel Picture">
	                                        <span class="desc">${picture.value}</span>
	                                    </div>
	                                </c:forEach>
	                            </div>
	                            <%-- Controls --%>
	                            <a class="left carousel-control" href="#carousel-generic" role="button" data-slide="prev">
	                                <span class="arrowLeft"></span>
	                                <span class="sr-only">Previous</span>
	                            </a>
	                            <a class="right carousel-control" href="#carousel-generic" role="button" data-slide="next">
	                                <span class="arrowRight"></span>
	                                <span class="sr-only">Next</span>
	                            </a>
	                        </div>
		                </div>
                        <script>
                            /*Position Carousel Captions Under The Images And Arrows In The Middle Of The Total Height*/
		                    function captionPosition() {
		                   		$(".carousel-caption-outer").html($.trim($(".carousel-inner .item.active .carousel-caption").html()));
		                    }
		                    $(document).ready(function() { //When Document Loads For The First Time Slide or Slid Is Not Invoked 
		                    	captionPosition();
		                    });
		                    $("#carousel-generic").on("slide.bs.carousel", function() { //Remove Previous Caption When slide Is Invoked 
		                        $(".carousel-caption-outer").html("");
		                    });
		                    $("#carousel-generic").on("slid.bs.carousel", function() {
		                        captionPosition();
		                    });
		                    /*Touch Swipe For Carousel*/
		                    $(".carousel-inner").swipe({
		                        swipeRight: function(event, direction, distance, duration, fingerCount) {
		                            $(this).parent().carousel('prev');
		                        },
		                        swipeLeft: function() {
		                            $(this).parent().carousel('next');
		                        },
		                        threshold: 0
		                    });
		                </script>
		            </div>
		        </div>
	        </c:if>
        </div><%-- container-fluid --%>
        <c:if test="${empty liveblogId && empty publicationId}">
        	<!-- Scripts In Case Of No Liveblog In Article -->
	        <script type="text/javascript">
	            $(document).ready(function() {
	                $(".inline_ph").replaceAll("#photostory"); //Inline Photostory 
                    $(".disq").click(function(event){                    
                   event.preventDefault();                
                  });
	            });
	            $(window).load(function() {
	                $(".pb_feed").prepend('<div style="text-align:center;"><img src="/static/ladylike/images/xs/quiz.png"></div>').delay(6); //Quiz 
	                $(".disq").text($(".disq").text().split(" ")[0]); //Disqus 
	            });
	        </script>
		</c:if>
	    <c:if test="${not empty liveblogId && not empty publicationId}">
	    	<%-- Liveblog Script In Case ArticleBodyTag Returned A liveblogId --%>
		    <link href="/static/ladylike/css/liveblogs/default.css" rel="stylesheet">
		    <script type="text/javascript">
		    	var config = {
					serviceURL : "http://live.24media.gr",
					liveBlogID : ${liveblogId},
					publicationID: ${publicationId},
					commentsMaxChars : 160,
					perPage : 10,
					css : ""
				}
				var app = new app.start(config);
	    	</script>
	    </c:if>
    </body>
</html>