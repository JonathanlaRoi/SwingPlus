package org.bigfoot.swingplus.configurable.components.path;

import java.awt.Component;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.components.JPButton;
import org.bigfoot.swingplus.configurable.components.text.JPTextField;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

public class JPFileSelector extends JPanel implements JPConfigurable {
	
	@Getter
	private JPButton btn;
	@Getter
	private JPTextField textfield;
	private Path path;
	private JPFileSelectionType fileSelectionType = JPFileSelectionType.ALL;
	@Setter
	@Getter
	private Component focusComponent;
	
	public JPFileSelector(JPFileSelectionType type){
		this();
		this.setFileSelectionType(type);
	}
	
	public JPFileSelector() {
		super();
		setLayout(new MigLayout("insets 0", "[grow][shrink]", "[grow]"));
		
		textfield = new JPTextField();
		textfield.setEditable(false);
		btn = new JPButton("...");
		
		add(textfield, "grow");
		add(btn, "shrinkx, growy");
		
		btn.addActionListener(event -> {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(fileSelectionType.getValue());
			if (path != null) {
				fc.setCurrentDirectory(getPath().toFile());
			}

			int returnVal = fc.showOpenDialog(focusComponent != null ? focusComponent : btn);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				setPath(fc.getSelectedFile().toPath());
			}
		});
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
		textfield.setText(path != null ? path.toString() : null);
	}

	@Override
	public void setEnabled(boolean enabled){
		btn.setEnabled(enabled);
	}
	
	@Override
	public boolean isEnabled(){
		return btn.isEnabled();
	}

	public JPFileSelectionType getFileSelectionType() {
		return fileSelectionType;
	}

	public void setFileSelectionType(JPFileSelectionType fileSelectionType) {
		this.fileSelectionType = fileSelectionType;
	}
	
	public void addMouseListener(JPLambdaPopupMouseListener adapter){
		super.addMouseListener(adapter);
	}
	
	public void addMouseListener(JPLambdaMouseClickListener adapter){
		super.addMouseListener(adapter);
	}
}
