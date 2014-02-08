<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New Password</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link
	href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css"
	rel="stylesheet">
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
</head>

<body>
	<div class="container">
		<h1>
			<a href="#">Password Setup</a>
		</h1>
		<div id="mainFrame">
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
						<form:form action = "newpasswdfunction.html" method ="post" modelAttribute="userandotp" >
							<div>
							<div style="color:red"> ${error}</div>
								<table>
									<tbody>
										<tr>
											<td width="150px"><label> OTP </label></td>
											<td width="200px"><form:input path="otpcode" type="text" autocomplete="off" /></td>
										</tr>
										<tr>
											<td width="150px"><label> New Password </label></td>
											<td width="200px"><form:input path="user.password" type="password"  autocomplete="off"/></td>
										</tr>
										<tr>
											<td width="150px"><label> Confirm Password </label></td>
											<td width="200px"><form:input path="comfirmpassword" type="password"  autocomplete="off"/></td>
										</tr>
										<tr>
											<td><input  class="btn btn-success" type="submit" value="Enter" /></td>
										</tr>
									</tbody>
								</table>


							</div>
						</form:form> 
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
