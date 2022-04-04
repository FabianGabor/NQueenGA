package hu.nye.mi.nqueen;

import hu.nye.mi.nqueen.domain.Board;
import hu.nye.mi.nqueen.domain.Population;
import hu.nye.mi.nqueen.service.GeneticAlgorithm;
import hu.nye.mi.nqueen.view.LogView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * Configuration class for the application.
 * These parameters are read from the config.properties file.
 * @see <a href="file:../../../../../src/main/resources/config.properties">/resources/config.properties</a>
 *
 * @author Fábián Gábor
 * @see <a href="https://github.com/FabianGabor/NQueenGA" target="_blank">https://github.com/FabianGabor/NQueenGA</a>
 */

@Configuration
@PropertySource("classpath:config.properties")

public class NQueenGaApplicationConfig {

    /**
     * The size of the population.
     * For 8x8 board, it should be at least 64.
     */
    @Value("${population.size}")
    int populationSize;

    /**
     * The size of the board. For 8x8 board, it is 8.
     */
    @Value("${board.size}")
    int boardSize;

    /**
     * The number of generations with the same winner after which the algorithm stops. For 8x8 board, a decent amount
     * is above 1000.
     */
    @Value("${population.sameWinnerMaxCount}")
    int sameWinnerMaxCount;

    /**
     * How likely that the parents common genes are copied to the offspring. A good value is above 0.7. Value of 1
     * guarantees that the offspring will inherit the common genes of the parents and should help to converge faster
     * to the solution.
     */
    @Value("${population.crossover.probability}")
    double crossoverProbability;

    /**
     * The probability that a gene is mutated. A good value is between 0.05 and 0.1
     */
    @Value("${population.mutation.probability}")
    double mutationProbability;

    @Bean
    GeneticAlgorithm geneticAlgorithm() {
        return new GeneticAlgorithm(population(), crossoverProbability, mutationProbability, sameWinnerMaxCount, board());
    }

    @Bean
    Population population() {
        return new Population(populationSize, boardSize);
    }

    @Bean
    Board board() {
        return new Board(boardSize);
    }

    @Bean
    LogView view() {
        return new LogView();
    }
}
