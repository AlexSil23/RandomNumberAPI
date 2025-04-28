package RandomNumberAPI;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;


// TODO: Auto-generated Javadoc
/**
 * The Class RandomNumberAPI.
 * @author Alexander Silvera
 */
public class RandomNumberAPI {
	
	/**
	 * Método principal que inicia un servidor HTTP en el puerto 8000.
	 * <p>
	 * El servidor maneja peticiones en el contexto "/random", utilizando la clase
	 * {@link RandomNumberHandler} para generar números aleatorios según los parámetros
	 * "min" y "max" proporcionados en la URL.
	 * </p>
	 *
	 * @param args Los argumentos de la línea de comandos (no se utilizan en este método).
	 * @throws IOException Si ocurre un error de entrada/salida al crear el servidor.
	 */
	public static void main(String[] args) throws IOException {

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/random", new RandomNumberHandler());
		server.setExecutor(null);
		server.start();
		System.out.println("Servidor iniciado en http://localhost:8000/random?min=10&max=100");
	}
}

