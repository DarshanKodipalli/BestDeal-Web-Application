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
import java.util.*;

@WebServlet("/Trending")

public class Trending extends HttpServlet {

	/* TV Page Displays all the TVs and their Information in Game Speed */
	static DBCollection allReviews;

	static Connection conn = null;
	HttpSession session;
	String username;

	ArrayList <Mostsold> mostsold = new ArrayList <Mostsold> ();
    ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
	ArrayList <Bestrating> bestrated = new ArrayList <Bestrating> ();


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
		String name = null;
		String CategoryName = request.getParameter("maker");
        System.out.print("Trending");
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");						
		pw.print("<div style='margin-top:5%;margin-left:15%;'>");
		pw.print("<a style='font-size: 28px;font-style:Italic'><u>Best Rated Products:</u></a>");
		pw.print("<div  class='table-responsive-lg' style='margin-top:5%;'>");
		pw.print("<table class='table table-striped' style='width:200%;'>");
		pw.print("<thead>");
		pw.print("<tr>");
		pw.print("<th style='text-align:center;' scope='col'>Product Name</th>");
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Product Manufactrure</th>");					
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Product Model</th>");
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Product Price</th>");		
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>User Rating</th>");		
		pw.print("</tr>");
		pw.print("</thead>");
		pw.print("<tbody>");
		try{
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("customerReviews");
			allReviews= db.getCollection("allReviews");	
			int retlimit =5;
			DBObject sort = new BasicDBObject();
			sort.put("userRating",-1);
			DBCursor cursor = allReviews.find().limit(retlimit).sort(sort);
			while(cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();
				String prodcutnm = obj.getString("productName");
				String rating = obj.getString("userRating");
				System.out.print(prodcutnm);
				System.out.print(rating);
				pw.print("<tr>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+obj.getString("productName")+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+obj.getString("productManufacturer")+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+obj.getString("productModel")+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+"$ "+obj.getString("productPrice")+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+obj.getString("userRating")+"</td>");
				pw.print("</tr>");
			}			
		}catch(Exception e){
			System.out.print("Exception");
			e.printStackTrace();
		}
	  	pw.print("</table>");
	  	pw.print("</div>");	

		pw.print("<hr style='margin-top: 8%'>");
		pw.print("<a style='font-size: 28px;font-style:Italic'><u>Most Sold Products:</u></a>");
		pw.print("<div  class='table-responsive-lg' style='margin-top:5%;'>");
		pw.print("<table class='table table-striped' style='width:200%;'>");
		pw.print("<thead>");
		pw.print("<tr>");
		pw.print("<th style='text-align:center;' scope='col'>Product Name</th>");
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Number of times Sold</th>");					
		pw.print("</tr>");
		pw.print("</thead>");
		pw.print("<tbody>");
		try{
			getConnection();
			String selectOrderQuery ="select count(*) as count,o_name as pname from orders group by o_name order by count(*) desc limit 5;";			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			ResultSet rs = pst.executeQuery();	
			while(rs.next()){
				pw.print("<tr>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("pname")+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+rs.getString("count")+"</td>");
				pw.print("</tr>");
			}
		}catch(Exception e){
			System.out.print("Exception");
			e.printStackTrace();
		    }
	  	pw.print("</table></div>");		  	

		pw.print("<hr style='margin-top: 8%'>");
		pw.print("<a style='font-size: 28px;font-style:Italic'><u>Most Sold Products by Zipcode:</u></a>");
		pw.print("<div  class='table-responsive-lg' style='margin-top:5%;'>");
		pw.print("<table class='table table-striped' style='width:200%;'>");
		pw.print("<thead>");
		pw.print("<tr>");
		pw.print("<th style='text-align:center;' scope='col'>Zip Code</th>");
		pw.print("<th style='text-align:center;' scope='col' style='margin-left:2%;'>Number of Products Sold in that Area</th>");					
		pw.print("</tr>");
		pw.print("</thead>");
		pw.print("<tbody>");
		try{
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("customerReviews");
			allReviews= db.getCollection("allReviews");	
	        DBObject groupProducts = new BasicDBObject("_id","$userZip"); 
	     	groupProducts.put("count",new BasicDBObject("$sum",1));
		    DBObject group = new BasicDBObject("$group",groupProducts);
		    DBObject limit=new BasicDBObject();
	        limit=new BasicDBObject("$limit",5);
		    DBObject sortFields = new BasicDBObject("count",-1);
		    DBObject sort = new BasicDBObject("$sort",sortFields);
		    AggregationOutput output = allReviews.aggregate(group,sort,limit);
   	        for (DBObject res : output.results()) {
				System.out.print("____________________");
				System.out.print(res);
				System.out.print("____________________");				
				String zipcode =(res.get("_id")).toString();
		        String count = (res.get("count")).toString();	
				System.out.print(zipcode);
				System.out.print(count);
				pw.print("<tr>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+zipcode+"</td>");
				pw.print("<td style='text-align:center;' style='margin-left:2%;'>"+count+"</td>");
				pw.print("</tr>");
			}
		}catch(Exception e){
			System.out.print("Exception");
			e.printStackTrace();
		    }
	  	pw.print("</table></div></div></div>");		  	


		pw.print("<hr style='margin-top: 3%'>");
		pw.print("</div></div></div></div>");
		utility.printHtml("Footer.html");
 	}
}
