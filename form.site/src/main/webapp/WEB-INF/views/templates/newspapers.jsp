<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
	<head>
		<c:choose>
			<c:when test="${not empty category.name}">
				<c:set var="pageTitle" value="${category.name} - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
			</c:when>
			<c:otherwise>
				<c:set var="pageTitle" value="Εφημερίδες | Ladylike.gr" />
			</c:otherwise>
		</c:choose>
		<title>${pageTitle}</title>
		<meta name="description" content="${pageTitle}" />
		<link rel="canonical" href="http://ladylike.gr${requestScope['javax.servlet.forward.request_uri']}" />
		<jsp:include page="../common/head.jsp"/>
		<%-- parse.ly Header --%>
		<jsp:include page="../analytics/parsely-header.jsp">
			<jsp:param name="headline" value="Newspapers" />
		</jsp:include>
		<link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
		<link href="/static/ladylike/css/templates/newspapers.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
	</head>
	<body class="newspapers">
		<jsp:include page="../common/navbar.jsp"/>
		<div class="container-fluid">
			<div class="row navigation">
				<div class="col-xs-12">
					<div class="form-group">
                		<div class='input-group date' id='datetime-picker'>
	                    	<span class="input-group-addon">
	                        	<span></span>
	                    	</span>
	                    	<c:choose>
	                    		<c:when test="${not empty param['datePublished']}">
	                    			<c:set var="initialDate" value="${param['datePublished']}" />
	                    		</c:when>
	                    		<c:otherwise>
	                    			<c:set var="today" value="<%=new java.util.Date()%>" />
	                    			<fmt:formatDate var="initialDate" pattern="d/M/yyyy" value="${today}" />
	                    		</c:otherwise>
	                    	</c:choose>
	                    	<input type='text' class="form-control" value="${initialDate}" readonly="readonly" />          		
                		</div>
            		</div>
					<div id="selectCategory" class="pull-right">
					    <button type="button" class="btn dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
					    	ΟΛΑ ΤΑ ΠΡΩΤΟΣΕΛΙΔΑ
					    	<span></span>
					    </button>
					    <ul class="dropdown-menu option" role="menu">
					    	<c:forEach var="selectCategory" items="${selectCategory}" varStatus="loop">
					    		<c:choose>
					    			<c:when test="${selectCategory.key==category.sectionUniqueName}">
					    				<c:set var="cl" value="selected" />
					    			</c:when>
					    			<c:otherwise>
					    				<c:set var="cl" value=""/>
					    			</c:otherwise>
					    		</c:choose>
					    		<li id="${loop.index}">
					    			<cms:ToUpperCase input="${selectCategory.value}" output="categoryToUpperCase"/>
					    			<a class="${cl}" href="/newspapers/${selectCategory.key}/">${categoryToUpperCase}</a>
					    		</li>
					    	</c:forEach>
					    </ul>
					</div>
				</div>
				<div class="border"></div>
			</div>
			<script>
				//Category Button Value In Case A Category Is Selected 
				if($('a.selected').length) {
					$('#selectCategory button').html($('a.selected').text() + '<span></span>');
				}
				//readonly Input Bug In Safari 
				$('input.form-control').on('focus', function() {
					$(this).trigger('blur');
				});
			</script>
			<c:forEach var="map" items="${newspapersMap}">
				<div class="row">
					<c:set var="showBanner" value="true" /><!-- Show Banner Only Before The First Newspaper Of Each Category -->
					<div class="col-xs-12">
						<c:forEach var="newspaper" items="${map.value}">
							<div class="row cover">
								<c:if test="${showBanner eq true}">
									<cms:ToUpperCase input="${map.key.name}" output="categoryToUpperCase"/>
									<span class="banner">
										${categoryToUpperCase}
										<span class="corner"></span>
									</span>
									<c:set var="showBanner" value="false" />
								</c:if>
								<div class="col-xs-12">
				      				<cms:ArticleUrl id="url" article="${newspaper}" />
				      				<cms:GetRelatedImage var="cover" version="w380" article="${newspaper}" cropped="false" />
									<h1>${newspaper.title}</h1>
									<a href="${url}">
										<img src="${cover}" class="img-responsive" alt="Cover Image" onError="this.src='${defaultImage}'" />
									</a>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
			<jsp:include page="../common/follow.jsp"/>
			<jsp:include page="../common/footer.jsp"/>
			<jsp:include page="../analytics/parsely-footer.jsp"/><%-- parse.ly Footer --%>
			<!-- Date Picker -->
			<script type="text/javascript" src="/static/admin/bootstrap-datepicker-1.3.1/dist/js/bootstrap-datepicker.js"></script>
			<script type="text/javascript">
				//Java ArrayList To JavaScript Array 
				var recent = '${recent}'.toString();
				recent = recent.replace("[","");
				recent = recent.replace("]","");
				recent = recent.replace(/ /g,'');
				var availableDates = recent.split(",");
				$.fn.datepicker.dates['el'] = {
				    days: ["ΚΥΡΙΑΚΗ", "ΔΕΥΤΕΡΑ", "ΤΡΙΤΗ", "ΤΕΤΑΡΤΗ", "ΠΕΜΠΤΗ", "ΠΑΡΑΣΚΕΥΗ", "ΣΑΒΒΑΤΟ"],
				    daysShort: ["ΚΥΡ", "ΔΕΥ", "ΤΡΙ", "ΤΕΤ", "ΠΕΜ", "ΠΑΡ", "ΣΑΒ"],
				    daysMin: ["ΚΥΡ", "ΔΕΥ", "ΤΡΙ", "ΤΕΤ", "ΠΕΜ", "ΠΑΡ", "ΣΑΒ"],
				    months: ["ΙΑΝΟΥΑΡΙΟΣ", "ΦΕΒΡΟΥΑΡΙΟΣ", "ΜΑΡΤΙΟΣ", "ΑΠΡΙΛΙΟΣ", "ΜΑΪΟΣ", "ΙΟΥΝΙΟΣ", "ΙΟΥΛΙΟΣ", "ΑΥΓΟΥΣΤΟΣ", "ΣΕΠΤΕΜΒΡΙΟΣ", "ΟΚΤΩΒΡΙΟΣ", "ΝΟΕΜΒΡΙΟΣ", "ΔΕΚΕΜΒΡΙΟΣ"],
				    monthsShort: ["ΙΑΝ", "ΦΕΒ", "ΜΑΡ", "ΑΠΡ", "ΜΑΙ", "ΙΟΥ", "ΙΟΥ", "ΑΥΓ", "ΣΕΠ", "ΟΚΤ", "ΝΟΕ", "ΔΕΚ"],
				    today: "Σήμερα",
				    clear: "Καθαρισμός"
				};
				$(function() {
					$('#datetime-picker').datepicker({
						format:'d/m/yyyy',
						language:'el',
						beforeShowDay: function(dt) {
							return available(dt);
				      	},				      	
				      	changeMonth:false,
				    	changeYear:false,
				    	todayHighlight:false,
				    	weekStart:1,
				    	autoclose:true,				    	
				    	disableTouchKeyboard:true
					}).on('changeDate', function() {				    
						var url = window.location.href;
						if(url.indexOf("?") < 0) { //No Query String 
					    	url += "?datePublished=" + $('#datetime-picker input').val();
						}
					    else { //Remove Query String First 
					    	url = url.split("?")[0] + "?datePublished=" + $('#datetime-picker input').val();
					    }
						window.location = url;
					}).on('show', function(event) {
						$(".row.navigation").addClass("datepicker-open");
					}).on('hide', function(event) {
						$(".row.navigation").removeClass("datepicker-open");
					});
				});
				function available(date) {
					dmy = date.getDate() + "/" + (date.getMonth()+1) + "/" + date.getFullYear();
				 	if($.inArray(dmy, availableDates)!=-1) {
						return true;
				  	}
				  	else {
				  		return false;
				  	}
				}
			</script>
			<!-- Category Selection -->
			<script type="text/javascript">
				$('#selectCategory').on('show.bs.dropdown', function () {
					$(".row.navigation").addClass("category-open");
				});
				$('#selectCategory').on('hide.bs.dropdown', function () {
					$(".row.navigation").removeClass("category-open");
				});
			</script>
		</div>
	</body>
</html>