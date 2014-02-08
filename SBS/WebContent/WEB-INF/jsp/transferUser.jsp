<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="com.asu.ss.secure_banking_system.util.AuthorizationUtil"%>
<%@page import="com.asu.ss.secure_banking_system.service.RequestRoleService"%>
<%@page import="com.sbs.model.user.User"%>
<%@page import="com.asu.ss.secure_banking_system.model.RoleType"%>
<%@page import="java.util.List"%>
<%@page import="com.asu.ss.secure_banking_system.model.DepartmentEnum"%>
  
<html>
<SCRIPT type="text/javascript">
	window.oncontextmenu = function() {return false;}
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Transfer User Account</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="container">
	<h1><a href="#">Transfer User</a></h1>
	<div class="navbar">
  		<div class="navbar-inner">
      	<ul class="nav nav-tabs">
      	<li><a href="addUser.html">Add User</a></li>
      	<li><a href="deleteUserPage.html">Delete User</a></li>
      	<li class="active"><a href="#">Transfer User</a></li>
    	</ul>
  		</div>
	</div>
	<div class="row">
         <div class="span4">
             <ul class="nav nav-list">
             	<li class="nav-header">Other Links</li>
             	<li><a href="welcomepage.html">Welcome Page</a></li>
             	<li><a href="logout.html">Logout</a></li>
             </ul>
         </div>
         <div class="span8">
          <div class="container">
           <div style="color:red">${error}</div>
          <div style="color:red">All fields are mandatory</div>
			<form:form action = "transferuser.html" method ="post" commandName="user" >
               <table>
               <tr>
               <td>
               </td>
               </tr>
    			<tr>
    				<td align="right">User ID:</td>
    				<td align="left"><form:input path="userID" type="text" autocomplete="off" value=""/></td>
    			</tr>
    			  			<tr>
      				<td align="right">Department:</td>
      				<%-- <td align="left"><form:input path="deptID" type="text" autocomplete="off"/></td> --%>
      				<td>
      					<form:select id="dept" path="deptID">
      						<option value=0 selected>Customer</option>
							<option value=1>Sales</option>
							<option value=2>IT and Tech Support</option>
							<option value=3>Transaction Monitoring</option>
							<option value=4>Human Resources</option>
							<option value=5>Company Management</option>
						</form:select>
						</td>
    			</tr>
    			<tr>
    			
    				<td align="justify"><input  class="btn btn-success" type="submit" value="transfer"></td>
    				<td align="justify"><input  class="btn" type="reset" value="Cancel"></td>
    			</tr>
    		</table>	
    		</form:form>  
		</div>
     </div>
     </div>
	
	
	
	</div>
</body>
</html>