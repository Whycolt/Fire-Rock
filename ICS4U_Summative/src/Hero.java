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

public class Hero extends Card{
//Variables
	private int hp,atk,mod,x,y,side,hpMax;
	private boolean attack;
//Constructor
	public Hero(String name,int side,int maxhp,int x,int y){
		super(name,0,-1,1,side,"",0);
		this.x = x;
		this.y = y;
		attack = true;
		hp = maxhp;
		hpMax = maxhp;
		atk = 0;
		this.side = side;
	}//end Hero
//Getters and Editors
	public void draw(Graphics g){
		g.drawImage(image, x, y, 100, 100, null);
		g.setColor(Color.red);
		g.fillOval(x+72, y+75, 20, 20);
		g.setColor(Color.white);
		g.drawString(hp+"", x+75, y+90);
		g.setColor(Color.black);
		if (atk>0){
			g.setColor(Color.blue);
			g.fillOval(x, y+75, 20, 20);
			g.setColor(Color.white);
			g.drawString(atk+"", x+5, y+90);
		}
	}//end draw
	//get side of hero
	public int getSide(){
		return side;
	}//end getSide
	//get hp of hero
	public int getHp(){
		return hp;
	}//end getHp
	//get atk of hero
	public int getAtk(){
		return atk;
	}//end getAtk
	//lose or gain health
	public void setHp(Board d, int change){
        if (change==60)//tfw you're too lazy to code a complicated exception for Alexstraza
            hp=15;
        else if (change < 0)
            hp += (change + mod);
        else
            hp += change;
        if (hp > hpMax)
            hp = hpMax;
        checkIsDead(d);
    }

	//change atk
	public void setAtk(int change){
		atk += change;
	}//end setAtk
	//change damage mod
	public void setMod(int change){
		mod+=change;
	}//end setMod
	//check if dead
	public void checkIsDead(Board d){
		if (hp <= 0)
			d.turnEnd(this);
	}//end checkIsDead
	public void battle(Board d,Minion card2){
		if(!attack && side==0){
			d.error();
		}
		if(!attack && side ==0){
			this.setHp(d, - card2.getAtk());
			card2.setHp(0, - this.getAtk());
			this.checkIsDead(d);
			card2.checkIsDead(d,card2);
			attack = false;
		}
	}
	//restets hero's attack
	public void reset(){
		attack = true;
	}
	//me go face
		public void smorc(Board d, Hero c){
			if(!attack && side==0)
				d.error();
			if(attack){
			c.setHp(d,-(this.getAtk()-c.mod));
			c.checkIsDead(d);
			attack = false;
			}
			
		}//end smorc
}//end Class Hero
