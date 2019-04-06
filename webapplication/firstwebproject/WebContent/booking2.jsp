<%@page import="java.util.ArrayList"%>
<%@page import="eHotel.entities.Room"%>
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
	
	<%
					String userSSN = (String) request.getAttribute("userSSN");
	%>
	
	<h4>
			Welcome,
			<%=userSSN%></h4>
	<form method="post" action="roombook2">
				<h4>Here are the result of your research</h4>

				<select name = "roomToBook">
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
				
				
				<input type="hidden" name="userSSN" value="<%=userSSN%>" />
				
				<%
					String dateStart = (String) request.getAttribute("dateStart");
				%>
				
				<input type="hidden" name="dateStart" value="<%=dateStart%>" />
				
				<%
					String dateEnd = (String) request.getAttribute("dateEnd");
				%>
				
				<input type="hidden" name="dateEnd" value="<%=dateEnd%>" />
				
				<%
					String custName = (String) request.getAttribute("custName");
				%>
				
				<input type="hidden" name="custName" value="<%=custName%>" />
				
				<button type="submit" value ="submit" onclick="return confirm('book?');">book</button>
	</form>


</body>
</html>