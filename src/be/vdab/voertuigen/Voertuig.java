/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.voertuigen;

import be.vdab.util.mens.MensException;
import be.vdab.util.Datum;
import be.vdab.util.mens.Mens;
import be.vdab.voertuigen.div.DIV;
import be.vdab.voertuigen.div.Nummerplaat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import org.apache.commons.collections4.list.UnmodifiableList;
import java.util.Collections;

/**
 *
 * @author osman.allam
 */
abstract public class Voertuig implements Serializable, Comparable<Voertuig> {
    private final Nummerplaat nummerplaat;
    private final String merk;
    private Datum datumEersteIngebruikname;
    private int aankoopprijs;
    private final int zitplaatsen;
    
    private Mens bestuurder;
    private List<Mens> _inzittenden;
    
    public Voertuig(
            String merk,
            Datum datumEersteIngebruikname,
            int aankoopprijs,
            int zitplaatsen,
            Mens bestuurder,
            Mens... inzittenden
    ) throws MensException {
        if (inzittenden.length > zitplaatsen -1)
            throw new MensException("Te veel inzittenden");
        
        this.merk = merk;
        this.datumEersteIngebruikname = datumEersteIngebruikname;
        this.aankoopprijs = aankoopprijs;
        this.zitplaatsen = zitplaatsen;
        this._inzittenden = new ArrayList<>(Arrays.asList(inzittenden));
        this.setBestuurder(bestuurder);
        
        // if everything has gone ok so far, we request a license plate
        this.nummerplaat = DIV.getInstance().getNummerPlaat();
    }
    
    public List<Mens> getInzittenden() {
        List<Mens> inzittenden = this._inzittenden;
        inzittenden.add(0, bestuurder);
        return Collections.unmodifiableList(inzittenden);
    }
    
    abstract protected boolean checkRijbewijs(Mens bestuurder);
    
    final public int setBestuurder(Mens bestuurder) throws MensException {
        if (! checkRijbewijs(bestuurder))
            throw new MensException("Invalid rijbewijs");
        
        if (this._inzittenden.size() > this.zitplaatsen -1)
            throw new MensException("Te veel inzittenden");
        
        if (this.bestuurder != null) 
            this._inzittenden.add(this.bestuurder);
        this.bestuurder = bestuurder;
        
        return this.getInzittenden().size();
    }

    public Nummerplaat getNummerplaat() {
        return nummerplaat;
    }

    public String getMerk() {
        return merk;
    }

    public int getAankoopprijs() {
        return aankoopprijs;
    }

    public int getZitplaatsen() {
        return zitplaatsen;
    }

    public Mens getBestuurder() {
        return bestuurder;
    }

    public void setDatumEersteIngebruikname(Datum datumEersteIngebruikname) {
        this.datumEersteIngebruikname = datumEersteIngebruikname;
    }

    public void setAankoopprijs(int aankoopprijs) {
        this.aankoopprijs = aankoopprijs;
    }

    @Override
    public String toString() {
        return "Voertuig{" + "nummerplaat=" + nummerplaat + ", merk=" + merk + ", bestuurder=" + bestuurder + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        // hash = 23 * hash + Objects.hashCode(this.nummerplaat);
        hash = 23 * hash + this.nummerplaat.hashCode();
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
        final Voertuig other = (Voertuig) obj;
        
        return this.nummerplaat.equals(other.nummerplaat);
    }

    @Override
    public int compareTo(Voertuig o) {
        return this.nummerplaat.compareTo(o.nummerplaat);
    }

    // Comparator classes
    
    private class ComparatorMerk implements Comparable<Voertuig> {
        @Override
        public int compareTo(Voertuig o) {
            return merk.compareTo(o.merk);
        }
        
    }
    
    private class ComparatorAankoopprijs implements Comparable<Voertuig> {
        @Override
        public int compareTo(Voertuig o) {
            return (aankoopprijs - o.aankoopprijs);
        }
        
    }
    
}
