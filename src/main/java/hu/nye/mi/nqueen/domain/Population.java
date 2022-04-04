package hu.nye.mi.nqueen.domain;

import lombok.SneakyThrows;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Individual> individuals;

    public Population(int size, int chromosomeLength) {
        initPopulation(size, chromosomeLength);
    }

    private Population(int size) {
        this.individuals = new ArrayList<>(size);
    }

    private Population(Population population) {
        this.individuals = population.individuals;
    }

    private void initPopulation(int size, int chromosomeLength) {
        individuals = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            individuals.add(new Individual(chromosomeLength));
        }
    }

    public int getSize() {
        return individuals.size();
    }

    public List<Individual> getIndividuals() {
        return individuals;
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

    private Individual getIndividual(int index) {
        return individuals.get(index);
    }

    private void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    public void evolve(double crossoverRate, double mutationRate) {
        Population newPopulation = getHalfPopulation(individuals);

        while (newPopulation.getSize() < this.getSize()) {
            Population parents = new Population(getTwoRandomParents(newPopulation));
            Individual child = crossover(parents, crossoverRate);
            mutate(child, mutationRate);
            child.calculateFitness();
            newPopulation.addIndividual(child);
        }

        individuals = newPopulation.getIndividuals();
        this.sort();
    }

    private Population getHalfPopulation(List<Individual> individuals) {
        Population newPopulation = new Population(this.getSize() / 2);
        for (int i = 0; i < this.getSize() / 2; i++) {
            newPopulation.addIndividual(individuals.get(i));
        }
        return newPopulation;
    }

    @SneakyThrows
    private Population getTwoRandomParents(Population population) {
        Individual parent1;
        Individual parent2;

        parent1 = population.getIndividual(rng(population.getSize()));
        do {
            parent2 = population.getIndividual(rng(population.getSize()));
        } while (parent1.equals(parent2));

        Population parents = new Population(2);
        parents.addIndividual(parent1);
        parents.addIndividual(parent2);

        return parents;
    }

    @SneakyThrows
    private Individual crossover(Population parents, double crossoverRate) {
        Individual parent1 = parents.getIndividual(0);
        Individual parent2 = parents.getIndividual(1);
        Individual child = new Individual(parent1.getChromosomeLength());

        for (int i = 0; i < child.getChromosomeLength(); i++) {
            if (isGeneOfParentsEqualAtIndex(parent1, parent2, i) && rng(crossoverRate)) {
                child.setGene(i, parents.getIndividual(0).getGene(i));
            }
        }

        child.calculateFitness();

        return child;
    }

    private int rng(int value) throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        return random.nextInt(value);
    }

    private boolean rng(double value) throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        return random.nextDouble() < value;
    }

    private boolean isGeneOfParentsEqualAtIndex(Individual i1, Individual i2, int geneIndex) {
        return i1.getGene(geneIndex) == i2.getGene(geneIndex);
    }

    @SneakyThrows
    private void mutate(Individual individual, double mutationRate) {
        if (rng(mutationRate)) {
            int randomPosition = rng(individual.getChromosomeLength());
            int randomValue = rng(individual.getChromosomeLength());
            individual.setGene(randomPosition, individual.getGene(randomValue));
        }
    }

    private void sort() {
        individuals.sort(this::compare);
    }

    private int compare(Individual i1, Individual i2) {
        return i1.getFitness() - i2.getFitness();
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
