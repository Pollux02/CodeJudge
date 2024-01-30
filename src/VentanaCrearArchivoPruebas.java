import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class VentanaCrearArchivoPruebas 
{
	public static void main(String[] args) 
	{		
		abrirVentanaCrearArchivo();
	}
	
	public static void abrirVentanaCrearArchivo() 
	{
        VentanaCrearArchivo ventanaCrearArchivo = new VentanaCrearArchivo();
        ventanaCrearArchivo.setLocationRelativeTo(null);
        ventanaCrearArchivo.setVisible(true);
    }
}

class VentanaCrearArchivo extends JFrame
{
	public static final long serialVersionUID = 1;
	private final int PIXELES_ANCHO = 720, PIXELES_ALTO = 480;
	
	private JButton cargarArchivoPruebas, nuevoArchivoPruebas, guardarArchivoPruebas, nuevaPrueba;
	private JTextField campoEntradas, campoSalidas;
	private JPanel panelEntradasSalidas, panelButtons;
	private JTextArea tablaEntradas, tablaSalidas;
	
	private Prueba pruebaActual = null;
	
	private List<Prueba> pruebasActuales = new ArrayList<>();
	
	public VentanaCrearArchivo()
	{
		super("Crear archivo de pruebas");
		ManejadorVentana mv = new ManejadorVentana();
		
		panelEntradasSalidas = new JPanel(new GridLayout(1,3));
		panelButtons = new JPanel(new GridLayout(4,3));
		
		cargarArchivoPruebas = new JButton("Cargar archivo de pruebas");
		nuevoArchivoPruebas = new JButton("Nuevo archivo de pruebas");
		guardarArchivoPruebas = new JButton("Guardar archivo de pruebas");
		nuevaPrueba = new JButton("Nueva prueba");
		
		campoEntradas = new JTextField();
		campoSalidas = new JTextField();
		
		tablaEntradas = new JTextArea("Num Prueba\t|\tEntrada");
		tablaSalidas = new JTextArea("Num Prueba\t|\tSalida");
		
		panelEntradasSalidas.add(tablaEntradas);
		panelEntradasSalidas.add(new JLabel(""));
		panelEntradasSalidas.add(tablaSalidas);
		
		panelButtons.add(new JLabel("Entradas:"));
		panelButtons.add(new JLabel(""));
		panelButtons.add(new JLabel("Salidas:"));
		panelButtons.add(campoEntradas);
		panelButtons.add(new JLabel(""));
		panelButtons.add(campoSalidas);
		panelButtons.add(new JLabel(""));
		panelButtons.add(nuevaPrueba);
		panelButtons.add(new JLabel(""));
		panelButtons.add(guardarArchivoPruebas);
		panelButtons.add(cargarArchivoPruebas);
		panelButtons.add(nuevoArchivoPruebas);
		add("Center",panelEntradasSalidas);
		add("South", panelButtons);
		setSize(PIXELES_ANCHO,PIXELES_ALTO);
		
		addWindowListener(mv);
		cargarArchivoPruebas.addActionListener(new ManejadorBotones());
		guardarArchivoPruebas.addActionListener(new ManejadorBotones());
		nuevoArchivoPruebas.addActionListener(new ManejadorBotones());
		nuevaPrueba.addActionListener(new ManejadorBotones());
		
		campoEntradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Este código se ejecutará cuando se presione Enter en el JTextField
                String textoIngresadoEntradas = campoEntradas.getText();

                String[] lineas = textoIngresadoEntradas.split("\\*");
                
                
                if(pruebaActual == null)
                {
                	pruebaActual = new Prueba(pruebasActuales.size());
                }
                
                for(String linea : lineas)
                {
                	pruebaActual.getEntradas().add(linea);
                	tablaEntradas.setText(tablaEntradas.getText()+"\n"+pruebaActual.getId()+"\t|\t"+linea);
                }
                
                campoEntradas.setText("");
                
            }
        });
		
		campoSalidas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Este código se ejecutará cuando se presione Enter en el JTextField
                String textoIngresadoSalidas = campoSalidas.getText();

                String[] lineas = textoIngresadoSalidas.split("\\*");
                
                
                if(pruebaActual == null)
                {
                	pruebaActual = new Prueba(pruebasActuales.size());
                }
                
                for(String linea : lineas)
                {
                	pruebaActual.getSalidas().add(linea);
                	tablaSalidas.setText(tablaSalidas.getText()+"\n"+pruebaActual.getId()+"\t|\t"+linea);
                }
                
                campoSalidas.setText("");
                
            }
        });
		
	}
	
	private class ManejadorBotones implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String comando = e.getActionCommand();

			switch(comando)
			{
				case "Cargar archivo de pruebas":
					String textoArchivo = seleccionarArchivoPruebas();
					
					String[] lineas = textoArchivo.split("\\*");

				    pruebasActuales = obtenerPruebas(lineas);
				    
				    for(Prueba prueba : pruebasActuales)
				    {
				    	for(String entrada:prueba.getEntradas())
				    	{
				    		tablaEntradas.setText(tablaEntradas.getText()+"\n"+prueba.getId()+"\t|\t"+entrada);
				    	}
				    	
				    	for(String salida:prueba.getSalidas())
				    	{
				    		tablaSalidas.setText(tablaSalidas.getText()+"\n"+prueba.getId()+"\t|\t"+salida);
				    	}
				    }
				    break;
				    
				case "Nueva prueba":			
					pruebasActuales.add(pruebaActual);
					pruebaActual = null;
					break;
					
				case "Nuevo archivo de pruebas":			
					pruebaActual = null;
					pruebasActuales = new ArrayList<>();
					campoEntradas.setText("");
					campoEntradas.setText("");
					tablaEntradas.setText("Num Prueba\t|\tEntrada");
					tablaSalidas.setText("Num Prueba\t|\tSalida");
					break;
					
				case "Guardar archivo de pruebas":	
					pruebasActuales.add(pruebaActual);
					String contenido = "";
					
					for(Prueba prueba : pruebasActuales)
					{
						for(String entrada : prueba.getEntradas())
						{
							contenido = contenido+entrada+"\n";
						}
						
						contenido = contenido+"="+"\n";
						
						for(String salida : prueba.getSalidas())
						{
							contenido = contenido+salida+"\n";
						}
						contenido = contenido+"*\n";
					}
					JFileChooser fileChooser = new JFileChooser();
					
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de prueba CodeHunter (*.pch)", "pch");
	                fileChooser.setFileFilter(filter);
	                
	                int seleccion = fileChooser.showSaveDialog(null);
	                
	                if (seleccion == JFileChooser.APPROVE_OPTION) {
	                    try {
	                        // Obtener la ruta seleccionada por el usuario
	                        String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
	                        
	                     // Verificar si la extensión está presente, agregarla si es necesario
	                        if (!rutaArchivo.toLowerCase().endsWith(".pch")) {
	                            rutaArchivo += ".pch";
	                        }

	                        // Crear un BufferedWriter para escribir en el archivo
	                        BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));

	                        // Escribir el contenido en el archivo
	                        writer.write(contenido);

	                        // Cerrar el BufferedWriter
	                        writer.close();

	                    } catch (IOException ex) {
	                        ex.printStackTrace();
	                    }
	                }
	                
	                pruebaActual = null;
					pruebasActuales = new ArrayList<>();
					campoEntradas.setText("");
					campoEntradas.setText("");
					tablaEntradas.setText("Num Prueba\t|\tEntrada");
					tablaSalidas.setText("Num Prueba\t|\tSalida");
					break;
			}
		}
	}
	
	public class ManejadorVentana implements WindowListener
	{
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
	
	//Función que abre un FileDialog para seleccionar un archivo de pruebas.
	public static String seleccionarArchivoPruebas() 
	{
		String rutaCompleta, linea;
		
        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        
        BufferedReader br;
        StringBuilder contenido;
        
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) 
        {
            try 
            {
                // Construir la ruta completa del archivo seleccionado
                rutaCompleta = fileChooser.getSelectedFile().getAbsolutePath();;
                
                // Leer el contenido del archivo en una variable
                br = new BufferedReader(new FileReader(rutaCompleta));
                contenido = new StringBuilder();
                
                while ((linea = br.readLine()) != null) 
                {
                    contenido.append(linea).append("\n");
                }
                br.close();
                
                // Devolver el contenido del archivo como una cadena
                return contenido.toString();
            } 
            
            catch (IOException e) 
            {
                e.printStackTrace();
                return null;
            }
        } 
        
        else 
        {
            // El usuario canceló la selección del archivo
            return null;
        }
    }
	
	private List<Prueba> obtenerPruebas(String[] lineas)
	{
		boolean esSalida = false;
		
	    int idPrueba=0;
	    
	    String[]lineasPrueba;
	    
	    Prueba prueba = new Prueba(0);
	    
	    List<String> entradas = new ArrayList<String>();
	    List<String> salidas = new ArrayList<String>();
	    List<Prueba> pruebas = new ArrayList<Prueba>();
	    
	    //Se recorren todas la lineas del archivo prueba ya separadas por el caracter "*".
		for (String linea : lineas) 
			{
				//Se vuelven a separar las lineas por medio del caracter de salto de linea.
		    	lineasPrueba = linea.split("\n");
		    	prueba = new Prueba(idPrueba);
		    	esSalida = false;
		    	entradas = new ArrayList<String>();
			    salidas = new ArrayList<String>();
		    	
			    //Se recorre cada linea.
		    	for (String lineaPrueba : lineasPrueba) 
				{
		    		/*Si la linea es igual a "=" significa que el archivo está señalando las salidas
		    		*esperadas del programa.
		    		*/
		    		if(lineaPrueba.equals("="))
		    		{
		    			esSalida = true;
		    		}
		    		
		    		/*Si la linea es igual a "=" significa que el archivo está señalando las salidas
		    		*esperadas del programa y además se espera que estén en orden.
		    		*/
		    		else if(lineaPrueba.equals("=O"))
		    		{
		    			esSalida = true;
		    			prueba.setBuscaOrden(true);
		    		}
		    		
		    		/*De lo contrario se están leyendo datos, sólo se verifica si lo que se está leyendo
		    		 *son salidas o entradas.
		    		 */
		    		else
		    		{
		    			if(!lineaPrueba.equals(""))
		    			{
			    			if(esSalida)
			    			{
			    				if(lineaPrueba.equals("\\="))
			    				{
			    					salidas.add("=");
			    				}
			    				else
			    				{
			    					salidas.add(lineaPrueba);
			    				}
			    			}
			    			else
			    			{
			    				if(lineaPrueba.equals("\\="))
			    				{
			    					entradas.add("=");
			    				}
			    				else
			    				{
			    					entradas.add(lineaPrueba);
			    				}
			    			}
		    			}
		    		}
		        }
		    	
		    	//Si hay entradas o salidas en la prueba, esta se guarda en la lista de pruebas.
		    	if(entradas.size()>0 || salidas.size()>0)
		    	{
			    	prueba.setEntradas(entradas);
					prueba.setSalidas(salidas);
					pruebas.add(prueba);
					
					idPrueba++;
		    	}
		    	
	        }
		
		return pruebas;
	}
}
