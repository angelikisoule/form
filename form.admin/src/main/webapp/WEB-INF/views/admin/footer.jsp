<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-default navbar-fixed-bottom">
  <div class="container-fluid">
  	<p class="navbar-text">
  		Copyright &copy; 24MEDIA
    </p>
  </div>
</nav>

<!-- Google's Hosted jQuery With Fallback -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>window.jQuery || document.write("<script src='/static/admin/jquery-1.11.2/jquery.min.js'><\/script>")</script>

<!-- Bootstrap JS -->
<script src="<c:url value='/static/admin/bootstrap/js/bootstrap.min.js' />"></script>