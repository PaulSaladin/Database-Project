<%@page import="java.util.ArrayList"%>
<%@page import="eHotel.entities.Booking"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Booking Page</title>
</head>
<body>

	<%
		String CustName = (String) request.getAttribute("CustName");
	%>
	<form method="post" action="roombook">
		<h4>
			Welcome,
			<%=CustName%></h4>
				<h4>Here are the room(s) you booked</h4>
				<ul>
					<%
						Object obj1 = request.getAttribute("bookedRooms");
						ArrayList<Booking> broomList = null;
						if (obj1 instanceof ArrayList) {
							broomList = (ArrayList<Booking>) obj1;
						}
						if (broomList != null) {
							for (Booking booking : broomList) {
								String roominfo ="Room" + booking.getIdRoom() + " in " + booking.getNameHotel() + " from " + booking.getDateStart() + " to " + booking.getDateEnd();
					%>
					<li><%=roominfo%></li>
					<%
						}
						}
					%>
				</ul>
				<input type="hidden" name="custName" value="<%=CustName%>" />
				<h4>Here are available rooms</h4>

				<button type="submit" onclick="return confirm('book?');">book</button>
	</form>


</body>
</html>