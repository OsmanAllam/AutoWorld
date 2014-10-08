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
public class MensException extends RuntimeException {
    public MensException() {
        super();
    }
    
    public MensException(String message) {
        super(message);
    }
    
    public MensException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MensException(Throwable cause) {
        super(cause);
    }
}
