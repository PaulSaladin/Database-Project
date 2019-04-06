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

public class CheckInServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String idbooking = req.getParameter("idbooking");
		
		String idrenting = "";

		String employee_id = (String) req.getParameter("employee_id");
		req.setAttribute("employee_id", employee_id);
		
		String ssncustomer = (String) req.getParameter("ssncustomer");
		System.out.println("ssn : "+ ssncustomer);
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		if(idbooking.equals("")) {
			System.out.println("Trying to get hotel name");
			String hotelname = con.getHotelnameByEmployeeId(employee_id);
			System.out.println(hotelname);
			String dateStart = req.getParameter("dateStart");
			String dateEnd = req.getParameter("dateEnd");
			String priceMin = req.getParameter("priceMin");
			String priceMax = req.getParameter("priceMax");
			String capacity = req.getParameter("capacity");
			System.out.println("Trying to get renting infos");
			
			ArrayList<String[]> roomsAvailable = con.getAllAvailRoomsInHotel(hotelname, dateStart, dateEnd, capacity, priceMin, priceMax);
			
			System.out.println("Passed the getAllavailable with success");
			req.setAttribute("ssncustomer", ssncustomer);
			req.setAttribute("RoomsOK", roomsAvailable);
			req.setAttribute("dateStart", dateStart);
			req.setAttribute("dateEnd", dateEnd);
			
			req.getRequestDispatcher("rent_a_room.jsp").forward(req, resp);
		}
		
		if(!(idbooking.equals(""))) {
			idrenting = con.rentRoomOnlyWithIdBooking(idbooking);
			System.out.println(idrenting);
			if(idrenting.equals("")) {
				req.setAttribute("id_booking", idbooking);
				req.getRequestDispatcher("renting_failure.jsp").forward(req,resp);
			}
			else {
				String price = con.getPriceByBooking(idbooking);
				req.setAttribute("price", price);
				req.setAttribute("idrenting", idrenting);
				req.getRequestDispatcher("payment.jsp").forward(req,resp);	
			}
			
		}
		System.out.println("test1");
		
	}
}

