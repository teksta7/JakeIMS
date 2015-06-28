import java.sql.DriverManager;

import javax.swing.Timer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.io.DataOutputStream;

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
		DateFormat Dformat = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		Date DATE = new Date();
		File reportOUT = new File("C:/Users/Jake/report.txt");
		PrintWriter OUTPUT = new PrintWriter(reportOUT, "UTF-8");
		OUTPUT.println("Product list created on " + Dformat.format(DATE));
		for(int i = 0; i < RECORDS; ++i)
		{
			try
			{
			 data.next(); 
			 ID = data.getInt("ID");
			 name = data.getString("Product_Name");
			 stock = data.getInt("Stock_Quantity");
			 cost = data.getFloat("Cost");
			 
			 
			OUTPUT.print("ID: "); OUTPUT.print(ID); OUTPUT.print(" ");
			OUTPUT.print("Product Name: "); OUTPUT.print(name); OUTPUT.print(" ");
			OUTPUT.print("Stock Quantity "); OUTPUT.print(stock); OUTPUT.print(" ");
			OUTPUT.print("Product Cost "); OUTPUT.print(cost); OUTPUT.println(" ");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.print("File created");
		OUTPUT.close();
		RECORDS = 0;
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
}

public void Update(int lCHOICE, int lSTOCK)
{
	try
	{
	 System.out.println("Preparing to update");
	 CONN = DriverManager.getConnection(DB_URL,username,password);
	 update = CONN.createStatement();
	 System.out.println("Connection established");
	 String updateSQL = "UPDATE product SET Stock_Quantity = " + lSTOCK + " WHERE ID = " + lCHOICE; 
	 update.executeUpdate(updateSQL);
	 System.out.println("Changes Saved");
	 //data.close();
	 CONN.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

public void simulate()
{
	try
	{
		ArrayList localstorageSTOCK = new ArrayList();
		ArrayList localstorageNAME = new ArrayList();
		Random GEN = new Random();
		int currentstock;
		//System.out.println(localstorage.size());
		System.out.println("Preparing to Simulate Stock, Please wait...");
		CONN = DriverManager.getConnection(DB_URL,username,password);
		update = CONN.createStatement();
		System.out.println("Connection established");
		String selectSQL = "Select Product_Name,Stock_Quantity FROM Product"; 
		data = update.executeQuery(selectSQL);
		 while (data.next())
		 {
			 name = data.getString("Product_Name");
			 stock = data.getInt("Stock_Quantity");
			 //int adjustVALUE = GEN.nextInt(21);
			 localstorageSTOCK.add(stock);
			 localstorageNAME.add(name);
			// ++RECORDS;
			// System.out.println("Product Name:" + name + " Stock Quantity " + stock);
		 }
		 System.out.println("Stock levels at the beginning of the day");
		 System.out.println(localstorageNAME);
		 System.out.println(localstorageSTOCK);
		 for(int i = 0; i < localstorageSTOCK.size(); ++i)
		 {
			 int adjustVALUE = GEN.nextInt(21);
			 currentstock = (Integer)localstorageSTOCK.get(i);
			 currentstock = currentstock - adjustVALUE;
			 localstorageSTOCK.set(i, currentstock);
		 }
		 System.out.println("Estimated Stock levels at the end of the day");
		 System.out.println(localstorageNAME);
		 System.out.println(localstorageSTOCK);
		 data.close();
		 CONN.close();
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
}

