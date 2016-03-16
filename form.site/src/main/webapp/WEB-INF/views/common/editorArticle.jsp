<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<div class="editor">
	<c:if test="${not empty param.name and (param.name=='Σάββια Σιάτη' or param.name=='Νίκη Χάγια' or param.name=='Ναταλί Σαϊτάκη' or param.name=='Δέσποινα Δημά' or param.name=='Ιωάννα Μαμάη' or param.name=='Μαριέτα Χριστοπούλου' or param.name=='Μάριον Παλιούρα')}">
		<c:choose>
			<c:when test="${param.name=='Σάββια Σιάτη'}">
				<c:set var="src" value="/static/ladylike/images/xs/siati.category.png" />
				<c:set var="opinions_byline" value="true" scope="request"/>
                <c:set var="editorClass" value="siati" />                
			</c:when>
			<c:when test="${param.name=='Νίκη Χάγια'}">
				<c:set var="src" value="/static/ladylike/images/xs/xagia.category.png" />
				<c:set var="opinions_byline" value="true" scope="request"/>
                <c:set var="editorClass" value="xagia" />                
			</c:when>
			<c:when test="${param.name=='Ναταλί Σαϊτάκη'}">
				<c:set var="src" value="/static/ladylike/images/xs/saitaki.category.png" />
                <c:set var="opinions_byline" value="true" scope="request"/>
                <c:set var="editorClass" value="saitaki" />
			</c:when>
			<c:when test="${param.name=='Δέσποινα Δημά'}">
				<c:set var="src" value="/static/ladylike/images/xs/despoina.category.png" />
                <c:set var="opinions_byline" value="true" scope="request"/>
                <c:set var="editorClass" value="despoina" />
			</c:when>
			<c:when test="${param.name=='Ιωάννα Μαμάη'}">
				<c:set var="src" value="/static/ladylike/images/xs/mamai.category.png" />
                <c:set var="opinions_byline" value="true" scope="request"/>
                <c:set var="editorClass" value="mamai" />
			</c:when>
			<c:when test="${param.name=='Μαριέτα Χριστοπούλου'}">
				<c:set var="src" value="/static/ladylike/images/xs/marietta.category.png" />
                <c:set var="opinions_byline" value="true" scope="request"/>
                <c:set var="editorClass" value="marietta" />
			</c:when>
			<c:when test="${param.name=='Μάριον Παλιούρα'}">
				<c:set var="src" value="/static/ladylike/images/xs/marion.category.png" />
                <c:set var="opinions_byline" value="true"  scope="request"/>
                <c:set var="editorClass" value="marion" />
			</c:when>
			<c:otherwise>
				<c:set var="opinions_byline" value="false" scope="request"/>
			</c:otherwise>
		</c:choose>
        <div class="img ${editorClass}">
			<div class="auth_cont">
        		<a href="/articles/opinions/${param.section}/">
        			<span class="pointer">&raquo;</span>
                	<span class="name">${param.name}</span>
                </a>
                <c:remove var="name"/>
			</div>
        </div>
        <div class="lineborder"></div>
	</c:if>
</div>