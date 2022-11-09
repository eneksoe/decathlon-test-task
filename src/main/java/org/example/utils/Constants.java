package org.example.utils;

import java.util.List;

public class Constants {

    public static final List<String> INPUT_TYPES = List.of("CSV");
    public static final List<String> OUTPUT_TYPES = List.of("XML");

    public static final String INPUT_IMPLEMENTATION_ERROR = "The received input type is not yet implemented";
    public static final String OUTPUT_IMPLEMENTATION_ERROR = "The received output type is not yet implemented";

    public static final String EXIT_OK = "The process finished correctly.";
    public static final String EXIT_ERROR = "The process finished with errors.";

    public static final String INVALID_CSV_INPUT = "The input directory or file does not exist";
    public static final String INVALID_CSV_FORMAT = "The input file does not have the required format: ";

    public static final String UNHANDLED_ORDER = "There was a problem ordering the data: ";

    public static final String INVALID_XML_OUTPUT = "The output directory does not exist";
    public static final String UNHANDLED_XML_OUTPUT = "There was a problem writing the output XML File: ";

    public static final String[] EVENT_NAME = new String[]{"",
            "100 m", "Long jump",
            "Shot put", "High jump",
            "400 m", "110 m hurdles",
            "Discus throw", "Pole vault",
            "Javelin throw", "1500 m"};
    public static final double[] CONSTANT_A = new double[]{0,
            25.4347, 0.14354, 51.39, 0.8465, 1.53775, 5.74352, 12.91, 0.2797, 10.14, 0.03768
    };
    public static final double[] CONSTANT_B = new double[]{0,
            18, 220, 1.5, 75, 82, 28.5, 4, 100, 7, 480
    };

    public static final double[] CONSTANT_C = new double[]{0,
            1.81, 1.4, 1.05, 1.42, 1.81, 1.92, 1.1, 1.35, 1.08, 1.85
    };
    public static final char[] UNIT = new char[]{'0',
            's', 'c', 'm', 'c', 's', 's', 'm', 'c', 'm', 's'
    };

    public static int qualify(int order, double performance) {

        if (UNIT[order] == 'c')
            performance = performance * 100;
        if (UNIT[order] == 's') {

            return (int) (CONSTANT_A[order] * Math.pow(CONSTANT_B[order] - performance, CONSTANT_C[order]));
        } else {

            return (int) (CONSTANT_A[order] * Math.pow(performance - CONSTANT_B[order], CONSTANT_C[order]));
        }
    }
}
