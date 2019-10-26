import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.*;

@WebServlet("/AddToCart")

public class AddToCart extends HttpServlet {

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


		/* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/

		Utilities utility = new Utilities(request, pw);
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String image = request.getParameter("image");
		String model = request.getParameter("model");
		String price = request.getParameter("price");
		String id = request.getParameter("id");
		String sku = request.getParameter("sku");
		String manufacturer = request.getParameter("manufacturer");
		System.out.print("userName");		
		System.out.print(request.getParameter("userName"));
		String userName = request.getParameter("userName");
		String receivedQuantity = request.getParameter("p_quantity");
		System.out.print("____________________");
		System.out.print(receivedQuantity);
		System.out.print("____________________");
		Integer quantity = Integer.parseInt(request.getParameter("p_quantity"));
		System.out.print("name " + name + " type " + type + " model " + model+ " price " + price+ " image " + image+"\n");

		/* StoreProduct Function stores the Purchased product in Orders HashMap.*/	
		try{
			getConnection();
			String addCartQurey = "INSERT INTO carts(p_id,p_type,p_name,p_price,p_image,p_manufacturer,p_model,p_sku,userName,p_quantity)" +
			"VALUES (?,?,?,?,?,?,?,?,?,?);";
			   		        			
				PreparedStatement pst = conn.prepareStatement(addCartQurey);
				pst.setString(1,id);
				pst.setString(2,type);
				pst.setString(3,name);
				pst.setDouble(4,Double.parseDouble(price));
				pst.setString(5,image);
				pst.setString(6,manufacturer);
				pst.setString(7,model);
				pst.setString(8,sku);
				pst.setString(9,userName);
				pst.setInt(10,quantity);
				pst.executeUpdate();
		}catch(Exception e){
			System.out.print(e);
		}
		displayCart(request, response);
	}
	

/* displayCart Function shows the products that users has bought, these products will be displayed with Total Amount.*/

	protected void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request,pw);
		Carousel carousel = new Carousel();

		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");
		utility.printHtml("LeftNavigationBar.html");  

		try{
			getConnection();
			String selectproductsQuery ="select * from carts where userName=?";
			System.out.print(utility.getUserName());
			PreparedStatement pst = conn.prepareStatement(selectproductsQuery);
			pst.setString(1,utility.getUserName());			
			ResultSet rs = pst.executeQuery();
			pw.print("<div class='col-sm-12 col-md-12 col-lg-10' style='margin-top: 4%'>");
	        pw.print("<h2 style='font-weight: bolder;'>Review all the Items added to the Cart....!</h2>");
	        pw.print("<hr style='margin-top: 3%'>");  
			int i = 1;
			double total = 0;

			while(rs.next()){  
        		pw.print("<div class='row' style='margin-top: 3%;margin-left: 7%'>");
          		pw.print("<div style='margin-left: 3%' class='column' >");
          		pw.print("<img src='"+rs.getString("p_image")+"' style='width: 25%'>");
          		pw.print("<h3 class='caption text-center'>"+rs.getString("p_name")+"</h3>");
          		pw.print("</div>");
          		pw.print("<div style='margin-left: 15%' class='column' >");
              	pw.print("<h3>");
                pw.print("Model:<span style='font-weight: bolder;'>&nbsp;"+rs.getString("p_model")+"</span>");				
                pw.print("</h3>");
              	pw.print("<h3>");
                pw.print("SKU:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;6216381</span>");
				pw.print("</h3>");
				pw.print("<h1>$"+rs.getString("p_price")+"</h1>");
              	pw.print("<form name ='Cart' action='AddToCart' method='get'><button name='formAction' value='removeItem' class='btn btn-danger'><i class='fa fa-remove'></i>&nbsp;&nbsp;Remove</button><input type='hidden' name='productToDelete' value='"+rs.getString("p_name")+"'></form></div></div>");                
				pw.print("<hr style='margin-top: 1%'>");
				total = total +Double.parseDouble(rs.getString("p_price"));
				total = Math.round(total*100D)/100D;
				i++;
			}
				pw.print("<hr style='margin-top: 1%'><div class='row' style='margin-top: 1%;margin-left: 10%'><div style='margin-left: 3%' class='column' ><h3 class='caption text-center'>Total</h3></div><div style='margin-left: 15%' class='column' >");
				pw.print("<h1 style='font-weight:bolder'>$"+total+"</h1>");
				pw.print("</div></div><hr style='margin-top: 1%'>");

				pw.print("  <div style='float: right;'><form name ='Cart' action='CheckOut' method='post'><button value='actualCheckout' name='formAction' class='btn btn-success btn-lg'><input type='hidden' name='p_quantity' value='"+rs.getString("p_quantity")+"'><i class='fa fa-shopping-cart'></i>&nbsp;&nbsp;Check Out</button></form>");

				pw.print("</div></div></form>");
				pw.print("</div></div>");
				pw.print("</div>");

		}catch(Exception e){
			System.out.print(e);
		}

		String str = request.getParameter("model");
		String model1 = "XBR55Y800G";
		String model2 = "NKJSS687";
		if(model1.equals(str)){
			pw.print("<h1><u>Accessories suggested</u></h1>");
    		pw.print("<div class='row' style='margin-top: 4%;margin-left: 7%'>");
      		pw.print("<div style='margin-left: 3%' class='column' >");
      		pw.print("<img src='images/Carousel_and_Others/10.jpg' style='width: 25%'>");
      		pw.print("<h3 class='caption text-center'>AudioQuest - Forest 3 4K Ultra HD In-Wall HDMI Cable - Black/Green Stripe</h3>");
      		pw.print("</div>");
      		pw.print("<div style='margin-left: 15%' class='column' >");
          	pw.print("<h3>");
            pw.print("Model:<span style='font-weight: bolder;'>&nbsp;XBR55X800G</span>");				
            pw.print("</h3>");
          	pw.print("<h3>");
            pw.print("SKU:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;6216381</span>");
			pw.print("</h3>");
			pw.print("<h1>$55.97</h1>");
          	pw.print("<button class='btn btn-success'><i class='fa fa-shopping-cart'></i>&nbsp;&nbsp;Add to the cart</button></div></div><hr style='margin-top: 1%'>");                

    		pw.print("<div class='row' style='margin-top: 3%;margin-left: 7%'>");
      		pw.print("<div style='margin-left: 3%' class='column' >");
      		pw.print("<img src='images/Carousel_and_Others/11.jpg' style='width: 25%'>");
      		pw.print("<h3 class='caption text-center'>Rocketfish™ - 4' 4K UltraHD/HDR In-Wall Rated HDMI Cable - Black</h3>");
      		pw.print("</div>");
      		pw.print("<div style='margin-left: 15%' class='column' >");
          	pw.print("<h3>");
            pw.print("Model:<span style='font-weight: bolder;'>&nbsp;BHGAFD546</span>");				
            pw.print("</h3>");
          	pw.print("<h3>");
            pw.print("SKU:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;6216381</span>");
			pw.print("</h3>");
			pw.print("<h1>$18.49</h1>");
          	pw.print("<button class='btn btn-success'><i class='fa fa-shopping-cart'></i>&nbsp;&nbsp;Add to the Cart</button></div></div><hr style='margin-top: 1%'>");                	              	
		}
		if(model2.equals(str)){
			pw.print("<h1><u>Accessories suggested</u></h1>");
    		pw.print("<div class='row' style='margin-top: 4%;margin-left: 7%'>");
      		pw.print("<div style='margin-left: 3%' class='column' >");
      		pw.print("<img src='images/Carousel_and_Others/12.jpg' style='width: 25%'>");
      		pw.print("<h3 class='caption text-center'>iOttie - Easy One Touch 4 Wireless Charging Dash and Windshield Car Mount - Black</h3>");
      		pw.print("</div>");
      		pw.print("<div style='margin-left: 15%' class='column' >");
          	pw.print("<h3>");
            pw.print("Model:<span style='font-weight: bolder;'>&nbsp;UISDQO98273</span>");				
            pw.print("</h3>");
          	pw.print("<h3>");
            pw.print("SKU:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;12645634</span>");
			pw.print("</h3>");
			pw.print("<h1>$49.99</h1>");
          	pw.print("<button class='btn btn-success'><i class='fa fa-shopping-cart'></i>&nbsp;&nbsp;Add to the cart</button></div></div><hr style='margin-top: 1%'>");                

    		pw.print("<div class='row' style='margin-top: 3%;margin-left: 7%'>");
      		pw.print("<div style='margin-left: 3%' class='column' >");
      		pw.print("<img src='images/Carousel_and_Others/13.jpg' style='width: 25%'>");
      		pw.print("<h3 class='caption text-center'>PopSockets - PopGrip - Black</h3>");
      		pw.print("</div>");
      		pw.print("<div style='margin-left: 15%' class='column' >");
          	pw.print("<h3>");
            pw.print("Model:<span style='font-weight: bolder;'>&nbsp;OJAED187312</span>");				
            pw.print("</h3>");
          	pw.print("<h3>");
            pw.print("SKU:<span style='font-weight: bolder;'>&nbsp;&nbsp;&nbsp;95465123</span>");
			pw.print("</h3>");
			pw.print("<h1>$18.49</h1>");
          	pw.print("<button class='btn btn-success'><i class='fa fa-shopping-cart'></i>&nbsp;&nbsp;Add to the Cart</button></div></div><hr style='margin-top: 1%'>");                	              						
		}
		pw.print("  <div style='float: right;'><form name ='Cart' action='CheckOut' method='post'><button class='btn btn-success btn-lg'><i class='fa fa-shopping-cart'></i>&nbsp;&nbsp;Check Out</button></form></div></div></form>");

		pw.print("</div></div>");
		pw.print("</div>");

/*		else
		{
	    	pw.print("<div style='width:50%;margin-top:20%;margin-left:20%'><div class='card card-price'>");
	      	pw.print("<div  class='card-img'>");
	        pw.print("<a href='#'>");
	        pw.print("<div class='card-caption'>");
	        pw.print("</div></a></div>");
	        pw.print("<div class='card-body'>");
	        pw.print("<div class='lead'><h3>Your Cart has no Items :-(</h3></div>");
	        pw.print("<ul class='details'>");
	        pw.print("<li>Shop to avail the best deals!</li>");
	        pw.print("</ul><a href='/BestDealApp/Home' class='btn btn-primary btn-lg btn-block buy-now'>");
	        pw.print("Shop <span class='glyphicon glyphicon-shopping-cart'></span>");
	        pw.print("</a></div></div></div></div></div></div></div>");           			
		}
*//*		utility.printHtml("Footer.html");*/
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
        String action = request.getParameter("formAction");
        if(action.equals("removeItem")){
        	System.out.print("Item Remove Called");
        	System.out.print(request.getParameter("productToDelete"));
			try{
				getConnection();
				String deleteCartQuery ="delete from carts where p_name=?";
				PreparedStatement pst = conn.prepareStatement(deleteCartQuery);
				pst.setString(1,request.getParameter("productToDelete"));			
				pst.executeUpdate();
				displayCart(request, response);
			}catch(Exception e){
				System.out.print(e);
			}	        	
        }else{
			displayCart(request, response);
        }		
	}
}
