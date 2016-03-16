<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<%--Facebook--%>
<meta property="og:type" content="article">
<meta property="og:url" content="${article.alternate}">
<meta property="og:title" content="${article.title}">
<meta property="og:description" content="${article.leadText}">
<cms:GetRelatedImage var="main" comment="main" version="w620" article="${article}" />
<meta property="og:image" content="${main}">
<%--Twitter--%>
<meta name="twitter:site" content="@ladylikegr">
<meta name="twitter:card" content="summary">
<meta name="twitter:title" content="${article.title}">
<meta name="twitter:description" content="${article.leadText}">
<meta name="twitter:image" content="${main}">