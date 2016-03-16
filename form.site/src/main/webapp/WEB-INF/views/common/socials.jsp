<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="row socials">
	<div class="facebook"><a href="javascript:void(0);" onclick="postToFeed();"></a></div>
	<div class="twitter"><a href="javascript:void(0);" onclick="postTwitter();"></a></div>
	<cms:GetRelatedImage var="main" comment="main" version="w620" article="${article}" />
	<div class="pinterest"><a href="" data-image="${main}" data-desc="Custom Pinterest" onclick="postPin();" ></a></div>
	<div class="whatsapp"><a href="whatsapp://send?text=${article.alternate}"></a></div>
	<%--Tag Call To Get Previous URL--%>
	<c:if test="${not empty article && !fn:contains(requestScope['javax.servlet.forward.request_uri'], '/shopping_list/')}">
		<%--If We In A Product View The previousUrl Will Be Set By product.jsp--%>
		<cms:PreviousOrNextArticle id="previousUrl" article="${article}" next="false" />
	</c:if>	
	<c:choose>
		<c:when test="${empty previousUrl}">
			<div class="next empty">
				<p>NEXT</p>
			</div>
		</c:when>
		<c:otherwise>
			<div class="next">
				<a href="${previousUrl}"><p>NEXT</p></a>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<cms:RemoveQuotes output="cleanTitle" input="${article.title}"/>
<script>
	function postToFeed() {
		var _url='${article.alternate}';
		var elink = enClickUrl=encodeURIComponent(_url);
		var url="https://m.facebook.com/sharer.php?caption=Ladylike.gr&u="+elink;
		window.location.href=url;
	}
	function postTwitter() {
		var _url='${article.alternate}';
		var elink = enClickUrl=encodeURIComponent(_url);
		var url= "http://twitter.com/intent/tweet?text=${cleanTitle} "+elink+" @ladylikegr";
		window.location.href=url;
	}
	function postPin() {	
	    var url = '${article.alternate}';
	    var media = '${main}';
	    var desc ="${cleanTitle}";
	    window.open("//www.pinterest.com/pin/create/button/"+
	    "?url="+url+
	    "&media="+media+
	    "&description="+desc,"_blank");
	    return false;
	}
</script>