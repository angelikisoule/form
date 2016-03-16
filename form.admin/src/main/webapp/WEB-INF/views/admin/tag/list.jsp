<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>List Of Tags</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li class="active">Tags</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>List Of Tags</h3>
					</div>
				</div>
			</div>
			<!-- Tags List -->
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
									<th>Display Name</th>
									<th>Publication</th>
									<th class="t-wider hidden-xs text-nowrap">Date Updated</th>
									<th class="t-narrow">Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${tags}" var="tag">
									<tr>
										<td class="hidden-xs">${tag.id}</td>
										<td>${tag.name}</td>
										<td>${tag.displayName}</td>
										<td>${tag.publication.name}</td>
										<td class="t-wider hidden-xs"><spring:eval expression="tag.dateUpdated" /></td>
										<td class="t-narrow">
											<a href="<c:url value='/admin/tag/articles/${tag.id}' />" class="tag-plus" title="View Articles"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
											<a href="<c:url value='/admin/tag/update/${tag.id}' />" title="Edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
											<a class="confirm-dialog" dialog="Are You Sure You Want To Delete This Tag?" href="<c:url value='/admin/tag/delete/${tag.id}' />" title="Delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
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
					<a class="btn btn-lg btn-primary" href="<c:url value='/admin/tag/create' />" role="button">Create Tag</a>
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
		<!-- AJAX To Get Tag Articles -->
		<script type="text/javascript" charset="UTF-8">
			$(document).on("click", ".tag-plus", function() {
				event.preventDefault();
				var _this = $(this);
				var _parent = _this.parent('td').parent('tr');
				/*Clear Classes And Possible Previous Results*/
				$("tr.ajax").remove();
				$("tr.ajax-parent").removeClass("ajax-parent");
				$("span.glyphicon-minus").removeClass("glyphicon-minus").addClass("glyphicon-plus");
				$(".tag-minus").not(this).removeClass("tag-minus");
				if(_this.hasClass("tag-minus")) { /*Collapse*/
					_this.removeClass("tag-minus");
					_this.find("span").removeClass("glyphicon-minus").addClass("glyphicon-plus");
					_this.attr("title", "View Articles");
				}
				else { /*Expand*/
					_parent.addClass("ajax-parent");
					_this.addClass("tag-minus");
					_this.find("span").removeClass("glyphicon-plus").addClass("glyphicon-minus");
					_this.attr("title", "Hide Articles");
					$.ajax({
						url: _this.attr('href'),
						success : function(data) {
							var _rows = "";
							var counter = 0;
							$.each(data, function(i, item) {
			                	_rows = _rows.concat("<tr class='ajax'>");
			                	_rows = _rows.concat("<td>&nbsp;</td>");
			                	_rows = _rows.concat("<td colspan='4'><a href='" + item + "' title='View Article' class='pull-right-extra' target='_blank'><span class='glyphicon glyphicon-search' aria-hidden='true'></span></a>" + i + "</td>");
			                	_rows = _rows.concat("<td class='t-narrow hidden-xs'>"); /*Hide On xs Screens*/
			                	_rows = _rows.concat("</tr>");
								counter++;
							});
							if(counter>0) {
								$(_rows).insertAfter(_parent);
							}
							else { /*No Results*/
								$("<tr class='ajax'><td>&nbsp;</td><td colspan='4'>This Tag Has No Articles</td><td class='t-narrow hidden-xs'></tr>").insertAfter(_parent);
							}
			            }
					});
				}
			});
		</script>
	</body>
</html>