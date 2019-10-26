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
 
@WebServlet("/ViewProductsAdded")

public class ViewProductsAdded extends HttpServlet {
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
		MySqlDataStoreUtilities.getAllProducts();
		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");
		pw.print("<div style='margin-top:3%;margin-left:.1%;'>");
		pw.print("<a style='font-size: 28px;'><u>All Products Added:</u></a>");
/*		User user=utility.getUser();
		pw.print("<h3><u>User Name:</u>&nbsp;&nbsp;"+user.getName()+"</h3>");
		pw.print("<h3><u>User Type:</u>&nbsp;&nbsp;&nbsp;&nbsp;"+user.getUsertype()+"</h3>");*/
		pw.print("<div  class='table-responsive-lg' style='margin-top:3%;'>");
		pw.print("<table class='table table-striped' style='width:200%;'>");
		pw.print("<thead>");
		pw.print("<tr>");
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Product Name</th>");					
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Price($)</th>");					
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Type</th>");
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Image</th>");		
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Manufacturer</th>");
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Model Number</th>");		
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Discount</th>");		
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Actions</th>");		
		pw.print("</tr>");
		pw.print("</thead>");
		pw.print("<tbody>");
		try{
			getConnection();
			Statement stmt = conn.createStatement();
			String query = "Select * from product_catalog";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pw.print("<tr>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("p_name")+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("p_price")+"</td>");				
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+Character.toUpperCase(rs.getString("p_type").charAt(0)) + rs.getString("p_type").substring(1)+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'><img src='"+rs.getString("p_image")+"' style='width:5%;height:5%'></td>");				
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("p_manufacturer")+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("p_model")+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("p_discount")+"&nbsp;%</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>");
				pw.print("<div class='row'><div class='col' style='margin-left:20%;font-size:150%'>");
				pw.print("<form method='get' action='UpdateProduct'><button style='border:none;outline: none;background: none;cursor: pointer;' type='submit'><i class='fa fa-pencil-square-o fa-lg' aria-hidden='true'></i></button><input type='hidden' name='p_id' value='"+rs.getString("p_id")+"'><input type='hidden' name='p_name' value='"+rs.getString("p_name")+"'><input type='hidden' name='p_image' value='"+rs.getString("p_image")+"'><input type='hidden' name='p_model' value='"+rs.getString("p_model")+"'><input type='hidden' name='p_type' value='"+rs.getString("p_type")+"'><input type='hidden' name='p_discount' value='"+rs.getString("p_discount")+"'><input type='hidden' name='p_on_sale' value='"+rs.getString("p_on_sale")+"'><input type='hidden' name='p_model' value='"+rs.getString("p_model")+"'><input type='hidden' name='p_sku' value='"+rs.getString("p_sku")+"'><input type='hidden' name='p_price' value='"+rs.getString("p_price")+"'><input type='hidden' name='p_manufacturer' value='"+rs.getString("p_manufacturer")+"'></form></div>");
				pw.print("<div class='col' style='margin-left:5%;font-size:150%'><form method='post' action='DeleteProduct'><button style='border:none;outline: none;background: none;cursor: pointer;' type='submit'><i class='fa fa-trash-o' aria-hidden='true'></i></button><input type='hidden' name='p_id' value='"+rs.getString("p_id")+"'><input type='hidden' name='p_name' value='"+rs.getString("p_name")+"'><input type='hidden' name='p_discount' value='"+rs.getString("p_discount")+"'><input type='hidden' name='p_on_sale' value='"+rs.getString("p_on_sale")+"'></form>");
				pw.print("</div></div></td>");
				pw.print("</tr>");
			}		
		}catch(Exception e){
			System.out.print(e);
		}
		pw.print("</tbody>");
		pw.print("</table></div>");
		pw.print("</div></div></div></div></div></div>");
	}
}
