import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

import net.tootallnate.websocket.WebSocketClient;

public class SockJSClient extends WebSocketClient implements SocketClient {
	private SocketClientEventListener listener;
	
	protected static int nextId = 0;
	protected int id;	
	
	public SockJSClient(URI server, SocketClientEventListener listener) {
		super(server);
		
		// Store listener
		this.listener = listener;
		
		// Get myself new id
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

		// We don't care about any messages except of data packet
		switch(message.toCharArray()[0]) {
		case 'a':
			// Quick and dirty message unpacking
			String parts = message.substring(2, message.length() - 1);
			
			String[] messages = parts.split(",");
			
			if (messages.length == 0)
			{
				System.out.println("Fail!!!");
			}
			
			for (String msg : messages)
			{
				String decodedMessage = msg.trim().substring(1, msg.length() - 1);
								
				long roundtripTime;
				String[] payloadParts = decodedMessage.split(":");
				if(new Integer(this.id).toString().compareTo(payloadParts[0])==0) {
					roundtripTime = messageArrivedAt - new Long(payloadParts[1]);
					this.listener.messageArrivedWithRoundtrip(roundtripTime);					
				}

				this.listener.onMessage(decodedMessage);
			}
			
			break;
		}
	}

	@Override
	public void onOpen() {
		this.listener.onOpen();
	}

	// Interface implementation
	public void sendMessage(String message) {
		try {
			String fullMessage = "[\"" + message + "\"]";
			this.send(fullMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendTimestamp() {
		String message = this.id + ":" + new Long(Calendar.getInstance().getTimeInMillis()).toString();
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
