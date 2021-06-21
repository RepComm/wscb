
package comm.rep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Hello World");

    int port = 10208;
    System.out.println("Instantiating WSServer with port " + port);

    WSServer server = new WSServer(port);

    server.setConnectionLostTimeout(1000);

    server.listen((WSEvent evt)->{
      System.out.println("Event type: " + evt.type);

    });

    server.start();

    
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
