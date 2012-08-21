package org.webbitserver;

import static org.junit.Assert.*;

import org.junit.Test;
import org.webbitserver.stub.StubHttpRequest;

import static java.util.Arrays.*;

import java.util.Collections;
import java.util.List;

public class HttpRequestTest {
  private static final List<String> EMPTY = Collections.emptyList();

  @Test
  public void extractsSingleQueryParameter() throws Exception {
    HttpRequest req = new StubHttpRequest("http://host.com:8080/path?fish=cod&fruit=orange");
    assertEquals("cod", req.queryParam("fish"));
  }

  @Test
  public void extractsMultipleQueryParameters() throws Exception {
    HttpRequest req =
        new StubHttpRequest("http://host.com:8080/path?fish=cod&fruit=orange&fish=smørflyndre");
    assertEquals(asList("cod", "smørflyndre"), req.queryParams("fish"));
  }

  @Test
  public void alwaysReturnsEmptyListWhenThereIsNoQueryString() throws Exception {
    HttpRequest req = new StubHttpRequest("http://host.com:8080/path");
    assertEquals(EMPTY, req.queryParams("fish"));
    assertNull(req.queryParam("fish"));
  }

  @Test
  public void returnsEmptyListWhenThereIsNoSuchParameter() throws Exception {
    HttpRequest req = new StubHttpRequest("http://host.com:8080/path?poisson=cabillaud");
    assertEquals(EMPTY, req.queryParams("fish"));
    assertNull(req.queryParam("fish"));
  }

}
