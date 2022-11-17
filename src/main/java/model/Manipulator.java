package model;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Manipulator extends GObject {

	public double alpha, beta, gamma;
	private final int l1;
	private final int l2;
	private final int d;
	private final int h;
	private static int j = 1;

	public Manipulator(int l1, int l2, int d, int h) {
		this.l1 = l1;
		this.l2 = l2;
		this.d = d;
		this.h = h;
	}

	@Override
	public void drawMe(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		int dx0;
		int dy0;

		dx0 = g2d.getClipBounds().width / 2 - 50 ;
		dy0 = g2d.getClipBounds().height / 2 + 35;

		AffineTransform saveAT = g2d.getTransform();

		t = new AffineTransform();
		t.translate(dx0, dy0);
		t.scale(1, -1);
		g2d.setTransform(t);
		g2d.drawOval(-l1,-l1,2*l1,2*l1);
		g2d.setStroke(new BasicStroke(8));
		g2d.drawOval(d,h,8,8);
		t.rotate(alpha);
		g2d.setStroke(new BasicStroke(4));
		g2d.setTransform(t);
		g2d.drawLine(0, 0, l1, 0);
		t.translate(l1, 0);
		t.rotate(beta);
		g2d.setTransform(t);
		g2d.drawLine(0, 0, l2, 0);
		g2d.setTransform(saveAT);
		j++;
	}

	public static void setJ(int j) {
		Manipulator.j = j;
	}

	@Override
	public void rotate() {
		alpha += Math.PI / 180.0;
		beta  = Math.atan(h/(d-l1)) - alpha;
		gamma += Math.PI / 180.0;
	}

}
