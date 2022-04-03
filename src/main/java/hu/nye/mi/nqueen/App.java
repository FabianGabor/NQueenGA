package hu.nye.mi.nqueen;

import hu.nye.mi.nqueen.service.GeneticAlgorithm;
import hu.nye.mi.nqueen.view.View;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.inject.Inject;

@SpringBootApplication
public class App {

    @Inject
    GeneticAlgorithm geneticAlgorithm;

    @Inject
    View view;

    @Bean
    public CommandLineRunner run() {
        return args -> geneticAlgorithm.solve(view);
    }

}
