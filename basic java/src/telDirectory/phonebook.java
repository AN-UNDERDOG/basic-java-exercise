package telDirectory;


import javafx.util.Callback;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import telDirectory.Person;

public class phonebook extends Application{
	//��ߵĿ������
	VBox ctrVBox = new VBox();

	//����ӺͲ鿴��̨��������
	TextField nametf = new TextField();
	TextField teltf = new TextField();
	TextArea noteta = new TextArea();
	
	//�����ұߵİ�ť���
	GridPane kbGridPane = new GridPane();
	
	//������ʾ��¼�ı�
	TableView<Person> table = new TableView<Person>();
	//������ʾ���֣������Լ���ע������
	TableColumn<Person,String> namecol;
	TableColumn<Person,String> telcol;
	TableColumn<Person,String> notecol;
	//�����Ͳ��ŵ���Ч
	MediaPlayer call_voc = new MediaPlayer(new Media("file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/dudu.mp3"));
	MediaPlayer key_voc = new MediaPlayer(new Media("file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/key_voc.mp3"));
	//���ڱ����¼
	ArrayList<Person> allrecoders = new ArrayList<Person>();
	//������ӺͲ鿴��Ϣ����̨
	Stage avstage = new Stage();
	
	//���������Ͳ����Լ������һ��
	HBox SCHBox = new HBox();
	//����������ı���
	TextField input = new TextField();
	
	//���������ļ���ַ
	String file_path = "D:\\Study\\JAVA\\My projects\\ecilpse\\2018.9\\basic java\\resources\\contacts_info.txt";
	
	@Override
	public void start(Stage stage) throws Exception {
		readStr2Person(file_path);
		HBox mainPane = new HBox();
		
		initCtrVBox(mainPane,stage);
		initKeyboard(mainPane);
		
		mainPane.setMaxWidth(600);
		mainPane.getChildren().add(ctrVBox);
		mainPane.getChildren().add(kbGridPane);
		mainPane.setStyle("-fx-background:transparent;-fx-background-color: rgba(255,255,255,0.85);" );

		Scene scene = new Scene(mainPane,500,500);
		scene.setFill(null);
		initMainStage(stage);
		stage.setScene(scene);
		
		stage.show();	
	}
	
	public void initMainStage(Stage stage) {
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/phonebook_32px.png";
		Image mainicon = new Image(iconpath);
		stage.getIcons().add(mainicon);
		stage.setTitle("phonebook");
		stage.setOnCloseRequest(ce->{
			try {
				savePersonAsStr(file_path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		stage.setMaxHeight(500);
		stage.setMaxWidth(530);
		stage.setMinHeight(450);
		stage.setMinWidth(520);
		stage.initStyle(StageStyle.TRANSPARENT);
	}
	
	//�ж�һ���¼�������Ƿ��Ѿ����ڣ����ڵı�׼�����ֺ͵绰����ͬ
	public Person isExists(Person p) {
		for(Person tmp : allrecoders) {
			if( tmp.isSame(p) ) {
				return tmp;
			}
		}
		return null;
	}
	
	//��ʾһ����ʾ��
	public void showAlert(String message,Alert.AlertType type) {
		Alert alert = new Alert(type);
		if(message.startsWith("you are calling") ) {
			alert.setOnShowing(e->{
				call_voc.play();
				call_voc.setCycleCount(6);
				call_voc.seek(Duration.ZERO);
			});
		}
		
		alert.setOnCloseRequest(e->{
			call_voc.stop();
		});
		alert.setWidth(200);
		alert.setHeight(80);
		alert.setHeaderText(message);
		alert.showAndWait();
		
	}
	
	//���������ַ����ķ�ʽд���ļ�
	public void savePersonAsStr(String file_path) throws IOException {
		//DataOutputStream ws = new DataOutputStream(new FileOutputStream(file_path));
		FileWriter ws = new FileWriter(file_path);
		for(Person p:allrecoders) {
			ws.write(p.toString()+"\n");//ע���ļ��ĸ�ʽ
			System.out.println("is saving " + p.toString());
		}
		
		ws.write("");
		System.out.println("write str ok");
		ws.close();
	}
	
	//���ļ��ж�ȡ�ַ������������
	public void readStr2Person(String file_path) throws IOException {
		//DataInputStream rs = new DataInputStream(new FileInputStream(file_path));
		//FileReader rs = new FileReader(file_path);
		BufferedReader rs = new BufferedReader(new FileReader(file_path));
		String recoder;
		String name,tel,note;
		while( (recoder = rs.readLine()) != null && !recoder.equals("")  ) {
			name = recoder.substring(recoder.indexOf("name:")+5,recoder.indexOf("tel:")).trim();
			tel = recoder.substring(recoder.indexOf("tel:")+4,recoder.indexOf("note:")).trim();
			note = recoder.substring(recoder.indexOf("note:")+5).trim();
			Person p = new Person(name,tel,note);
			System.out.println("person read from file:"+p.toString());
			allrecoders.add(p);
		}
		rs.close();
	}
	
	//���л����ж�����б���
	public void serializePerson(String file_path) {
		try {
			ObjectOutputStream wo = new ObjectOutputStream(new FileOutputStream(file_path));
			for(Person p:allrecoders) {
				wo.writeObject(p);
				System.out.println("is saving " + p.toString());
			}
			wo.close();
			showAlert("success to save all contacts!",Alert.AlertType.INFORMATION);
			return;
		} catch (FileNotFoundException e) {
			String message = "fail to open file "  + "while save contacts!\n"+ "filepath: " + file_path;
			showAlert(message,Alert.AlertType.ERROR);
			return;
		} catch (IOException e) {
			String message = "IO errors occurs while save contacts!\n"+ "filepath: " + file_path;
			showAlert(message,Alert.AlertType.ERROR);
			return;
		}finally{
			//
		}
	}

	//���ļ��з����л�����
	public void deSerializePerson(String file_path) throws FileNotFoundException, IOException, ClassNotFoundException {
		try {
			ObjectInputStream ro = new ObjectInputStream(new FileInputStream(file_path));
			if( ro.available() == 0 ) {
				System.out.println("�ļ�Ϊ�գ�");
				return;
			}
			while(ro.readBoolean()) {
				try {
					Person p = (Person)ro.readObject();
					allrecoders.add(p);
				} catch (ClassNotFoundException e) {
					String message = "can't   read contacts correctly!\n"+ "filepath: " + file_path;
					showAlert(message,Alert.AlertType.ERROR);
					return;
				}
			}
			
			ro.close();
			return;
		} catch (IOException e) {
			String message = "IO errors occurs while read contacts!\n"+ "filepath: " + file_path;
			showAlert(message,Alert.AlertType.ERROR);
			e.printStackTrace();
			return;
		}
	}
	
	//���ݶ����б���һ��ObservableList<Person>
	public ObservableList<Person> getInfoList(ArrayList<Person> recoders){
		ObservableList<Person> infodata = FXCollections.observableArrayList();
		for(Person p : recoders) {
			infodata.add(p);
		}
		
		return infodata;
	}
	
	//�������Ƿ�ϸ�
	public boolean isTelLegal(String tel) {
		if(tel.length() != 11 || !tel.startsWith("1")) {
			return false;
		}
		for(int i = 0 ; i < tel.length() ; i++) {
			if( !Character.isDigit(tel.charAt(i)) ) {
				return false;
			}
		}
		return true;
	}
	
	//�����������ֺ͵绰�����Ƿ�Ϸ�
	public boolean isNameTelLegal() {
		boolean err = false;
		String errmessage = "";
		if(nametf.getText().isEmpty()) {
			err = true;
			errmessage += "no name,please enter name!\n";
		}
		if( !isTelLegal(teltf.getText()) ) {
			err = true;
			errmessage += "tel illegal,please enter right tel!\n";
		}
		if(err) {
			showAlert(errmessage,Alert.AlertType.ERROR);
			return false;
		}
		
		return true;
	}
	
	//��ʼ��avstage�ı�ǩ
	public void initAVStageLabel(Label label) {
		label.setMinWidth(65);
	    label.setTextFill(Color.BLACK);
	}
	
	//��ʼ��avstage��TextField
	public void initAVStageTF(TextField tf,String prom) {
		tf.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		tf.setMinWidth(80);
		tf.setPromptText(prom);
	}
	
	//��ʼ��avstage�İ�ť
	public void initAVStageBtn(Button btn) {
		if(btn.getText().equals("cancel") ) {
			btn.setOnAction(ce->{
				avstage.close();
			});
		}else if( btn.getText().equals("save") ) {
			btn.setOnAction(se->{
				if( !isNameTelLegal() ) {
					return;
				}
				Person p = new Person(nametf.getText(),teltf.getText(),noteta.getText());
				Person tmp;
				if( ( tmp = isExists(p) ) != null ) {
					tmp.setName(p.getName());
					tmp.setTel(p.getTel());
					tmp.setNote(p.getNote());
					System.out.println("already exists");
				}else {
					allrecoders.add(p);
					System.out.println(p.toString());
				}
				table.setItems(getInfoList(allrecoders));
				avstage.close();
			});
		}
	}
	
	//��ʼ�������̨
	public void initAVStage(Stage stage) {
		GridPane addpane = new GridPane();
		//����ӺͲ鿴��̨��ı�ǩ
		Label namelabel = new Label("name:");
		Label tellabel = new Label("tel:");
		Label notelabel = new Label("note:");
		
		initAVStageLabel(namelabel);
		initAVStageLabel(tellabel);
		initAVStageLabel(notelabel);

		initAVStageTF(nametf,"name");
		initAVStageTF(teltf,"11 digits tel");
		
		noteta.setMaxHeight(60);
		noteta.setPrefColumnCount(25);
		noteta.setPrefRowCount(15);
		noteta.setWrapText(true);
		noteta.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		noteta.setPromptText("note");
		
		Button cancel = new Button("cancel");
		Button save = new Button("save");
		initAVStageBtn(cancel);
		initAVStageBtn(save);
		
		cancel.setPadding(new Insets(10,12,10,12));
		save.setPadding(new Insets(10,20,10,20));
	
		addpane.setPadding(new Insets(5,5,5,5));
		addpane.setVgap(5);
		addpane.setHgap(30);
		addpane.add(namelabel,0,0);
		addpane.add(nametf, 1, 0);
		addpane.add(tellabel, 0, 1);
		addpane.add(teltf, 1, 1);
		addpane.add(notelabel, 0, 2);
		addpane.add(noteta, 1, 2);
		addpane.add(cancel, 0, 3);
		addpane.add(save, 1, 3);
		addpane.setStyle("-fx-background:transparent;-fx-background-color: rgba(255,255,255,0.85);" );
		Scene scene = new Scene(addpane,250,320);
		
		scene.setFill(null);
		stage.setScene(scene);
		stage.setTitle("add person");
		stage.setMaxHeight(340);
		stage.setMaxWidth(270);
		stage.setMinHeight(340);
		stage.setMinWidth(270);
		stage.initStyle(StageStyle.TRANSPARENT);

	}
	
	//���tableview�е�һ���У�����field����ָ�����а����ݵ��ĸ��ֶ�
	public TableColumn<Person,String> getInfoCol(String field,double width,double height){
		TableColumn<Person,String> col = new TableColumn<Person,String>(field);
		col.setSortType(TableColumn.SortType.DESCENDING);
		col.setMinWidth(width);
		col.setMaxWidth(height);
		col.setVisible(true);
		col.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
		col.setCellValueFactory(new PropertyValueFactory<>(field));
		
		col.setOnEditCommit((CellEditEvent<Person,String> event)->{
			TablePosition<Person,String> pos = event.getTablePosition();
			String newvalue = event.getNewValue();
			int row = pos.getRow();
			Person p = event.getTableView().getItems().get(row);
			if(field.equals("name") ) {
				p.setName(newvalue);
			}else if(field.equals("tel") ) {
				p.setTel(newvalue);
			}else if(field.equals("note") ) {
				p.setNote(newvalue);
			}
			
		});
		
		return col;
	}
	
	//��ʼ��ÿһ��
	public void initAllCols() {
		namecol = getInfoCol("name",50,50);
		telcol = getInfoCol("tel",85,85);
		notecol = getInfoCol("note",110,110);
	}
	
	//����tableview����ʾ�Ѿ���ӵļ�¼
	@SuppressWarnings("unchecked")
	public void  initInfoTable() {
		initAllCols();
		
		table.getSelectionModel().selectedItemProperty().addListener(e->{
			Person p = table.getSelectionModel().getSelectedItem();
			if(p == null)
				return;
			System.out.println("selected "+p.toString() );
		});
		table.setOnMouseClicked(e->{
			
		});
		
		table.setItems(getInfoList(allrecoders));
		//table.setSelectionModel(null);
		table.setFixedCellSize(28);
		table.setMaxWidth(250);
		table.setMinWidth(200);
		table.setMaxHeight(400);
		table.setEditable(true);
		table.setMaxHeight(300);
		table.getColumns().addAll(namecol,telcol,notecol);
		table.setStyle("-fx-background:transparent;-fx-background-color: rgba(255,255,255,0.85);");
	}

	//��ʼ��advbtns
	public void initADVBtns(HBox hb,Stage stage) {
		String[] advstr = {"del","add","view","close"};
		//��ߵ�add.delete,view��ť
		Button[] advbtns = new Button[advstr.length];
		
		for(int i = 0; i < advstr.length ; i++) {
			advbtns[i] = new Button(advstr[i]);
			advbtns[i].setScaleX(1.2);
			advbtns[i].setScaleY(1.2);
			hb.getChildren().add(advbtns[i]);
			if(advstr[i].equals("del")) {
				advbtns[i].setOnAction(e->{
					Person p = table.getSelectionModel().getSelectedItem();
					if(p == null ) {
						return;
					}
					allrecoders.remove(p);
					table.setItems( getInfoList( allrecoders ) );
					System.out.println("removed " + p.toString());
				});
			}
			if(advstr[i].equals("add")) {
				advbtns[i].setOnAction(e->{
					avstage.getIcons().clear();
					String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/useradd_32px.png";
					Image icon = new Image(iconpath);
					avstage.getIcons().add(icon);
					nametf.setText("");
					teltf.setText("");
					noteta.setText("");
					avstage.show();
				});
			}
			if(advstr[i].equals("view")) {
				advbtns[i].setOnAction(e->{
					Person p = table.getSelectionModel().getSelectedItem();
					if(p == null)
						return;
					avstage.getIcons().clear();
					String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/userinfo_32px.png";
					Image icon = new Image(iconpath);
					avstage.getIcons().add(icon);
					nametf.setText(p.getName());
					teltf.setText(p.getTel());
					noteta.setText(p.getNote());
					
					avstage.show();
					
					System.out.println("view:"+p.toString() );
				});
			}
			if(advstr[i].equals("close")) {
				advbtns[i].setOnAction(e->{
					stage.close();
				});
			}
			
		}
	}
	
	public HBox getADVHBox(Stage stage) {
		HBox advHBox = new HBox();
		
		initADVBtns(advHBox,stage);
		advHBox.setSpacing(25);
		advHBox.setMinHeight(40);
		advHBox.setAlignment(Pos.CENTER);
		
		return advHBox;
	}
	
	//���������ǩ�������¼�
	public void search() {
		table.setItems( getInfoList( getSortedRecodersByInput( input.getText() ) ) );
		System.out.println("you are searching...");
	}
	
	//������ű�ǩ�������¼�
	public void call() {
		System.out.println("you are calling...");
		Person p = table.getSelectionModel().getSelectedItem();
		if(p == null ) {
			return;
		}
		showAlert("you are calling " + p.getName() + "\n" + p.getTel(),Alert.AlertType.INFORMATION);
	}
	
	//��ʼ�������Ͳ������õ��ı�ǩ
	public void initSCLabel(Label label,String name,String ico_path) {
		Image ico = new Image(ico_path);
		label.setGraphic(new ImageView(ico));
		label.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,15));
		label.setMaxWidth(34);
		label.setMinWidth(34);
		label.setPadding(new Insets(3,3,3,3));
		if(name.equals("search")) {
			label.setOnMouseClicked(e->search());
		}
		if(name.equals("call")) {
			label.setOnMouseClicked(e->{
				
				call();
			});
		}
	}
	
	//�������������ݶ����еļ�¼��������
	public ArrayList<Person> getSortedRecodersByInput(String key){
		ArrayList<Person> sortedrecoders = new ArrayList<Person>();
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		int index1 = -1,index2 = -1,index3 = -1;		//���ڱ����������ڼ�¼�е�λ��
		int tmp = -1;
		//��������ȫ�����֣����պ����������
		for(Person p : allrecoders) {
			index1 = p.getName().indexOf(key);
			index2 = p.getTel().indexOf(key);
			index3 = p.getNote().indexOf(key);
			if( index1 + index2 + index3  == -3) {
				continue;
			}else if(index1 + index2 >= -1 || index3 >= 0) {
				indexs.add( allrecoders.indexOf(p) );
			}
			System.out.println("compare result:\nindex1:"+index1+"   index2:"+index2+"\n"+p.toString());
		}
		Collections.sort(indexs);
		for(int i : indexs) {
			sortedrecoders.add(allrecoders.get(i) );
		}

		
		return sortedrecoders;
	}
	
	//��ʼ��������ı���
	public void initSearchTF() {
		input.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,18));
		input.setPromptText("enter name or tel");
		input.setPrefColumnCount(20);
		input.setMaxWidth(200);
		input.setMinWidth(150);
		input.setScaleY(1.2);
		input.textProperty().addListener(e->{
			table.setItems( getInfoList( getSortedRecodersByInput( input.getText() ) ) );
			if( input.getText().isEmpty() ) {
				return;
			}
			System.out.println("input value:"+input.getText());
		});
	}
	
	public void initSCHBox() {
		//�����ı�ǩ
		Label searchlabel = new Label();
		//���ŵı�ǩ
		Label calllabel = new Label();
		String search_ico_path = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/search_24px.png";
		String call_ico_path = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/call_32px.png";
		
		//��ʼ�������Ͳ��ű�ǩ
		initSCLabel(searchlabel,"search",search_ico_path);
		initSCLabel(calllabel,"call",call_ico_path);
		//��ʼ��������ı���
		initSearchTF();

		SCHBox.getChildren().add(searchlabel);
		SCHBox.getChildren().add(input);
		SCHBox.getChildren().add(calllabel);
	}
	
	//�����ߵĿ������
	public void initCtrVBox(Pane pane,Stage stage) {
		initInfoTable();
		initAVStage(avstage);
		initSCHBox();
		
		Insets inset = new Insets(5,5,0,3);
		ctrVBox.setPadding(inset);
		ctrVBox.setSpacing(10);
		ctrVBox.setAlignment(Pos.TOP_CENTER);
		ctrVBox.getChildren().add(SCHBox);
		ctrVBox.getChildren().add(table);
		ctrVBox.getChildren().add(getADVHBox(stage));
		ctrVBox.setStyle("-fx-background:transparent;-fx-background-color: rgba(255,255,255,0.85);");
	}
	
	//���ð�ť����ʽ
	public void setBtnStyle(Button btn) {
		DropShadow shadow = new DropShadow();
		btn.setPadding(new Insets(5,10,15,40));
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,20));
		btn.setTextAlignment(TextAlignment.CENTER);
	}
	
	//���ð�ť���¼�
	public void keyPressed(ActionEvent e) {
		key_voc.play();
		key_voc.seek(Duration.ZERO);
		input.setText(input.getText()+((Button) e.getSource()).getText());
		System.out.println("you are pressing " + ((Button) e.getSource()).getText() +"...");
	}
	
	//ɾ���¼�
	public void delete(ActionEvent e) {
		Button srcbtn = (Button) e.getSource();
		if(srcbtn.getText() == "D") {
			//�����D����ֻɾ��һ���ַ�,��input�Ѿ�û���ַ�ʱҪ���⴦��
			int len = input.getText().length();
			if(len == 0) {
				len = 0;
			}else {
				len = len - 1;
			}
			input.setText(input.getText(0 , len));
		}else if(srcbtn.getText() == "C") {
			//�����C����ɾ����������ı����ڵ������ַ�
			input.setText("");
		}
		
	}
	
	//��ʼ���ұߵİ���
	public void initAllBtns(GridPane pane) {
		call_voc.setCycleCount(2);
		key_voc.setStartTime(Duration.ZERO);
		key_voc.setStopTime(Duration.millis(500));
		String[] keynames = {"1","2","3",
				 "4","5","6",
				 "7","8","9",
				 "C","0","D"};
		//�ұߵİ���
		Button[] allbtns = new Button[12];
		
		for(int i = 0;i < keynames.length;i++) {
			allbtns[i] = new Button(keynames[i]);
			setBtnStyle(allbtns[i]);
			if(!keynames[i].equals("D") && !keynames[i].equals("C"))
				allbtns[i].setOnAction((e)->keyPressed(e));
			else if(keynames[i].equals("D") || keynames[i].equals("C"))
				allbtns[i].setOnAction((e)->delete(e));
			pane.add(allbtns[i],i%3,i/3);
			if(keynames[i] == "!") {
				allbtns[i].setVisible(false);
			}
		}
	}
	
	//��ʼ���������������
	public void  initKeyboard(Pane pane) {
		Insets s = new Insets(15,5,15,5);
		
		kbGridPane.setPadding(s);
		kbGridPane.setHgap(4);
		kbGridPane.setVgap(4);
		kbGridPane.setAlignment(Pos.TOP_RIGHT);
		//��ʼ������
		initAllBtns(kbGridPane);

	}
	
	public static void main(String[] arg) throws Exception {
		Application.launch(arg);
		
	}	
}