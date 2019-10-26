import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
 
@WebServlet("/DeleteProduct")

public class DeleteProduct extends HttpServlet {
	static Connection conn = null;

	public static void getConnection()
	{

		try
		{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BDDB?autoReconnect=true&useSSL=false","root","edenUbuntu");							
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
	}
	/* Fitness Page Displays all the Fitnesss and their Information in Game Speed */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		try{
			getConnection();
			String deleteproductsQuery ="Delete from product_catalog where p_id=?";
			System.out.print(request.getParameter("p_id"));
			PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
			pst.setString(1,request.getParameter("p_id"));			
			pst.executeUpdate();
		}catch(Exception e){
			System.out.print(e);
		}

		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");						
		pw.print("<div class='col-sm-12 col-md-12 col-lg-10' style='margin-top:2%;margin-left:10%'>");
    	pw.print("<h2><u>Product Deletion Confirmation:</u></h2>");
    	pw.print("<div style='margin-top:10%'>");
    	pw.print("<div class='card card-price'>");
      	pw.print("<div style='margin-left:3%;margin-top:3%' class='card-img'>");
        pw.print("<a href='#'>");
        pw.print("<div class='card-caption'>");
        pw.print("</div></a></div>");
        pw.print("<div class='card-body'>");
        pw.print("<div class='price'>Product Name: &nbsp;&nbsp;"+request.getParameter("p_name")+"</div>");                        
        pw.print("<div class='lead'><h3><u>Product Details</u></h3></div>");
        pw.print("<a href='BestDealApp/ViewProductsAdded' class='btn btn-primary btn-lg btn-block buy-now'>");
        pw.print("View Added Products <span class='glyphicon glyphicon-shopping-cart'></span>");
        pw.print("</a></div></div></div></div></div></div></div>");           
	}
}
