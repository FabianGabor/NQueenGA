package hu.nye.mi.nqueen.service;

import hu.nye.mi.nqueen.domain.Board;
import hu.nye.mi.nqueen.domain.Individual;
import hu.nye.mi.nqueen.domain.Population;
import hu.nye.mi.nqueen.view.View;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class GeneticAlgorithm {
    private final Population population;
    private final double crossoverRate;
    private final double mutationRate;
    private final int sameWinnerMaxCount;
    private final Board board;

    @Value("${print.population}")
    private boolean printPopulation;

    @Value("${print.population.best}")
    private boolean printPopulationBest;

    @Value("${print.board}")
    private boolean printBoard;

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

            if (printPopulation) {
                view.print(String.valueOf(population));
            }
            if (printPopulationBest) {
                view.print("Winner " + generation + ": " + newFittestIndividual);
            }
            if (printBoard) {
                board.putQueens(population.getFittestIndividual().getChromosome());
                view.print(String.valueOf(board));
            }
        }
    }
}
