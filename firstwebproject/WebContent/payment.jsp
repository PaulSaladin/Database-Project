<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Page</title>

	
<script>

	function validate() {
		var amount = document.getElementById("amount");
		if(amount.value ==  null){
			alert("Amount can't be null");
			return false;
		}
		else
			return true;
	}

</script>
	
</head>
<body>

	<%
		String employee_id = (String) request.getAttribute("employee_id");
		String idrenting = (String) request.getAttribute("idrenting");
		String room_amount = (String) request.getAttribute("price");
	%>
	<h4>
			Welcome,
			<%=employee_id%></h4>
			
			The room is : CA$<%=room_amount%> per night
			<h4>Please proceed to payment</h4>
	<form method="post" action="payment">
			Amount : <input type= "text" id = "amount" name="amount">
			<input type="hidden" name="idrenting" value="<%=idrenting%>" />
			
			<input type="hidden" name="employee_id" value="<%=employee_id%>" />
			<input type="hidden" name="room_amout" value="<%=room_amount%>" />
				
				
				<button type="submit" value ="submit" onclick="return validate();">Pay</button>
	</form>


</body>
</html>