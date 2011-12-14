
public interface SocketClientFactory {
	SocketClient newClient(String baseURI, SocketClientEventListener listener);
}
