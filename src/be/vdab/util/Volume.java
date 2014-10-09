/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.util;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author osman.allam
 */
public class Volume implements Comparable<Volume>, Serializable {
    private final int hoogte;
    private final int breedte;
    private final int diepte;
    private final Maat maat;
    
    private final long volumeInCentimeter;
    
    public Volume (
            int hoogte,
            int breedte,
            int diepte,
            Maat maat
            ) throws VolumeException {
        if ((hoogte < 0)
                || (breedte < 0)
                || (diepte < 0)) {
            throw new VolumeException("VOLUME: parameters must be positive");
        }      
        this.hoogte = hoogte;
        this.breedte = breedte;
        this.diepte = diepte;
        this.maat = maat;
        
        this.volumeInCentimeter = maat.getMultiplier() * hoogte * 
                maat.getMultiplier() * breedte * 
                maat.getMultiplier() * diepte;
    }
    
    public long getVolume() {
        return this.volumeInCentimeter;
    }

    @Override
    public int compareTo(Volume o) {
        return (int) (this.getVolume() - o.getVolume());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.hoogte;
        hash = 29 * hash + this.breedte;
        hash = 29 * hash + this.diepte;
        hash = 29 * hash + Objects.hashCode(this.maat);
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
        final Volume other = (Volume) obj;
        if (this.hoogte != other.hoogte) {
            return false;
        }
        if (this.breedte != other.breedte) {
            return false;
        }
        if (this.diepte != other.diepte) {
            return false;
        }
        return this.maat == other.maat;
    }

    public int getHoogte() {
        return hoogte;
    }

    public int getBreedte() {
        return breedte;
    }

    public int getDiepte() {
        return diepte;
    }

    public Maat getMaat() {
        return maat;
    }

    @Override
    public String toString() {
        return String.format("%d(h)x%d(b)x%d(d) %s", hoogte, breedte, diepte, maat.toString());
    }
}
