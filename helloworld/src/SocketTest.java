import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Luonanqin on 8/5/14.
 */
class SocketClient{

	public SocketClient() {
		try {
			Socket client = new Socket("127.0.0.1", 1234);

			OutputStream out = client.getOutputStream();
			String name = "luonanqin";
			out.write(name.getBytes("utf-8"));

			InputStream in = client.getInputStream();
			byte[] b = new byte[1024];
			while (in.read(b) != -1) {
				System.out.println(new String(b, "utf-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Client BEGIN!");
	}
}
class SocketServer implements Runnable{

	public SocketServer() {
	}

	public void run() {
		try {
			ServerSocket server = new ServerSocket(1234);
			Socket socket = server.accept();

			//InputStream in = socket.getInputStream();
			//byte[] b = new byte[1024];
			//while (in.read(b) != -1) {
			//	System.out.println(new String(b, "utf-8"));
			//}

			OutputStream out = socket.getOutputStream();
			String hello = "hello! luonanqin!";
			out.write(hello.getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server END!");
	}
}
public class SocketTest {

	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		new Thread(server).start();
		SocketClient client = new SocketClient();
	}
}
