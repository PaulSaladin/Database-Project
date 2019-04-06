<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Loginsuccess</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="ch-cn">
</head>
<body>
<%
String employee_id = (String)request.getParameter("employee_id");
request.setAttribute("employee_id", employee_id);
RequestDispatcher rd = request.getRequestDispatcher("employee_checkin.jsp");

%>

<script>
	function o(){
		alert("password can't be null");
		rd.forward(request,response);
	return
	}
</script>


	<h4>Welcome, <%=employee_id%></h4>
	<button onclick="return o();" >Check-In a booking</button>
	<button onclick = "" value="renting">Rent a room</button>
</body>
</html>
