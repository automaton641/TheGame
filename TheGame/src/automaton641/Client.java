package automaton641;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread
{
    public void run()
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        Socket socket;

        byte[] mensaje_bytes = new byte[256];
        String mensaje="Hello from client";
        try {

            socket = new Socket("127.0.0.1",6000);

// Declaramos e instanciamos el objeto DataOutputStream
// que nos valdrá para enviar datos al servidor destino
            DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());

// Creamos un bucle do while en el que enviamos al servidor el mensaje
// los datos que hemos obtenido despues de ejecutar la función
// "readLine" en la instancia "in"


// enviamos el mensaje codificado en UTF
                out.writeUTF(mensaje);
// mientras el mensaje no encuentre la cadena fin, seguiremos ejecutando
// el bucle do-while

        }
// utilizamos el catch para capturar los errores que puedan surgir
        catch (Exception e) {
// si existen errores los mostrará en la consola y después saldrá del
// programa
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
