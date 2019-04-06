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

public class CustomerRegisterServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
//		employee account = new employee();
		String custSSN = req.getParameter("custSSN");
		String custAddress = req.getParameter("custAddress");
		String custName = req.getParameter("custName");
		String custPwd = req.getParameter("custPwd");
		
		String[] param = new String[] {custSSN,custAddress, custName,custPwd};
		
		PostgreSqlConn con = new PostgreSqlConn();
		boolean pwdfromdb = con.insertNewCustomer(param);
		
		System.out.println(pwdfromdb);
		
		if (pwdfromdb) {			
				System.out.println("success");
				
				ArrayList<Booking> bookedRooms = con.getbookedRooms(custSSN);
				
				req.setAttribute("CustName", custName);
				req.setAttribute("bookedRooms", bookedRooms);

				req.getRequestDispatcher("booking.jsp").forward(req, resp);
				return;			
		}
		resp.sendRedirect("register_failure.jsp");
		return;
	}
	

}
