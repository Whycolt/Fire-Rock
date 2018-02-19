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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeckBuilder extends JPanel implements ActionListener, Serializable{

	private JButton menu, pre, next,save;
	private JButton slots[] = new JButton[10];
	private JButton cards[] = new JButton[30];
	private int cardindex[] = new int[30];
	private static ArrayList<Card>  col = new ArrayList<Card>();
	private boolean edditing;
	private int framecount;
	private Game g;
	private JLabel full;
	private int deck,page,am;
	private String deckName;
	private dk[] loaded = new dk[10];
	private JTextField name;
	private BufferedImage image,bm,bp,bn,bs,bdeck;
	private boolean warnFull;
	
	public DeckBuilder(Game in){
		g = in;
		warnFull = false;
		try {
			image = ImageIO.read(new File(Game.filePath +"DeckBuilder.png"));
			bm = ImageIO.read(new File(Game.filePath +"menuButton.png"));
			bp = ImageIO.read(new File(Game.filePath +"preButton.png"));
			bn = ImageIO.read(new File(Game.filePath +"nextButton.png"));
			bs = ImageIO.read(new File(Game.filePath +"saveButton.png"));
			bdeck = ImageIO.read(new File(Game.filePath +"deckButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
		edditing = false;
		page = 0;
		this.setSize(1314, 710);
		this.setLayout(null);
		full = new JLabel("DECK FULL",JLabel.CENTER);
		full.setBorder(BorderFactory.createEmptyBorder());
		full.setBounds(0,0,1314,710);
		full.setFont(new Font("Default",Font.PLAIN,150));
		full.setForeground(Color.yellow);
		add(full);
		full.hide();
		loaded[9] = new dk();
		name = new JTextField(10);
		name.addActionListener(this);
		name.setBounds(920,550, 280, 40);
		menu = new JButton("");
		menu.addActionListener(this);
		menu.setContentAreaFilled(false);
		menu.setBounds(135,105,25,25);
		add(menu);
		pre = new JButton("");
		pre.addActionListener(this);
		pre.setContentAreaFilled(false);
		pre.setBounds(135,135,25,444);
		add(pre);
		next = new JButton("");
		next.addActionListener(this);
		next.setContentAreaFilled(false);
		next.setBounds(850,105,25,474);
		add(next);
		save = new JButton("");
		save.addActionListener(this);
		save.setContentAreaFilled(false);
		save.setBounds(920,510, 280, 40);
		deck = 0;
		g.c.repaint();
		for (int i = 0; i < 9;i++){
			loaded[i] = load();
			deck+=1;
		}
		for(int i = 0; i<9;i++){
			slots[i] = new JButton(loaded[i].getName());
			add(slots[i]);
			slots[i].addActionListener(this);
			slots[i].setContentAreaFilled(false);
		}
		//adding cards to deckbuilder
		col.add(new Card_Backstreet_Alchemist());
		col.add(new Card_Boulderfist_Ogre());
		col.add(new Card_Chained_Ethereal());
		col.add(new Card_Chillwind_Yeti());
		col.add(new Card_Devoted_Cleric());
		col.add(new Card_Dragon_Queen());
		col.add(new Card_Envenomed_Viper());
		col.add(new Card_Flamewreathed_Faceless());
		col.add(new Card_Flickering_Flame());
		col.add(new Card_FWA());
		col.add(new Card_Ice_Lance());
		col.add(new Card_Lord_Of_Winds());
		col.add(new Card_Party_Deeps());
		col.add(new Card_Party_Tank());
		col.add(new Card_Polymorph());
		col.add(new Card_Pot_Of_Greed());
		col.add(new Card_Reno_Jackson());
		col.add(new Card_River_Croc());
		col.add(new Card_Unleashed_Kraken());
		col.add(new Card_Lay_On_Hands());
		col.add(new Card_Tower_Shield());
		col.add(new Card_Archmages_Greatstaff());
		col.add(new Card_Off_Hand_Dagger());
		col.add(new Card_Flimsy_Shiv());
		col.add(new Card_Cavalry_Lance());
		col.add(new Card_Blessed_Longsword());
		col.add(new Card_Broadshield());
		col.add(new Card_Paladin_Lord());
		for(int i = 0; i < col.size();i++){
			col.get(i).addActionListener(this);
		}
		page = 0;
		display();
		for(int i = 0; i<30;i++){
			cards[i] = new JButton("Empty");
			cards[i].addActionListener(this);
			cards[i].setBounds(920+i%3*90, 120+i/3*40, 87, 30);
		}
		normal();
	}
	public void paintComponent(Graphics g){
		g.drawImage(Game.base, 0, 0, 1314, 710, null);
		g.drawImage(image, 0, 0, 1314, 710, null);
		g.drawImage(bm, 135,105,25,25, null);
		g.drawImage(bp, 135,135,25,444, null);
		g.drawImage(bn, 850,105,25,474, null);
		if (edditing){
			g.drawImage(bs,920,510, 280, 40, null);
		}
		else{
			for(int i = 0; i<9;i++){
				g.drawImage(bdeck,920,125 + i*50, 280, 40, null);
			}
		}
		//deck full prompt
		if (warnFull){
			full.show();
			framecount += 1;
			if (framecount > 50){
				warnFull = false;
				full.hide();
			}
		}
	}
	
	//refreshes cards in collection
	public void display(){
		am = 6;
		if (am > col.size() - page*6)
			am = col.size() - page*6;
		for(int i = 0; i < am;i++){
			add(col.get(page*6+i));
			col.get(page*6+i).setBounds(250 + (i%3 * 200),160+ i/3*230,96,137);
		}

	}
	//removes current cards in collection
	public void removeDisplay(){
		am = 6;
		if (am > col.size() - page*6)
			am = col.size() - page*6;
		for(int i = 0; i < am;i++){
			add(col.get(page*6+i));
			col.get(page*6+i).setBounds(-10000 + (i%3 * 200),-1000+ i/3*230,96,137);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		loaded[deck].setName(name.getText());
		if(e.getSource() instanceof JTextField)
			return;
		JButton b = (JButton) e.getSource();
		if (b instanceof Card){
			for(int i = 0; i < col.size();i++){
				if (b == col.get(i)){
					if (loaded[deck].getCardNum() < 30){
						loaded[deck].add(i);
						if (edditing)
							inDeck();
					}
					else{
						warnFull = true;
						framecount = 0;
					}
				}
			}
		}
		for (int i = 0; i<30;i++){
			if (b==cards[i] && cards[i].getText().compareTo("Empty")!= 0){
				loaded[deck].remove(i);
				inDeck();
				break;
			}
		}
		if (b == menu){
			g.c.remove(g.d);
			g.c.add(g.m);
			g.c.revalidate();
			g.c.repaint();
		}
		if (b == next){
			if (col.size() > (page+1)*6){
				removeDisplay();
				page+=1;
				display();
			}
			g.c.revalidate();
			g.c.repaint();
		}
		if (b == pre){
			if (page > 0){
				removeDisplay();
				page -=1;
				display();
			}
		}
		if (b == save){
			try {
		         FileOutputStream fileOut = new FileOutputStream(Game.filePath + "deck" + deck +".dk");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(loaded[deck]);
		         out.close();
		         fileOut.close();
		      }catch(IOException i) {
		         i.printStackTrace();
		      }
				g.ds.update();
				normal();
		}
		for (int i = 0; i<9;i++){
			if (b == slots[i]){
				deck = i;
				for(int j = 0; j < 30;j++){
					add(cards[j]);
				}
				editing();
				inDeck();
			}
		}
	}
	
	//loading in dk files
	public dk load(){
		 dk s = null;
	      try {
	         FileInputStream fileIn = new FileInputStream(Game.filePath + "deck" + deck + ".dk");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         s = (dk) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	      }catch(ClassNotFoundException c) {
	         System.out.println("dk file missing");
	         c.printStackTrace();
	      }
	      return s;
	}
	
	//switch to editor mode
	public void editing(){
		add(save);
		add(name);
		edditing = true;
		name.setText(loaded[deck].getName());
		for(int i = 0; i<9;i++){
			remove(slots[i]);
		}
		inDeck();
		g.c.revalidate();
		g.c.repaint();
		}
	//switch to normal mode
	public void normal(){
		deck = 9;
		edditing = false;
		outDeck();
		remove(save);
		remove(name);
		for(int i = 0; i<9;i++){
			slots[i].setText((loaded[i].getName()));
			slots[i].setBounds(920,125 + i*50, 280, 40);
			add(slots[i]);
		}
		g.c.revalidate();
		g.c.repaint();
	}
	
	//displaying cards within a deck
	public void inDeck(){
		for(int i = 0; i < 30;i++)
			cards[i].setText("Empty");
		for(int i = 0;i < loaded[deck].getCardNum();i++){
			this.setFont(Board.smalletter);
			cards[i].setText(translate(loaded[deck].getCard(i)).getName());
			//cardindex[i] = loaded[deck].getCard(i);
		}
		g.c.revalidate();
		g.c.repaint();
	}
	//removing cards from panel 
	public void outDeck(){
		for(int i = 0; i < 30;i++)
			remove(cards[i]);
		g.c.revalidate();
		g.c.repaint();
	}
	
	//translating int to card
	public static Card translate(int i){
		return (col.get(i).clone());
	}
	
}
