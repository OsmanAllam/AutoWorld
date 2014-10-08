/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.voertuigen;

import be.vdab.util.mens.MensException;
import be.vdab.util.Datum;
import be.vdab.util.mens.Mens;
import be.vdab.util.mens.Rijbewijs;
import be.vdab.voertuigen.div.DIV;
import be.vdab.voertuigen.div.Nummerplaat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import org.apache.commons.collections4.list.UnmodifiableList;
import java.util.Collections;
import static org.apache.commons.lang3.StringUtils.join;

/**
 *
 * @author osman.allam
 */
abstract public class Voertuig implements Serializable, Comparable<Voertuig> {
    private final Nummerplaat nummerplaat;
    private String merk;
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
            Mens... ingezetenen
    ) throws MensException, IllegalArgumentException {
        this(true, merk, datumEersteIngebruikname, aankoopprijs, zitplaatsen, bestuurder, ingezetenen);
    }
    
    protected Voertuig(
            boolean childConstraintsMet,
            String merk,
            Datum datumEersteIngebruikname,
            int aankoopprijs,
            int zitplaatsen,
            Mens bestuurder,
            Mens... inzittenden
    ) throws MensException, IllegalArgumentException {
        if ((zitplaatsen < 1) || (aankoopprijs < 0))
            throw new IllegalArgumentException();
        
        this.merk = merk;
        this.datumEersteIngebruikname = datumEersteIngebruikname;
        this.aankoopprijs = aankoopprijs;
        this.zitplaatsen = zitplaatsen;
        
        this._inzittenden = new ArrayList<>();
        this.setBestuurder(bestuurder); // throws MensException
        for (Mens mens : inzittenden) {
            this.addIngezetene(mens);
        }        
        
        // if everything has gone ok so far, we request a license plate
        if (childConstraintsMet) {
            this.nummerplaat = DIV.getInstance().getNummerplaat();
        } else {
            this.nummerplaat = null;
        }
    }

    public List<Mens> getInzittenden() {
        List<Mens> inzittenden = this._inzittenden;
        inzittenden.add(0, bestuurder);
        return Collections.unmodifiableList(inzittenden);
    }
    
    protected boolean checkRijbewijs(Mens bestuurder) {
        for (Rijbewijs rb : this.getToegestaneRijbewijzen()) {
            if (bestuurder.heeftRijbewijs(rb))
                return true;
        }
        return false;
    }
    
    abstract protected Rijbewijs[] getToegestaneRijbewijzen();
    abstract protected int getMAX_ZITPLAATSEN();
    
    final public int setBestuurder(Mens bestuurder) throws MensException {
        if (! checkRijbewijs(bestuurder))
            throw new MensException("Invalid rijbewijs");       
        
        if (this.bestuurder != null) 
            this.addIngezetene(bestuurder);
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

    public Datum getDatumEersteIngebruikname() {
        return datumEersteIngebruikname;
    }

    public void setDatumEersteIngebruikname(Datum datumEersteIngebruikname) {
        this.datumEersteIngebruikname = datumEersteIngebruikname;
    }

    public void setAankoopprijs(int aankoopprijs) {
        this.aankoopprijs = aankoopprijs;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }
    

    final public boolean addIngezetene(Mens e) throws MensException {
        if (this._inzittenden.contains(e) || this.bestuurder.equals(e))
            return false;
        
        if (this._inzittenden.size() > this.zitplaatsen -1)
            throw new MensException("Te veel inzittenden");
        
        return _inzittenden.add(e);
    }
    
    public boolean isIngezetene(Mens mens) {
        return this.getInzittenden().contains(mens);
    }
    
    public Mens[] getIngezeteneExclusiefBestuurder() {
        Mens[] z = new Mens[this._inzittenden.size()];
        this._inzittenden.toArray(z);
        return z;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %d %s [%s]", 
                this.nummerplaat.toString(),
                this.merk,
                this.datumEersteIngebruikname.toString(),
                this.aankoopprijs,
                this.bestuurder.toString(),
                join(this._inzittenden, ", ")
        );
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
    public static Comparable<Voertuig> getMerkComparator() {
        return Voertuig.new ComparatorMerk();
    }
    
    public static Comparable<Voertuig> getAankoopprijsComparator(Voertuig voertuig) {
        return voertuig.new ComparatorAankoopprijs();
    }
    
    static class ComparatorMerk {
        @Override
        public int compareTo(Voertuig o) {
            return getMerk().compareTo(o.getMerk());
        }
    }
    
    class ComparatorAankoopprijs implements Comparable<Voertuig> {
        @Override
        public int compareTo(Voertuig o) {
            return (getAankoopprijs() - o.getAankoopprijs());
        }
    }
}
