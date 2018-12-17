package MyPaint;




import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;


final class MyCanvas extends Pane {
	private ToolsPane tool;
	private Integer lineType;
	private static boolean startPaint = false;
	private static boolean isChosedShape = false;
	private static int clickedTimes = 0;
	private double x;
	private double y;
	private static Shape shape = null;
	private static Shape dragged = null;
	private static Shape chosedShape = null;
	private static winattr attr = new winattr();
	
	public MyCanvas(Stage stage, ToolsPane tool) {
		super();
		this.tool = tool;
		this.lineType = tool.lineTypeProperty();
		this.prefWidthProperty().bind(stage.widthProperty().divide(1.1));
		this.prefHeightProperty().bind(stage.heightProperty().divide(1.5));
		this.setStyle("-fx-background-color: rgba(255,255,255);");
		this.setEffect(new InnerShadow());
		this.setViewOrder(5);
		this.setCursor(Cursor.cursor("CROSSHAIR"));
		this.bindEvents4Canvas();
		
	}
	
	public MyCanvas(Stage stage, double width, double height) {
		super();
		this.setWidth(width);
		this.setHeight(height);
		this.setViewOrder(5);
		this.bindEvents4Canvas();
	}
	
	@SuppressWarnings("static-access")
	public void bindEvents4Canvas() {
		this.setOnMousePressed(pe->{
			this.startPaint = true;
			System.out.println("start");
			this.x = pe.getX();
			this.y = pe.getY();
		});
		
		this.setOnMouseDragged(mouseDraggedEvt->{
			if( this.startPaint && !this.isChosedShape) {
				if( this.getChildren().size() >= 1 && this.shape != null)
					this.getChildren().remove(this.shape);

				this.createShapes();
				
				String shapeType = this.tool.getSelectedShape();
				if (shapeType.equals("")) {
					return;
				}
				
				
				double lineWidth = this.tool.getSelectedLineType();
				Color lineColor = this.tool.getSelectedColor();
				String brush = this.tool.getSelectedBrush();
				Shape shapeObj = null;
				
				double startX = Math.min(this.x, mouseDraggedEvt.getX() );
				double startY = Math.min(this.y, mouseDraggedEvt.getY() );
				if ( shapeType.equals("������") ) {
					//��������
					
					shapeObj = new MyRectangle(startX, startY,
							Math.abs(mouseDraggedEvt.getX()-this.x), Math.abs(mouseDraggedEvt.getY()-this.y),
							lineWidth, lineColor, null);
				}
				if ( shapeType.equals("������")) {
					//��������
					
					shapeObj = new MySquare(startX, startY,
							Math.max(Math.abs(mouseDraggedEvt.getX()-this.x), Math.abs(mouseDraggedEvt.getY()-this.y)),
							lineWidth, lineColor, null);
				}
				if ( shapeType.equals("Բ��")) {
					//��Բ��
					shapeObj = new MyCircle(this.x, this.y, 
							Math.max(Math.abs(mouseDraggedEvt.getX()-this.x), Math.abs(mouseDraggedEvt.getY()-this.y)),
							lineWidth, lineColor, null);	
					
				}
				if ( shapeType.equals("������")) {
					//��������
					//shapeObj = new MyCircle();
				}
				if ( shapeType.equals("�ı�")) {
					//д�ı�
					shapeObj = new MyText(this.x, this.y, "text", new Font("Consolas", 20), lineWidth, lineColor, null);
				}
					
				
				this.getChildren().add(shapeObj);
				this.shape = shapeObj;	
				
			}
		
		});
		
		
		this.setOnMouseReleased(re->{
			this.startPaint = false;
			if (this.shape == null) {
				return;
			}
			if ( this.shape != null && !this.getChildren().contains(this.shape)) {
				this.getChildren().add(this.shape);
				this.shape.setViewOrder(6);
				System.out.println("add successfully");
			}
				
			Node tmp = this.getChildren().get(this.getChildren().size()-1);
			this.bindEvents4Shape(tmp);
		
			System.out.println("released");
			this.shape = null;
			
		});
	}

	public void bindEvents4Shape(Node shape) {
		shape.setOnMousePressed(clickEvt->{
			MyCanvas.clickedTimes ++;
			System.out.println("i am clicked by mouse "+MyCanvas.clickedTimes+" times "
					+ " X:"+((Copy)shape).getPosX() + " Y:"+((Copy)shape).getPosY());
			if ( MyCanvas.clickedTimes == 1) {
				this.x = clickEvt.getX();
				this.y = clickEvt.getY();
				return;
			}
			
			if ( MyCanvas.clickedTimes == 2) {
				MyCanvas.clickedTimes = 0;
				MyCanvas.isChosedShape = true;
				
				shape.setEffect(attr.ds);
				this.chosedShape = (Shape)shape;
				return;
			}	
		});
		
		
		shape.setOnMouseDragged(dragEvt->{
			if ( MyCanvas.isChosedShape) {
				double offsetX = dragEvt.getX() - this.x;
				double offsetY = dragEvt.getY() - this.y;
				
				if ( this.dragged != null && this.getChildren().contains(this.dragged) ) {
					this.getChildren().remove( this.dragged );
				}
				System.out.println("type:"+shape.getClass());
				Shape newShape = (((Copy) shape).deepCopy( ((Copy)shape).getPosX()+offsetX, 
																((Copy)shape).getPosY()+offsetY ));
				
				this.dragged = newShape;
				
				this.dragged.setEffect(attr.ds);
				this.getChildren().add(newShape);
				shape.setVisible(false);
				this.shape = newShape;
				
			}
		});
	
		
		shape.setOnMouseReleased(releaseEvt->{
			System.out.println("release on rect");
			if ( !MyCanvas.isChosedShape ) {
				return;
			}
			
			
			if ( this.dragged != null) {
				this.dragged.setEffect(null);	
				
			}
			this.getChildren().remove(this.chosedShape);
			MyCanvas.clickedTimes = 0;
			MyCanvas.isChosedShape = false;
			this.dragged = null;
		});		
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

	/*
	 * ��ͼ�����̣�
	 * 		�ȵ�һ�ΰ������󣬼�¼���ʼ�ĵ㣻
	 * 		Ȼ��ȵ�����ͷţ�
	 * 		������Ӧ��ͼ�Ρ�
	 */
	private Shape createShapes() {
		/*
		 * ����this.tool�е�ѡ������״��������Ӧ����״
		 */
		
		String shape = this.tool.getSelectedShape();
		if (shape.equals("")) {
			return null;
		}
		
		Shape shapeObj = null;
		int lineWidth = this.tool.getSelectedLineType();
		Color lineColor = this.tool.getSelectedColor();
		String brush = this.tool.getSelectedBrush();
		
		if ( shape.equals("������") ) {
			//��������
			shapeObj = new MyRectangle();
		}
		if ( shape.equals("������")) {
			
		}
		if ( shape.equals("Բ��")) {
					
		}
		if ( shape.equals("������")) {
			
		}
		if ( shape.equals("�ı�")) {
			
		}
		
		return shapeObj;
		
	}
	
	
	
	
	private void drawRectangle() {
		
	}
	
	
}