/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Spell extends Card{
//Variables
	private int x,y;
	public static BufferedImage defaultS;
//Constructor
	public Spell(String name, int cost, int side,int target,int effect, String text,int value){
		super(name,cost,effect,target,side,text,value);
		this.side = side;
		if (defaultS == null)
			set();
	}//end Spell
//Vital Methods
	//Getting Base Card Image
	public static void set(){
		try {
			defaultS = ImageIO.read(new File(Game.filePath +"Card_Base_S1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
	}//end getImg
	//draw Spell
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(Board.smalletter);
		int x = 0;
		int y = 0;
		g.drawImage(image, x, y, 96, 137, null);
		g.drawImage(defaultS, x, y,96,137,null);
		g.drawString(name, x+10, y+83);
		for(int i = 0;i < Math.floor(text.length()/ 16);i++)
			g.drawString(text.substring(i*16, i*16+16), x+10, y+100+10*i);
		g.setColor(Color.white);
		g.drawString(cost+"", x+12, y+20);
		g.setColor(Color.black);
		}//end draw
	//drawing side image of spell
	public void draw(Graphics g){
		int x = 60;
		int y = 250;
		g.drawImage(image, x, y, 140, 200, null);
		g.drawImage(defaultS, x, y,140,200,null);	
		g.drawString(name, x+12, y+118);
		for(int i = 0;i < Math.floor(text.length()/ 18);i++)
			g.drawString(text.substring(i*18, i*18+18), x+16, y+146+14*i);
		g.setColor(Color.white);
		g.drawString(cost+"", x+18, y+25);
		g.setColor(Color.black);
		}//end draw
//Interactive Methods
	//BattleCry
	public void play(Board b,Card t){//Lets face it, a battlecry is pretty much a spell + body - so spells are basically battlecry
		if (effect != -1){//battlecries will arbitrarily be 1 for now
			b.effect(this,t);
		}//if effects
	}//end checkBattleCry
	public Card clone(){
		Card a = new Spell(name, cost, side,target,effect, text,value);
		return a;
	}
//Getters and Editors
}//end class Minion