import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
	private JFrame frame;
	private JPanel panel;
	private JTextArea textField;
	private JPanel inputPanel;
	private JTextField inputField;
	private JButton sendButton;

	public GUI() {
		frame = new JFrame("Chat Master 3000");
		//frame.setDefaultCloseOperation(exit()); TODO
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setOpaque(true);
		
		
		textField = new JTextArea(15,50); //To contain the incomming text
		textField.setWrapStyleWord(true);
		textField.setEditable(false);
		
		JScrollPane scroller = new JScrollPane(textField);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		
		inputField = new JTextField(20);
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(this);

		
		panel.add(scroller);
		inputPanel.add(inputField);
		inputPanel.add(sendButton);
		panel.add(inputPanel);
		
		frame.getContentPane().add(BorderLayout.CENTER,panel);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setResizable(false);
		inputField.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

//	public void createTable(ArrayList<String> values) {
//		if (pane != null) {
//			frame.remove(pane);
//		}
//		String[] colNames = new String[Integer.parseInt(values.get(0))];
//		int numberOfCol = Integer.parseInt(values.get(0));
//		for (int i = 0; i < numberOfCol; i++) {
//			colNames[i] = values.get(i + 1);
//		}
//		int numberOfRows = (values.size() - (1 + numberOfCol))
//				/ (colNames.length);
//		model = new DefaultTableModel(colNames, numberOfRows);
//		table = new JTable(model) {
//			@Override
//			public boolean isCellEditable(int arg0, int arg1) {
//
//				return false;
//			}
//		};
//		pane = new JScrollPane(table);
//		for (int i = 0; i < numberOfRows; i++) {
//			for (int j = 0; j < numberOfCol; j++) {
//				table.setValueAt(
//						values.get(numberOfCol * i + j + (numberOfCol + 1)), i,
//						j);
//			}
//		}
//		frame.add(pane);
//		setVisible(true);
//	}
//
//
//
//		deleteItems.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (count < ingredients.size()) {
//					//dropdown.addItem(ingredients.get(count++));
//					q.deleteIngredientsToKitchen(Integer.parseInt(amountSpinner
//							.getValue().toString()), dropdown.getSelectedItem()
//							.toString());
//					createTable(q.selectKitchen());
//				}
//			}
//		});
//		frame.add(amountSpinner);
//		frame.add(dropdown);
//		frame.add(addItems);
//		frame.add(deleteItems);
//
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent event) {
//		if (event.getSource() == sendButton) {
//			ArrayList<String> inKitchen = new ArrayList<String>();
//			inKitchen = q.selectKitchen();
//			createTable(inKitchen);
//
//		} else if (event.getSource() == exitButton) {
//			ArrayList<String> inKitchen = new ArrayList<String>();
//			inKitchen = q.checkRecipes();
//			createTable(inKitchen);
//		} else if (event.getSource() == mybutton3) {
//			ArrayList<String> recipes = new ArrayList<String>();
//			if (check1.isSelected()) {
//				recipes.add(check1.getText());
//			}
//			if (check2.isSelected()) {
//				recipes.add(check2.getText());
//			}
//			if (check3.isSelected()) {
//				recipes.add(check3.getText());
//			}
////			System.out.println(recipes.toString().replace("[", "'")
////					.replace(",", "', '").replace("]", "'"));
//			createTable(q.customShoppingList(recipes));
//
//		} else if (event.getSource() == mybutton4) {
//			ArrayList<String> recipes = new ArrayList<String>();
//			if (check1.isSelected()) {
//				recipes.add(check1.getText());
//			}
//			if (check2.isSelected()) {
//				recipes.add(check2.getText());
//			}
//			if (check3.isSelected()) {
//				recipes.add(check3.getText());
//			}
////			System.out.println(recipes.toString().replace("[", "'")
////					.replace(",", "', '").replace("]", "'"));
//			if (q.cookRecipe(recipes)) {
//				title.setText("You have cooked: " + recipes.toString());
//			} else {
////				System.out
////						.println("You have not enough ingredients to cook this!");
//				title.setText("You have not enough ingredients to cook this!");
//			}
//
//		}
//
//	}
}