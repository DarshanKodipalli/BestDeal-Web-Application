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

@WebServlet("/UpdateProduct")

public class UpdateProduct extends HttpServlet {
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
		displayUpdateProduct(request, response);
	}

	/* Display UpdateProduct Details of the Customer (Name and Usertype) */

	protected void displayUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			pw.print("<div class='container' style='margin-top: 5%'>");
				pw.print("<h2 style='margin-left: 8%;font-weight: bolder;font-style: italic;'>Product Details..</h2>");
				pw.print("<hr style='margin-left: 8%;margin-top: 3%'>");
				pw.print("<div class='row' style='margin-top: 3%;margin-left: 8%'>");
					pw.print("<div style='margin-left: 3%' class='column' >");
						pw.print("<img src='"+request.getParameter("p_image")+"' style='width: 25%'>");      
						pw.print("<h3 class='caption text-center'>"+request.getParameter("p_name")+"</h3>");
					pw.print("</div>");
					pw.print("<div style='margin-left: 15%' class='column' >");
						pw.print("<h3>");
						pw.print("Model:<span style='font-weight: bolder;'>&nbsp;"+request.getParameter("p_model")+"</span>");
						pw.print("</h3>");
						pw.print("<h3>");
						pw.print("SKU:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;"+request.getParameter("p_model")+"</span>");
						pw.print("</h3>");
						pw.print("<h3>");
						pw.print("Price:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;$ "+request.getParameter("p_price")+"</span>");
						pw.print("</h3>");
						pw.print("<h3>");
						pw.print("Type:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;"+(Character.toUpperCase(request.getParameter("p_type").charAt(0)) + request.getParameter("p_type").substring(1))+"</span>");
						pw.print("</h3>");
						pw.print("<h3>");
						pw.print("Manufacturer:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;"+request.getParameter("p_manufacturer")+"</span>");
						pw.print("</h3>");
						pw.print("<h3>");
						pw.print("Discount:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;"+request.getParameter("p_discount")+"&nbsp;%</span>");
						pw.print("</h3>");
					pw.print("</div>  ");      
				pw.print("</div>");
    

		pw.print("<div style='margin-top: 5%'>");
		pw.print("<h2 style='margin-left: 8%; font-weight: bolder;font-style: italic;'>Fields that can be Updated..</h2>");
		pw.print("<hr style='margin-left: 8%;margin-top: 3%'>");
		pw.print("<div class='container' style='margin-left: 8%;margin-top: 5%");
		
		pw.print("<div style='font-size: 130%;margin-top:5%;'>");
			pw.print("<div class='form-row' style='margin-top:5%;'>");
				pw.print("<form method='post' action='ProductUpdateConfirmation'>");
				pw.print("<div class='form-group col-md-6'>");
					pw.print("<label for='inputEmail4'>Product Name</label>");
					pw.print("<input type='text' class='form-control' style='border: none;' name='p_name' value='"+request.getParameter("p_name")+"' id='inputEmail4'>");
					pw.print("</div>");
				pw.print("<div class='form-group col-md-6'>");
					pw.print("<label for='inputPassword4'>Product Price</label>");
					pw.print("<input type='text' class='form-control' style='border: none;' name='p_price' value='"+request.getParameter("p_price")+"' id='inputEmail4'>");
				pw.print("</div>");
			pw.print("</div>");
			pw.print("<div class='form-row'>");
				pw.print("<div class='form-group col-md-6'>");
					pw.print("<label for='inputEmail4'>Product Model</label>");
					pw.print("<input type='text' class='form-control' style='border: none;' name='p_model' value='"+request.getParameter("p_model")+"' id='inputEmail4'>");
				pw.print("</div>");
				pw.print("<div class='form-group col-md-6'>");
					pw.print("<label for='inputPassword4'>Product SKU</label>");
					pw.print("<input type='text' class='form-control' style='border: none;' name='p_sku' value='"+request.getParameter("p_sku")+"' id='inputEmail4'>");
				pw.print("</div>");
			pw.print("</div>");
			
			pw.print("<div class='form-row'>");
				pw.print("<div class='form-group col-md-6'>");
					pw.print("<label for='inputEmail4'>Product On Sale?</label>");
					pw.print("<input type='text' class='form-control' style='border: none;' name='p_on_sale' value='"+request.getParameter("p_on_sale")+"' id='inputEmail4'>");
				pw.print("</div>");
				pw.print("<div class='form-group col-md-6'>");
					pw.print("<label for='inputPassword4'>Product Discount</label>");
					pw.print("<input type='text' class='form-control' style='border: none;' name='p_discount' value='"+request.getParameter("p_discount")+"' id='inputEmail4'>");
				pw.print("</div>");
			pw.print("</div>");

			pw.print("<div style='float:right;margin-top:5%'>");
					pw.print("<input type='hidden' name='p_id' value='"+request.getParameter("p_id")+"'><input type='hidden' name='p_name' value='"+request.getParameter("p_name")+"'>");
					pw.print("<input type='hidden' name='p_image' value='"+request.getParameter("p_image")+"'><input type='hidden' name='p_model' value='"+request.getParameter("p_model")+"'>");
					pw.print("<input type='hidden' name='p_type' value='"+request.getParameter("p_type")+"'><input type='hidden' name='p_discount' value='"+request.getParameter("p_discount")+"'><input type='hidden' name='p_on_sale' value='"+request.getParameter("p_on_sale")+"'><input type='hidden' name='p_model' value='"+request.getParameter("p_model")+"'>");
					pw.print("<input type='hidden' name='p_sku' value='"+request.getParameter("p_sku")+"'><input type='hidden' name='p_price' value='"+request.getParameter("p_price")+"'>");
					pw.print("<input type='hidden' name='p_manufacturer' value='"+request.getParameter("p_manufacturer")+"'>");						
				pw.print("<button type='submit' class='btn btn-success'>Update Product</button>");
				pw.print("</form>");
			pw.print("</div>");


		pw.print("</div>");
		pw.print("</div>");
		pw.print("</div>");

		}
		catch(Exception e)
		{
			System.out.print("Exception");
			e.printStackTrace();
		}
	}
}
