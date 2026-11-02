import com.sun.net.httpserver.*;
import java.net.*;

public class exp7 {
 static String u = "";   // session (server memory)
 public static void main(String[] a) throws Exception {
  HttpServer s = HttpServer.create(new InetSocketAddress(1234), 0);
  s.createContext("/", e -> {
   String q = e.getRequestURI().getQuery();
   String n = (q == null) ? "" : q.substring(2);
   String h = "<form>Name: <input name='n'><input type='submit'></form>";
   if (!n.isEmpty()) {
    u = n;                                               // 2. Session
    e.getResponseHeaders().add("Set-Cookie", "c=" + n);  // 1. Cookie
    h += "Hello " + n
      + "<br><a href='/?n=" + n + "'>URL Rewriting</a>"  // 3. URL rewriting
      + "<form><input type='hidden' name='n' value='" + n + "'>"
      + "<input type='submit' value='Hidden'></form>";   // 4. Hidden field
   }
   h += "<br>Cookie: " + e.getRequestHeaders().getFirst("Cookie") + "<br>Session: " + u;
   e.getResponseHeaders().add("Content-Type", "text/html");
   byte[] b = h.getBytes();
   e.sendResponseHeaders(200, b.length);
   e.getResponseBody().write(b);
   e.close();
  });
  s.start();
 }
}