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

	private static boolean exit;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			MainFrame thisClass = new MainFrame();
			thisClass.setTitle("Lab04");
			thisClass.setVisible(true);

			startButton.setFocusable(false);
			resetButton.setFocusable(false);

			resetButton.addActionListener(e -> {
				startButton.setEnabled(true);
				exit = false;
				SwingUtilities.updateComponentTreeUI(thisClass);
				Manipulator.setJ(1);
				if(mid != null) contentPane.remove(mid);
				contentPane.add(empty);
			});

			startButton.addActionListener(e -> {
				if(!l1.getText().isEmpty() && !l2.getText().isEmpty() && !d.getText().isEmpty() && !h.getText().isEmpty()){
					mid = new MyPanel(l1.getText(),l2.getText(),d.getText(),h.getText());
				}
				else{
					return;
				}
				thisClass.animate();
				startButton.setEnabled(false);
				contentPane.remove(empty);
				SwingUtilities.updateComponentTreeUI(thisClass);
				mid.setPreferredSize(new Dimension(715, 800));
				mid.setBackground(Color.ORANGE);
				contentPane.add(mid);
			});

		});

	}
	public void animate() {
		exit = true;
		Thread t = new Thread(() -> {
			int i = 360;
			while (i>0 && exit) {
				mid.g.rotate();
				mid.repaint();
				mid.revalidate();
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
		right.setBackground(Color.RED);
		right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS));

		rightTop.setPreferredSize(new Dimension(435, 330));
		rightTop.setBackground(Color.pink);

		rightBot.setPreferredSize(new Dimension(435, 365));
		rightBot.setBackground(Color.blue);

		empty.setPreferredSize(new Dimension(715, 800));
		empty.setBackground(Color.orange);

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
		right.add(rightTop);
		right.add(rightBot);
		contentPane.add(left);
		contentPane.add(right);
		contentPane.add(empty);
		resetButton.addActionListener(e -> l1.setText(""));
		resetButton.addActionListener(e -> l2.setText(""));
		resetButton.addActionListener(e -> d.setText(""));
		resetButton.addActionListener(e -> h.setText(""));
	}
}
