import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Login extends JFrame {

	/**
	 * This is the log in screen, that checks if it is a valid log in and 
	 */
	private static final long serialVersionUID = 1L;
	static boolean isManager;

	public Login() {
		JPanel loginPanel = new JPanel();
		JTextField usernameTextField = new JTextField(20);
		JPasswordField passwordTextField = new JPasswordField(20);
		JButton loginButton = new JButton("login");
		
		loginButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Database db= new Database();
				try {
					String passString = new String(passwordTextField.getPassword());
					if (db.validateLogin(usernameTextField.getText(), passString)){
						isManager = db.isManager(usernameTextField.getText());
						Viewer viewer = new Viewer(isManager,usernameTextField.getText());

						viewer.setVisible(true);
						viewer.setSize(500,500);
						dispose();
					}
					else{
						
						JOptionPane.showMessageDialog(null, "The login info you entered was incorrect");
						usernameTextField.setText("");
						passwordTextField.setText("");
						usernameTextField.grabFocus();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		
		
		
		
		loginPanel.add(usernameTextField);
		loginPanel.add(passwordTextField);
		loginPanel.add(loginButton);
		add(loginPanel);
		setSize(1280, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
	}
}
