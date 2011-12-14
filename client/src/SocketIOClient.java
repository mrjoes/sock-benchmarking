import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.tootallnate.websocket.WebSocketClient;


public class SocketIOClient extends WebSocketClient implements SocketClient {
	
	protected SocketClientEventListener listener;
	protected Map<String, Long> requests = new HashMap<String, Long>();
	
	protected static int nextId = 0;
	
	protected int id;
	
	
	public SocketIOClient(URI server, SocketClientEventListener listener) {
		super(server);
		
		this.listener = listener;
		id = nextId;
		
		nextId++;
	}

	@Override
	public void onClose() {
		this.listener.onClose();
	}

	@Override
	public void onIOError(IOException arg0) {
		System.out.println("error: " + arg0);
	}

	@Override
	public void onMessage(String message) {
		long messageArrivedAt = Calendar.getInstance().getTimeInMillis();
		
		switch(message.toCharArray()[0]) {
		case '2':
			this.heartbeat();
			break;
		case '5':
			// We want to extract the actual message. Going to hack this shit.
			String[] messageParts = message.split(":");
			String lastPart = messageParts[messageParts.length-1];
			String chatPayload = lastPart.substring(1, lastPart.length()-4);
			
			long roundtripTime;
			String[] payloadParts = chatPayload.split(",");
			if(new Integer(this.id).toString().compareTo(payloadParts[0])==0) {
				roundtripTime = messageArrivedAt - new Long(payloadParts[1]);
				this.listener.messageArrivedWithRoundtrip(roundtripTime);
			}

			this.listener.onMessage(chatPayload);

			break;
		}
	}

	@Override
	public void onOpen() {
		this.listener.onOpen();
	}
	
	public void heartbeat() {
		try {
			this.send("2:::");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) {
		try {
			String fullMessage = "5:::{\"name\":\"chat\", \"args\":[{\"text\":\""+message+"\"}]}";
			this.send(fullMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendTimestamp() {
		String message = this.id + "," + new Long(Calendar.getInstance().getTimeInMillis()).toString();
		this.sendMessage(message);
	}
		
	public void connect()
	{
		super.connect();
	}
	
	public void close() throws IOException
	{
		super.close();
	}	
}