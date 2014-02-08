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
<title>Request Payment</title>
<!-- Bootstrap core CSS -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="container">
	<h1><a href="#">Request Payment</a></h1>
	<!-- <div class="navbar">
  		<div class="navbar-inner">
      	<ul class="nav nav-tabs">
      	<li><a href="webpages/credit.jsp">Credit</a></li>
      	<li><a href="webpages/Debit.jsp">Debit</a></li>
      	<li class="active"><a href="webpages/Transfer.jsp">Req</a></li>
    	</ul>
  		</div>
  	</div> -->
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
          <form action="MerchantRequestServlet1" method="POST" enctype="multipart/form-data">
    	  <input type="hidden" name="pageInd" value="RequestMerchantPay"/>
	<table>
		
		<tr>
			<td>Customer User ID</td>
			<td><input type = "text" name = "customerUserID" autocomplete="off"></td>
			<td></td>
		</tr>
		<tr>
			<td>Amount</td>
			<td><input type="text" name = "amount" autocomplete="off"></td>
			<td></td>
		</tr>
		<tr>
			<td>Remarks</td>
			<td><input type="text" name = "remarks" autocomplete="off"></td>
			<td></td>
		</tr>
		<tr>
				<td align="right">Certificate File</td>
				<td align="left"><input type="file" name="fileName" autocomplete="off" /></td>
		</tr>
		<tr>
			<td></td>
			<td><button class="btn btn-success" type="submit" name="RequestPayment">Request Payment</button></td>
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