package hu.nye.mi.nqueen.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private final int size;
    private List<Individual> individuals;

    public Population(int size, int chromosomeLength) {
        this.size = size;

        initPopulation(chromosomeLength);
    }

    private void initPopulation(int chromosomeLength) {
        individuals = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            individuals.add(new Individual(chromosomeLength));
        }
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    public Individual getFittestIndividual() {
        sort();
        return individuals.get(0);
    }

    public int getFittestIndividualFitnessValue() {
        sort();
        Individual fittest = individuals.get(0);
        return fittest.getFitness();
    }

    public void evolve(double crossoverRate, double mutationRate) {
        List<Individual> newPopulation = new ArrayList<>(size);

        Individual bestIndividual = getFittestIndividual();
        //mutate(bestIndividual, mutationRate);
        bestIndividual.calculateFitness();
        newPopulation.add(bestIndividual); // add the fittest individual

        for (int i = 1; i < size; i++) {
            if (i < size/10) {
                Individual parent1;
                Individual parent2;

                parent1 = tournamentSelection(size / 2);
                do {
                    parent2 = tournamentSelection(size / 2);
                } while (parent1.equals(parent2));

                List<Individual> children = crossover(parent1, parent2, crossoverRate);

                for (Individual child : children) {
                    mutate(child, mutationRate);
                    child.calculateFitness();
                    newPopulation.add(child);
                }
            } else {
                mutate(individuals.get(i), mutationRate);
                newPopulation.add(individuals.get(i));
            }
        }

        individuals = newPopulation;
        sort();
    }

    private Individual tournamentSelection(int tournamentSize) {
        sort();

        Individual opponent1;
        Individual opponent2;

        Random random;
        try {
            random = SecureRandom.getInstanceStrong();

            opponent1 = getIndividual((int) (random.nextDouble() * tournamentSize));
            do {
                opponent2 = getIndividual((int) (random.nextDouble() * tournamentSize));
            } while (opponent1.equals(opponent2));

            return winner(opponent1, opponent2);
        } catch (Exception e) {

        }

        return null;
    }

    private List<Individual> crossover(Individual parent1, Individual parent2, double crossoverRate) {
        Individual child1 = new Individual(parent1);
        Individual child2 = new Individual(parent2);

        for (int i = 0; i < child1.getChromosomeLength() / 2; i++) {
            if (Math.random() <= crossoverRate) {
                child1.setGene(i, parent2.getGene(i));
                child2.setGene(i, parent1.getGene(i));
            }
        }

        child1.calculateFitness();
        child2.calculateFitness();

        List<Individual> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);

        return children;
    }

    private void mutate(Individual individual, double mutationRate) {
        if (Math.random() < mutationRate) {
            int randomPosition = (int) (Math.random() * individual.getChromosomeLength());
            int randomValue = (int) (Math.random() * individual.getChromosomeLength());
            individual.setGene(randomPosition, individual.getGene(randomValue));
        }
    }

    private void sort() {
        individuals.sort(this::compare);
    }

    private int compare(Individual i1, Individual i2) {
        return i1.getFitness() - i2.getFitness();
    }

    private Individual winner(Individual i1, Individual i2) {
        return i1.getFitness() < i2.getFitness() ? i1 : i2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        for (Individual individual : individuals) {
            sb.append(individual.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
