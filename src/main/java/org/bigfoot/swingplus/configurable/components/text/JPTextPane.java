package org.bigfoot.swingplus.configurable.components.text;

import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyledDocument;

import org.bigfoot.swingplus.configurable.JPConfigurable;

public class JPTextPane extends JTextPane implements JPConfigurable
{
	public JPTextPane()
	{
		super();
	}

	public JPTextPane(StyledDocument doc)
	{
		super(doc);
	}

	public JPTextPane setDocumentFilter(DocumentFilter documentFilter)
	{
		((AbstractDocument) this.getDocument()).setDocumentFilter(documentFilter);
		return this;
	}
}
