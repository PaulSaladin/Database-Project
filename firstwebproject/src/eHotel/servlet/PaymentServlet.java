package eHotel.servlet;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eHotel.connections.PostgreSqlConn;
import eHotel.entities.Room;
import eHotel.entities.Booking;

public class PaymentServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String idrenting = req.getParameter("idrenting");
		String amount = req.getParameter("amount");

		String employee_id = (String) req.getParameter("employee_id");
		req.setAttribute("employee_id", employee_id);
		
		PostgreSqlConn con = new PostgreSqlConn();
		con.pay(idrenting, amount);
		req.getRequestDispatcher("employee_checkin.jsp").forward(req, resp);
	}
	
	
}

