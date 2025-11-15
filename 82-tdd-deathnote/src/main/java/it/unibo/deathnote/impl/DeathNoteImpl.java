package it.unibo.deathnote.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;
import it.unibo.deathnote.api.DeathNote;

/**
 * Javadoc comment.
 */
public class DeathNoteImpl implements DeathNote {

    //Class Fields
    private final static String DEFAULT_DEATH_CAUSE = "Heart Attack";
    private final static long TIME_TO_WRITE_DEATH_CAUSE = 40L;
    private final static long TIME_TO_WRITE_DEATH_DETAILS = 6040L;
    private final List<PersonThatWillDie> deathNote;
    private long timeNameWrote;

    //Class Constructor
    public DeathNoteImpl() {
        this.deathNote = new LinkedList<>();
    }

    //Class Method
    @Override
    public String getRule( final int ruleNumber ) {
        if (ruleNumber < 1) {
            throw new IllegalArgumentException("Rule Number less than 1");
        } else {
            Iterator<String> iterator = RULES.iterator();
            int counter = 1;

            while ( iterator.hasNext() ) {
                String result = iterator.next();
                if ( ruleNumber == counter ) {
                    return result;
                }
                counter++;
            }
            throw new IllegalArgumentException("Rule Number too big");
        }

    }

    
    @Override
    public void writeName(String name) {
        if ( Objects.isNull(name) ) {
            throw new NullPointerException("Name is NULL");
        }
        this.deathNote.add(new PersonThatWillDie(name));
        timeNameWrote = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if ( this.deathNote.isEmpty() ) {
            throw new IllegalStateException("There is no name to assign the death cause");
        } else if ( Objects.isNull(cause)  ) {
            throw new IllegalStateException("Cause is NULL");
        }

        PersonThatWillDie lastPerson = getLast();

        if ( System.currentTimeMillis() - this.timeNameWrote <= TIME_TO_WRITE_DEATH_CAUSE ) {
            lastPerson.deathCause = cause;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean writeDetails(String details) {
        if ( this.deathNote.isEmpty() ) {
            throw new IllegalStateException("There is no name to assign the death cause");
        } else if ( Objects.isNull(details)  ) {
            throw new IllegalStateException("Details is NULL");
        }

        PersonThatWillDie lastPerson = getLast();

        if ( System.currentTimeMillis() - this.timeNameWrote <= TIME_TO_WRITE_DEATH_DETAILS ) {
            lastPerson.deathDetails = details;
            return true;
        } else {
            return false;
        }
    }

    private PersonThatWillDie getLast() {
        final Iterator<PersonThatWillDie> it = deathNote.iterator();
        PersonThatWillDie lastPerson = null;
        while ( it.hasNext() ) {
            lastPerson = it.next();     
        }
        return lastPerson;
    }

    @Override
    public String getDeathCause(String name) {
        if ( !isNameWritten(name) ) {
            throw new IllegalArgumentException("Name searched is not written in the Death Note");
        }

        return getPersonWithName(name).deathCause;   
    }

    @Override
    public String getDeathDetails(String name) {
        if ( !isNameWritten(name) ) {
            throw new IllegalArgumentException("Name searched is not written in the Death Note");
        }

        return getPersonWithName(name).deathDetails;
    }

    @Override
    public boolean isNameWritten(String name) {
        return getPersonWithName(name) == null ? false : true;
    }
     
    private PersonThatWillDie getPersonWithName(String name) {
        final Iterator<PersonThatWillDie> it = deathNote.iterator();
        PersonThatWillDie lastPerson = null;
        while ( it.hasNext() ) {
            lastPerson = it.next();
            if ( lastPerson.name == name ) {
                return lastPerson;
            }     
        }
        return null;
    }

    public void cancelDeathNote() {
        this.deathNote.clear();
    }

    //Inner Class
    private class PersonThatWillDie {
    
        //Class Fields
        private String name;
        private String deathCause;
        private String deathDetails;
        
        //Class Constructor
        public PersonThatWillDie(String PersonName) {
            name = PersonName;
            deathCause = DEFAULT_DEATH_CAUSE;
            deathDetails = "";
        }
    }
}
