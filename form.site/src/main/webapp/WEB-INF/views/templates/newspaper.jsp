<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${newspaper.categoryName} | Ladylike.gr</title>
		<meta name="description" content="${newspaper.categoryName} - Ladylike.gr | Ειδήσεις από όλον τον κόσμο - Portal ενημέρωσης και ψυχαγωγίας. Ειδήσεις από όλον τον κόσμο με έμφαση σε επικαιρότητα, πολιτική, κινηματογράφο, μουσική, τέχνες, τεχνολογία, lifestyle, περιβάλλον, υγεία κ.α." />
		<link rel="canonical" href="${newspaper.article.alternate}" />
		<jsp:include page="../common/head.jsp"/>
		<link href="/static/ladylike/css/common/navbar.css" rel="stylesheet">
		<link href="/static/ladylike/css/templates/newspapers.css" rel="stylesheet">
		<link href="/static/ladylike/css/common/follow.css" rel="stylesheet">
	</head>
	<body class="newspaper">
		<jsp:include page="../common/navbar.jsp"/>
		<div class="container-fluid">
			<div class="row navigation">
				<div class="col-xs-4">
					<div class="form-group">
                		<div class='input-group date' id='datetime-picker'>
	                    	<span class="input-group-addon">
	                        	<span></span>
	                    	</span>
	                    	<fmt:formatDate var="initialDate" pattern="d/M/yyyy" value="${newspaper.article.datePublished.time}" />
							<input type='text' class="form-control" value="${initialDate}" readonly="readonly" />
                		</div>
            		</div>
				</div>
				<div class="col-xs-8">
					<div class="pull-right">
						<c:if test="${newspaper.hasPrevious}">
		               		<a href="${newspaper.previous}" class="btn btn-default previousNewspaper" role="button"><span></span>ΠΡΟΗΓΟΥΜΕΝΟ</a>
		           		</c:if>
		           		<c:if test="${newspaper.hasNext}">
		               		<a href="${newspaper.next}" class="btn btn-default nextNewspaper" role="button">ΕΠΟΜΕΝΟ<span></span></a>
		           		</c:if>
	           		</div>
           		</div>
           		<div class="border"></div>
			</div>
			<div class="row text-center">
				<div class="col-xs-12">
					<h1>${newspaper.categoryName}</h1>
					<cms:GetRelatedImage var="cover" version="w380" article="${newspaper.article}" cropped="false" />
					<c:choose>
						<c:when test="${newspaper.hasNext}">
							<a href="${newspaper.next}">
								<img src="${cover}" class="img-responsive" alt="Cover Image" onError="this.src='${defaultImage}'" />
							</a>
						</c:when>
						<c:otherwise>
							<img src="${cover}" class="img-responsive" alt="Cover Image" onError="this.src='${defaultImage}'" />	
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-xs-12 back">
					<a href="/newspapers/">
						<span>&laquo;</span>επιστροφή στη λίστα
					</a>
				</div>	
			</div>
			<jsp:include page="../common/follow.jsp"/>
			<jsp:include page="../common/footer.jsp"/>
			<!-- Date Picker -->
			<script type="text/javascript" src="/static/admin/bootstrap-datepicker-1.3.1/dist/js/bootstrap-datepicker.js"></script>
			<script type="text/javascript">
				//Java HashMap To JavaScript Array 
				var recent = '${recent}'.toString();
				recent = recent.replace("{","");
				recent = recent.replace("}","");
				recent = recent.replace(/ /g,'');
				recent = recent.split(",");
				var newspaperMap = new Object();
				for(var i = 0; i<recent.length; i++) {
					var tokens = recent[i].split("=");
					newspaperMap[tokens[0]] = tokens[1];
				}
				var availableDates = Object.keys(newspaperMap); //Available Dates From Map Keys 
				//Custom i18n For Uppercase Only 
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
					    var alternate = newspaperMap[$('#datetime-picker input').val()];
					    if(alternate!==undefined) {
					    	window.location = alternate;
					    }
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
		</div>
	</body>
</html>