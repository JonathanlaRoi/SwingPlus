package org.bigfoot.swingplus.lang;

import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class JPLang {

    @Getter
    @Setter
    private static String defaultLocalePath;

    public static void setLocale(Locale locale) {
        Locale.setDefault(locale);
    }

    public static Locale getLocale() {
        return Locale.getDefault();
    }
}
