/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class BoardDirt extends Card{
	
	int x,y;
	//making playing ground of cards
	public BoardDirt(){
		super("Board_Dirt", 9999, 9999, 9999, 9999, "Not sure how u got here",0);
		this.setContentAreaFilled(false);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, x, y, 1084, 440, null);
	}//end draw
}
