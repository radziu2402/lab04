package model;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Manipulator extends GObject {

	public static double alpha, beta;
	private static int l1;
	private static int l2;
	private static int d;
	private static int h;

	public Manipulator(int l1, int l2, int d, int h) {
		Manipulator.l1 = l1;
		Manipulator.l2 = l2;
		Manipulator.d = d;
		Manipulator.h = h;
	}

	@Override
	public void drawMe(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		int dx0;
		int dy0;

		dx0 = g2d.getClipBounds().width / 2 - 50 ;
		dy0 = g2d.getClipBounds().height / 2 + 40;

		AffineTransform saveAT = g2d.getTransform();

		t = new AffineTransform();
		t.translate(dx0, dy0);
		t.scale(1, -1);
		g2d.setTransform(t);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(-300,0,395,0);
		g2d.drawLine(0,-375,0,375);
		drawArrow(g2d,395,0,400,0,10,45);
		drawArrow(g2d,0,375,0,380,10,45);
		g2d.drawOval(-l1,-l1,2*l1,2*l1);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawPolygon(new int[] {d-7, d, d+7}, new int[] {h-15, h-3, h-15}, 3);
		t.rotate(alpha);
		g2d.setStroke(new BasicStroke(4));
		g2d.setTransform(t);
		g2d.drawLine(0, 0, l1, 0);
		t.translate(l1,0);
		t.rotate(beta);
		g2d.setTransform(t);
		g2d.drawLine(0, 0, l2, 0);

		g2d.setTransform(saveAT);
	}
	static public void drawArrow(Graphics g, int x0, int y0, int x1,
								 int y1, int headLength, int headAngle){
		double offs = headAngle * Math.PI / 180.0;
		double angle = Math.atan2(y0 - y1, x0 - x1);
		int[] xs = { x1 + (int) (headLength * Math.cos(angle + offs)), x1,
				x1 + (int) (headLength * Math.cos(angle - offs))};
		int[] ys = { y1 + (int) (headLength * Math.sin(angle + offs)), y1,
				y1 + (int) (headLength * Math.sin(angle - offs))};
		g.drawLine(x0,y0,x1,y1);
		g.drawPolyline(xs,ys,3);
	}

	@Override
	public void rotate() {
		alpha += Math.PI / 360.0;
		double ay = l1 * Math.sin(alpha);
		double ax = l1 * Math.cos(alpha);
		beta = Math.atan2(h-ay,d-ax) - alpha;
	}
	public static double getYCoords(){
		return l1 * Math.sin(alpha) + l2 * Math.sin(beta);
	}
	public static double getXCoords(){
		return l1 * Math.cos(alpha) + l2 * Math.cos(beta);
	}
	public static double getAngle(){
		return alpha * (180/Math.PI);
	}
}
