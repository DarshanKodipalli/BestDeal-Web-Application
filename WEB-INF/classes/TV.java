import java.util.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TV")


/* 
	Console class contains class variables name,price,image,manufacturer,model,sku and Accessories Hashmap.

	Console class constructor with Arguments name,price,image,manufacturer,model,sku and Accessories .
	  
	Accessory class contains getters and setters for name,price,image,manufacturer,model,sku and Accessories .

*/

public class TV extends HttpServlet{
	private String id;
	private String name;
	private double price;
	private String image;
	private String manufacturer;
	private String model;
	private String sku;
	HashMap<String,String> accessories;
	public TV(String name, double price, String image, String manufacturer,String model,String sku){
		this.name=name;
		this.price=price;
		this.image=image;
		this.manufacturer = manufacturer;
		this.model=model;
		this.sku = sku;
        this.accessories=new HashMap<String,String>();
	}
	
    HashMap<String,String> getAccessories() {
		return accessories;
		}

	public TV(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setAccessories( HashMap<String,String> accessories) {
		this.accessories = accessories;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
}
