package server_multi_clients;




import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import server_multi_clients.ClientThread;

public class Client{
	/*
	 * �ͻ���������������󣬱���������������Ϣ�ģ������������߳�����������Ϣ
	 */
	
	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket("localhost", 9999);
			//����һ���߳̽�����Ϣ��������
			ClientThread thread=new ClientThread(socket);
			thread.start();
            //���߳�����������Ϣ	
			//br�������ӿ���̨��ȡ��Ϣ��������Ϣд�뵽����������ӵ�socket�������
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out=new PrintWriter(socket.getOutputStream());
			while(true)
			  {
			   String s=br.readLine();
			   out.println(s);
		//         out.write(s+"\n");
			   out.flush();
			  }
	    }catch(Exception e){
	    	System.out.println("�������쳣");
	    }
	}


}