<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default">
	<div class="container-fluid">
    	<!-- Brand and toggle get grouped for better mobile display -->
    	<div class="navbar-header">
      		<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        		<span class="sr-only">Toggle navigation</span>
        		<span class="icon-bar"></span>
        		<span class="icon-bar"></span>
        		<span class="icon-bar"></span>
      		</button>
      		<a class="navbar-brand" href="<c:url value='/admin/'/>">Admin Panel</a>
    	</div>
    	<!-- Collect the nav links, forms, and other content for toggling -->
    	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      		<ul class="nav navbar-nav">
        		<li><a href="<c:url value='/admin/article'/>">Articles</a></li>
        		<li><a href="<c:url value='/admin/author'/>">Authors</a></li>        		
        		<security:authorize access="hasAnyRole('Administrator','Manager')">
	        		<li><a href="<c:url value='/admin/publication'/>">Publications</a></li>
	        		<li><a href="<c:url value='/admin/category'/>">Categories</a></li>
	        		<li><a href="<c:url value='/admin/section'/>">Sections</a></li>
	        		<li><a href="<c:url value='/admin/tag'/>">Tags</a></li>
	        		<li><a href="<c:url value='/admin/feed'/>">Feeds</a></li>
        		</security:authorize>
	        	<security:authorize access="hasRole('Administrator')">
	        		<li><a href="<c:url value='/admin/user'/>">Users</a></li>
	        		<li><a href="<c:url value='/admin/role'/>">Roles</a></li>
	        		<li><a href="<c:url value='/admin/statistics/list'/>">Statistics</a></li>
	      		</security:authorize>
      		</ul>
			<!-- Navbar Right -->
      		<ul class="nav navbar-nav navbar-right">
        		<security:authorize access="isAuthenticated()">
					<p class="navbar-text hidden-xs">
						<span class="glyphicon glyphicon-user"></span>
						<security:authentication property="principal.username" /> [<security:authentication property="principal.role.name" />]
					</p>
					<li><a href="<c:url value='/j_spring_security_logout' />">Logout</a></li>
				</security:authorize>
      		</ul>
 		</div>
	</div>
</nav>