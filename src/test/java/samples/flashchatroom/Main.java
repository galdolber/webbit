package samples.flashchatroom;

import static org.webbitserver.WebServers.*;

import org.webbitserver.WebServer;
import org.webbitserver.handler.StaticFileHandler;
import org.webbitserver.handler.logging.LoggingHandler;
import org.webbitserver.handler.logging.SimpleLogSink;

public class Main {

  public static void main(String[] args) throws Exception {
    WebServer webServer =
        createWebServer(9876).add(new LoggingHandler(new SimpleLogSink(Chatroom.USERNAME_KEY)))
            .add("/chatsocket", new Chatroom()).add(
                new StaticFileHandler("./src/test/java/samples/flashchatroom/content")).start()
            .get();

    System.out.println("Chat room running on: " + webServer.getUri());
  }

}
