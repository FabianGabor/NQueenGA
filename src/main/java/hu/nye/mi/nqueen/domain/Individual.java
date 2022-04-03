package hu.nye.mi.nqueen.domain;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Individual {
    private final int chromosomeLength;
    private int[] chromosome;
    private int fitness;

    public Individual(int chromosomeSize) {
        this.chromosomeLength = chromosomeSize;
        this.chromosome = new int[chromosomeSize];

        initChromosome();
    }

    public Individual(Individual individual) {
        this.chromosomeLength = individual.chromosomeLength;
        this.chromosome = individual.chromosome.clone();
        this.fitness = individual.fitness;
    }

    private void initChromosome() {
        try {
            Random rand = SecureRandom.getInstanceStrong();

            for (int i = 0; i < chromosomeLength; i++) {
                chromosome[i] = (byte) rand.nextInt(chromosomeLength);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        calculateFitness();
    }

    public int getChromosomeLength() {
        return chromosomeLength;
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public int getGene(int index) {
        return chromosome[index];
    }

    public void setGene(int index, int value) {
        chromosome[index] = value;
    }

    public int getFitness() {
        return fitness;
    }

    public void calculateFitness() {
        int clashes = 0;
        // calculate row and column clashes
        // just subtract the unique length of array from total length of array
        // [1,1,1,2,2,2] - [1,2] => 4 clashes

        int[] noDuplicates = IntStream.of(chromosome).distinct().toArray();

        int rowColClashes = Math.abs(chromosome.length - (noDuplicates.length));
        clashes += rowColClashes;

        //calculate diagonal clashes
        for (int i = 0; i < chromosome.length; i++) {
            for (int j = i + 1; j < chromosome.length; j++) {
                if (i != j) {
                    int dx = Math.abs(i - j);
                    int dy = Math.abs(chromosome[i] - chromosome[j]);
                    if (dx == dy) {
                        clashes++;
                    }
                }
            }
        }

        this.fitness = clashes;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "chromosomeSize=" + chromosomeLength +
                ", chromosome=" + Arrays.toString(chromosome) +
                ", fitness=" + fitness +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Arrays.equals(chromosome, that.chromosome);
    }
}
