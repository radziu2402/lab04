package main;

import java.awt.Graphics;
import java.io.Serial;

import javax.swing.JPanel;

import model.GObject;
import model.Manipulator;

public class MyPanel extends JPanel {

	public GObject g;
	@Serial
	private static final long serialVersionUID = 1L;

	public MyPanel(String l1, String l2, String d, String h) {
		g = new Manipulator(Integer.parseInt(l1), Integer.parseInt(l2), Integer.parseInt(d), Integer.parseInt(h));
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		if (g != null) {
			g.drawMe(arg0);
		}
	}

}
