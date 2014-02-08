<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Profile</title>
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
			<a href="#">Profile</a>
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
						<form id="form1" name="form1" method="post" action="">
							<div>
								<table>
									<tbody>
										<tr>
											<td><label> UserID</label></td>
											<td width="200px" style="border-style: dotted;"><label
												id="userid " name="userid">  <%= session.getAttribute("UserID")%></label></td>
										</tr>
										<tr>
											<td><label> Firstname</label></td>
											<td width="200px" style="border-style: dotted;"><label
												id="username " name="username"> <c:out value="${Firstname}" /></label></td>
										</tr>
										<tr>
											<td><label> Lastname</label></td>
											<td width="200px" style="border-style: dotted;"><label
												id="username " name="username">  ${Lastname}</label></td>
										</tr>
										<tr>
										<td><label> Email</label></td>
										
											<td width="200px" style="border-style: dotted;"><label
												id="email " name="email">   ${Emailid}</label></td>
										</tr>
										<tr>
											<td><label> Contact</label></td>
											<td width="200px" style="border-style: dotted;"><label
												id="contact " name="contact">  ${Contact}</label></td>
										</tr>
										<tr>
											<td width="200px"><label> Address</label></td>
										
										
											<td width="200" style="border-style: dotted;"><label
												id="username " name="username"> ${Address}</label></td>

										</tr>
										
										<tr>
											<td><label> Date Of Birth</label></td>
											<td width="200px" style="border-style: dotted;"><label
												id="dob" name="dob">  ${Dob}</label></td>
										</tr>
										<tr>
											<td width="200px"><label id="seq1" name="seq1">
													Secure Question 1?</label></td>
													<td width="200px" style="border-style: dotted;"><label
												id="dob" name="dob">  ${SecureQ1}</label></td>
										</tr>
										<tr>
											<td width="200px"><label id="seq2" name="seq2">
													Secure Question 2?</label></td>
													<td width="200px" style="border-style: dotted;"><label
												id="dob" name="dob"> ${SecureQ2}</label></td>
										</tr>
										<tr>
											<td width="200px"><label id="seq3" name="seq3">
													Secure Question 3?</label></td>
													<td width="200px" style="border-style: dotted;"><label
												id="dob" name="dob"> ${SecureQ3}</label></td>
										</tr>
									</tbody>
								</table>
							</div>
						</form>
						<form:form action = "changecontactPage.html" method ="post" commandName="user" >
							<td><input  class="btn btn-success" type="submit" value="Change Contact" /></td>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
