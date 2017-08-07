package ru.dso.nano.v3.analyzer.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Rule extends JComponent implements MouseMotionListener, MouseListener {
    private static final int SIZE = 25;
    private static final Color COLOR_BACKGROUND = Color.GRAY;
    private static final Color COLOR_KNOB_BORDER = Color.BLACK;
    private static final Color COLOR_KNOB = Color.LIGHT_GRAY;
    private static final Color COLOR_KNOB_SELECTED = new Color(193, 39, 36);
    private static final int KNOB_SIZE = 18;
    private static final int KNOB_HALF_SIZE = KNOB_SIZE / 2;

    private Color knob1Color = COLOR_KNOB;
    private Color knob2Color = COLOR_KNOB;
    private int knob1pos = 10;
    private int knob2pos = 50;
    private OscillogramView view;
    private java.util.List<KnobsListener> listeners = new ArrayList<>();

    public Rule(OscillogramView view) {
        this.view = view;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.knob1pos = view.getMeasureBegin();
        this.knob2pos = view.getMeasureEnd();
    }

    public void addKnobListener(KnobsListener listener) {
        this.listeners.add(listener);
    }

    public void setPreferredWidth(int pw) {
        setPreferredSize(new Dimension(pw, SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Rectangle drawHere = g.getClipBounds();
        g.setColor(COLOR_BACKGROUND);
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
        drawKnob(g, knob1pos, knob1Color);
        drawKnob(g, knob2pos, knob2Color);
    }

    private void drawKnob(Graphics g, int pos, Color color) {
        g.setColor(COLOR_KNOB_BORDER);
        g.drawRect(pos - (KNOB_SIZE / 2), SIZE - KNOB_SIZE, KNOB_SIZE, KNOB_SIZE);
        g.setColor(color);
        g.fillRect(pos - (KNOB_SIZE / 2) + 1, SIZE - KNOB_SIZE + 1, KNOB_SIZE, KNOB_SIZE);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getX() > KNOB_HALF_SIZE && e.getX() < this.getWidth() - KNOB_HALF_SIZE) {
            if(isKnobHit(knob1pos, e.getX(), e.getY())) {
                knob1pos = e.getX();
                this.repaint();
                view.setMeasureBegin(knob1pos);
                view.repaint();
                for(KnobsListener k : listeners) {
                    k.onKnob1PositionChanged(knob1pos);
                }
                return;
            }
            if(isKnobHit(knob2pos, e.getX(), e.getY())) {
                knob2pos = e.getX();
                this.repaint();
                view.setMeasureEnd(knob2pos);
                view.repaint();
                for(KnobsListener k : listeners) {
                    k.onKnob2PositionChanged(knob2pos);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(isKnobHit(knob1pos, e.getX(), e.getY())) {
            knob1Color = COLOR_KNOB_SELECTED;
            this.repaint();
            return;
        }
        if(isKnobHit(knob2pos, e.getX(), e.getY())) {
            knob2Color = COLOR_KNOB_SELECTED;
            this.repaint();
            return;
        }
        knob1Color = COLOR_KNOB;
        knob2Color = COLOR_KNOB;
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        knob1Color = COLOR_KNOB;
        knob2Color = COLOR_KNOB;
    }

    private boolean isKnobHit(int knobPos, int x, int y) {
        return x >= (knobPos - KNOB_HALF_SIZE) && x <= (knobPos + KNOB_HALF_SIZE) &&
                y <= SIZE && y >= SIZE - KNOB_SIZE;
    }

    public int getKnob1pos() {
        return knob1pos;
    }

    public void setKnob1pos(int knob1pos) {
        this.knob1pos = knob1pos;
    }

    public int getKnob2pos() {
        return knob2pos;
    }

    public void setKnob2pos(int knob2pos) {
        this.knob2pos = knob2pos;
    }
}
