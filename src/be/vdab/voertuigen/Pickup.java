/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.voertuigen;

import be.vdab.util.Datum;
import be.vdab.util.Laadbaar;
import be.vdab.util.Volume;
import be.vdab.util.VolumeException;
import be.vdab.util.mens.Mens;
import be.vdab.util.mens.MensException;
import java.awt.Color;

/**
 *
 * @author osman.allam
 */
public class Pickup extends Personenwagen implements Laadbaar {
    private Volume laadVolume;
    
    public Pickup(
            String merk, 
            Datum datumEersteIngebruikname, 
            int aankoopprijs, 
            int zitplaatsen, 
            Color color, 
            Volume laadVolume,
            Mens bestuurder, 
            Mens... inzittenden
    ) throws MensException, IllegalArgumentException {
        super(merk, datumEersteIngebruikname, aankoopprijs, zitplaatsen, color, bestuurder, inzittenden);
        this.laadVolume = laadVolume;
    }

    @Override
    public Volume getLaadvolume() {
        return this.laadVolume;
    }

    @Override
    public void setLaadvolume(Volume laadVolume) {
        this.laadVolume = laadVolume;
    }    

    @Override
    public String toString() {
        return super.toString() + " " + this.getLaadvolume().toString();
    }
}
