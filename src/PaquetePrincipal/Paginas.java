package PaquetePrincipal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * ****************************************************************************
 * clase no instanciable donde se definen algunos valores finales
 *
 * @author IMCG
 */
public class Paginas {
	
	
	/*
	 * M�todo que devuelve la fecha que se usar� en la cabecera P�ginas de info
	 * utilizadas sobre Date
	 * https://en.wikipedia.org/wiki/List_of_HTTP_header_fields
	 * https://desarrolloweb.com/articulos/cabeceras-http-livehttpheaders.html 
	 * A este m�todo lo llamar� desde ServidorHTTP para agregar la cabecera pues aqu� solo
	 * consta el head y body de la p�gina
	 */
	public static String obtenerFecha() {
		DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(new Date());
	}
	

  public static final String primeraCabecera =
          "Content-Type:text/html;charset=UTF-8";
  //contenido index
  public static final String html_index = "<html>"
          + "<head><title>index</title></head>"
          + "<body>"
          + "<h1>¡Enhorabuena!</h1>"
          + "<p>Tu servidor HTTP mínimo funciona correctamente</p>"
          + "</body>"
          + "</html>";
  //contenido quijote
  public static final String html_quijote = "<html>"
          + "<head><title>quijote</title></head>"
          + "<body>"
          + "<h1>Así comienza el Quijote</h1>"
          + "<p>En un lugar de la Mancha, de cuyo nombre no quiero "
          + "acordarme, no ha mucho tiempo que vivía un hidalgo de los "
          + "de lanza en astillero, adarga antigua, rocín flaco y galgo "
          + "corredor. Una olla de algo más vaca que carnero, salpicón "
          + "las más noches, duelos y quebrantos (huevos con tocino) los "
          + "sábados, lentejas los viernes, algún palomino de añadidura "
          + "los domingos, consumían las tres partes de su hacienda. El "
          + "resto della concluían sayo de velarte (traje de paño fino), "
          + "calzas de velludo (terciopelo) para las fiestas con sus "
          + "pantuflos de lo mismo, y los días de entresemana se honraba "
          + "con su vellorí (pardo de paño) de lo más fino. Tenía en su "
          + "casa una ama que pasaba de los cuarenta, y una sobrina que "
          + "no llegaba a los veinte, y un mozo de campo y plaza, que "
          + "así ensillaba el rocín como tomaba la podadera...</p>"
          + "</body>"
          + "</html>";
  //contenido noEncontrado
  public static final String html_noEncontrado = "<html>"
          + "<head><title>noEncontrado</title></head>"
          + "<body>"
          + "<h1>¡ERROR! Página no encontrada</h1>"
          + "<p>La página que solicitaste no existe en nuestro "
          + "servidor</p>"
          + "</body>"
          + "</html>";
}