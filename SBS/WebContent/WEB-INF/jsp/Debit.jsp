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
<title>Debit</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
<script language="JavaScript">

var nHist = window.history.length;
if(window.history[nHist] != window.location)
  window.history.forward();

</script>
 
</head>

<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<!-- <body> -->
<div class="container"> 
	<h1><a href="#">Debit</a></h1>
	<div class="navbar">
  		<div class="navbar-inner">
      	<ul class="nav nav-tabs">
      	<li><a href="creditPage.html">Credit</a></li>
      	<li class="active"><a href="#">Debit</a></li>
      	<li><a href="TransferPage.html">Transfer</a></li>
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
          
          <form action="DebitAndCreditServlet1" method="POST">
    	  <input type="hidden" name="pageInd" value="Debit"/>
	<table>
		
		<tr>
			<td>Account Number</td>
			<td><input type = "text" name = "accountNo" autocomplete="off"></td>
			<td></td>
		</tr>
		<tr>
			<td>Amount</td>
			<td><input type="text" name = "amount" autocomplete="off"></td>
			<td></td>
		</tr>
		
		<tr>
			<td></td>
			<td><button class="btn btn-success" type="submit" name="debit">OK</button></td>
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
