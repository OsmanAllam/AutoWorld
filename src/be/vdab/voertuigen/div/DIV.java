/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.voertuigen.div;

/**
 *
 * @author osman.allam
 */
public class DIV {
    private static final DIV instance = new DIV();
    private int cijfer;
    
    private DIV() {
        this.cijfer = 1;
    }
    
    public static DIV getInstance() {
        return instance;
    }
    
    public Nummerplaat getNummerPlaat() {
        if (this.cijfer > 999-1) {
            this.cijfer = 1;
        }
        
        return new Nummerplaat(String.format("AAA-%03d", this.cijfer++));
    }
}
