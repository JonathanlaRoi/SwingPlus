package org.bigfoot.swingplus.configurable.components.text;

public class JPFormats {
    public static final String ONLY_NUMBERS = "\\d+";

    public static final String DECIMAL_NUMBER = "^\\d+(\\.\\d+)";

    public static final String UPPER_CASE = "A-Z";

    public static final String LOWER_CASE = "a-z";

    public static final String HOUR = "([01]?[0-9]|2[0-3])";

    public static final String MINUTE = "[0-9]|[0-5][0-9]";

    public static final String TIME = HOUR + ":" + MINUTE;

    public static final String EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static final String TELEPHONE = "\\+?[0-9\\- ]+";

    public static final String TEXT = UPPER_CASE + LOWER_CASE;
}
