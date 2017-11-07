import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Egg {

	Yard yard = null;
	Random r = new Random();
	int x, y;// 蛋的坐标

	public Egg(Yard yard) {
		this.yard = yard;
		this.newEgg();
	}

	// 随机坐标产生一个蛋
	public void newEgg() {
		this.x = r.nextInt(yard.COLS - 1) + 1;
		this.y = r.nextInt(yard.ROWS - 2) + 2;
	}

	// 绘制图形
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GREEN);// 绿色
		g.fillRoundRect(x * yard.BLOCK_SIZE, y * yard.BLOCK_SIZE,
				yard.BLOCK_SIZE, yard.BLOCK_SIZE, yard.BLOCK_SIZE / 5,
				yard.BLOCK_SIZE / 5);// 画出蛋蛋
		g.setColor(c);
	}

}
