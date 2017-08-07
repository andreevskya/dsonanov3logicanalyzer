package ru.dso.nano.v3.analyzer.resources;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum Strings {
    WINDOW_TITLE,
    STATISTIC_MAX,
    STATISTIC_MIN,
    STATISTIC_AVERAGE,
    STATISTIC_LEN,
    VALUE_EMPTY,
    MEASURE1,
    MEASURE2,
    MEASURE_LENGTH,
    SELECTION_START,
    SELECTION_END,
    SELECTION_LEN,
    ANALYZE_LOGIC_ONE_LEN,
    ANALYZE_LOGIC_ZERO_LEN,
    ANALYZE,
    MENU_FILE,
    MENU_FILE_OPEN,
    MENU_FILE_EXIT,
    MENU_EDIT,
    MENU_EDIT_CUT,
    MENU_EDIT_COPY,
    MENU_EDIT_PASTE,
    MENU_EDIT_DELETE,
    MENU_EDIT_SELECT_ALL;

    private static ResourceBundle bundle = ResourceBundle.getBundle("localization", Locale.getDefault());

    public String getString() {
        try {
            return bundle.getString(this.name());
        } catch(MissingResourceException mrex) {
            System.err.println("Missing resource \"" + this.name() + "\"");
            return "";
        }
    }
}
