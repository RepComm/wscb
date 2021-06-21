package comm.rep;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WSServer extends WebSocketServer {
  private List<WSEventListener> listeners = new ArrayList<>();

  private final ReentrantLock listenerLock = new ReentrantLock();
  
  WSServer(int port) {
    super(new InetSocketAddress(port));    
  }

  public WSServer listen (WSEventListener listener) {
    this.listenerLock.lock();
    this.listeners.add(listener);
    this.listenerLock.unlock();
    return this;
  }

  public WSServer deafen (WSEventListener listener) {
    this.listenerLock.lock();
    this.listeners.remove(listener);
    this.listenerLock.unlock();
    return this;
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    this.listenerLock.lock();

    for (WSEventListener listener : this.listeners) {
      //TODO - not sure if legal, may need locks again?
      WSEvent evt = new WSEvent( WSEvent.EVENT_TYPE_CONNECT );
      evt.client = conn;
      evt.handshake = handshake;

      listener.onEvent(evt);
    }

    this.listenerLock.unlock();
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    this.listenerLock.lock();

    for (WSEventListener listener : this.listeners) {
      //TODO - not sure if legal, may need locks again?
      WSEvent evt = new WSEvent( WSEvent.EVENT_TYPE_DISCONNECT );
      evt.client = conn;
      evt.stopCode = code;
      evt.stopReason = reason;
      evt.stopClientWasRemote = remote;
      
      listener.onEvent(evt);
    }

    this.listenerLock.unlock();
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    this.listenerLock.lock();

    for (WSEventListener listener : this.listeners) {
      //TODO - not sure if legal, may need locks again?
      WSEvent evt = new WSEvent( WSEvent.EVENT_TYPE_MESSAGE_STRING );
      evt.client = conn;
      evt.messageString = message;
      
      listener.onEvent(evt);
    }

    this.listenerLock.unlock();
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    this.listenerLock.lock();

    for (WSEventListener listener : this.listeners) {
      //TODO - not sure if legal, may need locks again?
      WSEvent evt = new WSEvent( WSEvent.EVENT_TYPE_EXCEPTION );
      evt.client = conn;
      evt.exception = ex;
      
      listener.onEvent(evt);
    }

    this.listenerLock.unlock();
  }

  @Override
  public void onStart() {
    this.listenerLock.lock();

    for (WSEventListener listener : this.listeners) {
      //TODO - not sure if legal, may need locks again?
      WSEvent evt = new WSEvent( WSEvent.EVENT_TYPE_START );      
      listener.onEvent(evt);
    }

    this.listenerLock.unlock();
  }
}
