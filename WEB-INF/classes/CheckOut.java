import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {

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
	        Utilities Utility = new Utilities(request, pw);
	        String action = request.getParameter("formAction");
	        if(request.getParameter("formAction") == null){
				storeOrders(request, response);
	        }else{
	        	System.out.print("Item Remove Called");
	        	System.out.print(request.getParameter("productToDelete"));
				try{
					getConnection();
					String deleteCartQuery ="delete from carts where p_name=?";
					PreparedStatement pst = conn.prepareStatement(deleteCartQuery);
					pst.setString(1,request.getParameter("productToDelete"));			
					pst.executeUpdate();

				}catch(Exception e){
					System.out.print(e);
				}	        	
	        }
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 

		//get the order product details	on clicking submit the form will be passed to submitorder page	
		
	    String userName = session.getAttribute("username").toString();
		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div class='col-sm-12 col-md-12 col-lg-10' style='margin-top: 4%'>");
		pw.print("<form name ='CheckOut' action='Payment' method='post'>");
		pw.print("<div class='container' style='margin-top: 2%;'>"+
				 "<h2 style='font-weight: bolder;'>Customer Name:<span style='font-weight: bolder;'>&nbsp;&nbsp;"+userName+"</span></h2>"+
				 "<hr style='margin-top: 3%'>");
		// for each order iterate and display the order name price
		double total = 0;
		int i=0;

			try{
				getConnection();
				String selectproductsQuery ="select * from carts where userName=?";
				System.out.print(utility.getUserName());
				PreparedStatement pst = conn.prepareStatement(selectproductsQuery);
				pst.setString(1,utility.getUserName());			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){  
					pw.print("<div class='row' style='margin-top: 3%;margin-left: 5%'>");
					pw.print("<div  class='column' >"+"<h3 class='caption text-center'>"+rs.getString("p_name")+"</h3>"+"</div>");
					pw.print("<div  class='column' style='margin-left: 15%'>"+"<h1>$"+Double.parseDouble(rs.getString("p_price"))+"</h1>"+"</div></div><hr style=';margin-top: 3%'>");
					total = total +Double.parseDouble(rs.getString("p_price"));
					total = Math.round(total*100D)/100D;
					i++;					
				}			

				pw.print("<div class='row' style='margin-top: 3%;margin-left: 5%'><div class='column' ><h3 class='caption text-center'>Total</h3>");
	    		pw.print("</div><div style='margin-left: 15%' class='column' ><h1>$"+total+"</h1></div></div><hr style='margin-top: 1%'>");
	    		pw.print("<div class='row' style='margin-top: 3%;margin-left: 5%'><div class='column' >");
	    		pw.print("<h3><u>Payment Details:</u></h3><div style='margin-top:5%'><form name ='CheckOut' action='Payment' method='post'><div class='form-row'><div class='form-group col-md-6'><label for='inputEmail4'>Credit Card Number</label>");
	    		pw.print("<input type='number' class='form-control' id='inputEmail4' name='creditCardNo' placeholder='Credit Card Number'></div><div class='form-group col-md-6'>");
	    		pw.print("<label for='inputPassword4'>Name on the Card</label><input type='text' class='form-control' name='nameOnCard' id='inputPassword4' placeholder='Name on the Card'></div></div><div class='form-group'><label for='inputAddress'>Billing Address</label><input type='text' class='form-control' id='inputAddress' name='userAddress' placeholder='1234 Main St'></div><div class='form-group'><label for='inputAddress2'>Billing Address 2</label>");
	    		pw.print("<input type='text' class='form-control' id='inputAddress2' placeholder='Apartment, studio, or floor'></div><div class='form-row'><div class='form-group col-md-6'><label for='inputCity'>City</label><input type='text' class='form-control' id='inputCity'></div>");
				pw.print("<div class='form-group col-md-6'><label for='inputZip'>Zip</label><input type='text' class='form-control' id='inputZip'></div></div><div style='float:right'><button type='submit' class='btn btn-lg btn-success'>Place Order</button></div></form></div></div></div></div></div>");

			}catch(Exception e){
				System.out.print(e);
			}
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}  			
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    }
}
