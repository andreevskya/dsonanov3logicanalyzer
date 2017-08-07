package ru.dso.nano.v3.analyzer.gui;


import ru.dso.nano.v3.analyzer.OscillogramData;
import ru.dso.nano.v3.analyzer.resources.Strings;

import javax.swing.*;
import java.awt.*;

public class DataStatisticPanel {
    private JPanel panel;

    private JLabel labelMax;
    private JLabel labelMin;
    private JLabel labelAvg;
    private JLabel labelLength;

    public DataStatisticPanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelMax = new JLabel(Strings.STATISTIC_MAX.getString() + Strings.VALUE_EMPTY.getString());
        labelMin = new JLabel(Strings.STATISTIC_MIN.getString() + Strings.VALUE_EMPTY.getString());
        labelAvg = new JLabel(Strings.STATISTIC_AVERAGE.getString() + Strings.VALUE_EMPTY.getString());
        labelLength = new JLabel(Strings.STATISTIC_LEN.getString() + Strings.VALUE_EMPTY.getString());

        panel.add(labelMax);
        panel.add(labelMin);
        panel.add(labelAvg);
        panel.add(labelLength);
    }

    public void setStatistic(OscillogramData data) {
        labelMax.setText(Strings.STATISTIC_MAX.getString() + String.valueOf(data.getMax()));
        labelMin.setText(Strings.STATISTIC_MIN.getString() + String.valueOf(data.getMin()));
        labelAvg.setText(Strings.STATISTIC_AVERAGE.getString() + String.valueOf(data.getAverage()));
        labelLength.setText(Strings.STATISTIC_LEN.getString() + String.valueOf(data.getData().length));
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
