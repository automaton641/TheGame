package automaton641;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
    public void run()
    {
        ServerSocket serverSocket;
        boolean fin = false;
        try {
            serverSocket = new ServerSocket(6000);
            Socket socket = serverSocket.accept();
            DataInputStream in =
                    new DataInputStream(socket.getInputStream());
            do {
                String mensaje ="";
                mensaje = in.readUTF();
                System.out.println(mensaje);
            } while (1>0);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
