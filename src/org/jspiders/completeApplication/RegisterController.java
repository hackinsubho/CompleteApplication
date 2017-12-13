package org.jspiders.completeApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/reg")
public class RegisterController extends HttpServlet{
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	ServletContext ctx = req.getServletContext();
	String driver=ctx.getInitParameter("driver");
	String pass = ctx.getInitParameter("pass");
	String user = ctx.getInitParameter("user");
	String url = ctx.getInitParameter("url");
	
	try {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, pass);
		String qry = "insert into jdbc.logininfo values(?,?,?)";
		PreparedStatement ps = con.prepareStatement(qry);
		String uname = req.getParameter("uname");
		String password = req.getParameter("pass");
		String name = req.getParameter("name");
		ps.setString(1, uname);
		ps.setString(2, password);
		ps.setString(3, name);
		int res = ps.executeUpdate();
		PrintWriter pw = resp.getWriter();
		if (res > 0) {
			pw.println(
					"<html><body><h3>You Registered Successfully</h3><a href=\"login.html\">Login</a></body></html>");
		} else {
			pw.println(
					"<html><body><h3>Registration Unsuccessfully</h3><a href=\"register.html\">Back</a></body></html>");
		}
	} catch (SQLException | ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
