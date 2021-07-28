package org.bigfoot.swingplus.configurable.components.path;

import javax.swing.JFileChooser;

public enum JPFileSelectionType {
	FILES(JFileChooser.FILES_ONLY),
	DIRECTORIES(JFileChooser.DIRECTORIES_ONLY),
	ALL(JFileChooser.FILES_AND_DIRECTORIES);
	
	private final int value;
	
	private JPFileSelectionType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}
