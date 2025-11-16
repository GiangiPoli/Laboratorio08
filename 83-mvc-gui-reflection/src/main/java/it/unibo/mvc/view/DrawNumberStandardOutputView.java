package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * Class to output on the Std Out.
 */
public class DrawNumberStandardOutputView implements DrawNumberView {
    /**
     * Construct a new DrawNumberStandardOutputView.
     */
    public DrawNumberStandardOutputView() {
        /*
         * Commenting to avoid warning
         */
    }
    //Class Method

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final DrawNumberController observer) {
        /*
         * NO NEED TO SET CONTROLLER: IS OUTPUT ONLY
         */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        /*
         * STANDARD OUTPUT IS ALWAYS ON
         */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void result(final DrawResult res) {
        switch (res) {
            case YOURS_HIGH, YOURS_LOW -> {
                // CHECKSTYLE: OFF
                System.out.println("RESULT -> "
                + res.getDescription());
                // CHECKSTYLE: ON
            }
            case YOU_WON, YOU_LOST -> {
                // CHECKSTYLE: OFF
                System.out.println("RESULT ->"
                + res.getDescription()
                + "\nNEW MATCH STARTING...");
                // CHECKSTYLE: ON
            }
        }
    }
}
