package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        final String firstView = "it.unibo.mvc.view.DrawNumberStandardOutputView";
        final String secondView = "it.unibo.mvc.view.DrawNumberSwingView";

        for (int i = 0; i < 3; i++) {
            try {
                final DrawNumberView myFirstView = (DrawNumberView) Class.forName(firstView).getConstructor().newInstance();
                final DrawNumberView mySecondView = (DrawNumberView) Class.forName(secondView).getConstructor().newInstance();
                app.addView(myFirstView);
                app.addView(mySecondView);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException
                    | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
