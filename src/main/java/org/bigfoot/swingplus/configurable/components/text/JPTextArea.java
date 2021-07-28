package org.bigfoot.swingplus.configurable.components.text;

import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.components.text.filter.JPLimitDocumentFilter;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

public class JPTextArea extends JTextArea implements JPConfigurable{
	private static final long serialVersionUID = -4467229866229321110L;
	@Deprecated
	private Integer limit = 50;
	@Deprecated
	private boolean enforceLimit = true;
	
	public JPTextArea(){
		this(null, false, null);
	}

	@Deprecated
	public JPTextArea(Integer maxLength){
		this(null, maxLength!=null, maxLength);
	}
	
	public JPTextArea(String text){
		this(text, false, null);
	}
	
	@Deprecated
	public JPTextArea(String text, boolean enforceLimit, Integer maxLength){
		super(text);
		setLineWrap(true);
		setWrapStyleWord(true);
		setMaxLength(maxLength);
		setEnforceLimit(enforceLimit && maxLength!=null);
	}
	
	public void setEnforceLimit(boolean b){
		this.enforceLimit = b && getMaxLength()!=null;
		setMaxLength(getMaxLength());
	}
	
	public void setMaxLength(Integer i){
		this.limit = i;
		if(this.enforceLimit && limit != null && this.limit < this.getText().length()){
			this.setText(this.getText().substring(0, i));
		}
		JPLimitDocumentFilter documentFilter = new JPLimitDocumentFilter(i);
		documentFilter.setEnforceLimit(enforceLimit);
		
		((AbstractDocument)this.getDocument()).setDocumentFilter(documentFilter);
	}
	
	public Integer getMaxLength(){
		return this.limit;
	}

	@Override
	public void onConfigure() {
		
	}
	
	public JPTextArea addMouseListener(JPLambdaPopupMouseListener adapter){
		super.addMouseListener(adapter);
		return this;
	}
	
	public JPTextArea addMouseListener(JPLambdaMouseClickListener adapter){
		super.addMouseListener(adapter);
		return this;
	}
	
	public JPTextArea setDocumentFilter(DocumentFilter documentFilter){
		((AbstractDocument)this.getDocument()).setDocumentFilter(documentFilter);
		return this;
	}
}
