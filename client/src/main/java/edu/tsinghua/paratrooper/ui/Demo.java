package edu.tsinghua.paratrooper.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

class Demo {

		public static void main(String[] args) {
			JFrame frame = new JFrame("����Բ�ǰ�ť����");
			frame.setBounds(100, 100, 400, 400);
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 30));
			panel.setBackground(Color.white);


			MyRoundButton myButton = new MyRoundButton("122", new Color(151, 112,
					212), new Color(95, 185, 160));
			panel.add(myButton);
			frame.add(panel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			myButton.requestFocus();
		}

	}

	class MyRoundButton extends JButton {
		private Color color, color_initial, color_enter;

		public MyRoundButton(String s, Color c_initial, Color c_enter) {
			super(s);
			color = c_initial;
			color_initial = c_initial;
			color_enter = c_enter;
			setPreferredSize(new Dimension(150, 50));
			setFont(new Font("Dialog", Font.BOLD, 18));
			setForeground(Color.white);
			setFocusPainted(true);
			setContentAreaFilled(false);
			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					color = color_enter;
				}

				public void mouseExited(MouseEvent e) {
					color = color_initial;
				}

				public void mouseReleased(MouseEvent e) {
					color = color_initial;
				}

				public void mouseClicked(MouseEvent e) {
					color = color_enter;
				}

			});
		}

		public void paintComponent(Graphics g) {// ������䰴ť����״��Բ�Ǿ��ΰ�ť
			Graphics2D g2d = (Graphics2D) g;
			// �������䣬������Բ���ܱߵľ���õ�
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.addRenderingHints(rh);
			// ������䰴ť����ɫ
			g2d.setColor(color);
			// �������Բ�Ǿ�������
			g2d.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10,
					10);
			super.paintComponent(g);
		}

		public void paintBorder(Graphics g) {// ���ư�ť�߿�Բ�Ǿ��α߿�

		}
	}


/**
 * @author zhangsukun
 *   边框设置
 */

class TextBorderUtlis extends LineBorder
{

	private static final long serialVersionUID = 1L;

	public TextBorderUtlis(Color color, int thickness, boolean roundedCorners)
	{
		super(color, thickness, roundedCorners);
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	{

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color oldColor = g.getColor();
		Graphics2D g2 = (Graphics2D) g;
		int i;
		g2.setRenderingHints(rh);
		g2.setColor(lineColor);
		for (i = 0; i < thickness; i++)
		{
			if (!roundedCorners){
				g2.drawRect(x + i, y + i, width - i - i - 1, height - i - i - 1);
			}else{
				g2.drawRoundRect(x + i, y + i, width - i - i - 1, height - i - i - 1, 5, 5);}
		}
		g2.setColor(oldColor);
	}

}

