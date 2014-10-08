/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.util.mens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static org.apache.commons.lang3.StringUtils.join;

/**
 *
 * @author osman.allam
 */
public class Mens implements Serializable, Comparable<Mens> {
    private final String naam;
    private List<Rijbewijs> rijbewijzen;
    
    public Mens() {
        this("onbekend");
    }
    
    public Mens(String naam, Rijbewijs... rijbewijzen) {
        this.naam = naam;
        this.rijbewijzen = new ArrayList<>();
        
        for (Rijbewijs rb : rijbewijzen) {
            this.addRijbewijs(rb);
        }
    }

    public String getNaam() {
        return naam;
    }
    
    public Rijbewijs[] getRijbewijs() {
        Rijbewijs[] z = new Rijbewijs[this.rijbewijzen.size()];
        this.rijbewijzen.toArray(z);
        return z;
    }

    public boolean heeftRijbewijs(Rijbewijs o) {
        return rijbewijzen.contains(o);
    }

    final public boolean addRijbewijs(Rijbewijs e) {
        if (this.heeftRijbewijs(e))
            return false;
        
        return rijbewijzen.add(e);
    }

    public boolean removeRijbewijs(Rijbewijs o) {
        return rijbewijzen.remove(o);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.naam);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mens other = (Mens) obj;
        return (this.naam.equals(other.naam) && 
                this.rijbewijzen.containsAll(other.rijbewijzen) &&
                other.rijbewijzen.containsAll(this.rijbewijzen));
    }

    @Override
    public String toString() {
        if (rijbewijzen.size() > 0)
            return String.format("%s(%s)", this.getNaam(), join(this.rijbewijzen, ", "));
        else 
            return this.getNaam();
    }   

    @Override
    public int compareTo(Mens o) {
        return this.naam.compareTo(o.naam);
    }
    
    
}
