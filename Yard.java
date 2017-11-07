import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {

	public static final int ROWS = 30;// ����
	public static final int COLS = 30;// ����
	public static final int BLOCK_SIZE = 20;// ÿ����
	public static final int REFLASHTIME = 250;// ����ˢ��Ƶ�ʣ���λms
	public Snake snake = null;
	public Egg egg = null;
	private PaintThread paintThread = null;

	public static void main(String[] args) {
		new Yard().launch();// �����������
	}

	// ���췽����ʼ������
	public void launch() {
		setSize(ROWS * BLOCK_SIZE, COLS * BLOCK_SIZE);
		setLocation(550, 200);
		setVisible(true);
		setResizable(false);

		// ����ر��¼�
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});

		// ���̼���
		addKeyListener(new KeyMonitor());// KeyMonitor��д���ڲ��࣬�����

		// �����ߺ͵��Ķ���
		snake = new Snake(this);
		egg = new Egg(this);

		// �������ƻ����߳�
		paintThread = new PaintThread();
		new Thread(paintThread).start();
	}

	// ������Ϸ����
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();// ������ɫ����
		// ������ɫ����
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, ROWS * BLOCK_SIZE, COLS * BLOCK_SIZE);// �������η���

		// ����������
		g.setColor(Color.BLACK);
		for (int i = 1; i < ROWS; i++) {
			g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);// ��������
		}
		for (int j = 1; j < COLS; j++) {
			g.drawLine(BLOCK_SIZE * j, 0, BLOCK_SIZE * j, ROWS * BLOCK_SIZE);// ��������
		}
		g.setColor(c);
		//������
		egg.draw(g);
		//������
		snake.draw(g);
	}

	// ��ֹˢ����˸�ķ�����˫���壩
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

	//�ж����Ƿ�Ե����ķ���
	private boolean eat() {
		if((this.snake.head.x == this.egg.x) && (this.snake.head.y == this.egg.y)){
			return true;
		}
		return false;
	}
	
	// ����ͼ���ڲ��߳���
	private class PaintThread implements Runnable {
		@Override
		public void run() {
			while (!snake.isdeath) {
				repaint();// һֱ�ػ����棬������paint����������
				snake.move();
				if(eat()){
					snake.eat();
					egg.newEgg();
				}
				try {
					Thread.sleep(REFLASHTIME);//ÿ��REFLASHTIMEˢ�»��棬
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ���̼����ڲ���
	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			snake.keyPressed(e);
		}
	}

}
