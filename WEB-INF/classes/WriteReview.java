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

@WebServlet("/WriteReview")

public class WriteReview extends HttpServlet {

	/* Fitness Page Displays all the Fitnesss and their Information in Game Speed */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session =request.getSession(true);
		String username= "";
		String usertype= "";
		if(session.getAttribute("username")!=null){
			username = session.getAttribute("username").toString();
			username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
			usertype = session.getAttribute("usertype").toString();
			usertype = Character.toUpperCase(usertype.charAt(0)) + usertype.substring(1);			
		}
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;

		String productId = request.getParameter("id");
		String productType = request.getParameter("type");
		String productImage = request.getParameter("image");
		String productModel = request.getParameter("model");
		String productPrice = request.getParameter("price");
		String productManufacturer = request.getParameter("manufacturer");
		String productName = request.getParameter("name");
		String productSKU = request.getParameter("sku");

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");						
		utility.printHtml("LeftNavigationBar.html");

		pw.print("<div class='container' style='margin-top: 5%'>");
  		pw.print("<h2 style='margin-left: 5%;font-weight: bolder;font-style: italic;'>Product Details..</h2>");
  		pw.print("<hr style='margin-left: 5%;margin-top: 3%'>");
  		pw.print("<div class='row' style='margin-top: 3%;margin-left: 5%'>");
		pw.print("<div style='margin-left: 3%' class='column' >");

        pw.print("<img src='"+productImage+"' style='width: 25%'>");      
        pw.print("<h3 class='caption text-center'>"+productName+"</h3>");
	    pw.print("</div>");
	    pw.print("<div style='margin-left: 15%' class='column' >");
	    pw.print("<h3>");
	    pw.print("Product Type:<span style='font-weight: bolder;'>&nbsp;"+Character.toUpperCase(productType.charAt(0)) + productType.substring(1)+"</span>");
	    pw.print("</h3>");
	    pw.print("<h3>");
	    pw.print("Product Model:<span style='font-weight: bolder;'>&nbsp;"+productModel+"</span>");
	    pw.print("</h3>");
	    pw.print("<h3>");
	    pw.print("Product SKU:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;"+productSKU+"</span>");
	    pw.print("</h3>");
	    pw.print("<h3>");
	    pw.print("Product Price:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;$"+productPrice+"</span>");
	    pw.print("</h3>");
	    pw.print("<h3>");
	    pw.print("Manufacturer Name:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;"+productManufacturer+"</span>");
	    pw.print("</h3>");
	    pw.print("</div>");        
	  	pw.print("</div>");

		pw.print("<div style='margin-top: 5%'><h2 style='margin-left: 5%; font-weight: bolder;font-style: italic;'>Leave your Review..</h2><hr style='margin-left: 5%;margin-top: 3%'>");
		pw.print("<div class='container' style='margin-left: 5%;margin-top: 3%'><form method='post' action='SubmitReview' style='font-size: 130%'><div class='form-row'><div class='form-group col-md-6'><label for='inputEmail4'>User Name</label><input type='hidden' class='form-control' style='border: none;' disabled='true' id='inputEmail4' value='"+username+"'><label style='border:none' class='form-control' style='font-weight:bolder'>"+username+"</label>");
		pw.print("</div><div class='form-group col-md-6'><label for='inputPassword4'>User Type</label><input type='hidden' class='form-control' style='border: none;' disabled='true' value='"+usertype+"'><label style='border:none' class='form-control' style='font-weight:bolder'>"+usertype+"</label></div></div>");

		pw.print("<div style='margin-top: 3%' class='form-row'><div class='form-group col-md-6'><label for='inputEmail4'>User Age</label><input type='text' class='form-control' id='inputAddress' name='age' placeholder='Enter your Age..'></div><div class='form-group col-md-6'>");
		pw.print("<label for='inputState'>User Gender</label><select id='inputState' name='gender' class='form-control'><option selected>Choose...</option><option value='Female'>Female</option><option value='Male'>Male</option><option value='Not Specified'>Not Specified</option></select></div></div>");
		pw.print("<div style='margin-top: 3%' class='form-row'><div class='form-group col-md-6'><label for='inputCity'>Retailer Name</label><input type='text' class='form-control' name='retailerName' id='inputCity'></div><div class='form-group col-md-6'><label for='inputZip'>User Occupation</label><input name='retailerOccupation' type='text' class='form-control' id='inputZip'></div></div>");
		pw.print("<div style='margin-top: 3%' class='form-row'><div class='form-group col-md-6'><label for='inputCity'>Retailer City</label><input type='text' class='form-control' name='city' id='inputCity'></div><div class='form-group col-md-6'><label for='inputZip'>Retailer Zip</label><input name='zip' type='text' class='form-control' id='inputZip'></div></div>");

		pw.print("<div style='margin-top: 3%' class='form-row'><div class='form-group col-md-6'><label for='rating'>Rating</label><select id='inputState' name='rating' class='form-control'><option selected>Choose...</option><option name='rating' value='1'>1</option><option name='rating' value='2'>2</option><option name='rating' value='3'>3</option><option name='rating' value='4'>4</option><option name='rating' value='5'>5</option></select></div><div class='form-group col-md-6'><label for='inputState'>Review Date</label><input type='Date'  class='form-control' name='reviewDate'></div></div>");

		pw.print("<div style='margin-top: 3%' class='form-row'><div class='form-group col-md-6'><label for='inputCity'>Pros</label><textarea class='form-control' name='pros' id='exampleFormControlTextarea1' rows='5'></textarea></div><div class='form-group col-md-6'><label for='inputState'>Cons</label><textarea name='cons' class='form-control' id='exampleFormControlTextarea1' class='cons' rows='5'></textarea></div></div>");

		pw.print("<div style='margin-top: 3%' class='form-row'><div class='form-group col-md-6'><label for='inputCity'>Product Quality</label><br><div style='margin-left: 5%'><input type='radio' name='quality' value='Excellent'>Excellent<br><input type='radio' name='quality' value='Good'>Good<br><input type='radio' name='quality' value='Could be Better'>Could be Better<br><input type='radio' name='quality' value='Bad'>Bad<br></div></div>");

		pw.print("<div class='form-group col-md-6'><label for='inputCity'>How likely would you Recommend?</label><br><div style='margin-left: 5%'><input type='radio' name='recommend' value='Very Likely'>Very Likely<br><input type='radio' name='recommend' value='Likely'>Likely<br><input type='radio' name='recommend' value='Wont Recommend'>Won't Recommend<br></div></div></div>");

		pw.print("<input type='hidden' name='image' value='"+productImage+"'>");
		pw.print("<input type='hidden' name='name' value='"+productName+"'>");
		pw.print("<input type='hidden' name='manufacturer' value='"+productManufacturer+"'>");
		pw.print("<input type='hidden' name='model' value='"+productModel+"'>");
		pw.print("<input type='hidden' name='sku' value='"+productSKU+"'>");
		pw.print("<input type='hidden' name='price' value='"+productPrice+"'>");
		pw.print("<input type='hidden' name='id' value='"+productId+"'>");
		pw.print("<input type='hidden' name='type' value='"+productType+"'>");


		pw.print("<div style='margin-top: 3%' class='form-row'><div class='form-group col-md-12'><label for='inputCity'>Write Review</label><textarea class='form-control' id='exampleFormControlTextarea1' name='review' rows='5'></textarea></div></div>");

		pw.print("<button type='submit' class='btn btn-primary'>Submit Review</button></form></div></div></div>");
	}
}
