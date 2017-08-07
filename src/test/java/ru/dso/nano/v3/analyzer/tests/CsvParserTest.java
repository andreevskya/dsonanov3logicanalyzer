package ru.dso.nano.v3.analyzer.tests;

import org.junit.Test;
import ru.dso.nano.v3.analyzer.CsvParser;
import ru.dso.nano.v3.analyzer.OscillogramData;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CsvParserTest {

    @Test
    public void testParse() throws Exception {
        OscillogramData data = CsvParser.parseData(new File(CsvParserTest.class.getClassLoader().getResource("DATA16.CSV").getFile()));
        assertNotNull(data);
        assertEquals(85, data.getMax());
        assertEquals(38, data.getMin());
        assertEquals(58, data.getAverage());
        assertNotNull(data.getData());
        assertEquals(4094, data.getData().length);
    }
}
