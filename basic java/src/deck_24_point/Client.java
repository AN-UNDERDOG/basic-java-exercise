package deck_24_point;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import deck_24_point.client_ui;
import javafx.application.Application;
import javafx.stage.Stage;
import deck_24_point.Server;

import deck_24_point.ClientThread;

public class Client{

	public static void main(String[] args) {		
		try {
			String ipstr = "169.254.198.49";
			//�������ӵ�ָ��ip��socket
			byte[] ipbytes = Server.getIpBytes(ipstr);
			Socket socket = new Socket(InetAddress.getByAddress(ipbytes), 9989);
			client_ui.socket = socket;
			System.out.println("connect to server successfully...socket:"+socket.toString());
			//System.out.println("in play client:"+client.toString());
			//����һ�����߳���������Ϣ��������
			//ClientThread thread=new ClientThread(/*client,*/ socket);
			//thread.start();
			//Thread.sleep(2000);

            //���߳�����������Ϣ			
			//�����������ָ��
			//�����Ϸ���͵���Ϣ����ҵ����󣬱�����Ҫһ���ƣ��������̯�Ƶ�����		
			client_ui.play(socket);
			//Application.launch(client);
			
	    }catch(Exception e){
	    	System.out.println("�������쳣");
	    }
	}


}