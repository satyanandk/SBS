<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<SCRIPT type="text/javascript">
	window.oncontextmenu = function() {return false;}
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>OTP page</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="container">
	<h1><a href="#">OTP</a></h1>
	<div class="navbar">
  		<div class="navbar-inner">
      	<ul class="nav nav-tabs">
      	<li><a href="webpages/credit.jsp">Credit</a></li>
      	<li><a href="webpages/Debit.jsp">Debit</a></li>
      	<li><a href="webpages/Transfer.jsp">Transfer</a></li>
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
          <div style="color:red">${exception}</div>
          
          <form action="OTPServlet1" method="POST">
    	  <input type="hidden" name="pageInd" value="OTP"/>
	<table>
		<tr>
			<td>Your One Time Password would be expired in 2 minutes.</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>One Time Password is sent to your registered email ID</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Enter OTP</td>
			<td><input type="text" name = "otpCode" autocomplete="off"></td>
			<td></td>
		</tr>
		
		<tr>
			<td></td>
			<td><button class="btn btn-success" type="submit" name="otp">OK</button></td>
			<td></td>
		</tr>
	</table>
	</form>
	</div>
	</div>
	</div>
	</div>
	</body>
	</html>
