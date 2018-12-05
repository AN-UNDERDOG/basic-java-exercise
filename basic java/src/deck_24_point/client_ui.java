package deck_24_point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.event.Event;

public class client_ui extends Application{

	static Socket socket;		//���������������ͨ�ŵ�socket
	VBox main_pane = new VBox();
	/*playerInfoHBox _player1  = new playerInfoHBox();//�Է����
	playerInfoHBox _player2 = new playerInfoHBox();//�Լ�
	HBox player1 = _player1.playerHBox;
	HBox player2 = _player2.playerHBox;*/
	PrintWriter send2Server;
	
	String msg = "player:msg from ui";
	HBox player1 = new HBox();
	cardsHBox carddesk1 = new cardsHBox();
	HBox player2 = new HBox();
	cardsHBox carddesk2 = new cardsHBox();
	//���������Ϣ�ı�ǩ�Ͱ�ť
	//��ִ�й��캯��ʱʱ����ִ�������ģ������ں��������Ҳ���������쳣
	Label playerName1;
	Label cardNum1;
	//Label pointNum1;
	Button one_more;
	Button submit;
    Label playerName2;
	Label cardNum2;
	Label pointNum2;
	int point;
	static int lock;
	//��������ÿ����ҵĿؼ�������������Ϸ��Ϣ
	ArrayList<Node> widgetslist1 = new ArrayList<Node>();
	ArrayList<Node> widgetslist2 = new ArrayList<Node>();
	
	BufferedReader br;
	
	String cardpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/card/";
	
	//init��������start����֮ǰ��ִ��
	public void init() {
		//System.out.println("in init   this:"+this.toString());
		
	}
	
	
	public client_ui() {
		
		//System.out.println("in client_ui   this:"+this.toString());
		lock = 0;//Ϊ0����one_more ��ã�Ϊ��submit���
		
		playerName1 = new Label("player: -1" );
		cardNum1 = new Label("����:0");
		//pointNum1 = new Label("����: 0");
		playerName2 = new Label("player: " + Math.round(Math.random()*100));
		cardNum2 = new Label("����:0");
		pointNum2 = new Label("����: 0");
		one_more = new Button("one more");
		submit = new Button("submit");
		point = 0;

		widgetslist1.add(0, playerName1);
		widgetslist1.add(1, cardNum1);
		//widgetslist1.add(2, pointNum1);
			
		widgetslist2.add(0, playerName2);
		widgetslist2.add(1, cardNum2);
		widgetslist2.add(2, pointNum2);
		widgetslist2.add(3, one_more);
		widgetslist2.add(4, submit);
							
		initPlayerHBox(player1);
		initPlayerHBox(player2);
			
		installWidgets(player2,widgetslist2);
		installWidgets(player1,widgetslist1);
		try {
			this.send2Server = new PrintWriter(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		try {
			InputStream inputStream = socket.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			br = new BufferedReader(inputStreamReader);
			//System.out.println("br init success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bindBtnEvt();
		
	}
	
	public void initPlayerHBox(HBox player) {
		player.setPrefSize(300, 50);
		player.setPadding(new Insets(5,5,5,5));
		player.setStyle("-fx-background:transparent;-fx-background-color: rgba(10,10,10,0.85);" );

	}
	
	
	public void initBtn(Button btn) {
		DropShadow shadow = new DropShadow();
		btn.setPadding(new Insets(5,15,5,15));
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		btn.setTextAlignment(TextAlignment.CENTER);

	}
	
	//���ñ�ǩ����ʽ
	public void initLabel(Label label) {
		label.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		label.setMinSize(80, 33);
		label.setPadding(new Insets(2,2,2,2));
	}
	
	
	//�����еĿؼ���װ��HBox��
	public void installWidgets(HBox playerHBox, ArrayList<Node> widgets) {
		for(Node node:widgets) {
			if(widgets.indexOf(node) <= 2) {
				initLabel((Label)node);
			}
			else if(widgets.indexOf(node) <= 4) {
				initBtn((Button)node);
			}
			playerHBox.getChildren().add(node);
			HBox.setMargin(node, new Insets(5,5,5,5));
		}
		
	}
	
	//Ϊ�����ؼ����¼�
	
	public void bindBtnEvt() {
		//System.out.println("in bindBtnEvt");
		one_more.setOnAction(oe->{
			System.out.println("carddesk2:"+carddesk2.cardlist.size());
			System.out.println("carddesk1:"+carddesk1.cardlist.size());
			if(this.carddesk2.cardlist.size() >= 4) {
				System.out.println("you already have 4 cards!!!");
			}
			
			this.send2Server.println("one_more:"+this.carddesk2.cardlist.size());
			this.send2Server.flush();
			System.out.println("send one_more successfully...");
			
			try {
				System.out.println("is recieving in one_more");
	
				//����������Ϣ��һֱ�ȵ��յ��˿���Ϊֹ
				while( !msg.startsWith("card:") ) {
					msg = "";
					if(!br.ready()) {
						System.out.println("br is bit ready!");
						break;
					}
					msg = br.readLine();
					
					System.out.println("recieved from server: "+msg);
					if(msg.startsWith("card:")) {
						if(carddesk2.cardlist.size() > 4) {
							lock = 1;
							break;
						}
						int point = Integer.valueOf(msg.substring(5));
						addCard2Desk(point);
						System.out.println("you get a card with point "+ point);
					}else if(msg.equals("test")) {
						System.out.println("recieve test");
					}else if(msg.startsWith("player1Num:")){
						int num = Integer.valueOf(msg.substring(11));
						int nownum = Integer.valueOf( cardNum1.getText().substring(3));
						
						System.out.println("nownum is " + nownum);
						if(num <=  nownum) {
							continue;
						}
						this.cardNum1.setText("����:"+num);
						//carddesk1.setCardDesk(num);
						for(int i = 1; i <= num - nownum; i++) {
							addCard2Desk(-1);
						}
					}
				}
				//lock = 1;
			}catch (SocketException e) {
				showAlert("server has closed!", Alert.AlertType.WARNING);
				
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		});
			
		submit.setOnAction(se->{
			this.send2Server.println("submit:"+point);
			this.send2Server.flush();
			System.out.println("send submit successfully...");
		
					try {
						
						System.out.println("is recieving in submit");
						Thread.sleep(10);
						//����������Ϣ��һֱ�ȵ��յ����Ϊֹ
						while( !msg.startsWith("results:")/* && lock == 1*/) {
							msg = "";
							if(!br.ready()) {
								System.out.println("br is not ready");
								break;
							}
							
							msg = br.readLine();
							
							System.out.println("recieved from server: "+msg);
							if(msg.startsWith("card:")) {
								System.out.println("get a card in submit");
								/*int point = Integer.valueOf(msg.substring(5));
								System.out.println("you get a card with point "+ point);
								*/
								if(carddesk2.cardlist.size() > 4) {
									continue;
								}
								int point = Integer.valueOf(msg.substring(5));
								addCard2Desk(point);
								System.out.println("you get a card with point "+ point);
							}else if(msg.equals("test")) {
								System.out.println("recieve test");
							}else if(msg.startsWith("result:")) {
								System.out.println(msg);
								showAlert("You "+msg.substring(7) , Alert.AlertType.INFORMATION);
								break;
							}else if(msg.startsWith("player1Num:")){
								int num = Integer.valueOf(msg.substring(11));
								this.cardNum1.setText("����:"+num);
								int nownum = Integer.valueOf( cardNum1.getText().substring(3));
								
								System.out.println("nownum is " + nownum);
								if(num <=  nownum) {
									continue;
								}
								this.cardNum1.setText("����:"+num);
								//carddesk1.setCardDesk(num);
								for(int i = 1; i <= num - nownum; i++) {
									addCard2Desk(-1);
								}
							}
						}
						lock = 0;
					} catch (SocketException e) {
						showAlert("server has closed!", Alert.AlertType.WARNING);
						
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
			
		});
	}
	
	
	//��ʾһ����ʾ��
	public void showAlert(String message,Alert.AlertType type) {
		Alert alert = new Alert(type);

		alert.setWidth(200);
		alert.setHeight(80);
		alert.setHeaderText(message);
		alert.showAndWait();
			
		}

	//���������
	public void initMainPane(Pane main_pane) {
		main_pane.setMaxWidth(620);
		main_pane.setStyle("-fx-background:transparent;-fx-background-color: rgba(0,0,0,0.85);" );
	}
	
	//������̨
	public void initMainStage(Stage stage) {
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/poker_32px.png";
		Image mainicon = new Image(iconpath);
		stage.getIcons().add(mainicon);
		stage.setTitle("24Point");
		
		stage.setMaxHeight(650);
		stage.setMaxWidth(530);
		stage.setMinHeight(600);
		stage.setMinWidth(520);
		
		stage.showingProperty().addListener(showingevt->{
			
		});

	}
	
	
	//��������ϰ�װ��������
	public void installComponents(Pane main_pane, ArrayList<Pane> panelist) {
		for(Pane pane:panelist) {
			main_pane.getChildren().add(pane);
			((VBox)main_pane).setMargin(pane, new Insets(5, 10, 5,10));
		}
	}
	
	
	
	
	//�����������
	public void setPlayerName(String name) {
		System.out.println("in setPlayerName...");
		this.playerName2.setText(name);
		System.out.println("after setPlayerName...");
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//System.out.println("in start");
		initMainPane(main_pane);
		
		
		
		if(socket == null) {
			System.out.println("socket is null  socket:"+socket);
		}
		
		if(this.send2Server == null) {
			System.out.println("send2server is null");
		}
		
		ArrayList<Pane> panelist = new ArrayList<Pane>();
		panelist.add(0, player1);
		panelist.add(1, carddesk1);
		panelist.add(2, carddesk2);
		panelist.add(3, player2);
		
		installComponents(main_pane, panelist);
		
		Scene scene = new Scene(main_pane, 550, 620);
		
		scene.setFill(null);
		initMainStage(stage);
		stage.setScene(scene);
		initMainStage(stage);
		
		
		try {
			//�����Լ������ָ�����������ʽΪ player:name
			this.send2Server.println(this.playerName2.getText());
			this.send2Server.flush();

			msg = br.readLine();
			System.out.println("recieved from server: "+msg);
			if(msg.startsWith("player:")) {
				this.playerName1.setText(msg);
			}else if(msg.equals("test")) {
				System.out.println("recieve test in showingproperty");
			}else if(msg.startsWith("card:")) {
				System.out.println("get a card in showingproperty");
				int point = Integer.valueOf(msg.substring(5));
				System.out.println("you get a card with point "+ point);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Platform.setImplicitExit(true);
		//ClientThread clientthread = new ClientThread(this, client_ui.socket);
		//Platform.runLater(clientthread);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				/*
				while( !msg.startsWith("card:")) {
					System.out.println("is recieving in runlater...");
					msg = "";
					try {
						while(!br.ready()) {
							System.out.println("br is not ready!");
							break;
						}
						msg = br.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					System.out.println("recieved from server: "+msg);
					if(msg.startsWith("card:")) {
						if(carddesk2.cardlist.size() > 4) {
							lock = 1;
							break;
						}
						int point = Integer.valueOf(msg.substring(5));
						addCard2Desk(point);
						System.out.println("you get a card with point "+ point);
					}else if(msg.equals("test")) {
						System.out.println("recieve test");
					}else if(msg.startsWith("player1Num:")){
						int num = Integer.valueOf(msg.substring(11));
						int nownum = Integer.valueOf( cardNum1.getText().substring(3));
						
						System.out.println("nownum is " + nownum);
						if(num <=  nownum) {
							continue;
						}
						cardNum1.setText("����:"+num);
						//carddesk1.setCardDesk(num);
						for(int i = 1; i <= num - nownum; i++) {
							addCard2Desk(-1);
						}
					}
				}
			}*/
			
		}});
		/*Platform.runLater(new Runnable(){
			
			@Override
			public void run() {
				String msg = "";
				
				System.out.println("while recieveing msg from server... ");
				//���շ���������Ϣ,�������Ϣ�������
				
				try {
					while(!br.ready()) {
						System.out.println("br is not ready");
						Thread.sleep(100);
					}
					msg = br.readLine();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("in clientthread recieved msg: " + msg );
				if(msg.startsWith("card:")) {
					if(this.carddesk2.cardlist.size() > 4) {
						return;
					}
					int point = Integer.valueOf(msg.substring(5));
					System.out.println("carddesk:"+this.carddesk2.toString());
					this.addCard2Desk(point);
					System.out.println("you get a card with point "+ point);
					//this.playerName1.setText(msg);
				}
				
			}
		});*/
		//clientthread.start();
		
		stage.show();	
		
	}
	
	//��������ƽ��д������Լ�������һ����
	public void addCard2Desk(int cardpoint) {
		/*if(carddesk1.cardlist.size() > 4 || carddesk2.cardlist.size() > 4) {
			System.out.println("add error, more than 4!");
			return;
		}*/
		if(cardpoint == -1) {
			//�ڶԷ����������һ���Ƶı���
			Image card = new Image(cardpath+"b2fv.png");
			carddesk1.addCard(card);
			cardNum1.setText("����:"+carddesk2.cardlist.size());
		}else {
			//���Լ����������һ���ƣ����޸ĵ���
			Image card = new Image(cardpath+String.valueOf(cardpoint)+".png");
			if( carddesk2.addCard(card) ) {
				point += (cardpoint % 13 == 0? 13: cardpoint % 13);
				pointNum2.setText("����:" + point);
				cardNum2.setText("����:"+carddesk2.cardlist.size());
			}
			
		}
	}
	
	
	
	
	public static  void play(Socket socket1) throws Exception {
		//System.out.println("in play client:"+this.toString());
		socket = socket1;
		//System.out.println("in play :" + socket);
		Application.launch();	
	}
	
	
}