<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../common/head.jsp" />
	<title>Αναζήτηση | Ladylike.gr</title>
	<link rel="stylesheet" type="text/css" href="/static/ladylike/css/templates/search.css">
	<link rel="stylesheet" type="text/css" href="/static/ladylike/css/common/navbar.css">
	<link rel="stylesheet" type="text/css" href="/static/ladylike/css/common/follow.css">
</head>
<body>
	<jsp:include page="../common/navbar.jsp" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12">
				<script>
					(function() {
						var cx = '006320514970240743029:cbwx9mkpw8s';
						var gcse = document.createElement('script');
						gcse.type = 'text/javascript';
						gcse.async = true;
						gcse.src = (document.location.protocol == 'https:' ? 'https:'
								: 'http:')
								+ '//cse.google.com/cse.js?cx=' + cx;
						var s = document.getElementsByTagName('script')[0];
						s.parentNode.insertBefore(gcse, s);
					})();
				</script>
				<gcse:searchresults-only></gcse:searchresults-only>
			</div>
		</div>
		<jsp:include page="../common/follow.jsp" />
		<jsp:include page="../common/footer.jsp" />
		<script type="text/javascript" src="/static/admin/fakecrop/fakecrop/jquery.fakecrop.js"></script>
		<script>
			$(window).onload(function() {
				var _size = 180;
				$(".col-xs-12 img").fakecrop({
					'wrapperWidth' : _size,
					'wrapperHeight' : _size
				});
			});
		</script>
	</div>
</body>
</html>