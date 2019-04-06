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

public class RentServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		String employee_id = (String) req.getParameter("employee_id");
		req.setAttribute("employee_id", employee_id);
		String userSSN = req.getParameter("ssncustomer");
		String roomToRent = (req.getParameterValues("roomToRent"))[0];
		String idRoom = roomToRent.substring(0, 1);
		int t = roomToRent.indexOf(',');
		int t2 = roomToRent.indexOf('$');
		String nameHotel = roomToRent.substring(5,t);
		t = roomToRent.indexOf(':');
		String price = roomToRent.substring(t+2,t2);
		String dateStart = req.getParameter("dateStart");
		System.out.println(dateStart);
		String dateEnd = req.getParameter("dateEnd");
		
		PostgreSqlConn con = new PostgreSqlConn();
		String idrenting = con.rentRoom2(idRoom, nameHotel, dateStart, dateEnd, userSSN);
		
		req.setAttribute("idrenting", idrenting);
		req.setAttribute("price", price);
		req.getRequestDispatcher("payment.jsp").forward(req, resp);
	}
}

