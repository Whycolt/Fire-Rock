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

public class Minion extends Card{
//Variables
	private int atk,hp,form,x,y;
	public static BufferedImage defaultM1, defaultM2;
	private boolean attack;
//Constructor
	public Minion(String name, int cost, int atk, int hp,int side){
		super(name,cost,-1,-1,side,"",0);
		attack = false;
		form = 0;
		this.atk = atk;
		this.hp = hp;
		if (defaultM1 == null)
			set();
	}//end Minion default
	public Minion(String name, int cost, int atk, int hp, int effect, int target,String text,int side, int value){
		super(name,cost,effect,target,side,text,value);
		form = 0;
		this.atk = atk;
		this.hp = hp;
		if (defaultM1 == null)
			set();
	}//end Minion ability
//Vital Methods
	//Getting Base Card Image
	public static void set(){
		try {
			defaultM1 = ImageIO.read(new File(Game.filePath +"Card_Base_M1.png"));
			defaultM2 = ImageIO.read(new File(Game.filePath +"Card_Base_M2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
	}//end getImg
	//draw minion
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(Board.smalletter);
		g.drawImage(image, x, y, 96, 137, null);
		if (form == 0){
			g.drawImage(defaultM1, x, y,96,137,null);	
			g.drawString(name, x+10, y+83);
			for(int i = 0;i < Math.floor(text.length()/ 16);i++)
				g.drawString(text.substring(i*16, i*16+16), x+10, y+100+10*i);
			g.setColor(Color.white);
			g.drawString(cost+"", x+12, y+20);
			g.drawString(hp+"", x+55, y+126);
			g.drawString(atk+"", x+13, y+126);
			g.setColor(Color.black);
			}
		else{
			g.setColor(Color.white);
			g.drawImage(defaultM2, x, y,96,100,null);	
			g.drawString(hp+"", x+55, y+93);
			g.drawString(atk+"", x+10, y+93);
			g.setColor(Color.black);
		}
		}//end draw
	
	public void draw(Graphics g){
		int x = 60;
		int y = 250;
		g.drawImage(image, x, y, 140, 200, null);
		g.drawImage(defaultM1, x, y,140,200,null);	
		g.drawString(name, x+12, y+118);
		for(int i = 0;i < Math.floor(text.length()/ 18);i++)
			g.drawString(text.substring(i*18, i*18+18), x+16, y+146+14*i);
		g.setColor(Color.white);
		g.drawString(cost+"", x+18, y+25);
		g.drawString(hp+"", x+75, y+183);
		g.drawString(atk+"", x+20, y+183);
		g.setColor(Color.black);
		}//end draw

	//Interactive Methods
		//Summon minion
		public void summon(Board d,Card t){
			checkBattlecry(d,t);
			form = 1;
			target = 1;
		}//end summon
		//BattleCry
		public void checkBattlecry(Board d,Card t){//Lets face it, a battlecry is pretty much a spell + body
			if (effect != -1){//battlecries will arbitrarily be 1 for now
				d.effect(this,t);
			}//if effects
		}//end checkBattleCry
		//Death
		public void checkIsDead(Board d, Card t){//call this after every spell is cast and every instance of combat
			if (hp<=0){
				d.destroy(this);
			}//end check hp
		}//end checkIsDead
		//battle
		public void battle(Board d,Minion card2){
			if(!attack&&side==0)
				d.error();
			if(attack){
			this.setHp(0, - card2.getAtk());
			card2.setHp(0, - this.getAtk());
			
			//venomous eff
			
			if (this.effect==2)
				d.destroy(card2);
			else if (card2.effect==2)
				d.destroy(this);
			this.checkIsDead(d,card2);
			card2.checkIsDead(d,this);
			attack = false;
			}

		}//end battle
		//me go face
		public void smorc(Board d, Hero c){
			if (!attack&&side == 0)
				d.error();
			if(attack){
			c.setHp(d,-this.getAtk());
			c.checkIsDead(d);
			attack = false;
			}
		
		}//end smorc
		//resets the minions attack
		public void reset(){
			attack = true;
		}
	//Getters and Editors
		//Setting Atk
		public void setAtk(int x, int amount){
			if (x==1)//We use this for stuff like keeper of uldaman, i.e. set to 3
				atk = amount;
			else//this for buffs i.e. give +2 atk
				atk+=amount;//use a negative amount for debuffs
		}//end setAtk
		//Setting Hp
		public void setHp(int x, int amount){//see above
			if (x==1)
				hp = amount;
			else
				hp+=amount;
		}//end setHp
		public void setAttack(boolean x){
			attack=x;
		}
		//getting Atk
		public int getAtk(){
			return atk;
		}//end getAtk
		//getting Hp
		public int getHp(){
			return hp;
		}//end getHp
		public int getForm(){
			return form;
		}
		public Card clone(){
			Card a = new Minion(name, cost, atk, hp, effect, target,text,side,value);
			return a;
		}
	}//end class Minion