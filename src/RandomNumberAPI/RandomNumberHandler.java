package RandomNumberAPI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * La clase RandomNumberHandler maneja solicitudes HTTP y genera un número aleatorio.
 * Implementa la interfaz HttpHandler y procesa solicitudes GET para devolver
 * un número aleatorio dentro de un rango especificado.
 * 
 * Los parámetros de la solicitud 'min' y 'max' definen el rango. Si no se proporcionan,
 * el rango por defecto es de 1 a 100.
 * 
 * Ejemplo de solicitud: GET /random?min=10&max=50
 * Respuesta: { "randomNumber": 25 }
 * 
 * @author Alexander Silvera
 */
class RandomNumberHandler implements HttpHandler {

	/** Generador de números aleatorios. */
	private final Random R = new Random();

	/**
	 * Maneja las solicitudes HTTP GET para generar un número aleatorio.
	 * <p>
	 * Este método procesa solicitudes GET, extrayendo los parámetros "min" y "max"
	 * de la consulta. Luego genera un número aleatorio dentro de ese rango
	 * y devuelve la respuesta en formato JSON.
	 * </p>
	 * <p>
	 * Si el método HTTP no es GET, responde con un código 405 (Method Not Allowed).
	 * </p>
	 *
	 * @param exchange El objeto {@link HttpExchange} que representa la solicitud y respuesta HTTP.
	 * @throws IOException Si ocurre un error al leer la solicitud o escribir la respuesta.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (!"GET".equals(exchange.getRequestMethod())) {
			exchange.sendResponseHeaders(405, 0);
			return;
		}

		Map<String, Integer> params = parseQuery(exchange.getRequestURI().getQuery());
		int min = params.getOrDefault("min", 1);
		int max = params.getOrDefault("max", 100);
		int randomNumber = generateRandomNumber(min, max);

		String response = "{ \"randomNumber\": " + randomNumber + " }";
		exchange.sendResponseHeaders(200, response.length());
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	/**
	 * Parsea una cadena de consulta (query string) en un mapa de parámetros clave-valor.
	 * <p>
	 * Cada par clave=valor es separado por el carácter '&'. 
	 * Si el valor no es un número entero válido, el par se ignora y se registra un mensaje en la consola.
	 * </p>
	 *
	 * @param query La cadena de consulta de la URL (por ejemplo: "min=10&max=100").
	 * @return Un {@link Map} que contiene las claves y valores numéricos extraídos de la consulta.
	 */
	private Map<String, Integer> parseQuery(String query) {
		Map<String, Integer> params = new HashMap<>();
		if (query != null) {
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				String[] keyValue = pair.split("=");
				if (keyValue.length == 2) {
					try {
						params.put(keyValue[0], Integer.parseInt(keyValue[1]));
					} catch (NumberFormatException e) {
						System.out.println("Formatos de número inválidos. "+keyValue[1]);
					}
				}
			}
		}
		return params;
	}

	/**
	 * Genera un número aleatorio dentro de un rango especificado, incluyendo ambos extremos.
	 *
	 * @param min El valor mínimo (inclusive) que puede tomar el número aleatorio.
	 * @param max El valor máximo (inclusive) que puede tomar el número aleatorio.
	 * @return Un número entero aleatorio entre {@code min} y {@code max}, ambos inclusive.
	 */
	private int generateRandomNumber(int min, int max) {
		return min + R.nextInt(max - min + 1);
	}
}
