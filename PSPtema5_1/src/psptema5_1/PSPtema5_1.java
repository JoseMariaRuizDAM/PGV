/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psptema5_1;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Jose Maria Ruiz
 */
public class PSPtema5_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String arg = args[0];
        try{
            //Patterns en String que indican si son direcciones IP o no
            String IPv4 = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
            String IPv6 = "^([\\da-fA-F]{1,4}:){7}[\\da-fA-F]{1,4}$";
            
            /*Condicional que hace un match con el argumento que se la ha pasado
            *comprobandolo con el pattern de la IP
            */
            if(arg.matches(IPv4) || arg.matches(IPv6)){
                InetAddress ip = InetAddress.getByName(arg);
                System.out.println("Address: " + arg +
                                    "\nName: " + (ip.getHostName()));
            }
            
            /*En caso de que no sea una dirección IP la que ha pasado el usuario
            *Entra por aquí mostrando la Ip que ha localizado con el DNS
            */
            else {
                InetAddress[] direcciones = InetAddress.getAllByName(arg);
                for(int i = 0; i < direcciones.length;i++) {
                    
                    System.out.println("Address: " + arg +
                                    "\nName: " + direcciones[i].getHostAddress());
                }                
            }
            
        }catch (UnknownHostException e) {
            System.out.println(e);
            System.out.println(
                    "Parece que no estás conectado, o que el servidor DNS no ha "
                    + "podido tramitar tu solicitud");
        }
    }
    
}
