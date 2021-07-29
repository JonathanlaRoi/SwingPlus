package org.bigfoot.swingplus.configurable.components;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.JMenu;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.JPContainer;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

/**
 * @author Jonathan la Roi
 * @since 14 march 2020
 */
public class JPMenu extends JMenu implements JPContainer {
	
    public JPMenu() {
        super("");
    }

    
    public JPMenu(String s) {
        super(s);
    }

   
    public JPMenu(Action a) {
        super(a);
    }

    @Override
	public void onConfigure(){
		for(Component comp : this.getMenuComponents()){
			if(comp instanceof JPConfigurable){
				((JPConfigurable) comp).onConfigure();
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
