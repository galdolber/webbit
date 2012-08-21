package org.webbitserver.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

import static java.util.Arrays.*;

public class QueryParametersTest {
  @Test
  public void parsesOneParameter() throws Exception {
    assertEquals("bar", new QueryParameters("foo=bar").first("foo"));
  }

  @Test
  public void parsesTwoParameters() throws Exception {
    assertEquals(asList("b", "e"), new QueryParameters("a=b&c=d&a=e").all("a"));
  }

  @Test
  public void parsesEmptyParameter() throws Exception {
    assertEquals(null, new QueryParameters("a=").first("a"));
  }

  @Test
  public void parsesMixOfPresentAndEmptyParameters() throws Exception {
    assertEquals(asList("b", null, "e"), new QueryParameters("a=b&a=&a=e").all("a"));
  }
}
