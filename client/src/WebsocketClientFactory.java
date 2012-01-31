import java.net.URI;


public class WebsocketClientFactory implements SocketClientFactory {
	public SocketClient newClient(String baseURI, SocketClientEventListener listener)
	{
		try {
		    URI server = new URI("ws://" + baseURI + "/websocket");
		    return new WebsocketClient(server, listener);
		} catch (Exception e) {
			System.out.println("error: " + e);
			return null;
		}
	}
}
