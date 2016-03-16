<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>List Of Articles</title>
		<c:choose>
			<c:when test="${param.type=='story'}"><c:set var="plural" value="Stories" /></c:when>
			<c:when test="${param.type=='advertorial'}"><c:set var="plural" value="Advertorials" /></c:when>
			<c:when test="${param.type=='photostory'}"><c:set var="plural" value="Photostories" /></c:when>
			<c:when test="${param.type=='newspaper'}"><c:set var="plural" value="Newspapers" /></c:when>
			<c:when test="${param.type=='picture'}"><c:set var="plural" value="Pictures" /></c:when>
			<c:when test="${param.type=='video'}"><c:set var="plural" value="Videos" /></c:when>
			<c:otherwise>
				<c:set var="plural" value="Articles" />
			</c:otherwise>
		</c:choose>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<c:if test="${not empty param.type}">
  					<li><a href="<c:url value='/admin/article' />">Articles</a></li>
  				</c:if>
  				<li class="active">${plural}</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="btn-group btn-group-justified" role="group">
						<a class="btn btn-default <c:if test='${param.type=="story"}'>active</c:if>" href="<c:url value='/admin/story?type=story'/>">Stories</a>
         				<a class="btn btn-default <c:if test='${param.type=="advertorial"}'>active</c:if>" href="<c:url value='/admin/advertorial?type=advertorial'/>">Advertorials</a>
         				<a class="btn btn-default <c:if test='${param.type=="photostory"}'>active</c:if>" href="<c:url value='/admin/photostory?type=photostory'/>">Photostories</a>
         				<a class="btn btn-default <c:if test='${param.type=="newspaper"}'>active</c:if>" href="<c:url value='/admin/newspaper?type=newspaper'/>">Newspapers</a>
         				<a class="btn btn-default <c:if test='${param.type=="picture"}'>active</c:if>" href="<c:url value='/admin/picture?type=picture'/>">Pictures</a>
         				<a class="btn btn-default <c:if test='${param.type=="video"}'>active</c:if>" href="<c:url value='/admin/video?type=video'/>">Videos</a>
					</div>
				</div>
				<div class="col-xs-12">
					<div class="page-header">
						<h3>List Of ${plural}
							<c:if test="${not empty param.type}">
								<small>[ARCHIVED ${plural} Are Not Visible]</small>
							</c:if>
						</h3>
					</div>
				</div>
			</div>
			<!-- Articles List -->
			<div class="row">
				<!-- Alert Messages -->
				<jsp:include page="../alerts.jsp"/>
				<!-- Search Form -->
				<div class="col-xs-12">
					<form id="search-form" class="form-inline well text-center">
						<div class="row">
							<div class="col-xs-10 text-right">
								<div class="form-group" style="width:90%;">
						    		<input type="text" class="form-control input-lg autocomplete" id="search-term" placeholder="Search EceId Or Title" maxlength="45" style="width:100%;" />
			  					</div>
							</div>
							<div class="col-xs-2 text-left">
								<input type="reset" value="Clear" class="btn btn-lg btn-primary" />
							</div>
						</div>
					</form>
				</div>
				<!-- Table -->
				<div class="col-xs-12">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th class="hidden-xs">Id</th>
									<th>EceId</th>
									<th>Type</th>
									<th>State</th>
									<c:if test='${param.type=="video"}'>
										<th>Video Type</th>
									</c:if>
									<th>Title</th>
									<th>Home Category</th>
									<th class="t-wider hidden-xs">Date Published</th>
									<th class="t-narrow">Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${articles}" var="article">
									<tr>
										<td class="hidden-xs">${article.id}</td>
										<td>${article.eceArticleId}</td>
										<td>${article.articleType}</td>
										<td>${article.articleState}</td>
										<c:if test='${param.type=="video"}'>
											<td>${article.videoType}</td>
										</c:if>
										<td>${article.title}</td>
										<td>
											<c:choose>
												<c:when test="${not empty article.categories[0].name}">
													${article.categories[0].name}
												</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</td>
										<td class="t-wider hidden-xs text-nowrap">
											<c:choose>
												<c:when test="${not empty article.datePublished}">
													<spring:eval expression="article.datePublished" />
												</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</td>
										<td class="t-narrow">
											<c:set var="type" value="${fn:toLowerCase(article.articleType)}" />
											<a href="<c:url value='/admin/${type}/update/${article.id}' />" title="Edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
											<c:choose>
												<c:when test="${article.articleState!='ARCHIVED'}">
													<a class="confirm-dialog" dialog="Are You Sure You Want To Archive This Article?" accept="Archive" href="<c:url value='/admin/article/archive/${article.id}' />" title="Archive"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
												</c:when>
												<c:otherwise>
													<span class="glyphicon glyphicon-remove" aria-hidden="true" title="Already Archived"></span>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- Pagination And Extra Actions -->
			<div class="row">
				<div class="col-xs-12 col-md-6">
					<a class="btn btn-lg btn-primary" href="<c:url value='/admin/article/archived' />" role="button">Archived Articles</a>
				</div>
				<div class="col-xs-12 col-md-6">
					<nav>
						<ul class="pagination pagination-lg pull-right">
							<jsp:include page="../pagination.jsp" />
						</ul>
					</nav>
				</div>
			</div>
		</div><!-- container-fluid -->
		<jsp:include page="../footer.jsp"/>	
		<!-- BootBox Confirm Dialogs -->
		<script src="<c:url value='/static/admin/bootbox-4.4.0/bootbox.min.js' />"></script>
		<script src="<c:url value='/static/admin/admin.js' />"></script>
		<script src="<c:url value='/static/admin/jquery-ui-1.11.4/jquery-ui.min.js' />"></script>
		<script type="text/javascript" charset="UTF-8">
			$("#search-form").submit(function (event) {
				event.preventDefault();
			});
			$(document).on("focus", ".autocomplete", function() { /*Autocomplete*/
				$(this).autocomplete({
		      		source: function(request,response) {
	    	        	$.ajax({
	    	            	url: "/admin/article/searchArticle",
	    	            	dataType: "json",
	    	            	contentType: "application/json; charset=utf-8",
	    	            	data: {
	    	              		term: request.term
	    	            	},
	    	            	success: function(data) {
	    	            		if(!data.length) {
	    	            			var result = [{ label: 'No matches found', value: "" }];
	    	            		   	response(result);
	    	            		}
	    	            		else {
	    	              			response(data);
	    	            		}  		
	    	            	}
	    	    		});
		        	},
		        	minLength: 5,
		        	delay: 800,
		        	select: function (event, ui) {
		        		var tokens = ui.item.value.split(":");
		        		if(tokens.length>1) {
		        			window.location = "/admin/article/searchResult/" + tokens[0];
		        		};
	            	}
				});
			});
		</script>
	</body>
</html>