import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {

	public static final int ROWS = 30;// 行数
	public static final int COLS = 30;// 列数
	public static final int BLOCK_SIZE = 20;// 每格宽度
	public static final int REFLASHTIME = 250;// 画面刷新频率，单位ms
	public Snake snake = null;
	public Egg egg = null;
	private PaintThread paintThread = null;

	public static void main(String[] args) {
		new Yard().launch();// 程序启动入口
	}

	// 构造方法初始化窗口
	public void launch() {
		setSize(ROWS * BLOCK_SIZE, COLS * BLOCK_SIZE);
		setLocation(550, 200);
		setVisible(true);
		setResizable(false);

		// 窗体关闭事件
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});

		// 键盘监听
		addKeyListener(new KeyMonitor());// KeyMonitor类写成内部类，在最后

		// 创建蛇和蛋的对象
		snake = new Snake(this);
		egg = new Egg(this);

		// 启动绘制画面线程
		paintThread = new PaintThread();
		new Thread(paintThread).start();
	}

	// 绘制游戏内容
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();// 创建颜色对象
		// 画出灰色背景
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, ROWS * BLOCK_SIZE, COLS * BLOCK_SIZE);// 画长方形方法

		// 画出网格线
		g.setColor(Color.BLACK);
		for (int i = 1; i < ROWS; i++) {
			g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);// 画出横线
		}
		for (int j = 1; j < COLS; j++) {
			g.drawLine(BLOCK_SIZE * j, 0, BLOCK_SIZE * j, ROWS * BLOCK_SIZE);// 画出竖线
		}
		g.setColor(c);
		//画出蛋
		egg.draw(g);
		//画出蛇
		snake.draw(g);
	}

	// 防止刷新闪烁的方法（双缓冲）
	Image offScreenImage = null;

	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS
					* BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	//判断蛇是否吃到蛋的方法
	private boolean eat() {
		if((this.snake.head.x == this.egg.x) && (this.snake.head.y == this.egg.y)){
			return true;
		}
		return false;
	}
	
	// 绘制图形内部线程类
	private class PaintThread implements Runnable {
		@Override
		public void run() {
			while (!snake.isdeath) {
				repaint();// 一直重画画面，内容是paint方法的内容
				snake.move();
				if(eat()){
					snake.eat();
					egg.newEgg();
				}
				try {
					Thread.sleep(REFLASHTIME);//每隔REFLASHTIME刷新画面，
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 键盘监听内部类
	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			snake.keyPressed(e);
		}
	}

}
