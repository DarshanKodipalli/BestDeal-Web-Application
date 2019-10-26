import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Home")

/* 
	Home class uses the printHtml Function of Utilities class and prints the Header,LeftNavigationBar,
	Content,Footer of Game Speed Application.

*/

public class Home extends HttpServlet {
	HttpSession session;
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				utility.printHtml("Pre-Content.html");
				utility.printHtml("LeftNavigationBar.html");
				utility.printHtml("Content.html");
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
