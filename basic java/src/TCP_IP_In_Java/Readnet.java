package TCP_IP_In_Java;
import  time.time;
import java.io.*;
import java.net.*;


public class Readnet{
	
	public static void main(String[] arg) throws IOException {
		byte[] buff = new byte[1024];
		Socket readsocket = null;
		InputStream instr = null;
		OutputStream outstr = null;
		boolean cont = true;
		
		String dns = "https://cn.bing.com/";
		int port = 80;
		
		try {
			readsocket = new Socket(dns,port);
			instr = readsocket.getInputStream();
			outstr = readsocket.getOutputStream();
			System.out.println("socket�����ɹ���");
		}catch (Exception e){
			System.out.println("�ļ�û���ҵ���");
			System.exit(1);
		}
		
		String request = "GET /./sa/simg/SharedSpriteDesktopRewards_022118.png HTTP/1.1";
		outstr.write(request.getBytes(),0,request.length());
		
		//ѭ����ȡ����
		while(cont) {
			System.out.println("now is "+time.getTime()+" "+time.getDate()+" reading the data form net!");
			try {
				int n = instr.read(buff);
				System.out.write(buff,0,n);
				
			}catch (Exception e) {
				cont = false;
			}
			
			//�Ͽ�����
			try {
				instr.close();
			}catch (Exception e) {
				System.out.println("����ر�ʧ�ܣ�");
				System.exit(1);
			}
		}
	}

	
}