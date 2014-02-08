<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
</head>


<body>
<div class="container">
<h1><a href="#">Login</a></h1>
<div id ="mainFrame">
<div id="content">
<div class="row">
         <div class="span3">
             <ul class="nav nav-list">

             </ul>
         </div>
      	<div class="span9">
			<form:form action = "loginFunction.html" method ="post" commandName="upme" >
			<div style =" width : 600px;">
    		<table>
    		<tbody>
    		<tr>
    			<td width="100px">
    			<label> Username </label>
    			</td>
    			<td width ="200px">
    			<label id = "username" name = "username">  <%= session.getAttribute("UserID")%> </label>	
    			</td>
    		</tr>
    		<tr>
    			<td width="100px">
    			<label> Password </label>
    			</td>
    			<td width="200px">
    			<form:input path="password" type="password" autocomplete="off" />
    			</td>
    			<td><div style="color:red"> ${errorblocked}</div></td>
    		</tr>
    		<tr>
    		<td><input  class="btn btn-success" type="submit" value="login" /></td>
    		</tr>
    		</tbody>
    		</table>   
    
    </div>
</form:form>
<form:form action = "forgetPasswordPage.html" method ="post" commandName="user" >
	<input  class="btn btn-success" type="submit" value="Forget Password" />
</form:form>
</div>
</div>
</div>
</div>
</div>

</body>
</html>
