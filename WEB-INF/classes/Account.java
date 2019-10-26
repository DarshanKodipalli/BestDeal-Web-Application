import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.nio.file.*;

@WebServlet("/Account")

public class Account extends HttpServlet {
	private String error_msg;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        String action = request.getParameter("formAction");
//        System.out.print(request.getParameter("formAction").length);
        if(request.getParameter("formAction") == null){
        	displayAccount(request, response);        	
        }else{
	        if(action.equals("setInTransit") || action.equals(null)){
	        	System.out.print("In transit Called");
	        	System.out.print(request.getParameter("productToDelete"));
				try{
					getConnection();
					String updateProductQurey = "UPDATE orders SET o_status=? where o_id=?;";
					PreparedStatement pst = conn.prepareStatement(updateProductQurey);		
					pst.setString(1,"In Transit");
					pst.setString(2,request.getParameter("o_id"));
					pst.executeUpdate();
					displayAccount(request, response);
				}catch(Exception e){
					System.out.print(e);
				}	        	
	        }
	        if(action.equals("setDelivered")){
	        	System.out.print("In transit Called");
	        	System.out.print(request.getParameter("productToDelete"));
				try{
					getConnection();
					String updateProductQurey = "UPDATE orders SET o_status=? where o_id=?;";
					PreparedStatement pst = conn.prepareStatement(updateProductQurey);		
					pst.setString(1,"Delivered");
					pst.setString(2,request.getParameter("o_id"));
					pst.executeUpdate();
					displayAccount(request, response);
				}catch(Exception e){
					System.out.print(e);
				}	        	
	        }
	        if(action.equals("deleteOrder")){
	        	System.out.print("In transit Called");
	        	System.out.print(request.getParameter("productToDelete"));
				try{
					getConnection();
					String deleteCartQuery ="delete from orders where o_id=?";
					PreparedStatement pst = conn.prepareStatement(deleteCartQuery);
					pst.setString(1,request.getParameter("o_id"));			
					pst.executeUpdate();
					displayAccount(request, response);
				}catch(Exception e){
					System.out.print(e);
				}	        	
	        }
        }
	}

	/* Display Account Details of the Customer (Name and Usertype) */

	protected void displayAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try
         {
           response.setContentType("text/html");
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);
				session.setAttribute("login_msg", "Please Login to add items to cart");
				response.sendRedirect("Login");
				return;
			}
			HttpSession session=request.getSession();
			utility.printHtml("Header.html");
			utility.printHtml("Pre-Content.html");
			pw.print("<div style='margin-top:5%;margin-left:10%;'>");
			pw.print("<a style='font-size: 28px;font-style:Italic'><u>Order Summary:</u></a>");
			pw.print("<div style='float:right'><form method='get' action='ViewCustomers'><button class='btn btn-primary' type='submit'><i class='fa fa-person fa-lg' aria-hidden='true'>View Customers</i></button></form></div>");
			String userName=utility.getUserName();
			String userType=utility.getUserType();
			if(userType.equals("manager")){
				pw.print("<h3>User Name:&nbsp;&nbsp;<span style='font-weight:bolder'>"+userName+"</span></h3>");
				pw.print("<h3>User Type:&nbsp;&nbsp;&nbsp;&nbsp;<span style='font-weight:bolder'>"+"Sales Manager"+"</span></h3>");
				pw.print("<div  class='table-responsive-lg' style='margin-top:5%;'>");
				pw.print("<table class='table table-striped' style='width:250%;'>");
				pw.print("<thead>");
				pw.print("<tr>");
				pw.print("<th style='text-align:center;' scope='col'>Select</th>");
				pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Product Ordered</th>");
				pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Price</th>");		
				pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Order-Status</th>");					
				pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Delivery Address</th>");		
				pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Actions</th>");						
				pw.print("</tr>");
				pw.print("</thead>");
				pw.print("<tbody>");
				try{
					getConnection();
					String selectproductsQuery ="select * from orders";
					PreparedStatement pst = conn.prepareStatement(selectproductsQuery);
					ResultSet rs = pst.executeQuery();
					while(rs.next()){  
						pw.print("<tr>");
						pw.print("<td style='text-align:center;'><input type='radio' name='orderName' value='"+rs.getString("o_name")+"'></td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("o_name")+"</td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+"$ "+rs.getString("o_price")+"</td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("o_status")+"</td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+"$ "+rs.getString("o_address")+"</td>");
						pw.print("<input type='hidden' name='orderId' value='"+rs.getString("o_id")+"'>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'>");
						pw.print("<div class='row'><div class='col' style='margin-left:20%;font-size:150%'>");
						pw.print("<form method='get' action='Account'><button style='border:none;outline: none;background: none;cursor: pointer;' type='submit' name='formAction' value='setInTransit'><i class='fa fa-truck fa-lg' aria-hidden='true'></i></button><input type='hidden' name='o_id' value='"+rs.getString("o_id")+"'><input type='hidden' name='o_name' value='"+rs.getString("o_name")+"'><input type='hidden' name='o_image' value='"+rs.getString("o_image")+"'><input type='hidden' name='o_model' value='"+rs.getString("o_model")+"'><input type='hidden' name='o_type' value='"+rs.getString("o_type")+"'><input type='hidden' name='o_model' value='"+rs.getString("o_model")+"'><input type='hidden' name='o_sku' value='"+rs.getString("o_sku")+"'><input type='hidden' name='o_price' value='"+rs.getString("o_price")+"'><input type='hidden' name='o_manufacturer' value='"+rs.getString("o_manufacturer")+"'></form></div>");
						pw.print("<div class='col' style='margin-left:5%;font-size:150%'><form method='get' action='Account'><button name='formAction' value='setDelivered' style='border:none;outline: none;background: none;cursor: pointer;' type='submit'><i class='fa fa-check' aria-hidden='true'></i></button><input type='hidden' name='o_id' value='"+rs.getString("o_id")+"'><input type='hidden' name='o_name' value='"+rs.getString("o_name")+"'></form></div>");						
						pw.print("<div class='col' style='margin-left:5%;font-size:150%'><form method='get' action='Account'><button name='formAction' value='deleteOrder' style='border:none;outline: none;background: none;cursor: pointer;' type='submit'  name='Order' value='CancelOrder' ><i class='fa fa-trash-o' aria-hidden='true'></i></button><input type='hidden' name='o_id' value='"+rs.getString("o_id")+"'><input type='hidden' name='o_name' value='"+rs.getString("o_name")+"'></form></div>");						
						pw.print("</tr>");
					}
				}catch(Exception e){
					System.out.print(e);
				}
				pw.print("</tbody>");
				pw.print("</table></div>");
				pw.print("</div></div></div></div></div></div>");
			}else{
				pw.print("<h3>User Name:&nbsp;&nbsp;<span style='font-weight:bolder'>"+userName+"</span></h3>");
				pw.print("<h3>User Type:&nbsp;&nbsp;&nbsp;&nbsp;<span style='font-weight:bolder'>"+Character.toUpperCase(userType.charAt(0)) + userType.substring(1)+"</span></h3>");
				pw.print("<div  class='table-responsive-lg' style='margin-top:5%;'>");
				pw.print("<table class='table table-striped' style='width:200%;'>");
				pw.print("<thead>");
				pw.print("<tr>");
				pw.print("<th style='text-align:center;' scope='col'>Select</th>");
				pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Product Ordered</th>");
				pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Price</th>");		
				pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Order-Status</th>");					
				pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Delivery Address</th>");		
				pw.print("</tr>");
				pw.print("</thead>");
				pw.print("<tbody>");
				try{
					getConnection();
					String selectproductsQuery ="select * from orders where o_username=?";
					System.out.print(utility.getUserName());
					PreparedStatement pst = conn.prepareStatement(selectproductsQuery);
					pst.setString(1,utility.getUserName());			
					ResultSet rs = pst.executeQuery();
					while(rs.next()){  
						pw.print("<form method='get' action='ViewOrder'>");
						pw.print("<tr>");
						pw.print("<td style='text-align:center;'><input type='radio' name='orderName' value='"+rs.getString("o_name")+"'></td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("o_name")+"</td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+"$ "+rs.getString("o_price")+"</td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("o_status")+"</td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+"$ "+rs.getString("o_address")+"</td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'><input type='hidden' name='orderId' value='"+rs.getString("o_id")+"'></td>");
						pw.print("<td style='text-align:center;' style='margin-left:2%;'><input type='submit' class='btn btn-danger' name='Order' value='CancelOrder' class='btnbuy'><input type='hidden' name='orderName' value='"+rs.getString("o_name")+"/></td>");
						pw.print("</tr>");
						pw.print("</form>");
					}
				}catch(Exception e){
					System.out.print(e);
				}
				pw.print("</tbody>");
				pw.print("</table></div>");
				pw.print("</div></div></div></div></div></div>");
			}
		}
		catch(Exception e)
		{
			System.out.print("Exception");
			e.printStackTrace();
		}
	}
}
