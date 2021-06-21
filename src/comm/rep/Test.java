
package comm.rep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Hello World");

    int port = 10208;
    System.out.println("Instantiating WSServer with port " + port);

    //Create the server object
    WSServer server = new WSServer(port);

    // milliseconds until a connection terminates due to lag
    server.setConnectionLostTimeout(1000);

    //----Demo separate thread that polls events, also responsible for calling event listeners
    //Event listeners are called in the same thread that server.pollEvents() is called in
    boolean keepListenerThreadAlive = true;

    Thread listenerThread = new Thread(()->{
      while (keepListenerThreadAlive) {
        server.pollEvents();
      }
    });
    listenerThread.start();

    //Listen to events on the server, executed in same thread as pollEvents() is called in
    server.listen((WSEvent evt)->{
      System.out.println("Event type: " + evt.type);
      if (evt.type == WSEvent.EVENT_TYPE_STOP) {
        System.exit(0);
      }
    });

    //Start the server
    server.start();

    //Demo interactive code for sending data to all connected clients from the command line terminal
    //"exit" will stop the server (and in this program, the whole program)
    BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      String in = null;
      in = sysin.readLine();
      server.broadcast(in);
      if (in.equals("exit")) {
        server.stop(1000);
        break;
      }
    }
  }
}
