package it.unibo.deathnote;

import it.unibo.deathnote.impl.DeathNoteImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestDeathNote {    
    
    //Class Fields
    private final static String DEFAULT_DEATH_CAUSE = "Heart Attack";
    final DeathNoteImpl dn = new DeathNoteImpl();

    //Class Method
    /**
     * Rule number 0 and negative rules do not exist in the 
     * DeathNote rules:
     * Check that the exceptions are thrown correctly, that 
     * their type is the expected one, and that the message is 
     * not null, empty, or blank.
    */
    @Test
    public void testGetRule() {
        /*
        *Testing 3 different situation:
        *ruleNumber < 1, ruleNumber in range, ruleNumber too big
        */
        findRule(0);
        findRule(2);
        findRule(50);
    }

    public void findRule(final int ruleNumber) {
        
        try {
            String actual = dn.getRule(ruleNumber);
            String expected = """
                This note will not take effect unless the writer has the subject's face in mind when
                writing his/her name. This is to prevent people who share the same name from being
                affected. 
                """;
            assertEquals(expected,actual);
        } catch (final IllegalArgumentException e) {
            System.out.println("EXCEPTION GENERATED -> " 
            + e.getMessage() );
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
    public void testInsertHuman() {
        dn.cancelDeathNote();
        assertFalse( dn.isNameWritten("Gigi") );

        dn.writeName("Gigi");
        assertTrue( dn.isNameWritten("Gigi") );

        assertFalse( dn.isNameWritten("Pippo") );
        assertFalse( dn.isNameWritten("") );
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
    public void testInsertCause() {
        dn.cancelDeathNote();
        try {
            dn.writeDeathCause("Car Accident");
        } catch (final IllegalStateException e) {
            System.out.println("EXCEPTION GENERATED -> " 
            + e.getMessage());
        }

        /*
         * Cause inserting for Gigi
         */
        dn.writeName("Gigi");
        assertTrue(dn.isNameWritten("Gigi"));
        assertEquals(DEFAULT_DEATH_CAUSE,dn.getDeathCause("Gigi"));
        
        /*
         * Cause inserting for Pippo (new human)
         */
        String pippoDeathCause = "karting accident";
        dn.writeName("Pippo");
        assertTrue( dn.writeDeathCause(pippoDeathCause) );
        assertEquals(pippoDeathCause,dn.getDeathCause("Pippo"));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("THIS THREAD IS ALREADY PAUSED -> "
            + e.getMessage());
        }
        assertFalse(dn.writeDeathCause("high fall"));
        assertEquals(pippoDeathCause,dn.getDeathCause("Pippo"));
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
    public void testInsertDetails() {
        dn.cancelDeathNote();
        try {
            dn.writeDetails("prova che deve fallire");
        } catch (final IllegalStateException e) {
            System.out.println("EXCEPTION GENERATED -> "
            + e.getMessage());
        }

        /*
         * Details inserting for Gigi
         */
        dn.writeName("Gigi");
        assertTrue(dn.isNameWritten("Gigi"));

        System.out.println("INITIAL DETAILS -> expected: \t actual:" 
        + dn.getDeathDetails("Gigi"));

        assertEquals("", dn.getDeathDetails("Gigi"));
        String gigiDeathDetails = "ran for too long";
        assertTrue(dn.writeDetails(gigiDeathDetails));

        System.out.println("NEW DETAILS -> expected: "
        + gigiDeathDetails
        + "\t actual: " 
        + dn.getDeathDetails("Gigi"));
        
        assertEquals(gigiDeathDetails, dn.getDeathDetails("Gigi"));

        /*
         * Details inserting for Pippo (new human)
         */
        dn.writeName("Pippo");
        try {
            Thread.sleep(6100);
        } catch (InterruptedException e) {
            System.out.println("THIS THREAD IS ALREADY PAUSED -> "
            + e.getMessage());
        }
        assertTrue(dn.isNameWritten("Pippo"));
        dn.writeDetails(gigiDeathDetails);
        
        System.out.println("NEW DETAILS -> expected: \t actual: " 
        + dn.getDeathDetails("Pippo"));
        assertNotEquals(gigiDeathDetails, dn.getDeathDetails("Pippo"));
        System.out.println("DETAILS DID NOT CHANGE CAUSE RUN OUT OF TIME");

    }
}