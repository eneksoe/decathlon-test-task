package org.example.service;

import org.example.model.Athlete;
import org.example.model.Discipline;
import org.example.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AthleteServiceImpl implements AthleteService {

    @Override
    public List<Athlete> importData(String input, Object data) {

        List<Athlete> athletes = null;

        if (input.equals("CSV"))
            athletes = importFromCSV(data);

        return athletes;
    }

    @Override
    public Object exportData(String output, Object data, List<Athlete> athletes) {
        if (output.equals("XML"))
            return exportToXML(athletes, data);
        return null;
    }

    Object exportToXML(List<Athlete> athletes, Object file) {

        Document document;
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;
            documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();

            Element root = document.createElement("decathlon");
            document.appendChild(root);

            for (Athlete row : athletes) {

                Element athlete = document.createElement("athlete");

                root.appendChild(athlete);

                athlete.setAttribute("position", row.getPrintedOrder());
                athlete.setAttribute("name", row.getName());
                athlete.setAttribute("total_points", row.getTotalPoints().toString());

                for (Discipline discRow : row.getDisciplines()) {
                    Element discipline = document.createElement("discipline");

                    athlete.appendChild(discipline);

                    discipline.setAttribute("name", Constants.EVENT_NAME[discRow.getOrder()]);
                    discipline.setAttribute("performance", discRow.getResult().toString());
                    discipline.setAttribute("points", discRow.getPoints().toString());
                }

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            try {
                StreamResult streamResult = new StreamResult(new File(file.toString()));
                transformer.transform(domSource, streamResult);
            } catch (Exception e) {
                System.out.println(Constants.INVALID_XML_OUTPUT);
                return null;
            }

        } catch (Exception e) {
            System.out.println(Constants.UNHANDLED_XML_OUTPUT + e.getMessage());
            return null;
        }

        return document;
    }

    List<Athlete> importFromCSV(Object file) {

        List<Athlete> athletes = new ArrayList<>();
        Athlete athlete;
        List<Discipline> disciplines;
        Discipline discipline;
        int athletePoints;

        Path pathToFile = Paths.get(file.toString());

        BufferedReader br;
        try {
            br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII);

        } catch (IOException e) {
            System.out.println(Constants.INVALID_CSV_INPUT);
            return null;
        }

        try {
            String rawLine = br.readLine();

            while (rawLine != null && rawLine.length() > 0) {
                String[] line = rawLine.split(";");
                athlete = new Athlete();
                athlete.setName(line[0]);
                disciplines = new ArrayList<Discipline>();
                athletePoints = 0;
                for (int i = 1; i <= 10; i++) {
                    discipline = new Discipline();
                    discipline.setOrder(i);
                    discipline.setResult(line[i]);
                    discipline.setPoints(Constants.qualify(i, discipline.getResult()));
                    athletePoints += discipline.getPoints();
                    disciplines.add(discipline);
                }
                athlete.setDisciplines(disciplines);
                athlete.setTotalPoints(athletePoints);
                athletes.add(athlete);

                rawLine = br.readLine();
            }
        } catch (Throwable e) {
            System.out.println(Constants.INVALID_CSV_FORMAT + e.getMessage());
            return null;
        }

        return athletes;
    }
}
