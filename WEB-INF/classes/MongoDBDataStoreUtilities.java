import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;
                	
public class MongoDBDataStoreUtilities
{
	static DBCollection allReviews;
	public static void getConnection()
	{
		try{
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("customerReviews");
			 allReviews= db.getCollection("allReviews");	
			 System.out.print("Connected to the DB");
		}catch(Exception e){
			System.out.print(e);
		}		
	}

	public static String insertReview(String productId,String productType,String productImage,String productName,String productModel,Double productPrice,String productManufacturer,String productSKU,String userName,String userType,String age,String gender,String city,String zip,Integer rating,String reviewDate,String pros,String cons,String quality,String recommend,String review, String retailerName, String retailerOccupation)
	{
		try
			{		
			 	System.out.print("Insert Review Called");				
				getConnection();
				BasicDBObject doc = new BasicDBObject("title", "allReviews").
					append("userName", userName).
					append("userType", userType).
					append("userCity", city).
					append("userZip", zip).
					append("userAge", age).
					append("userGender", gender).
					append("userReviewDate", reviewDate).
					append("productName", productName).
					append("productID", productId).
					append("productType", productType).
					append("productManufacturer", productManufacturer).
					append("productImage", productImage).
					append("productModel", productModel).
					append("productSKU", productSKU).
					append("userRating",rating).
					append("pros", pros).
					append("cons", cons).
					append("quality", quality).
					append("recommend", recommend).
					append("review", review).
					append("productPrice",productPrice).
					append("retailerName", retailerName).
					append("retailerOccupation",retailerOccupation);
					System.out.print("Inside Insert Review");
					System.out.print(doc);
					allReviews.insert(doc);
				return "Successfull";
			}
			catch(Exception e)
			{
			return "UnSuccessfull";
			}	
	}
	public static void selectReview(String productName)
	{	
		try
			{

			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("customerReviews");
			allReviews= db.getCollection("allReviews");
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("productName",productName);

			DBCursor cursor = allReviews.find(whereQuery);
			System.out.print("\n");
			System.out.print("All Reviews for the Product");
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject) cursor.next();
				System.out.print(obj.getString("productName"));
				System.out.print("\n");
			}
			}
			catch(Exception e)
			{
				System.out.print(e);
			}	
		}
}