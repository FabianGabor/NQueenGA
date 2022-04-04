package hu.nye.mi.nqueen.domain;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Individual> individuals;

    private Population(int size) {
        this.individuals = new ArrayList<>(size);
    }

    private Population(Population population) {
        this.individuals = population.individuals;
    }

    public Population(int size, int chromosomeLength) {
        initPopulation(size, chromosomeLength);
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

    private void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    public void evolve(double crossoverRate, double mutationRate) {
        Population newPopulation = getHalfPopulation(individuals);

        while (newPopulation.getSize() < this.getSize()) {
            Population parents;
            try {
                parents = new Population(getTwoRandomParents(newPopulation));
                Individual child = crossover(parents, crossoverRate);
                mutate(child, mutationRate);
                child.calculateFitness();
                newPopulation.addIndividual(child);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
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

    private Population getTwoRandomParents(Population population) throws NoSuchAlgorithmException {
        Individual parent1;
        Individual parent2;
        Random random = SecureRandom.getInstanceStrong();

        parent1 = population.getIndividual(random.nextInt(population.getSize()));
        do {
            parent2 = population.getIndividual(random.nextInt(population.getSize()));
        } while (parent1.equals(parent2));

        Population parents = new Population(2);
        parents.addIndividual(parent1);
        parents.addIndividual(parent2);

        return parents;
    }

    private Individual crossover(Population parents, double crossoverRate) throws NoSuchAlgorithmException {
        Individual child = new Individual(parents.getIndividual(0).getChromosomeLength());

        Random random = SecureRandom.getInstanceStrong();

        for (int i = 0; i < child.getChromosomeLength(); i++) {
            if (parents.getIndividual(0).getGene(i) == parents.getIndividual(1).getGene(i) && random.nextDouble() < crossoverRate) {
                child.setGene(i, parents.getIndividual(0).getGene(i));
            }
        }

        child.calculateFitness();

        return child;
    }

    private void mutate(Individual individual, double mutationRate) throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();

        if (random.nextDouble() < mutationRate) {
            int randomPosition = random.nextInt(individual.getChromosomeLength());
            int randomValue = random.nextInt(individual.getChromosomeLength());
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
