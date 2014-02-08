<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Forgot Password</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
</head>

<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="container">
<h1><a href="#">Forgot Password</a></h1>
<div id ="mainFrame">
<div id="content">
<div class="row">
         <div class="span3">
             <ul class="nav nav-list">
             	<li class="nav-header">Other Links</li>
             	<li><a href="welcomepage.html">Welcome Page</a></li>
					<li><a href="logout.html">Logout</a></li>
             </ul>
         </div>
      	<div class="span9">
		<form:form action = "forgetPassFunction.html" method ="post" commandName="user" >
		<div style="color:red"> ${errorblocked}</div>
    	<table>
    	<tbody>
    	
    	<tr>
    		<td><label> UserID </label></td>
    		<td><label id = "username" name = "username"> <%= session.getAttribute("UserID")%> </label></td>
    	</tr>
    	<tr>
    		<td width="150px">
    		<label> Date Of Birth </label>
    		</td>
    		<td width="200px">
    		<form:input path="DOB" type="text" placeholder="MM/DD/YYYY"  autocomplete="off"/>
    		</td>
    	</tr>
    	<tr>
    		<td><label id="seq1" name="seq1" >${SecureQ1}</label></td> 
    		<td width="200px">
    		<form:input path="secureA1" type="text"  autocomplete="off"/>
    		</td>
    	</tr>
    	<tr>
    		<td><label id="seq2" name="seq2">${SecureQ2}</label></td> 
    	    <td width="200px">
    		<form:input path="secureA2" type="text" autocomplete="off" />
    		</td>
    	</tr>
    	<tr>
    		<td><label id="seq3" name="seq3"> ${SecureQ3}</label></td> 
    		<td width="200px">
    		<form:input path="secureA3" type="text" autocomplete="off" />
    		</td>
    	</tr>
    	<tr>
    	<td>
    		<input  class="btn btn-success" type="submit" value="Enter" style=" float:right;" />
    	</td>
    	</tr>
    	
</tbody>
</table>
</form:form>
</div>
</div>
</div>

</div>
</div>
</body>
</html>
