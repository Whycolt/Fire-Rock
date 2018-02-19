/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Card extends JButton{
//Variables
	protected String name,text;
	protected int cost, effect, target,side,value;
	protected BufferedImage image;
//Constructor
	public Card(String name, int cost, int effect, int target,int side,String text,int value){
		super();
		this.name = name;
		this.effect = effect;
		this.target = target;
		this.cost = cost;
		this.text = text;
		this.side = side;
		this.value=value;
		getImg();
	}//end Card
//Vital Methods
	//Getting Card Image
	public void getImg(){
		try {
			image = ImageIO.read(new File(Game.filePath +name.replaceAll("\\s","")+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
	}//end getImg
//Getters and Editors
	//Get Cost
	public void draw(Graphics g){
	}
	public int getCost(){
		return cost;
	}//end getCost
	//Get Card Type
	public int getType(){
		return(0);
	}//end getType
	//get Weapon side. 
	public int getSide(){
		return side;
	}//end getSide
	public int getTarget(){
		return target;
	}
	public int getEffect() {
		return effect;
	}
	public int getValue(){
		return value;
	}
	public String getName(){
		return name;
	}
	public void setHp(Board d, int change){
	}//end setHp
	//en
	//change atk
	public void setAtk(int change){
	}//end setAtk
	public void setAtk(int x,int change){
	}//end setAtk
	public void draw(Graphics g, int x, int y, int form){
		
	}
	public Card clone(){
		return this;
	}
}//end class Card