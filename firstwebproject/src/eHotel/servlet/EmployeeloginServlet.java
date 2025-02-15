package eHotel.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eHotel.connections.PostgreSqlConn;
import eHotel.entities.employee;

public class EmployeeloginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		employee account = new employee();
		String employee_id = req.getParameter("username");
		String pwd = req.getParameter("pwd");
		
		PostgreSqlConn con = new PostgreSqlConn();
		String pwdfromdb = con.getpwdbyUname(employee_id);
		
		
		if (pwd.equals(pwdfromdb)) {			
				System.out.println("success");
				req.setAttribute("employee_id", employee_id);
				req.getRequestDispatcher("employee_checkin.jsp").forward(req, resp);
				//resp.sendRedirect("employee_checkin.jsp?employee_id="+username);
				return;			
		}
		resp.sendRedirect("login_failure.jsp");
		return;
	}
}