import java.io.IOException;


public interface SocketClientEventListener {

	public void onError(IOException e);
	public void onMessage(String message);
	public void onClose();
	public void onOpen();
	public void messageArrivedWithRoundtrip(long roundtripTime);
	
}
