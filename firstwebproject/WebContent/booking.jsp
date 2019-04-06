<%@page import="java.util.ArrayList"%>
<%@page import="eHotel.entities.Booking"%>
<%@page import = "eHotel.connections.PostgreSqlConn"%>
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
				<h4>Search a room :</h4>
				
				
				Hotel Chain : 
					<select name = "hotelChain">
					<%
						ArrayList<String> hotelChain =  new ArrayList<String>();
						hotelChain.add("");
						hotelChain.add("Pivac");
						hotelChain.add("Super Hotel");
						hotelChain.add("Cheraton");
						hotelChain.add("Hilton");
						hotelChain.add("Ibis");
						if (hotelChain != null) {
							for (String nameHotelChain : hotelChain) {
					%>					
						<option><%=nameHotelChain%></option>

					<%
						}
						}
					%>
				</select>
				<br><br>
				
				
				Category : 
				<select name = "category">
					<%
						String[] rates = {"","1","2","3","4","5"};
						if (rates != null) {
							for (String rate : rates) {
					%>					
						<option><%=rate%></option>

					<%
						}
						}
					%>
				</select>
				<br><br>
				
				Area : 
				<select name = "category">
					<%
						String[] areas = {"","QC","ON","MB","BC","NB", "NS", "AB"};
						if (areas != null) {
							for (String area : areas) {
					%>					
						<option><%=area%></option>

					<%
						}
						}
					%>
				</select>
				<br><br>
				
				

				<button type="submit" value="submit" onclick="return validate();">search</button>
	</form>


</body>
</html>