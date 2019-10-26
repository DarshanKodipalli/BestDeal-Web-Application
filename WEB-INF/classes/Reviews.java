import java.io.IOException;
import java.io.*;


/* 
	Review class contains class variables username,productname,reviewtext,reviewdate,reviewrating

	Review class has a constructor with Arguments username,productname,reviewtext,reviewdate,reviewrating
	  
	Review class contains getters and setters for username,productname,reviewtext,reviewdate,reviewrating
*/

public class Reviews implements Serializable{
	private String userName;
	private String userType;
	private String userCity;
	private String userZip;
	private String userAge;
	private String userGender;
	private String userReviewDate;
	private String productName;
	private String productID;
	private String productType;
	private String productManufacturer;
	private String productImage;
	private String productModel;
	private String productSKU;
	private Integer userRating;
	private String pros;
	private String cons;
	private String review;
	private String quality;
	private String recommend;
	private Double price;

	public Reviews(String userName, String userType, String userCity, String userZip, String userAge, String userGender, String userReviewDate, String productName, String productID, String productType, String productManufacturer, String productImage, String productModel, String productSKU, Integer userRating, String pros, String cons, String review, String quality, String recommend, Double price) {
		this.userName = userName;
		this.userType = userType;
		this.userCity = userCity;
		this.userZip = userZip;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userReviewDate = userReviewDate;
		this.productName = productName;
		this.productID = productID;
		this.productType = productType;
		this.productManufacturer = productManufacturer;
		this.productImage = productImage;
		this.productModel = productModel;
		this.productSKU = productSKU;
		this.userRating = userRating;
		this.pros = pros;
		this.cons = cons;
		this.review = review;
		this.quality = quality;
		this.recommend = recommend;
		this.price = price;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserZip() {
		return userZip;
	}

	public void setUserZip(String userZip) {
		this.userZip = userZip;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserReviewDate() {
		return userReviewDate;
	}

	public void setUserReviewDate(String userReviewDate) {
		this.userReviewDate = userReviewDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductManufacturer() {
		return productManufacturer;
	}

	public void setProductManufacturer(String productManufacturer) {
		this.productManufacturer = productManufacturer;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getProductSKU() {
		return productSKU;
	}

	public void setProductSKU(String productSKU) {
		this.productSKU = productSKU;
	}

	public Integer getUserRating() {
		return userRating;
	}

	public void setUserRating(Integer userRating) {
		this.userRating = userRating;
	}

	public String getPros() {
		return pros;
	}

	public void setPros(String pros) {
		this.pros = pros;
	}

	public String getCons() {
		return cons;
	}

	public void setCons(String cons) {
		this.cons = cons;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
