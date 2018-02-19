/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Game implements ActionListener{
//Variables
	public Board b;
	public Menu m;
	public Tutorial t;
	public DeckBuilder d;
	private Timer myTimer;
	public boolean game= true;
	public Container c;
	public DeckSelect ds;
	public static final String filePath = new File("").getAbsolutePath() + "/src/";
	public static BufferedImage base = null;
	
//Constructor
	public Game(Container c){
		try {
			base = ImageIO.read(new File(Game.filePath +"Base.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
		this.c = c;
		ds = new DeckSelect(this);
		m = new Menu(this);
		d = new DeckBuilder(this);
		t = new Tutorial(this);
		c.add(m);
		b = new Board(0);
		game = false;
		myTimer = new Timer(34,this);
		myTimer.start();
	}
	public void actionPerformed(ActionEvent e) {
	if (e.getSource()==myTimer){
		c.repaint();
		//while game is on
		if (game){	
			//if game is not over
			if(b.over()){
				if((b.getEnd()==2 || b.getEnd()==0) && b.getState()==false){
					b.turnStart(b.player);
				}
				else if(b.getEnd()==1 && b.getState()==false){
					b.turnStart(b.enemy);
					for (int i=0;i<b.enemyMinion.size(); i++){
						b.enemyMinion.get(i).smorc(b,b.player);
					}
					b.turnEnd(b.enemy);
				}
			}
			else if(b.checkEnd()){
			}
			else{
				c.remove(b);
				b = new Board(0);
				c.remove(b);
				c.add(m);
				b.revalidate();
				b.repaint();
				game = false;

			}
		}
	}
	}
	//pause for end screen
}//end class Game
