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
<title>Payment Confirmation</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="container">

	<div class="row">
         <div class="span4">
             
         </div>
         <div class="span8">
          <div class="container">
          <div style="color:red">${exception}</div>
    <form action="CustomerPayConfServlet1" method="POST">
    <input type="hidden" name="pageInd" value="Credit"/>
		<table>
			<tr>
				<td>Account Number</td>
				<td><input type = "text" name = "accountNo" autocomplete="off"></td>
				<td></td>
			</tr>
			<tr>
				<td>Amount</td>
				<td><%=(String)session.getAttribute("Payment.amount")%></td>
				<td></td>
			</tr>
			<tr>
				<td>Remarks</td>
				<td><%=(String)session.getAttribute("Payment.remarks")%></td>
				<td></td>
			</tr>
			<tr>
				<td>User ID</td>
				<td><%=(String)session.getAttribute("Payment.CusUserID")%></td>
				<td></td>
			</tr>
			<tr>
				<td>Enter OTP </td>
				<td><input type = "text" name = "otpKey" autocomplete="off"></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td><button class="btn btn-success" type="submit" name="Pay">Pay</button></td>
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
