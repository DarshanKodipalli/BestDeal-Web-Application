import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.nio.file.*;

@WebServlet("/AddProduct")

public class AddProduct extends HttpServlet {
	private String error_msg;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String productType= "",productId="",productName="",productImage="",productManufacturer="",productModel="",productSKU = ""; 
		double productPrice=0.0;
		productId = request.getParameter("productId");
		productType = request.getParameter("productType");
		productName = productId = request.getParameter("productName");
		productPrice = Double.parseDouble(request.getParameter("productPrice"));
		productSKU = request.getParameter("productSKU");
		productModel = request.getParameter("productModel");
		productManufacturer = request.getParameter("productManufacturer");
		String message = "";
		productImage = "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/6421/6421026_sd.jpg;maxHeight=640;maxWidth=550";
		try{
			message = MySqlDataStoreUtilities.addproducts(productId,productType, productName, productPrice, productSKU, productModel, productManufacturer, productImage);
		}catch(Exception e){
			System.out.print(e);
		}
		displayAddProduct(request, response);
	}

	/* Display AddProduct Details of the Customer (Name and Usertype) */

	protected void displayAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("Pre-Content.html");						
		pw.print("<div class='col-sm-12 col-md-12 col-lg-10' style='margin-top:2%;margin-left:10%'>");
    	pw.print("<h2><u>Product Added Confirmation:</u></h2>");
    	pw.print("<div style='margin-top:10%'>");
    	pw.print("<div class='card card-price'>");
      	pw.print("<div style='margin-left:3%;margin-top:3%' class='card-img'>");
        pw.print("<a href='#'>");
        pw.print("<div class='card-caption'>");
        pw.print("</div></a></div>");
        pw.print("<div class='card-body'>");
        pw.print("<div class='price'>Product Name: &nbsp;&nbsp;"+request.getParameter("productName")+"</div>");                        
        pw.print("<div class='lead'><h3><u>Product Details</u></h3></div>");
        pw.print("<ul class='details'>");
        pw.print("<li><h4>Product Type:   "+request.getParameter("productType")+"</h4></li>");
        pw.print("<li><h4>Product Price:   "+request.getParameter("productPrice")+"</h4></li>");
        pw.print("<li><h4>Product Model:   "+request.getParameter("productModel")+"</h4></li>");
        pw.print("<li><h4>Product SKU:   "+request.getParameter("productSKU")+"</h4></li>");                        
        pw.print("</ul><a href='/BestDealApp/ViewProductsAdded' class='btn btn-primary btn-lg btn-block buy-now'>");
        pw.print("View Added Products <span class='glyphicon glyphicon-shopping-cart'></span>");
        pw.print("</a></div></div></div></div></div></div></div>");            
	}
}