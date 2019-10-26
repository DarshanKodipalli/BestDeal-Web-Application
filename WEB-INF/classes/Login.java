import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.file.*;

@WebServlet("/Login")

public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* User Information(username,password,usertype) is obtained from HttpServletRequest,
		Based on the Type of user(customer,retailer,manager) respective hashmap is called and the username and
		password are validated and added to session variable and display Login Function is called */

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		HashMap<String, User> hm=new HashMap<String, User>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
		//user details are validated using a file
		//if the file containts username and passoword user entered user will be directed to home page
		//else error message will be shown
		try
		{
			hm=MySqlDataStoreUtilities.selectUser();
		}
		catch(Exception e)
		{

		}
		User user = hm.get(username);
		if(user!=null)
		{
		 String user_password = user.getPassword();
		 String type = user.getUsertype();
		 if (password.equals(user_password) && usertype.equals(type))
			{
			HttpSession session = request.getSession(true);
			session.setAttribute("username", user.getName());
			session.setAttribute("usertype", user.getUsertype());
			response.sendRedirect("Home");
			return;
			}
		}
		displayLogin(request, response, pw, true);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}


	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='entry'>"
				+ "<div style='width:100%; margin:25px; margin-left: auto;margin-right: auto;'>");
		if (error)
			pw.print("<h4 style='color:red'>Please check your username, password and user type!</h4>");
		HttpSession session = request.getSession(true);
		if(session.getAttribute("login_msg")!=null){
//			pw.print("<h4 style='color:red'>"+session.getAttribute("login_msg")+"</h4>");
			session.removeAttribute("login_msg");
		}
		pw.print("<div class='col-sm-12 col-md-12 col-lg-10' style='margin-top:2%;margin-left:10%'>");
    	pw.print("<div class='card card-price'>");
      	pw.print("<div  class='card-img'>");
        pw.print("<a href='#'>");
/*        pw.print("<img style='width:100%;margin-left:80%;width:50%;height:50%' src='https://www.activehands.com/wp-content/uploads/2019/05/kisspng-check-mark-computer-icons-clip-art-green-check-circle-5b3fb28f711452.5419564815309011354632.png' class='img-responsive'>");
*/        pw.print("<div class='card-caption'>");
        pw.print("</div></a></div>");
        pw.print("<div class='card-body'>");
        pw.print("<div class='lead'><h3>Your Customer Account is Created!</h3></div>");
        pw.print("<ul class='details'>");
        pw.print("<li>Please login to avail the Best deals!</li>");
        pw.print("</ul><a href='/BestDealApp/Home' class='btn btn-primary btn-lg btn-block buy-now'>");
        pw.print("Login <span class='glyphicon glyphicon-log-in'></span>");
        pw.print("</a></div></div></div></div></div></div>");            
	}

}
