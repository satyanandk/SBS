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
<title>Transfer</title>
<!-- Bootstrap core CSS -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="container">
	<h1><a href="#">Transfer</a></h1>
	<div class="navbar">
  		<div class="navbar-inner">
      	<ul class="nav nav-tabs">
      	<li><a href="creditPage.html">Credit</a></li>
      	<li><a href="DebitPage.html">Debit</a></li>
      	<li class="active"><a href="#">Transfer</a></li>
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
          <form action="TransferServlet1" method="POST">
    	  <input type="hidden" name="pageInd" value="Transfer"/>
	<table>
		
		<tr>
			<td>From Account Number</td>
			<td><input type = "text" name = "fromAccount" autocomplete="off"></td>
			<td></td>
		</tr>
		<tr>
			<td>To Account Number</td>
			<td><input type="text" name = "toAccount" autocomplete="off"></td>
			<td></td>
		</tr>
		<tr>
			<td>Amount</td>
			<td><input type="text" name = "amount" autocomplete="off"></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><button class="btn btn-success" type="submit" name="transfer">OK</button></td>
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