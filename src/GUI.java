import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class GUI extends JFrame 
{
	private JFrame mainFrame;
	private JLabel headerLABEL;
	private JLabel statusLABEL;
	private JPanel ControlPanel;
	String name;
	int stock;
	int choice;
	float cost;
	Timer TIMER;
	
	public GUI()
	{
		prepareGUI();
	}
	
	private void prepareGUI()
	{
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
		ControlPanel.setLayout(new FlowLayout());
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
		ControlPanel.add(DeliveryButton);
		ControlPanel.add(Simbutton);
		ControlPanel.add(Updatebutton);
		ControlPanel.add(ADDbutton);
		ControlPanel.add(Submitbutton);
		ControlPanel.add(Cancelbutton);
		mainFrame.setVisible(true);
	}	

	private class ButtonClickListener implements ActionListener
	{
		product accessPRODUCT = new product();
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
					accessPRODUCT.Report();
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

