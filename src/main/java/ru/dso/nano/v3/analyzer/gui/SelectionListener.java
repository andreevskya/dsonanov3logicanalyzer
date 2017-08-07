package ru.dso.nano.v3.analyzer.gui;


public interface SelectionListener {
    void onSelectionStart(int value);
    void onSelectionChanged(int newValue);
    void onSelectionReset();
}
