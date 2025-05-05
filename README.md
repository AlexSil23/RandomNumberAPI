# Random Number API

Esta API proporciona un servidor HTTP simple que genera números aleatorios dentro de un rango especificado a través de solicitudes GET.

## Uso

Para utilizar la API, sigue estos pasos:

### Iniciar el Servidor:

1.  Ejecuta la clase `RandomNumberAPI` como una aplicación Java.
2.  El servidor se iniciará en `http://localhost:8000/random`.

### Generar Números Aleatorios:

Envía una solicitud `GET` a `http://localhost:8000/random` con los parámetros `min` y `max` en la URL.

Por ejemplo: `http://localhost:8000/random?min=10&max=100` generará un número aleatorio entre 10 y 100, ambos inclusive.

## Detalles Técnicos

* La API utiliza la clase `RandomNumberHandler` para manejar las solicitudes HTTP y generar números aleatorios.
* Los parámetros `min` y `max` en la solicitud determinan el rango dentro del cual se genera el número aleatorio.
* Si los parámetros no son proporcionados o no son números válidos, la API responderá utilizando los valores por defecto (min=1, max=100) y mostrará un mensaje en la consola del servidor.

## Ejemplo de Implementación

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EjemploClienteRandom {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8000/random?min=10&max=100");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String response = in.readLine();
            System.out.println("Número aleatorio: " + response);
            in.close();
        } else {
            System.out.println("Error al obtener el número aleatorio. Código de respuesta: " + responseCode);
        }
    }
}

```

## Requisitos

* Java Development Kit (JDK) 8 o superior.
* No se requiere conexión a internet para iniciar el servidor localmente una vez que la aplicación está en ejecución.
