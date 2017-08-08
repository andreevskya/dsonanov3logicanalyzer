package ru.dso.nano.v3.analyzer;

import java.io.Serializable;

public class OscillogramData implements Serializable {
    private int min;
    private int max;
    private int average;
    private int[] data;

    public OscillogramData(int[] data) {
        this.data = data;
        calc(data);
    }

    public void removeRange(int from, int to) {
        int newData[] = new int[data.length - (to - from)];
        System.arraycopy(data, 0, newData, 0, from);
        System.arraycopy(data, to, newData, from, data.length - to);
        data = newData;
        calc(data);
    }

    private void calc(int[] data) {
        if(data.length == 0) {
            min = 0;
            max = 0;
            average = 0;
            return;
        }
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        average = 0;
        for(int i : data) {
            average += i;
            if(i > max) {
                max = i;
                continue;
            }
            if(i < min) {
                min = i;
            }
        }
        average /= data.length;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getAverage() {
        return average;
    }

    public int[] getData() {
        return data;
    }
}
