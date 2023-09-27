import java.awt.Frame;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Panel;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.awt.FileDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EvaluacionCodigo 
{
	public static void main(String[] args) 
	{		
		EvaluacionCod evaluacionCodigo = new EvaluacionCod();
		evaluacionCodigo.setVisible(true);
	}
}

class EvaluacionCod extends Frame
{
	public static final long serialVersionUID = 1;
	private final int PIXELES_ANCHO = 720, PIXELES_ALTO = 480;
	private Button seleccionarCarpeta, seleccionarArchivoPruebas;
	private Panel panelButtons;
	private TextField campoRutaCarpeta;
	private List<CodigoAlumno>codigosAlumnos = new ArrayList<>();

	public EvaluacionCod()
	{
		super("Evaluación de Código");
		ManejadorVentana mv = new ManejadorVentana();
		panelButtons = new Panel(new GridLayout(6,1));
		seleccionarCarpeta = new Button("Seleccionar carpeta");
		seleccionarArchivoPruebas = new Button("Seleccionar archivo de pruebas");
		campoRutaCarpeta = new TextField();
		
		//PanelButtons
		panelButtons.add(new Label("Ingrese ruta de carpeta: "));
		panelButtons.add(campoRutaCarpeta);
		panelButtons.add(new Label(""));
		panelButtons.add(seleccionarCarpeta);
		panelButtons.add(new Label(""));
		panelButtons.add(seleccionarArchivoPruebas);
		//Frame
		add("Center",panelButtons);
		setSize(PIXELES_ANCHO,PIXELES_ALTO);
		
		addWindowListener(mv);
		seleccionarCarpeta.addActionListener(new ManejadorBotones());
		seleccionarArchivoPruebas.addActionListener(new ManejadorBotones());
		seleccionarArchivoPruebas.setVisible(false);
	}
	
	//Función para compilar un archivo C por medio de la ruta del archivo.
	private String compilarC(String rutaArchivo)
	{
		//Evaluación de compilación
	    String comandoCompilar = "gcc -Wall -Wextra -Wswitch-default -Wfloat-equal -Wunreachable-code " + rutaArchivo + " -o program"; // Comando de compilación
		
        try 
        {
            // Compilar el programa C
            ProcessBuilder compileProcessBuilder = new ProcessBuilder(comandoCompilar.split("\\s+"));
            compileProcessBuilder.redirectErrorStream(true);
            Process compileProcess = compileProcessBuilder.start();

            String compileOutput = capturarSalida(compileProcess.getInputStream());
            
            // Obtener el código de salida de compilación
            int compileExitCode = compileProcess.waitFor(); 

            if (compileExitCode == 0) 
            {   
                return("Compilación exitosa.\n\nWarnings:\n"+compileOutput);
            } 
            
            else 
            {
                return("Error en la compilación:\n" + compileOutput);
            }
        } 
        
        catch (IOException | InterruptedException e1) 
        {
            e1.printStackTrace();
        }
		return ("");
	}
	
	//Función para ejecturar el programa usando las pruebas obtenidas.
	private String ejecutarC(Prueba prueba)
	{
		String linea;
		String salida = "";
		int i = 0;
		
		try 
		{
            // Crear un proceso para ejecutar el programa en C
            ProcessBuilder processBuilder = new ProcessBuilder("./program");
            processBuilder.redirectErrorStream(true); // Redirigir stderr a stdout

            // Iniciar el proceso
            Process procesoC = processBuilder.start();

            // Obtener el flujo de salida estándar del proceso (lo que el programa C imprimirá)
            InputStream inputStream = procesoC.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Obtener el flujo de entrada estándar del proceso (lo que el programa C espera para el scanf)
            OutputStream outputStream = procesoC.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);

            // Escritura de las entradas
            
            for(i = 0; i<prueba.getEntradas().size(); i++)
            {
            	writer.write(prueba.getEntradas().get(i)+"\n");
            }
            writer.flush();

            // Leer y mostrar la salida del programa en C
            while ((linea = reader.readLine()) != null) {
                salida = salida + linea;
            }

            // Esperar a que el proceso en C termine
            int exitCode = procesoC.waitFor();
            System.out.println("El programa en C ha terminado con código de salida: " + exitCode);
        } 
		
		catch (IOException | InterruptedException e) 
		{
            e.printStackTrace();
        }
		
		return salida;
	}
	
	private static String capturarSalida(InputStream inputStream) throws IOException {
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
		int i = 0, j = 0, indiceSalida = 0;
		String salida = "";
		
		for(i = 0; i<pruebas.size(); i++)
		{
			salida = ejecutarC(pruebas.get(i));
			System.out.println(salida);
			
			if(pruebas.get(i).getBuscaOrden())
			{
				for(j = 0; j<pruebas.get(i).getSalidas().size(); j++)
				{
					indiceSalida = salida.indexOf(pruebas.get(i).getSalidas().get(j), indiceSalida);

					if(indiceSalida == -1)
					{
						return false;
					}
				}
			}
			else
			{
				for(j = 0; j<pruebas.get(i).getSalidas().size(); j++)
				{
					if(pruebas.get(i).getSalidas().get(j).equals("\\d"))
					{
						if (!salida.matches(".*\\d+.*")) 
						{
							return false;
						}
					}
					else
					{
						if(!salida.contains(pruebas.get(i).getSalidas().get(j)))
					    {
					    	return false;
					    }
					}
				}
			}
		}

	    return true;
	}
	
	//Función para la obtención de pruebas por medio de una lista de lineas.
	private List<Prueba> obtenerPruebas(String[] lineas)
	{
		boolean esSalida = false;
	    int idPrueba=0;
	    Prueba prueba = new Prueba(0);
	    List<String> entradas = new ArrayList<String>();
	    List<String> salidas = new ArrayList<String>();
	    List<Prueba> pruebas = new ArrayList<Prueba>();
	    
	    //Se recorren todas la lineas del archivo prueba ya separadas por el caracter "*".
		for (String linea : lineas) 
			{
				//Se vuelven a separar las lineas por medio del caracter de salto de linea.
		    	String[]lineasPrueba = linea.split("\n");
		    	prueba = new Prueba(idPrueba);
		    	esSalida = false;
		    	entradas = new ArrayList<String>();
			    salidas = new ArrayList<String>();
		    	
			    //Se recorre cada linea.
		    	for (String lineaPrueba : lineasPrueba) 
				{
		    		/*Si la linea es igual a "=" significa que el archivo está señalando las salidas
		    		esperadas del programa.*/
		    		if(lineaPrueba.equals("="))
		    		{
		    			esSalida = true;
		    		}
		    		
		    		/*Si la linea es igual a "=" significa que el archivo está señalando las salidas
		    		esperadas del programa y además se espera que estén en orden.*/
		    		else if(lineaPrueba.equals("=O"))
		    		{
		    			esSalida = true;
		    			prueba.setBuscaOrden(true);
		    		}
		    		
		    		/*De lo contrario se están leyendo datos, sólo se verifica si lo que se está leyendo
		    		 son salidas o entradas.*/
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
		File carpetaAlumno = new File(campoRutaCarpeta.getText()+"\\"+ nombreArchivo);
		
		if (!carpetaAlumno.exists()) 
		{
            if (carpetaAlumno.mkdirs()) 
            {
                System.out.println("Directorio creado");
            } 
            else 
            {
                System.out.println("Error al crear directorio");
            }
        }
	}
	
	//Función para la búsqueda de la carpeta "C".
	private static String buscarCarpetaC(File directorio) {
        // Verificar si el directorio existe
        if (!directorio.exists()) 
        {
            return null;
        }

        // Obtener la lista de archivos y directorios en el directorio actual
        File[] archivos = directorio.listFiles();

        if (archivos != null) 
        {
            // Recorrer todos los archivos y directorios en el directorio actual
            for (File archivo : archivos) 
            {
                if (archivo.isDirectory()) 
                {	
                    // Si encontramos una carpeta con el nombre "C", retornamos su ruta
                    if (archivo.getName().equalsIgnoreCase("C")) 
                    {
                        return archivo.getAbsolutePath();
                    } 
                    else 
                    {
                        // Si no es la carpeta que estamos buscando, recursivamente
                        // buscamos en esta carpeta
                        String rutaEncontrada = buscarCarpetaC(archivo);
                        if (rutaEncontrada != null) {
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
		//Si se detecta que el sistema operativo es windows.
		if (System.getProperty("os.name").startsWith("Windows"))
		{
	        // Crear un proceso para ejecutar el comando tar
	        ProcessBuilder processBuilder = new ProcessBuilder("tar", "-xf", archivoOrigen, "-C", directorioDestino);
	
	        // Redirigir la salida estándar y de error del proceso a la consola
	        processBuilder.inheritIO();
	
	        // Ejecutar el proceso
	        Process proceso = processBuilder.start();
	
	        try 
	        {
	            // Esperar a que el proceso termine
	            int codigoSalida = proceso.waitFor();
	
	            // Verificar si el comando se ejecutó correctamente (código de salida 0)
	            if (codigoSalida == 0) 
	            {
	                System.out.println("Extracción exitosa.");
	            } 
	            else 
	            {
	                System.err.println("Error al extraer. Código de salida: " + codigoSalida);
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
	        ProcessBuilder processBuilder = new ProcessBuilder("unzip", archivoOrigen, "-d", directorioDestino);

	        // Redirigir la salida estándar y de error del proceso a la consola
	        processBuilder.inheritIO();

	        // Ejecutar el proceso
	        Process proceso = processBuilder.start();

	        try 
	        {
	            // Esperar a que el proceso termine
	            int codigoSalida = proceso.waitFor();

	            // Verificar si el comando se ejecutó correctamente (código de salida 0)
	            if (codigoSalida == 0) 
	            {
	                System.out.println("Descompresión exitosa.");
	            } 
	            else 
	            {
	                System.err.println("Error al descomprimir. Código de salida: " + codigoSalida);
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
	public static String seleccionarArchivoPruebas() {
        Frame frame = new Frame();
        FileDialog fileDialog = new FileDialog(frame, "Seleccionar Archivo", FileDialog.LOAD);
        
        // Mostrar el cuadro de diálogo
        fileDialog.setVisible(true);
        
        String archivoSeleccionado = fileDialog.getFile();
        String directorioSeleccionado = fileDialog.getDirectory();
        
        if (archivoSeleccionado != null) {
            try {
                // Construir la ruta completa del archivo seleccionado
                String rutaCompleta = directorioSeleccionado + archivoSeleccionado;
                
                // Leer el contenido del archivo en una variable
                BufferedReader br = new BufferedReader(new FileReader(rutaCompleta));
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                br.close();
                
                // Devolver el contenido del archivo como una cadena
                return contenido.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            // El usuario canceló la selección del archivo
            return null;
        }
    }
	
	private class ManejadorBotones implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String nombreArchivo = "";
			String comando = e.getActionCommand();
			int i = 0;
	        
			switch(comando)
			{
				//Si se presiona el botón "Seleccionar Carpeta".
				case "Seleccionar carpeta":
					File carpetaCodigos = new File(campoRutaCarpeta.getText()); 
					File[] listaArchivos = carpetaCodigos.listFiles();
					CodigoAlumno codigoAlumno = new CodigoAlumno();
					String rutaCarpetaC = "";
					
					//Este ciclo recorre todos los archvos dentro de la carpetaCodigos.
					for(i = 0; i<listaArchivos.length; i++)
					{
						nombreArchivo = listaArchivos[i].getName();
						
						//En la variable nombreArchivo se guarda el nombre del zip eliminando la extensión del mismo.
						if (nombreArchivo.endsWith(".zip")) 
						{
							//Si el archivo termina con .zip, se comprueba si ya existe una carpeta con ese nombre.
				            nombreArchivo = nombreArchivo.replace(".zip", "");
				            
				            File carpetaAlumno = new File(campoRutaCarpeta.getText()+"\\"+nombreArchivo);
							
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
									descomprimirZip(campoRutaCarpeta.getText()+"\\"+nombreArchivo+".zip", campoRutaCarpeta.getText()+"\\"+nombreArchivo);
								} 
						        catch (IOException e1) 
						        {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							//Se busca la carpeta "C" dentro de la carpeta donde se descomprimió el .zip.
							rutaCarpetaC = buscarCarpetaC(new File(campoRutaCarpeta.getText()+"\\"+nombreArchivo));
							
							//Si rutaCarpetaC no está vacía significa que se encontró la carpeta "C".
							if (rutaCarpetaC != null) 
							{
					            /*Se crea una instancia de la clase CodigoAlumno con su nickname el cual es el nombreArchivo pero se
					            eliminan los primeros 10 caracteres, también se guarda la ruta de la carpeta C y se le asigna una 
					            calificación de 100 (por el momento).*/
					            codigoAlumno = new CodigoAlumno(nombreArchivo.substring(10), rutaCarpetaC, 100);
					        } 
							
							else 
							{
					            /*Si no se encontró la carpeta, se crea una instancia de CodigoAlumno de la misma forma, sólo que 
								ahora la ruta de la carpeta C queda vacía y la calificación es 0.*/
					            codigoAlumno = new CodigoAlumno(nombreArchivo.substring(10), "", 0);
					        }
							
							//Se agrega la instancia creada a la lista de codigoAlumnos
							codigosAlumnos.add(codigoAlumno);
				        }
					}
					
					//Al terminar con todos los archivos, se hace visible el botón seleccionarArchivoPruebas.
					seleccionarArchivoPruebas.setVisible(true);
					break;
					
				case "Seleccionar archivo de pruebas":			
					String textoArchivo = seleccionarArchivoPruebas();
					String compilacion;
					
					//Si el usuario seleccionó un archivo.
					if(textoArchivo != null)
					{
						//Se dividen las lineas del archivo de pruebas, esto por medio del caracter "*".
					    String[] lineas = textoArchivo.split("\\*");
					    
					    List<Prueba> pruebas = obtenerPruebas(lineas);
					    
					    for(i = 0; i<codigosAlumnos.size(); i++)
					    {
					    	if(codigosAlumnos.get(i).getRutaCodigo() != "")
					    	{
						    	compilacion = compilarC(codigosAlumnos.get(i).getRutaCodigo()+"\\main.c");
				    			
							    System.out.println(compilacion);
							    
							    if(probarPrograma(pruebas))
				    			{
				    				System.out.println("Está bien");
				    			}
				    			else
				    			{
				    				System.out.println("Está mal");
				    				codigosAlumnos.get(i).setCalificacion(0);
				    			}
					    	}
					    }
					}
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
}
