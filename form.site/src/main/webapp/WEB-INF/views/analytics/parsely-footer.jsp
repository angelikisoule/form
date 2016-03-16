<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="parsely" value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:if test="${not(fn:contains(parsely, '.html') and fn:contains(parsely, '/newspapers/'))}">
	<div id="parsely-root" style="display:none">
		<div id="parsely-cfg" data-parsely-site="ladylike.gr"></div>
	</div>
	<script type="text/javascript">
		(function(s, p, d) {
		var h=d.location.protocol, i=p+"-"+s,
		e=d.getElementById(i), r=d.getElementById(p+"-root"),
		u=h==="https:"?"d1z2jf7jlzjs58.cloudfront.net"
		:"static."+p+".com";
		if(e) return;
		e = d.createElement(s); e.id = i; e.async = true;
		e.src = h+"//"+u+"/p.js"; r.appendChild(e);
		})("script", "parsely", document);
	</script>
</c:if>
<c:remove var="parsely" />