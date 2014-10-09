/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.schoolgerief;

import be.vdab.util.Laadbaar;
import be.vdab.util.Volume;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author osman.allam
 */
public class Boekentas implements Laadbaar, Serializable {
    private Volume laadvolume;
    private String kleur;

    public Boekentas(String kleur, Volume laadvolume) throws IllegalArgumentException {
        this.setKleur(kleur);
        this.setLaadvolume(laadvolume);
    }
    
    @Override
    public Volume getLaadvolume() {
        return laadvolume;
    }

    public String getKleur() {
        return kleur;
    }

    @Override
    final public void setLaadvolume(Volume laadvolume) throws IllegalArgumentException {
        if (laadvolume == null)
            throw new IllegalArgumentException("laadvolume mag niet null zijn");
        this.laadvolume = laadvolume;
    }

    final public void setKleur(String kleur) throws IllegalArgumentException {
        if (kleur == null)
            throw new IllegalArgumentException("kleur mag niet null zijn");
        this.kleur = kleur;
    }

    @Override
    public String toString() {
        return "boekentas " + kleur + " " + laadvolume;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.laadvolume);
        hash = 79 * hash + Objects.hashCode(this.kleur);
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
        final Boekentas other = (Boekentas) obj;
        if (!Objects.equals(this.laadvolume, other.laadvolume)) {
            return false;
        }
        return Objects.equals(this.kleur, other.kleur);
    }

    
    
}
