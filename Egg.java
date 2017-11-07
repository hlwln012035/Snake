import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Egg {

	Yard yard = null;
	Random r = new Random();
	int x, y;// ��������

	public Egg(Yard yard) {
		this.yard = yard;
		this.newEgg();
	}

	// ����������һ����
	public void newEgg() {
		this.x = r.nextInt(yard.COLS - 1) + 1;
		this.y = r.nextInt(yard.ROWS - 2) + 2;
	}

	// ����ͼ��
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GREEN);// ��ɫ
		g.fillRoundRect(x * yard.BLOCK_SIZE, y * yard.BLOCK_SIZE,
				yard.BLOCK_SIZE, yard.BLOCK_SIZE, yard.BLOCK_SIZE / 5,
				yard.BLOCK_SIZE / 5);// ��������
		g.setColor(c);
	}

}
