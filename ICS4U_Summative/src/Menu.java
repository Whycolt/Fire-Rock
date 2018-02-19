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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel implements ActionListener{

	private JButton play,tutorial,deckBuilder,exit;
	private String panel;
	private Game g;
	private BufferedImage image,bp,bt,bc,be;
	
	public Menu(Game in){
		g = in;
		this.setLayout(null);
		try {
			image = ImageIO.read(new File(Game.filePath +"MainMenu.png"));
			bp = ImageIO.read(new File(Game.filePath +"PlayButton.png"));
			bt = ImageIO.read(new File(Game.filePath +"insButton.png"));
			bc = ImageIO.read(new File(Game.filePath +"decButton.png"));
			be = ImageIO.read(new File(Game.filePath +"exButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
		panel = "menu";
		play = new JButton("");
		play.setContentAreaFilled(false);
		play.setBounds(555,290,200,50);
		play.addActionListener(this);
		add(play);
		tutorial = new JButton("");
		tutorial.setContentAreaFilled(false);
		tutorial.setBounds(555,365,200,50);
		tutorial.addActionListener(this);
		add(tutorial);
		deckBuilder = new JButton("");
		deckBuilder.setBounds(555,440,200,50);
		deckBuilder.setContentAreaFilled(false);
		deckBuilder.addActionListener(this);
		add(deckBuilder);
		exit = new JButton("");
		exit.setContentAreaFilled(false);
		exit.setBounds(555,515,200,50);
		exit.addActionListener(this);
		add(exit);
	}
	public void paintComponent(Graphics g){
		g.drawImage(Game.base, 0, 0, 1314, 710, null);
		g.drawImage(image, 0, 0, 1314, 710, null);
		g.drawImage(bp,555,290,200,50, null);
		g.drawImage(bt,555,365,200,50, null);
		g.drawImage(bc,555,440,200,50, null);
		g.drawImage(be,555,515,200,50, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton a = (JButton) e.getSource();
		//moving to deck select menu
		if (a == play){
			g.c.remove(g.m);
			g.c.add(g.ds);
			g.ds.update();
			g.c.revalidate();
			g.c.repaint();
		}
		//moving to instructions menu
		else if (a ==tutorial){
			g.c.remove(g.m);
			g.c.add(g.t);
			g.c.revalidate();
			g.c.repaint();
		}
		//moving to deck builder menu
		else if (a == deckBuilder){
			g.c.remove(g.m);
			g.c.add(g.d);
			g.c.revalidate();
			g.c.repaint();
		}
		//exit
		else if( a == exit){
			System.exit(0);;
		}
		
	}
}
