/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.voertuigen;

import be.vdab.util.Datum;
import be.vdab.util.mens.Mens;
import be.vdab.util.mens.MensException;
import be.vdab.util.mens.Rijbewijs;
import java.awt.Color;

/**
 *
 * @author osman.allam
 */
public class Personenwagen extends Voertuig {
    private Color color;
    private static final int MAX_ZITPLAATSEN = 8;
    private static final Rijbewijs[] ToegestaneRijbewijzen = Rijbewijs.values(); // TODO
    
    public Personenwagen(
            String merk,
            Datum datumEersteIngebruikname,
            int aankoopprijs,
            int zitplaatsen,
            Color color,
            Mens bestuurder,
            Mens... inzittenden
    ) throws MensException, IllegalArgumentException {
        super((zitplaatsen <= MAX_ZITPLAATSEN), merk, datumEersteIngebruikname, aankoopprijs, zitplaatsen, bestuurder, inzittenden);
        if (zitplaatsen > MAX_ZITPLAATSEN)
            throw new IllegalArgumentException("Een Personenwagen mag maximum 8 zitplaatsen hebben");
        
        this.color = color;
    }
    
    @Override
    protected Rijbewijs[] getToegestaneRijbewijzen() {
        return Personenwagen.ToegestaneRijbewijzen;
    }

    @Override
    protected int getMAX_ZITPLAATSEN() {
        return Personenwagen.MAX_ZITPLAATSEN;
    }

    public Color getKleur() {
        return new Color(this.color.getRGB()); // _@@_ copy my color before giving it to strangers
    }

    public void setKleur(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.getZitplaatsen();
    }
    
    
    
}
