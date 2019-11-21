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
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.json.*; 

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.nio.file.*;


@WebServlet("/SearchItem")

public class SearchItem extends HttpServlet {

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
	static DBCollection allReviews;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session =request.getSession(true);
		String userName= "";
		String userType= "";
		if(session.getAttribute("username")!=null){
			userName = session.getAttribute("username").toString();
			userName = Character.toUpperCase(userName.charAt(0)) + userName.substring(1);
			userType = session.getAttribute("usertype").toString();
			userType = Character.toUpperCase(userType.charAt(0)) + userType.substring(1);			
		}
		System.out.print("UserType: "+userType);
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String productName = request.getParameter("myProduct");
		System.out.print(productName);
		int NumberOfReviews;

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");						
		utility.printHtml("LeftNavigationBar.html");

		try{
			getConnection();
			String selectproductQuery ="select * from product_catalog where p_name=?";

			System.out.print(utility.getUserName());
			PreparedStatement pst = conn.prepareStatement(selectproductQuery);
			pst.setString(1,productName);			
			System.out.print(pst);
			ResultSet rs = pst.executeQuery();
			pw.print("<div class='container' style='margin-top: 5%'><h2 style='margin-left: 5%;font-weight: bolder;font-style: italic;'><u>Product</u></h2><hr style='margin-left: 5%;margin-top: 3%'><div class='row' style='margin-top: 3%;margin-left: 10%'><div style='margin-left: 0%' class='column' >");
			while (rs.next()){
				System.out.print(rs.getString("p_image"));				
				pw.print("<img src='"+rs.getString("p_image")+"'style='width: 25%'></div>");
				pw.print("<div style='margin-left: 5%;margin-top: 6%' class='column' >");
				pw.print("<h3 class='caption text-center' style='font-weight: bolder;'>"+rs.getString("p_name")+"</h3></div></div><hr style='margin-left: 5%;margin-top: 3%'><div style='margin-top: 5%'><h2 style='margin-left: 5%; font-weight: bolder;font-style: italic;'><u>Product Details:</u></h2><div class='container' style='margin-left: 5%;margin-top: 3%'>");
				pw.print("<div class='row'><div class='col col-md-6'>");
				pw.print("<h2>Product Type: <span style='font-weight: bolder;'>"+rs.getString("p_type")+"</span></h2>");
				pw.print("<h2>Model Number: <span style='font-weight: bolder;'>"+rs.getString("p_model")+"</span></h2>");
				pw.print("<h2>SKU Number: <span style='font-weight: bolder;'>"+rs.getString("p_sku")+"</span></h2></div><div class='col col-md-6'>");

				pw.print("<h2>Product Manufacturer: <span style='font-weight: bolder;'> "+rs.getString("p_manufacturer")+"</span></h2>");
				pw.print("<h2>Price: <span style='font-weight: bolder;'> $ "+rs.getString("p_price")+"</span></h2>");
				pw.print("<h2>Discount: <span style='font-weight: bolder;'> "+rs.getString("p_discount")+" %</span></h2></div></div>");
				pw.print("<hr style='margin-top: 3%'>");
				pw.print("<div style='margin-top: 5%'><h2 style='font-weight: bolder;font-style: italic;'><u>Actions:</u></h2>");				
				if(userType.equals("Customer")){
					pw.print("<form method='post' action='AddToCart'><div style='margin-top: 5%' class='row'><div class='col col-md-4'><span><button class='btn btn-success' type='submit'>Add to Cart</button></span></div><input type='hidden' name='userName' value='"+userName+"'/><input type='hidden' name='id' value='"+rs.getString("p_id")+"'/><input type='hidden' name='sku' value='"+rs.getString("p_sku")+"'/><input type='hidden' name='type' value='smarts'/><input type='hidden' name='image' value='"+rs.getString("p_image")+"'/><input type='hidden' name='model' value='"+rs.getString("p_model")+"'/><input type='hidden' name='price' value='"+rs.getString("p_price")+"'/><input type='hidden' name='manufacturer' value='"+rs.getString("p_manufacturer")+"'/><input type='hidden' name='name' value='"+rs.getString("p_name")+"'/><input type='hidden' name='p_quantity' value='"+rs.getString("p_quantity")+"'/></form><form style='margin-top:2%' method='post' action='WriteReview'><div class='col col-md-4'><span><button class='btn btn-warning' type='submit'>Write Review</button></span></div><input type='hidden' name='id' value='"+rs.getString("p_id")+"'/><input type='hidden' name='type' value='phones'/><input type='hidden' name='image' value='"+rs.getString("p_image")+"'/><input type='hidden' name='model' value='"+rs.getString("p_model")+"'/><input type='hidden' name='manufacturer' value='"+rs.getString("p_manufacturer")+"'/><input type='hidden' name='sku' value='"+rs.getString("p_sku")+"'/><input type='hidden' name='name' value='"+rs.getString("p_name")+"'/></form><form style='margin-top:2%' method='post' action='ViewReview'><div class='col col-md-4'><span><button class='btn btn-primary' type='submit'>View Review</button></span></div><input type='hidden' name='id' value='"+rs.getString("p_id")+"'/><input type='hidden' name='type' value='phones'/><input type='hidden' name='image' value='"+rs.getString("p_image")+"'/><input type='hidden' name='model' value='"+rs.getString("p_model")+"'/><input type='hidden' name='manufacturer' value='"+rs.getString("p_manufacturer")+"'/><input type='hidden' name='name' value='"+rs.getString("p_name")+"'/></div>");					
				}else{
					pw.print("<form method='get' action='UpdateProduct'><div style='margin-top: 5%' class='row'><div class='col col-md-6'><span><button class='btn btn-success' type='submit'>Update Product</button></span></div><input type='hidden' name='p_id' value='"+rs.getString("p_id")+"'><input type='hidden' name='p_name' value='"+rs.getString("p_name")+"'><input type='hidden' name='p_image' value='"+rs.getString("p_image")+"'><input type='hidden' name='p_model' value='"+rs.getString("p_model")+"'><input type='hidden' name='p_type' value='"+rs.getString("p_type")+"'><input type='hidden' name='p_discount' value='"+rs.getString("p_discount")+"'><input type='hidden' name='p_on_sale' value='"+rs.getString("p_on_sale")+"'><input type='hidden' name='p_model' value='"+rs.getString("p_model")+"'><input type='hidden' name='p_sku' value='"+rs.getString("p_sku")+"'><input type='hidden' name='p_price' value='"+rs.getString("p_price")+"'><input type='hidden' name='p_manufacturer' value='"+rs.getString("p_manufacturer")+"'></form><form style='margin-top:2%' method='post' action='DeleteProduct'><div class='col col-md-6'><span><button class='btn btn-warning' type='submit'>Delete Product</button></span></div><input type='hidden' name='p_id' value='"+rs.getString("p_id")+"'><input type='hidden' name='p_name' value='"+rs.getString("p_name")+"'><input type='hidden' name='p_discount' value='"+rs.getString("p_discount")+"'><input type='hidden' name='p_on_sale' value='"+rs.getString("p_on_sale")+"'></div></form>");									
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.print(e);
		}

		pw.print("</div></div></div>");

	}
}