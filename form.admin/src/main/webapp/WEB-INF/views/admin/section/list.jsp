<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>List Of Sections</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li class="active">Sections</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>List Of Sections</h3>
					</div>
				</div>
			</div>
			<!-- Sections List -->
			<div class="row">
				<!-- Alert Messages -->
				<jsp:include page="../alerts.jsp"/>
				<!-- Table -->
				<div class="col-xs-12">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th class="hidden-xs">Id</th>
									<th>Name</th>
									<th>Publication</th>
									<th class="t-wider hidden-xs text-nowrap">Date Updated</th>
									<th class="t-narrow">Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${sections}" var="section">
									<tr>
										<td class="hidden-xs">${section.id}</td>
										<td>${section.name}</td>
										<td>${section.publication.name}</td>
										<td class="t-wider hidden-xs"><spring:eval expression="section.dateUpdated" /></td>
										<td class="t-narrow">
											<a href="<c:url value='/admin/section/articles/${section.id}' />" class="section-plus" title="View Articles"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
											<a href="<c:url value='/admin/section/update/${section.id}' />" title="Edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
											<a class="confirm-dialog" dialog="Are You Sure You Want To Delete This Section?" href="<c:url value='/admin/section/delete/${section.id}' />" title="Delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
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
					<a class="btn btn-lg btn-primary" href="<c:url value='/admin/section/create' />" role="button">Create Section</a>
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
		<!-- AJAX To Get Section Articles -->
		<script type="text/javascript" charset="UTF-8">
			$(document).on("click", ".section-plus", function() {
				event.preventDefault();
				var _this = $(this);
				var _parent = _this.parent('td').parent('tr');
				/*Clear Classes And Possible Previous Results*/
				$("tr.ajax").remove();
				$("tr.ajax-parent").removeClass("ajax-parent");
				$("span.glyphicon-minus").removeClass("glyphicon-minus").addClass("glyphicon-plus");
				$(".section-minus").not(this).removeClass("section-minus");
				if(_this.hasClass("section-minus")) { /*Collapse*/
					_this.removeClass("section-minus");
					_this.find("span").removeClass("glyphicon-minus").addClass("glyphicon-plus");
					_this.attr("title", "View Articles");
				}
				else { /*Expand*/
					_parent.addClass("ajax-parent");
					_this.addClass("section-minus");
					_this.find("span").removeClass("glyphicon-plus").addClass("glyphicon-minus");
					_this.attr("title", "Hide Articles");
					$.ajax({
						url: _this.attr('href'),
						success : function(data) {
							var _rows = "";
							var counter = 0;
							$.each(data, function(i, item) {
								/*Section and Article Ids To Allow Deletion Of sectionArticle*/
								var _sectionTokens = _this.attr('href').split("/");
								var _sectionId = _sectionTokens[_sectionTokens.length-1];
								var _articleTokens = i.split(":");
								var _eceArticleId = _articleTokens[0];
								var _deleteIt = ""; //A Default Value 
								var _isNumeric = /^\d+$/;
								if(_isNumeric.test(_sectionId) && _isNumeric.test(_eceArticleId)) {
									_deleteIt = "<a href='/admin/section/deleteArticle/?sectionId=" + _sectionId + "&eceArticleId=" + _eceArticleId + "' title='Delete Article From Section' class='confirm-dialog pull-right-extra' dialog='Are You Sure You Want To Delete This Article From This Section?'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></a>";
								}
								/*Build The HTML For Every Row*/
								_rows = _rows.concat("<tr class='ajax'>");
			                	_rows = _rows.concat("<td>&nbsp;</td>");
			                	_rows = _rows.concat("<td colspan='3'><a href='" + item + "' title='View Article' target='_blank' class='pull-right-extra'><span class='glyphicon glyphicon-search' aria-hidden='true'></span></a>" + _deleteIt + i + "</td>");
			                	_rows = _rows.concat("<td class='t-narrow hidden-xs'>"); /*Hide On xs Screens*/
			                	_rows = _rows.concat("</tr>");
								counter++;
							});
							if(counter>0) {
								$(_rows).insertAfter(_parent);
							}
							else { /*No Results*/
								$("<tr class='ajax'><td>&nbsp;</td><td colspan='3'>This Section Has No Articles</td><td class='t-narrow hidden-xs'></tr>").insertAfter(_parent);
							}
			            }
					});
				}
			});
		</script>
	</body>
</html>