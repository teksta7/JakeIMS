import java.util.Scanner;

/**
 * @author Jake
 *	Command Line interface class 
 *  Access all functions in the IMS through command line
 *	
 */
public class CLI {
	// Setup instance of product and setup scanners used in menu 
	product cmdP = new product();
	Scanner input = new Scanner(System.in);
	Scanner string = new Scanner(System.in);
	
	public void Menu()
	{
		//Options that can be selected in the menu
		System.out.println("Please select one of five options ");
		System.out.println("1: Add a record");
		System.out.println("2: List all products");
		System.out.println("3: Create Report");
		System.out.println("4: Simulate stock");
		System.out.println("5: Update stock");
		switch(input.nextInt())
		{
			
			case 1:
				//Get information about new product and pass on to product class
				System.out.println("Enter the name of the product");
				String name = string.nextLine();
				System.out.println("Enter the quantity for this product");
				int stock =  input.nextInt();
				System.out.println("Enter the cost of the product");
				float cost = input.nextFloat();
				cmdP.Communicate(name, stock, cost);
				break;
				
			case 2: 
				//Call function to list all products in command line
				cmdP.Read();
				break;
				
			case 3:	
				//Call report function passing in the location to save report
				System.out.println("Report Saved in /home of this user");
				cmdP.Read();
				cmdP.Report("C:/");
				break;
				
			case 4:
				//Call simulate stock function to output in command line
				System.out.println("Estimating stock at the end of the day");
				cmdP.simulate();
				
			case 5:
				//Get information of product to edit and how its edited
				//Then pass on to update function
				int choice;
				System.out.println("Please enter the ID of the product you want to edit stock levels with");
				choice = input.nextInt();
				System.out.println("Now enter the new stock level for this product");
				stock = input.nextInt();
				cmdP.Update(choice, stock);
				cmdP.Read();

		}
		
		//Call menu after an operation has been completed
		Menu();
	}

}
