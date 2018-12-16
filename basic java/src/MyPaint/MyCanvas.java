package MyPaint;




import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

final class MyCanvas extends Pane {
	private ToolsPane tool;
	private Integer lineType;
	private static boolean startPaint = false;
	private double x;
	private double y;
	public MyCanvas(Stage stage, ToolsPane tool) {
		super();
		this.tool = tool;
		this.lineType = tool.lineTypeProperty();
		this.prefWidthProperty().bind(stage.widthProperty().divide(1.1));
		this.prefHeightProperty().bind(stage.heightProperty().divide(1.5));
		this.setStyle("-fx-background-color: rgba(255,255,255);");
		this.setEffect(new InnerShadow());
		this.setViewOrder(5);
		
		this.setOnMousePressed(pe->{
			this.startPaint = true;
			System.out.println("start");
			this.x = pe.getX();
			this.y = pe.getY();
		});
		
		this.setOnMouseDragged(mouseDraggedEvt->{
			if( this.startPaint ) {
				this.getChildren().clear();
				System.out.println("dragged x:"+mouseDraggedEvt.getX()+"  y:"+mouseDraggedEvt.getY());
				Rectangle rect = new Rectangle(this.x, this.y, mouseDraggedEvt.getX()-this.x, mouseDraggedEvt.getY()-this.y);
				this.getChildren().add(rect);
			}
			
			
		});
		
		this.setOnMouseReleased(re->{
			this.startPaint = false;
			System.out.println("released");
			
		});
		
	}
	public MyCanvas(Stage stage, double width, double height) {
		super();
		this.setWidth(width);
		this.setHeight(height);
		this.setViewOrder(5);
	}
	/*
	 * setWidth��setHeight�����ᵼ����ѭ�����Ե���ջ�������
	 * ��Ϊʱ��д��Pane�ķ��������Իᵼ�������Լ������Լ�����������ѭ�������Լ�
	 *���Ըĳɵ��ø����setWidth��setHeight����*/
	public void setWidth(double width) {
		super.setWidth(width);
	}
	
	public void setHeight(double height) {
		super.setHeight(height);
	} 
	
	public void bindWidthProperty(Node node) {
		this.prefWidthProperty().bind(((Pane)node).widthProperty());
	}
	
	public void bindHeightProperty(Node node) {
		this.prefHeightProperty().bind(((Pane)node).heightProperty());
	}
	
	public int[] getMousePos() {
		int[] pos = new int[2];
		
		
		return pos;
	}
	
	
	/*
	 * ��ͼ�����̣�
	 * 		�ȵ�һ�ΰ������󣬼�¼���ʼ�ĵ㣻
	 * 		Ȼ��ȵ�����ͷţ�
	 * 		������Ӧ��ͼ�Ρ�
	 */
	private void drawShapes() {
		/*
		 * ����this.tool�е�ѡ������״��������Ӧ����״
		 */
		String shape = this.tool.getSelectedShape();
		if (shape.equals("")) {
			return;
		}
		
		int lineWidth = this.tool.getSelectedLineType();
		Color lineColor = this.tool.getSelectedColor();
		String brush = this.tool.getSelectedBrush();
		
		if ( shape.equals("������") ) {
			//��������
		}
		if ( shape.equals("������")) {
			
		}
		if ( shape.equals("Բ��")) {
					
		}
		if ( shape.equals("������")) {
			
		}
		if ( shape.equals("�ı�")) {
			
		}
		
		
		
	}
	
}