package ru.dso.nano.v3.analyzer.gui;


import ru.dso.nano.v3.analyzer.resources.Strings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AnalyzePanel implements SelectionListener {
    private static final int SPINNER_WIDTH = 60;
    private JPanel panel;

    private JSpinner spinnerLogicOne;
    private JSpinner spinnerLogicZero;
    private JButton buttonAnalyze;

    public AnalyzePanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(Strings.ANALYZE_LOGIC_ONE_LEN.getString()));

        spinnerLogicOne = createSpinner();
        spinnerLogicOne.setValue(3);
        panel.add(spinnerLogicOne);

        panel.add(new JLabel(Strings.ANALYZE_LOGIC_ZERO_LEN.getString()));

        spinnerLogicZero = createSpinner();
        spinnerLogicZero.setValue(10);
        panel.add(spinnerLogicZero);

        buttonAnalyze = new JButton(Strings.ANALYZE.getString());
        panel.add(buttonAnalyze);
        buttonAnalyze.setEnabled(false);
    }

    public JPanel getPanel() {
        return panel;
    }

    private JSpinner createSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        Dimension d = spinner.getPreferredSize();
        d.width = SPINNER_WIDTH;
        spinner.setPreferredSize(d);
        return spinner;
    }

    public void setMaxValue(int value) {
        if((int)spinnerLogicOne.getValue() > value) {
            spinnerLogicOne.setValue(value);
        }
        if((int)spinnerLogicZero.getValue() > value) {
            spinnerLogicZero.setValue(value);
        }
        SpinnerNumberModel m = (SpinnerNumberModel)spinnerLogicOne.getModel();
        m.setMaximum(value);
        m = (SpinnerNumberModel)spinnerLogicZero.getModel();
        m.setMaximum(value);
    }

    public void addButtonListener(ActionListener listener) {
        buttonAnalyze.addActionListener(listener);
    }

    @Override
    public void onSelectionStart(int value) {

    }

    @Override
    public void onSelectionChanged(int newValue) {
        buttonAnalyze.setEnabled(true);
    }

    @Override
    public void onSelectionReset() {
        buttonAnalyze.setEnabled(false);
    }

    public int getLogicOne() {
        return (int)spinnerLogicOne.getValue();
    }

    public int getLogicZero() {
        return (int)spinnerLogicZero.getValue();
    }
}
