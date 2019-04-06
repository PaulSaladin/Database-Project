<%@page import="java.util.ArrayList"%>
<%@page import="eHotel.entities.Booking"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Page</title>

<script>

	function validate() {
		var dateStart = document.getElementById("dateStart");
		var dateEnd = document.getElementById("datEend");
		if(dateStart.value == "" || dateEnd.value == ""){
			alert("Please fill the dates");
			return false;
		}
		else
			return true;
	}

</script>


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
				
				<input type="hidden" name="userSSN" value="<%=userSSN%>" />
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
				<select name = "rate">
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
				<select name = "area">
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
				
				Start Date : (YYYY-MM-DD)  <input type="text" id="dateStart" name="dateStart">
				<br><br>
				End Date : (YYYY-MM-DD) <input type="text" id="dateEnd" name="dateEnd">
				<br><br>
				Capacity :  <input type="text" id="capacity" name="capacity">
				<br><br>
				Number min of rooms in the Hotel :  <input type="text" id="minRoom" name="minRoom">
				<br><br>
				Number max of rooms in the Hotel :  <input type="text" id="maxRoom" name="maxRoom">
				<br><br>
				Price min :  <input type="text" id="priceMin" name="priceMin">
				<br><br>
				Price max :  <input type="text" id="priceMax" name="priceMax">
				<br><br>
				
				

				<button type="submit" value="submit" onclick="return validate();">search</button>
	</form>


</body>
</html>