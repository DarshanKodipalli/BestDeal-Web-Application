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

@WebServlet("/SubmitReview")

public class SubmitReview extends HttpServlet {

	/* Fitness Page Displays all the Fitnesss and their Information in Game Speed */

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
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		response.setContentType("text/html");
		String name = null;

		String productId = request.getParameter("name");
		String productType = request.getParameter("type");
		String productImage = request.getParameter("image");
		String productModel = request.getParameter("model");
		String productPrice = request.getParameter("price");
		String productManufacturer = request.getParameter("manufacturer");
		String productName = request.getParameter("name");
		String productSKU = "Product SKU -24";

		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
		String city = request.getParameter("city");
		String zip = request.getParameter("zip");
		String rating = request.getParameter("rating");
		String reviewDate = request.getParameter("reviewDate");
		String pros = request.getParameter("pros");
		String cons = request.getParameter("cons");
		String quality = request.getParameter("quality");
		String recommend = request.getParameter("recommend");
		String review = request.getParameter("review");
		String retailerName = request.getParameter("retailerName");
		String retailerOccupation = request.getParameter("retailerOccupation");

		System.out.print(productId);
		System.out.print(productType);
		System.out.print(productImage);
		System.out.print(productName);
		System.out.print(productModel);
		System.out.print(productPrice);
		System.out.print(productManufacturer);
		System.out.print(productSKU);

		System.out.print(age);
		System.out.print(gender);
		System.out.print(city);
		System.out.print(zip);
		System.out.print("Rating: "+rating);
		System.out.print(reviewDate);
		System.out.print(pros);
		System.out.print(cons);
		System.out.print(quality);
		System.out.print(recommend);
		System.out.print(review);

		System.out.print("\nCalling Utility Function:");
		String message=utility.storeReview(productId,productType,productImage,productName,productModel,Double.parseDouble(productPrice),productManufacturer,productSKU,userName,userType,age,gender,city,zip,Integer.parseInt(rating),reviewDate,pros,cons,quality,recommend,review,retailerName,retailerOccupation);		

		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");						
/*		utility.printHtml("LeftNavigationBar.html");*/
		pw.print("<div class='col-sm-12 col-md-12 col-lg-10' style='margin-top:2%;margin-left:10%'>");

    	pw.print("<div class='card card-price'>");
      	pw.print("<div style='margin-left:10%;' class='card-img'>");
        pw.print("<a href='#'>");
        pw.print("<img style='width:100%' src='images/Carousel_and_Others/15.png' class='img-responsive'>");
        pw.print("<div class='card-caption'>");
        pw.print("</div></a></div>");
        pw.print("<div class='card-body'>");
        pw.print("<div class='price'>&nbsp;&nbsp;"+productName+"</div>");
        pw.print("<div class='lead'><h3>Thank you for the Feedback</h3></div>");
        pw.print("<ul class='details'>");
        pw.print("<li><h4>Hope you had a great experience shopping with us</h4></li>");
        pw.print("</ul><a href='/BestDealApp/Home' class='btn btn-primary btn-lg btn-block buy-now'>");
        pw.print("Continue Shopping <span class='glyphicon glyphicon-shopping-cart'></span>");
        pw.print("</a></div></div></div></div></div></div>");            
	}
}
