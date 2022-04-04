package hu.nye.mi.nqueen.service;

import hu.nye.mi.nqueen.domain.Board;
import hu.nye.mi.nqueen.domain.Individual;
import hu.nye.mi.nqueen.domain.Population;
import hu.nye.mi.nqueen.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GeneticAlgorithm {
    private Population population;
    private double crossoverRate;
    private double mutationRate;
    int sameWinnerMaxCount;
    private Board board;

    public void solve(View view) {
        for (Individual individual : population.getIndividuals()) {
            view.print(String.valueOf(individual));
        }

        int generation = 0;
        int sameWinner = 0;

        while (population.getFittestIndividualFitnessValue() > 0 && sameWinner < sameWinnerMaxCount) {
            Individual oldFittestIndividual = population.getFittestIndividual();
            population.evolve(crossoverRate, mutationRate);
            Individual newFittestIndividual = population.getFittestIndividual();

            if (oldFittestIndividual.equals(newFittestIndividual)) {
                sameWinner++;
            } else {
                sameWinner = 0;
            }

            generation++;
            view.print(String.valueOf(population));
            view.print("Winner " + generation + ": " + newFittestIndividual);
        }


        board.putQueens(population.getFittestIndividual().getChromosome());

        view.print(String.valueOf(board));
    }
}
