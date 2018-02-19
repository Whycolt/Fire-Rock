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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Gear extends Card{
//Variables
	private int form,slot,mod, atk, x,y;
	public static BufferedImage defaultG1,defaultG2;
//Constructor
	public Gear(String name, int cost, int atk, int mod,int side,int effect, int slot,String text,int value){
		super(name,cost,-1,effect,side,text,0);
		form = 0;
		this.slot = slot;
		this.mod = mod;
		this.atk = atk;
		if (defaultG1 == null)
			set();
	}//end Gear default
//Vital Methods
	//Getting Base Card Image
	public static void set(){
		try {
			defaultG1 = ImageIO.read(new File(Game.filePath +"Card_Base_G1.png"));
			defaultG2 = ImageIO.read(new File(Game.filePath +"Card_Base_G2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
	}//end getImg
	//draw Weapon
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(Board.smalletter);
		g.drawImage(image, x, y, 96,137, null);
		if (form == 0){
			g.drawImage(defaultG1, x, y,96,137,null);
			g.setColor(Color.white);
			g.drawString(cost+"", x+12, y+20);
			if (atk>0)
				g.drawString(atk+"", x+20, y+127);
			else
				g.drawString(mod+"", x+20, y+127);
			g.setColor(Color.black);
			g.drawString(name, x+10, y+83);
			for(int i = 0;i < Math.floor(text.length()/ 16);i++)
				g.drawString(text.substring(i*16, i*16+16), x+10, y+100+10*i);
			}
		else{
			g.setColor(Color.white);
			g.drawImage(defaultG2, x, y,96,100,null);
			if (atk>0)
				g.drawString(atk+"", x+20, y+92);
			else
				g.drawString(mod+"", x+20, y+92);
			g.setColor(Color.black);
		}
		}//end draw
	
	//draing side line image
	public void draw(Graphics g){
		int x = 60;
		int y = 250;
		g.drawImage(image, x, y, 140, 200, null);
		g.drawImage(defaultG1, x, y,140,200,null);	
		g.drawString(name, x+12, y+118);
		for(int i = 0;i < Math.floor(text.length()/ 18);i++)
			g.drawString(text.substring(i*18, i*18+18), x+16, y+146+14*i);
		g.setColor(Color.white);
		g.drawString(cost+"", x+18, y+25);
		if (atk>0)
			g.drawString(atk+"", x+20, y+183);
		else
			g.drawString("-"+mod+"", x+20, y+183);
		g.setColor(Color.black);
		}//end draw
//Interactive Methods
	//equip weapon
	public void equip(Hero a,Board b, Card t){
		checkBattlecry(b,t);
		form = 1;
		a.setAtk(atk);
		a.setMod(mod);
	}//end summon
	public void remove(Hero a){
		a.setAtk(-atk);
		a.setMod(-mod);
	}
	//BattleCry
	public void checkBattlecry(Board b, Card t){//Lets face it, a battlecry is pretty much a spell + body
		if (effect == 1){//battlecries will arbitrarily be 1 for now
			b.effect(this,t);
		}//if effects
	}//end checkBattleCry
//Getters and Editors
	//get weapon slot
	public int getSlot(){
		return slot;
	}
	public int getTarget(){
		return target;
	}
	public Card clone(){
	Card a = new Gear(name, cost, atk, mod,side,effect, slot,text,value);
	return a;
	}
}//end class Minion