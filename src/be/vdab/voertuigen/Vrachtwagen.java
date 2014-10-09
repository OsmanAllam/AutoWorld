/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.voertuigen;

import be.vdab.util.Datum;
import be.vdab.util.Laadbaar;
import be.vdab.util.Volume;
import be.vdab.util.mens.Mens;
import be.vdab.util.mens.MensException;
import be.vdab.util.mens.Rijbewijs;

/**
 *
 * @author osman.allam
 */
public class Vrachtwagen extends Voertuig implements Laadbaar {
    private Volume laadvolume;
    private int maximaalToegelatenMassa ;
    private int aantalAssen;
    
    private static final int MAX_ZITPLAATSEN = 3;
    private static final Rijbewijs[] ToegestaneRijbewijzen = Rijbewijs.values(); // TODO

    public Vrachtwagen(
            String merk, 
            Datum datumEersteIngebruikname, 
            int aankoopprijs, 
            int zitplaatsen, 
                Volume laadvolume, 
                int maximaalToegelatenMassa, 
                int aantalAssen, 
            Mens bestuurder, 
            Mens... ingezetenen
    ) throws MensException, IllegalArgumentException {
        super((zitplaatsen <= MAX_ZITPLAATSEN), merk, datumEersteIngebruikname, aankoopprijs, zitplaatsen, bestuurder, ingezetenen);
        if (zitplaatsen > MAX_ZITPLAATSEN)
            throw new IllegalArgumentException("Een Vrachtwagen mag maximum 3 zitplaatsen hebben");
        
        this.laadvolume = laadvolume;
        this.maximaalToegelatenMassa = maximaalToegelatenMassa;
        this.aantalAssen = aantalAssen;
    }
    
    @Override
    protected Rijbewijs[] getToegestaneRijbewijzen() {
        return Vrachtwagen.ToegestaneRijbewijzen;
    }

    @Override
    protected int getMAX_ZITPLAATSEN() {
        return Vrachtwagen.MAX_ZITPLAATSEN;
    }

    @Override
    public Volume getLaadvolume() {
        return this.laadvolume;
    }

    @Override
    public void setLaadvolume(Volume vol) {
        this.laadvolume = vol;
    }

    public int getMaximaalToegelatenMassa() {
        return maximaalToegelatenMassa;
    }

    public int getAantalAssen() {
        return aantalAssen;
    }

    public void setMaximaalToegelatenMassa(int maximaalToegelatenMassa) {
        this.maximaalToegelatenMassa = maximaalToegelatenMassa;
    }

    public void setAantalAssen(int aantalAssen) {
        this.aantalAssen = aantalAssen;
    }

    @Override
    public String toString() {
        return String.format("%s assen:%d, maximaal toegelaten massa:%d, laadvolume:%s", super.toString(), aantalAssen, maximaalToegelatenMassa, laadvolume);
    }
 
}
