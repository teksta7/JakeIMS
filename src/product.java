import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;


public class product 
{
	Scanner input = new Scanner(System.in);
	String name;
	int stock;
	float cost;
	static final String JDBC_DRIVER = "com.mysql.jbdc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/ims";
	static final String username = "imt_user1";
	static final String password = "user1";
	
	public void Communicate(String Lname, int Lstock, float Lcost)
	{	
		Connection CONN = null;
		Statement update = null;
	 try
	 {
		 //Class.forName(JDBC_DRIVER);
		 System.out.println("Connecting to the data base, Please wait...");
		 CONN = DriverManager.getConnection(DB_URL,username,password);
		 System.out.println("Connection established");
		 update = CONN.createStatement();
		 //System.out.println("Enter Product Name");
		 //name = input.next();
		 //System.out.println("Enter quantity of stock");
		 //stock = input.nextInt();
		 //System.out.println("Enter cost of product");
		 //cost = input.nextFloat();
		 String insertSQL = "INSERT INTO product VALUES(default, '" + Lname + "', " + Lstock + ", " + Lcost + ")";
		 update.executeUpdate(insertSQL);
		 System.out.println("Record added to product table");
		 
		 CONN.close();
		 System.out.println("Connection Closed");
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();
	 }
	
	}
	
}
