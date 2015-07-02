import java.io.IOException;
import java.util.Scanner;



public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.setProperty("java.awt.headless", "true");
		//System.out.println("Checking if Java Headless Mode is active...");
		//System.out.println(java.awt.GraphicsEnvironment.isHeadless());
		System.out.println("Opened OK");
		menu();
		//System.in.read()
		//GUI graphics = new GUI();
		//graphics.Display();
		//product accessPRODUCT = new product();
		//accessPRODUCT.Communicate();
		

	}
	
	public static void menu()
	{
		Scanner input = new Scanner(System.in);
		int choice = 0;
		System.out.println("Which interface would you like to use?");
		System.out.println("1: Graphical user interface");
		System.out.println("2: Command line interface");
		choice = input.nextInt();
		//System.out.println(choice);
		switch(choice)
		{
			case 1 :
				System.out.println("GUI interface Selected");
				GUI graphics = new GUI();
				graphics.Display();
				break;
				
			case 2:
				System.out.println("Command Line Interface Selected");
				CLI command = new CLI();
				command.Menu();
				//CLI class
				break;

			default :
				System.out.println("No option selected Please try again");
				menu();
				break;
		}
		
	}

}
