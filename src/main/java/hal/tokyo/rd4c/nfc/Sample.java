/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hal.tokyo.rd4c.nfc;

/**
 *
 * @author pi
 */
public class Sample {
    public static void main(String[] args) throws InterruptedException{
        NFCReader nfc = new NFCReader();
        
        System.out.println(nfc.readBGMNum());
        
    }
}
