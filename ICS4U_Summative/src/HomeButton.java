/**
 * name: Colt Ma, Matthew Ho 
 * teacher: Mrs.Strelkovska
 * course: ICS4U
 * date: Jan,18,2017
 * Assignment: ICS Summative
 * Description: Virtual Card Game Inspired by Hearthstone and mechanics of Dungeons and Dragons 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class HomeButton extends JButton implements ActionListener{

	private Board b;
	//just a button
	public HomeButton(Board on){
		super();
		b = on;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (b.end == -1){
		b.player.setHp(b, -1000);
		b.enemy.setHp(b, -1000);
		b.player.checkIsDead(b);
		}
		else{
			b.end();
		}
	}
	
	
}
