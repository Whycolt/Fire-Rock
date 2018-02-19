/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javafx.scene.text.*;

public class Board extends JPanel implements ActionListener{
//variables
	private BufferedImage boardImage,cardBack, coverImage,bm,tie,win,loss,rm,heroCover;
	private Card targeter,card,endTurn,boardB;
	int end;
	public Hero player;
	public Bot enemy;
	public Gear[] playerGear = new Gear[3];
	public Gear[] enemyGear = new Gear[3];
	public int[] attacked;
	public ArrayList<Minion> enemyMinion = new ArrayList<Minion>();
	public ArrayList<Minion> playerMinion = new ArrayList<Minion>();
	private ArrayList<Card> playerDeck = new ArrayList<Card>();
	private ArrayList<Card> playerHand = new ArrayList<Card>();
	private ArrayList<Card> enemyDeck = new ArrayList<Card>();
	private ArrayList<Card> enemyHand = new ArrayList<Card>();
	private int playerAction,enemyAction, targetE,targetP,rng;
	private boolean targeting,endScreen,cantatk,tarno,actno,error;
	private int turnEnd,framecount;//0 in neutral player turn, 1 in neutral enemy turn, 2 when enemy ends, wrap to 0
	public static Font smalletter;
	private HomeButton x;
	private boolean neutralState,isover;
	private JLabel winlab, tielab,losslab,cantAttack,inTar,noAct,playerCard,enemyCard;
	
//Constructor
 	public Board(int currentSetting){
 		end = -1;
 		endScreen = true;
 		neutralState = false;
 		isover = false;
 		cantatk = false;
 		tarno = false;
 		actno = false;
 		error = false;
 		try {
			boardImage = ImageIO.read(new File(Game.filePath +"boardImage.png"));
			coverImage = ImageIO.read(new File(Game.filePath +"coverImage.png"));
			cardBack = ImageIO.read(new File(Game.filePath +"CardBack.png"));
			bm = ImageIO.read(new File(Game.filePath +"menuButton.png"));
			tie = ImageIO.read(new File(Game.filePath +"tie.png"));
			rm= ImageIO.read(new File(Game.filePath +"returnButton.png"));
			win = ImageIO.read(new File(Game.filePath +"win.png"));
			loss = ImageIO.read(new File(Game.filePath +"loss.png"));
			heroCover = ImageIO.read(new File(Game.filePath +"HeroCover.png"));
 		} catch (IOException e) {
			e.printStackTrace();
		}//end try
 		playerCard = new JLabel("Cards In Deck: "+playerDeck.size(),JLabel.CENTER);
		playerCard.setBorder(BorderFactory.createEmptyBorder());
		playerCard.setBounds(220,510,150,100);
		playerCard.setFont(new Font("Default",Font.PLAIN,15));
		playerCard.setForeground(Color.white);
		add(playerCard);
		enemyCard = new JLabel("Cards In Deck: "+enemyDeck.size(),JLabel.CENTER);
		enemyCard.setBorder(BorderFactory.createEmptyBorder());
		enemyCard.setBounds(220,100,150,100);
		enemyCard.setFont(new Font("Default",Font.PLAIN,15));
		enemyCard.setForeground(Color.white);
		add(enemyCard);
 		cantAttack = new JLabel("CANNOT ATTACK RIGHT NOW",JLabel.CENTER);
		cantAttack.setBorder(BorderFactory.createEmptyBorder());
		cantAttack.setBounds(0,0,1314,710);
		cantAttack.setFont(new Font("Default",Font.PLAIN,80));
		cantAttack.setForeground(Color.yellow);
		add(cantAttack);
		cantAttack.hide();
		inTar = new JLabel("INVALID TARGET",JLabel.CENTER);
		inTar.setBorder(BorderFactory.createEmptyBorder());
		inTar.setBounds(0,0,1314,710);
		inTar.setFont(new Font("Default",Font.PLAIN,150));
		inTar.setForeground(Color.yellow);
		add(inTar);
		inTar.hide();
		noAct = new JLabel("NOT ENOUGH ACTION POINTS",JLabel.CENTER);
		noAct.setBorder(BorderFactory.createEmptyBorder());
		noAct.setBounds(0,0,1314,710);
		noAct.setFont(new Font("Default",Font.PLAIN,80));
		noAct.setForeground(Color.yellow);
		add(noAct);
		noAct.hide();
 		smalletter = new Font("Default",Font.PLAIN,9);
 		winlab = new JLabel("");
 		winlab.setIcon(new ImageIcon(win));
		winlab.setBorder(BorderFactory.createEmptyBorder());
		add(winlab);
		tielab = new JLabel("");
 		tielab.setIcon(new ImageIcon(tie));
		tielab.setBorder(BorderFactory.createEmptyBorder());
		add(tielab);
		losslab = new JLabel("");
 		losslab.setIcon(new ImageIcon(loss));
		losslab.setBorder(BorderFactory.createEmptyBorder());
		add(losslab);
		this.setLayout(null);
		this.setSize(1366, 768);
		x = new HomeButton(this);
		x.setContentAreaFilled(false);
		x.setBounds(100,105,25,25);
		add(x);
		attacked = new int[7];
		playerAction = 2;
		player = new Hero("whycolt",0,99,653,500);
		player.setContentAreaFilled(false);
		player.setBorder(BorderFactory.createEmptyBorder());
		player.addActionListener(this);
		player.setBounds(653,500,100,100);
		this.add(player);
		targeting = false;
		endTurn = new EndButton();
		//750->y,
		endTurn.setBounds(1142,330,100,50);
		endTurn.addActionListener(this);
		this.add(endTurn);
		boardB = new BoardDirt();
		boardB.setBounds(225, 200, 917, 301);
		boardB.addActionListener(this);
		boardB.setBorder(BorderFactory.createEmptyBorder());
		this.add(boardB);
	}//end Board
//Vital Methods
 	//starts prompt
 	public void error(){
 		error = true;
 		cantatk = true;
 		framecount = 0;
 	}
 	
	public void end(){
		endScreen = false;
	}
 	
 	public boolean checkEnd(){
 		return endScreen;
 	}
 	
 	//choose enemy hero
 	public void setEnemy(Bot a){
 		enemy = a;
		enemy.setBorder(BorderFactory.createEmptyBorder());
		enemy.setContentAreaFilled(false);
		enemy.addActionListener(this);
		enemy.setBounds(653,100,100,100);
		enemyDeck = enemy.getDeck();
		this.add(enemy);
 	}
 	//start game
 	public void startGame(){
		for (int i=0;i<5;i++){
			cDraw(player);
			cDraw(enemy);
		}
 	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		playerCard.setText("Cards In Deck: "+playerDeck.size());
		enemyCard.setText("Cards In Deck: "+enemyDeck.size());
		g.drawImage(Game.base, 0, 0, 1314, 710, null);
		g.drawImage(boardImage, 0, 0, 1314, 710, null);
		if (end != -1){
			x.setBounds(607, 341, 100, 20);
			g.drawImage(rm,607,341,100,20,null);
		}
		else
			g.drawImage(bm, 100,105,25,25, null);
		
		player.draw(g);
		enemy.draw(g);
		if (targeting){
			targeter.draw(g);
		}
		g.setColor(Color.GREEN);
		if (playerAction == 2)
			g.fillRect(753, 500, 414, 100);
		else if(playerAction == 1)
			g.fillRect(753, 500, 207, 100);
		if (enemyAction == 2)
			g.fillRect(753, 100, 414, 100);
		else if(enemyAction == 1)
			g.fillRect(753, 100, 207, 100);
		g.setColor(Color.BLACK);
		g.drawRect(753, 501, 414, 99);
		g.drawRect(753, 501, 207, 99);
		g.drawRect(753, 101, 414, 99);
		g.drawRect(753, 101, 207, 99);
		int s = enemyMinion.size();
		for (int i = 0; i < s; i++){  
			enemyMinion.get(i).setBounds(50+120*i ,30,96,100);
		}//end draw enemy minions
		s = playerMinion.size();
		for (int i = 0; i < s; i++){
			playerMinion.get(i).setBounds(50+120*i ,180,96,100);
		}//end draw player minion
		s = playerHand.size();
		for (int i = 0; i < s; i++){
			playerHand.get(i).setBounds(125+115*i ,610,96,137);
		}//end draw player minion
		for(int i = 0; i<3;i++){
			if (playerGear[i] != null){
				playerGear[i].setBounds(255+i*120, 501, 96, 100);
			}
			if (enemyGear[i] != null){
				enemyGear[i].setBounds(255+i*120, 104, 96, 100);
			}
			}
		if (targeter == player&&targeting){
			g.drawImage(heroCover,653,500,100,100, null);
		}
		g.drawImage(coverImage, 0, 0, 1314, 710, null);
		s = enemyHand.size();
		for (int i = 0; i < s; i++){  
			g.drawImage(cardBack,125+115*i ,-20,96,137,null);
		}//end draw enemy minions
		if (error){
			if(actno)
				noAct.show();
			else if(tarno)
				inTar.show();
			else if(cantatk) 
				cantAttack.show();
			framecount += 1;
			if (framecount > 50){
				error = false;
				actno = false;
				cantatk = false;
				tarno = false;
				cantAttack.hide();
				inTar.hide();
				noAct.hide();
			}
		}
		if (end == 0)
			tielab.setBounds(0, 0, 1314, 710);
		if (end == 1)
			losslab.setBounds(0, 0, 1314, 710);
		if (end == 2)
			winlab.setBounds(0, 0, 1314, 710);
		
	}//end paintComponent
	//Drawing the board
	
	public void setDeck(ArrayList<Card> a){
		playerDeck=a;
	}
	
//Interactive Methods
	
	//turn started
	public void turnStart(Hero hero){
		neutralState=true;
		cDraw(hero);
		if (hero==player){
			for (int i=0; i<playerHand.size(); i++)
			playerAction = 2;
			turnEnd = 0;
			player.reset();
			if (playerMinion.size()>0)
				for (int i = 0; i < playerMinion.size(); i++)
					playerMinion.get(i).reset();
		}
		else if(hero==enemy){
			enemyAction=2;
			turnEnd = 1;
			enemy.reset();
			if (enemyMinion.size()>0)
				for (int i = 0; i < enemyMinion.size(); i++){
					enemyMinion.get(i).reset();
					if (enemyMinion.get(i).effect==9){
						enemyMinion.get(i).setAtk(0, enemyMinion.get(i).getValue());
						enemyMinion.get(i).setHp(0, enemyMinion.get(i).getValue());
					}
				}
		}
		repaint();
		enemy.play(this);
	}//end turnStart
	
	public void cDraw(Hero h){
		if(h.getSide() == 0){
			if (playerDeck.size()>0){
				rng = (int)(Math.random()*playerDeck.size());
				if (playerHand.size() < 10){
					playerHand.add(playerDeck.get(rng));
					this.add(playerDeck.get(rng));
					playerDeck.get(rng).addActionListener(this);
				}
				playerDeck.remove(rng);	
			}
		}
		else{
			if (enemyDeck.size()>0){
				rng = (int)(Math.random()*enemyDeck.size());
				if (enemyHand.size() < 10){
					enemyHand.add(enemyDeck.get(rng));
					this.add(enemyDeck.get(rng));
					enemyDeck.get(rng).addActionListener(this);
				}
				enemyDeck.remove(rng);
			}	
		}
	}
	//turn end
	public void turnEnd(Hero hero){
		neutralState=false;
		targeting = false;
		if (hero==player)
			turnEnd = 1;
		else if (hero==enemy)
			turnEnd = 2;
	}//end turnEnd
	
	//Start Target ->Need revision
	public void targetStart(Card c){
		targeter = c;
		targetP = c.getTarget();
		targetE = c.getEffect();
		targeting = true;
	}//end targetStart
	
	//Check target/exit ->Need revision
	public boolean targetCheck(Card c){
		//0-> All
		if(targetP == 0)
			return true;
		//1-> All Enemies
		else if (targetP == 1 && c.getSide() == 1)
			return true;
		//2-> All Enemy Minions
		else if (targetP == 2 && c.getSide() == 1 && c instanceof Minion)
			return true;
		//3-> All Friendlies
		else if (targetP == 3 && c.getSide() == 0)
			return true;
		//4-> All Friendly Minions
		else if (targetP == 2 && c.getSide() == 0 && c instanceof Minion)
			return true;
		//5-> Heroes
				else if (targetP==5 && c instanceof Hero){
					return true;
				}
		else{
			error = true;
			tarno = true;
			framecount = 0;
			return false;
			}
	}//end targetCheck
	//Summoning minion
	//Playing minion from hand
	public void play(Minion c,Card t){
		if (c.getSide() == 0){
			if (turnEnd==0){
				if (playerMinion.size() < 7){
					if (c.getCost() <= playerAction){

						playerAction -= c.getCost();
						c.summon(this,t);
						playerMinion.add(c);
						playerHand.remove(c);
						boardB.add(c);
					}
					else{
						error = true;
						actno = true;
						framecount = 0;
					}
				}
			}
		}
		else{
			if (enemyMinion.size() < 7){
				if (c.getCost() <= enemyAction){ 
					enemyAction -= c.getCost();
					c.summon(this,t);
					enemyMinion.add(c);
					enemyHand.remove(c);
					this.remove(c);
					boardB.add(c);
				}
			}
		}
	}//end play
	//Playing spells from hand
	public void play(Spell c,Card t){
		if (c.getSide() == 0){
			if (c.getCost() <= playerAction){
				playerAction -= c.getCost();
				c.play(this,t);
				playerHand.remove(c);
				remove(c);
			}
			else{
				error = true;
				actno = true;
				framecount = 0;
			}
		}
		else{
			if (c.getCost() <= enemyAction){
				enemyAction -= c.getCost();
				c.play(this,t);
				enemyHand.remove(c);
				remove(c);
			}
		}
	}//end play
	public void play(Gear c,Card t){
		if (c.getSide() == 0){
			if (c.getCost() <= playerAction){
				playerAction -= c.getCost();
				if (playerGear[c.getSlot()]!=null){
					remove(playerGear[c.getSlot()]);
					playerGear[c.getSlot()].remove(player);}
				playerGear[c.getSlot()] = c;
				c.equip(player,this,t);
				c.removeActionListener(this);
				playerHand.remove(c);
			}
			else{
				error = true;
				actno = true;
				framecount = 0;
			}
		}
		else{
			if (c.getCost() <= enemyAction){
				enemyAction -= c.getCost();
				if (enemyGear[c.getSlot()]!=null)
					enemyGear[c.getSlot()].remove(enemy);
				enemyGear[c.getSlot()] = c;
				c.equip(enemy,this,t);
				c.removeActionListener(this);
				enemyHand.remove(c);
			}
			else{
				error = true;
				actno = true;
				framecount = 0;
			}
		}
	}//end play
	//Removing card from board
	public void destroy(Minion c){
		if (c.getSide() == 0){
			playerMinion.remove(c);
			boardB.remove(c);
		}
		else{
			enemyMinion.remove(c);
			boardB.remove(c);
		}
	}//end remove
//getter and setter
	//check end turn
	public int getEnd(){
		return(turnEnd);
	}//end getEnd
	public boolean over(){
		if(player.getHp() <= 0 && enemy.getHp() <= 0){
			isover = true;
			end = 0;
		}
		else if(player.getHp() <= 0){
			isover = true;
			end =  1;
		}
		else if(enemy.getHp() <= 0){
			isover = true;
			end = 2; 
		}
		return !(isover);
	}
	public ArrayList getEnemyHand(){
		return enemyHand;
	}
	public int getEnemyAction(){
		return enemyAction;
	}
	public void setEnemyAction(int x){
		enemyAction=x;
	}
	public boolean getState(){
		return neutralState;
	}
	
//vital
	//Checking Card pressed and targeting
	public void actionPerformed(ActionEvent e) {
		if (end == -1){
		if(turnEnd!=2){
		card = (Card)e.getSource();
		if (card == endTurn){
			if (turnEnd==0)
				turnEnd(player);
			else if (turnEnd==1)
				turnEnd(enemy);
			//else if (turnEnd==2)
				//turnEnd=0;
		}
		else if (!targeting){
			if(card != endTurn && card != boardB && ((Card) card).getSide() == 0){
				targetStart(card);
			}
			else if (card != endTurn && card != boardB && ((Card) card).getSide() == 1){
				targetStart(card);
				targetP = 10;
			}
		}
		else{
			if (card == targeter){
				targeting = false;
			}
			else if(targeter.getTarget() == -1 &&card == boardB&& (targeter instanceof Spell || (targeter instanceof Minion && ((Minion) targeter).getForm() == 0) || targeter instanceof Gear)){
				if (targeter instanceof Minion)
					play((Minion)targeter,null);
				else if (targeter instanceof Spell)
					play((Spell)targeter,null);
				else if (targeter instanceof Gear){
					play((Gear)targeter,null);
				}
				targeting = false;
			}
			else if (targeter instanceof Hero){
				if (targetCheck((Card)card)){
					if (card instanceof Minion)
						((Hero) targeter).battle(this, (Minion)card);
					else if (card instanceof Hero)
						((Hero)targeter).smorc(this, (Hero)card);
					targeting = false;
				}
				//invalid target
			}
			else if (targeter instanceof Minion && ((Minion)targeter).getForm() == 1 && card != endTurn && card != boardB){
				if (targetCheck((Card)card)){
					if (card instanceof Minion && ((Minion)card).getForm() == 1)
						((Minion) targeter).battle(this, (Minion)card);
					else if (card instanceof Hero)
						((Minion)targeter).smorc(this, (Hero)card);
					targeting = false;
				}
			}
			else if (targeter instanceof Minion && ((Minion)targeter).getForm() == 0 && card != endTurn && card != boardB){
				if (targetCheck((Card)card)){
					if (card instanceof Minion && ((Minion)card).getForm() == 1)
						play((Minion)targeter,(Card)card);
					else if (card instanceof Hero)
						play((Minion)targeter, (Hero)card);
					targeting = false;
				}
			}
			else if (targeter instanceof Gear && card != endTurn && card != boardB){
				if (targetCheck((Card)card)){
					if (card instanceof Minion  && ((Minion)card).getForm() == 1)
						play((Gear)targeter,(Card)card);
					else if (card instanceof Hero)
						play((Gear)targeter,(Hero)card);
					targeting = false;
				}
			}
			else if (targeter instanceof Spell && card instanceof Card&& card != endTurn && card != boardB){
				if (targetCheck((Card)card)){
					if (card instanceof Minion  && ((Minion)card).getForm() == 1)
						play((Spell)targeter,(Card)card);
					else if (card instanceof Hero)
						play((Spell)targeter,(Hero)card);
					targeting = false;
				}
			}
			targeting = false;
		}
		repaint();
		}
		if (turnEnd==2){
			turnEnd=0;
		}
		}
	}
	
	//applying card effects
	public void effect(Card c, Card t){
		Board b = this;
		switch(c.getEffect()){
		case 0: //buffing
			if (t instanceof Minion){
				Minion w = (Minion) t;
				w.setHp(0, c.getValue());
				w.setAtk(0, c.getValue());
			}
			break;
		case 1: //damage/healing
			if (t instanceof Minion){
				Minion w = (Minion) t;
				w.setHp(0, -c.getValue());
				w.checkIsDead(b, w);
				t = w;
			}
			else if (t instanceof Hero){
				Hero w = (Hero) t;
				w.setHp(b, -c.getValue());
				t = w;
			}
			repaint();
			break;
		case 2: //venomous
			break;
		case 3: //I BRING LIFE AND HOPE
			Hero w = (Hero) t;
			t.setHp(b, 60);
			break;
		case 4: //PUT YOUR FAITH IN THE LIGHT DUN DUN DUNDUNDUN
			Card AshBringer = new Card_AshBringer();
			playerHand.add(AshBringer);
			this.add(AshBringer);
			AshBringer.addActionListener(this);
			break;
		case 5: //Charge
			Minion k = (Minion)c;
			k.setAttack(true);
			c = k;
			break;
		case 6: //damage + draw
			if (t instanceof Minion){
				Minion l = (Minion) t;
				l.setHp(0, -c.getValue());
				l.checkIsDead(b, l);
				t = l;
			}
			else if (t instanceof Hero){
				Hero l = (Hero) t;
				l.setHp(b, -c.getValue());
				t = l;
			}
			for (int i=0; i<Math.abs(c.getValue())/2;i++){
				if (c.getSide()==0)
					cDraw(player);
				else
					cDraw(enemy);
			}
			break;
		case 7: //Card advantage
			for (int i=0; i<c.getValue();i++){
				if (c.getSide()==0)
					cDraw(player);
				else
					cDraw(enemy);
			}
			break;
		case 8: //Polymorph
			if (t instanceof Minion){
				Minion m = (Minion) t;
				destroy(m);
				Minion Sheep = new Card_Sheep();
				enemyHand.add(Sheep);
				this.add(Sheep);
				Sheep.addActionListener(this);
				b.play(Sheep, null);
				break;
			}
		case 9: break;
		}
	}
	//determine win condition and end screen
}//end class Board