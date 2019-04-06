package eHotel.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eHotel.connections.PostgreSqlConn;

public class RoombookServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		employee account = new employee();
		String userSSN = req.getParameter("userSSN");
		String custName = req.getParameter("custName");
		String hotelChain = req.getParameter("hotelChain");
		String rate = req.getParameter("rate");
		String area = req.getParameter("area");
		String dateStart = req.getParameter("dateStart");
		String dateEnd = req.getParameter("dateEnd");
		String capacity = req.getParameter("capacity");
		String roomMin = req.getParameter("minRoom");
		String roomMax = req.getParameter("maxRoom");
		String priceMin = req.getParameter("priceMin");
		String priceMax = req.getParameter("priceMax");
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		System.out.println("On va chercher");
		
		ArrayList<String[]> results = con.getAllAvailRooms(dateStart, dateEnd, capacity, priceMin, priceMax, hotelChain, rate, area, roomMin, roomMax);
		
		System.out.println("On a trouvé");
		
		req.setAttribute("CustName", custName);
		req.setAttribute("RoomsOK", results);
		req.setAttribute("userSSN", userSSN);
		req.setAttribute("dateStart", dateStart);
		req.setAttribute("dateEnd", dateEnd);
		
		req.getRequestDispatcher("booking2.jsp").forward(req, resp);
		return;	
	}
}