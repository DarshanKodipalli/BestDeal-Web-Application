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

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {

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
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String productName = request.getParameter("name");
		String productImage = request.getParameter("image");
		int NumberOfReviews;

		Utilities utility = new Utilities(request,pw);
/*		MongoDBDataStoreUtilities.selectReview(request.getParameter("name"));*/
		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");						
		utility.printHtml("LeftNavigationBar.html");

		int reviewsList=0;
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("customerReviews");
		allReviews= db.getCollection("allReviews");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("productName",request.getParameter("name"));

		DBCursor cursor = allReviews.find(whereQuery);

		System.out.print("All Reviews for the Product");
		reviewsList = cursor.size();
		pw.print("<div class='container' style='margin-top: 5%'>");
		pw.print("<h2 style='margin-left: 5%;font-weight: bolder;font-style: italic;'><u>Product</u></h2>");
		pw.print("<hr style='margin-left: 5%;margin-top: 3%'>");
		pw.print("<div class='row' style='margin-top: 3%;margin-left: 5%'>");
		pw.print("<div style='margin-left: 3%' class='column' >");
		pw.print("<img src='"+productImage+"' style='width: 25%'></div>");
		pw.print("<div style='margin-left: 15%;margin-top: 2%' class='column' >");
	    pw.print("<h3>");
	    pw.print("Product Name:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;"+request.getParameter("name")+"</span>");
	    pw.print("</h3>");
		pw.print("</div>");
		pw.print("</div>");
		pw.print("<hr style='margin-left: 5%;margin-top: 3%'>");
		pw.print("<div style='margin-top: 5%'>");
		pw.print("<h2 style='margin-left: 5%; font-weight: bolder;font-style: italic;'><u>Reviews ("+reviewsList+")</u></h2>");
		pw.print("<div class='container' style='margin-left: 5%;margin-top: 3%'>");
		
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject) cursor.next();
				System.out.print(obj.getString("productName"));

				pw.print("<div class='row'>");
				pw.print("<div class='col col-md-6'>");
				pw.print("<h2>"+obj.getString("userName")+" (Age: "+obj.getString("userAge")+",Gender: "+obj.getString("userGender")+")</h2>");
				pw.print("<span style='margin-top: 3%'>");
				pw.print("<p><u>Manufacturer:</u>&nbsp;<span style='font-weight: bolder;'>"+obj.getString("productManufacturer")+"</span></p>");				
				pw.print("<p><u>Retailer Name:</u>&nbsp;<span style='font-weight: bolder;'>"+obj.getString("retailerName")+"</span></p>");
				pw.print("<p><u>Retailer City:</u>&nbsp;<span style='font-weight: bolder;'>"+obj.getString("userCity")+"</span></p>          	");
				pw.print("<p><u>Retailer Zip:</u>&nbsp;<span style='font-weight: bolder;'>"+obj.getString("userZip")+"</span></p>");
				pw.print("<p><u>Pros Mentioned:</u>&nbsp;<span style='font-weight: bolder;'>"+obj.getString("pros")+"</span></p>          	");				
				pw.print("<p><u>Cons Mentioned:</u>&nbsp;<span style='font-weight: bolder;'>"+obj.getString("cons")+"</span></p>          	");
				pw.print("</span>");
				pw.print("<div style='margin-top:7%;'>");
				pw.print("<span style='margin-top:20%;'><p><u>User Rating:</u></p>");
				for (int i=0; i< Integer.parseInt(obj.getString("userRating")); i++) {
					pw.print("<span style='font-size: 220%' class='fa fa-star checked'></span>");
				}			
				for( int j=0; j< (5-Integer.parseInt(obj.getString("userRating")));j++){
					pw.print("<span style='font-size: 220%' class='fa fa-star'></span>");
				}	
				pw.print("</span>");
				pw.print("</div>");				
				pw.print("</div>");
				pw.print("<div class='col col-md-6'>");
				pw.print("<h2>Description</h2>");
				pw.print("<span style='margin-top: 3%'>");
				pw.print("<img alt src='https://www.bestbuy.com/~assets/bby/_com/ugc-raas/ugc-common-assets/ugc-badge-verified-check.svg'>Verified Product. | Posted on <span style='font-weight:bolder;'>"+obj.getString("userReviewDate")+"</span>");
				pw.print("<div style='margin-top: 5%'>");
				pw.print("<p><u>Product Quality:</u>&nbsp;<span style='font-weight: bolder;'>"+obj.getString("quality")+"</span></p>");
				pw.print("<p><u>Recommend:</u>&nbsp;<span style='font-weight: bolder;'>"+obj.getString("recommend")+"</span></p>       ");   
				pw.print("<p><u>Review:</u>&nbsp;<span style='font-weight: bolder;'>"+obj.getString("review")+"</span></p>                    ");
				pw.print("</div>");
				pw.print("</span>");
				pw.print("</div>");
				pw.print("</div>");
				pw.print("<hr style='margin-top: 3%'>");
			}

			pw.print("</div></div></div>");
/*

		DBCursor cursor = allReviews.find(whereQuery);
		System.out.print("\n");
		System.out.print("All Reviews for the Product");*/
/*		List<DBObject> allReviewsForAProduct = null;
		allReviewsForAProduct = cursor.toArray();*/
/*		while(cursor.hasNext()){
			System.out.print(cursor.next());
			System.out.print("\n");
		}
*/
/*		String productImage = cursor.next().productImage;
		String productName = cursor.next().productName;*/

/*		System.out.print(allReviewsForAProduct);
*/
/*		while(cursor.hasNext()){
			System.out.println(cursor.next());			
			System.out.print("_____________________________________________________");
		}*/

	}
}
