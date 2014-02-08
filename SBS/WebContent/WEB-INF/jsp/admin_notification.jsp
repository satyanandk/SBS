<%@page import="com.asu.ss.secure_banking_system.util.AuthorizationUtil"%>
<%@page import="com.asu.ss.secure_banking_system.model.RoleRequestEntity"%>
<%@page import="com.asu.ss.secure_banking_system.model.TAARequestEntity"%>
<%@page import="com.asu.ss.secure_banking_system.service.AdminNotificationService"%>
<%@page import="com.asu.ss.secure_banking_system.model.RequestEntity"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin notification page</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
<SCRIPT type="text/javascript">
	window.oncontextmenu = function() {return false;}
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">

<div class="container">
<div class="hero-unit">
  		<div class="container">
    		<h1>System administrator notification page</h1>
    		<p>Notification page for system administrator</p>
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
	<div class="container">
		<table border="2px">
			<tr style="padding-top: 4px">
			<td style="padding-left: 20px; padding-right: 20px"><b>Requester</b></td>
					<td style="padding-left: 20px; padding-right: 20px"><b>Request type</b></td>
					<td style="padding-left: 20px; padding-right: 20px"><b>Request details</b></td>
					<td style="padding-left: 20px; padding-right: 20px"><b>Action</b></td>
			</tr>
				
			<%
				AdminNotificationService ansvc = new AdminNotificationService();
			    ArrayList<RequestEntity> requestList = ansvc.getAllRequestes(session.getAttribute("UserID").toString());
			    for(int i=0; i< requestList.size();i++)
			    {
			    	String role ="";
			    	String user = "";
			    	String description="";
			    	
		    		
			    	if(requestList.get(i).getClass()==RoleRequestEntity.class)
			    	{
			    		user =( ((RoleRequestEntity)requestList.get(i)).getRequestForUser().getFirstname()==null?" ":
			    			((RoleRequestEntity)requestList.get(i)).getRequestForUser().getFirstname())+ 
		    				(((RoleRequestEntity)requestList.get(i)).getRequestForUser().getLastname()==null?" "
		    							  : ((RoleRequestEntity)requestList.get(i)).getRequestForUser().getLastname());
			    		role = AuthorizationUtil.getStringValueOfRole(((RoleRequestEntity)requestList.get(i)).getRole());

			 %> 
			<form action="RoleRequestValidation" method="POST"> 
			<tr style="padding-left: 20px; padding-right: 20px; padding-top: 4px">
				<td style="padding-left: 20px; padding-right: 20px"><%= requestList.get(i).getRequestedBy().getFirstname()%></td>
				<td style="padding-left: 20px; padding-right: 20px">Role assignment</td>
				<td style="padding-left: 20px; padding-right: 20px">
				<%= role%> role to be assigned to <%= user%>
				</td>
				<td style="padding-left: 20px; padding-right: 20px">
				<button class="btn btn-primary" id = "RoleRequestToValidate" name = "RoleRequestToValidate"value=<%=i%> type="submit">Validate</button></td>
			</tr>
			</form>
			<%    	
			    }
			    	else if(requestList.get(i).getClass()==TAARequestEntity.class)
			    	{
			    		user = (requestList.get(i).getRequestedBy().getFirstname()==null?"":
			    			requestList.get(i).getRequestedBy().getFirstname()+" ")
			    				+(requestList.get(i).getRequestedBy().getLastname()==null?""
			    						:requestList.get(i).getRequestedBy().getLastname());
			    		description = ((TAARequestEntity)requestList.get(i)).getDescription();
			%>
			<form action="TAARequestValidation" method="POST"> 
			   <tr style="padding-left: 20px; padding-right: 20px; padding-top: 4px">
				<td style="padding-left: 20px; padding-right: 20px"><%= user%></td>
				<td style="padding-left: 20px; padding-right: 20px">Technical account access</td>
				<td style="padding-left: 20px; padding-right: 20px"><%= description%></td>
				<td style="padding-left: 20px; padding-right: 20px">
				<button class="btn btn-primary" type="submit" name="TAARequestToValidate" id = "TAARequestToValidate" value ="<%=i%>">Validate</button></td>
			</tr>
			</form>
			<%     	}
			    }
			%>

		</table>
	</div>
	</div>
</div>
</div>
</body>
</html>