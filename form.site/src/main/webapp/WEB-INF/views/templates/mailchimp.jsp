<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="/WEB-INF/tlds/cms.tld" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="http://ladylike.gr/favicon.ico">
    <meta name="google-site-verification" content="aYd9O26y3HErjkhlqCMI_voUTZY9NG0e5USMgKIhUNE" />
    <style>
    	/*
    	 * Most Of The Styles Are Hardcoded Due To Email Client Compatibility Issues
    	 * TABLE widths and heights, and Borders As TDs with width or height = 1 Are Critical 
    	 */
		body {
			min-width:600px!important;
			background-color:#DDDDDD;
		}
		table {
			border-collapse:collapse;
			border-spacing:0;
			background-color:#FFFFFF;
			empty-cells:show;
		}
		.container {
			margin-left:auto;
			margin-right:auto;
		}
		td {
			padding:0;
		}
		a {
			text-decoration:none;
			color:black;
		}
		img {
			display:block;
			border:none;
			outline:none;
		}
		p {
			margin:0!important;
		}
		/*** Font Styles ***/
		a, p {
			font-family:Verdana, Geneva, sans-serif !important;
		}
		.topStories.main .title a {
			font-size:30px;
			line-height:29px;
		}
		.topStories.articles .title a {
			font-size:20px;
			line-height:22px;
		}
		.opinion .author a {
			font-size:24px;
			line-height:36px;
			font-weight:bold;
			color:white;
		}
		.opinion .title a {
			font-size:20px;
			line-height:22px;
			color:white;
		}	
		.likestyle .title a {
			font-size:20px;
			line-height:22px;
		}	
		.recipe .title a {
			font-size:32px;
			line-height:34px;
		}
		.quiz .banner {
			font-size:60px;
			line-height:55px;
			color:white;
		}
		.quiz .title a {
			font-size:20px;
			line-height:22px;
			color:white;
		}
		.readmore .title a {
			font-size:20px;
			line-height:22px;
		}
		.copyright a {
			font-size:18px;
			color:#B4B4B4;
		}
		.summary, p {
			font-size:13px;
			line-height:16px;
		}
		/*** Styles Per Section ***/
		.topStories.articles .summary, .likestyle .summary, .likestyle .readit, .readmore .summary {
			padding-top:10px;
		}
		.quiz .title {
			padding-top:15px;
		}
		.recipe .banner {
			padding-left:15px;
			padding-top:3px;
		}
		.topStories.main .title, .topStories.articles .title {
			padding-top:20px;
		}
		.opinion .author {
			padding-top:30px;
		}
		.opinion table {
			background-color:#05CB98;
		}
		.quiz table {
			background-color:#9E0039;
			color:white;
		}
    </style>
    <meta name="Robots" content="noarchive">
    <meta name="Googlebot" content="noarchive">
    <title>Ladylike.gr | Celebrities, Μόδα, Ομορφιά, Σινεμά, Τηλεόραση, Παιδί, Fitness</title>
    <meta name="description" content="Ladylike.gr | Celebrities, Μόδα, Ομορφιά, Σινεμά, Τηλεόραση, Παιδί, Fitness">
</head>
<body data-twttr-rendered="true">
	<table width="600" border="0" align="center" cellpadding="0" cellspacing="0" class="container">
  		<!-- 
  		 * Ladylike Banner
  		-->
  		<tr><td height="5" colspan="3" align="center" valign="middle" style="background-color:#DDDDDD;"></td></tr><!-- Divider -->
  		<tr><td height="10" colspan="3"></td></tr><!-- Divider -->
  		<tr>
	  		<td width="20" height="150"></td>
    		<td width="560" height="150" align="center" valign="top">
		    	<a href="http://www.ladylike.gr">
					<img width="560" src="http://www.ladylike.gr/incoming/article3057894.ece/BINARY/original/nwsl-header.png" alt="Ladylike Banner" />
				</a>
    		</td>
    		<td width="20" height="150"></td>
  		</tr>
   		<tr><td height="10" colspan="3"></td></tr><!-- Divider -->
  		<!-- 
  		 * TopStories
  		-->
  		<cms:SectionService maxItems="3" id="articles" articleTypes="STORY,ADVERTORIAL,PHOTOSTORY" name="newsletter@topStories1" />
		<c:forEach begin="0" end="0" items="${articles}" var="art">
			<cms:GetRelatedImage version="w460" article="${art}" var="imgURL"/>
	 		<c:choose>
		 		<c:when test="${not empty imgURL}">
					<c:set var="imageURL" value="${imgURL}" />
			  	</c:when>
			  	<c:otherwise>
			  		<c:set var="imageURL" value="${defaultImage}" />
			  	</c:otherwise>
	 		</c:choose>
		  	<tr class="topStories main">
				<td width="20"></td>
		    	<td width="560" align="center" valign="top">
		    		<a href="${art.alternate}">
		    			<img width="560" src="${imageURL}" alt="TopStories Main Image" />
		    		</a>
		    	</td>
		    	<td width="20"></td>
		  	</tr>
		   	<tr class="topStories main">
		   		<td width="20"></td>
		    	<td width="560" align="left" valign="top" class="title">
		    		<a href="${art.alternate}">${art.title}</a>
	        	</td>
		    	<td width="20"></td>
		 	</tr>
		</c:forEach>
		<tr><td width="600" height="20" colspan="3"></td></tr><!-- Divider -->
  		<tr class="topStories articles">
    		<td width="20"></td>
    		<td width="560" align="left" valign="top">
	    		<table border="0" align="left" cellpadding="0" cellspacing="0">
	    			<tr>
			    		<c:forEach begin="1" end="2" items="${articles}" var="art" varStatus="loop">
							<cms:GetRelatedImage version="w300" article="${art}" var="imgURL"/>
							<c:choose>
								<c:when test="${not empty imgURL}">
			    					<c:set var="imageURL" value="${imgURL}" />
			    				</c:when>
			    				<c:otherwise>
			    					<c:set var="imageURL" value="${defaultImage}" />
			    				</c:otherwise>
							</c:choose>
			    			<td width="270" align="left" valign="top">
		        				<a href="${art.alternate}">
		                      		<img width="270" src="${imageURL}" alt="TopStories Article Image" />
		                       	</a>
		        			</td>
		    				<c:if test="${loop.index eq 1}">
		    					<td rowspan="3" width="20"></td>
			    			</c:if>
			    		</c:forEach>
	       			</tr>
	      			<tr>
			        	<c:forEach begin="1" end="2" items="${articles}" var="art">
		    				<td align="left" valign="top" class="title">
		    					<a href="${art.alternate}">${art.title}</a>
					            <p class="summary">${art.leadText}</p>
		        			</td>
			    		</c:forEach>
	    			</tr>
	    		</table>
    		</td>
    		<td width="20"></td>
  		</tr>  
		<tr><td height="30" colspan="3"></td></tr><!-- Divider -->
		<!--
		 * Opinion
		-->
		<cms:SectionService maxItems="1" id="articles" articleTypes="STORY,ADVERTORIAL" name="newsletter@main1" />
		<c:forEach begin="0" end="0" items="${articles}" var="art">
	  		<tr class="opinion">
	    		<td width="600" colspan="3" align="center" valign="middle">
	    			<table width="560" height="180" border="0" align="center" cellpadding="0" cellspacing="0">
	      				<tr>
	      					<td width="20"></td>
        				  	<td rowspan="2" width="110" height="165" align="center" valign="middle">
	        					<img width="110" height="165" src="http://www.ladylike.gr/incoming/article3045044.ece/BINARY/original/nwsl-author.png" alt="Opinion Image" />
	        				</td>
        				  	<td width="20"></td>
	        				<td width="390" height="45" align="left" valign="top" class="author">		
	               				<c:forEach var="author" items="${art.authors}" begin="0" end="0">
	               					<a href="${art.alternate}">${author.name}</a>
	               				</c:forEach>
	               			</td>
	               			<td width="20"></td>
	      				</tr>
	      				<tr>
      					  	<td width="20"></td>
      					  	<td width="20"></td>
	        				<td width="390" align="left" valign="top" class="title">
	        					<a href="${art.alternate}">${art.title}</a>
			      			</td>
			      			<td width="20"></td>
	      				</tr>
	    			</table>
	    		</td>
	  		</tr>
		</c:forEach>
		<tr><td height="30" colspan="3"></td></tr><!-- Divider -->
  		<!--
  		 * Likestyle
  		-->
	    <tr class="likestyle">
	   		<td width="20"></td>
	      	<td width="560" align="center" valign="top">
				<table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
		  			<tr><!--Likestyle Banner -->
					    <td width="1" height="21"></td>
					    <td width="19" height="21"></td>
					    <td width="130" height="21"></td>
					    <td width="260" rowspan="3" align="center" valign="middle">
					    	<img src="http://www.ladylike.gr/incoming/article3056121.ece/BINARY/original/likestyle.png" alt="Likestyle Banner" />
					    </td>
					    <td width="130" height="21"></td>
					    <td width="19" height="21"></td>
					    <td width="1" height="21"></td>
		  			</tr>
		  			<tr>  
					    <td width="1" height="1" style="background-color:#000000;"></td>
					    <td width="19" height="1" style="background-color:#000000;"></td>
					    <td width="130" height="1" style="background-color:#000000;"></td>
					    <td width="130" height="1" style="background-color:#000000;"></td>
					    <td width="19" height="1" style="background-color:#000000;"></td>
					    <td width="1" height="1" style="background-color:#000000;"></td>
		  			</tr>
		  			<tr>
					    <td width="1" height="20" style="background-color:#000000;"></td>
					    <td width="19" height="20"></td>
					    <td width="130" height="20"></td>
					    <td width="130" height="20"></td>
					    <td width="19" height="20"></td>
					    <td width="1" height="20" style="background-color:#000000;"></td>
		  			</tr>
		  			<tr><!-- Divider -->
					    <td height="30" width="1" style="background-color:#000000;"></td>
					    <td height="30" width="558" colspan="5"></td>
					    <td height="30" width="1" style="background-color:#000000;"></td>
		  			</tr>
					<!-- START OF : Living -->
					<cms:SectionService maxItems="1" id="articles" articleTypes="STORY,ADVERTORIAL" name="newsletter@main2" />
   		      		<c:forEach begin="0" end="0" items="${articles}" var="art">
						<cms:GetRelatedImage version="w300" article="${art}" var="imgURL"/>
						<tr>
			    			<td width="1" style="background-color:#000000;"></td>
			    			<td width="19"></td>
			    			<td width="518" colspan="3">
			      				<table width="518" border="0" align="center" cellpadding="0" cellspacing="0">
									<!--START OF : Living Banner -->
									<tr>
										<td width="1" height="20"></td>
									  	<td width="9" height="20"></td>
									  	<td width="86" height="20"></td>
									  	<td width="326" rowspan="3" align="center" valign="middle">
									  		<img src="http://www.ladylike.gr/incoming/article3044988.ece/BINARY/original/nws-living.png" alt="Living Banner" />
									  	</td>
									  	<td width="86" height="20"></td>
									  	<td width="9" height="20"></td>
									  	<td width="1" height="20"></td>
									</tr>
									<tr>  
				  						<td width="1" height="1" style="background-color:#DCDCDC;"></td>
										<td width="9" height="1" style="background-color:#DCDCDC;"></td>
										<td width="86" height="1" style="background-color:#DCDCDC;"></td>
										<td width="86" height="1" style="background-color:#DCDCDC;"></td>
										<td width="9" height="1" style="background-color:#DCDCDC;"></td>
										<td width="1" height="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr>
				  						<td width="1" height="26" style="background-color:#DCDCDC;"></td>
				  						<td width="9" height="26"></td>
				  						<td width="86" height="26"></td>
									  	<td width="86" height="26"></td>
									  	<td width="9" height="26"></td>
									  	<td width="1" height="26" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr><!-- Divider -->
								  		<td height="30" width="1" style="background-color:#DCDCDC;"></td>
								  		<td height="30" width="516" colspan="5"></td>
								  		<td height="30" width="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<!-- END OF : Living Banner -->
									<tr>
				  						<td width="1" style="background-color:#DCDCDC;"></td>
				  						<td width="9"></td>
				  						<td width="498" colspan="3">
				    						<table width="498" border="0" align="center" cellpadding="0" cellspacing="0">
				      							<tr>
													<td width="240">
														<c:choose>
															<c:when test="${not empty imgURL}">
																<c:set var="imageURL" value="${imgURL}" />
	    		            								</c:when>
	    		            								<c:otherwise>
	    		            									<c:set var="imageURL" value="${defaultImage}" />
	    		            								</c:otherwise>
														</c:choose>
   		            									<a href="${art.alternate}">
   		            										<img width="240" src="${imageURL}" alt="Living Article" />
   		            									</a>
													</td>
													<td width="9"></td>
													<td width="249" align="left" valign="top">
					  									<p class="title">
						    								<a href="${art.alternate}">${art.title}</a>	
					  									</p>
					  									<p class="summary">${art.leadText}</p>
					  									<p>
					    									<a href="${art.alternate}">
					      										<img class="readit" src="http://www.ladylike.gr/incoming/article3046174.ece/BINARY/original/readit.png" alt="Read It" />
					    									</a>
					  									</p>
													</td>
				      							</tr>
				    						</table>
				    					</td>
				    					<td width="9"></td>
				    					<td width="1" style="background-color:#DCDCDC;"></td>
				  					</tr>
				  					<tr><!-- Divider -->
				    					<td height="10" width="1" style="background-color:#DCDCDC;"></td>
				    					<td height="10" width="496" colspan="5"></td>
				    					<td height="10" width="1" style="background-color:#DCDCDC;"></td>
				  					</tr>
				  					<tr><td width="498" height="1" colspan="7" style="background-color:#DCDCDC;"></td></tr>
								</table>
			      			</td>
			    			<td width="19"></td>
			    			<td width="1" style="background-color:#000000;"></td>
			  			</tr>
			  			<tr><!-- Divider -->
			    			<td height="20" width="1" style="background-color:#000000;"></td>
			    			<td height="20" width="558" colspan="5"></td>
			    			<td height="20" width="1" style="background-color:#000000;"></td>
			  			</tr>
			  		</c:forEach>
		  			<!-- END OF : Living -->
				  	<!-- START OF : Fashion -->
		  			<cms:SectionService maxItems="1" id="articles" articleTypes="STORY,ADVERTORIAL" name="newsletter@main3" />
    		      	<c:forEach begin="0" end="0" items="${articles}" var="art">
    		        	<cms:GetRelatedImage version="w300" article="${art}" var="imgURL"/>
			  			<tr>
			    			<td width="1" style="background-color:#000000;"></td>
			    			<td width="19"></td>
			    			<td width="518" colspan="3">
			      				<table width="518" border="0" align="center" cellpadding="0" cellspacing="0">
									<!--START OF : Fashion Banner -->
									<tr>
								  		<td width="1" height="20"></td>
								  		<td width="9" height="20"></td>
								  		<td width="86" height="20"></td>
								  		<td width="326" rowspan="3" align="center" valign="middle">
								  			<img src="http://www.ladylike.gr/incoming/article3044995.ece/BINARY/original/nwsl-fashion.png" alt="Fashion Banner" />
								  		</td>
								  		<td width="86" height="20"></td>
								  		<td width="9" height="20"></td>
								  		<td width="1" height="20"></td>
									</tr>
									<tr>  
								  		<td width="1" height="1" style="background-color:#DCDCDC;"></td>
								  		<td width="9" height="1" style="background-color:#DCDCDC;"></td>
								  		<td width="86" height="1" style="background-color:#DCDCDC;"></td>
								  		<td width="86" height="1" style="background-color:#DCDCDC;"></td>
								  		<td width="9" height="1" style="background-color:#DCDCDC;"></td>
								  		<td width="1" height="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr>
				  						<td width="1" height="26" style="background-color:#DCDCDC;"></td>
									  	<td width="9" height="26"></td>
									  	<td width="86" height="26"></td>
									  	<td width="86" height="26"></td>
									  	<td width="9" height="26"></td>
									  	<td width="1" height="26" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr><!-- Divider -->
				  						<td height="30" width="1" style="background-color:#DCDCDC;"></td>
				  						<td height="30" width="516" colspan="5"></td>
				  						<td height="30" width="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<!-- END OF : Fashion Banner -->
									<tr>
				  						<td width="1" style="background-color:#DCDCDC;"></td>
				 	 					<td width="9"></td>
				  						<td width="498" colspan="3">
				    						<table width="498" border="0" align="center" cellpadding="0" cellspacing="0">
				      							<tr>
													<td width="240">
					  									<c:choose>
					  										<c:when test="${not empty imgURL}">
					  											<c:set var="imageURL" value="${imgURL}" />
					  										</c:when>
					  										<c:otherwise>
						  										<c:set var="imageURL" value="${defaultImage}" />
					  										</c:otherwise>
					  									</c:choose>	
					  									<a href="${art.alternate}">
   		            										<img width="240" src="${imageURL}" alt="Fashion Article" />
   		            									</a>
   		            								</td>
													<td width="9"></td>
													<td width="249" align="left" valign="top">
					  									<p class="title">
					  										<a href="${art.alternate}">${art.title}</a>
					    								</p>
													  	<p class="summary">${art.leadText}</p>
													    <p>
													    	<a href="${art.alternate}">
													      		<img class="readit" src="http://www.ladylike.gr/incoming/article3046174.ece/BINARY/original/readit.png" alt="Read It" />
													    	</a>
													  	</p>
													</td>
				      							</tr>
				    						</table>
				    					</td>
				    					<td width="9"></td>
				    					<td width="1" style="background-color:#DCDCDC;"></td>
				  					</tr>
				  					<tr><!-- Divider -->
				    					<td height="10" width="1" style="background-color:#DCDCDC;"></td>
				    					<td height="10" width="496" colspan="5"></td>
				    					<td height="10" width="1" style="background-color:#DCDCDC;"></td>
				  					</tr>
				  					<tr><td width="498" height="1" colspan="7" style="background-color:#DCDCDC;"></td></tr>
								</table>
			      			</td>
			    			<td width="19"></td>
			    			<td width="1" style="background-color:#000000;"></td>
			  			</tr>
			  			<tr><!-- Divider -->
			    			<td height="20" width="1" style="background-color:#000000;"></td>
			    			<td height="20" width="558" colspan="5"></td>
			    			<td height="20" width="1" style="background-color:#000000;"></td>
			  			</tr>
		  			</c:forEach>
			  		<!-- END OF : Fashion -->
				  	<!-- START OF : Pop Culture -->
		  			<cms:SectionService maxItems="1" id="articles" articleTypes="STORY,ADVERTORIAL" name="newsletter@main4" />
    		      	<c:forEach begin="0" end="0" items="${articles}" var="art">
    		        	<cms:GetRelatedImage version="w300" article="${art}" var="imgURL"/>
			  			<tr>
			    			<td width="1" style="background-color:#000000;"></td>
			    			<td width="19"></td>
			    			<td width="518" colspan="3">
			      				<table width="518" border="0" align="center" cellpadding="0" cellspacing="0">
									<!--START OF : Pop Culture Banner -->
									<tr>
										<td width="1" height="20"></td>
									  	<td width="9" height="20"></td>
									  	<td width="86" height="20"></td>
									  	<td width="326" rowspan="3" align="center" valign="middle">
									  		<img src="http://www.ladylike.gr/incoming/article3044997.ece/BINARY/original/nwsl-culture.png" alt="Pop Culture Banner" />
									  	</td>
									  	<td width="86" height="20"></td>
									  	<td width="9" height="20"></td>
									  	<td width="1" height="20"></td>
									</tr>
									<tr>
									  	<td width="1" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="9" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="86" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="86" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="9" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="1" height="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr>
									  	<td width="1" height="26" style="background-color:#DCDCDC;"></td>
									  	<td width="9" height="26"></td>
									  	<td width="86" height="26"></td>
									  	<td width="86" height="26"></td>
									  	<td width="9" height="26"></td>
									  	<td width="1" height="26" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr><!-- Divider -->
				  						<td height="30" width="1" style="background-color:#DCDCDC;"></td>
				  						<td height="30" width="516" colspan="5"></td>
				  						<td height="30" width="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<!-- END OF : Pop Culture Banner -->
									<tr>
				  						<td width="1" style="background-color:#DCDCDC;"></td>
				  						<td width="9"></td>
				  						<td width="498" colspan="3">
				    						<table width="498" border="0" align="center" cellpadding="0" cellspacing="0">
				      							<tr>
													<td width="240">
														<c:choose>
													  		<c:when test="${not empty imgURL}">
																<c:set var="imageURL" value="${imgURL}" />
	    		            								</c:when>
	    		            								<c:otherwise>
	    		            									<c:set var="imageURL" value="${defaultImage}" />
	    		            								</c:otherwise>
    		            								</c:choose>
   		            									<a href="${art.alternate}">
   		            										<img width="240" src="${imageURL}" alt="Pop Culture Article" />
   		            									</a>
													</td>
													<td width="9"></td>
													<td width="249" align="left" valign="top">
											  			<p class="title">
											    			<a href="${art.alternate}">${art.title}</a>
											  			</p>
											  			<p class="summary">${art.leadText}</p>
											  			<p>
											    			<a href="${art.alternate}">
											      				<img class="readit" src="http://www.ladylike.gr/incoming/article3046174.ece/BINARY/original/readit.png" alt="Read It" />
											    			</a>
											  			</p>
													</td>
				      							</tr>
				    						</table>
				    					</td>
				    					<td width="9"></td>
				    					<td width="1" style="background-color:#DCDCDC;"></td>
				  					</tr>
				  					<tr><!-- Divider -->
								    	<td height="10" width="1" style="background-color:#DCDCDC;"></td>
								    	<td height="10" width="496" colspan="5"></td>
								    	<td height="10" width="1" style="background-color:#DCDCDC;"></td>
								  	</tr>
				  					<tr><td width="498" height="1" colspan="7" style="background-color:#DCDCDC;"></td></tr>
								</table>
			      			</td>
			    			<td width="19"></td>
			    			<td width="1" style="background-color:#000000;"></td>
			  			</tr>
					  	<tr><!-- Divider -->
					   		<td height="20" width="1" style="background-color:#000000;"></td>
					    	<td height="20" width="558" colspan="5"></td>
					    	<td height="20" width="1" style="background-color:#000000;"></td>
					  	</tr>
				  	</c:forEach>
		  			<!-- END OF : Pop Culture -->
				  	<!-- START OF : Shopping List -->
		  			<cms:SectionService maxItems="1" id="articles" articleTypes="STORY,ADVERTORIAL" name="newsletter@main8" />
    		      	<c:forEach begin="0" end="0" items="${articles}" var="art">
    		        	<cms:GetRelatedImage version="w300" article="${art}" var="imgURL"/>
			  			<tr>
			    			<td width="1" style="background-color:#000000;"></td>
			    			<td width="19"></td>
			    			<td width="518" colspan="3">
			      				<table width="518" border="0" align="center" cellpadding="0" cellspacing="0">
									<!--START OF : Shopping List Banner -->
									<tr>
										<td width="1" height="20"></td>
									  	<td width="9" height="20"></td>
									  	<td width="86" height="20"></td>
									  	<td width="326" rowspan="3" align="center" valign="middle">
									  		<img src="http://www.ladylike.gr/incoming/article3110690.ece/BINARY/original/shopping-ribon.png" alt="Shopping List Banner" />
									  	</td>
									  	<td width="86" height="20"></td>
									  	<td width="9" height="20"></td>
									  	<td width="1" height="20"></td>
									</tr>
									<tr>
									  	<td width="1" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="9" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="86" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="86" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="9" height="1" style="background-color:#DCDCDC;"></td>
									  	<td width="1" height="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr>
									  	<td width="1" height="26" style="background-color:#DCDCDC;"></td>
									  	<td width="9" height="26"></td>
									  	<td width="86" height="26"></td>
									  	<td width="86" height="26"></td>
									  	<td width="9" height="26"></td>
									  	<td width="1" height="26" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr><!-- Divider -->
				  						<td height="30" width="1" style="background-color:#DCDCDC;"></td>
				  						<td height="30" width="516" colspan="5"></td>
				  						<td height="30" width="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<!-- END OF : Shopping List Banner -->
									<tr>
				  						<td width="1" style="background-color:#DCDCDC;"></td>
				  						<td width="9"></td>
				  						<td width="498" colspan="3">
				    						<table width="498" border="0" align="center" cellpadding="0" cellspacing="0">
				      							<tr>
													<td width="240">
												  		<c:choose>
												  			<c:when test="${not empty imgURL}">
    		            										<c:set var="imageURL" value="${imgURL}" />
    		            									</c:when>
    		            									<c:otherwise>
    		            										<c:set var="imageURL" value="${defaultImage}" />
    		            									</c:otherwise>
    		            								</c:choose>
   		            									<a href="${art.alternate}">
   		            										<img width="240" src="${imageURL}" alt="Shopping List Article" />
   		            									</a>
													</td>
													<td width="9"></td>
													<td width="249" align="left" valign="top">
											  			<p class="title">
											    			<a href="${art.alternate}">${art.title}</a>
											  			</p>
										  				<c:choose>
										  					<c:when test="${not empty art.leadText}">
										  						<p class="summary">${art.leadText}</p>
										  					</c:when>
										  					<c:otherwise>
										  						${art.body}
										  					</c:otherwise>
										  				</c:choose>
											  			<p>
											    			<a href="${art.alternate}">
											      				<img class="readit" src="http://www.ladylike.gr/incoming/article3110696.ece/BINARY/original/buy-it-btn.png" alt="Read It" />
											    			</a>
											  			</p>
													</td>
				      							</tr>
				    						</table>
				    					</td>
				    					<td width="9"></td>
				    					<td width="1" style="background-color:#DCDCDC;"></td>
				  					</tr>
				  					<tr><!-- Divider -->
								    	<td height="10" width="1" style="background-color:#DCDCDC;"></td>
								    	<td height="10" width="496" colspan="5"></td>
								    	<td height="10" width="1" style="background-color:#DCDCDC;"></td>
								  	</tr>
				  					<tr><td width="498" height="1" colspan="7" style="background-color:#DCDCDC;"></td></tr>
								</table>
			      			</td>
			    			<td width="19"></td>
			    			<td width="1" style="background-color:#000000;"></td>
			  			</tr>
					  	<tr><!-- Divider -->
					   		<td height="20" width="1" style="background-color:#000000;"></td>
					    	<td height="20" width="558" colspan="5"></td>
					    	<td height="20" width="1" style="background-color:#000000;"></td>
					  	</tr>
				  	</c:forEach>
		  			<!-- END OF : Shopping List -->
		  			<tr><td width="560" height="1" colspan="7" style="background-color:#000000;"></td></tr>
				</table>
			</td>
	      	<td width="20"></td>
	    </tr>
	    <tr><td height="30" colspan="3"></td></tr><!-- Divider -->  		
  		<!--
  		 * Recipe
  		-->
  		<cms:SectionService maxItems="1" id="articles" articleTypes="STORY,ADVERTORIAL" name="newsletter@main5" />
  		<c:forEach begin="0" end="0" items="${articles}" var="art">
  			<cms:GetRelatedImage version="w540" article="${art}" var="imgURL" />
			<tr class="recipe">
	    		<td width="20"></td>
	    		<td>
	    			<table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
	      				<tr>
	        				<td colspan="3">
            					<c:choose>
            						<c:when test="${not empty imgURL}">
            							<c:set var="imageURL" value="${imgURL}" />
            						</c:when>
            						<c:otherwise>
            							<c:set var="imageURL" value="${defaultImage}" />
            						</c:otherwise>
            					</c:choose>
            					<img width="560" src="${imageURL}" alt="Recipe Article" />
	          				</td>
	        			</tr>
	      				<tr><td height="20" colspan="3"></td></tr><!-- Divider -->
	      				<tr>
	        				<td width="51" height="62" class="banner">
	        					<img src="http://www.ladylike.gr/incoming/article3057038.ece/BINARY/original/nwsl-spoons.png" alt="Spoons" />
	        				</td>
	        				<td width="10" height="62"></td>
	        				<td width="499" height="62" align="left" valign="top" class="title">
	        					<a href="${art.alternate}">${art.title}</a>
			      			</td>
	      				</tr>
	    			</table>
	    		</td>
	    		<td width="20"></td>
	  		</tr>
  		</c:forEach>
		<tr><td height="30" colspan="3"></td></tr><!-- Divider -->
  		<!--
  		 * Quiz
  		-->
  		<cms:SectionService maxItems="1" id="articles" articleTypes="STORY,ADVERTORIAL" name="newsletter@main6" />
		<c:forEach begin="0" end="0" items="${articles}" var="art">
			<cms:GetRelatedImage version="w300" article="${art}" var="imgURL"/>
	  		<tr class="quiz">
	    		<td width="20"></td>
	    		<td>
	    			<table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
	      				<tr><td height="25" colspan="5"></td></tr><!-- Divider -->
	      				<tr>
	        				<td width="20"></td>
	        				<td width="240">
	        					<c:choose>
	        						<c:when test="${not empty imgURL}">
                						<c:set var="imageURL" value="${imgURL}" />
                					</c:when>
                					<c:otherwise>
                						<c:set var="imageURL" value="${defaultImage}" />
                					</c:otherwise>
              					</c:choose>
            					<a href="${art.alternate}">
                  					<img width="240" src="${imageURL}" alt="Quiz Article" />
               					</a>
	          				</td>
	        				<td width="20"></td>
	        				<td width="360" valign="top">
	        					<p class="banner">QUIZ</p>
	        					<p class="title">
	        						<a href="${art.alternate}">${art.title}</a>
								</p>
	          				</td>
	        				<td width="20"></td>
	      				</tr>
	      				<tr><td height="25" colspan="5"></td></tr><!-- Divider -->
	    			</table>
	    		</td>
	    		<td width="20"></td>
	  		</tr>
  		</c:forEach>
		<tr><td height="30" colspan="3"></td></tr><!-- Divider -->
		<!-- 
		 * Read More
		-->
		<tr class="readmore">
	    	<td width="20"></td>
	      	<td width="560" align="center" valign="top">
				<table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
		  			<tr><!--Readmore Banner -->
					    <td width="1" height="21"></td>
					    <td width="19" height="21"></td>
					    <td width="14" height="21"></td>
					    <td width="492" rowspan="3" align="center" valign="middle">
					    	<img src="http://www.ladylike.gr/incoming/article3056193.ece/BINARY/original/readmore.png" alt="Readmore Banner" />
					    </td>
					    <td width="14" height="21"></td>
					    <td width="19" height="21"></td>
					    <td width="1" height="21"></td>
		  			</tr>
		  			<tr>
					    <td width="1" height="1" style="background-color:#000000;"></td>
					    <td width="19" height="1" style="background-color:#000000;"></td>
					    <td width="14" height="1" style="background-color:#000000;"></td>
					    <td width="14" height="1" style="background-color:#000000;"></td>
					    <td width="19" height="1" style="background-color:#000000;"></td>
					    <td width="1" height="1" style="background-color:#000000;"></td>
		  			</tr>
		  			<tr>
					    <td width="1" height="20" style="background-color:#000000;"></td>
					    <td width="19" height="20"></td>
					    <td width="14" height="20"></td>
					    <td width="14" height="20"></td>
					    <td width="19" height="20"></td>
					    <td width="1" height="20" style="background-color:#000000;"></td>
		  			</tr>
		  			<tr><!-- Divider -->
					    <td height="30" width="1" style="background-color:#000000;"></td>
					    <td height="30" width="558" colspan="5"></td>
					    <td height="30" width="1" style="background-color:#000000;"></td>
					</tr>
		  			<!-- START OF : Elements -->
		  			<cms:SectionService maxItems="4" id="articles" articleTypes="STORY,ADVERTORIAL,PHOTOSTORY" name="newsletter@main7" />
					<c:forEach begin="0" end="4" items="${articles}" var="art">
						<cms:GetRelatedImage version="w140" article="${art}" var="imgURL"/>
			  			<tr>
			    			<td width="1" style="background-color:#000000;"></td>
			    			<td width="19"></td>
						    <td width="518" colspan="3">
						   		<table width="518" border="0" align="center" cellpadding="0" cellspacing="0">
									<tr>
						  				<td width="1" height="1" colspan="7" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr><!-- Divider -->
										<td height="10" width="1" style="background-color:#DCDCDC;"></td>
									  	<td height="10" width="516" colspan="5"></td>
									  	<td height="10" width="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr>
						  				<td width="1" style="background-color:#DCDCDC;"></td>
						  				<td width="9"></td>
						  				<td width="498" colspan="3">
						    				<table width="498" border="0" align="center" cellpadding="0" cellspacing="0">
						      					<tr>
													<td width="140">
														<c:choose>
															<c:when test="${not empty imgURL}">
					                							<c:set var="imageURL" value="${imgURL}" />
					                						</c:when>
					                						<c:otherwise>
						                						<c:set var="imageURL" value="${defaultImage}" />
					                						</c:otherwise>
					              						</c:choose>
				              							<a href="${art.alternate}">
				                    						<img width="140" src="${imageURL}" alt="Read More Article" />
				                 						</a>
													</td>
													<td width="15"></td>
													<td width="343" align="left" valign="top">
														<p class="title">
													    	<a href="${art.alternate}">${art.title}</a>
													  	</p>
													  	<p class="summary">${art.leadText}</p>
													</td>
						      					</tr>
						    				</table>
						  				</td>
						  				<td width="9"></td>
						  				<td width="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr><!-- Divider -->
										<td height="10" width="1" style="background-color:#DCDCDC;"></td>
									  	<td height="10" width="496" colspan="5"></td>
									  	<td height="10" width="1" style="background-color:#DCDCDC;"></td>
									</tr>
									<tr><td width="498" height="1" colspan="7" style="background-color:#DCDCDC;"></td></tr>
			      				</table>
			    			</td>
			    			<td width="19"></td>
			    			<td width="1" style="background-color:#000000;"></td>
			  			</tr>
					  	<tr><!-- Divider -->
					    	<td height="20" width="1" style="background-color:#000000;"></td>
					    	<td height="20" width="558" colspan="5"></td>
					    	<td height="20" width="1" style="background-color:#000000;"></td>
					  	</tr>
				  	</c:forEach>
			 	 	<!-- END OF : Elements -->
		  			<tr><td width="560" height="1" colspan="7" style="background-color:#000000;"></td></tr>
				</table>
			</td>
	      	<td width="20" align="center" valign="middle"></td>
	    </tr>
	    <tr><td height="70" colspan="3"></td></tr><!-- Divider -->		
		<!-- 
		 * Categories
		-->
		<tr>
	    	<td width="20"></td>
	    	<td>
	    		<img src="http://www.ladylike.gr/incoming/article3045180.ece/BINARY/original/nwsl-footer.png" alt="Ladylike Footer" />
	    	</td>
	    	<td width="20"></td>
	  	</tr>
		<tr><td colspan="3" height="50"></td></tr><!-- Divider -->
  		<tr class="categories">
    		<td width="20"></td>
    		<td width="560" height="81" align="center" valign="middle">
            	<table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
  					<tr>
					    <td width="52" height="21"></td>
					    <td width="53" height="21" align="center" valign="bottom">
					    	<a href="http://www.ladylike.gr/articles/style/" target="_blank">
					    		<img width="53" height="21" src="http://www.ladylike.gr/incoming/article3059106.ece/BINARY/original/style.png" alt="Style" />
					    	</a>
					    </td>
					    <td width="39" height="21"></td>
					    <td width="56" height="21" align="center" valign="bottom">
					    	<a href="http://www.ladylike.gr/articles/living/" target="_blank">
					    		<img width="56" height="21" src="http://www.ladylike.gr/incoming/article3059104.ece/BINARY/original/living.png" alt="Living" />
					    	</a>
					    </td>
					    <td width="41" height="21"></td>
					    <td width="109" height="21" align="center" valign="bottom">
					    	<a href="http://www.ladylike.gr/articles/celebrities/" target="_blank">
					    		<img width="109" height="21" src="http://www.ladylike.gr/incoming/article3059108.ece/BINARY/original/celebrities.png" alt="Celebrities" />
					    	</a>
					    </td>
					    <td width="41" height="21"></td>
					    <td width="117" height="21" align="center" valign="bottom">
					    	<a href="http://www.ladylike.gr/articles/pop_culture/" target="_blank">
					    		<img width="117" height="21" src="http://www.ladylike.gr/incoming/article3059105.ece/BINARY/original/popculture.png" alt="Pop Culture" />
					    	</a>
					    </td>
					    <td width="52" height="21"></td>
  					</tr>
  					<tr><td height="39" colspan="9"></td></tr><!-- Divider -->
				</table>
				<table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
  					<tr>
					    <td width="135" height="21"></td>
					    <td width="71" height="21" align="center" valign="middle">
					    	<a href="http://www.ladylike.gr/articles/body-and-mind/" target="_blank">
					    		<img width="71" height="21" src="http://www.ladylike.gr/incoming/article3059085.ece/BINARY/original/fitness.png" alt="Fitness" />
					    	</a>
					    </td>
					    <td width="35" height="21"></td>
					    <td width="61" height="21" align="center" valign="middle">
					    	<a href="http://www.ladylike.gr/articles/mom-and-kids/" target="_blank">
					    		<img width="61" height="21" src="http://www.ladylike.gr/incoming/article3059079.ece/BINARY/original/family.png" alt="Family" />
					    	</a>
					    </td>
					    <td width="34" height="21"></td>
					    <td width="89" height="21" align="center" valign="middle">
					    	<a href="http://www.ladylike.gr/articles/shopping_list/" target="_blank">
					    		<img width="89" height="21" src="http://www.ladylike.gr/incoming/article3059100.ece/BINARY/original/shopping.png" alt="Shopping" />
					    	</a>
					    </td>
					    <td width="135" height="21"></td>
  					</tr>
				</table>
            </td>
    		<td width="20"></td>
  		</tr>
		<tr><td height="70" colspan="3"></td></tr><!-- Divider -->
  		<!--
  		 * Follow Us
  		-->
  		<tr>
    		<td width="20"></td>
    		<td>
    			<img src="http://www.ladylike.gr/incoming/article3045182.ece/BINARY/original/nwsl-follow.png" alt="Follow Us" />
    		</td>
    		<td width="20"></td>
  		</tr>
		<tr><td height="70" colspan="3"></td></tr><!-- Divider -->
  		<tr class="followus">
    		<td width="20"></td>
    		<td width="560" align="center" valign="middle">
	            <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
	  				<tr>
					    <td width="43" height="65"></td>
					    <td width="64" height="65" align="center" valign="middle">
					    	<a href="https://www.facebook.com/LadyLikegr" target="_blank">
					    		<img src="http://www.ladylike.gr/incoming/article3045184.ece/BINARY/original/nwsl-facebook.png" alt="Facebook" />
					    	</a>
					    </td>
					    <td height="65"></td>
					    <td width="79" height="65" align="center" valign="middle">
					    	<a href="https://twitter.com/ladylikegr" target="_blank">
					    		<img src="http://www.ladylike.gr/incoming/article3045186.ece/BINARY/original/nwsl-twitter.png" alt="Twitter" />
					    	</a>
					    </td>
					    <td height="65"></td>
					    <td width="64" height="65" align="center" valign="middle">
					    	<a href="http://www.pinterest.com/ladylikegr/" target="_blank">
					    		<img src="http://www.ladylike.gr/incoming/article3045189.ece/BINARY/original/nwsl-pinterest.png" alt="Pinterest" />
					    	</a>
					    </td>
					    <td height="65"></td>
					    <td width="45" height="65" align="center" valign="middle">
					    	<a href="https://foursquare.com/p/ladylikegr/57681415" target="_blank">
					    		<img src="http://www.ladylike.gr/incoming/article3045192.ece/BINARY/original/nwsl-forsquare.png" alt="Forsquare" />
					    	</a>
					    </td>
					    <td height="65"></td>
					    <td width="65" height="65" align="center" valign="middle">
					    	<a href="http://instagram.com/ladylike.gr" target="_blank">
					    		<img src="http://www.ladylike.gr/incoming/article3045195.ece/BINARY/original/nwsl-instagram.png" alt="Instagram" />
					    	</a>
					    </td>
					    <td width="42" height="65"></td>
	  				</tr>
				</table>
            </td>
    		<td width="20"></td>
  		</tr>
		<tr><td height="70" colspan="3"></td></tr><!-- Divider -->
  		<tr class="copyright">
    		<td width="20"></td>
    		<td align="center"><a href="http://www.ladylike.gr">&copy; 2015 LADYLIKE.GR</a></td>
    		<td width="20"></td>
  		</tr>
		<tr><td height="30" colspan="3"></td></tr><!-- Divider -->
</table>
<jsp:include page="../analytics/nuggad.jsp"/>
<jsp:include page="../analytics/atinternet.jsp"/>
</body>
</html>