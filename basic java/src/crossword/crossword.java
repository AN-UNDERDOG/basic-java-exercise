package crossword;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class crossword extends Application{
	String currentPlayer = "";
	int score = -1;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		
		StartScene startscene = new StartScene(stage);
		GameScene gamescene = new GameScene(stage);
		ResultScene resultscene = new ResultScene(stage);
		RankScene rankscene = new RankScene(stage);
		ArrayList<Scene> allscene = new ArrayList<Scene>();
		allscene.add(startscene);//0
		allscene.add(gamescene);//1
		allscene.add(resultscene);//2
		allscene.add(rankscene);//3
		
		
		
		
		initStage(stage);
		linkAllScene(allscene, stage);
		
		stage.setScene(startscene);
		//stage.setScene(gamescene);
		//stage.setScene(resultscene);
		//stage.setScene(rankscene);
		stage.setOnShowing(showingevt->{
			System.out.println("read from file 4 ranktable");
			initRankTable(rankscene);
		});
		
		
		
		
		stage.show();
		
		
		
	}
	
	
	//������̨
	public void initStage(Stage stage) {
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/Game_Controller_32px.png";
		Image mainicon = new Image(iconpath);
		stage.getIcons().add(mainicon);
		stage.setTitle("crossword");
		
		
		stage.setMaxHeight(450);
		stage.setMaxWidth(320);
		stage.setMinHeight(380);
		stage.setMinWidth(290);		
		stage.initStyle(StageStyle.TRANSPARENT);
		
		
	}
	
	//��ʾ�����ҵĶԻ���
	public void addPlayer() {
		TextInputDialog addPlayer = new TextInputDialog();
		addPlayer.setTitle("�������");
		addPlayer.setHeaderText("");
		addPlayer.setContentText("�������������:");
		
		addPlayer.showAndWait();
		currentPlayer = addPlayer.getResult();
		//System.out.println( addPlayer.getResult());
	}
	
	public void initRankTable(Scene scene) {
		((RankScene)scene).readRankRecord();
	}
	
	public void saveRankTable(Scene scene) {
		((RankScene)scene).writeRankRecord();
	}
	
	public void linkAllScene(ArrayList<Scene> allscene, Stage stage) {
		//�������а�ķ��ذ�ť����Ϸ��ʼ���������
		bindRankSceneBackBtn(allscene.get(3), allscene.get(0), stage);
		//���ÿ�ʼ����Ŀ�ʼ��Ϸ��ť����Ϸ���������
		bindStartSceneStartBtn(allscene.get(0), allscene.get(1), stage);
		//���ÿ�ʼ��������а�ť�����а������
		bindStartSceneRankBtn(allscene.get(0), allscene.get(3), stage);
		//Ϊ��������˳���ť���ñ������а���¼�
		bindStartSceneExitBtn(allscene.get(0), allscene.get(3), stage);
		//������Ϸ�������¿�ʼ��ť����ʼ���������
		bindGameSceneReplayBtn(allscene.get(1), allscene.get(0), stage);
		//���õ�����������ڵ���3ʱ����Ϊ��Ϸ������ת����Ϸ����Ľ���
		bindGameSceneNextBtn(allscene.get(1), allscene.get(2), stage);
		//������Ϸ������水�������水ť��������
		bindResultSceneBackBtn(allscene.get(2), allscene.get(0), allscene.get(3), stage);
	}
	
	//Ϊ���а�ķ��ذ�ť���¼�
	public void bindRankSceneBackBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((RankScene)srcscene).back.setOnAction(backevt->{
			stage.setScene(dstscene);
		});
	}
	
	//Ϊ��ʼ�����Ŀ�ʼ��Ϸ��ť���¼�
	public void bindStartSceneStartBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((StartScene) srcscene).getPlayBtn().setOnAction(playevt->{
			if(currentPlayer.equals("")) {
				addPlayer();
			}
			((GameScene)dstscene).initFlags();
			((GameScene)dstscene).setProblem();
			
			stage.setScene(dstscene);
			
		});
	}
	
	//Ϊ��ʼ���������а�ť��ת���������¼�
	public void bindStartSceneRankBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((StartScene) srcscene).getRankBtn().setOnAction(playevt->{
			((RankScene)dstscene).sortByScore();
			stage.setScene(dstscene);
		});
	}
	
	//Ϊ��������˳���ť���¼�
	public void bindStartSceneExitBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((StartScene)srcscene).getExitBtn().setOnAction(exitevt->{
			((RankScene)dstscene).sortByScore();
			((RankScene)dstscene).writeRankRecord();
			stage.close();
		});
	}
	
	//Ϊ��Ϸ��������¿�ʼ��ť���¼�
	public void bindGameSceneReplayBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((GameScene)srcscene).getReplayBtn().setOnAction(replayevt->{
			((GameScene)srcscene).resetTfs();
			stage.setScene(dstscene);
		});
	}
	
	//Ϊ��Ϸ�������һ�ⰴť���¼�
	public void bindGameSceneNextBtn(Scene srcscene, Scene dstscene, Stage stage) {
		GameScene newscene = (GameScene)srcscene;
		newscene.getNextBtn().setOnAction(judgeevt->{
			if(!newscene.hasEdit) {
				return;
			}
			if(newscene.wrongNum >= 3) {
				((ResultScene)dstscene).addStars(newscene.rightNum);
				((ResultScene)dstscene).setRightNum(newscene.rightNum);
				((ResultScene)dstscene).setWrongNum(newscene.wrongNum);
				score = newscene.rightNum;
				//System.out.println(" after in bindnextbtn, score:"+score);
				stage.setScene(dstscene);
			}else {
				((GameScene)srcscene).setProblem();
			}
			((GameScene)srcscene).resetTfs();
			newscene.hasEdit = false;
		});
	}
	
	//Ϊ��Ϸ�������󶨷��������水ť���¼�
	public void bindResultSceneBackBtn(Scene srcscene, Scene dstscene, Scene rankscene, Stage stage) {
		((ResultScene)srcscene).getBackBtn().setOnAction(backevt->{
			ObservableList<RankRecord> data = ((RankScene)rankscene).getData();
			//System.out.println("in bindbackbtn,score:"+score);
			RankRecord record = new RankRecord( data.size()+1, currentPlayer, score );
			data.add( record );
			stage.setScene(dstscene);	
		});
	}
	
	
	

	public static void main(String[] args) {
		Application.launch();
	}

	
}