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
public class JPPasswordFormField extends JPasswordField implements JPFormComponent<char[]> {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean updatable = true;

    @Getter
    private boolean showPassword = false;

    private char hide;

    public JPPasswordFormField(String id) {
        super();
        setId(id);
        hide = this.getEchoChar();
    }

    @Override
    public void setComponentValue(char[] value) {
        //Value kan niet worden geset, bij update van form wordt de value geleegd
        //Dit omdat je wachtwoorden niet moet vertalen naar String
        this.setText(null);
    }

    @Override
    public char[] getComponentValue() {
        return this.getPassword();
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
