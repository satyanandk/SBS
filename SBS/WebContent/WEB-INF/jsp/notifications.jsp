<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<SCRIPT type="text/javascript">
	window.oncontextmenu = function() {return false;}
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
<head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
<title>Internal_TXN</title>
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
			<a href="#">Notifications</a>
		</h1>
		<div class="row">
			<div class="span3">
				<ul class="nav nav-list">
					<li class="nav-header">Other Links</li>
					<li><a href="welcomepage.html">Welcome Page</a></li>
					<li><a href="logout.html">Logout</a></li>
				</ul>
			</div>
			<div class="span9">
 			<form:form action="notificationRequest.html" method ="post" modelAttribute="notificationList">
				<c:if test="${not empty notificationList.notifications}">
					<div style="color:red">Clicking authorize will display your personal information to the requester</div>
					<table border="1" width="100%" cellpadding="10" cellspacing="10">
						<tbody>
							<tr>
								<th>Requester</th>
								<th>Notification Type</th>
								<th>Action</th>
							</tr>
							<c:forEach var="notification" items="${notificationList.notifications}" varStatus="s">
								<tr>
									<td>${notification.requesterId}</td>
									<td>
									<c:if test="${notification.notificationType eq 'N'}">
										Transaction view request
									</c:if></td>
									<td>
										<input type="radio" name="notifications[${s.index}].status" value="A"/>
                                            Authorize
                    					<input type="radio" name="notifications[${s.index}].status" value="D"/>
                                            Deny
                                    </td>
								</tr>
								<input name="notifications[${s.index}].notificationId" type="hidden" value="${notification.notificationId}"/>
							</c:forEach>
						</tbody>
					</table>
					<input  class="btn btn-success" type="submit" value="Submit" />
				</c:if>
				<c:if test="${empty notificationList.notifications}">
					No new notifications
				</c:if>
				</form:form>
			</div>
		</div>
	</div>

</body>
</html>