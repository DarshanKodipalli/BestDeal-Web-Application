/* This code is used to implement carousel feature in Website. Carousels are used to implement slide show feature. This  code is used to display 
all the accessories related to a particular product. This java code is getting called from cart.java. The product which is added to cart, all the 
accessories realated to product will get displayed in the carousel.*/
  

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;	
import java.sql.*;

			
			
public class Carousel{

	static Connection conn = null;
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

	public String  carouselfeature(Utilities utility){
				
		ProductRecommenderUtility prodRecUtility = new ProductRecommenderUtility();
		
		HashMap<String, Console> hm = new HashMap<String, Console>();
		StringBuilder sb = new StringBuilder();
		String myCarousel = null;
			
		String name = null;
		String CategoryName = null;
		HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		prodRecmMap = prodRecUtility.readOutputFile();
		
		
		
		int l =0;
		for(String user: prodRecmMap.keySet())
		{
			if(user.equals(utility.username()))
			{
				String products = prodRecmMap.get(user);
				products=products.replace("[","");
				products=products.replace("]","");
				products=products.replace("\"", " ");
				ArrayList<String> productsList = new ArrayList<String>(Arrays.asList(products.split(",")));

		        myCarousel = "myCarousel"+l;
					
				sb.append("<div id='content'><div class='post'><h2 class='title meta'>");
				sb.append("<a style='font-size: 24px;'>"+""+" Recommended Products</a>");
				
				sb.append("</h2>");

				sb.append("<div class='container'>");
				/* Carousels require the use of an id (in this case id="myCarousel") for carousel controls to function properly.
				The .slide class adds a CSS transition and animation effect, which makes the items slide when showing a new item.
				Omit this class if you do not want this effect. 
				The data-ride="carousel" attribute tells Bootstrap to begin animating the carousel immediately when the page loads. 
		 
				*/
				sb.append("<div class='carousel slide' id='"+myCarousel+"' data-ride='carousel'>");
				
				/*The slides are specified in a <div> with class .carousel-inner.
				The content of each slide is defined in a <div> with class .item. This can be text or images.
				The .active class needs to be added to one of the slides. Otherwise, the carousel will not be visible.
				*/
				sb.append("<div class='carousel-inner'>");
						
				int k = 0;
				System.out.print(productsList);
				username = utility.getUserName();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
/*				try{
					getConnection();
					String selectproductsQuery ="select * from product_catalog where p_name in (?)";
					PreparedStatement pst = conn.prepareStatement(selectproductsQuery);
					pst.setString(1,productsList);			
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						System.out.print(rs.getString("p_name"));
						System.out.print(rs.getString("p_image"));
						System.out.print(rs.getString("p_model"));
					}
				}catch(Exception e){
					System.out.print(e);
				}
*/

				for(String prod : productsList){
					prod= prod.replace("'", "");
					System.out.print("____________________");
					System.out.print(prod);
					System.out.print("____________________");					



					Product prodObj = new Product();
					prodObj = ProductRecommenderUtility.getProduct(prod.trim());
					if (k==0 )
					{
						sb.append("<div class='item active'><div class='col-md-6' style = ''>");
					}
					else
					{
						sb.append("<div class='item'><div class='col-md-6' style = '' >");
					}
					sb.append("<div style='margin-left:70%;width:100%'><div id='shop_item'>");
					Double afterDiscount = prodObj.getPrice()*(1-(Double.parseDouble(prodObj.getDiscount())/100));
					afterDiscount = Math.round(afterDiscount*100D)/100D;				
					sb.append("<h3>"+prodObj.getName()+"</h3>");
					sb.append("<h4> Price: $"+prodObj.getPrice()+"</h4><ul>");
					sb.append("<li id='item' style='margin-left:25%'><img src='"+prodObj.getImage()+"' style='width: 25%' alt='' /></li>");
					sb.append("<li><form method='post' action='AddToCart'>" +
							"<input type='hidden' name='userName' value='"+username+"'/><input type='hidden' name='id' value='"+prodObj.getId()+"'/><input type='hidden' name='sku' value='"+prodObj.getSKU()+"'/><input type='hidden' name='type' value='fitnesses'/><input type='hidden' name='image' value='"+prodObj.getImage()+"'/><input type='hidden' name='model' value='"+prodObj.getModel()+"'/><input type='hidden' name='price' value='"+afterDiscount+"'/><input type='hidden' name='manufacturer' value='"+prodObj.getManufacturer()+"'/><input type='hidden' name='name' value='"+prodObj.getName()+"'/>"+
							"<input type='hidden' name='name' value='"+prod.trim()+"'>"+
							"<input type='hidden' name='type' value='"+prodObj.getType()+"'>"+
							"<input type='hidden' name='maker' value='"+prodObj.getManufacturer()+"'>"+
							"<input type='hidden' name='access' value='"+" "+"'>"+
							"<button class='btn btn-success' type='submit'>Add to Cart</button><input type='hidden' name='p_quantity' value='"+prodObj.getDiscount()+"'/></form></form></li>");
					sb.append("<li><form style='margin-top:2%' method='post' action='WriteReview'><button class='btn btn-warning' type='submit'>Write Review</button>"+"<input type='hidden' name='name' value='"+prodObj.getName()+"'>"+
							"<input type='hidden' name='userName' value='"+username+"'/><input type='hidden' name='id' value='"+prodObj.getId()+"'/><input type='hidden' name='sku' value='"+prodObj.getSKU()+"'/><input type='hidden' name='type' value='fitnesses'/><input type='hidden' name='image' value='"+prodObj.getImage()+"'/><input type='hidden' name='model' value='"+prodObj.getModel()+"'/><input type='hidden' name='price' value='"+afterDiscount+"'/><input type='hidden' name='manufacturer' value='"+prodObj.getManufacturer()+"'/><input type='hidden' name='name' value='"+prodObj.getName()+"'/>"+							
							"</form></li>");
					sb.append("<li><form style='margin-top:2%' method='post' action='ViewReview'><button class='btn btn-primary' type='submit'>View Review</button>"+"<input type='hidden' name='name' value='"+prodObj.getName()+"'>"+
							"<input type='hidden' name='userName' value='"+username+"'/><input type='hidden' name='id' value='"+prodObj.getId()+"'/><input type='hidden' name='sku' value='"+prodObj.getSKU()+"'/><input type='hidden' name='type' value='fitnesses'/><input type='hidden' name='image' value='"+prodObj.getImage()+"'/><input type='hidden' name='model' value='"+prodObj.getModel()+"'/><input type='hidden' name='price' value='"+afterDiscount+"'/><input type='hidden' name='manufacturer' value='"+prodObj.getManufacturer()+"'/><input type='hidden' name='name' value='"+prodObj.getName()+"'/>"+							
							"</form></li>");
					sb.append("</ul></div></div>");
					sb.append("</div></div>");
				
					k++;
					
				}
				
			
				sb.append("</div>");
				sb.append("<a class='left carousel-control' href='#"+myCarousel+"' data-slide='prev' style = 'width : 10% ;background-color:#050505; opacity :1'>"+
						"<span class='glyphicon glyphicon-chevron-left' style = 'color :red'></span>"+
						"<span class='sr-only'>Previous</span>"+
						"</a>");
				sb.append("<a class='right carousel-control' href='#"+myCarousel+"' data-slide='next' style = 'width : 10% ;background-color:#050505; opacity :1'>"+
						"<span class='glyphicon glyphicon-chevron-right' style = 'color :red'></span>"+

							"<span class='sr-only'>Next</span>"+
							"</a>");

			
				sb.append("</div>");
			
				sb.append("</div></div>");
				sb.append("</div>");
				l++;
			
				}
			}
			return sb.toString();
		}
	}
	