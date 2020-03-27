import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  @author Giovanni Caire - TILAB
 */
class BookSellerGui extends JFrame {	
	private BookSellerAgent myAgent;
	
	private JTextField titleField, priceField;
	
	BookSellerGui(BookSellerAgent a) {
		super(a.getLocalName());
		
		myAgent = a;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		p.add(new JLabel("Book title:"));
		titleField = new JTextField(15);
		p.add(titleField);
		p.add(new JLabel("Price:"));
		priceField = new JTextField(15);
		p.add(priceField);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String title = titleField.getText().trim();
					String price = priceField.getText().trim();
					//myAgent.updateCatalogue(title, Integer.parseInt(price));
					newPrice=Integer.parseInt(price);
					int updatePrice= Integer.parseInt(price);

					float acceptedPrice=newPrice/2;
					float posiblePrice=newPrice-(newPrice*75/100);
					
					float acceptedOffert=acceptedPrice;
					float posibleOffert=posiblePrice-(newPrice*75/100);

					while(newPrice>posiblePrice) {
						if (acceptedPrice > posiblePrice && newPrice != acceptedOffert) {
							newPrice=(int) (newPrice-newPrice/5);
							System.out.println("randomWalker: " + newPrice);
						}
						if (acceptedOffert > posibleOffert && newPrice != acceptedPrice) {
							newPrice=(int)(newPrice-newPrice/5);
							System.out.println("imitatingAgent: " + newPrice);
						}
					myAgent.updateCatalogue(title, randomWalk(updatePrice));
					titleField.setText("");
					priceField.setText("");
					}
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(BookSellerGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );
		p = new JPanel();
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		// Make the agent terminate when the user closes 
		// the GUI using the button on the upper right corner	
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
		
		setResizable(false);
	}
	
	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}	

	static int newPrice;
	static int paiment;
	static float posiblePrice;
	static float acceptedPrice;
	static float posibleOffert;
	static float acceptedOffert;
	public static int randomWalk(int x) 
		{
		int currentPosition = 0;
		int currentMax = 0;
		int currentMin = newPrice;

		while (currentPosition != 3 && currentPosition != -3)

			//step randomly

			{
			double random = Math.random();
			if(random < 0.5)
			{
			currentPosition++;
			if (currentPosition > currentMax) {
				currentMax = currentMin++;
				paiment = paiment + currentMax;
			}			}
			else {
				currentPosition--;
				if (currentPosition > currentMin) {
					currentMax = currentMin++;
					paiment = paiment + currentMax;
				}	
			}

			// print location

			System.out.println("curent location: " + currentPosition);
			}
		if(currentPosition ==3 )
		{

			System.out.println("min position was: " + currentMin);;
		}
		else {		
			System.out.println("max position was: " + currentMax);;

			}
		return paiment;
		}
	}
