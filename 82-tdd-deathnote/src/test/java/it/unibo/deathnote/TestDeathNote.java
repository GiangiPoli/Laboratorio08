package it.unibo.deathnote;

import it.unibo.deathnote.impl.DeathNoteImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestDeathNote {

    //Class Fields
    private static final String DEFAULT_DEATH_CAUSE = "Heart Attack";
    private static final long DETAIL_SLEEP_TIME = 6100L;
    private static final int RULE_NUMB = 50;
    private final DeathNoteImpl dn = new DeathNoteImpl();

    //Class Method
    /**
     * Rule number 0 and negative rules do not exist in the 
     * DeathNote rules:
     * Check that the exceptions are thrown correctly, that 
     * their type is the expected one, and that the message is 
     * not null, empty, or blank.
     */
    @Test
    void testGetRule() {
        /*
        *Testing 3 different situation:
        *ruleNumber < 1, ruleNumber in range, ruleNumber too big
        */
        findRule(0);
        findRule(2);
        findRule(RULE_NUMB);
    }

    private void findRule(final int ruleNumber) {

        try {
            final String actual = dn.getRule(ruleNumber);
            final String expected = """
                This note will not take effect unless the writer has the subject's face in mind when
                writing his/her name. This is to prevent people who share the same name from being
                affected. 
                """;
            assertEquals(expected, actual);
        } catch (final IllegalArgumentException ignored) {
            //Intezionalmente vuoto in quanto mi aspettavo l'eccezione
        }
    }

    /**
     * The human whose name is written in the DeathNote will 
     * eventually die:
     * Verify that the human has not been written in the notebook 
     * yet.
     * Write the human in the notebook.
     * Verify that the human has been written in the notebook.
     * Verify that another human has not been written in the 
     * notebook.
     * Verify that the empty string has not been written in the 
     * notebook.
     */ 
    @Test
    void testInsertHuman() {
        dn.cancelDeathNote();

        final String newHumanName = "Gigi";
        assertFalse(dn.isNameWritten(newHumanName));

        insertHuman(newHumanName);
        assertTrue(dn.isNameWritten(newHumanName));

        assertFalse(dn.isNameWritten("Pippo"));
        assertFalse(dn.isNameWritten(""));
    }

    private void insertHuman(final String name) {
        dn.writeName(name);
    }
    /**
     * If the cause of death is written within the next 40 
     * milliseconds of writing the person's name, it will happen. 
     * If the cause of death is not specified, the person will 
     * simply die of a heart attack:
     * Check that writing a cause of death before writing a name 
     * throws the correct exception.
     * Write the name of a human in the notebook.
     * Verify that the cause of death is a heart attack.
     * Write the name of another human in the notebook.
     * Set the cause of death to "karting accident".
     * Verify that the cause of death has been set correctly 
     * (returned true, and the cause is indeed "karting accident").
     * Sleep for 100ms.
     * Try to change the cause of death.
     * Verify that the cause of death has not been changed.
     */

    @Test
    void testInsertCause() {
        dn.cancelDeathNote();
        try {
            dn.writeDeathCause("Car Accident");
        } catch (final IllegalStateException ignored) {
            //Intezionalmente vuoto in quanto mi aspettavo l'eccezione
        }

        /*
         * Cause inserting for Gigio
         */
        String newHumanName = "Gigio";
        insertHuman(newHumanName);
        assertTrue(dn.isNameWritten(newHumanName));
        assertEquals(DEFAULT_DEATH_CAUSE, dn.getDeathCause(newHumanName));

        /*
         * Cause inserting for Pippoz (new human)
         */
        newHumanName = "Pippoz";
        final String pippoDeathCause = "karting accident";

        insertHuman(newHumanName);
        assertTrue(dn.writeDeathCause(pippoDeathCause));
        assertEquals(pippoDeathCause, dn.getDeathCause(newHumanName));
        try {
            Thread.sleep(100);
        } catch (final InterruptedException ignored) {
            //Intezionalmente vuoto in quanto mi aspettavo l'eccezione
        }
        assertFalse(dn.writeDeathCause("high fall"));
        assertEquals(pippoDeathCause, dn.getDeathCause(newHumanName));
    }

    /**
     * After writing the cause of death, details of the death 
     * should be written in the next 6 seconds and 40 milliseconds
     * of writing the death's cause:
     * -Check that writing the death details before writing a name
     *  throws the correct exception.
     * -Write the name of a human in the notebook.
     * -Verify that the details of the death are currently empty.
     * -Set the details of the death to "ran for too long".
     * -Verify that death details have been set correctly 
     *  (returned true, and the details are indeed "ran for too 
     *  long").
     * -Write the name of another human in the notebook.
     * -Sleep for 6100ms.
     * -Try to change the details.
     * -Verify that the details have not been changed.
     */ 
    @Test
    void testInsertDetails() {
        dn.cancelDeathNote();
        try {
            dn.writeDetails("prova che deve fallire");
        } catch (final IllegalStateException ignored) {
            //Intezionalmente vuoto in quanto mi aspettavo l'eccezione
        }

        /*
         * Details inserting for Gigi
         */
        String newHumanName = "Gigiz";
        insertHuman(newHumanName);
        assertTrue(dn.isNameWritten(newHumanName));

        assertEquals("", dn.getDeathDetails(newHumanName));
        final String gigiDeathDetails = "ran for too long";

        assertTrue(dn.writeDetails(gigiDeathDetails));

        assertEquals(gigiDeathDetails, dn.getDeathDetails(newHumanName));

        /*
         * Details inserting for Pippo (new human)
         */
        newHumanName = "Pippox";
        insertHuman(newHumanName);
        try {
            Thread.sleep(DETAIL_SLEEP_TIME);
        } catch (final InterruptedException ignored) {
            //Intenzionalmente ignorato
        }
        assertTrue(dn.isNameWritten(newHumanName));
        dn.writeDetails(gigiDeathDetails);

    }

}
