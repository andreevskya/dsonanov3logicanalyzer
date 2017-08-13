package ru.dso.nano.v3.analyzer;

import ru.dso.nano.v3.analyzer.gui.*;
import ru.dso.nano.v3.analyzer.resources.Strings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AppMain implements ActionListener {
    private static final int MAIN_WINDOW_WIDTH = 640;
    private static final int MAIN_WINDOW_HEIGHT = 320;

    private JFrame frame;
    private JPanel mainPanel;
    private DataStatisticPanel dataStatisticPanel;
    private JScrollPane oscillogramViewScrollPane;
    private Rule selectionRule;
    private OscillogramView oscillogramView;
    private SelectionBriefPanel selectionBriefPanel;
    private AnalyzePanel analyzePanel;
    private JTextArea textArea = createLogicAnalyzeResultArea();
    private MainMenu menu;

    private OscillogramData oscillogramData = null;
    private LogicAnalyzer logicAnalyzer = new LogicAnalyzer();

    public AppMain(File file) {
        this();
        open(file);
    }

    public AppMain() {
        createGui();
    }

    private void createGui() {
        frame = new JFrame();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        dataStatisticPanel = new DataStatisticPanel();
        mainPanel.add(dataStatisticPanel.getPanel(), BorderLayout.PAGE_START);

        oscillogramView = new OscillogramView();
        oscillogramViewScrollPane = new JScrollPane(oscillogramView);
        oscillogramViewScrollPane.getHorizontalScrollBar().addAdjustmentListener(oscillogramView);
        oscillogramView.setParentScrollBar(oscillogramViewScrollPane.getHorizontalScrollBar());

        selectionRule = new Rule(oscillogramView);
        selectionRule.setPreferredWidth(oscillogramView.getPreferredSize().width);
        oscillogramViewScrollPane.setColumnHeaderView(selectionRule);
        oscillogramViewScrollPane.setPreferredSize(new Dimension(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));
        mainPanel.add(oscillogramViewScrollPane, BorderLayout.CENTER);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        selectionBriefPanel = new SelectionBriefPanel();
        analyzePanel = new AnalyzePanel();
        selectionBriefPanel.setMeasure1Value(oscillogramView.getMeasureBegin());
        selectionBriefPanel.setMeasure2Value(oscillogramView.getMeasureEnd());
        containerPanel.add(selectionBriefPanel.getPanel());
        containerPanel.add(analyzePanel.getPanel());
        JScrollPane textAreaScroll = new JScrollPane(textArea);
        containerPanel.add(textAreaScroll);
        mainPanel.add(containerPanel, BorderLayout.PAGE_END);

        selectionRule.addKnobListener(selectionBriefPanel);
        oscillogramView.addSelectionListener(selectionBriefPanel);
        oscillogramView.addSelectionListener(analyzePanel);
        analyzePanel.addButtonListener(this);

        menu = new MainMenu(this);
        frame.setJMenuBar(menu.getMenu());
        frame.setTitle(Strings.WINDOW_TITLE.getString());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JTextArea createLogicAnalyzeResultArea() {
        JTextArea area = new JTextArea(5, 20);
        area.setLineWrap(true);
        return area;
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void open(File file) {
        try {
            oscillogramData = CsvParser.parseData(file);
        } catch(IOException ioex) {
            ioex.printStackTrace(System.err);
            return;
        }
        dataStatisticPanel.setStatistic(oscillogramData);
        oscillogramView.setOscillogramData(oscillogramData);
        analyzePanel.setMaxValue(oscillogramData.getData().length);

        oscillogramView.invalidate();
        oscillogramViewScrollPane.repaint();
    }

    public void exit() {
        System.exit(0);
    }

    public void deleteSelected() {
        if(oscillogramView.getSelectionBegin() != oscillogramView.getSelectionEnd()) {
            int start = Math.min(oscillogramView.getSelectionBegin(), oscillogramView.getSelectionEnd());
            int end = Math.max(oscillogramView.getSelectionBegin(), oscillogramView.getSelectionEnd());
            oscillogramData.removeRange(start, end);
            dataStatisticPanel.setStatistic(oscillogramData);
            oscillogramView.setOscillogramData(oscillogramData);
            analyzePanel.setMaxValue(oscillogramData.getData().length);

            oscillogramView.resetSelection();
            oscillogramView.invalidate();
            oscillogramViewScrollPane.repaint();
        }
    }

    public void selectAll() {
        if(oscillogramData == null || oscillogramData.getData().length == 0) {
            return;
        }
        oscillogramView.setSelectionBegin(0);
        oscillogramView.setSelectionEnd(oscillogramData.getData().length);
        oscillogramView.invalidate();
        oscillogramViewScrollPane.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int start = Math.min(oscillogramView.getSelectionBegin(), oscillogramView.getSelectionEnd());
        int end = Math.max(oscillogramView.getSelectionBegin(), oscillogramView.getSelectionEnd());
        boolean[] logicData = logicAnalyzer.analyze(oscillogramData, start, end, analyzePanel.getLogicOne(), analyzePanel.getLogicZero());
        StringBuilder data = new StringBuilder();
        for(boolean b : logicData) {
            data.append(b ? "1" : "0");
        }
        data.append("\n");
        textArea.append(data.toString());
    }
}
