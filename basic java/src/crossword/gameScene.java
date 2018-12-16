package crossword;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

class GameScene extends Scene{
	private Label[] allLabels = new Label[4];
	private Button[] allBtns = new Button[3];
	private TextField[] allTFs = new TextField[4];
	private static HBox subpane = new HBox();
	private static GridPane gridpane = new GridPane();
	private static VBox mainpane = new VBox();
	ArrayList<ArrayList<String>> allnovels = new ArrayList<ArrayList<String>>();
	ArrayList<Boolean> selected = new ArrayList<Boolean>();
	int currentSelected = -1;	//��¼��ǰ��ѡ�е�С˵���±�
	int[] roleselected = new int[] {-1,-1,-1,-1};	//��¼��ǰ��ѡ�е�С˵����Ϊ������ֶε��±�
	int rightNum = 0;
	int wrongNum = 0;
	boolean hasEdit = false;
	String file_path = "D:\\Study\\JAVA\\My projects\\ecilpse\\git\\basic java\\resources\\novels.dat";
	Stage stage;
	
	public GameScene(Stage stage) {
		super(mainpane);
		this.initHBox(subpane);
		this.initGridPane(gridpane);
		this.initMainPane(mainpane);
		this.initBtns();
		this.initLabels();
		this.initTextFields();
		this.installWidgetsOnGridPane(gridpane);
		this.installWidgetsOnHBox(subpane);
		this.mainpane.getChildren().add(gridpane);
		this.mainpane.getChildren().add(subpane);
		this.mainpane.setMargin(this.subpane, new Insets(5,5,5,5));
		this.initGame();
		//this.setProblem();
		this.setFill(null);
		this.stage = stage;
		
	}
	
	public void initLabels() {
		String[] labelName = new String[] {"С˵����:", "��Ҫ��ɫ1:", "��Ҫ��ɫ2:", "��Ҫ��ɫ3:"};
		for(int i = 0; i < 4; i++) {
			allLabels[i] = new Label(labelName[i]);
			setLabelStyle(allLabels[i]);
		}
	}
	
	public void initBtns() {
		String[] btnName = new String[] {"���¿�ʼ", "�ж�", "��һ��"};
		for(int i = 0; i < 3; i++) {
			allBtns[i] = new Button(btnName[i]);
			setBtnStyle(allBtns[i]);
			if(allBtns[i].getText().equals("�ж�")) {
				this.allBtns[i].setOnAction(judgeevt->{
					if( !this.judgeAnswer() ) {
						//System.out.println("you lose!");
						//this.wrongNum++;
					}else {
						//this.rightNum += 4;
					}
					((Button)judgeevt.getSource()).setDisable(true);
				});

			}
		}
	}
	
	public Button getJudgeBtn() {
		return this.allBtns[1];
	}
	
	public Button getNextBtn() {
		return this.allBtns[2];
	}
	
	public void initTextFields() {
		for(int i = 0; i < allTFs.length; i++) {
			allTFs[i] = new TextField();
			this.allTFs[i].textProperty().addListener(extevt->{
				this.hasEdit = true;
			});
			this.setTextFieldStyle(allTFs[i], "");
		}
	}
	
	
	//����TextField��ʽ
	public void setTextFieldStyle(TextField tf,String prom) {
		tf.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		tf.setMinWidth(80);
		tf.setPromptText(prom);
	}
	
	//���ñ�ǩ����ʽ
	public void setLabelStyle(Label label) {
		label.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		label.setMinSize(50, 25);
	}
	
	public void setBtnStyle(Button btn) {
		DropShadow shadow = new DropShadow();
		btn.setPrefSize(70, 30);
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		btn.setTextAlignment(TextAlignment.CENTER);

	}
	
	
	public void initGridPane(GridPane gp) {
		gp.setPadding(new Insets(10,10,10,10));
		gp.setHgap(4);
		gp.setVgap(4);
		gp.setAlignment(Pos.TOP_RIGHT);
	}
	
	
	public void initHBox(HBox pane) {
		pane.setMinSize(240, 40);
		pane.setStyle("-fx-background:transparent;-fx-background-color: rgba(10,10,10,0.85);" );

	}
	
	//���������
	public void initMainPane(Pane main_pane) {
		main_pane.setPadding(new Insets(20, 20, 20, 20));
		main_pane.setPrefSize(280, 300);
		main_pane.setMinSize(280, 300);
		main_pane.setStyle("-fx-background:transparent;-fx-background-color: rgba(0,0,0,0.85);" );
	}
	
	public void installWidgetsOnGridPane(GridPane gp) {
		for(int i = 0; i < allLabels.length; i++) {
			gp.add(allLabels[i], 0, i);
			gp.setMargin(allLabels[i], new Insets(5,1,5,1));
			gp.add(allTFs[i], 1, i);
			gp.setMargin(allTFs[i], new Insets(5,10,5,1));
		}
	}
	
	private void installWidgetsOnHBox(Pane pane) {
		for(int i = 0; i < allBtns.length; i++) {
			pane.getChildren().add(allBtns[i]);
			((HBox) pane).setMargin(allBtns[i], new Insets(10,10,10,10));
		}
	}
	
	public Button getReplayBtn() {
		return this.allBtns[0];
	}
	
	
	public void resetTfs() {
		for(int i = 0; i < this.allTFs.length; i++) {
			this.allTFs[i].setStyle("-fx-text-fill: black; -fx-font-size: 10px;");
		}
	}
	
	public void initFlags() {
		/*
		 * ����Ϸ���б�ǵı�־����ȷ�ʹ��������
		 */
		this.currentSelected = -1;
		//this.selected = new ArrayList<Boolean>();
		this.currentSelected = -1;	//��¼��ǰ��ѡ�е�С˵���±�
		for(int i = 0; i < this.roleselected.length; i++) {
			this.roleselected[i] = -1;
		}
		
		this.rightNum = 0;
		this.wrongNum = 0;
	}
	
	
	public void initGame() {
		/*
		 * ������Ϸ�ĳ�ʼ����������ȡС˵������
		 */
		
		BufferedReader rs;
		try {
			rs = new BufferedReader(new FileReader(file_path));
			String novel;
			String[] temp;
			
			
			while( (novel = rs.readLine()) != null && !novel.equals("")  ) {
				//System.out.println("read novel: " + novel);
				temp = novel.split(" ");
				ArrayList<String> tempnovel = new ArrayList<String>();
				for(int i = 0; i < temp.length; i++) {
					temp[i] = temp[i].trim();
					//System.out.print("  temp:"+"["+i+"]:"+temp[i]);
					tempnovel.add(temp[i]);
				}
				//System.out.println();
				//System.out.println("tempnovel: "+tempnovel);
				allnovels.add(tempnovel);
				//System.out.println("allnovels:"+allnovels);
			}
			
			rs.close();
			
			
			//�������е�С˵����û��������Ŀ��
			for(int i = 0; i < allnovels.size(); i++) {
				this.selected.add(i, false);;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int generateRandomInt(int min, int max) {
		/*
		 * ����һ�����������Χ��[min, max]
		 * �����ķ������Ȳ���һ����[0, max - min]����������ټ���min��Ϊ��[min, max]�ڵ������
		 */
		int random = (int)(Math.random() * ( max - min + 1));
		return min + random;
	}
	
	public String maskString(String srcstr, int masklen) {
		/*
		 * ��һ���ַ��������ڸǣ������ѡȡ����ַ�����ĳЩλ�ý����ڸ�
		 * srcstr:��Ҫ�ڸǵ��ַ���
		 * masklen:�ڸǵĳ���
		 */
		if(srcstr.equals("") || masklen == 0) {
			return srcstr;
		}
		
		boolean[] masked = new boolean[srcstr.length()];
		for(int i = 0; i < masked.length; i++) {
			masked[i] = false;
		}
		
		int mask = -1;
		StringBuilder src = new StringBuilder(srcstr);
		for(int i = 1; i <= masklen && i <= srcstr.length()-1; ) {
			mask = this.generateRandomInt(0, srcstr.length()-1);
			if(masked[mask] == true) {
				continue;
			}
			src.setCharAt(mask, '*');
			i++;
		}
		
		return src.toString();
		
	}
	
	public void setProblem() {
		this.allBtns[1].setDisable(false);
		int selectnovel = -1;
		do {
			selectnovel = this.generateRandomInt(0, this.allnovels.size()-1);
			//System.out.println("selectnovel:"+selectnovel + "\n allnovels.size():"+allnovels.size());
			
		}while( this.selected.get(selectnovel));
		this.currentSelected = selectnovel;
		this.selected.set(selectnovel, true);
		ArrayList<String> selectednovel = this.allnovels.get(selectnovel);
		
		//System.out.println("allnovels:"+allnovels);
		//�ලС˵�е���Щ���Ǳ�ѡ����
		boolean[] selectedrole = new boolean[selectednovel.size()];
		for(int i = 1; i < selectedrole.length; i++) {
			selectedrole[i] = false;
		}
		
		this.allTFs[0].setText(this.maskString(selectednovel.get(0), 
									(int)(selectednovel.get(0).length() * 0.5)));
		this.roleselected[0] = 0;
		int selectrole = -1;
		for(int i = 1; i <= 3; ) {
			selectrole = this.generateRandomInt(1, selectednovel.size()-1);
			if(selectedrole[selectrole] == true) {
				continue;
			}
			System.out.println("selectedrole:"+selectrole);
			this.allTFs[i].setText(this.maskString(selectednovel.get(selectrole), 1));
			selectedrole[selectrole] = true;
			this.roleselected[i] = selectrole;
			i++;
		}
		
		
	}
	
	
	public boolean judgeAnswer() {
		boolean isAllRight = true;
		for(int i = 0; i < this.roleselected.length; i++) {
			if( !this.allTFs[i].getText().equals( this.allnovels.get(this.currentSelected)
					.get(this.roleselected[i]) ) ) {
				this.allTFs[i].setText(this.allnovels.get(this.currentSelected)
						.get(this.roleselected[i]));
				this.allTFs[i].setStyle("-fx-text-fill: red; -fx-font-size: 11px;");
				isAllRight = false;
				this.wrongNum++;
				//return false;
			}else {
				this.rightNum++;
				this.allTFs[i].setStyle("-fx-text-fill: green; -fx-font-size: 10px;");
			}
		}
		
		return isAllRight;
	}
	
}