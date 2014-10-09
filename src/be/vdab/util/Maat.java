/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.util;

/**
 *
 * @author osman.allam
 */
public enum Maat {
    centimeter(1), decimeter(10), meter(100);
    
    private final int multiplier;
    
    Maat(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
