import java.util.*;
import java.util.Map;



public class Product {
	private String p_id;
	private String p_name;
	private String p_type;
	private double p_price;
	private String p_image;
	private String p_manufacturer;
	private String p_quantity;
	private String p_model;
	private String p_sku;
	private String p_on_sale;
	private String p_discount;

	public Product(String id,String name, String type, double price, String image, String manufacturer, String quantity, String model, String sku,String on_sale,String discount){
		this.p_id=id;
		this.p_name=name;
		this.p_type=type;
		this.p_price=price;
		this.p_image = image;
		this.p_manufacturer=manufacturer;
		this.p_quantity=quantity;
		this.p_model = model;
		this.p_sku = sku;
		this.p_on_sale = on_sale;
		this.p_discount = discount;
	}
	
	public Product(){
		
	}
	public String getId() {
		return p_id;
	}
	public void setId(String id) {
		this.p_id = id;
	}
	public String getName() {
		return p_name;
	}
	public void setName(String name) {
		this.p_name = name;
	}
	public String getType() {
		return p_type;
	}
	public void setType(String type) {
		this.p_type =type;
	}
	public double getPrice() {
		return p_price;
	}
	public void setPrice(double price) {
		this.p_price = price;
	}
	public String getImage() {
		return p_image;
	}
	public void setImage(String image) {
		this.p_image = image;
	}
	public String getManufacturer() {
		return this.p_manufacturer;
	}
	public void setManufacurer(String retailer) {
		this.p_manufacturer = retailer;
	}
	public String getModel() {
		return p_model;
	}
	public void setModel(String model) {
		this.p_model = model;
	}
	public String getSKU() {
		return p_sku;
	}
	public void setSKU(String sku) {
		this.p_sku = sku;
	}
	public String getDiscount() {
		return this.p_discount;
	}
	public void setDiscount(String discount) {
		this.p_discount = discount;
	}
	public void setOnSale(String sale) {
		this.p_on_sale = sale;
	}
	public String getOnSale() {
		return p_on_sale;
	}
	
}
