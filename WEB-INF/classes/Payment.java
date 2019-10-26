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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.*;
import java.util.*;

@WebServlet("/Payment")

public class Payment extends HttpServlet {

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
		System.out.print("the user address is" +userAddress);
		System.out.print(creditCardNo);
		double total = 0.0;
		int i=0;
		
		Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 14); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        Date currentDatePlusThree = c.getTime();

		DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
		Date date1 = new Date();
		System.out.println(dateFormat1.format(date1));

		try{
			getConnection();

			String selectproductsQuery ="select * from carts where userName=?";
			System.out.print(utility.getUserName());
			PreparedStatement pst = conn.prepareStatement(selectproductsQuery);
			pst.setString(1,utility.getUserName());			
			ResultSet rs = pst.executeQuery();
			while(rs.next()){  
				total = total +Double.parseDouble(rs.getString("p_price"));
				total = Math.round(total*100D)/100D;
				String insertIntoCustomerOrderQuery = "INSERT INTO orders(o_username,o_name,o_price,o_type,o_image,o_manufacturer,o_model,o_sku,o_address,o_credit_card,o_status,o_order_date)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";	
				String updateProductQuantityStmt = "Update product_catalog set p_quantity=? where p_name=?";
				PreparedStatement pst1 = conn.prepareStatement(insertIntoCustomerOrderQuery);
				PreparedStatement updatestmt = conn.prepareStatement(updateProductQuantityStmt);					
				//set the parameter for each column and execute the prepared statement
				pst1.setString(1,utility.getUserName());
				pst1.setString(2,rs.getString("p_name"));
				pst1.setString(3,rs.getString("p_price"));
				pst1.setString(4,rs.getString("p_type"));
				pst1.setString(5,rs.getString("p_image"));
				pst1.setString(6,rs.getString("p_manufacturer"));
				pst1.setString(7,rs.getString("p_model"));
				pst1.setString(8,rs.getString("p_sku"));
				pst1.setString(9,userAddress);
				pst1.setString(10,creditCardNo);
				pst1.setString(11,"Ordered");
				pst1.setString(12,dateFormat1.format(date1));
				Integer updatedQuantity = Integer.parseInt(rs.getString("p_quantity"))-1;
				updatestmt.setInt(1,updatedQuantity);
				updatestmt.setString(2,rs.getString("p_name"));
				pst1.execute();				
				updatestmt.execute();
			}			
			String deleteCartsQuery ="Delete from carts where userName=?";
			PreparedStatement pst2 = conn.prepareStatement(deleteCartsQuery);
			pst2.setString(1,utility.getUserName());			
			pst2.executeUpdate();

		}catch(Exception e){
			System.out.print(e);
		}

		if(!userAddress.isEmpty() && !creditCardNo.isEmpty() )
		{
			int latestOrder = 0;
			try{
				getConnection();

				String selectCartsQuery ="select * from orders where o_username=?";
				System.out.print(utility.getUserName());
				PreparedStatement pst4 = conn.prepareStatement(selectCartsQuery);
				pst4.setString(1,utility.getUserName());			
				ResultSet rs = pst4.executeQuery();
				while (rs.next()) {
					latestOrder = Integer.parseInt(rs.getString("o_id"));
				}
				latestOrder +=1;
			}catch(Exception e){
				System.out.print(e);
			}
			utility.printHtml("Header.html");
			utility.printHtml("Pre-Content.html");						
/*			utility.printHtml("LeftNavigationBar.html");*/
			pw.print("<div class='col-sm-12 col-md-12 col-lg-10' style='margin-top:2%;margin-left:10%'>");

        	pw.print("<h2>Payment Confirmation:</h2>");
        	pw.print("<div class='card card-price'>");
          	pw.print("<div style='margin-left:3%;' class='card-img'>");
            pw.print("<a href='#'>");
            pw.print("<img style='width:100%' src='images/Carousel_and_Others/14.jpg' class='img-responsive'>");
            pw.print("<div class='card-caption'>");
            pw.print("</div></a></div>");
            pw.print("<div class='card-body'>");
            pw.print("<div class='price'>Order ID: &nbsp;&nbsp;"+latestOrder+"</div>");
            pw.print("<div class='lead'><h3>Thank you for Shopping with us</h3></div>");
            pw.print("<ul class='details'>");
            pw.print("<li><h4>Hope you had a great experience shopping with us</h4></li>");
            pw.print("<li><b>Total Payment received : $"+total+"</b>, Expect your items to be delivered by <span style='font-weight:bolder;'>"+currentDatePlusThree+"</span></li>");
            pw.print("</ul><a href='/BestDealApp/Home' class='btn btn-primary btn-lg btn-block buy-now'>");
            pw.print("Continue Shopping <span class='glyphicon glyphicon-shopping-cart'></span>");
            pw.print("</a></div></div></div></div></div></div>");            
			utility.printHtml("Footer.html");
		}else
		{
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");

			pw.print("<h4 style='color:red'>Please enter valid address and creditcard number</h4>");
			pw.print("</h2></div></div></div>");
			utility.printHtml("Footer.html");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);


	}
}
