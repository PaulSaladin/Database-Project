<%@page import="java.util.ArrayList"%>
<%@page import="eHotel.entities.Room"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Renting Page</title>
</head>
<body>

	<%
		String employee_id = (String) request.getAttribute("employee_id");
	%>
	

	
	<h4>
			Welcome,
			<%=employee_id%></h4>
	<form method="post" action="rentRoom">
				<h4>Here are the result of your research</h4>

				<select name = "roomToRent">
					<%
						Object obj = request.getAttribute("RoomsOK");
						ArrayList<String[]> roomList = null;
						if (obj instanceof ArrayList) {
							roomList = (ArrayList<String[]>) obj;
						}
						if (roomList != null) {
							for (String[] room : roomList) {
								/* String roominfo = room.getRoom_no() + "---" + room.getRoom_status(); */
					%>					
						<option><%=room[0]%> in <%=room[1]%>, price : <%=room[2]%>$CAD / night</option>

					<%
						}
						}
					%>
				</select>
				
				
				<%
					String dateStart = (String) request.getAttribute("dateStart");
					String ssncustomer = (String) request.getAttribute("ssncustomer");
				
				%>
				
				<input type="hidden" name="dateStart" value="<%=dateStart%>" />
				
				<%
					String dateEnd = (String) request.getAttribute("dateEnd");
				%>
				
				<input type="hidden" name="dateEnd" value="<%=dateEnd%>" />
				
				
				<input type="hidden" name="employee_id" value="<%=employee_id%>" />
				<input type="hidden" name="ssncustomer" value="<%=ssncustomer%>" />
				
				<button type="submit" value ="submit" onclick="return confirm('Rent?');">Rent and go to payment page</button>
	</form>


</body>
</html>