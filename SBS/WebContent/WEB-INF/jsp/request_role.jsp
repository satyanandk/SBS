<%@page import="com.asu.ss.secure_banking_system.util.AuthorizationUtil"%>
<%@page import="com.asu.ss.secure_banking_system.service.RequestRoleService"%>
<%@page import="com.sbs.model.user.User"%>
<%@page import="com.asu.ss.secure_banking_system.model.RoleType"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Request role page</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
	
<div class="container">
<div class="hero-unit">
  		<div class="container">
    		<h1>Internal user page for role request</h1>
    		<p>Role request page for internal user</p>
  		</div>
</div>
	<h1><a href="#">Request role page</a></h1>
	<div class="row">
		<div class="span4">
			 <ul class="nav nav-list">
             				<li><a href="welcomepage.html">Welcome Page</a></li>							
							<li><a href="logout.html">Logout</a></li>

             </ul>
		</div>
		<div class="span8">
		<form  action="RequestRole" method="post" onsubmit="reset">
		<table style="height: 121px; width: 605px;">
		<tr>
			<td>Select user :</td>
			<td>
			<select style="width: 238px;" onchange="" name="userSelect" id="userSelect">
			<option>Please select one...</option>
			<% 
				RequestRoleService rrsvc = new RequestRoleService();
				List<User> users = rrsvc.getAllInternalUsers(session.getAttribute("UserID").toString());
				for(int i=0;i<users.size(); i++)
				{
			%>
  				<option value=<%= i%>><%=((users.get(i).getFirstname()==null?"":users.get(i).getFirstname())
  				+(users.get(i).getLastname()==null?"":users.get(i).getLastname()))%></option>
  			<%  
  				}
  			%>
  			</select>
			
			</td>
		</tr>
		<tr>
			<td>Select role :</td>
			<td><select style="width: 234px;" name="roleSelect" id = "roleSelect">
			<option>Please select one...</option>
			<%
				RoleType[] roles = rrsvc.getAllRoles();
				for(int i=0;i<roles.length; i++)
				{
			%>  			
				<option value= <%= i%>><%= AuthorizationUtil.getStringValueOfRole(roles[i].getValue())%></option>
			<%
				}
			%>
			</select>		
			</td>
		</tr>
		<tr>
		<td></td>
			<td><button class="btn btn-primary" type="submit" style="width: 118px; "name="Request role">Request role</button>
			</td>
		</tr>	
		<tr>	
			<td></td>
			<td>
			<h4 style="color: RED"><%= (session.getAttribute("errorDuplicateRequest")==null?"": session.getAttribute("errorDuplicateRequest").toString())%></h4>
			</td>
		</tr>
		<tr>
		<td></td>
		<td style="color: GREEN">
		<h4><%= (session.getAttribute("successMessage")==null?"":session.getAttribute("successMessage"))%></h4>
		</td>
		<% session.removeAttribute("errorDuplicateRequest");
		session.removeAttribute("successMessage");%>		
		</tr>
	</table>
	</form>
</div>
</div>
</div>
</body>
</html>