package ru.dso.nano.v3.analyzer.gui;


import ru.dso.nano.v3.analyzer.resources.Strings;

import javax.swing.*;
import java.awt.*;

public class SelectionBriefPanel implements KnobsListener, SelectionListener {
    private JPanel panel;

    private JLabel labelMeasure1;
    private JLabel labelMeasure2;
    private JLabel labelMeasureLength;

    private JLabel labelSelectionStart;
    private JLabel labelSelectionEnd;
    private JLabel labelSelectionLength;

    private int measure1 = 0;
    private int measure2 = 0;
    private int selectionStart = 0;
    private int selectionEnd = 0;

    public SelectionBriefPanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelMeasure1 = new JLabel(Strings.MEASURE1.getString() + Strings.VALUE_EMPTY.getString());
        labelMeasure2 = new JLabel(Strings.MEASURE2.getString() + Strings.VALUE_EMPTY.getString());
        labelMeasureLength = new JLabel(Strings.MEASURE_LENGTH.getString() + Strings.VALUE_EMPTY.getString());
        labelSelectionStart = new JLabel(Strings.SELECTION_START.getString() + Strings.VALUE_EMPTY.getString());
        labelSelectionEnd = new JLabel(Strings.SELECTION_END.getString() + Strings.VALUE_EMPTY.getString());
        labelSelectionLength = new JLabel(Strings.SELECTION_LEN.getString() + Strings.VALUE_EMPTY.getString());

        panel.add(labelMeasure1);
        panel.add(labelMeasure2);
        panel.add(labelMeasureLength);
        panel.add(labelSelectionStart);
        panel.add(labelSelectionEnd);
        panel.add(labelSelectionLength);
    }

    public JPanel getPanel() {
        return this.panel;
    }

    @Override
    public void onKnob1PositionChanged(int newValue) {
        setMeasure1Value(newValue);
    }

    @Override
    public void onKnob2PositionChanged(int newValue) {
        setMeasure2Value(newValue);
    }

    @Override
    public void onSelectionStart(int value) {
        setSelectionStart(value);
    }

    @Override
    public void onSelectionReset() {
        selectionStart = 0;
        selectionEnd = 0;
        labelSelectionStart.setText(Strings.SELECTION_START.getString() + Strings.VALUE_EMPTY.getString());
        labelSelectionEnd.setText(Strings.SELECTION_END.getString() + Strings.VALUE_EMPTY.getString());
        labelSelectionLength.setText(Strings.SELECTION_LEN.getString() + Strings.VALUE_EMPTY.getString());
    }

    @Override
    public void onSelectionChanged(int newValue) {
        setSelectionEnd(newValue);
    }

    public void setMeasure1Value(int value) {
        measure1 = value;
        labelMeasure1.setText(Strings.MEASURE1.getString() + String.valueOf(value));
        labelMeasureLength.setText(Strings.MEASURE_LENGTH.getString() + String.valueOf(Math.abs(measure1 - measure2)));
    }

    public void setMeasure2Value(int value) {
        measure2 = value;
        labelMeasure2.setText(Strings.MEASURE2.getString() + String.valueOf(value));
        labelMeasureLength.setText(Strings.MEASURE_LENGTH.getString() + String.valueOf(Math.abs(measure1 - measure2)));
    }

    public void setSelectionStart(int value) {
        selectionStart = value;
        labelSelectionStart.setText(Strings.SELECTION_START.getString() + String.valueOf(selectionStart));
        labelSelectionLength.setText(Strings.SELECTION_END.getString() + String.valueOf(Math.abs(selectionStart - selectionEnd)));
    }

    public void setSelectionEnd(int value) {
        selectionEnd = value;
        labelSelectionEnd.setText(Strings.SELECTION_END.getString() + String.valueOf(value));
        labelSelectionLength.setText(Strings.SELECTION_LEN.getString() + String.valueOf(Math.abs(selectionStart - selectionEnd)));
    }
}
