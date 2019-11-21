import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import javax.servlet.http.HttpSession;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import com.google.gson.Gson;
import javax.servlet.RequestDispatcher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


@WebServlet("/SalesReports")

public class SalesReports extends HttpServlet {

	/* TV Page Displays all the TVs and their Information in Game Speed */
	static DBCollection allReviews;

	static Connection conn = null;
	HttpSession session;
	String username;

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
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		MySqlDataStoreUtilities.getAllProducts();
		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");
		pw.print("<div style='margin-top:3%;margin-left:4%;'>");

		pw.print("<a style='font-size: 28px;'><u>Sold Products with left Over Stock:</u></a>");
		pw.print("<div  class='table-responsive-lg' style='margin-top:3%;'>");
		pw.print("<table class='table table-striped' style='width:200%;'>");
		pw.print("<tr>");
		pw.print("<th style='text-align:center;width:40%' scope='col' style='margin-left:12%;'>Product Name</th>");					
		pw.print("<th style='text-align:center;width:20%' scope='col' style='margin-left:2%;'>Image</th>");		
		pw.print("<th style='text-align:center;width:10%' scope='col' style='margin-left:2%;'>Price</th>");					
		pw.print("<th style='text-align:center;width:10%' scope='col' style='margin-left:2%;'>Quantity Sold</th>");		
		pw.print("<th style='text-align:center;width:10%' scope='col' style='margin-left:2%;'>Total Revenue</th>");		
		pw.print("<th style='text-align:center;width:10%' scope='col' style='margin-left:2%;'>Quantity Remaining</th>");		
		pw.print("</tr>");
		pw.print("</table>");
		pw.print("<div style='max-height:350px;overflow:auto;'>");
		pw.print("<table class='table table-striped' style='width:200%;'>");
		try{
			getConnection();
			Statement stmt = conn.createStatement();
			String query = "Select * from product_catalog where p_quantity<15";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Integer quantityLeftOut = Integer.parseInt(rs.getString("p_quantity"));
				Integer quantitySold = 15 - quantityLeftOut;
				Double totalRev = quantitySold * Double.parseDouble(rs.getString("p_price"));
				totalRev = Math.round(totalRev*100D)/100D;
				pw.print("<tr>");
				pw.print("<td style='text-align:center;width:40%' style='margin-left:2%;'>"+rs.getString("p_name")+"</td>");
				pw.print("<td style='text-align:center;width:20%' style='margin-left:2%;'><img src='"+rs.getString("p_image")+"' style='width:5%;height:5%'></td>");				
				pw.print("<td style='text-align:center;width:10%' style='margin-left:2%;'>"+rs.getString("p_price")+"&nbsp;$</td>");				
				pw.print("<td style='text-align:center;width:10%' style='margin-left:2%;'>"+quantitySold+"&nbsp;units</td>");
				pw.print("<td style='text-align:center;width:10%' style='margin-left:2%;'>"+totalRev+"&nbsp;$</td>");
				pw.print("<td style='text-align:center;width:10%' style='margin-left:2%;'>"+quantityLeftOut+"&nbsp;units</td>");				
				pw.print("</tr>");
			}		
		}catch(Exception e){
			System.out.print(e);
		}
		pw.print("</table></div>");
		pw.print("<hr style='margin-top: 8%'>");

		pw.print("<div style='margin-top:3%;'>");
		pw.print("<a style='font-size: 28px;'><u>Product Sales:</u></a>");
		pw.print("<span style='margin-left:5%;'><a href='DataVisualization1'><span class='glyphicon'>Click_here!</span></a></span>");
		pw.print("<hr style='margin-top: 4%'>");

		pw.print("<a style='font-size: 28px;'><u>Sales on a Particular Date:</u></a>");
		pw.print("<div  class='table-responsive-lg' style='margin-top:3%;'>");
		pw.print("<table class='table table-striped' style='width:200%;'>");
		pw.print("<tr>");
		pw.print("<th style='text-align:center;width:50%' scope='col' style='margin-left:12%;'>Ordered Date</th>");
		pw.print("<th style='text-align:center;width:25%' scope='col' style='margin-left:12%;'>Total Revenue Generated</th>");							
		pw.print("<th style='text-align:center;width:25%' scope='col' style='margin-left:2%;'>Total Products Purchased</th>");		
		pw.print("</tr>");
		pw.print("</table>");
		pw.print("<div style='max-height:350px;overflow:auto;'>");
		pw.print("<table class='table table-striped' style='width:200%;'>");
		try{
			getConnection();
			Statement stmt = conn.createStatement();
			String query = "select count(*) as count, DATE_FORMAT(o_order_date, '%e %b, %Y') as date, sum(o_price) as totalPrice from orders group by o_order_date desc";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Double price = Double.parseDouble(rs.getString("totalPrice"));
				Double totalRevenue = Math.round(price*100D)/100D;
				pw.print("<tr>");
				pw.print("<td style='text-align:center;width:50%' style='margin-left:2%;'>"+rs.getString("date")+"</td>");
				pw.print("<td style='text-align:center;width:25%' style='margin-left:2%;'>"+totalRevenue+"&nbsp;$</td>");				
				pw.print("<td style='text-align:center;width:25%' style='margin-left:2%;'>"+rs.getString("count")+"&nbsp;Products</td>");				
				pw.print("</tr>");
			}		
		}catch(Exception e){
			e.printStackTrace();
			System.out.print(e);
		}
		pw.print("</table></div>");

		pw.print("</div></div></div></div></div></div>");


		pw.print("</div></div></div></div></div></div>");
 	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

			try{
				getConnection();
				Statement stmt = conn.createStatement();
				String query = "Select * from product_catalog where p_quantity<15 order by p_quantity";
				ResultSet rs = stmt.executeQuery(query);

				JSONArray json = new JSONArray();
				ResultSetMetaData rsmd = rs.getMetaData();

				while(rs.next()){
					Integer quantityLeftOut = Integer.parseInt(rs.getString("p_quantity"));
					Integer quantitySold = 15 - quantityLeftOut;
					Double totalRev = quantitySold * Double.parseDouble(rs.getString("p_price"));
					totalRev = Math.round(totalRev*100D)/100D;
					JSONObject obj = new JSONObject();
					obj.put("product_name",rs.getString("p_name"));
					obj.put("product_quantity",totalRev);
					json.put(obj);
				}		

	            String reportJson = new Gson().toJson(json);
	            response.setContentType("application/JSON");
	            response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(reportJson);
			}catch(Exception e){
				System.out.print(e);
			}

	} 	
}
