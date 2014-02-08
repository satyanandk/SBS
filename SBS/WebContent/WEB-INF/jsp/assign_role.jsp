<%@page import="com.asu.ss.secure_banking_system.model.RoleRequestEntity"%>
<%@page import="com.asu.ss.secure_banking_system.util.AuthorizationUtil"%>
<%@page import="com.asu.ss.secure_banking_system.model.RoleRequestEntity"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role assignment page for system administrator</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<form action="AssignRole" method ="post">
<div class="container">
<div class="hero-unit">
  		<div class="container">
    		<h1>System administrator role request assignment page</h1>
    		<p>role request assignment for system administrator</p>
  		</div>
</div>


	<div class="row">
         <div class="span4">
             <ul class="nav nav-list">
			<li class="nav-header">Links</li>
			<li><a href="welcomepage.html">Welcome Page</a></li>
			<li><a href="logout.html">Logout</a></li>
             </ul>
         </div>
     	<div class="span8">
     		<div class="container">
			<form>
			<%
			String user = ( ((RoleRequestEntity)session.getAttribute("requestDetails")).getRequestForUser().getFirstname()==null?" ":
    			((RoleRequestEntity)session.getAttribute("requestDetails")).getRequestForUser().getFirstname())+ 
				(((RoleRequestEntity)session.getAttribute("requestDetails")).getRequestForUser().getLastname()==null?" "
							  : ((RoleRequestEntity)session.getAttribute("requestDetails")).getRequestForUser().getLastname());
			String role =AuthorizationUtil.getStringValueOfRole(((RoleRequestEntity)session.getAttribute("requestDetails")).getRole());
			String time = ((RoleRequestEntity)session.getAttribute("requestDetails")).getRequestTime().toLocaleString();%>
			<table border="2px">
				<tr><td><h4>Request details</h4></td></tr>
				<tr><td>Name of user: <%= user %></td></tr>
				<tr><td>Role to be assigned: <%= role%></td></tr>
				<tr><td>Time of request: <%= time %></td></tr>
				<tr>
					<td><h4>Validation status :
					<%= (Boolean)session.getAttribute("isValidRequest")==true
					? "The request is a valid request":"This is an invalid request" %></h4>
					</td>
				</tr>
				<tr>
					<td><%
					if((Boolean)session.getAttribute("isValidRequest")==false)
					{
					%><button class="btn btn-success" type="submit"  name="Accept request" disabled="disabled">Accept request</button>
					<% 
					}
					else
					{
					%>
					
					<button class="btn btn-success" type="submit" name="Accept request">Accept request</button>
					<% } %>
					<button class="btn btn-danger" type="submit"  name="Reject request">Reject request</button>
					</td>
				</tr>
			</table>

			</div>
		</div>
	</div>
</div>
</form>
</body>
</html>