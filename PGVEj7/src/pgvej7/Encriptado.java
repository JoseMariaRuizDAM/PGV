/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgvej7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Jose Maria Ruiz
 */
public class Encriptado {
    String secretKeyAES; //Llave o Key de AES creada
    String saltAES; //Sal de AES
    ArrayList<String> cadenasEncriptadas = new ArrayList<>();
    ArrayList<String> cadenasDesencriptadas = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    private static final String ficheroLeer =  "ficherocifrado.txt";  
    private static final String ficheroEncriptado = "ficheroEncriptado.txt";
    
    public Encriptado(){
        recogerUsuario();
        recogerContraseña(); 
        System.out.println("Escribe el mensaje que quieres encriptar");
        String mensaje = sc.nextLine();
        try {
            escribirFichero(ficheroLeer, mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Encriptado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que lee un archivo de texto
     * lo encripta con AES y PKCS5Padding
     * @return 
     */
    public String Encriptar() {
        try {
            byte[] iv = new byte[16];
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKeyAES.toCharArray(), saltAES.getBytes(), 65536, 128);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
            cadenasEncriptadas = leerFichero(ficheroLeer);
            
            for (String linea : cadenasEncriptadas) {        
                byte[] cipherText = cipher.doFinal(linea.getBytes());
                String codigo = Base64.getEncoder().encodeToString(cipherText);
                escribirFichero(ficheroEncriptado, codigo);
                System.out.println("Se ha introducido la clave encriptada en el archivo");  
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Metodo para desencriptar el mensaje
     * que recibe de un archivo de texto.
     * @return 
     */
    public String Desencriptar() {
        try{
            byte[] iv = new byte[16];
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKeyAES.toCharArray(), saltAES.getBytes(), 65536, 128);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret , ivParameterSpec);
            cadenasDesencriptadas = leerFichero(ficheroEncriptado);
            for (String linea : cadenasDesencriptadas) {        
                byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(linea));
                
                System.out.println("Texto desencriptado " + new String (plainText));  
            }
            
        }catch(Exception e){
            System.out.println("Ha habido un error");
        }
        
        return null;
    }
    
    private ArrayList<String> leerFichero(String ficheroLeer){
        ArrayList<String> leer = new ArrayList<>();
        try {
            File file= new File(ficheroLeer);
            Scanner myReader = new Scanner(file);

            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              leer.add(data);
            }
        
            myReader.close();
        }catch (FileNotFoundException e) {
            System.out.println("Ha ocurrido un error.");
            e.printStackTrace();
        }
        return leer;
    }
    
    /*
    private ArrayList<String> leerFicheroEncriptado(){
        ArrayList<String> leer = new ArrayList<>();
        try {
            File myObj = new File(ficheroEncriptado);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              leer.add(data);
            }
        
            myReader.close();
        }catch (FileNotFoundException e) {
            System.out.println("Ha ocurrido un error.");
            e.printStackTrace();
        }
        return leer;
    }*/
    
    public void escribirFichero(String file, String data) throws IOException{
        File ficheroEncriptado = new File(file);
        ficheroEncriptado.createNewFile();
        FileWriter writer = null;
        PrintWriter pw = null;
        try
        {
            writer = new FileWriter(ficheroEncriptado);
            pw = new PrintWriter(writer);

            pw.print(data);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != writer)
              writer.close();
           } catch (Exception e) {
              e.printStackTrace();
           }
        }
        
    }
    
    private void recogerUsuario(){
        System.out.println("Introduce tu Usuario");
        secretKeyAES = sc.nextLine();    
    }
    
    private void recogerContraseña(){
        System.out.println("Introduce la contraseña");
        saltAES = sc.nextLine();
    }
        
}

    
    

