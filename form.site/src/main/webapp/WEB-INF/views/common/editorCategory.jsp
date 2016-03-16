<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="editor">
	<c:if test="${not empty param.name and (param.name=='Νίκη Χάγια' or param.name=='Ναταλί Σαϊτάκη' or param.name=='Δέσποινα Δημά' or param.name=='Ιωάννα Μαμάη' or param.name=='Μαριέτα Χριστοπούλου' or param.name=='Μάριον Παλιούρα')}">
		<!-- All work and no play makes Jack a dull boy -->
		<c:choose>
			<c:when test="${param.name=='Νίκη Χάγια'}">
				<c:set var="editorClass" value="xagia" />
			</c:when>
			<c:when test="${param.name=='Ναταλί Σαϊτάκη'}">
				<c:set var="editorClass" value="saitaki" />
			</c:when>
			<c:when test="${param.name=='Δέσποινα Δημά'}">
				<c:set var="editorClass" value="despoina" />
			</c:when>
			<c:when test="${param.name=='Ιωάννα Μαμάη'}">
				<c:set var="editorClass" value="mamai" />
			</c:when>
			<c:when test="${param.name=='Μαριέτα Χριστοπούλου'}">
				<c:set var="editorClass" value="marietta" />
			</c:when>
			<c:when test="${param.name=='Μάριον Παλιούρα'}">
				<c:set var="editorClass" value="marion" />
			</c:when>
			<c:otherwise>
				<c:set var="editorClass" value="" />
			</c:otherwise>
		</c:choose>
		<div class="image ${editorClass}"><span>&raquo;</span> ${param.name}</div>
	</c:if>
</div>