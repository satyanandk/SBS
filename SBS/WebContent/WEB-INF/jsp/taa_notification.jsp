<%@page import="com.asu.ss.secure_banking_system.service.TAANotificationService"%>
<%@page import="com.sbs.model.user.User"%>
<%@page import="com.asu.ss.secure_banking_system.model.TAARequestEntity"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<SCRIPT type="text/javascript">
	window.oncontextmenu = function() {return false;}
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Technical account access notification</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link
	href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css"
	rel="stylesheet">
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>


</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="container">
<div class="hero-unit">
  		<div class="container">
    		<h1>Internal user technical account access page</h1>
    		<p>Process technical account access request</p>
  		</div>
</div>
		<h1>Technical Account Access notification page
		</h1>
		<div id="mainFrame">
			<div id="content">
				<div class="row">
					<div class="span3">
						<ul class="nav nav-list">
							<li class="nav-header">Links</li>
							<li>Transaction History</li>
							<li><a href="welcomepage.html">Welcome Page</a></li>
							<li><a href="logout.html">Logout</a></li>
						</ul>
					</div>
					<div class="span9">
		<form action="ProcessTAARequest" method="post">
		<table border="2px">
			<tr>

				<td><h4>Technical account access requests assigned to you</h4></td><td></td>
			</tr>

			<%
				TAANotificationService svc =  new  TAANotificationService();
				User user = new User();
				user.setUserID(session.getAttribute("UserID").toString());
				List<TAARequestEntity> requests = svc.getAllTAARequestsForUser(user);
				for(int i=0;i<requests.size();i++)
				{
			%> 
			<tr>			
				<td><%= requests.get(i).getDescription()%></td>
				<td><button class="btn btn-primary" type="submit" style="width: 118px;"
				 name = "TAAToProcess" id = "TAAToProcess" align="left" value=<%= i %>>Details</td>
			</tr>				
			<% 
				}
			%>	

		</table>
	</form>
					</form>
					</div>
					</div>
					</div>
					</div>
					</div>
					
</body>
</html>