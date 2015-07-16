import java.io.IOException;
import java.util.Scanner;
/**
 * @author Jake
 *	Main class which calls the menu to select which interface to use
 *	
 */
public class main {

	public static void main(String[] args) {
		
		System.out.println("Opened OK");
		
		//Call menu function
		menu();
	}
	
	/**
	 * @author Jake
	 *	Asks for user input in command line and then selects relevant interface to display
	 *	Either CLI - command line interface
	 *	or GUI - Graphical user interface
	 */
	public static void menu()
	{
		//Setup new input stream via scanner and variable choice to hold selection
		Scanner input = new Scanner(System.in);
		int choice = 0;
		
		//Options to choose
		System.out.println("Which interface would you like to use?");
		System.out.println("1: Graphical user interface");
		System.out.println("2: Command line interface");
		
		//Get next input into command line to load chosen interface
		choice = input.nextInt();
		switch(choice)
		{
			case 1 :
				//Load new instance of GUI
				System.out.println("GUI interface Selected");
				GUI graphics = new GUI();
				graphics.Display();
				break;
				
			case 2:
				//Load new instance of CLI
				System.out.println("Command Line Interface Selected");
				CLI command = new CLI();
				command.Menu();
				//CLI class
				break;

			default :
				//If no option is entered/doesn't match options available call menu again
				System.out.println("No option selected Please try again");
				menu();
				break;
		}	
	}
}
