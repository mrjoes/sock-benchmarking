import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


public class SocketIOClientFactory implements SocketClientFactory {
	public SocketClient newClient(String baseURI, SocketClientEventListener listener)
	{
		try {
			URL url = new URL("http://" + baseURI + "/socket.io/1/"); 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST"); 

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
			wr.flush();
			wr.close();
			
		    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    String line = rd.readLine();
		    String hskey = line.split(":")[0];
		    URI server = new URI("ws://" + baseURI + "/socket.io/1/websocket/" + hskey);
		    return new SocketIOClient(server, listener);
		} catch (Exception e) {
			System.out.println("error: " + e);
			return null;
		}
	}
}
