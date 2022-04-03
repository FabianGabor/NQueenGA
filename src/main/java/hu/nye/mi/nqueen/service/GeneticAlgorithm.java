package hu.nye.mi.nqueen.service;

import hu.nye.mi.nqueen.domain.Individual;
import hu.nye.mi.nqueen.domain.Population;
import hu.nye.mi.nqueen.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GeneticAlgorithm {
    private Population population;
    private double crossoverRate;
    private double mutationRate;

    public void solve(View view) {
        for (Individual individual : population.getIndividuals()) {
            view.print(String.valueOf(individual));
        }

        int generation = 0;

        while (population.getFittestIndividualFitnessValue() > 0) {
            population.evolve(crossoverRate, mutationRate);
            generation++;
            //view.print(String.valueOf(population));
            view.print("Winner " + generation + ": " + population.getFittestIndividual());
        }
        //view.print("Solution: " + "Generation " + generation + ": " + population.getFittestIndividual());
    }
}
