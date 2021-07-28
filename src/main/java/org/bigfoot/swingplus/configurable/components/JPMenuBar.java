package org.bigfoot.swingplus.configurable.components;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

/**
 * @author Jonathan la Roi
 * @since 14 march 2020
 */
public class JPMenuBar extends JMenuBar implements JPConfigurable {
	
	public void onConfigure(){
		for(int i = 0; i < this.getMenuCount() ; i++){
			JMenu menu = this.getMenu(i);
			if(menu instanceof JPConfigurable){
				((JPConfigurable)menu).onConfigure();
			}
		}
	}
	
	public void addMouseListener(JPLambdaPopupMouseListener adapter){
		super.addMouseListener(adapter);
	}
	
	public void addMouseListener(JPLambdaMouseClickListener adapter){
		super.addMouseListener(adapter);
	}
}
