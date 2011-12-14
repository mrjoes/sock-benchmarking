import java.io.IOException;


public abstract interface SocketClient {
	public abstract void sendMessage(String message);
	public abstract void sendTimestamp();
	
	public abstract void connect();
	public abstract void close() throws IOException;
}
