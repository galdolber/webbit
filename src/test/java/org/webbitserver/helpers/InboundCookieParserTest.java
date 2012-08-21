package org.webbitserver.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class InboundCookieParserTest {
  @Test
  public void testParseEmpty() throws Exception {
    List<String> values = new ArrayList<String>();
    assertEquals(new ArrayList<HttpCookie>(), InboundCookieParser.parse(values));
  }

  @Test
  public void testParse() throws Exception {
    List<String> values = new ArrayList<String>();
    values.add("test=me");
    values.add("testing=\"now\"");

    List<HttpCookie> expected = new ArrayList<HttpCookie>();
    expected.add(new HttpCookie("test", "me"));
    expected.add(new HttpCookie("testing", "now"));

    assertEquals(expected, InboundCookieParser.parse(values));
  }
}
