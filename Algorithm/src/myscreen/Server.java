package myscreen;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;

public class Server {

	public static void main(String args[]) {
		SendScreenImg sender = new SendScreenImg();
		sender.changeServerPort(30009);// �˴������޸ķ���˿�
		new Thread(sender).start();// ��ͼ�������
		OperateWindow operate = new OperateWindow();
		// operate.changeServerPort(30010);//�˴������޸ķ���˿�
		new Thread(operate).start();// ������ٿط���

		//
	}
}

class SendScreenImg implements Runnable {

	public static final int DEFAULT_SERVER_PORT = 30011;
	private int serverPort;
	private Robot robot;
	private ServerSocket serverSocket;
	private Rectangle rect;// Rectangle ָ�������ռ��е�һ������
	private Dimension screen;// Dimension ���װ��������������Ŀ�ȡ�
	private BufferedImage img;
	private Socket socket;
	private ZipOutputStream zip;// ����Ϊ�� ZIP �ļ���ʽд���ļ�ʵ���������������

	public SendScreenImg() {
		this.serverPort = SendScreenImg.DEFAULT_SERVER_PORT;
		try {
			serverSocket = new ServerSocket(this.serverPort);
			serverSocket.setSoTimeout(86400000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		rect = new Rectangle(screen);
	}

	public void changeServerPort(int serverPort) {
		if (this.serverPort == serverPort)
			return;
		this.serverPort = serverPort;
		try {
			this.serverSocket.close();
		} catch (Exception e) {
		}
		try {
			serverSocket = new ServerSocket(this.serverPort);
			serverSocket.setSoTimeout(86400000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public int getServerPort() {
		return this.serverPort;
	}

	public void run() {
		while (true) {
			try {
				System.out.println("�ȴ���ս�����Ϣ");
				socket = serverSocket.accept();
				zip = new ZipOutputStream(new DataOutputStream(socket.getOutputStream()));
				zip.setLevel(9);// Ϊ����� DEFLATED ��Ŀ����ѹ������ ѹ������ (0-9)
				try {
					img = robot.createScreenCapture(rect);
					zip.putNextEntry(new ZipEntry("test.jpg"));
					ImageIO.write(img, "jpg", zip);
					if (zip != null)
						zip.close();
					System.out.println("���ضˣ�connect");
				} catch (IOException ioe) {
					System.out.println("���ضˣ�disconnect");
				}
			} catch (IOException ioe) {
				System.out.println("����1");
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
}

class OperateWindow implements Runnable {

	public static final int DEFAULT_SERVER_PORT = 30012;
	private int serverPort;
	private ServerSocket serverSocket;
	private Robot robot;

	public OperateWindow() {
		this.serverPort = OperateWindow.DEFAULT_SERVER_PORT;
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
			this.serverSocket.setSoTimeout(86400000);// ////
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void changeServerPort(int serverPort) {
		if (this.serverPort == serverPort)
			return;
		this.serverPort = serverPort;
		try {
			this.serverSocket.close();
		} catch (Exception e) {
		}
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
			this.serverSocket.setSoTimeout(86400000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getServerPort() {
		return this.serverPort;
	}

	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				// ��ȡ������Ϣ:120,200,InputEvent.BUTTON1_DOWN_MASK ȫ����int����
				InputStream is = socket.getInputStream();
				int r;
				String info = "";
				while ((r = is.read()) != -1) {
					info += "" + (char) r;
				}
				System.out.println(info);
				is.close();
				if (info != null) {
					String s[] = info.trim().split(",");
					if ("mouseClicked".equals(s[0].trim())) {// operateStr Model: mouseClicked,x,y,type
																// ���ڼ��ϵ����¼�����갴�²�����̧�� ����Ƶ����¡�̧�𡢵��� ����¼�����������Ϊ��˫�����Ϻ��淶
																// ���� ����˲�û��ʵ�ֵ����¼��ļ������ﱣ�� �����޸�
						int type = Integer.parseInt(s[s.length - 1].trim());
						if (s.length == 4) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
							robot.mousePress(type);
							robot.mouseRelease(type);
							System.out.println("ClientINFO:MOUSE move to " + x + "," + y + " AND execute TYPE IS click " + type);
						}
					} else if ("mousePressed".equals(s[0].trim())) {// operateStr Model: mousePressed,x,y,type
						int type = Integer.parseInt(s[s.length - 1].trim());
						if (s.length == 4) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
							robot.mousePress(type);
							System.out.println("ClientINFO:MOUSE move to " + x + "," + y + " AND execute TYPE IS press " + type);
						}
					} else if ("mouseReleased".equals(s[0].trim())) {// operateStr Model: mouseReleased,x,y,type
						int type = Integer.parseInt(s[s.length - 1].trim());
						if (s.length == 4) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
							robot.mouseRelease(type);
							System.out.println("ClientINFO:MOUSE move to " + x + "," + y + " AND execute TYPE IS release  " + type);
						}
					} else if ("mouseDragged".equals(s[0].trim())) {// operateStr Model: mouseDragged,x,y,type
						if (s.length == 4) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
							System.out.println("ClientINFO:MOUSE move to " + x + "," + y);
						}
					} else if ("mouseMoved".equals(s[0].trim())) {
						if (s.length == 3) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
							System.out.println("ClientINFO:MOUSE move to " + x + "," + y);
						}
					} else if ("keyPress".equals(s[0].trim())) {
						if (s.length == 2) {
							int keycode = Integer.parseInt(s[1]);
							robot.keyPress(keycode);
						}
					} else if ("keyRelease".equals(s[0].trim())) {
						if (s.length == 2) {
							int keycode = Integer.parseInt(s[1]);
							robot.keyRelease(keycode);
						}
					} else if ("keyTyped".equals(s[0].trim())) {
						if (s.length == 2) {
							int keycode = Integer.parseInt(s[1]);
							robot.keyPress(keycode);
							robot.keyRelease(keycode);
						}
					}
				}
			} catch (IOException e) {
				System.out.println("error1");
			}
		}
	}
}
