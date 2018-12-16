package MyPaint;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import MyPaint.ToolsPane;
import MyPaint.winattr;
import MyPaint.MyCanvas;

public class test extends Application{

	@SuppressWarnings("static-access")
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		initStage(stage);
		
		VBox mainPane = new VBox();
		Scene mainScene = new Scene(mainPane, stage.getWidth(), stage.getHeight());
		stage.setScene(mainScene);
		initMainPane(mainPane, stage);
		
		MenuBar menuBar = this.createMenuBar(stage);
		mainPane.getChildren().add(menuBar);
		mainPane.setMargin(menuBar, new Insets(5,5,5,5));
		
		ToolsPane toolsBar = this.createToolsBar(stage);
		mainPane.getChildren().add(toolsBar);
		mainPane.setMargin(toolsBar, new Insets(5,5,5,5));
		
		MyCanvas canvas = this.createMyCanvas(stage, toolsBar);

		mainPane.getChildren().add(canvas);
		mainPane.setMargin(canvas, new Insets(5,5,5,5));
		
		stage.setAlwaysOnTop(true);
		stage.show();
		
	}
	
	//�����˵���
	public MenuBar createMenuBar(Stage stage) {
		MenuBar menubar = new MenuBar();
		//���˵����Ŀ����̨��
		menubar.prefWidthProperty().bind(stage.widthProperty());
		
		//�ڲ˵�������Ӳ˵�:�ļ��˵� ==> �����ļ��������ļ����˳�
		Menu fileMenu = new Menu("�ļ�");
		MenuItem newFileItem = new MenuItem("�½�");
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/addfile_16px.png";
		ImageView openfile = new ImageView(iconpath);
		newFileItem.setGraphic(openfile);
		MenuItem openFileItem = new MenuItem("��");
		MenuItem saveFileItem = new MenuItem("����");
		MenuItem exitItem = new MenuItem("�˳�");
		fileMenu.getItems().addAll(newFileItem, openFileItem,
				saveFileItem, exitItem);
		iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/file_24px.png";
		ImageView icon = new ImageView(iconpath);
		fileMenu.setGraphic(icon);
		
		menubar.setEffect(new Blend());
		
		menubar.getMenus().add(fileMenu);
		return menubar;
	}

	
	//���������������������ܰ�����
	//���ʵ�ѡ����״��ѡ��������ϸ��ѡ����ɫ��ѡ��
	//���Ϲ��ܵ�ʵ�֣�
	//
	public ToolsPane createToolsBar(Stage stage) {
		ToolsPane tools = new ToolsPane(stage);
		return tools;
	}
	
	public MyCanvas createMyCanvas(Stage stage, ToolsPane tool) {
		MyCanvas canvas = new MyCanvas(stage, tool);
		
		return canvas;
	}
	
	//������̨
	public void initStage(Stage stage) {
		//
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/paint.png";
		Image mainicon = new Image(iconpath);
		stage.getIcons().add(mainicon);
		stage.setTitle("paint");
		
		stage.setMinHeight(winattr.winMinHeight);
		stage.setMinWidth(winattr.winMinWidth);	
		stage.initStyle(StageStyle.DECORATED);
	}
		
	//���������
	public void initMainPane(Pane main_pane, Stage stage) {	
		main_pane.setMinSize(winattr.winMinWidth, winattr.winMinHeight);
		main_pane.prefHeightProperty().bind(stage.heightProperty());
		main_pane.prefWidthProperty().bind(stage.widthProperty());
		main_pane.setStyle("-fx-background-color: rgba(230,230,230);" );
	}
	
	
	public static void main(String[] args) {
		Application.launch();
	}
	
}