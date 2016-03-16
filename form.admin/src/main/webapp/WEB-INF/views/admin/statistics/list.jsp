<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../head.jsp"/>
		<title>Hibernate Statistics</title>
	</head>
	<body>
		<jsp:include page="../menu.jsp"/>
		<div class="container-fluid">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
  				<li><a href="<c:url value='/admin' />">Home</a></li>
  				<li class="active">Hibernate Statistics</li>
			</ol>
			<!-- Page Header -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>Hibernate Statistics <small>[Start Time <fmt:formatDate value="${startTime}" pattern="dd/MM/yyyy HH:mm"/>]</small></h3>
					</div>				
				</div>
			</div>
			<!-- Statistics -->		
			<div class="row">
				<!-- Alert Messages -->
				<jsp:include page="../alerts.jsp"/>
				<div class="col-xs-12">
					<div class="table-responsive">
						<table class="table table-striped">
							<tr>
								<td>Connections Obtained</td>
								<td class="text-center t-wider">${statistics.connectCount}</td>
							</tr>
							<tr>
								<td>Sessions Opened</td>
								<td class="text-center t-wider">${statistics.sessionOpenCount}</td>
							</tr>
							<tr>
								<td>Sessions Closed</td>
								<td class="text-center t-wider">${statistics.sessionCloseCount}</td>
							</tr>
							<tr>
								<td>Transactions</td>
								<td class="text-center t-wider">${statistics.transactionCount}</td>
							</tr>
							<tr>
								<td>Successful Transactions</td>
								<td class="text-center t-wider">${statistics.successfulTransactionCount}</td>
							</tr>
							<tr>
								<td>Optimistic Lock Failures</td>
								<td class="text-center t-wider">${statistics.optimisticFailureCount}</td>
							</tr>
							<tr>
								<td>Session Flushes [Either By Client Code Or By Hibernate]</td>
								<td class="text-center t-wider">${statistics.flushCount}</td>
							</tr>
							<tr>
								<td>Statements Prepared</td>
								<td class="text-center t-wider">${statistics.prepareStatementCount}</td>
							</tr>
							<tr>
								<td>Statements Closed</td>
								<td class="text-center t-wider">${statistics.closeStatementCount}</td>
							</tr>
							<!-- Objects Statistics -->
							<tr class="info"><td colspan="2"><strong>Objects</strong></td></tr>
							<tr>
								<td>Entities Loaded</td>
								<td class="text-center t-wider">${statistics.entityLoadCount}</td>
							</tr>
							<tr>
								<td>Entities Updated</td>
								<td class="text-center t-wider">${statistics.entityUpdateCount}</td>
							</tr>
							<tr>
								<td>Entities Inserted</td>
								<td class="text-center t-wider">${statistics.entityInsertCount}</td>
							</tr>
							<tr>
								<td>Entities Deleted</td>
								<td class="text-center t-wider">${statistics.entityDeleteCount}</td>
							</tr>
							<tr>
								<td>Entities Fetched</td>
								<td class="text-center t-wider">${statistics.entityFetchCount}</td>
							</tr>
							<!-- Collections Statistics -->
							<tr class="info"><td colspan="2"><strong>Collections Of Objects</strong></td></tr>
							<tr>
								<td>Collections Fetched</td>
								<td class="text-center t-wider">${statistics.collectionFetchCount}</td>
							</tr>
							<tr>
								<td>Collections Loaded</td>
								<td class="text-center t-wider">${statistics.collectionLoadCount}</td>
							</tr>
							<tr>
								<td>Collections Updated</td>
								<td class="text-center t-wider">${statistics.collectionUpdateCount}</td>
							</tr>
							<tr>
								<td>Collections Removed</td>
								<td class="text-center t-wider">${statistics.collectionRemoveCount}</td>
							</tr>
							<tr>
								<td>Collections Recreated (Rebuilt)</td>
								<td class="text-center t-wider">${statistics.collectionRecreateCount}</td>
							</tr>
							<!-- Second Level Cache Statistics -->
							<tr class="info"><td colspan="2"><strong>Second Level Cache <em>[ Total Statistics For All Regions ]</em></strong></td></tr>
							<tr>
								<td>Second Level Cache Puts</td>
								<td class="text-center t-wider">${statistics.secondLevelCachePutCount}</td>
							</tr>
							<tr>
								<td>Second Level Cache Hits</td>
								<td class="text-center t-wider">${statistics.secondLevelCacheHitCount}</td>
							</tr>
							<tr>
								<td>Second Level Cache Misses</td>
								<td class="text-center t-wider">${statistics.secondLevelCacheMissCount}</td>
							</tr>
							<tr>
								<td>Second Level Cache Hits (%)</td>
								<td class="text-center t-wider">
									<c:choose>
										<c:when test="${statistics.secondLevelCacheMissCount+statistics.secondLevelCacheHitCount!=0}">
											<c:set var="percent" value="${(100 * statistics.secondLevelCacheHitCount)/(statistics.secondLevelCacheMissCount+statistics.secondLevelCacheHitCount)}"/>
											${percent-(percent%1)} %
										</c:when>
										<c:otherwise>N/A</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<!-- Second Level Cache Elements -->
							<tr class="active">
								<td><em>Second Level Cache Elements In Memory</em></td>
								<td class="text-center t-wider"><em>${elementsCountInMemory}</em></td>
							</tr>
							<tr class="active">
								<td><em>Second Level Cache Size In Memory</em></td>
								<td class="text-center t-wider">
									<c:set var="cacheSize" value="${elementsSizeInMemory/1024}"/>
	    							<em>${cacheSize-(cacheSize%1)} Kb</em>
								</td>
							</tr>
							<tr class="active">
								<td><em>Second Level Cache Elements On Disk</em></td>
								<td class="text-center t-wider"><em>${elementsCountOnDisk}</em></td>
							</tr>
							<!-- Query Statistics -->
							<tr class="info"><td colspan="2"><strong>Queries + Query Cache</strong></td></tr>
							<tr>
								<td>Queries Executed</td>
								<td class="text-center t-wider">${statistics.queryExecutionCount}</td>
							</tr>
							<tr>
								<td>Query Cache Puts</td>
								<td class="text-center t-wider">${statistics.queryCachePutCount}</td>
							</tr>
							<tr>
								<td>Query Cache Hits</td>
								<td class="text-center t-wider">${statistics.queryCacheHitCount}</td>
							</tr>
							<tr>
								<td>Query Cache Misses</td>
								<td class="text-center t-wider">${statistics.queryCacheMissCount}</td>
							</tr>
							<tr>
								<td>Query Cache Hits (%)</td>
								<td class="text-center t-wider">
									<c:choose>
										<c:when test="${statistics.queryCacheHitCount+statistics.queryCacheMissCount!=0}">
											<c:set var="percent" value="${(100 * statistics.queryCacheHitCount)/(statistics.queryCacheHitCount+statistics.queryCacheMissCount)}"/>
											${percent-(percent%1)} %
										</c:when>
										<c:otherwise>N/A</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td>Query Execution Maximum Time</td>
								<td class="text-center t-wider">${statistics.queryExecutionMaxTime} ms</td>
							</tr>
						</table>
					</div>
					<!-- Query With Maximum Execution Time -->
					<div class="well well-sm">
						<h4>Query With Maximum Execution Time</h4>
						<p>${queryExecutionMaxTimeQueryString}</p>
					</div>
				</div>
			</div>
			<!-- Statistics Per Region -->
			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h3>Second Level Cache Statistics Per Region <small>[Start Time <fmt:formatDate value="${startTime}" pattern="dd/MM/yyyy HH:mm"/>]</small></h3>
					</div>				
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<c:forEach items="${regionStatistics}" var="region">
    					<table class="table table-striped">
    						<tr class="info"><td colspan="2"><strong>${region.key}</strong></td></tr>
    						<tr>
    							<td>Cache Puts</td>
    							<td class="text-center t-wider">${region.value.putCount}</td>
    						</tr>
    						<tr>
    							<td>Cache Hits</td>
    							<td class="text-center t-wider">${region.value.hitCount}</td>
    						</tr>
    						<tr>
    							<td>Cache Misses</td>
    							<td class="text-center t-wider">${region.value.missCount}</td>
    						</tr>
    						<tr>
    							<td>Elements In Memory</td>
    							<td class="text-center t-wider">${region.value.elementCountInMemory}</td>
    						</tr>
    						<tr>
    							<td>Size In Memory</td>
    							<td class="text-center t-wider">
    								<c:set var="regionSize" value="${region.value.sizeInMemory/1024}"/>
    								${regionSize-(regionSize%1)} Kb
    							</td>
    						</tr>
    						<tr>
    							<td>Elements On Disk</td>
    							<td class="text-center t-wider">${region.value.elementCountOnDisk}</td>
    						</tr>
    					</table>
					</c:forEach>
				</div>
			</div>
    	 	<!-- Pagination And Extra Actions -->
			<div class="row">
				<div class="col-xs-12 col-md-6">
					<a class="btn btn-lg btn-primary confirm-dialog" dialog="Are You Sure You Want To Delete Current Hibernate Statistics?" href="<c:url value='/admin/statistics/clear' />" role="button">Delete Statistics</a>
					<a class="btn btn-lg btn-danger confirm-dialog" dialog="Are You Sure You Want To Delete Hibernate Cache?" href="<c:url value='/admin/cache/clear' />" role="button">Delete Cache</a>
				</div>
				<div class="col-xs-12 col-md-6">
					<!-- Nothing For Now -->
				</div>
			</div>
		</div><!-- container-fluid -->
		<jsp:include page="../footer.jsp"/>	
		<!-- BootBox Confirm Dialogs -->
		<script src="<c:url value='/static/admin/bootbox-4.4.0/bootbox.min.js' />"></script>
		<script src="<c:url value='/static/admin/admin.js' />"></script>
    </body>
</html>