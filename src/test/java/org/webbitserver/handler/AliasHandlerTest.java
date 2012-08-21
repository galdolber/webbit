package org.webbitserver.handler;

import static org.junit.Assert.*;
import static org.webbitserver.WebServers.*;
import static org.webbitserver.testutil.HttpClient.*;

import org.junit.After;
import org.junit.Test;
import org.webbitserver.WebServer;

import java.util.concurrent.ExecutionException;

public class AliasHandlerTest {
  private WebServer webServer = createWebServer(59504);

  @After
  public void die() throws InterruptedException, ExecutionException {
    webServer.stop().get();
  }

  @Test
  public void forwardsAliasedPath() throws Exception {
    webServer.add("/tomayto", new AliasHandler("/tomato")).add("/tomato",
        new StringHttpHandler("text/plain", "body")).start().get();
    assertEquals("body", contents(httpGet(webServer, "/tomayto")));
  }
}
