import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.*;
import java.sql.*;

@WebServlet("/Home")

public class Home extends HttpServlet {
	HttpSession session;
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
		String line=null;
		String TOMCAT_HOME = System.getProperty("catalina.home");
		Path fpath = Paths.get(TOMCAT_HOME, "webapps", "BestDealApp", "DealMatches.txt");
		BufferedReader reader = new BufferedReader(new FileReader (fpath.toString()));
		System.out.print(reader);		
		line=reader.readLine();
		System.out.print(line);
		HttpSession session =request.getSession(true);
		String userName= "";
		String userType= "";
		if(session.getAttribute("username")!=null){
			userName = session.getAttribute("username").toString();
			userName = Character.toUpperCase(userName.charAt(0)) + userName.substring(1);
			userType = session.getAttribute("usertype").toString();
			userType = Character.toUpperCase(userType.charAt(0)) + userType.substring(1);			
		}		
		this.session = request.getSession(true);
		if(session.getAttribute("usertype")!=null){
			String usertype = session.getAttribute("usertype").toString();
			System.out.print(usertype);
			if(usertype.equals("customer")){
				response.setContentType("text/html");
				PrintWriter pw = response.getWriter();
				Utilities utility = new Utilities(request,pw);
				utility.printHtml("Header.html");
				utility.printHtml("Carousel.html");
				pw.print("<hr style='margin-left: 3%;margin-top: 2%;margin-bottom: 1rem;border: 0;width: 90%;border-top: 5px solid grey;'>");
				pw.print("<div style='margin-left:20%;margin-top:3%;' class='card'>  <div class='card-header' style='color:blue;font-size:250%'><u>We beat out Competitors in all Aspects. Price-Match Guaranteed</u></div>");
				if(line == null){
					pw.print("<div class='card-body' style='margin-left:10%;font-size:150%'>"+"No Offers Found"+"</div></div>");					
				}else{
					pw.print("<div class='card-body' style='margin-left:10%;font-size:150%'><a>"+line+"</a></div></div>");										
				}
				pw.print("<hr style='margin-left: 3%;margin-top: 5%;margin-bottom: 1rem;border: 0;width: 90%;border-top: 5px solid grey;'>");
				utility.printHtml("Pre-Content.html");
				utility.printHtml("LeftNavigationBar.html");
				utility.printHtml("Content.html");
				pw.print("<hr style='margin-left: 3%;margin-top: 5%;margin-bottom: 1rem;border: 0;width: 90%;border-top: 5px solid grey;'>");
				try{

					pw.print("<div class='container' style='margin-top: .1%'><h2 style='margin-left: 5%;font-weight: bolder;font-style: italic;'><u>Deal Matches</u></h2><hr style='margin-left: 5%;margin-top: 3%'><div class='row' style='margin-top: 3%;margin-left: 10%'><div style='margin-left: 0%' class='column' >");
					if(line == null){
						pw.print("<p style='font-size:150%'> No Deals Matched! </p>");
					}else {
					getConnection();
					String[] productArray = line.split(" ");
					String productName = productArray[4]+" "+productArray[5]+" "+productArray[6]+" ";
					String selectproductQuery ="select * from product_catalog where p_name like ?";
					System.out.print(utility.getUserName());
					PreparedStatement pst = conn.prepareStatement(selectproductQuery);
					pst.setString(1,"%"+productName+"%");			
					System.out.print(pst);
					ResultSet rs = pst.executeQuery();
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
							pw.print("<form method='post' action='AddToCart'><div style='margin-top: 5%' class='row'><div class='col col-md-4'><span><button class='btn btn-success' type='submit'>Add to Cart</button></span></div><input type='hidden' name='userName' value='"+userName+"'/><input type='hidden' name='id' value='"+rs.getString("p_id")+"'/><input type='hidden' name='sku' value='"+rs.getString("p_sku")+"'/><input type='hidden' name='type' value='smarts'/><input type='hidden' name='image' value='"+rs.getString("p_image")+"'/><input type='hidden' name='model' value='"+rs.getString("p_model")+"'/><input type='hidden' name='price' value='"+rs.getString("p_price")+"'/><input type='hidden' name='manufacturer' value='"+rs.getString("p_manufacturer")+"'/><input type='hidden' name='name' value='"+rs.getString("p_name")+"'/><input type='hidden' name='p_quantity' value='"+rs.getString("p_quantity")+"'/></form><form style='margin-top:2%' method='post' action='WriteReview'><div class='col col-md-4'><span><button class='btn btn-warning' type='submit'>Write Review</button></span></div><input type='hidden' name='id' value='"+rs.getString("p_id")+"'/><input type='hidden' name='type' value='phones'/><input type='hidden' name='image' value='"+rs.getString("p_image")+"'/><input type='hidden' name='model' value='"+rs.getString("p_model")+"'/><input type='hidden' name='manufacturer' value='"+rs.getString("p_manufacturer")+"'/><input type='hidden' name='sku' value='"+rs.getString("p_sku")+"'/><input type='hidden' name='name' value='"+rs.getString("p_name")+"'/></form><form style='margin-top:2%' method='post' action='ViewReview'><div class='col col-md-4'><span><button class='btn btn-primary' type='submit'>View Review</button></span></div><input type='hidden' name='id' value='"+rs.getString("p_id")+"'/><input type='hidden' name='type' value='phones'/><input type='hidden' name='image' value='"+rs.getString("p_image")+"'/><input type='hidden' name='model' value='"+rs.getString("p_model")+"'/><input type='hidden' name='manufacturer' value='"+rs.getString("p_manufacturer")+"'/><input type='hidden' name='name' value='"+rs.getString("p_name")+"'/></div>");					
						}											
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.print(e);
				}

				pw.print("<hr style='margin-left: 3%;margin-top: 5%;margin-bottom: 1rem;border: 0;width: 90%;border-top: 5px solid grey;'>");				
				utility.printHtml("Footer.html");				
			}if(usertype.equals("retailer")) {
				response.setContentType("text/html");
				PrintWriter pw = response.getWriter();
				Utilities utility = new Utilities(request,pw);
				utility.printHtml("Header.html");
				utility.printHtml("Add_Product.html");
				utility.printHtml("Footer.html");								
			}if(usertype.equals("manager")){
				response.setContentType("text/html");
				PrintWriter pw = response.getWriter();
				Utilities utility = new Utilities(request,pw);
				utility.printHtml("Header.html");
				utility.printHtml("Carousel.html");
				utility.printHtml("Pre-Content.html");
			}
		}else{
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request,pw);
			utility.printHtml("Header.html");
			utility.printHtml("Carousel.html");
			utility.printHtml("Pre-Content.html");
			utility.printHtml("LeftNavigationBar.html");
			utility.printHtml("Content.html");
			utility.printHtml("Footer.html");				
		}
	}

}
