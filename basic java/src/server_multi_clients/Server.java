package server_multi_clients;

import server_multi_clients.ServerThread;
import server_multi_clients.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server{
	
	public static void main(String[] args) throws IOException {
		List<User> user_list = new ArrayList<User>();
		
		ServerSocket serversocket = new ServerSocket(9999);
		System.out.println("server is starting...");
		
		//ѭ�������ͻ�������
		while(true) {
			Socket socket = serversocket.accept();
			//ÿ����һ���߳̾�����һ���û�
			User user = new User("user"+Math.round(Math.random()*100),socket);
			System.out.println(user.getName()+"is signing...");
			user_list.add(user);
			
			//����һ���½��̣�������Ϣ��ת��
			ServerThread thread = new ServerThread(user, user_list);
			thread.start();
			
			
		}
		
	}
	
	
	
}