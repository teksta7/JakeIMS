import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 * @author Jake
 *	Graphical user interface class 
 *  Access all functions in the IMS through a graphical window using swing
 *	
 */
public class GUI extends JFrame 
{	
	//Create new instance of product, create a new JTable and default table model
	product accessPRODUCT = new product();
	DefaultTableModel tm;
	JTable table = null;
	
	//Other GUI elements including JFrame, JPanel and JLabels
	JScrollPane scrollPane;
	private JFrame mainFrame;
	private JLabel headerLABEL;
	private JLabel statusLABEL;
	private JPanel ControlPanel;
	
	//Variables used when entering product information
	String name;
	int stock;
	int choice;
	float cost;
	
	//New menu bar created using JMenuBar
	JMenuBar menu = new JMenuBar();
	
	public GUI()
	{
		prepareGUI();
	}
	
	/**
	 * @author Jake
	 *	Prepare GUI - sets up the GUI components so that they can be used and are visible
	 */
	private void prepareGUI()
	{
		//Get the product class to update the table when the GUI window loads
		   	try 
		   	{
			table = accessPRODUCT.PopulateTable();
			}
		   	catch (Exception e) 
		   	{
			e.printStackTrace();
			}
		   scrollPane = new JScrollPane(table);
		   		
		//Setup menu items and add them to the menu categories
		//then add the categories to the menu-bar 
	    JMenu fileMenu = new JMenu("File");
	    menu.add(fileMenu);
	    
	    JMenuItem reportmenuItem = new JMenuItem("Save Stock Report");
	    fileMenu.add(reportmenuItem);
	    
	    JMenu stockmenu = new JMenu("Stock");
	    menu.add(stockmenu);

	  	JMenuItem addstockItem = new JMenuItem("Add stock");
	  	stockmenu.add(addstockItem);
	    
	    JMenuItem updatestockItem = new JMenuItem("Update stock");
	    stockmenu.add(updatestockItem);
	    
	    JMenuItem simstockItem = new JMenuItem("Simulate stock");
	    stockmenu.add(simstockItem);
	    
	  /**
	  * @author Jake
	  *	If add stock is selected it will bring up inputDialog boxes to enter new product info
	  * This is then passed on to the product class which will add it the system	
	  */
	  addstockItem.addActionListener(new ActionListener() 
	  {
		 @Override
		  public void actionPerformed(ActionEvent e)
		  {
			headerLABEL.setText("Please Enter the new product below");
			name = JOptionPane.showInputDialog(mainFrame,"Please enter the name of product",null);
			stock = Integer.parseInt(JOptionPane.showInputDialog(mainFrame, "Please enter the quantity of stock for this product", null));
			cost = Float.parseFloat(JOptionPane.showInputDialog(mainFrame, "Please enter the cost of the product", null));
			System.out.println(name + " " + stock + " " + cost);
			
			//Call function to add product to system
			accessPRODUCT.Communicate(name,stock,cost);		
			headerLABEL.setText("New Record Added to System");
			
			//read in latest changes to database and notify user of changes
			accessPRODUCT.Read();
			JOptionPane.showMessageDialog(mainFrame, "Product has been added to the database");
		  }
		 
	  });
	  
	   /**
	   *@author Jake
	   * If update stock is selected it will bring up inputDialog boxes to edit product info
	   * This is then passed on to the product class which will add it the system	
	   */
	  updatestockItem.addActionListener(new ActionListener() 
	  {
		 @Override
		  public void actionPerformed(ActionEvent e)
		  {
			 headerLABEL.setText("Please Enter the new product below");
			 headerLABEL.setText("Updating Record");
			 
			 //get input from dialog boxes and parse string as integer
			 choice = Integer.parseInt(JOptionPane.showInputDialog(mainFrame,"Please enter the ID of the product you want to edit stock levels with",null));
			 stock = Integer.parseInt(JOptionPane.showInputDialog(mainFrame,"Please enter the new stock level for this product",null));
			 accessPRODUCT.Update(choice,stock);
			 JOptionPane.showMessageDialog(mainFrame, "Product details have been updated");
			 accessPRODUCT.Read();

			 
				try {
					//Attempt to refresh table
					table.removeAll();
					table = accessPRODUCT.PopulateTable();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				

		  }
		 
	  });
	  
	  /**
		  * @author Jake
	  *	If simulate stock is selected it call the simulate function and decrease a copy o	
	  */
	  simstockItem.addActionListener(new ActionListener() 
	  {
		 @Override
		  public void actionPerformed(ActionEvent e)
		  {
			 headerLABEL.setText("Estimating stock at the end of the day");
				accessPRODUCT.simulate();
		  }
		 
	  });
	  
	  reportmenuItem.addActionListener(new ActionListener() 
	  {
		 @Override
		  public void actionPerformed(ActionEvent e)
		  {
			//Create a file chooser
			 JFileChooser reportLOC = new JFileChooser();
			 
			 reportLOC.setDialogTitle("Choose a location for the report to be saved in");
			 //In response to a button click:
			 int location =  reportLOC.showOpenDialog(mainFrame);
			 File saved = reportLOC.getSelectedFile();
			 System.out.println("Save to " + saved.getAbsolutePath());
			 accessPRODUCT.Read();
			 accessPRODUCT.Report(saved.getAbsolutePath());
		  }
		 
	  });
		   
		mainFrame = new JFrame("Inventory Management System");
		mainFrame.setSize(1024, 768);
		mainFrame.setLayout(new GridLayout(4,4,4,4));
		headerLABEL = new JLabel("",JLabel.CENTER);
		//headerLABEL.setSize(500, 200);
		statusLABEL = new JLabel("",JLabel.CENTER);
		//statusLABEL.setSize(500, 200);
		mainFrame.addWindowListener(new WindowAdapter()	
		{
			public void windowClosing(WindowEvent windowevent)
			{
				System.exit(0);
			}
		});
		ControlPanel = new JPanel();
		ControlPanel.setLayout(new BorderLayout());
		getContentPane().add(ControlPanel);
		table.setEnabled(false);
		
		
		mainFrame.add(menu);
		mainFrame.add(headerLABEL);
		mainFrame.add(statusLABEL);
		mainFrame.add(ControlPanel);

		
		mainFrame.setVisible(true);
	}
		
	public void Display()
	{
		
		headerLABEL.setText("Please Select an Option");
		statusLABEL.setText("Stock Levels OK");
		JButton ADDbutton = new JButton("Add new record");
		JButton Submitbutton = new JButton("Create Report");
		JButton Cancelbutton = new JButton("List all products");
		JButton Updatebutton = new JButton("Update Stock levels");
		JButton Simbutton = new JButton("Simulate Stock levels");
		JButton DeliveryButton = new JButton("Calculate Deliveries");
		DeliveryButton.setActionCommand("Estimate");
		Simbutton.setActionCommand("Sim");
		Updatebutton.setActionCommand("Update");
		ADDbutton.setActionCommand("ADD");
		Submitbutton.setActionCommand("Submit");
		Cancelbutton.setActionCommand("List");
		DeliveryButton.addActionListener(new ButtonClickListener());
		Simbutton.addActionListener(new ButtonClickListener());
		Updatebutton.addActionListener(new ButtonClickListener());
		ADDbutton.addActionListener(new ButtonClickListener());
		Submitbutton.addActionListener(new ButtonClickListener());
		Cancelbutton.addActionListener(new ButtonClickListener());
		//ControlPanel.add(tester);
		//ControlPanel.add(DeliveryButton);
		//ControlPanel.add(Simbutton);
		//ControlPanel.add(Updatebutton);
		//ControlPanel.add(ADDbutton);
		//ControlPanel.add(Submitbutton);
		//ControlPanel.add(Cancelbutton);
		//ControlPanel.add(table);
		ControlPanel.add(scrollPane, BorderLayout.CENTER);
		
		mainFrame.setVisible(true);
	}	

	private class ButtonClickListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent AE)
		{
			String command = AE.getActionCommand();
			switch (command)
			{
				case "ADD": 
					headerLABEL.setText("Please Enter the new product below");
					
					//
					name = JOptionPane.showInputDialog(mainFrame,"Please enter the name of product",null);
					stock = Integer.parseInt(JOptionPane.showInputDialog(mainFrame, "Please enter the quantity of stock for this product", null));
					cost = Float.parseFloat(JOptionPane.showInputDialog(mainFrame, "Please enter the cost of the product", null));
					System.out.println(name + " " + stock + " " + cost);
					accessPRODUCT.Communicate(name,stock,cost);		
					headerLABEL.setText("New Record Added to System");
					accessPRODUCT.Read();
					break;
					
				case "Submit" :
					headerLABEL.setText("Report Generated");
					accessPRODUCT.Read();
					//accessPRODUCT.Report();
				break;
				
				case "List" :
					headerLABEL.setText("All products currently in system");
					//name = JOptionPane.showMessageDialog(mainFrame,);
					accessPRODUCT.Read();
					break;
					
				case "Update" :
					headerLABEL.setText("Updating Record");
					choice = Integer.parseInt(JOptionPane.showInputDialog(mainFrame,"Please enter the ID of the product you want to edit stock levels with",null));
					stock = Integer.parseInt(JOptionPane.showInputDialog(mainFrame,"Please enter the new stock level for this product",null));
					accessPRODUCT.Update(choice,stock);
					accessPRODUCT.Read();
					break;
					
				case "Sim" :
					headerLABEL.setText("Estimating stock at the end of the day");
					
					//TIMER = new Timer(100,this);
					//TIMER.setInitialDelay(2000);
					//TIMER.start();
					accessPRODUCT.simulate();
					break;
					
				case "Estimate" :
					accessPRODUCT.Delivery();
					break;
			}
		}
	}
		
}

