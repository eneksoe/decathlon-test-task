package org.example;

import org.example.model.Athlete;
import org.example.service.AthleteService;
import org.example.service.AthleteServiceImpl;
import org.example.utils.Constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String input;
        String inputData;
        String output;
        String outputData;

        try {
            input = args[0];
            inputData = args[1];
            output = args[2];
            outputData = args[3];

        } catch (Exception e) {
            System.out.println("Loading default parameters parameters: \n"
                    + "CSV src/resources/results.csv XML src/resources/output/results.xml");

            input = "CSV";
            inputData = "src/main/resources/results.csv";
            output = "XML";
            outputData = "src/main/resources/output/results.xml";

        }

        if (!Constants.INPUT_TYPES.contains(input)) {
            System.out.println(Constants.INPUT_IMPLEMENTATION_ERROR);
            System.out.println(Constants.EXIT_ERROR);
            return;
        }
        if (!Constants.OUTPUT_TYPES.contains(output)) {
            System.out.println(Constants.OUTPUT_IMPLEMENTATION_ERROR);
            System.out.println(Constants.EXIT_ERROR);
            return;
        }

        AthleteService athleteService = new AthleteServiceImpl();

        List<Athlete> athletes = athleteService.importData(input, inputData);

        if (athletes == null) {
            System.out.println(Constants.EXIT_ERROR);
            return;
        }

        try {
            athletes.sort(Collections.reverseOrder());
            athletes = addPosition(athletes);
        } catch (Exception e) {
            System.out.println(Constants.UNHANDLED_ORDER + e.getMessage());
        }

        Object salida = athleteService.exportData(output, outputData, athletes);

        if (salida == null)
            System.out.println(Constants.EXIT_ERROR);
        else
            System.out.println(Constants.EXIT_OK);

    }

    private static List<Athlete> addPosition(List<Athlete> athletes) {
        Athlete[] athleteArray = new Athlete[athletes.size()];
        athletes.toArray(athleteArray);
        int position;
        StringBuilder sharedPosition = new StringBuilder();

        for (int i = 0; i < athletes.size(); i++) {
            position = i + 1;

            if (!sharedPosition.toString().contains("-" + position + "-")) {
                sharedPosition = new StringBuilder("-" + position + "-");
                for (int j = i + 1;
                     j < athletes.size() && athleteArray[i].getTotalPoints().intValue() == athleteArray[j].getTotalPoints().intValue();
                     j++) {
                    position = j + 1;
                    sharedPosition.append(position).append("-");
                }
            }

            athleteArray[i].setPrintedOrder(sharedPosition.substring(1, sharedPosition.length() - 1));
        }

        return Arrays.asList(athleteArray);
    }
}
