<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<SCRIPT type="text/javascript">
	window.oncontextmenu = function() {return false;}
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Create New Account</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link
	href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>

</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
	<div class="container">
		<h1>
			<a href="#">Create New Account</a>
		</h1>
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
					<div style="color: red">${exception}</div>
					<form action="CreateAccountServlet1" method="POST"
						enctype="multipart/form-data">
						<table width="50%">
							<tr>
								<td align="right">User ID</td>
								<td align="left"><input type="text" name="userID"
									maxlength="15" autocomplete="off" /></td>
							</tr>
							<tr>
								<td align="right">Certificate File</td>
								<td align="left"><input type="file" name="fileName" autocomplete="off" /></td>
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
								<td><input class="btn btn-success" type="submit"
									name="Create" value="Create" /></td>
								<td><input class="btn" type="reset" name="cancel"
									value="Cancel" /></td>
							</tr>

						</table>

					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>