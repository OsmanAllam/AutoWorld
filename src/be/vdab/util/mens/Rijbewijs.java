/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.util.mens;

/**
 *
 * @author osman.allam
 */
public enum Rijbewijs {
    A, B, C, D, 
    BE, CE, DE;

    @Override
    public String toString() {
        switch(this) {
            case A: return "A";
            case B: return "B";
            case C: return "C";
            case D: return "D";
            case BE: return "B+E";
            case CE: return "C+E";
            case DE: return "D+E";
            default: return null; // unreachable
        }
    }
    
    
    
}
