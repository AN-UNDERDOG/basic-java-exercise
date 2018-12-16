package MyPaint;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import MyPaint.ButtonStyle;;


/*
 * @author:GZY
 * @date:2018.12.16
 * description:������������������
 * 		����ѡ��������
 * 		��״ѡ��������
 * 		����ѡ��������
 * 		��ɫѡ������
 * �������ʵ�ַ�����
 * 		�������ⴰ�񣬽����ⴰ����뵽Accordion (�ַ��ٿؼ���Ҳ������Ϊ����ؼ����������)��
 *		�������̣�
 *			�������ÿؼ������A�����ؼ����뵽�������
 *			�����������B�����ñ�����������ΪA
 *			����Accordion�ؼ� C�����C������б���������ӱ������
 */

class ToolsPane extends HBox{
	//����ѡ���ͼ���·���Լ�ͼ�������
	private static String iconRootPath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/";
	private static String[] brushIconPath = new String[] {iconRootPath+"pencil_16px.png", iconRootPath+"brush_16px.png"};
	private static String[] brushLabels = new String[] {"Ǧ��", "ˢ��"};
	private static String[] shapeIconPath = 
			new String[] {iconRootPath + "rectangle_16px.png", iconRootPath + "square_16px.png",
					iconRootPath + "circle_16px.png", iconRootPath + "triangle_16px.png", 
					iconRootPath + "text_16px.png"};
	private static String[] shapeLabels = new String[] {"������", "������", "Բ��", "������", "�ı�"};
	private static String[] lineTypeLabels = new String[] {"1px", "3px", "5px", "8px", "10px", "13px", "16px"};
	private static String[] lineTypeIconPath = new String[] {iconRootPath + "line_1px.png", iconRootPath + "line_3px.png",
					iconRootPath + "line_5px.png", iconRootPath + "line_8px.png",
					iconRootPath + "line_10px.png", iconRootPath + "line_13px.png", iconRootPath + "line_16px.png"};
	
	private static String selectedBrush = "";
	private static String selectedLineType = "";
	private static String selectedShape = "";
	private static Color selectedColor = null;
	
	private Accordion brush;
	private Accordion shape;
	private Accordion lineType;
	private ColorPicker color;
	private boolean showColor = false;
	
	public ToolsPane(Stage stage) {
		super(5);
		this.setEffect(new DropShadow());
		this.setStyle("-fx-background-color:rgba(225,225,225)");
		this.prefWidthProperty().bind(stage.widthProperty());
		this.prefHeightProperty().bind(stage.heightProperty().divide(10));
		this.setMinHeight(40);
		this.setViewOrder(3);
		this.createTools();
		//��ӻ���ѡ���
		this.getChildren().add(this.brush);
		//�����״ѡ���
		this.getChildren().add(this.shape);
		//���������ϸѡ���
		this.getChildren().add(this.lineType);
		//�����ɫѡ����
		this.getChildren().add(this.color);
		
	}
	
	
	public void createTools() {
		this.brush = this.createBox(ToolsPane.brushIconPath, ToolsPane.brushLabels, "����", 3);
		this.shape = this.createBox(ToolsPane.shapeIconPath, ToolsPane.shapeLabels, "��״", 3);
		this.lineType = this.createBox(ToolsPane.lineTypeIconPath, ToolsPane.lineTypeLabels, "������ϸ", 1);
		this.color = new ColorPicker(Color.BLACK);
		this.color.prefWidthProperty().bind(this.widthProperty().divide(6));
		this.color.setTooltip(new Tooltip("��ɫ"));
		this.color.valueProperty().addListener(e->{
			this.selectedColor = this.color.getValue();
			System.out.println("chosed color "+this.selectedColor);
		});
		
	}
	
	//���ø������ܵ�������
	/*ʵ�ַ�����
	�������ⴰ�񣬽����ⴰ����뵽Accordion (�ַ��ٿؼ���Ҳ������Ϊ����ؼ����������)��
	�������̣�
	�������ÿؼ������A�����ؼ����뵽�������
	�����������B�����ñ�����������ΪA
	����Accordion�ؼ� C�����C������б���������ӱ������
	���ø����ؼ�������*/
	public Accordion createBox(String[] iconPath, String[] labels, String tip, int col_num) {
		ImageView[] icons = this.createIcons(iconPath);
		GridPane grid = new GridPane();
		Button[] btns = this.addNodes2GridPane(grid, icons, labels, col_num);
		
		TitledPane tp = new TitledPane();
		tp.setContent(grid);
		
		Accordion ac = new Accordion();
		ac.getPanes().addAll(tp);
		
		
		//���ø����ؼ�������
		tp.setCollapsible(true);
		tp.prefWidthProperty().bind(this.widthProperty().divide(4.5));
		tp.prefHeightProperty().bind(grid.widthProperty());
		tp.setText(tip);
		tp.setTooltip(new Tooltip(tip));
		
		this.bindEvents(btns, tp, iconPath);
		
		return ac;
	}
	
	
	//���ø�������ѡ�ѡ�е��¼�
	public void bindEvents(Node[] nodes, TitledPane tp, String[] iconPath) {
		for(int i = 0; i < nodes.length; i++) {
			Button tmp = (Button)nodes[i];
			
			//�������´���һ��imageview���󣬷���Ҫ��ʹ�ð�ť��ʹ�õ�imageview�����õĻ�����ԭ����ť��graphics��ʧЧ��
			ImageView iv = new ImageView(iconPath[i]);
			((Button)nodes[i]).setOnAction(evt->{
				tp.setText(tmp.getText());
				//ѡ���ͽ���Ӧ������۵�����
				tp.setExpanded(false);
				
				tp.setGraphic(iv);
				System.out.println("chosed  " + tmp.getText());
				
				if(tp.getTooltip().getText().equals("����")) {
					this.selectedBrush = tmp.getText();
					
				}
				if(tp.getTooltip().getText().equals("��״")) {
					this.selectedShape = tmp.getText();
					
				}
				if(tp.getTooltip().getText().equals("������ϸ")) {
					this.selectedLineType = tmp.getText();
					
				}
				
			});
			
		}
		
		tp.setOnMouseExited(e->{
			tp.setExpanded(false);
		});
		
		
	}
	
	//����ͼƬ��ַ����ͼ��
	public ImageView[] createIcons(String[] iconPath) {
		ImageView[] icons = new ImageView[iconPath.length];
		for( int i = 0; i < icons.length; i++) {
			icons[i] = new ImageView(iconPath[i]);
		}
		
		return icons;
	}
	
	//��������������ͼ��
	@SuppressWarnings("static-access")
	public Button[] addNodes2GridPane(GridPane grid, Node[] nodes, String[] labels, int col_num) {
		//System.out.print(""+nodes.length);
		Button[] btns = new Button[nodes.length];
		Button tmp;
		for(int i = 0; i < nodes.length; i++) {
			
			if( !(nodes[i] instanceof Button)) {
				tmp = new Button(labels[i], nodes[i]);
				
			}else {
				tmp = (Button)nodes[i];
				tmp.setText(labels[i]);
			}
			btns[i] = tmp;
			tmp.prefWidthProperty().bind(grid.widthProperty().divide(col_num+0.2));
			tmp.prefHeightProperty().bind(grid.heightProperty().divide(nodes.length/col_num+0.4));
			ButtonStyle.setButtonStyle(tmp);
			tmp.setTooltip(new Tooltip(labels[i]));
			grid.add(tmp, i % col_num, i / col_num);
			grid.setMargin(tmp, new Insets(2,3,2,2));
		}
		
		return btns;
	}
	
	public String getSelectedBrush() {
		return ToolsPane.selectedBrush;
	}
	
	public String getSelectedShape() {
		return ToolsPane.selectedShape;
	}
	
	public int getSelectedLineType() {
		if (ToolsPane.selectedLineType.equals("")) {
			//Ĭ���߿�Ϊ3px
			return 3;
		}
		return Integer.valueOf(ToolsPane.selectedLineType.substring(0, ToolsPane.selectedLineType.indexOf("px")));
	}

	public Color getSelectedColor() {
		return this.color.getValue();
	}
	
	@SuppressWarnings("deprecation")
	public Integer lineTypeProperty() {
		return new Integer(this.getSelectedLineType());
	}
}