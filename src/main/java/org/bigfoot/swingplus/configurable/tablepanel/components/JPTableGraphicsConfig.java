package org.bigfoot.swingplus.configurable.tablepanel.components;

import javax.swing.Icon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JPTableGraphicsConfig {
	
	private Icon ascendingIcon;
	
	private Icon descendingIcon;
	
	private boolean showHeaderBorder = true;
	
	private boolean showRowBorders = true;
	
	protected JPTableGraphicsConfig(){
		
	}
}
