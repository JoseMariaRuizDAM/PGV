/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psptema5_2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Jose Maria Ruiz
 */
public class PSPtema5_2 {

    /**
     * @param args the command line arguments
     */
    public static final int ANY_IP_TEST=0;
    public static final int LOOPBACK_TEST=1;
    public static final int LOCAL_TEST=2;
    
    public static void main(String[] args) {
        
        String arg = args[0];
        
        try{
            
            InetAddress direccion = InetAddress.getByName(arg);
            if(direccion.isLoopbackAddress()){
                
            }
        }catch(UnknownHostException e){
            System.out.println("Ha habido un error");
        }
        
        
    }
    
}
