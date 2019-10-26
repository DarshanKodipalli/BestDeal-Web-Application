import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.nio.file.*;
import java.sql.*;

@WebServlet("/ProductUpdateConfirmation")

public class ProductUpdateConfirmation extends HttpServlet {

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

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();


		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to Pay");
			response.sendRedirect("Login");
			return;
		}
		// get the payment details like credit card no address processed from cart servlet

		String userAddress=request.getParameter("userAddress");
		String creditCardNo=request.getParameter("creditCardNo");
		try{
			getConnection();
			String updateProductQurey = "UPDATE product_catalog SET p_id=?,p_type=?,p_name=?,p_price=?,p_image=?,p_manufacturer=?,p_model=?,p_sku=?,p_on_sale=?,p_discount=? where p_name=?;" ;				        			
			PreparedStatement pst = conn.prepareStatement(updateProductQurey);		
			pst.setString(1,request.getParameter("p_id"));
			pst.setString(2,request.getParameter("p_type"));
			pst.setString(3,request.getParameter("p_name"));
			pst.setDouble(4,Double.parseDouble(request.getParameter("p_price")));
			pst.setString(5,request.getParameter("p_image"));
			pst.setString(6,request.getParameter("p_manufacturer"));
			pst.setString(7,request.getParameter("p_model"));
			pst.setString(8,request.getParameter("p_sku"));
			pst.setString(9,request.getParameter("p_on_sale"));
			pst.setString(10,request.getParameter("p_discount"));
			pst.setString(11,request.getParameter("p_name"));
			pst.executeUpdate();

			utility.printHtml("Header.html");
			utility.printHtml("Pre-Content.html");						
			pw.print("<div class='col-sm-12 col-md-12 col-lg-10' style='margin-top:2%;margin-left:10%'>");

	    	pw.print("<h2>Product Updated:</h2>");
	    	pw.print("<div class='card card-price'>");
	      	pw.print("<div style='margin-left:3%;' class='card-img'>");
	        pw.print("<a href='#'>");
	        pw.print("<img style='width:100%;margin-left:30%' src='https://www.e-bas.com.au/wp-content/uploads/2015/08/bigstock-Updated-Red-D-Realistic-Paper-63435205.jpg' class='img-responsive'>");
	        pw.print("<div class='card-caption'>");
	        pw.print("</div></a></div>");
	        pw.print("<div class='card-body'>");
	        pw.print("<div class='price'>Product Updated: &nbsp;&nbsp;"+request.getParameter("p_name")+"</div>");
	        pw.print("<ul class='details'>");
	        pw.print("</ul><a href='/BestDealApp/Home' class='btn btn-primary btn-lg btn-block buy-now'>");
	        pw.print("Continue Operations <span class='glyphicon glyphicon-shopping-cart'></span>");
	        pw.print("</a></div></div></div></div></div></div>");            
			utility.printHtml("Footer.html");
		}catch(Exception e){
			System.out.print("Exception in Updating the record");
			e.printStackTrace();
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);


	}
}
