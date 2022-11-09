package org.example.service;

import org.example.model.Athlete;

import java.util.List;

public interface AthleteService {

    List<Athlete> importData(String input, Object data);

    Object exportData(String output, Object data, List<Athlete> athletes);
}
