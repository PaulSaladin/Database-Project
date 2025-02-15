package eHotel.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eHotel.connections.PostgreSqlConn;
import eHotel.entities.Booking;

public class RoombookServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		employee account = new employee();
		String custName = req.getParameter("custName");
		String roomno = req.getParameter("roomno");
		
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		String userSSN = con.bookRoom(custName,roomno);
		
		if (userSSN.length()!=0) {			
			
			ArrayList<Booking> bookedRooms = con.getbookedRooms(userSSN);
			
			
			req.setAttribute("CustName", custName);
			req.setAttribute("bookedRooms", bookedRooms);

			req.getRequestDispatcher("booking.jsp").forward(req, resp);
			return;	
		}
		resp.sendRedirect("login_failure.jsp");
		return;
	}
}