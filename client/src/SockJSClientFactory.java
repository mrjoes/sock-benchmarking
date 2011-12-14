import java.net.URI;


public class SockJSClientFactory implements SocketClientFactory {
	public SocketClient newClient(String baseURI, SocketClientEventListener listener)
	{
		try {
		    URI server = new URI("ws://" + baseURI + "/broadcast/0/0/websocket");
		    return new SockJSClient(server, listener);
		} catch (Exception e) {
			System.out.println("error: " + e);
			return null;
		}
	}
}
