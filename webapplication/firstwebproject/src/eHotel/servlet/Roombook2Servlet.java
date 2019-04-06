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

public class Roombook2Servlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		employee account = new employee();
		String userSSN = req.getParameter("userSSN");
		String roomToBook = (req.getParameterValues("roomToBook"))[0];
		System.out.println(roomToBook);
		String idRoom = roomToBook.substring(0, 1);
		int t = roomToBook.indexOf(',');
		String nameHotel = roomToBook.substring(5,t);
		String dateStart = req.getParameter("dateStart");
		String dateEnd = req.getParameter("dateEnd");
		String custName = req.getParameter("custName");
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		System.out.println(idRoom);
		System.out.println(nameHotel);
		System.out.println(userSSN);
		con.createBooking(idRoom, nameHotel, userSSN, dateStart, dateEnd);
		
		ArrayList<Booking> bookedRooms = con.getbookedRooms(userSSN);
		
		
		req.setAttribute("CustName", custName);
		req.setAttribute("bookedRooms", bookedRooms);
		req.setAttribute("userSSN", userSSN);
		req.getRequestDispatcher("booking.jsp").forward(req, resp);
		return;
	}
}