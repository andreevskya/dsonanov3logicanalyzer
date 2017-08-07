package ru.dso.nano.v3.analyzer;


import java.util.ArrayList;
import java.util.List;

public class LogicAnalyzer {

    public boolean[] analyze(OscillogramData data, int start, int end, int logicOne, int logicZero) {
        List<Boolean> line = new ArrayList<>(end - start);
        int prev = 2;
        int value;
        int lo = 0;
        int lz = 0;
        for(int i = start; i < end; ++i) {
            value = data.getData()[i] > data.getAverage() ? 1 : 0;
            if(prev != value) {
                prev = value;
                lo = lz = 0;
                continue;
            }
            if(value > 0) {
                if(lo == logicOne) {
                    line.add(true);
                    lo = 0;
                } else {
                    ++lo;
                }
            } else {
                if(lz == logicZero) {
                    line.add(false);
                    lz = 0;
                } else {
                    ++lz;
                }
            }
        }
        boolean[] bline = new boolean[line.size()];
        for(int i = 0; i < line.size(); ++i) {
            bline[i] = line.get(i);
        }
        return bline;
    }
}
