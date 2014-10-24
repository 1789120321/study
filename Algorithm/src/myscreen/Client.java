package myscreen;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Client {

	public static void main(String args[]) {
		ServerGUI sendOrder = new ServerGUI("192.168.1.199", "PLC��λ��ʵʱ���");// ����ص��Ե�ip��ַ
		WriteGUI catchScreen = new WriteGUI(sendOrder);
		catchScreen.changePort(30009);// ���ڿ����޸Ļ�ȡ������Ļ��ϢҪ���ʵĶ˿ں�
		new Thread(catchScreen).start();// �����߳�
	}
}

class WriteGUI extends Thread {

	public static final int DEFAULT_PORT = 30011;
	private int port;
	private ServerGUI rec;

	public WriteGUI(ServerGUI rec) {
		this.port = WriteGUI.DEFAULT_PORT;
		this.rec = rec;
	}

	public void changePort(int port) {
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}

	public void run() {
		while (rec.getBoo()) {
			System.out.println((System.currentTimeMillis() / 1000));
			Socket socket = null;
			try {
				socket = new Socket(rec.getIP(), this.port);
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				ZipInputStream zis = new ZipInputStream(dis);
				Image image = null;
				try {
					zis.getNextEntry();// ��ȡ��һ�� ZIP �ļ���Ŀ��������λ������Ŀ��ݵĿ�ʼ��
					image = ImageIO.read(zis);// ��ZIP��ת��ΪͼƬ
					rec.jlabel.setIcon(new ImageIcon(image));
					rec.scroll.setViewportView(rec.jlabel);
					rec.validate();
				} catch (IOException ioe) {
				}
				try {
					// dis.close();
					zis.close();
				} catch (Exception e) {
				}
				try {
					TimeUnit.MILLISECONDS.sleep(50);// ����ͼƬ���ʱ��
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			} catch (IOException ioe) {
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}

class SendOperate extends Thread {

	public static int DEFAULT_PORT = 30012;
	private String ip;
	private int port;// 30012
	private String operateStr;

	public SendOperate(String ip, String operateStr) {
		this.ip = ip;
		this.port = SendOperate.DEFAULT_PORT;
		this.operateStr = operateStr;
	}

	public void setOperateStr(String operateStr) {
		this.operateStr = operateStr;
	}

	public void changePort(int port) {
		this.port = port;
	}

	public boolean changeIP(String ip) {
		if (UtilServer.checkIp(ip)) {
			this.ip = ip;
			return true;
		}
		return false;
	}

	public int getPort() {
		return this.port;
	}

	public String getIP() {
		return this.ip;
	}

	public void run() {
		if (this.operateStr == null || this.operateStr.equals("")) {
			return;
		}
		// if(this.operateStr.trim().startsWith("mouseMoved")){
		// return;
		// }
		try {
			Socket socket = new Socket(this.ip, this.port);
			OutputStream os = socket.getOutputStream();
			os.write((this.operateStr).getBytes());
			os.flush();
			socket.close();
			System.out.println("INFO: ��SendOperate��ip=" + this.ip + ",port=" + this.port + ",operateStr=\"" + this.operateStr + "\".");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class UtilServer {

	public static boolean checkIp(String ip) {
		if (ip == null)
			return false;
		String[] dps = ip.split("\\.");
		if (dps.length != 4 && dps.length != 6)
			return false;
		boolean isIp = true;
		for (int i = 0; i < dps.length; i++) {
			try {
				int dp = Integer.parseInt(dps[i]);
				if (dp > 255 || dp < 0) {
					throw new RuntimeException("error IP");
				}
			} catch (Exception e) {
				isIp = false;
				break;
			}
		}
		return isIp;
	}
}

class ServerGUI extends JFrame {

	private static final long serialVersionUID = 2273190419221320707L;
	JLabel jlabel;
	JScrollPane scroll;
	private String ip;
	private int port;
	private boolean boo;

	public boolean getBoo() {
		return this.boo;
	}

	public int getPort() {
		return this.port;
	}

	public void changePort(int port) {
		this.port = port;
	}

	public String getIP() {
		return this.ip;
	}

	public boolean changeIP(String ip) {
		if (UtilServer.checkIp(ip)) {
			this.setTitle(this.getTitle().replace(this.ip, ip));
			this.ip = ip;
			return true;
		}
		return false;
	}

	protected ServerGUI(String IP, String sub) {
		this.boo = true;
		this.ip = IP;
		this.port = SendOperate.DEFAULT_PORT;
		this.setTitle("Զ�̼��--IP:" + IP + "--����:" + sub);
		this.jlabel = new JLabel();
		this.scroll = new JScrollPane();
		this.scroll.add(this.jlabel);
		scroll.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				int x = (int) e.getX() + (int) ServerGUI.this.scroll.getHorizontalScrollBar().getValue();
				int y = (int) e.getY() + (int) ServerGUI.this.scroll.getVerticalScrollBar().getValue();
				// int type = e.getModifiers();//e.BUTTON1_MASK �� e.BUTTON2_MASK
				// �� e.BUTTON3_MASK
				String operateStr = "mousePressed," + x + "," + y + "," + e.getModifiers();

				SendOperate sender = new SendOperate(ServerGUI.this.ip, (operateStr));
				sender.changeIP(ServerGUI.this.ip);// ͬ��ip
				sender.changePort(ServerGUI.this.port);// ͬ��port
				sender.start();
			}

			// @SuppressWarning(""static-access"");
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				int x = (int) e.getX() + (int) ServerGUI.this.scroll.getHorizontalScrollBar().getValue();
				int y = (int) e.getY() + (int) ServerGUI.this.scroll.getVerticalScrollBar().getValue();
				// int type = e.getModifiers();//e.BUTTON1_MASK �� e.BUTTON2_MASK
				// �� e.BUTTON3_MASK
				String operateStr = "mouseReleased," + x + "," + y + "," + e.getModifiers();

				SendOperate sender = new SendOperate(ServerGUI.this.ip, (operateStr));
				sender.changeIP(ServerGUI.this.ip);// ͬ��ip
				sender.changePort(ServerGUI.this.port);// ͬ��port
				sender.start();
			}
		});
		scroll.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				int x = (int) e.getX() + (int) ServerGUI.this.scroll.getHorizontalScrollBar().getValue();
				int y = (int) e.getY() + (int) ServerGUI.this.scroll.getVerticalScrollBar().getValue();
				String operateStr = "mouseDragged," + x + "," + y + "," + e.getModifiers();

				SendOperate sender = new SendOperate(ServerGUI.this.ip, operateStr);
				sender.changeIP(ServerGUI.this.ip);// ͬ��ip
				sender.changePort(ServerGUI.this.port);// ͬ��port
				sender.start();
			}

			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				int x = (int) e.getX() + (int) ServerGUI.this.scroll.getHorizontalScrollBar().getValue();
				int y = (int) e.getY() + (int) ServerGUI.this.scroll.getVerticalScrollBar().getValue();
				String operateStr = "mouseMoved," + x + "," + y;

				SendOperate sender = new SendOperate(ServerGUI.this.ip, (operateStr));
				sender.changeIP(ServerGUI.this.ip);// ͬ��ip
				sender.changePort(ServerGUI.this.port);// ͬ��port
				sender.start();
			}
		});
		this.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				String operateStr = "keyPress," + e.getKeyCode();

				SendOperate sender = new SendOperate(ServerGUI.this.ip, (operateStr));
				sender.changeIP(ServerGUI.this.ip);// ͬ��ip
				sender.changePort(ServerGUI.this.port);// ͬ��port
				sender.start();
			}

			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				String operateStr = "keyReleas," + e.getKeyCode();

				SendOperate sender = new SendOperate(ServerGUI.this.ip, (operateStr));
				sender.changeIP(ServerGUI.this.ip);// ͬ��ip
				sender.changePort(ServerGUI.this.port);// ͬ��port
				sender.start();
			}

			public void keyTyped(KeyEvent e) {
				// super.keyTyped(e);
			}
		});
		this.add(scroll);
		this.setAlwaysOnTop(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(100, 75, (int) screenSize.getWidth() - 200, (int) screenSize.getHeight() - 150);
		// this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// �رմ��岻���κ���
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				boo = false;
				ServerGUI.this.dispose();
				System.out.println("����ر�");
				System.gc();
				System.exit(0);
			}
		});
		this.setVisible(true);
		this.validate();
	}
}