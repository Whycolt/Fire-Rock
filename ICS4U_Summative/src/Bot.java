/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Bot extends Hero{

	private String name;
	private int side;
	protected ArrayList<Card> deck = new ArrayList<Card>();
	
	public Bot(String name, int side,int maxhp) {
		super(name, side,maxhp,653,100);
	}
	//add Cards to bot deck
	public void addcard(Card c,int i){
		if (i == 0){
			return;
		}
		deck.add(c.clone());
		addcard(c,i-1);
	}
	
	
	public BufferedImage getImage(){
		return image;
	}
	
	//bot's turn play
	public void play(Board b){
		ArrayList<Card> hand=b.getEnemyHand();
		int action = b.getEnemyAction();
		ArrayList cost=new ArrayList();
		if (hand.size()>0){
			Card highest = hand.get(0);	//hand.indexOf(Collections.max(cost)));
			if (highest instanceof Minion){
				b.play((Minion)highest, null);
				//highest.form=1;
			}
			if (highest instanceof Spell)
				b.play((Spell)highest, null);
			if (highest instanceof Gear)
				b.play((Gear)highest, null);
		}
		
	}
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
}
