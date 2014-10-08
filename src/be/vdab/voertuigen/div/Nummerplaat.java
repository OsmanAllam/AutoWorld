/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.voertuigen.div;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author osman.allam
 */
public class Nummerplaat implements Serializable, Comparable<Nummerplaat> {
    private final String plaat;
    
    /* default visibility */ Nummerplaat(String plaat) {
        this.plaat = plaat;
    }

    public String getPlaat() {
        return plaat;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.plaat);
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
        final Nummerplaat other = (Nummerplaat) obj;
        return Objects.equals(this.plaat, other.plaat);
    }

    @Override
    public int compareTo(Nummerplaat o) {
        return this.plaat.compareTo(o.plaat);
    }

    @Override
    public String toString() {
        return this.getPlaat();
    }
    
}
