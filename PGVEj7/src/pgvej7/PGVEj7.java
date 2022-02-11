/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgvej7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Jose Maria Ruiz
 */
public class PGVEj7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Encriptado encriptado = new Encriptado();
        encriptado.Encriptar();
        encriptado.Desencriptar();
    }
    
}
