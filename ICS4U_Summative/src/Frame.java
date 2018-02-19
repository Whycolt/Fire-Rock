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
import javax.swing.*;

public class Frame extends JFrame{
//Constructor
	public Frame(){
		super("FIRE ROCK");
		Container c = getContentPane();
		Game g = new Game(c);
		this.setSize(1314, 740);
	}//end Frame
}//end class Main