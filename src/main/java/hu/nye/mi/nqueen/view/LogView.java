package hu.nye.mi.nqueen.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogView implements View {

    private static final Logger LOG = LoggerFactory.getLogger(LogView.class);

    @Override
    public void print(String message) {
        LOG.info(message);
    }

}
