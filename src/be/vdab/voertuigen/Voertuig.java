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
import java.util.Comparator;
import org.apache.commons.collections4.ListUtils;
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
        
        /*
        * The constructor in the only place where we set the driver and the
        * passengers at the same time. There is the risk that the driver is one
        * of the passengers. In this case, we must exclude the driver from the
        * array of passengers. Afterwards, things go as normal.
        */
        for (Mens mens : inzittenden) {
            if (!mens.equals(bestuurder)) // <<-- exclude the driver
                this.addIngezetene(mens);
        }        
        
        // if everything has gone ok so far, we request a license plate
        if (childConstraintsMet) {
            this.nummerplaat = DIV.getInstance().getNummerplaat();
        } else {
            this.nummerplaat = null;
        }
    }

//    public List<Mens> getInzittenden() {
//        List<Mens> inzittenden = this._inzittenden;
//        inzittenden.add(0, bestuurder);
//        return Collections.unmodifiableList(inzittenden);
//    }
    
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
        
        /*
        * There are a number of possibilities
        * [1] A new driver enters the vehicle
        *   [1.a] There had been a driver before, move him to a passenger seat
        *   [1.b] otherwise, simple seat the new driver
        * [2] An existing passenger moves to the driver's seat + old driver 
        *     moves to a passenger seat
        */
        
        if (this.bestuurder != null) {
            final Mens bestuurder_current = this.bestuurder;
            int idx = this._inzittenden.indexOf(bestuurder);
            
            if (idx > -1) { // [2]
                this.bestuurder = this._inzittenden.remove(idx);
            } else { // [1.a]
                this.bestuurder = bestuurder;
            }
            this.addIngezetene(bestuurder_current);
            
        } else { // [1.b]
            this.bestuurder = bestuurder;
        }
        
        return this._inzittenden.size()  +1;
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
        if (this.isIngezetene(e))
            return false;
        
        if (this._inzittenden.size() >= this.zitplaatsen -1)
            throw new MensException("Te veel inzittenden");
        
        return _inzittenden.add(e);
    }
    
    public boolean isIngezetene(Mens e) {
        return (this._inzittenden.contains(e) || this.bestuurder.equals(e));
    }
    
    protected boolean _isIngezetene(Mens e) {
        return this._inzittenden.contains(e);
    }
    

    public List<Mens> getIngezetenen() {
        List<Mens> z = new ArrayList<>(this._inzittenden.size() +1);
        
        z.addAll(this._inzittenden);
        z.add(this.bestuurder);
        Collections.sort(z);
        return z;
    }
    
    public List<Mens> getIngezeteneExclusiefBestuurder() {
        return ListUtils.unmodifiableList(this._inzittenden);
    }

    @Override
    public String toString() {
        String z = String.format("%s %s %s %d %s", 
                this.nummerplaat.toString(),
                this.merk,
                this.datumEersteIngebruikname.toString(),
                this.aankoopprijs,
                this.bestuurder.toString()
        );
        if (this._inzittenden.size() > 0) {
            z += " [" + join(this._inzittenden, ", ") + "]";
        }
        
        return z;
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
    public static Comparator<Voertuig> getMerkComparator() {
        return new Voertuig.ComparatorMerk();
    }

    static class ComparatorMerk implements Comparator<Voertuig> {

        @Override
        public int compare(Voertuig o1, Voertuig o2) {
            return o1.getMerk().compareTo(o2.getMerk());
        }

    }
    
    public static Comparator<Voertuig> getAankoopprijsComparator() {
        return new Voertuig.ComparatorAankoopprijs();
    }

    static class ComparatorAankoopprijs implements Comparator<Voertuig> {

        @Override
        public int compare(Voertuig o1, Voertuig o2) {
            return o1.getAankoopprijs() - o2.getAankoopprijs();
        }

    }

}
