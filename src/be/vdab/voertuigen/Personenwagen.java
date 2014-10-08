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

/**
 *
 * @author osman.allam
 */
public class Personenwagen extends Voertuig {
    public Personenwagen(
            String merk,
            Datum datumEersteIngebruikname,
            int aankoopprijs,
            int zitplaatsen,
            Mens bestuurder,
            Mens... inzittenden
    ) throws MensException, IllegalArgumentException {
//        if (zitplaatsen > 8) {
//            throw new IllegalArgumentException();
//        }
        
        super((zitplaatsen <= 8), merk, datumEersteIngebruikname, aankoopprijs, zitplaatsen, bestuurder, inzittenden);
    }
    
    @Override
    protected boolean checkRijbewijs(Mens bestuurder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Rijbewijs[] getToegestaneRijbewijzen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected int getMAX_ZITPLAATSEN() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
