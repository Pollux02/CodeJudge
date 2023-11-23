import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;



public class VentanaPrincipal
{
	public static void main(String[] args) 
	{		
		VentanaPrin evaluacionCodigo = new VentanaPrin();
		evaluacionCodigo.setLocationRelativeTo(null);
		evaluacionCodigo.setVisible(true);
	}
}

class VentanaPrin extends JFrame
{
	public static final long serialVersionUID = 1;
	private final int PIXELES_ANCHO = 720, PIXELES_ALTO = 480;
	private final int CALIFICACION_MINIMA = 5, CALIFICACION_MAXIMA = 100, CALIFICACION_WARNINGS = 95;
	private final String LENGUAJEC = "C", LENGUAJECPP = "C++";
	
	private JButton seleccionarCarpeta, seleccionarArchivoPruebas;
	private JPanel panelButtons, panelCheckboxs;
	private JTextField campoRutaCarpeta;
	private JTextArea tablaAlumnos;
	private JCheckBox checkBoxC, checkBoxCPP;
	private JScrollPane scrollPane;
	private List<CodigoAlumno>codigosAlumnos = new ArrayList<>();
	private static String lenguajeSolicitado = "";

	public VentanaPrin()
	{
		super("CodeHunter");
		ManejadorVentana mv = new ManejadorVentana();
		
		panelButtons = new JPanel(new GridLayout(7,1));
		panelCheckboxs = new JPanel(new GridLayout(1,2));
		
		seleccionarCarpeta = new JButton("Seleccionar carpeta");
		seleccionarArchivoPruebas = new JButton("Seleccionar archivo de pruebas");
		
		campoRutaCarpeta = new JTextField();
		
		tablaAlumnos = new JTextArea("Nickname\t|\tCalificación\t|\tFaltas");
		scrollPane = new JScrollPane(tablaAlumnos);
		scrollPane.setPreferredSize(new Dimension(300, 150));
		
		checkBoxC = new JCheckBox("Lenguaje C");
		checkBoxCPP = new JCheckBox("Lenguaje C++");

		//PanelButtons
		panelButtons.add(new JLabel("Ingrese ruta de carpeta: "));
		panelButtons.add(campoRutaCarpeta);
		panelButtons.add(new JLabel(""));
		panelButtons.add(seleccionarCarpeta);
		panelButtons.add(new JLabel(""));
		panelButtons.add(seleccionarArchivoPruebas);
		panelButtons.add(new JLabel(""));

		panelCheckboxs.add(checkBoxC);
		panelCheckboxs.add(checkBoxCPP);
		
		//Frame
		add("North",panelButtons);
		add("Center", panelCheckboxs);
		add("South", scrollPane);
		setSize(PIXELES_ANCHO,PIXELES_ALTO);
		
		addWindowListener(mv);
		seleccionarCarpeta.addActionListener(new ManejadorBotones());
		seleccionarArchivoPruebas.addActionListener(new ManejadorBotones());
		seleccionarArchivoPruebas.setVisible(false);
	}
	
	private class ManejadorBotones implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String comando = e.getActionCommand();

			switch(comando)
			{
				//Si se presiona el botón "Seleccionar Carpeta".
				case "Seleccionar carpeta":
					if(campoRutaCarpeta.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "No se ingresó una ruta de carpeta", "Aviso", JOptionPane.WARNING_MESSAGE);
					}
					
					else
					{
						File carpetaCodigos = new File(campoRutaCarpeta.getText()); 
						File[] listaArchivos = carpetaCodigos.listFiles();
						
						codigosAlumnos.clear();
						seleccionarArchivoPruebas.setVisible(false);
						tablaAlumnos.setText("Nickname\t|\tCalificación\t|\tFaltas");
	
						if(checkBoxCPP.isSelected() && checkBoxC.isSelected())
						{
							JOptionPane.showMessageDialog(null, "Se seleccionó más de un lenguaje", "Aviso", JOptionPane.WARNING_MESSAGE);
						}
						
						else if(!checkBoxCPP.isSelected() && !checkBoxC.isSelected())
						{
							JOptionPane.showMessageDialog(null, "No se seleccionó ningún lenguaje", "Aviso", JOptionPane.WARNING_MESSAGE);
						}
						
						else if(checkBoxC.isSelected())
						{
							lenguajeSolicitado = LENGUAJEC;
							
							obtenerCodigos(listaArchivos);
							//Al terminar con todos los archivos, se hace visible el botón seleccionarArchivoPruebas.
							seleccionarArchivoPruebas.setVisible(true);
						}
						
						else if(checkBoxCPP.isSelected())
						{
							lenguajeSolicitado = LENGUAJECPP;
							
							obtenerCodigos(listaArchivos);
							//Al terminar con todos los archivos, se hace visible el botón seleccionarArchivoPruebas.
							seleccionarArchivoPruebas.setVisible(true);
						}
					}
					
					break;
					
				case "Seleccionar archivo de pruebas":			
					String textoArchivo = seleccionarArchivoPruebas();
					
					//Si el usuario seleccionó un archivo.
					if(textoArchivo != null)
					{
						evaluarCodigos(textoArchivo);
					}
					
					checkBoxC.setSelected(false);
					checkBoxCPP.setSelected(false);	
					
					lenguajeSolicitado = "";
					
					seleccionarArchivoPruebas.setVisible(false);
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
	
	
	private static String extraerNickName(String nombreArchivo) 
	{
        // Definir el patrón de la expresión regular
        Pattern patron = Pattern.compile("j\\d+pro\\d+b([a-zA-Z]+)");

        // Crear un objeto Matcher
        Matcher matcher = patron.matcher(nombreArchivo);

        // Verificar si hay coincidencias
        if (matcher.find()) 
        {
            // Devolver el grupo capturado (en este caso, el nick name)
            return matcher.group(1);
        } 
        else 
        {
            // En caso de no encontrar coincidencias
            return "No se encontró coincidencia";
        }
    }
	
	private void obtenerCodigos(File[] listaArchivos)
	{
		int i;
		
		String nombreArchivo, rutaCarpetaC;
		
		CodigoAlumno codigoAlumno = new CodigoAlumno();
		
		File carpetaAlumno;
		
		//Este ciclo recorre todos los archvos dentro de la carpetaCodigos.
		for(i = 0; i<listaArchivos.length; i++)
		{
			nombreArchivo = listaArchivos[i].getName();
			
			//En la variable nombreArchivo se guarda el nombre del zip eliminando la extensión del mismo.
			if (nombreArchivo.endsWith(".zip")) 
			{
				//Si el archivo termina con .zip, se comprueba si ya existe una carpeta con ese nombre.
	            nombreArchivo = nombreArchivo.replace(".zip", "");
	            
	            carpetaAlumno = new File(campoRutaCarpeta.getText()+"/"+nombreArchivo);
	            
	            if (System.getProperty("os.name").startsWith("Windows"))
	    		{
	            	carpetaAlumno = new File(campoRutaCarpeta.getText()+"\\"+nombreArchivo);
	    		}
				
	            //Si no existe la carpeta se crea y se extrae el zip en la carpeta creada.
				if(carpetaAlumno.exists() && carpetaAlumno.isDirectory())
				{
					System.out.println("La carpeta ya existe.");
				}
				else
				{
					//Se crea la carpeta con el nombre guardado en nombreArchivo.
					crearCarpeta(nombreArchivo);
					
					//Se descomprime el zip con el nombre obtenido anteriormente en la carpeta que se creó.
			        try 
			        {
			        	if (System.getProperty("os.name").startsWith("Windows"))
			    		{
			        		descomprimirZip(campoRutaCarpeta.getText()+"\\"+nombreArchivo+".zip", campoRutaCarpeta.getText()+"\\"+nombreArchivo);
			    		}
			        	else
			        	{
			        		descomprimirZip(campoRutaCarpeta.getText()+"/"+nombreArchivo+".zip", campoRutaCarpeta.getText()+"/"+nombreArchivo);
			        	}
					} 
			        catch (IOException e1) 
			        {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				//Se busca la carpeta "C" dentro de la carpeta donde se descomprimió el .zip.
				if (System.getProperty("os.name").startsWith("Windows"))
	    		{
					rutaCarpetaC = buscarCarpetaCodigo(new File(campoRutaCarpeta.getText()+"\\"+nombreArchivo));
	    		}
				else
				{
					rutaCarpetaC = buscarCarpetaCodigo(new File(campoRutaCarpeta.getText()+"/"+nombreArchivo));
				}
				
				//Si rutaCarpetaC no está vacía significa que se encontró la carpeta "C".
				if (rutaCarpetaC != null) 
				{
		            /*Se crea una instancia de la clase CodigoAlumno con su nickname el cual es el nombreArchivo pero se
		            eliminan los primeros 10 caracteres, también se guarda la ruta de la carpeta C y se le asigna una 
		            calificación de 100 (por el momento).*/
					nombreArchivo = extraerNickName(nombreArchivo);
		            codigoAlumno = new CodigoAlumno(nombreArchivo, rutaCarpetaC, CALIFICACION_MAXIMA, "");
		        } 
				
				else 
				{
		            /*Si no se encontró la carpeta, se crea una instancia de CodigoAlumno de la misma forma, sólo que 
					ahora la ruta de la carpeta C queda vacía y la calificación es 0.*/
					nombreArchivo = extraerNickName(nombreArchivo);
		            codigoAlumno = new CodigoAlumno(nombreArchivo, "", CALIFICACION_MINIMA, "Formato de entrega de evaluandos 4 (No se encontró la carpeta \"" + lenguajeSolicitado + "\").\n");
		        }
				
				//Se agrega la instancia creada a la lista de codigoAlumnos
				codigosAlumnos.add(codigoAlumno);
	        }
		}
	}
	
	private void evaluarCodigos(String textoArchivo)
	{
		int i, j;
		//Se dividen las lineas del archivo de pruebas, esto por medio del caracter "*".
	    String[] lineas = textoArchivo.split("\\*");
	    String compilacion;
	    
	    File archivoMain;

	    List<Prueba> pruebas = obtenerPruebas(lineas);
	    
	    //Se hace un recorrido por cada alumno
	    for(i = 0; i<codigosAlumnos.size(); i++)
	    {
	    	//Si el alumno tiene la carpeta "C"
	    	if(codigosAlumnos.get(i).getRutaCodigo() != "")
	    	{
	    		//Se evaluará que lenguaje seleccionó el usuario.
	    		switch(lenguajeSolicitado)
	    		{
	    			case LENGUAJEC:
			    		if (System.getProperty("os.name").startsWith("Windows"))
			    		{
			    			archivoMain = new File(codigosAlumnos.get(i).getRutaCodigo()+"\\main.c");
			    		}
			    		else
			    		{
			    			archivoMain = new File(codigosAlumnos.get(i).getRutaCodigo()+"/main.c");
			    		}
	    				break;
	    			case LENGUAJECPP:
			    		if (System.getProperty("os.name").startsWith("Windows"))
			    		{
			    			archivoMain = new File(codigosAlumnos.get(i).getRutaCodigo()+"\\main.cpp");
			    		}
			    		else
			    		{
			    			archivoMain = new File(codigosAlumnos.get(i).getRutaCodigo()+"/main.cpp");
			    		}
	    				break;
	    			default:
	    				archivoMain = null;
	    		}
	    		
	    		//Se verifica si existe un archivo llamado "main.c"
	    		if(archivoMain.exists())
	    		{
	    			switch(lenguajeSolicitado)
	    			{
	    				case LENGUAJEC:
	    					//Se trata de compilar el archivo "main.c" y su salida se guarda en "compilacion".
			    			if (System.getProperty("os.name").startsWith("Windows"))
				    		{
			    				compilacion = compilar(codigosAlumnos.get(i).getRutaCodigo()+"\\main.c");
				    		}
			    			else
			    			{
			    				compilacion = compilar(codigosAlumnos.get(i).getRutaCodigo()+"/main.c");
			    			}
	    					break;
	    				case LENGUAJECPP:
	    					//Se trata de compilar el archivo "main.cpp" y su salida se guarda en "compilacion".
			    			if (System.getProperty("os.name").startsWith("Windows"))
				    		{
			    				compilacion = compilar(codigosAlumnos.get(i).getRutaCodigo()+"\\main.cpp");
				    		}
			    			else
			    			{
			    				compilacion = compilar(codigosAlumnos.get(i).getRutaCodigo()+"/main.cpp");
			    			}
	    					break;
	    				default:
	    					compilacion = null;
	    			}
	    			
			    	
			    	//Si en la compilación se registra un error en la compilación
				    if(compilacion.contains("Error en la compilación"))
				    {
				    	//Se pondrá una calificación de 0 al alumno y se registrará el error.
				    	codigosAlumnos.get(i).setCalificacion(CALIFICACION_MINIMA);
		    			codigosAlumnos.get(i).setFaltas(codigosAlumnos.get(i).getFaltas()+"Requerimiento de valor agregado K (Error en la compilación).\n");
				    }
				    else
				    {
				    	//Verificación de warnings en la compilación
				    	if(compilacion.contains("-Wunused-variable"))//Variable no usada.
					    {
					    	codigosAlumnos.get(i).setFaltas(codigosAlumnos.get(i).getFaltas()+"Requerimiento de valor agregado B (Variable sin usar).\n");
					    	codigosAlumnos.get(i).setCalificacion(CALIFICACION_WARNINGS);
					    }
					    
					    if(compilacion.contains("-Wuninitialized"))//Variable no inicializada.
					    {
					    	codigosAlumnos.get(i).setFaltas(codigosAlumnos.get(i).getFaltas()+"Requerimiento de valor agregado K (Variable no inicializada).\n");
					    	if(codigosAlumnos.get(i).getCalificacion() > CALIFICACION_WARNINGS)
					    	{
					    		codigosAlumnos.get(i).setCalificacion(CALIFICACION_WARNINGS);
					    	}
					    }
					    
					    if(compilacion.contains("-Wfloat-equal"))//Trata de comparar flotantes.
					    {
					    	codigosAlumnos.get(i).setFaltas(codigosAlumnos.get(i).getFaltas()+"Requerimiento de valor agregado K (Trata de comparar flotantes).\n");
					    	if(codigosAlumnos.get(i).getCalificacion() > CALIFICACION_WARNINGS)
					    	{
					    		codigosAlumnos.get(i).setCalificacion(CALIFICACION_WARNINGS);
					    	}
					    }
					    
					    if(compilacion.contains("-Wswitch-default"))//Switch sin default.
					    {
					    	codigosAlumnos.get(i).setFaltas(codigosAlumnos.get(i).getFaltas()+"Requerimiento de valor agregado K (Switch sin default).\n");
					    	if(codigosAlumnos.get(i).getCalificacion() > CALIFICACION_WARNINGS)
					    	{
					    		codigosAlumnos.get(i).setCalificacion(CALIFICACION_WARNINGS);
					    	}
					    }
					    
					    if(compilacion.contains("-Wunreachable-code"))//Código inalcanzable.
					    {
					    	codigosAlumnos.get(i).setFaltas(codigosAlumnos.get(i).getFaltas()+"Requerimiento de valor agregado K (Código inalcanzable).\n");
					    	if(codigosAlumnos.get(i).getCalificacion() > CALIFICACION_WARNINGS)
					    	{
					    		codigosAlumnos.get(i).setCalificacion(CALIFICACION_WARNINGS);
					    	}
					    }
					    
					    if(!probarPrograma(pruebas))//Si la compilación resulta exitosa se debe crear un .exe en la carpeta raíz del juez.
		    			{
		    				codigosAlumnos.get(i).setCalificacion(CALIFICACION_MINIMA);
		    				codigosAlumnos.get(i).setFaltas(codigosAlumnos.get(i).getFaltas()+"El programa no cumple con lo requerido.\n");
		    			}
				    }	
	    		}
	    		
	    		//Si no existe un archivo llamado "main.c"
	    		else
	    		{
	    			codigosAlumnos.get(i).setCalificacion(CALIFICACION_MINIMA);
	    			codigosAlumnos.get(i).setFaltas(codigosAlumnos.get(i).getFaltas()+"Formato de entrega de evaluandos 3 (No se encontró \"main." + lenguajeSolicitado +"\").\n");
	    		}
	    	}
	    	
	    	//Si se detecta que el trabajo es una V2 o posterior (Es decir que en su nickname el penúltimo caracter es una V).:
	    	if(codigosAlumnos.get(i).getNickName().charAt(codigosAlumnos.get(i).getNickName().length()-2) == 'V')
			{
	    		//Se eliminan los 2 últimos caracteres del nickname.
	    		codigosAlumnos.get(i).setNickName(codigosAlumnos.get(i).getNickName().substring(0, codigosAlumnos.get(i).getNickName().length() - 2));
	    		
	    		//Se buscará en la lista codigosAlumnos un código con el mismo NickName que el código anterior.
	    		for(j = 0; j<codigosAlumnos.size(); j++)
	    		{
	    			//Si se encuentra y no es el mismo código que el anterior.
	    			if (codigosAlumnos.get(j).getNickName().equals(codigosAlumnos.get(i).getNickName()) && j != i) 
	    			{
	    				/*Se fijará en el código encontrado los valores del código con una versión posterior, aumentará 
	    				 *su número de versiones y se le restarán los puntos dependiendo de el número de versiones que tenga
	    				 */
	    				codigosAlumnos.get(j).setNumVersiones(codigosAlumnos.get(j).getNumVersiones()+1);
	                    codigosAlumnos.get(j).setCalificacion(codigosAlumnos.get(i).getCalificacion()-10*codigosAlumnos.get(j).getNumVersiones());
	                    codigosAlumnos.get(j).setFaltas(codigosAlumnos.get(i).getFaltas()+"Formato de entrega de evaluandos 11("+(codigosAlumnos.get(j).getNumVersiones()+1)+" versiones)");
	                    
	                    codigosAlumnos.remove(i);
	                    
	                    //Se reduce i para que el ciclo for continúe sin problemas.
	                    i--;
	                    break; 
	                }
	    		}
			}
	    }
	    
	    //Se escriben los datos de codigosAlumnos en la tabla.
	    for(i = 0; i<codigosAlumnos.size(); i++)
	    {
	    	tablaAlumnos.setText(tablaAlumnos.getText()+"\n"+codigosAlumnos.get(i).getNickName()+"\t|\t"+ 
	    			codigosAlumnos.get(i).getCalificacion()+"\t|\t"+codigosAlumnos.get(i).getFaltas());
	    }
	}
	
	//Función para compilar un archivo C por medio de la ruta del archivo.
	private String compilar(String rutaArchivo) 
	{
	    // Comando de compilación para Linux
	    String comandoCompilar;
	    
	    ProcessBuilder compileProcessBuilder;
	    Process compileProcess;
	    
	    switch(lenguajeSolicitado)
	    {
	    	case LENGUAJEC:
	    		comandoCompilar = "gcc -Wall -Wextra -Wswitch-default -Wfloat-equal -Wunreachable-code " + rutaArchivo + " -o program -lm";
	    		break;
	    	case LENGUAJECPP:
	    		comandoCompilar = "g++ -Wall -Wextra -Wswitch-default -Wfloat-equal -Wunreachable-code " + rutaArchivo + " -o program -lm";
	    		break;	
	    	default:
	    		comandoCompilar = "";
	    }
	    
	    try 
	    {
	        // Compilar el programa C
	        compileProcessBuilder = new ProcessBuilder("bash", "-c", comandoCompilar);
	        compileProcessBuilder.redirectErrorStream(true);
	        compileProcess = compileProcessBuilder.start();

	        String compileOutput = capturarSalida(compileProcess.getInputStream());

	        // Obtener el código de salida de compilación
	        int compileExitCode = compileProcess.waitFor();

	        if (compileExitCode == 0) 
	        {
	            return "Compilación exitosa.\n\nWarnings:\n" + compileOutput;
	        } 
	        else 
	        {
	            return "Error en la compilación:\n" + compileOutput;
	        }
	    } 
	    catch (IOException | InterruptedException e1) 
	    {
	        e1.printStackTrace();
	    }
	    return "";
	}
	
	//Función para ejecturar el programa usando las pruebas obtenidas.
	private String ejecutarC(Prueba prueba) 
	{
	    // Utilizar un StringBuilder para acumular la salida
	    StringBuilder salidaBuilder = new StringBuilder();
	    ProcessBuilder processBuilder;
	    Process procesoC;
	    
	    InputStream inputStream;
        OutputStream outputStream;
        
        BufferedReader reader;
        PrintWriter writer;
        
        Thread inputThread, outputThread;

	    try 
	    {
	        processBuilder = new ProcessBuilder("./program");
	        processBuilder.redirectErrorStream(true);

	        procesoC = processBuilder.start();
	        inputStream = procesoC.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(inputStream));
	        outputStream = procesoC.getOutputStream();
	        writer = new PrintWriter(outputStream);

	        inputThread = new Thread(() -> {
	            for (String entrada : prueba.getEntradas()) 
	            {
	                writer.write(entrada + "\n");
	                writer.flush();
	            }
	            writer.close();
	        });

	        outputThread = new Thread(() -> {
	            try 
	            {
	                String linea;
	                while ((linea = reader.readLine()) != null) 
	                {
	                    // Acumular la salida utilizando StringBuilder
	                    salidaBuilder.append(linea).append("\n");
	                }
	                reader.close();
	                System.out.println(salidaBuilder.toString());
	            } 
	            
	            catch (IOException e) 
	            {
	                e.printStackTrace();
	            }
	        });

	        inputThread.start();
	        outputThread.start();

	        inputThread.join();
	        outputThread.join();

	        int exitCode = procesoC.waitFor();
	        System.out.println("El programa en C ha terminado con código de salida: " + exitCode);
	    } 
	    catch (IOException | InterruptedException e) 
	    {
	        e.printStackTrace();
	    }

	    return salidaBuilder.toString();
	}
	
	private String capturarSalida(InputStream inputStream) throws IOException 
	{
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder output = new StringBuilder();
        
        String line;
        
        while ((line = reader.readLine()) != null) 
        {
            output.append(line).append("\n");
        }
        return output.toString();
    }
	
	private boolean probarPrograma(List<Prueba> pruebas)
	{
		int i = 0, j = 0, z = 0, indiceSalida = 0;
		
		boolean encontrado;
		
		String salida = "";
		
		List<String> palabras = new ArrayList<>();
		
		DecimalFormat df = new DecimalFormat("#." + "0".repeat(2));
		double valorRedondeado;
		
		Pattern pattern;
        Matcher matcher;
		
		//Se trabajará con todas las pruebas.
		for(i = 0; i<pruebas.size(); i++)
		{
			palabras.clear();
			
			//Se ejecutará el archivo C con las entradas de la prueba y la salida se guardará en "salida"
			salida = ejecutarC(pruebas.get(i));
			
			//Se crea una expresión regular que consiste en separar palabras y números.
			pattern = Pattern.compile("[a-zA-Z]+|-?\\d+(\\.\\d+)?|=");
	        matcher = pattern.matcher(salida);
	        
	        //Toda cadena que cumpla con la expresión se guardará en "palabras".
	        while (matcher.find()) 
	        {
	            palabras.add(matcher.group());
	        }
	        
	        //Si la prueba requiere la búsqueda de orden.
			if(pruebas.get(i).getBuscaOrden())
			{
				//Se trabajará con las salidas esperadas de la prueba.
				for(j = 0; j<pruebas.get(i).getSalidas().size(); j++)
				{
					encontrado = false;
					
					//Se recorrerá la lista de palabras.
					for(z = indiceSalida; z<palabras.size(); z++)
					{
						//Si en la salida se detecta "\d" significa que se espera cualquier salida numérica.
						if(pruebas.get(i).getSalidas().get(j).equals("\\d"))
						{
							//Si la palabra en indice z es un número.
							if (palabras.get(z).matches(".*\\d+.*")) 
							{
								/*El indiceSalida indica de donde partirá z en el ciclo for para que no inicie
								*de 0, esto es así porque se espera que las salidas restantes estén después de la
								*salida evaluada.
								*/
								indiceSalida = z+1;
								//Encontrado pasará a ser verdadero.
								encontrado = true;
								break;
							}
						}
						
						if(esNumeroFlotante(pruebas.get(i).getSalidas().get(j)) && esNumeroFlotante(palabras.get(z)))
						{
							if (palabras.get(z).contains(pruebas.get(i).getSalidas().get(j))) 
							{
								indiceSalida = z+1;
								//Encontrado pasará a ser verdadero.
								encontrado = true;
								break;
							}
						}
						
						else
						{
							//Si la palabra en z concuerda con la salida
							if(palabras.get(z).equals(pruebas.get(i).getSalidas().get(j)))
							{
								/*El indiceSalida indica de donde partirá z en el ciclo for para que no inicie
								*de 0, esto es así porque se espera que las salidas restantes estén después de la
								*salida evaluada.
								*/
								indiceSalida = z+1;
								//Encontrado pasará a ser verdadero.
								encontrado = true;
								break;
							}
						}
					}
					
					/*Si "encontrado" se quedó en falso significa que una de las salidas esperadas no
					 *se encontró en la lista "palabras" por ende el programa está mal y la función retornará
					 *falso.
					 */
					if(!encontrado)
					{
						return false;
					}
				}
			}
			
			/*Si la prueba no busca orden trabaja muy similar, sólo no se asigna el "indiceSalida", en
			 *lugar de eso se elimina la palabra de la lista y se vuelve a recorrer desde el principio.
			 *Se elimina la palabra para que no se vuelva a tomar en caso de que en las salidas de la prueba
			 *existan valores repetidos.
			 */
			else
			{
				for(j = 0; j<pruebas.get(i).getSalidas().size(); j++)
				{
					encontrado = false;
					
					for(z = 0; z<palabras.size(); z++)
					{
						if(pruebas.get(i).getSalidas().get(j).equals("\\d"))
						{
							if (palabras.get(z).matches(".*\\d+.*")) 
							{
								palabras.remove(z);
								encontrado = true;
								break;
							}
						}
						
						if(esNumeroFlotante(pruebas.get(i).getSalidas().get(j)) && esNumeroFlotante(palabras.get(z)))
						{
							
							valorRedondeado = Double.parseDouble(df.format(Float.parseFloat(palabras.get(z))));
							
							palabras.set(z, Double.toString(valorRedondeado));
							if (palabras.get(z).contains(pruebas.get(i).getSalidas().get(j))) 
							{
								palabras.remove(z);
								encontrado = true;
								break;
							}
						}
						
						else
						{
							if(palabras.get(z).equals(pruebas.get(i).getSalidas().get(j)))
							{
								palabras.remove(z);
								encontrado = true;
								break;
							}
						}
					}
					
					if(!encontrado)
					{
						return false;
					}
				}
			}
		}

	    return true;
	}
	
	public static boolean esNumeroFlotante(String str) 
	{
        String regex = "\\d+\\.\\d+";
        return Pattern.matches(regex, str);
    }
	
	//Función para la obtención de pruebas por medio de una lista de lineas.
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
	
	//Función para la creación de carpetas.
	private void crearCarpeta(String nombreArchivo)
	{
		File carpetaAlumno = new File(campoRutaCarpeta.getText()+"/"+ nombreArchivo);
		
		if (System.getProperty("os.name").startsWith("Windows"))
		{
			carpetaAlumno = new File(campoRutaCarpeta.getText()+"\\"+ nombreArchivo);
		}
		
		if (!carpetaAlumno.exists()) 
		{
			carpetaAlumno.mkdirs();
        }
	}
	
	//Función para la búsqueda de la carpeta "C".
	private static String buscarCarpetaCodigo(File directorio) 
	{
		String rutaEncontrada;
		
		File[] archivos;
        // Se verifiva si el directorio existe.
        if (!directorio.exists()) 
        {
            return null;
        }

        //Obtenemos la lista de archivos y directorios en el directorio actual.
        archivos = directorio.listFiles();

        if (archivos != null) 
        {
            //Se recorrerán todos los archivos y directorios en el directorio actual.
            for (File archivo : archivos) 
            {
                if (archivo.isDirectory()) 
                {	
                    //Si encontramos una carpeta con el nombre "C", retornamos su ruta.
                    if (archivo.getName().equalsIgnoreCase(lenguajeSolicitado)) 
                    {
                        return archivo.getAbsolutePath();
                    } 
                    else 
                    {
                        //Si no es la carpeta que estamos buscando, recursivamente buscamos en esta carpeta.
                        rutaEncontrada = buscarCarpetaCodigo(archivo);
                        
                        if (rutaEncontrada != null) 
                        {
                            return rutaEncontrada;
                        }
                    }
                }
            }
        }
        // Si no se encontró la carpeta "C" en este directorio o en sus subdirectorios, retornamos null
        return null;
    }
	
	//Función para descomprimir un archivo zip por medio de la ruta del zip y la ruta donde se descomprimirá su contenido.
	private static void descomprimirZip(String archivoOrigen, String directorioDestino) throws IOException 
	{
		int codigoSalida;
		
		ProcessBuilder processBuilder;
		Process proceso;
		
		//Si se detecta que el sistema operativo es windows.
		if (System.getProperty("os.name").startsWith("Windows"))
		{
	        // Crear un proceso para ejecutar el comando tar
	        processBuilder = new ProcessBuilder("tar", "-xf", archivoOrigen, "-C", directorioDestino);
	
	        // Redirigir la salida estándar y de error del proceso a la consola
	        processBuilder.inheritIO();
	
	        // Ejecutar el proceso
	        proceso = processBuilder.start();
	
	        try 
	        {
	            // Esperar a que el proceso termine
	            codigoSalida = proceso.waitFor();
	
	            // Verificar si el comando se ejecutó correctamente (código de salida 0)
	            if (codigoSalida == 0) 
	            {
	                System.out.println("Extracción exitosa.");
	            } 
	        } 
	        catch (InterruptedException e) 
	        {
	            Thread.currentThread().interrupt();
	            System.err.println("El proceso fue interrumpido.");
	        }
		}
		
		//Si se detecta otro sistema operativo (Linux).
		else
		{
			// Crear un proceso para ejecutar el comando unzip
	        processBuilder = new ProcessBuilder("unzip", archivoOrigen, "-d", directorioDestino);

	        // Redirigir la salida estándar y de error del proceso a la consola
	        processBuilder.inheritIO();

	        // Ejecutar el proceso
	        proceso = processBuilder.start();

	        try 
	        {
	            // Esperar a que el proceso termine
	            codigoSalida = proceso.waitFor();

	            // Verificar si el comando se ejecutó correctamente (código de salida 0)
	            if (codigoSalida == 0) 
	            {
	                System.out.println("Descompresión exitosa.");
	            } 
	        } 
	        catch (InterruptedException e) 
	        {
	            Thread.currentThread().interrupt();
	            System.err.println("El proceso fue interrumpido.");
	        }
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
}
