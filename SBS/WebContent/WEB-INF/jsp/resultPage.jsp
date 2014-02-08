<%@page import="com.asu.ss.secure_banking_system.model.TranConfResult"%>
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
<title>Result Page</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<%String resPageInd = (String)session.getAttribute("resPageInd");
String accountID =null;
TranConfResult tranConfResult = null;%>
<%if(resPageInd.equalsIgnoreCase("CREATE_ACCOUNT")) 
{
	accountID = (String)session.getAttribute("createAccount.accountID");
	System.out.println("Account Created in JSP :"+accountID);
}
else if(resPageInd.equalsIgnoreCase("TRAN_CONF"))
{
	tranConfResult = (TranConfResult)session.getAttribute("tranConfResult"); 
}%>
<div class="container">
	<h1>Transaction Confirmation</h1>
	<!-- <div class="navbar">
  		<div class="navbar-inner">
      	<ul class="nav nav-tabs">
      	<li><a href="creditPage.html">Credit</a></li>
      	<li class="active"><a href="DebitPage.html">Debit</a></li>
      	<li><a href="TransferPage.html">Transfer</a></li>
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
          <form action="">
          <% if(resPageInd.equalsIgnoreCase("CREATE_ACCOUNT")) {%>
          <table>
		<tr>
			<td>Account Number <%=accountID%> created successfully.</td>
		</td>
		</tr>
		</table>
          <%} 
          	else if(resPageInd.equalsIgnoreCase("TRAN_CONF")) {%>
	<table>
		<tr>
			<td>From Account Number</td>
			<td><%=tranConfResult.getFromAccountId() %></td>
			<td></td>
		</tr>
		<tr>
			<td>To Account Number</td>
			<td><%=tranConfResult.getToAccountId() %></td>
			<td></td>
		</tr>
		<tr>
			<td>Amount</td>
			<td><%=tranConfResult.getAmount() %></td>
			<td></td>
		</tr>
		</table>
		<%} %>
		<!-- <tr>
			<td></td>
			<td><button class="btn btn-success" type="button" type="submit" name="OK">OK</button></td>
			<td></td>
		</tr> -->
	
	</form>
	</div>
	</div>
	</div>
	</div>
	</body>
	</html>
