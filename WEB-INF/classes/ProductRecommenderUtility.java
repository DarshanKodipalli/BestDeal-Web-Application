import java.io.*;
import java.sql.*;
import java.io.IOException;
import java.util.*;
import java.nio.file.*;

public class ProductRecommenderUtility{
	
	static Connection conn = null;
    static String message;
	
	public static String getConnection()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BDDB?autoReconnect=true&useSSL=false","root","edenUbuntu");							
			message="Successfull";
			return message;
		}
		catch(SQLException e)
		{
			 message="unsuccessful";
		     return message;
		}
		catch(Exception e)
		{
			 message="unsuccessful";
		     return message;
		}
	}

	public HashMap<String,String> readOutputFile(){

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		try {
			String TOMCAT_HOME = System.getProperty("catalina.home");
			Path fpath = Paths.get(TOMCAT_HOME, "webapps", "BestDealApp", "output.csv");
			BufferedReader reader = new BufferedReader(new FileReader (fpath.toString()));
            while ((line = reader.readLine()) != null) {

                // use comma as separator
                String[] prod_recm = line.split(cvsSplitBy,2);
				prodRecmMap.put(prod_recm[0],prod_recm[1]);
            }
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		
		return prodRecmMap;
	}
	
	public static Product getProduct(String product){
		Product prodObj = new Product();
		try 
		{
			String msg = getConnection();
			String selectProd="select * from  product_catalog where p_name=?";
			PreparedStatement pst = conn.prepareStatement(selectProd);
			pst.setString(1,product);
			ResultSet rs = pst.executeQuery();
			System.out.print(pst);
			while(rs.next())
			{	
				prodObj = new Product(rs.getString("p_id"),rs.getString("p_name"),rs.getString("p_type"),rs.getDouble("p_price"),rs.getString("p_image"),rs.getString("p_manufacturer"),rs.getString("p_quantity"),rs.getString("p_model"),rs.getString("p_sku"),rs.getString("p_on_sale"),rs.getString("p_quantity"));
			}
			rs.close();
			pst.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return prodObj;	
	}
}