package ru.dso.nano.v3.analyzer.gui;

import ru.dso.nano.v3.analyzer.OscillogramData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OscillogramView extends JComponent implements MouseListener, MouseMotionListener, AdjustmentListener {
    private static final int UNIT = 20;
    private static final Color COLOR_BACKGROUND = Color.BLACK;
    private static final Color COLOR_GRID = Color.GRAY;
    private static final Color COLOR_OSCILLOGRAM = Color.GREEN;
    private static final Color COLOR_MEASURE = Color.WHITE;
    private static final Color COLOR_SELECT = new Color(0, 0, 255, 127);
    private static final int WIDTH_DEFAULT = 320;
    private static final int HEIGHT_DEFAULT = 240;
    private static final int SCROLL_TRIGGERING_OUTSIDE_DISTANCE = 50;

    private int measureBegin = 0;
    private int measureEnd = 0;
    private int measureAdjustment = 0;
    private int selectionBegin = 0;
    private int selectionEnd = 0;
    private boolean selectionInProcess = false;
    private int outsideScroll = 0;

    private OscillogramData data;
    private JScrollBar parentScrollBar;

    private java.util.List<SelectionListener> selectionListeners = new ArrayList<>();

    public OscillogramView() {
        super();
        this.setPreferredSize(new Dimension(WIDTH_DEFAULT, HEIGHT_DEFAULT));
        setupInitialMeasurePosition();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void setParentScrollBar(JScrollBar bar) {
        this.parentScrollBar = bar;
    }

    public void addSelectionListener(SelectionListener listener) {
        selectionListeners.add(listener);
    }

    public void setOscillogramData(OscillogramData data) {
        this.data = data;
        if(data != null) {
            this.setPreferredSize(new Dimension(data.getData().length, HEIGHT_DEFAULT));
        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        measureAdjustment = e.getValue();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            selectionBegin = e.getX();
            selectionEnd = e.getX();
            selectionInProcess = true;
            for(SelectionListener listener : selectionListeners) {
                listener.onSelectionStart(selectionBegin);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            selectionInProcess = false;
            this.repaint();
        }
        if(selectionBegin == selectionEnd) {
            for(SelectionListener listener : selectionListeners) {
                listener.onSelectionReset();
            }
        }
        outsideScroll = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        outsideScroll = e.getX();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(selectionInProcess) {
            if(outsideScroll > 0 && e.getX() - outsideScroll > SCROLL_TRIGGERING_OUTSIDE_DISTANCE) {
                if(parentScrollBar != null) {
                    parentScrollBar.setValueIsAdjusting(true);
                    parentScrollBar.setValue(parentScrollBar.getValue() + parentScrollBar.getBlockIncrement());
                    parentScrollBar.setValueIsAdjusting(false);
                }
            }
            if(selectionEnd < data.getData().length) {
                selectionEnd = e.getX();
                for (SelectionListener listener : selectionListeners) {
                    listener.onSelectionChanged(selectionEnd);
                }
            }
            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        drawBackground(g);
        if(data != null) {
            drawOscillogram(g, data);
        }
        drawSelection(g);
        drawMeasure(g);
    }

    private void drawBackground(Graphics g) {
        g.setColor(COLOR_BACKGROUND);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(COLOR_GRID);
        for(int x = 0; x < this.getWidth(); x+= UNIT) {
            g.drawLine(x, 0, x, this.getHeight());
        }
        for(int y = 0; y < this.getHeight(); y += UNIT) {
            g.drawLine(0, y, this.getWidth(), y);
        }
    }

    private void drawOscillogram(Graphics g, OscillogramData od) {
        int x = 0;
        g.setColor(COLOR_OSCILLOGRAM);
        for(int i = 1; i < od.getData().length; ++i) {
            g.drawLine(x,this.getHeight() - (data.getData()[i - 1] - data.getMin()),
                    x,this.getHeight() - (data.getData()[i] - data.getMin())
            );
            ++x;
        }
    }

    private void drawSelection(Graphics g) {
        int start = Math.min(selectionBegin, selectionEnd);
        int end = Math.max(selectionBegin, selectionEnd);
        if(start == end) {
            return;
        }
        g.setColor(COLOR_SELECT);
        g.fillRect(start, 0, Math.abs(start - end), this.getHeight());
    }

    private void drawMeasure(Graphics g) {
        g.setColor(COLOR_MEASURE);
        g.drawLine(measureBegin + measureAdjustment, 0, measureBegin + measureAdjustment, this.getHeight());
        g.drawLine(measureEnd + measureAdjustment, 0, measureEnd + measureAdjustment, this.getHeight());
    }

    private void setupInitialMeasurePosition() {
        measureBegin = (WIDTH_DEFAULT / 100) * 10;
        measureEnd = (WIDTH_DEFAULT / 100) * 30;
    }

    public int getMeasureBegin() {
        return measureBegin;
    }

    public void setMeasureBegin(int measureBegin) {
        this.measureBegin = measureBegin;
    }

    public int getMeasureEnd() {
        return measureEnd;
    }

    public void setMeasureEnd(int measureEnd) {
        this.measureEnd = measureEnd;
    }

    public int getSelectionBegin() {
        return selectionBegin;
    }

    public void setSelectionBegin(int selectionBegin) {
        this.selectionBegin = selectionBegin;
    }

    public int getSelectionEnd() {
        return selectionEnd;
    }

    public void setSelectionEnd(int selectionEnd) {
        this.selectionEnd = selectionEnd;
    }
}
