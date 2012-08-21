package org.webbitserver.netty;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Values.*;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.websocketx.WebSocket13FrameDecoder;
import org.jboss.netty.handler.codec.http.websocketx.WebSocket13FrameEncoder;

public class Hixie75 implements WebSocketVersion {
  private final HttpRequest req;
  private final HttpResponse res;

  public Hixie75(HttpRequest req, HttpResponse res) {
    this.req = req;
    this.res = res;
  }

  @Override
  public boolean matches() {
    return false;
  }

  @Override
  public void prepareHandshakeResponse(NettyWebSocketConnection webSocketConnection) {
    webSocketConnection.setVersion("HIXIE-75");
    res.setStatus(new HttpResponseStatus(101, "Web Socket Protocol Handshake"));
    res.addHeader(org.jboss.netty.handler.codec.http.HttpHeaders.Names.UPGRADE, WEBSOCKET);
    res.addHeader(CONNECTION, HttpHeaders.Values.UPGRADE);
    String origin = req.getHeader(ORIGIN);
    if (origin != null) {
      res.addHeader(WEBSOCKET_ORIGIN, origin);
    }
    res.addHeader(WEBSOCKET_LOCATION, getWebSocketLocation(req));
    String protocol = req.getHeader(WEBSOCKET_PROTOCOL);
    if (protocol != null) {
      res.addHeader(WEBSOCKET_PROTOCOL, protocol);
    }
  }

  @Override
  public ChannelHandler createDecoder() {
    return new WebSocket13FrameDecoder(true, true);
  }

  @Override
  public ChannelHandler createEncoder() {
    return new WebSocket13FrameEncoder(true);
  }

  private String getWebSocketLocation(HttpRequest req) {
    return getWebSocketProtocol(req) + req.getHeader(HttpHeaders.Names.HOST) + req.getUri();
  }

  private String getWebSocketProtocol(HttpRequest req) {
    if (req.getHeader(HttpHeaders.Names.ORIGIN).matches("(?s)https://.*")) {
      return "wss://";
    } else {
      return "ws://";
    }
  }
}
