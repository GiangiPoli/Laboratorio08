package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

public class DrawNumberStandardOutputView implements DrawNumberView {

    //Class Constructor

    /**
     * Construct a new DrawNumberStandardOutputView
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
    public void setController(DrawNumberController observer) {
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
    public void result(DrawResult res) {
        switch (res) {
            case YOURS_HIGH, YOURS_LOW -> {
                System.out.println("RESULT -> "
                + res.getDescription());
                return;
            }
            case YOU_WON, YOU_LOST -> {
                System.out.println("RESULT ->"
                + res.getDescription()
                + "\nNEW MATCH STARTING...");
            }
        }
    }
}
