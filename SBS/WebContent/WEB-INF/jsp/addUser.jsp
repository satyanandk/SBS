<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<title>Add User Account</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>

</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">

<div class="container">
	<h1><a href="#">Add User</a></h1>
	<div class="navbar">
  		<div class="navbar-inner">
      	<ul class="nav nav-tabs">
      	<li class="active"><a href="#">Add User</a></li>
      	<li><a href="deleteUserPage.html">Delete User</a></li>
      	<li><a href="transferUserPage.html">Transfer User</a></li>
    	</ul>
  		</div>
	</div> 
	
	<div class="row">
         <div class="span4">
             <ul class="nav nav-list">
             	<li class="nav-header">Other Links</li>
             	<!-- <li>Add Links Here</li> -->
             	<li><a href="welcomepage.html">Welcome Page</a></li>
				<li><a href="logout.html">Logout</a></li>
             </ul>
         </div>
         <div class="span8">
          <div class="container">
          <div style="color:red">${error}</div>
          <div style="color:red">All fields are mandatory</div>
			<form:form action = "createuser.html" method ="post" commandName="user" >
               <table>

    			<tr>
      				<td align="right">First Name:</td>
      				<td align="left"><form:input path="firstname" type="text" autocomplete="off" /></td>
    			</tr>
    			<tr>
      				<td align="right">Last Name:</td>
      				<td align="left"><form:input path="lastname" type="text" autocomplete="off" /></td>
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
      				<td align="right">Role:</td>
					<td>
						<form:select id="role" path="roleID">
							<option value=1 selected>Merchant</option>
							<option value=2>Regular Customer</option>
							<option value=3>System Admin</option>
							<option value=4>Regular Internal</option>
							<option value=5>Department Head</option>
							<option value=6>Corporate Official</option>
						</form:select>
					</td>			<%-- <td align="left"><form:input path="roleID" type="text" autocomplete="off"/> --%>
    			</tr>
    			
    			<tr>
      				<td align="right">User ID:</td>
      				<td align="left"><form:input path="userID" type="text"  value="" autocomplete="off"/></td>
    			</tr>
    			
    			<tr>
      				<td align="right">Email:</td>
      				<td align="left"><form:input path="emailid" type="text" autocomplete="off" /></td>
    			</tr>
    			<tr> 
      				<td align="right">Phone Number:</td>
      				<td align="left"><form:input path="contact"  type="text" placeholder="xxx-xxx-xxxx" autocomplete="off" /></td>
    			</tr>
    			<tr>
      				<td align="right">Date of Birth:</td>
      				<td align="left"><form:input path="DOB" type="text" placeholder="MM/DD/YYYY" autocomplete="off"/></td>
    			</tr>
    			<tr>
      				<td align="right">Address:</td>
      				<td align="left"><form:input path="address" type="text" autocomplete="off"/></td>
    			</tr>
    			<tr>
      				<td align="right">Identification Type</td>
      				<td align="left"><form:input path="idtype" type="text"  placeholder="Passport/License..." autocomplete="off"/></td>
    			</tr>
    			<tr>
      				<td align="right">Identification Number</td>
      				<td align="left"><form:input path="idNo" type="text" autocomplete="off"/></td>
    			</tr>
    			<tr>
    				<td align="right">Security Question 1</td>
    				<td align="left"><form:input path="secureQ1" type="text"  autocomplete="off"/></td>
    			</tr>
    			<tr>
    				<td align="right">Security Answer 1</td>
    				<td align="left"><form:input path="secureA1" type="text"  autocomplete="off"/></td>
    			</tr>
    			<tr>
    				<td align="right">Security Question 2</td>
    				<td align="left"><form:input path="secureQ2" type="text"  autocomplete="off"/></td>
    			</tr>
    			<tr>
    				<td align="right">Security Answer 2</td>
    				<td align="left"><form:input path="secureA2" type="text"  autocomplete="off"/></td>
    			</tr>
    			<tr>
    				<td align="right">Security Question 3</td>
    				<td align="left"><form:input path="secureQ3" type="text" autocomplete="off"/></td>
    			</tr>
    			<tr>
    				<td align="right">Security Answer 3</td>
    				<td align="left"><form:input path="secureA3" type="text" autocomplete="off"/></td>
    			</tr>
    			<tr>
								 <td></td>
								<td><script type="text/javascript"
										src="https://www.google.com/recaptcha/api/challenge?k=6LdIzOkSAAAAAJX2Qd6w62v8nIgFkFr4s7tROC0Z">
									
								</script>
									<noscript>
										<iframe
											src="https://www.google.com/recaptcha/api/noscript?k=6LdIzOkSAAAAAJX2Qd6w62v8nIgFkFr4s7tROC0Z"
											height="300" width="500" frameborder="0"></iframe>
										<br>
										<textarea name="recaptcha_challenge_field" rows="3" cols="40">
   </textarea>
										<input type="hidden" name="recaptcha_response_field"
											value="manual_challenge">
									</noscript></td> 


				</tr>
    			<tr>
    				<td></td>
    				<td><input  class="btn btn-success" type="submit" value="submit" /></td>
      				<td><input  class="btn" type="reset" value="Cancel" /></td>	
    			</tr>
    		</table>	
    		<br><br>
    		</form:form> 
		</div>
        </div>
     </div>
</div>
</body>
</html>