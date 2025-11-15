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
    private static final String DEFAULT_DEATH_CAUSE = "Heart Attack";
    private static final long TIME_TO_WRITE_DEATH_CAUSE = 40L;
    private static final long TIME_TO_WRITE_DEATH_DETAILS = 6040L;
    private final List<PersonThatWillDie> deathNote;
    private long timeNameWrote;

    //Class Constructor

    /*
     * Turning Off this checkstyle because there is nothing 
     * specific to say in javadoc about this constrctuctor
     */
    //CHECKSTYLE: MissingJavadocMethod OFF
    public DeathNoteImpl() {
        this.deathNote = new LinkedList<>();
    }
    //CHECKSTYLE: MissingJavadocMethod ON

    //Class Method

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1) {
            throw new IllegalArgumentException("Rule Number less than 1");
        } else {
            final Iterator<String> iterator = RULES.iterator();
            int counter = 1;

            while (iterator.hasNext()) {
                final String result = iterator.next();
                if (ruleNumber == counter) {
                    return result;
                }
                counter++;
            }
            throw new IllegalArgumentException("Rule Number too big");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        this.deathNote.add(new PersonThatWillDie(name));
        timeNameWrote = System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        if (this.deathNote.isEmpty()) {
            throw new IllegalStateException("There is no name to assign the death cause");
        } else if (Objects.isNull(cause)) {
            throw new IllegalStateException("Cause is NULL");
        }

        final PersonThatWillDie lastPerson = getLast();

        if (System.currentTimeMillis() - this.timeNameWrote <= TIME_TO_WRITE_DEATH_CAUSE) {
            lastPerson.deathCause = cause;
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(final String details) {
        if (this.deathNote.isEmpty()) {
            throw new IllegalStateException("There is no name to assign the death cause");
        } else if (Objects.isNull(details)) {
            throw new IllegalStateException("Details is NULL");
        }

        final PersonThatWillDie lastPerson = getLast();

        if (System.currentTimeMillis() - this.timeNameWrote <= TIME_TO_WRITE_DEATH_DETAILS) {
            lastPerson.deathDetails = details;
            return true;
        } else {
            return false;
        }
    }

    private PersonThatWillDie getLast() {
        final Iterator<PersonThatWillDie> it = deathNote.iterator();
        PersonThatWillDie lastPerson = null;
        while (it.hasNext()) {
            lastPerson = it.next();
        }
        return lastPerson;
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException("Name searched is not written in the Death Note");
        }

        return getPersonWithName(name).deathCause;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException("Name searched is not written in the Death Note");
        }

        return getPersonWithName(name).deathDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(final String name) {
        return getPersonWithName(name) != null;
    }

    private PersonThatWillDie getPersonWithName(final String name) {
        final Iterator<PersonThatWillDie> it = deathNote.iterator();
        PersonThatWillDie lastPerson;
        while (it.hasNext()) {
            lastPerson = it.next();
            if (lastPerson.name.equals(name)) {
                return lastPerson;
            }
        }
        return null;
    }

    /**
     * This method cancel all the name from the Dath Note.
     * I use this to make different test during the testing
     */
    public void cancelDeathNote() {
        this.deathNote.clear();
    }

    //Inner Class
    private final class PersonThatWillDie {

        //Class Fields
        private final String name;
        private String deathCause;
        private String deathDetails;

        //Class Constructor
        private PersonThatWillDie(final String personName) {
            name = personName;
            deathCause = DEFAULT_DEATH_CAUSE;
            deathDetails = "";
        }
    }
}
