package org.example;

import org.example.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void inputIsNotImplemented() {
        String[] args = new String[] {"XLS", "src/resources/results.csv", "XML", "src/resources/output/results.xml"};
        String expectedMessage = Constants.INPUT_IMPLEMENTATION_ERROR;
        Main.main(args);
        assertTrue(outputStreamCaptor.toString().contains(expectedMessage));
    }

    @Test
    public void inputCSVDataIsNotValid() {
        String[] args = new String[] {"CSV", "src/resources/results.csv", "XML", "src/resources/output/results.xml"};
        String expectedMessage = Constants.INVALID_CSV_INPUT;
        Main.main(args);
        assertTrue(outputStreamCaptor.toString().contains(expectedMessage));
    }

    @Test
    public void inputCSVBrokenFile() {
        String[] args = new String[] {"CSV", "src/main/resources/broken_results.csv", "XML", "src/resources/output/results.xml"};
        String expectedMessage = Constants.INVALID_CSV_FORMAT;
        Main.main(args);
        assertTrue(outputStreamCaptor.toString().contains(expectedMessage));
    }

    @Test
    public void outputIsNotImplemented() {
        String[] args = new String[] {"CSV", "src/resources/results.csv", "JSON", "src/resources/output/results.xml"};
        String expectedMessage = Constants.OUTPUT_IMPLEMENTATION_ERROR;
        Main.main(args);
        assertTrue(outputStreamCaptor.toString().contains(expectedMessage));
    }

    @Test
    public void outputXMLDataIsNotValid() {
        String[] args = new String[] {"CSV", "src/main/resources/results.csv", "XML", "src/resources/results.xml"};
        String expectedMessage = Constants.INVALID_XML_OUTPUT;
        Main.main(args);
        assertTrue(outputStreamCaptor.toString().contains(expectedMessage));
    }

    @Test
    public void finishesOKWithParameters() {
        String[] args = new String[] {"CSV", "src/main/resources/results.csv", "XML", "src/main/resources/output/results.xml"};
        String expectedMessage = Constants.EXIT_OK;
        Main.main(args);
        assertTrue(outputStreamCaptor.toString().contains(expectedMessage));
    }

    @Test
    public void finishesOKWithoutParameters() {
        String expectedMessage = Constants.EXIT_OK;
        Main.main(null);
        assertTrue(outputStreamCaptor.toString().contains(expectedMessage));
    }

    @Test
    public void score1000Points() {
        double[] wikiValues = new double[] {0, 10.395, 7.76, 18.4, 2.20, 46.17, 13.8, 56.17, 5.28, 77.19, 233.79};
        for (int i = 1; i< 11; i++) {
            int score = Constants.qualify(i, wikiValues[i]);
            assertTrue(Math.abs(1000-score)<10);
        }
    }

    @Test
    public void score900Points() {
        double[] wikiValues = new double[] {0, 10.827, 7.36, 16.79, 2.10, 48.19, 14.59, 51.4, 4.96, 70.67, 247.42};
        for (int i = 1; i< 11; i++) {
            int score = Constants.qualify(i, wikiValues[i]);
            assertTrue(Math.abs(900-score)<10);
        }
    }

    @Test
    public void score800Points() {
        double[] wikiValues = new double[] {0, 11.278, 6.94, 15.16, 1.99, 50.32, 15.419, 46.59, 4.63, 64.09, 261.77};
        for (int i = 1; i< 11; i++) {
            int score = Constants.qualify(i, wikiValues[i]);
            assertTrue(Math.abs(800-score)<10);
        }
    }

    @Test
    public void score700Points() {
        double[] wikiValues = new double[] {0, 11.756, 6.51, 13.53, 1.88, 52.58, 16.29, 41.72, 4.29, 57.45, 276.96};
        for (int i = 1; i< 11; i++) {
            int score = Constants.qualify(i, wikiValues[i]);
            assertTrue(Math.abs(700-score)<10);
        }
    }
}