package hu.nye.mi.nqueen;

import hu.nye.mi.nqueen.domain.Population;
import hu.nye.mi.nqueen.service.GeneticAlgorithm;
import hu.nye.mi.nqueen.view.LogView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NQueenGaApplicationConfig {

    @Bean
    GeneticAlgorithm geneticAlgorithm() {
        return new GeneticAlgorithm(population(), 0.8, 0.05);
    }

    @Bean
    Population population() {
        return new Population(50, 8);
    }

    @Bean
    LogView view() {
        return new LogView();
    }
}
