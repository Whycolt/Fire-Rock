/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.io.Serializable;
import java.util.ArrayList;
//file used to store deck information
public class dk implements Serializable{
	
	private String name;
	private ArrayList<Integer> cards = new ArrayList<Integer>();
	private int cardNum;
	
	public dk(){
		cardNum = cards.size();
		name = "deck";
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String s){
		name = s;
	}
	
	//add cards
	public void add(int card){
		if (cardNum < 30){
			cards.add(card);
			cardNum++;
		}
		else
			System.out.println("Deck Full");
	}
	//remove cards
	public void remove(int card){
		if (cardNum >0){
			cards.remove(card);
			cardNum -=1;
		}
		else System.out.println("Deck Empty");
	}
	
	public int getCard(int i){
		return cards.get(i);
	}
	public int getCardNum(){
		return cardNum;
	}
}
