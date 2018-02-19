/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Tutorial extends JPanel implements ActionListener{

	private JButton menu;
	private boolean indeck;
	private Game g;
	private BufferedImage image,bm;
	
	public Tutorial (Game in){
		g = in;
		this.setLayout(null);
		try {
			image = ImageIO.read(new File(Game.filePath +"tutorial.png"));
			bm = ImageIO.read(new File(Game.filePath +"menuButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
		menu = new JButton("kjna;ladsf");
		menu.addActionListener(this);
		menu.setContentAreaFilled(false);
		menu.setBounds(135,105,25,25);
		add(menu);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		//back to menu
		if (b == menu){
			g.c.remove(g.t);
			g.c.add(g.m);
		}
	}
	public void paintComponent(Graphics g){
		g.drawImage(Game.base, 0, 0, 1314, 710, null);
		g.drawImage(image, 0, 0, 1314, 710, null);
		g.drawImage(bm,135,105,25,25,null);
		
	}
}
