package org.bigfoot.swingplus.form.components.impl.text;

import javax.swing.text.StyledDocument;

import org.bigfoot.swingplus.configurable.components.text.JPDocumentListener;
import org.bigfoot.swingplus.configurable.components.text.JPTextPane;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;
import org.bigfoot.swingplus.form.components.JPFormComponent;

import lombok.Getter;
import lombok.Setter;

public class JPFormTextPane extends JPTextPane implements JPFormComponent<String>
{
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private boolean updatable = true;

	public JPFormTextPane(String id)
	{
		super();
		setId(id);
	}

	public JPFormTextPane(String id, StyledDocument styledDocument)
	{
		super(styledDocument);
		setId(id);
	}

	public JPTextPane addDocumentListener(JPDocumentListener documentListener)
	{
		getDocument().addDocumentListener(documentListener);
		return this;
	}

	public JPTextPane addMouseListener(JPLambdaPopupMouseListener adapter)
	{
		super.addMouseListener(adapter);
		return this;
	}

	public JPTextPane addMouseListener(JPLambdaMouseClickListener adapter)
	{
		super.addMouseListener(adapter);
		return this;
	}

	@Override
	public void setComponentValue(String value)
	{
		setText(value);
	}

	@Override
	public String getComponentValue()
	{
		return getText();
	}
}
