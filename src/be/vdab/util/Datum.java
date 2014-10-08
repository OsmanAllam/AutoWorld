/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.util;

import java.io.Serializable;

/**
 *
 * @author osman.allam
 */
public class Datum implements Serializable, Comparable<Datum> {
    private final int dag;
    private final int maand;
    private final int jaar;
    
    public Datum(int dag, int maand, int jaar) throws DatumException {
        if ((jaar < 1583) || (jaar > 4099))
            throw new DatumException();
        
        this.dag = dag;
        this.maand = maand;
        this.jaar = jaar;
    }

    public int getDag() {
        return dag;
    }

    public int getMaand() {
        return maand;
    }

    public int getJaar() {
        return jaar;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%4d", this.dag, this.maand, this.jaar);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.dag;
        hash = 29 * hash + this.maand;
        hash = 29 * hash + this.jaar;
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
        final Datum other = (Datum) obj;
        if (this.dag != other.dag) {
            return false;
        }
        if (this.maand != other.maand) {
            return false;
        }
        return this.jaar == other.jaar;
    }

    @Override
    public int compareTo(Datum o) {
        // TODO: is there a better way of coding this?
        if (this.jaar > o.jaar) {
            return 1;
        } else if (this.jaar < o.jaar) {
            return -1;
        } else { // this.jaar == o.jaar
            if (this.maand > o.maand) {
                return 1;
            } else if (this.maand < o.maand) {
                return -1;
            } else { // this.maand == o.maand 
                if (this.dag > o.dag) {
                    return 1;
                } else if (this.dag < o.dag) {
                    return -1;
                } else { // this.dag == o.dag;
                    return 0;
                }
            }
        }
    }
    
    
    
}
