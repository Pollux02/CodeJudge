import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow {
	public static void main(String[] args) {
		MainWin mainWindow = new MainWin();
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
	}

}

class MainWin extends JFrame{
	public static final long serialVersionUID = 1;
	private final int PIXELS_WIDTH = 720, PIXELS_LENGTH= 480;
	
	private final String EMPTY_STRING = "",
						 BUTTON_REVIEW_CODES_TEXT = "Revisión de códigos",
						 BUTTON_GENERATE_TEST_FILE_TEXT = "Generar archivo de pruebas"; 
	
	private JPanel panelMenu, panelMain;
	private JButton buttonReviewCodes, buttonGenerateTestFile;
	
	public MainWin() {
		super("CodeHunter");
		WindowController mv = new WindowController();
		
		panelMenu = new JPanel(new GridLayout(9, 1));
		panelMain = new JPanel(new GridLayout(1, 1));
		
		buttonReviewCodes = new JButton(BUTTON_REVIEW_CODES_TEXT);
		buttonReviewCodes.setBackground(new Color(0, 39, 45));
		buttonReviewCodes.setForeground(Color.WHITE);
		
		buttonGenerateTestFile = new JButton(BUTTON_GENERATE_TEST_FILE_TEXT );
		buttonGenerateTestFile.setBackground(new Color(0, 39, 45));
		buttonGenerateTestFile.setForeground(Color.WHITE);
		
		//PANEL MENU
		panelMenu.add(new JLabel("LOGO CODEHUNTER"));
		panelMenu.add(new JLabel(EMPTY_STRING));
		panelMenu.add(new JLabel(EMPTY_STRING));
		panelMenu.add(buttonReviewCodes);
		panelMenu.add(new JLabel(EMPTY_STRING));
		panelMenu.add(buttonGenerateTestFile);
		panelMenu.add(new JLabel(EMPTY_STRING));
		panelMenu.add(new JLabel(EMPTY_STRING));
		panelMenu.add(new JLabel(EMPTY_STRING));
		
		panelMenu.setBackground(new Color(0, 39, 45));
		
		//PANEL MAIN
		panelMain.setBackground(new Color(0, 7, 6));
		
		add("West", panelMenu);
		add("Center", panelMain);
		setSize(PIXELS_WIDTH, PIXELS_LENGTH);
		addWindowListener(mv);
		
		buttonReviewCodes.addActionListener(new ButtonsController());
	}
	
	private class ButtonsController implements ActionListener {
		
		public void actionPerformed(ActionEvent e)
		{
			String comando = e.getActionCommand();

			switch(comando)
			{
			case BUTTON_REVIEW_CODES_TEXT:
					loadSecondaryPanel(new ReviewCodesPanel());
				break;
				
			case BUTTON_GENERATE_TEST_FILE_TEXT :			
				break;
			}
		}
	}
	
	private void loadSecondaryPanel(JPanel secondaryPanel) {
		panelMain.removeAll();
        panelMain.add(secondaryPanel);
        panelMain.revalidate();
        panelMain.repaint();
	}
	public class WindowController implements WindowListener {
		
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}

		public void windowActivated(WindowEvent e)
		{
		}

		public void windowDeactivated(WindowEvent e)
		{
		}

		public void windowClosed(WindowEvent e)
		{
		}

		public void windowDeiconified(WindowEvent e)
		{
		}

		public void windowIconified(WindowEvent e)
		{
		}

		public void windowOpened(WindowEvent e)
		{
		}
	}
}
