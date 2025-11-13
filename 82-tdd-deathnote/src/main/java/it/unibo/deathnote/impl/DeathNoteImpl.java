package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import it.unibo.deathnote.api.DeathNote;
import java.util.Map;

/**
 * Javadoc comment.
 */
public class DeathNoteImpl implements DeathNote {

    //Class Fields
    private final Map<String,String> deathNote; 

    //Class Constructor
    public DeathNoteImpl() {
        this.deathNote = new HashMap<>();
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
        this.deathNote.putIfAbsent(name, "Heart Attack");
    }

    @Override
    public boolean writeDeathCause(String cause) {
        
    }

    @Override
    public boolean writeDetails(String details) {
        return true;
    }

    @Override
    public String getDeathCause(String name) {
        return null;
    }

    @Override
    public String getDeathDetails(String name) {
        return null;
    }

    @Override
    public boolean isNameWritten(String name) {
        return true;
    }
     
}
