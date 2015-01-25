import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel panel;
	private JTextArea textField;
	private JPanel inputPanel;
	private JTextField inputField;
	private JButton sendButton;
	private JScrollPane scroller;

	public GUI() {
		frame = new JFrame("Chat Master 3000");
		//frame.setDefaultCloseOperation(exit()); TODO
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setOpaque(true);
		
		
		textField = new JTextArea(15,50); //To contain the incomming text
		textField.setWrapStyleWord(true);
		textField.setEditable(false);
		DefaultCaret caret = (DefaultCaret)textField.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		scroller = new JScrollPane(textField);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		
		inputField = new JTextField(20);
		inputField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				outgoingText();
			}
		});
		
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
		if(e.getSource() == sendButton){
			outgoingText();
		}	
	}

	public void incommingText(String string) {
		textField.append(string);
	}

	public void outgoingText() {
		String input = inputField.getText();
		inputField.setText("");
		ClientOutThread.out.println(input);
	}
}