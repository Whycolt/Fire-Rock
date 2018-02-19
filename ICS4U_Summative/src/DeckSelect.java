/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeckSelect extends JPanel implements ActionListener{

	private int framecount;
	private JButton play,exit,pre,next;
	private ArrayList <Bot> bots= new ArrayList<Bot>();
	private String panel;
	private Game g;
	private dk[] loaded = new dk[10];
	private JButton slots[] = new JButton[10];
	private ArrayList<Card> chosen = new ArrayList<Card>();
	private int deck = 0,selb;
	private BufferedImage image,bm;
	private JLabel unFin;
	private boolean deckFin;
	
	public DeckSelect(Game in){
		g = in;
		selb = 0;
		deckFin = false;
		this.setLayout(null);
		try {
			image = ImageIO.read(new File(Game.filePath +"DeckSelect.png"));
			bm = ImageIO.read(new File(Game.filePath +"menuButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
		unFin = new JLabel("DECK NOT COMPLETE",JLabel.CENTER);
		unFin.setBorder(BorderFactory.createEmptyBorder());
		unFin.setBounds(0,0,1314,710);
		unFin.setFont(new Font("Default",Font.PLAIN,100));
		unFin.setForeground(Color.yellow);
		add(unFin);
		unFin.hide();
		bots.add(new MillHouse());
		panel = "menu";
		next = new JButton("");
		next.addActionListener(this);
		add(next);
		next.setBounds(994,386,100,46);
		next.setContentAreaFilled(false);
		pre = new JButton("");
		pre.addActionListener(this);
		add(pre);
		pre.setBounds(892,386,102,46);
		pre.setContentAreaFilled(false);
		exit = new JButton("");
		exit.addActionListener(this);
		add(exit);
		exit.setBounds(135,105,25,25);
		exit.setContentAreaFilled(false);
		deck = 0;
		for (int i = 0; i < 9;i++){
			loaded[i] = load();
			deck+=1;
		}
		for(int i = 0; i<9;i++){
			slots[i] = new JButton(loaded[i].getName());
			add(slots[i]);
			slots[i].addActionListener(this);
			slots[i].setBounds(140+i%3*250, 240+i/3*60, 200, 50);
		}
		update();
		repaint();
		
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(Game.base, 0, 0, 1314, 710, null);
		g.drawImage(image, 0, 0, 1314, 710, null);
		g.drawImage(bm, 135,105,25,25, null);
		g.drawImage(bots.get(selb).getImage(), 894,186,200, 200, null);
		if (deckFin){
			unFin.show();
			framecount += 1;
			if (framecount > 50){
				deckFin = false;
				unFin.hide();
			}
		}
	}
	//loading decks from dk files
	public dk load(){
		 dk s = null;
	      try {
	         FileInputStream fileIn = new FileInputStream(Game.filePath + "deck" + deck + ".dk");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         s = (dk) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	      }catch(ClassNotFoundException c) {
	         System.out.println("dk file missing");
	         c.printStackTrace();
	      }
	      return s;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton a = (JButton) e.getSource();
		if(a == next){
			if (selb+1 <bots.size())
				selb+=1;
		}
		if(a==pre){
			if (selb > 0)
				selb-=1;
		}
		for(int i = 0; i<9;i++){
			if (a == slots[i]){
				if(loaded[i].getCardNum() == 30){
					chosen = new ArrayList<Card>();
					for (int j = 0; j<30;j++){
						chosen.add(DeckBuilder.translate(loaded[i].getCard(j)));
					}
					if (selb == 0)
						g.b.setEnemy(new MillHouse());
					g.c.remove(g.ds);
					g.game = true;
					g.c.add(g.b);
					g.b.setDeck(chosen);
					g.b.startGame();
					g.c.revalidate();
					g.c.repaint();
				}
				else{
						deckFin = true;
						framecount = 0;
				}
			}
		}
		if( a == exit){
			g.c.remove(g.ds);
			g.c.add(g.m);
		}
		
	}
	//updates loaded files after edits
	public void update(){
		deck = 0;
		for (int i = 0; i < 9;i++){
			loaded[i] = load();
			deck+=1;
		}
		for(int i = 0; i<9;i++){
			slots[i].setText(loaded[i].getName());
		}
	}
}
