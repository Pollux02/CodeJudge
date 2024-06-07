import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridLayout;

public class ReviewCodesPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private String EMPTY_STRING = "",
				   FOLDER_PATH_TEXT = "Ingrese ruta de la carpeta con los códigos:";
	
	private JTextField textFieldFolderPath;

	private JLabel labelEmpty, labelFolderPath; 
	public ReviewCodesPanel() {
		
		textFieldFolderPath = new JTextField();
		
		labelEmpty = new JLabel(EMPTY_STRING);
		labelEmpty.setForeground(Color.WHITE);
		
		labelFolderPath = new JLabel(FOLDER_PATH_TEXT);
		labelFolderPath.setForeground(Color.WHITE);
		
		setLayout(new GridLayout(9, 1)); 
		setBackground(new Color(0, 7, 6));
		setBorder(new EmptyBorder(20, 20, 20, 20));
        
        add(labelFolderPath);
        add(textFieldFolderPath);
        add(labelEmpty);
	}
}
