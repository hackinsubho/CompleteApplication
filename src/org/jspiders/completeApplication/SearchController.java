package org.jspiders.completeApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/search")
public class SearchController extends HttpServlet{
	PrintWriter pw;
	PreparedStatement ps;
	Connection con;

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	ServletContext ctx = req.getServletContext();
	String driver=ctx.getInitParameter("driver");
	String pass = ctx.getInitParameter("pass");
	String user = ctx.getInitParameter("user");
	String url = ctx.getInitParameter("url");
	pw = resp.getWriter();
	String stream = req.getParameter("st");
	try {
		Class.forName(driver);
		con = DriverManager.getConnection(url,user,pass);
		String qry = "select * from db.studentinfo where stream=?";
		ps = con.prepareStatement(qry);
		ps.setString(1, stream);
		ResultSet rs = ps.executeQuery();
		
			
			while (rs.next()) {
				
				String out="<html>" + 
						"<body>" + 
						"<table border=\"1px\">" + 
						"<tr padding=\"3px\">" + 
						"<td>"+rs.getInt(1)+"</td>" + 
						"<td>"+rs.getString(2)+"</td>" + 
						"<td>"+rs.getDouble(3)+"</td>" + 
						"</tr>" + 
						"</table>"+ 
						"</body>" + 
						"</html>";
				pw.println(out);
			
			}
			
		
	} catch (ClassNotFoundException | SQLException e) {

		e.printStackTrace();
	}
		
}
}
