/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.util.mens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author osman.allam
 */
public class Mens {
    private final String naam;
    private List<Rijbewijs> rijbewijzen;
    
    public Mens() {
        this("onbekend");
    }
    
    public Mens(String naam) {
        this.naam = naam;
        this.rijbewijzen = new ArrayList<>();
    }

    public String getNaam() {
        return naam;
    }

    public List<Rijbewijs> getRijbewijzen() {
        return rijbewijzen;
    }

    public boolean heeftRijbewijs(Rijbewijs o) {
        return rijbewijzen.contains(o);
    }

    public Iterator<Rijbewijs> getRijbewijzenIterator() {
        return rijbewijzen.iterator();
    }

    public boolean addRijbewijs(Rijbewijs e) {
        return rijbewijzen.add(e);
    }

    public boolean removeRijbewijs(Rijbewijs o) {
        return rijbewijzen.remove(o);
    }
}
