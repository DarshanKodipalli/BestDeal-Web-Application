import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;


@WebServlet("/Utilities")

/*
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.

*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
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

	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument,
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
				result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				String usertype = session.getAttribute("username").toString();
				result = result + "<ul class='nav navbar-nav navbar-right'><li style='color:white;font-weight:bolder'><a style='font-weight:bolder;color:white'> Welcome, &nbsp;"+username+"</a></li><li style='color:white;font-weight:bolder'><a href='Account' style='cursor:pointer'><span class='glyphicon glyphicon-user'></span>  Account</a></li><li style='color:white;font-weight:bolder'><a href='/BestDealApp/AddToCart'><span class='glyphicon glyphicon-shopping-cart'></span> Cart</a></li><li style='color:white;font-weight:bolder'><a href='Logout'><span class='glyphicon glyphicon-log-out'></span> Logout</a></li></ul></div></div></nav>";
			}
			else
				result = result +"<ul class='nav navbar-nav navbar-right'><li><a style='font-size: 150%' href='#' data-toggle='modal' data-target='#ModalSignupForm'><span class='glyphicon glyphicon-user'></span> Sign Up</a></li><li><a style='font-size: 150%' href='#' data-toggle='modal' data-target='#ModalLoginForm'><span class='glyphicon glyphicon-log-in'></span> Login</a></li></ul></div></div></nav><div id='ModalLoginForm' class='modal fade'><div class='modal-dialog' role='document'><div class='modal-content'><div class='modal-header' style='color: black'><h1>Login</h1></div><div class='modal-body'><form method='post' action='Login'><input type='hidden' value=''><div class='form-group'><label class='control-label'>User Name</label><div><input type='text' name='username' class='form-control' style='width: 90%' value=''></div></div>"+
								 "<div class='form-group'><label class='control-label'>Password</label><div><input type='password' class='form-control' style='width: 90%' name='password'></div></div><div class='form-group'><label class='control-label'>User Type</label><select name='usertype' class='form-control' style='width: 90%' class='input'><option value='customer' selected>Customer</option><option value='retailer'>Store Manager</option><option value='manager'>Salesman</option></select></div><div class='form-group'><div><div class='checkbox'><label><input type='checkbox' name='remember'> Remember Me</label></div>"+
								 "</div></div><div class='form-group'><div><label><p>Don't have an account yet?<a type='checkbox'  name='remember' data-toggle='modal' data-target='#ModalSignupForm'>Click here</a></p></label></div></div><div class='form-group'><div style='float: right;'><button type='submit' class='btn btn-lg btn-success'>Login</button></div><a class='btn btn-link' href=''></a></div></form></div></div></div></div><div id='ModalSignupForm' class='modal fade'><div class='modal-dialog' role='document'><div class='modal-content'><div class='modal-header' style='color: black'><h1>Sign-Up</h1></div>"+
								 "<div class='modal-body'><form method='post' action='Registration'><input type='hidden'  value=''><div class='form-group'><label class='control-label'>User Name</label><div><input type='text' name='username' class='form-control' style='width: 90%' ></div></div><div class='form-group'><label class='control-label'>Password</label><div><input type='password' class='form-control' name='password' style='width: 90%' value=''></div></div><div class='form-group'><label class='control-label'>Retype Password</label><div><input type='password' class='form-control' style='width: 90%' name='repassword'>"+
								 "</div></div><div class='form-group'><label class='control-label'>User Type</label><select name='usertype' class='form-control' style='width: 90%' class='input'><option value='customer' selected>Customer</option><option value='retailer'>Store Manager</option><option value='manager'>Salesman</option></select></div><div class='g-recaptcha' id='rcaptcha'  data-sitekey='6LdiVLkUAAAAAIkc9llI68N2YZSsHvBVvQaRzR8m'></div><span id='captcha' style='color:red' /></span><div class='form-group'><div style='float: right;'><button type='submit' class='btn btn-lg btn-success'>Register</button></div><a class='btn btn-link' href=''></a></div></form></div></div></div></div>";
				pw.print(result);
		} else
				pw.print(result);
	}


	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}
	public String getUserName(){
		return session.getAttribute("username").toString();
	}
	public String getUserType(){
		return session.getAttribute("usertype").toString();		
	}
	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		}
		catch (Exception e) {
		}
		return result;
	}

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}

	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/

	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}

	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}

	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{
				Path fpath = Paths.get(TOMCAT_HOME, "webapps", "BestDealApp", "UserDetails.txt");
				FileInputStream fileInputStream=new FileInputStream(new File(fpath.toString()));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				hm= (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			}
		User user = hm.get(username());
		return user;
	}
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>();
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{
				Path fpath = Paths.get(TOMCAT_HOME, "webapps", "BestDealApp", "PaymentDetails.txt");
				FileInputStream fileInputStream = new FileInputStream(new File(fpath.toString()));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				orderPayments = (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{

			}
			int size=0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
					 size=size + 1;

			}
			return size;
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		int cartcount = 0;	
		try{
			getConnection();
			String selectproductsQuery ="select count(*) as count from orders where o_username=?";
			System.out.print(username());
			PreparedStatement pst = conn.prepareStatement(selectproductsQuery);
			pst.setString(1,username());			
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				cartcount = Integer.parseInt(rs.getString("count"));
			}
		}catch(Exception e){
			System.out.print(e);
		}
		return cartcount;
	}

	public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			// get the payment details file
			try
			{
				Path fpath = Paths.get(TOMCAT_HOME, "webapps", "BestDealApp", "PaymentDetails.txt");
				FileInputStream fileInputStream = new FileInputStream(new File(fpath.toString()));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				orderPayments = (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{

			}
			if(orderPayments==null)
			{
				orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			}
			// if there exist order id already add it into same list for order id or create a new record with order id

			if(!orderPayments.containsKey(orderId)){
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(orderId, arr);
			}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
		listOrderPayment.add(orderpayment);

			// add order details into file

			try
			{
				Path fpath = Paths.get(TOMCAT_HOME, "webapps", "BestDealApp", "PaymentDetails.txt");
				FileOutputStream fileOutputStream = new FileOutputStream(new File(fpath.toString()));
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            	objectOutputStream.writeObject(orderPayments);
				objectOutputStream.flush();
				objectOutputStream.close();
				fileOutputStream.close();
			}
			catch(Exception e)
			{
				System.out.println("inside exception file not written properly");
			}
	}	

    public String storeReview(String productId,String productType,String productImage,String productName,String productModel,Double productPrice,String productManufacturer,String productSKU,String userName,String userType,String age,String gender,String city,String zip,Integer rating,String reviewDate,String pros,String cons,String quality,String recommend,String review, String retailerName, String retailerOccupation){
		System.out.print("Store Review Called");				
		String message=MongoDBDataStoreUtilities.insertReview(productId,productType,productImage,productName,productModel,productPrice,productManufacturer,productSKU,userName,userType,age,gender,city,zip,rating,reviewDate,pros,cons,quality,recommend,review, retailerName, retailerOccupation);
		return message;
	}

	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String image, String model, double price){
		
		System.out.print("name " + name + " ,type " + type + " ,model " + model+ " ,price " + price+ " ,image " + image+"\n");

		if(!OrdersHashMap.orders.containsKey(username())){
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		if(type.equals("tvs")){
			TV tv;
			tv = SaxParserDataStore.tvs.get(name);
			OrderItem orderitem = new OrderItem(name, price, image, type, model);
			orderItems.add(orderitem);
		}
		if(type.equals("soundSystems")){
			SoundSystem soundSystems;
			soundSystems = SaxParserDataStore.soundSystems.get(name);
			OrderItem orderitem = new OrderItem(soundSystems.getName(), soundSystems.getPrice(), soundSystems.getImage(), soundSystems.getManufacturer(), soundSystems.getModel());
			orderItems.add(orderitem);
		}
		if(type.equals("phones")){
			Phone phone;
			phone = SaxParserDataStore.phones.get(name);
			OrderItem orderitem = new OrderItem(phone.getName(), phone.getPrice(), phone.getImage(), phone.getManufacturer(), phone.getModel());
			orderItems.add(orderitem);
		}
		if(type.equals("laptops")){
			Laptop laptop = SaxParserDataStore.laptops.get(name);
			OrderItem orderitem = new OrderItem(laptop.getName(), laptop.getPrice(), laptop.getImage(), laptop.getManufacturer(), laptop.getModel());
			orderItems.add(orderitem);
		}
		if(type.equals("fitnesses")){
			Fitness fitness;
			fitness = SaxParserDataStore.fitnesses.get(name);
			OrderItem orderitem = new OrderItem(fitness.getName(), fitness.getPrice(), fitness.getImage(), fitness.getManufacturer(), fitness.getModel());
			orderItems.add(orderitem);
		}
		if(type.equals("smarts")){
			Smart ss;
			ss = SaxParserDataStore.smarts.get(name);
			OrderItem orderitem = new OrderItem(ss.getName(), ss.getPrice(), ss.getImage(), ss.getManufacturer(), ss.getModel());
			orderItems.add(orderitem);
		}
		if(type.equals("headphoness")){
			Headphones headphone;
			headphone = SaxParserDataStore.headphoness.get(name);
			OrderItem orderitem = new OrderItem(headphone.getName(), headphone.getPrice(), headphone.getImage(), headphone.getManufacturer(), headphone.getModel());
			orderItems.add(orderitem);
		}
		if(type.equals("wireless")){
			Wireless wireless = SaxParserDataStore.wirelesss.get(name);
			OrderItem orderitem = new OrderItem(wireless.getName(), wireless.getPrice(), wireless.getImage(), wireless.getManufacturer(), wireless.getModel());
			orderItems.add(orderitem);
		}
		if(type.equals("voice")){
			Voice voice = SaxParserDataStore.voices.get(name);
			OrderItem orderitem = new OrderItem(voice.getName(), voice.getPrice(), voice.getImage(), voice.getManufacturer(), voice.getModel());
			orderItems.add(orderitem);
		}

	}
}