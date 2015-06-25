import java.sql.DriverManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class product 
{
	Scanner input = new Scanner(System.in);
	int ID;
	String name;
	int stock;
	float cost;
	int RECORDS = 0;
	static final String JDBC_DRIVER = "com.mysql.jbdc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/ims";
	static final String username = "imt_user1";
	static final String password = "user1";
	
	Connection CONN = null;
	Statement update = null;
	ResultSet data;
	
	public void Communicate(String Lname, int Lstock, float Lcost)
	{		
	 try
	 {
		 //Class.forName(JDBC_DRIVER);
		 System.out.println("Connecting to the data base, Please wait...");
		 CONN = DriverManager.getConnection(DB_URL,username,password);
		 System.out.println("Connection established");
		 update = CONN.createStatement();
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
	
public void Read()
	{
		try
		{
		 System.out.println("Fetching products Please wait...");
		 CONN = DriverManager.getConnection(DB_URL,username,password);
		 update = CONN.createStatement();
		 System.out.println("Connection established");
		 String selectSQL = "Select ID,Product_Name,Stock_Quantity,Cost FROM Product"; 
		 data = update.executeQuery(selectSQL);
		 while (data.next())
		 {
			 ID = data.getInt("ID");
			 name = data.getString("Product_Name");
			 stock = data.getInt("Stock_Quantity");
			 cost = data.getFloat("Cost");
			 ++RECORDS;
			 System.out.println("ID:" + ID + " Product Name:" + name + " Stock Quantity " + stock + " Cost " + cost);
		 }
		 data.close();
		 CONN.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}

public void Report()
{
	int Convert;
	try
	{
		 CONN = DriverManager.getConnection(DB_URL,username,password);
		 update = CONN.createStatement();
		 String selectSQL = "Select ID,Product_Name,Stock_Quantity,Cost FROM Product"; 
		 data = update.executeQuery(selectSQL);

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	
	try
	{
		File StockReport = new File("C:/Users/Jake/report.txt");
		
		if(!StockReport.exists())
		{
			StockReport.createNewFile();
		}
		FileWriter RW = new FileWriter(StockReport.getAbsoluteFile());
		BufferedWriter BW = new BufferedWriter(RW);
		for(int i = 0; i < RECORDS; ++i)
		{
			try
			{
			 data.next(); 
			 ID = data.getInt("ID");
			 name = data.getString("Product_Name");
			 stock = data.getInt("Stock_Quantity");
			 cost = data.getFloat("Cost");
			 
			 Convert = (int)cost;
	
			 //ADD multiple fields top file
			 //BW.write(name, stock, Convert);
			 BW.write(name);
			 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.print("File created");
		BW.close();
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
}

}
