package MyPaint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;



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

class ToolsPane extends VBox{
	//����ѡ���ͼ���·���Լ�ͼ�������
	private static String iconRootPath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/";
	private static String[] brushIconPath = new String[] {iconRootPath+"pencil_16px.png", iconRootPath+"brush_16px.png"};
	private static String[] brushLabels = new String[] {"Ǧ��", "ˢ��"};
	private static String[] shapeIconPath = 
			new String[] {iconRootPath + "randomline_16px.png", iconRootPath + "straitline_16px.png", iconRootPath + "rectangle_16px.png", iconRootPath + "square_16px.png",
					iconRootPath + "circle_16px.png" , iconRootPath + "ellipse_16px.png", 
					iconRootPath + "triangle_16px.png", iconRootPath + "text_16px.png"};
	private static String[] shapeLabels = new String[] {"������", "ֱ��", "������", "������", "Բ��", "��Բ", "������", "�ı�"};
	private static String[] lineTypeLabels = new String[] {"1px", "3px", "5px", "8px", "10px", "13px", "16px"};
	private static String[] lineTypeIconPath = new String[] {iconRootPath + "line_1px.png", iconRootPath + "line_3px.png",
					iconRootPath + "line_5px.png", iconRootPath + "line_8px.png",
					iconRootPath + "line_10px.png", iconRootPath + "line_13px.png", iconRootPath + "line_16px.png"};
	
	protected static boolean hasStroke = false;
	protected static boolean isFilled = false;
	private static String selectedBrush = "";
	private static String selectedLineType = "";
	private static String selectedShape = "������";
	private static Color selectedColor = null;
	private static String selectedFontFamily = "Microsoft YaHei";
	private static int selectedFontSize = 15;
	
	private Accordion brush;
	private Accordion shape;
	private Accordion lineType;
	private Accordion shapeAttrSelector;
	protected Button foreGroundColorSelector;
	protected Button backGroundColorSelector;
	protected Button foreBackColorSelector;
	private ColorPicker color;
	private ComboBox fontFamilySelector;
	private ComboBox fontSizeSelector;
	private boolean showColor = false;
	
	public ToolsPane(Stage stage) {
		super(5);
		HBox subPaneTop = new HBox();
		HBox subPaneMid = new HBox();
		this.setEffect(new DropShadow());
		this.setStyle("-fx-background-color:rgba(225,225,225)");
		this.prefWidthProperty().bind(stage.widthProperty());
		this.prefHeightProperty().bind(stage.heightProperty().divide(10));
		this.setMinHeight(40);
		this.setViewOrder(3);
		this.createTools();
		subPaneTop.getChildren().add(this.shape);
		subPaneTop.getChildren().add(this.lineType);
		subPaneTop.getChildren().add(this.foreGroundColorSelector);
		subPaneTop.getChildren().add(this.backGroundColorSelector);
		subPaneTop.getChildren().add(this.color);
		subPaneTop.getChildren().add(this.createTextBox("�ı�"));
		
		subPaneTop.getChildren().add(this.shapeAttrSelector);
		//subPaneMid.getChildren().add(this.fontSizeSelector);
		//this.getChildren().add(this.createTextBox("�ı�"));
		this.getChildren().add(subPaneTop);
		
		//�����ǰ����ɫ��ǩ
		
		//��ӱ�����ɫ��ǩ
		
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
			if ( this.foreBackColorSelector != null) {
				this.foreBackColorSelector.setStyle("-fx-background-color:#"+this.selectedColor.toString().substring(2) );
				this.foreBackColorSelector.setTooltip(
						new Tooltip(this.foreBackColorSelector.getText()+":#"+
								this.selectedColor.toString().substring(2)) );
			}
		});
		
		this.fontFamilySelector = this.createFontFamilySelector();
		this.fontSizeSelector = this.createFontSizeSelector();
		
		this.foreGroundColorSelector = new Button("ǰ��ɫ");
		this.backGroundColorSelector = new Button("����ɫ");
		this.foreGroundColorSelector.setStyle("-fx-background-color:#000000");
		this.backGroundColorSelector.setStyle("-fx-background-color:#000000");
		this.foreGroundColorSelector.setTooltip(new Tooltip("ǰ��ɫ"+
				this.foreGroundColorSelector.getStyle().substring("-fx-background-color:".length()-1) ) );
		this.backGroundColorSelector.setTooltip(new Tooltip("����ɫ" + 
				this.backGroundColorSelector.getStyle().substring("-fx-background-color:".length()-1) ) );
		
		this.foreGroundColorSelector.prefWidthProperty().bind(this.widthProperty().divide(15));
		this.backGroundColorSelector.prefWidthProperty().bind(this.widthProperty().divide(15));
		
		this.foreGroundColorSelector.setOnAction(e->{
			this.foreBackColorSelector = this.foreGroundColorSelector;
		});

		this.backGroundColorSelector.setOnAction(e->{
			this.foreBackColorSelector = this.backGroundColorSelector;
		});
		
		//this.shapeAttrSelector = this.createShapeAttrSelector("��״����", 3);
		this.shapeAttrSelector = this.createBox(null, new String[] {"�߿�", "���"}, "��״����", 3);
	}
	
	public ComboBox createFontFamilySelector() {
		ComboBox cb = new ComboBox();
		ObservableList<String> fontFamilies = FXCollections.observableList(Font.getFamilies());
		cb.setItems(fontFamilies);
		cb.setOnAction(e->{
			this.selectedFontFamily = (String) cb.getSelectionModel().getSelectedItem();
			System.out.println("font:"+this.selectedFontFamily);
		});
		cb.prefWidthProperty().bind(this.widthProperty().divide(10));
		cb.setTooltip(new Tooltip("����"));
		cb.setValue(this.selectedFontFamily);
		return cb;
	}
	
	public ComboBox createFontSizeSelector() {
		ComboBox cb = new ComboBox();
		ArrayList<Integer> fontSizeList = new ArrayList<Integer>();
		for (int i = 8; i < 72; i += 4) {
			fontSizeList.add(i);
		}
		ObservableList<Integer> fontSize = FXCollections.observableList(fontSizeList);
		cb.setItems(fontSize);
		cb.setOnAction(e->{
			this.selectedFontSize = (Integer) cb.getSelectionModel().getSelectedItem();
			System.out.println("font:"+this.selectedFontSize);
		});
		cb.prefWidthProperty().bind(this.widthProperty().divide(10));
		cb.setTooltip(new Tooltip("�����С"));
		cb.setValue(this.selectedFontSize);
		return cb;
	}
	
	public Accordion createTextBox(String tip) {
		GridPane grid = new GridPane();
		grid.add(this.fontFamilySelector, 0, 0);
		grid.add(this.fontSizeSelector, 1, 0);
		
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
		
		//this.bindEvents(btns, tp, iconPath);
		
		return ac;
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
		ImageView[] icons = null;
		if ( iconPath != null) {
			icons = this.createIcons(iconPath);
		}
			
		GridPane grid = new GridPane();
		Node[] btns =  this.addNodes2GridPane(grid, icons, labels, col_num);
		
		TitledPane tp = new TitledPane();
		tp.setContent(grid);
		
		Accordion ac = new Accordion();
		ac.getPanes().addAll(tp);
		
		
		//���ø����ؼ�������
		tp.setCollapsible(true);
		tp.prefWidthProperty().bind(this.widthProperty().divide(6.5));
		tp.prefHeightProperty().bind(grid.widthProperty());
		tp.setText(tip);
		tp.setTooltip(new Tooltip(tip));
		
		this.bindEvents(btns, tp, iconPath);
		
		return ac;
	}

	//���ø�������ѡ�ѡ�е��¼�
	public void bindEvents(Node[] nodes, TitledPane tp, String[] iconPath) {
		//ImageView iv = null;
		for(int i = 0; i < nodes.length; i++) {
			if (nodes[i] instanceof Button) {
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
			if ( nodes[i] instanceof CheckBox) {
				CheckBox tmp = (CheckBox)nodes[i];
				tmp.setOnAction(e->{
					if ( tmp.getText().equals("�߿�")) {
						this.hasStroke = (this.hasStroke?false:true);
					}
					if ( tmp.getText().equals("���")) {
						this.isFilled = (this.isFilled?false:true);
					}
				});
				
			}
			
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
	public Node[] addNodes2GridPane(GridPane grid, Node[] nodes, String[] labels, int col_num) {
		Node[] btns = null;
		if (nodes != null && nodes[0] instanceof ImageView)
			btns = new Button[nodes.length];
		if (nodes == null || nodes[0] instanceof CheckBox)
			btns = new CheckBox[labels.length];
		for(int i = 0; i < labels.length; i++) {
			if ( nodes == null) {
				CheckBox tmp = new CheckBox(labels[i]);
				tmp.prefWidthProperty().bind(grid.widthProperty().divide(col_num+0.2));
				tmp.prefHeightProperty().bind(grid.heightProperty().divide(labels.length/col_num+0.4));
				tmp.setTooltip(new Tooltip(labels[i]));
				btns[i] = tmp;
			}
			else if( nodes[i] instanceof ImageView ) {
				Button tmp = new Button(labels[i], nodes[i]);
				ButtonStyle.setButtonStyle(tmp);
				tmp.prefWidthProperty().bind(grid.widthProperty().divide(col_num+0.2));
				tmp.prefHeightProperty().bind(grid.heightProperty().divide(nodes.length/col_num+0.4));
				tmp.setTooltip(new Tooltip(labels[i]));
				btns[i] = tmp;
			}
			

			grid.add(btns[i], i % col_num, i / col_num);
			grid.setMargin(btns[i], new Insets(2,3,2,2));
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
		if ( this.hasStroke) {
			//Ĭ���߿�Ϊ3px
			if (ToolsPane.selectedLineType.equals(""))
				return 3;
			return Integer.valueOf(ToolsPane.selectedLineType.substring(0, ToolsPane.selectedLineType.indexOf("px")));
		}
		else
			return 0;

		
	}

	public Color getSelectedForeColor() {
		if ( this.color.getValue() == null)
			return Color.BLACK;
		return Color.valueOf("0x"+this.foreGroundColorSelector.getTooltip().getText().substring(5));
	}
	
	public Color getSelectedBackColor() {
		//System.out.println("0x"+this.backGroundColorSelector.getTooltip().getText().substring(5));
		if ( !this.isFilled )
			return null;
		return Color.valueOf("0x"+this.backGroundColorSelector.getTooltip().getText().substring(5));
		
	}
	
	public String getSelectedFontFamily() {
		return this.selectedFontFamily;
	}
	
	public Integer lineTypeProperty() {
		return this.getSelectedLineType();
	}

	public int getSelectedFontSize() {
		return this.selectedFontSize;
	}
	
	
}