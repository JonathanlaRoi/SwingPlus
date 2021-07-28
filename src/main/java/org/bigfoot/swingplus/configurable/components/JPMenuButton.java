package org.bigfoot.swingplus.configurable.components;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * 
 * @author Jonathan la Roi
 *
 */
public class JPMenuButton extends JPButton {
	private JPopupMenu menu;
	
	public JPMenuButton(String titel){
		this(titel, null);
	}
	
	public JPMenuButton(String titel, Icon icon){
		super(titel, icon);
		this.setFocusable(false);
		
		menu = new JPopupMenu(){
			/**
			 * 
			 */
			private static final long serialVersionUID = -6309867311798105665L;

			@Override
            public Dimension getPreferredSize() {
                Dimension preferred = super.getPreferredSize();
                Dimension minimum = getMinimumSize();
                Dimension maximum = getMaximumSize();
                preferred.width = Math.min(Math.max(preferred.width, JPMenuButton.this.getWidth()),
                        maximum.width);
                preferred.height = Math.min(Math.max(preferred.height, minimum.height),
                        maximum.height);
                return preferred;
            }
		};
		
		addActionListener(e -> {
			if (!menu.isVisible()) {
				Point p = JPMenuButton.this.getLocationOnScreen();
				menu.setInvoker(JPMenuButton.this);
				menu.setLocation((int) p.getX(),
						(int) p.getY() + JPMenuButton.this.getHeight());
				menu.setVisible(true);
			} else {
				menu.setVisible(false);
			}

		});
	}
	
	public void addMenuItem(JMenuItem item){
		menu.add(item);
	}
	
	public void removeMenuItem(JMenuItem item){
		menu.remove(item);
	}
}
