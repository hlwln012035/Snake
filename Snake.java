import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Snake {

	Yard yard = null;
	LinkedList<Node> nodes = new LinkedList<Node>();// ��������Ϊ�ߵ�����ģ��
	Node head = null;// �ߵ�ͷ
	int size;// �ߵĴ�С
	boolean isdeath = false;

	// ��yard����
	public Snake(Yard yard) {
		this();
		this.yard = yard;
	}

	// �޲ι���
	public Snake() {
		head = new Node();
		head.x = 15;// ��ʼλ��λ������
		head.y = 15;
		head.direction = Dir.L;// ��ʼ��������
		nodes.add(head);// ��ʼ���ߵ�ʱ��
		size = nodes.size();// ����Ĵ�С��������Ĵ�С
	}
	
	//�Ե��ķ���
	public void eat(){
		//�Ե�������ͷ���ƶ���������һ��
		Node node = new Node();
		switch (head.direction) {
		case U:
			node.x = head.x;
			node.y = head.y-1;
			node.direction = head.direction;
			nodes.addFirst(node);// ��ͷ�ϼ���һ��
			head = nodes.getFirst();// ���¶���ͷ
			break;
		case D:
			node.x = head.x;
			node.y = head.y+1;
			node.direction = head.direction;
			nodes.addFirst(node);// ��ͷ�ϼ���һ��
			head = nodes.getFirst();// ���¶���ͷ
			break;
		case R:
			node.x = head.x+1;
			node.y = head.y;
			node.direction = head.direction;
			nodes.addFirst(node);// ��ͷ�ϼ���һ��
			head = nodes.getFirst();// ���¶���ͷ
			break;
		case L:
			node.x = head.x-1;
			node.y = head.y;
			node.direction = head.direction;
			nodes.addFirst(node);// ��ͷ�ϼ���һ��
			head = nodes.getFirst();// ���¶���ͷ
			break;
		}
		size = nodes.size();
	}
	// �ƶ�����
	public void move() {
		// �ƶ�����ͷ������һ�ڣ���β��ȥ��һ��
		Node node = null;
		switch (head.direction) {
		case U:
			node = nodes.getLast();
			node.x = head.x;
			node.y = head.y-1;
			node.direction = head.direction;//�����һ��Ԫ����ȡ�����Ը���ͷ�����Ը�ֵ
			nodes.addFirst(node);// ��ͷ�ϼ���һ��
			head = nodes.getFirst();// ���¶���ͷ
			nodes.removeLast();// ȥ��β��һ��
			break;
		case D:
			node = nodes.getLast();
			node.x = head.x;
			node.y = head.y+1;
			node.direction = head.direction;//�����һ��Ԫ����ȡ�����Ը���ͷ�����Ը�ֵ
			nodes.addFirst(node);// ��ͷ�ϼ���һ��
			head = nodes.getFirst();// ���¶���ͷ
			nodes.removeLast();// ȥ��β��һ��
			break;
		case R:
			node = nodes.getLast();
			node.x = head.x+1;
			node.y = head.y;
			node.direction = head.direction;//�����һ��Ԫ����ȡ�����Ը���ͷ�����Ը�ֵ
			nodes.addFirst(node);// ��ͷ�ϼ���һ��
			head = nodes.getFirst();// ���¶���ͷ
			nodes.removeLast();// ȥ��β��һ��
			break;
		case L:
			node = nodes.getLast();
			node.x = head.x-1;
			node.y = head.y;
			node.direction = head.direction;//�����һ��Ԫ����ȡ�����Ը���ͷ�����Ը�ֵ
			nodes.addFirst(node);// ��ͷ�ϼ���һ��
			head = nodes.getFirst();// ���¶���ͷ
			nodes.removeLast();// ȥ��β��һ��
			break;
		}
		isdeath();
	}
	//�ж��Ƿ������ķ���
	private void isdeath() {
		//�ж��Ƿ񳬳��߽磬���������������
		if((head.x > yard.COLS) || (head.x < 0)) {
			isdeath = true;
			return;
		}
		if((head.y > yard.ROWS) || (head.y < 2)) {
			isdeath = true;
			return;
		}
		
		//�ж��Ƿ�ҧ���Լ������ҧ����������
		for(int i = 1;i<size;i++){
			Node node = nodes.get(i);
			if((head.x == node.x )&&(head.y == node.y)) {
				isdeath = true;
				return;
			}
		}
	}

	// ��������ķ���
	public void draw(Graphics g) {
//		size = nodes.size();
//		if (size == 1) {// ��������Сֻ��1����ֻ��һ��node
//			Node n = nodes.getFirst();
//			n.paint(g);
//			return;
//		}

		for (Node n : nodes) {// ��������������ÿ������
			n.paint(g);
		}
//		Node n = null;
//		size = nodes.size();
//		for(int i = 0; i<size;i++){
//			n = nodes.get(i);
//			if(n!=null){
//				n.paint(g);
//			}
//		}
	}

	// ÿ�ڵ�Ԫ��node�ڲ���
	class Node {
		int x, y;// ����
		int blocksize = yard.BLOCK_SIZE;// ÿ��Ԫ�صĴ�С������yard��ÿ��Ĵ�С
		Dir direction;// �ƶ�����

		// ����ͼ�η���
		public void paint(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLACK);// �����Ǻ�ɫ
			g.fillRect(x * blocksize, y * blocksize, blocksize, blocksize);// ��һ����ɫ�ľ���
			g.setColor(c);
		}

	}

	// ���̼����࣬����Yard�ļ������ã������޸��ߵ��˶���������ʵ����ֻ�޸�head�ķ���
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if (head.direction != Dir.D)
				head.direction = Dir.U;// ���ͷ�ķ������£����ߵķ������Ϊ�ϣ������Դ�����
			break;
		case KeyEvent.VK_DOWN:
			if (head.direction != Dir.U)
				head.direction = Dir.D;
			break;
		case KeyEvent.VK_RIGHT:
			if (head.direction != Dir.L)
				head.direction = Dir.R;
			break;
		case KeyEvent.VK_LEFT:
			if (head.direction != Dir.R)
				head.direction = Dir.L;
			break;
		}
	}

}
