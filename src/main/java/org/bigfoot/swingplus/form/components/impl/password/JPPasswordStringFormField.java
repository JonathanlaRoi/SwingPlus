package org.bigfoot.swingplus.form.components.impl.password;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.form.components.JPFormComponent;

import javax.swing.*;

/**
 * TODO show/hide werkt niet goed
 *
 * @author Jonathan la Roi
 * @since 10/8/2020
 */
public class JPPasswordStringFormField extends JPasswordField implements JPFormComponent<String> {

    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private boolean updatable = true;

    @Getter
    private boolean showPassword;

    private char hide;

    public JPPasswordStringFormField(String id) {
        super();
        this.setId(id);
        hide = this.getEchoChar();
    }

    @Override
    public void setComponentValue(String value) {
        this.setText(value);
    }

    @Override
    public String getComponentValue() {
        return String.valueOf(getPassword());
    }

    @Override
    public void setEchoChar(char c) {
        super.setEchoChar(c);
        if (c != (char) 0) {
            hide = c;
        }
    }

    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
        if (showPassword) {
            this.setEchoChar((char) 0);
        } else {
            this.setEchoChar(hide);
        }
    }

    public void toggleShowPassword() {
        setShowPassword(!showPassword);
    }

    public void showPassword() {
        showPassword = true;
        this.setEchoChar((char) 0);
    }

    public void hidePassword() {
        showPassword = false;
        this.setEchoChar(hide);
    }
}