import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    public static void main(String[] args) {

    enum State {
        Init,
        SLeft,
        SRight,
        Sfinal


    };





        try(ServerSocket serverSocket = new ServerSocket(1337) ){
            System.out.println("server running ...");
        while (true){
            final var socket = serverSocket.accept(); //block bis Verbindung
            System.out.println("client connected");
            Thread.startVirtualThread( () -> {
               try(final var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                   final var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                   State currentState = State.Init; // initial state
                   String line ;

                   while ((line = reader.readLine()) != null) {

                       switch (currentState) {
                           case Init:
                               if(line.equals("Left!")){
                                   currentState = State.SLeft;
                                   writer.write("WentLeft.");
                                   writer.flush();
                               } else if (line.equals("Right!")) {
                                   writer.write("WentRight.\n");
                                   currentState = State.SRight;
                                   writer.flush();
                               }
                               else {
                                   System.out.println("Invalid command exiting...");
                                   return;
                               }
                               break;

                           case SLeft:

                               if (line.equals("GoOn1!")) {
                                   currentState = State.Sfinal;
                                   writer.write("WentOn1.\n");
                                   writer.flush();
                               }
                               else {
                                   System.out.println("Invalid command in SLeft. Closing connection.");
                                   return;
                               }
                               break;

                           case SRight:
                               if (line.equals("GoOn2!")) {
                                   currentState = State.Sfinal;
                                   writer.write("WentOn2.\n");
                                   writer.flush();
                               }
                               else {
                                   System.out.println("Invalid command in SRight. Closing connection.");
                                   return;
                               }
                               break;

                           case Sfinal:
                               if (line.equals("Back!")) {
                                   currentState = State.Init;
                                   writer.write("WentBack.\n");
                                   writer.flush();

                               } else if (line.equals("OnceMore!")) {
                               currentState = State.SLeft;
                               writer.write("DidOnceMore.\n");
                               writer.flush();

                               } else {
                                   System.out.println("Invalid command in Sfinal. Exiting...");
                                   return;
                               }
                               break;


                       }
                   }




               } catch (IOException e) {
                   System.out.println(" error" + e.getMessage());
               } ;


            });
        }




        } catch (final IOException e) {
            throw new RuntimeException(e);

        }










}



}
