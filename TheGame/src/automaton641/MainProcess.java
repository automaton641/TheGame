package automaton641;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class MainProcess extends Thread
{
    LinkedList<MyInteger> riddleList = new LinkedList<MyInteger>();
    public Player player;
    public int level = 3;
    public Main main;
    public Timer timer = new Timer();

    public void fillRiddleList() {
        riddleList.clear();
        for (int i = 0; i < level; i++) {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, 128);
            riddleList.add(new MyInteger(randomNumber));
        }
    }
    public void sendRiddleList(DataOutputStream out) {
        Iterator iterator = riddleList.iterator();
        while (iterator.hasNext()) {
            try {
                out.writeUTF(iterator.next().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void fillRiddleListFromClient(DataInputStream in) {
        riddleList.clear();
        String message = "";
        for (int i = 0; i < level; i++) {
            try {
                message = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            riddleList.add(new MyInteger(Integer.parseInt(message)));
        }
    }
    String message = "";
    public synchronized void getMessage() throws InterruptedException {
        wait();
    }
    public synchronized void putMessage(String message) throws InterruptedException {
        this.message = message;
        notify();
    }
    public void printRiddleList() {
        main.writeMessage("RiddleList:");
        int i = 1;
        Iterator iterator = riddleList.iterator();
        while (iterator.hasNext()) {
            main.writeMessage(i + ": " + iterator.next().toString());
            i++;
        }
    }
    public void serverFunction() {
        main.writeMessage("Server is alive");
        ServerSocket serverSocket;
        boolean fin = false;
        try {
            serverSocket = new ServerSocket(6000);
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String mensaje ="";
            mensaje = in.readUTF();
            main.writeMessage("Connection established with client");
            player = new Player();
            fillRiddleList();
            out.writeUTF(Integer.toString(level));
            sendRiddleList(out);
            main.writeMessage("press enter or the send button when you are ready for the next round");
            getMessage();
            printRiddleList();

            /*timer.schedule(new TimerTask() {
                @Override
                public void run() {

                }
            }, 16 * 1000);*/
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void clientFunction() {
        main.writeMessage("Client is alive");
        Socket socket;
        byte[] mensaje_bytes = new byte[256];
        String mensaje="Hello from client";
        try {
            socket = new Socket("127.0.0.1",6000);
            main.writeMessage("Connection established with server");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out.writeUTF(mensaje);
            player = new Player();
            main.writeMessage("press enter or the send button when you are ready for the next round");
            getMessage();

            String levelString = in.readUTF();
            level = Integer.parseInt(levelString);
            fillRiddleListFromClient(in);
            printRiddleList();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run()
    {
        System.out.println("HELLO FROM MAIN PROCESS");
        try {
            System.out.println("message: " + message);
            boolean messageAccepted = false;
            while(!messageAccepted) {
                getMessage();
                if(message.equalsIgnoreCase("a")) {
                    serverFunction();
                    messageAccepted = true;
                }
                if(message.equalsIgnoreCase("b")) {
                    clientFunction();
                    messageAccepted = true;
                }
                if(!messageAccepted) {
                    main.writeMessage("Sorry, your command is not recognized, try again...");
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
