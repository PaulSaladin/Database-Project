<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Employee Check-In Menu</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="ch-cn">
<script>

	function validate() {
		var ssncustomer = document.getElementById("ssncustomer");
		var idbooking = document.getElementById("idbooking");
		var dateStart = document.getElementById("dateStart");
		var dateEnd = document.getElementById("dateEnd");
		var capacity = document.getElementById("capacity");
		var priceMin = document.getElementById("priceMin");
		var priceMax = document.getElementById("priceMax");
		if(idbooking.value == ""){
			//alert("The ID Booking can't be null");
			if(dateStart.value == "" || dateEnd.value == "" || ssncustomer.value == ""){
				alert("Please insert either a booking Number either the all the mandatory informations.");
				return false;
			}
			
		}
		else
			return true;
	}

</script>

</head>
<body>
	<%
	String employee_id = (String) request.getAttribute("employee_id");
	%>

	 
	<h4>Welcome, <%=employee_id%></h4>
	
	<form method="post" action="CheckInMenu">
	<input type="hidden" id="employee_id" name="employee_id" value="<%=employee_id%>" />
	This is the Employee Check-In/Renting Menu.
	<br>
	<br>
	<br>
		<h3>If the customer has already booked a room:</h3>
		<br>
		ID Booking:<input type="text" id="idbooking" name="idbooking"><br>
		<br>
		<br>
		<h3>If the customer has no Booking Number, then create a renting with the informations below:</h3>
		<h4>Search a room :</h4>
		<br>
				<h4>Mandatory:</h4>
				SSN Customer : <input type="text" id="ssncustomer" name="ssncustomer">
				<br><br>
				Start Date : (YYYY-MM-DD)  <input type="text" id="dateStart" name="dateStart">
				<br><br>
				End Date : (YYYY-MM-DD) <input type="text" id="dateEnd" name="dateEnd">
				<br><br>
				<h4>Optional:</h4>
				Capacity :  <input type="text" id="capacity" name="capacity">
				<br><br>
				
				Price min :  <input type="text" id="priceMin" name="priceMin">
				<br><br>
				Price max :  <input type="text" id="priceMax" name="priceMax">
				<br><br>
				
		
		<button type="submit" value="submit" onclick="return validate();">submit</button>
		<button type="reset" value="reset">reset</button>
	</form>
</body>
</html>
