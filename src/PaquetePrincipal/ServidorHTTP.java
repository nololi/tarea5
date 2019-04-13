package PaquetePrincipal;

import java.io.BufferedReader;
import java.net.Socket;
import java.util.Date;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * *****************************************************************************
 * Servidor HTTP que atiende peticiones de tipo 'GET' recibidas por el puerto 
 * 8066
 *
 * NOTA: para probar este código, comprueba primero de que no tienes ningún otro
 * servicio por el puerto 8066 (por ejemplo, con el comando 'netstat' si estás
 * utilizando Windows)
 *
 * @author IMCG
 */
class ServidorHTTP  extends Thread{ //extiendo la clase Thread para concurrencia
	private Socket socCliente;
	private static int CONTADOR_PETICIONES=0;
	private static int numeroPeticionActual;

  /**
   * **************************************************************************
   * procedimiento principal que asigna a cada petición entrante un socket 
   * cliente, por donde se enviará la respuesta una vez procesada 
   *
   * @param args the command line arguments
   */
	public ServidorHTTP(Socket socCliente) {
		this.socCliente =socCliente;
	}
	

	/**
	   *****************************************************************************
	   * procesa la petición recibida
	   *
	   * @throws IOException
	   */
	  private static void procesaPeticion(Socket socketCliente) throws IOException {		  
	    //variables locales
	    String peticion;
	    String html;

	    //Flujo de entrada
	    InputStreamReader inSR = new InputStreamReader(
	            socketCliente.getInputStream());
	    //espacio en memoria para la entrada de peticiones
	    BufferedReader bufLeer = new BufferedReader(inSR);

	    //objeto de java.io que entre otras características, permite escribir 
	    //'línea a línea' en un flujo de salida
	    PrintWriter printWriter = new PrintWriter(
	            socketCliente.getOutputStream(), true);

	    //mensaje petición cliente
	    peticion = bufLeer.readLine();

	    //para compactar la petición y facilitar así su análisis, suprimimos todos 
	    //los espacios en blanco que contenga
	    peticion = peticion.replaceAll(" ", "");

	    //si realmente se trata de una petición 'GET' (que es la única que vamos a
	    //implementar en nuestro Servidor)
	    if (peticion.startsWith("GET")) {
	      System.out.println("Atendiendo al cliente n�mero "  + numeroPeticionActual);
	      //extrae la subcadena entre 'GET' y 'HTTP/1.1'
	      peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));

	      //si corresponde a la página de inicio
	      if (peticion.length() == 0 || peticion.equals("/")) {
	        //sirve la página
	        html = Paginas.html_index;
	        printWriter.println(Mensajes.lineaInicial_OK);
	        printWriter.println(Paginas.primeraCabecera);
	        printWriter.println("Content-Length: " + html.length() + 1);
	        printWriter.println("\n");
	        printWriter.println(html);
	      } //si corresponde a la página del Quijote
	      else if (peticion.equals("/quijote")) {
	        //sirve la página
	        html = Paginas.html_quijote;
	        printWriter.println(Mensajes.lineaInicial_OK);
	        printWriter.println(Paginas.primeraCabecera);
	        printWriter.println("Content-Length: " + html.length() + 1);
	        printWriter.println("\n");
	        printWriter.println(html);
	      } //en cualquier otro caso
	      else {
	        //sirve la página
	        html = Paginas.html_noEncontrado;
	        printWriter.println(Mensajes.lineaInicial_NotFound);
	        printWriter.println(Paginas.primeraCabecera);
	        printWriter.println("Content-Length: " + html.length() + 1);
	        printWriter.println("\n");
	        printWriter.println(html);
	      }
	    
	    }
	  }

	  /**
	   * **************************************************************************
	   * muestra un mensaje en la Salida que confirma el arranque, y da algunas
	   * indicaciones posteriores
	   */
	  private static void imprimeDisponible() {
	    System.out.println("El Servidor WEB se está ejecutando y permanece a la "
	            + "escucha por el puerto 8066.\nEscribe en la barra de direcciones "
	            + "de tu explorador preferido:\n\nhttp://localhost:8066\npara "
	            + "solicitar la página de bienvenida\n\nhttp://localhost:8066/"
	            + "quijote\n para solicitar una página del Quijote,\n\nhttp://"
	            + "localhost:8066/q\n para simular un error");
	  }
	
	  @Override
	  public void run() {
		numeroPeticionActual = ++CONTADOR_PETICIONES;
		System.out.println("Cliente " + numeroPeticionActual +" conectado en nuevo hilo " + new Date());
		
		 //atiendo un cliente
	    System.out.println("Atendiendo al cliente ");
	    
		try {
			procesaPeticion(socCliente);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// cierra la conexión entrante
		if (socCliente != null) {
			try {
				socCliente.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    System.out.println("cliente atendido.\n Petici�n " + numeroPeticionActual +" atendida");
	    System.out.println("Cerrando hilo de petici�n n�mero  " + numeroPeticionActual + " a fecha " + new Date());
	  }
	  
	
  public static void main(String[] args) throws IOException, Exception {
		//Sockets cliente y servidor
		ServerSocket socServidor = null;
		Socket socCliente = null;

		try {
			// Asociamos al servidor el puerto 8066
			socServidor = new ServerSocket(8066);
			imprimeDisponible();

			// ante una petición entrante, procesa la petición por el socket cliente
			// por donde la recibe
			while (true) {
				// a la espera de peticiones
				socCliente = socServidor.accept();
				//creo un nuevo hilo por cada una de las peticiones
				new ServidorHTTP(socCliente).start();
			}

		} catch (IOException e) {
			System.out.println("Error al conectarse" + e.getMessage());
		} finally {
			//si no son nulos cierro los sockets cliente y servidor
			if (socServidor != null) {
				try {
					socServidor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socCliente != null) {
				try {
					socCliente.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
  }

}
