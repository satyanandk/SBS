<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.asu.ss.secure_banking_system.service.TAAAssignmentService"%>
<%@page import="com.sbs.model.user.User"%>    
<%@page import="java.util.List"%>
<%@page import="com.asu.ss.secure_banking_system.model.TAARequestEntity"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<SCRIPT type="text/javascript">
	window.oncontextmenu = function() {return false;}
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Assign</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="container">
<div class="hero-unit">
  		<div class="container">
    		<h1>System administrator technical account access assignment page</h1>
    		<p>Technical account access assignment page for system administrator</p>
  		</div>
</div>

	<div class="row">
	<div class="span3">
		<ul class="nav nav-list">
			<li class="nav-header">Links</li>
			<li><a href="welcomepage.html">Welcome Page</a></li>
			<li><a href="logout.html">Logout</a></li>
			
		</ul>
	</div>
	<div class="span9">
		<form action="TAAAssignmentServlet" method="post">
		<table border="2px">
		<%
			TAARequestEntity taa = (TAARequestEntity)session.getAttribute("TAARequestDetails");
    				
			String user = (taa.getRequestedBy().getFirstname()==null?"":
				taa.getRequestedBy().getFirstname())+" "
		    				+(taa.getRequestedBy().getLastname()==null?""
		    						:taa.getRequestedBy().getLastname());
			String description  = taa.getDescription();
			
			String requestTime =taa.getRequestTime().toLocaleString();
		%>
		<tr><td><h4>Request details</h4></td></tr>
		<tr><td>Requester user: <%= user%></td></tr>
		<tr><td>Request details: <%=  description%></td></tr>
		<tr><td>Request time: <%=  requestTime%></td>
		</tr>
		<tr>	
			<td><h4>Validation status :
			<%= 
			(Boolean)session.getAttribute("isValidTAARequest")==true
					? "The request is a valid request</h4>":"This is an invalid request</h4>" %></td>
			</h4>
			</td>
		</tr>
		<tr>
			<td>Assigned employee :</td>
		</tr>
		<tr>	
			<td><select name ="assignedUser" id ="assignedUser" style="width: 238px;" >
			<% 
			TAAAssignmentService svc = new TAAAssignmentService();
				List<User> users = svc.getAllInternalUsers(session.getAttribute("UserID").toString());
				for(int i=0;i<users.size(); i++)
				{
					String displayValue = (users.get(i).getFirstname()==null?"":users.get(i).getFirstname())
							+" "+(users.get(i).getLastname()==null?"":users.get(i).getLastname())+" | "+ 
					(users.get(i).getEmailid()==null?"":users.get(i).getEmailid());
			%>
			<option value= <%= i%>><%= displayValue%>
			</option>
  			<%  
  				}
  			%>
			</select></td>
			
		</tr>
		<tr>
			
			<td><button class="btn btn-primary" type="submit" style="width: 118px;"	name="Assign">Assign</button>
			<button class="btn btn-danger" type="reset" style="width: 118px;" name="Reject">Reject</button></td>
		
		</tr>
	</table>
	</form>
</div>
</div>
</div>
</body>
</html>