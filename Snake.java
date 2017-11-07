import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Snake {

	Yard yard = null;
	LinkedList<Node> nodes = new LinkedList<Node>();// 用链表作为蛇的数据模型
	Node head = null;// 蛇的头
	int size;// 蛇的大小
	boolean isdeath = false;

	// 传yard构造
	public Snake(Yard yard) {
		this();
		this.yard = yard;
	}

	// 无参构造
	public Snake() {
		head = new Node();
		head.x = 15;// 初始位置位于正中
		head.y = 15;
		head.direction = Dir.L;// 初始方向向左
		nodes.add(head);// 初始化蛇的时候
		size = nodes.size();// 蛇身的大小等于链表的大小
	}
	
	//吃蛋的方法
	public void eat(){
		//吃蛋就是在头的移动方向增加一节
		Node node = new Node();
		switch (head.direction) {
		case U:
			node.x = head.x;
			node.y = head.y-1;
			node.direction = head.direction;
			nodes.addFirst(node);// 在头上加上一节
			head = nodes.getFirst();// 重新定义头
			break;
		case D:
			node.x = head.x;
			node.y = head.y+1;
			node.direction = head.direction;
			nodes.addFirst(node);// 在头上加上一节
			head = nodes.getFirst();// 重新定义头
			break;
		case R:
			node.x = head.x+1;
			node.y = head.y;
			node.direction = head.direction;
			nodes.addFirst(node);// 在头上加上一节
			head = nodes.getFirst();// 重新定义头
			break;
		case L:
			node.x = head.x-1;
			node.y = head.y;
			node.direction = head.direction;
			nodes.addFirst(node);// 在头上加上一节
			head = nodes.getFirst();// 重新定义头
			break;
		}
		size = nodes.size();
	}
	// 移动方法
	public void move() {
		// 移动就是头上增加一节，在尾部去掉一节
		Node node = null;
		switch (head.direction) {
		case U:
			node = nodes.getLast();
			node.x = head.x;
			node.y = head.y-1;
			node.direction = head.direction;//将最后一个元素提取，属性根据头的属性赋值
			nodes.addFirst(node);// 在头上加上一节
			head = nodes.getFirst();// 重新定义头
			nodes.removeLast();// 去掉尾部一节
			break;
		case D:
			node = nodes.getLast();
			node.x = head.x;
			node.y = head.y+1;
			node.direction = head.direction;//将最后一个元素提取，属性根据头的属性赋值
			nodes.addFirst(node);// 在头上加上一节
			head = nodes.getFirst();// 重新定义头
			nodes.removeLast();// 去掉尾部一节
			break;
		case R:
			node = nodes.getLast();
			node.x = head.x+1;
			node.y = head.y;
			node.direction = head.direction;//将最后一个元素提取，属性根据头的属性赋值
			nodes.addFirst(node);// 在头上加上一节
			head = nodes.getFirst();// 重新定义头
			nodes.removeLast();// 去掉尾部一节
			break;
		case L:
			node = nodes.getLast();
			node.x = head.x-1;
			node.y = head.y;
			node.direction = head.direction;//将最后一个元素提取，属性根据头的属性赋值
			nodes.addFirst(node);// 在头上加上一节
			head = nodes.getFirst();// 重新定义头
			nodes.removeLast();// 去掉尾部一节
			break;
		}
		isdeath();
	}
	//判断是否死掉的方法
	private void isdeath() {
		//判断是否超出边界，如果超出，就死了
		if((head.x > yard.COLS) || (head.x < 0)) {
			isdeath = true;
			return;
		}
		if((head.y > yard.ROWS) || (head.y < 2)) {
			isdeath = true;
			return;
		}
		
		//判断是否咬到自己，如果咬到，就死了
		for(int i = 1;i<size;i++){
			Node node = nodes.get(i);
			if((head.x == node.x )&&(head.y == node.y)) {
				isdeath = true;
				return;
			}
		}
	}

	// 画出蛇身的方法
	public void draw(Graphics g) {
//		size = nodes.size();
//		if (size == 1) {// 如果蛇身大小只有1，则只画一个node
//			Node n = nodes.getFirst();
//			n.paint(g);
//			return;
//		}

		for (Node n : nodes) {// 遍历链表，并画出每节蛇身
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

	// 每节的元素node内部类
	class Node {
		int x, y;// 坐标
		int blocksize = yard.BLOCK_SIZE;// 每节元素的大小，等于yard中每格的大小
		Dir direction;// 移动方向

		// 绘制图形方法
		public void paint(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLACK);// 蛇身是黑色
			g.fillRect(x * blocksize, y * blocksize, blocksize, blocksize);// 画一个黑色的矩形
			g.setColor(c);
		}

	}

	// 键盘监听类，将被Yard的监听调用，用于修改蛇的运动方向，最终实现是只修改head的方向
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if (head.direction != Dir.D)
				head.direction = Dir.U;// 如果头的方向不是下，则将蛇的方向调整为上，其他以此类推
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
