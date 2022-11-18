package main;

import model.Manipulator;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;


public class MainFrame extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;
	private static final JPanel contentPane = new JPanel();
	private static MyPanel mid;
	private static final JPanel empty = new JPanel();
	private static final JPanel left = new JPanel();
	private static final JPanel right = new JPanel();
	private static final JPanel rightTop = new JPanel();
	private static final JPanel rightBot = new JPanel();
	private static final JButton startButton = new JButton("Uruchom");
	private static final JButton resetButton = new JButton("Resetuj");
	private static final JTextField l1 = new JTextField();
	private static final JTextField l2 = new JTextField();
	private static final JTextField d = new JTextField();
	private static final JTextField h = new JTextField();
	private static final JPanel empty1 = new JPanel();
	private static final XYLineChart_AWT chartTop = new XYLineChart_AWT("Wykres składowej Vx predkości","Vx");
	private static final XYLineChart_AWT chartBot = new XYLineChart_AWT("Wykres składowej Vy predkości","Vy");

	private static boolean exit;

	public static void main(String[] args) {


		SwingUtilities.invokeLater(() -> {
			MainFrame thisClass = new MainFrame();
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			thisClass.setLocation(dim.width/2-thisClass.getSize().width/2, dim.height/2-thisClass.getSize().height/2);
			thisClass.setTitle("Lab04");
			thisClass.setVisible(true);

			startButton.setFocusable(false);
			resetButton.setFocusable(false);

			resetButton.addActionListener(e -> {
				startButton.setEnabled(true);
				exit = false;
				SwingUtilities.updateComponentTreeUI(thisClass);
				if(mid != null){
					contentPane.remove(mid);
				}
				contentPane.add(empty);
				right.removeAll();
				right.revalidate();
				chartTop.deleteSeries();
				chartBot.deleteSeries();
				Manipulator.alpha = 0;
				Manipulator.beta = 0;
			});

			startButton.addActionListener(e -> {
				if(!l1.getText().isEmpty() && !l2.getText().isEmpty() && !d.getText().isEmpty() && !h.getText().isEmpty()){
					mid = new MyPanel(l1.getText(),l2.getText(),d.getText(),h.getText());
				}
				else{
					return;
				}

				if(Integer.parseInt(d.getText()) > 390 || Integer.parseInt(h.getText()) > 370
						|| Integer.parseInt(l2.getText()) - Integer.parseInt(l1.getText()) < Math.sqrt(Math.pow(Integer.parseInt(h.getText()),2)+Math.pow(Integer.parseInt(d.getText()),2))
						|| Math.sqrt(Math.pow(Integer.parseInt(h.getText()),2)+Math.pow(Integer.parseInt(d.getText()),2)) < Integer.parseInt(l1.getText())){
					return;
				}
				thisClass.animate();
				startButton.setEnabled(false);
				contentPane.remove(empty);
				mid.setPreferredSize(new Dimension(710,800));
				mid.setBackground(new Color(240,248,255));
				contentPane.add(mid);
				right.add(rightTop);
				right.add(rightBot);
				chartTop.setVisible(true);
				chartTop.setPreferredSize(new Dimension(440,400));
				chartBot.setVisible(true);
				chartBot.setPreferredSize(new Dimension(440,400));
				rightTop.add(chartTop);
				rightBot.add(chartBot);
			});

		});

	}
	public void animate() {
		exit = true;
		Thread t = new Thread(() -> {
			int i = 360;
			double beforeXCoords = 0;
			double beforeYCoords = 0;
			while (i>0 && exit) {
				mid.g.rotate();
				mid.repaint();
				mid.revalidate();
				if(i==360){
					beforeXCoords = Manipulator.getXCoords();
					beforeYCoords = Manipulator.getYCoords();
				}
				chartTop.updateSeries(Manipulator.getXCoords() - beforeXCoords,Manipulator.getAngle());
				chartBot.updateSeries(Manipulator.getYCoords() - beforeYCoords,Manipulator.getAngle());
				beforeXCoords = Manipulator.getXCoords();
				beforeYCoords = Manipulator.getYCoords();
				i--;
				try {
					Thread.sleep(20);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			System.out.println("Animation stopped");
		});
		t.start();
	}

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(new Dimension(1400, 800));
		contentPane.setLayout(new FlowLayout());
		setContentPane(contentPane);

		left.setPreferredSize(new Dimension(220, 800));

		right.setPreferredSize(new Dimension(435, 800));
		right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS));

		rightTop.setPreferredSize(new Dimension(435, 330));
		rightBot.setPreferredSize(new Dimension(435, 365));

		empty.setPreferredSize(new Dimension(710, 800));
		empty1.setPreferredSize(new Dimension(435, 330));

		l1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				l1.setEditable(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE);
			}
		});
		l1.setFont(new Font(null,Font.ITALIC,18));
		l2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				l2.setEditable(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE);
			}
		});
		l2.setFont(new Font(null,Font.ITALIC,18));
		d.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				d.setEditable(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE);
			}
		});
		d.setFont(new Font(null,Font.ITALIC,18));
		h.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				h.setEditable(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE);
			}
		});
		h.setFont(new Font(null,Font.ITALIC,18));
		JLabel l1Label = new JLabel("Wprowadź l1:");
		l1Label.setFont(new Font(null,Font.ITALIC,21));
		JLabel l2Label = new JLabel("Wprowadź l2:");
		l2Label.setFont(new Font(null,Font.ITALIC,21));
		JLabel dLabel = new JLabel("Wprowadź d:");
		dLabel.setFont(new Font(null,Font.ITALIC,21));
		JLabel hlabel = new JLabel("Wprowadź h:");
		hlabel.setFont(new Font(null,Font.ITALIC,21));
		left.setLayout(new GridLayout(11,2));
		left.add(l1Label);
		left.add(l1);
		left.add(l2Label);
		left.add(l2);
		left.add(dLabel);
		left.add(d);
		left.add(hlabel);
		left.add(h);
		left.add(startButton);
		left.add(resetButton);
		contentPane.add(left);
		contentPane.add(right);
		contentPane.add(empty);
		resetButton.addActionListener(e -> l1.setText(""));
		resetButton.addActionListener(e -> l2.setText(""));
		resetButton.addActionListener(e -> d.setText(""));
		resetButton.addActionListener(e -> h.setText(""));
	}
}
