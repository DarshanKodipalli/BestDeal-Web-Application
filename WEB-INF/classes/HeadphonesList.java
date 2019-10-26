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

@WebServlet("/HeadphonesList")

public class HeadphonesList extends HttpServlet {

	/* TV Page Displays all the TVs and their Information in Game Speed */
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
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String CategoryName = request.getParameter("maker");
        System.out.print("HeadphonesList");

		/* Checks the Tablets type whether it is microsft or sony or nintendo */

		
		/* Header, Left Navigation Bar are Printed.

		All the TV and TV information are dispalyed in the Content Section

		and then Footer is Printed*/
		String maker1 = Character.toUpperCase(CategoryName.charAt(0)) + CategoryName.substring(1);
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");						
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div class='col-sm-12 col-md-12 col-lg-10' ><div class='container' style='margin-top: 5%'>");
		pw.print("<h2 style='font-weight: bolder;'>Results for "+maker1+" Headphones.....</h2>");
		pw.print("<hr style='margin-top: 3%'>");
		username = utility.getUserName();
		username = Character.toUpperCase(username.charAt(0)) + username.substring(1);

		try{
			getConnection();
			String selectproductsQuery ="select * from product_catalog where p_manufacturer=?";
			System.out.print(request.getParameter("p_manufacturer"));
			PreparedStatement pst = conn.prepareStatement(selectproductsQuery);
			pst.setString(1,maker1);			
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Double afterDiscount = Double.parseDouble(rs.getString("p_price"))*(1-(Double.parseDouble(rs.getString("p_discount"))/100));
				afterDiscount = Math.round(afterDiscount*100D)/100D;				
				pw.print("<div class='row' style='margin-top: 3%;margin-left: 10%'><div style='margin-left: 3%' class='column' >");
				pw.print("<form method='post' action='AddToCart'><img src='"+rs.getString("p_image")+"' style='width: 25%'><input type='hidden' name='userName' value='"+username+"'/><input type='hidden' name='id' value='"+rs.getString("p_id")+"'/><input type='hidden' name='sku' value='"+rs.getString("p_sku")+"'/><input type='hidden' name='type' value='headphoness'/><input type='hidden' name='image' value='"+rs.getString("p_image")+"'/><input type='hidden' name='model' value='"+rs.getString("p_model")+"'/><input type='hidden' name='price' value='"+afterDiscount+"'/><input type='hidden' name='manufacturer' value='"+rs.getString("p_manufacturer")+"'/><input type='hidden' name='name' value='"+rs.getString("p_name")+"'/>");
				pw.print("<h3 class='caption text-center' name='name' value='"+rs.getString("p_name")+"' >"+rs.getString("p_name")+"</h3>");
				pw.print("</div><div style='margin-left: 15%' class='column' ><h3>");
				pw.print("Model:<span style='font-weight: bolder;' >&nbsp;"+rs.getString("p_model")+"</span></h3><h3>");
				pw.print("SKU:<span style='font-weight: bolder;' >&nbsp;&nbsp;&nbsp;"+rs.getString("p_sku")+"</span></h3>");
				pw.print("<h3>$"+afterDiscount+"<span>&nbsp;( on <span style='color:red;'>"+rs.getString("p_discount")+"&nbsp;% </span>Discount)</span></h3>");
				pw.print("<button class='btn btn-success' type='submit'>Add to Cart</button><input type='hidden' name='p_quantity' value='"+rs.getString("p_quantity")+"'/></form><form style='margin-top:2%' method='post' action='WriteReview'><button class='btn btn-warning' type='submit'>Write Review</button><input type='hidden' name='id' value='"+rs.getString("p_id")+"'/><input type='hidden' name='type' value='phones'/><input type='hidden' name='image' value='"+rs.getString("p_image")+"'/><input type='hidden' name='model' value='"+rs.getString("p_model")+"'/><input type='hidden' name='price' value='"+afterDiscount+"'/><input type='hidden' name='manufacturer' value='"+rs.getString("p_manufacturer")+"'/><input type='hidden' name='sku' value='"+rs.getString("p_sku")+"'/><input type='hidden' name='name' value='"+rs.getString("p_name")+"'/></form><form style='margin-top:2%' method='post' action='ViewReview'><button class='btn btn-primary' type='submit'>View Review</button><input type='hidden' name='id' value='"+rs.getString("p_id")+"'/><input type='hidden' name='type' value='phones'/><input type='hidden' name='image' value='"+rs.getString("p_image")+"'/><input type='hidden' name='model' value='"+rs.getString("p_model")+"'/><input type='hidden' name='price' value='"+afterDiscount+"'/><input type='hidden' name='manufacturer' value='"+rs.getString("p_manufacturer")+"'/><input type='hidden' name='name' value='"+rs.getString("p_name")+"'/></form></div></div><hr style='margin-top: 3%'>");
			}
			pw.print("</div></div></div></div>");
			utility.printHtml("Footer.html");
		}catch(Exception e){
			System.out.print(e);
		}
 	}
}
