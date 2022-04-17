package encryption_decryption;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.Cipher;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.InvalidParameterException;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;


public class DES {
 public static void EncryptDecrypt(String key, int CipherMode, File in, File out)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            IOException, InvalidParameterException, InvalidAlgorithmParameterException {
       FileInputStream fis = new FileInputStream(in);
       FileOutputStream fos = new FileOutputStream(out);

        DESKeySpec desKeySpec =new DESKeySpec(key.getBytes());

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        byte[] ivBytes = new  byte[8];
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        if (CipherMode == Cipher.ENCRYPT_MODE){
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, SecureRandom.getInstance("SHA1PRNG"));
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            write(cis, fos);

        }
        else if(CipherMode == Cipher.DECRYPT_MODE){
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv, SecureRandom.getInstance("SHA1PRNG"));
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            write(fis, cos);
        }
    }
   
    private static void write(InputStream in, OutputStream out) throws IOException{
        byte[] buffer = new byte[64];
        int numberOfBytesRead;
        while ((numberOfBytesRead = in.read(buffer)) != -1){
            out.write(buffer,0,numberOfBytesRead);
        }
        out.close();
        in.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        File plainText = new File("C:\\Users\\internet\\eclipse-workspace\\DES\\src\\encryption_decryption\\motivation.txt");
        File encrypted = new File("C:\\Users\\internet\\eclipse-workspace\\DES\\src\\encryption_decryption\\encrypted.txt");
        File decrypted = new File("C:\\Users\\internet\\eclipse-workspace\\DES\\src\\encryption_decryption\\decrypted.txt");

        System.out.println("Do you want to encrypt or decrypt, or delete the content of a file?");
        String chm = input.nextLine();
       
        while (!(chm.toLowerCase().equals("encrypt") || chm.toLowerCase().equals("decrypt") || (chm.toUpperCase().equals("DELETE")))) {
            System.out.println("Please choose one of these option, ENCRYPT or DECRYPT: ");
            chm = input.nextLine();
        }
       
        if (chm.toUpperCase().equals("ENCRYPT")) {
            try {
                EncryptDecrypt("12345678", Cipher.ENCRYPT_MODE, plainText, encrypted);
                System.out.println("Encryption Completed");
            } catch (InvalidAlgorithmParameterException | IOException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException
                    | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
       
        else if (chm.toUpperCase().equals("DECRYPT")) {
        try {
                EncryptDecrypt("12345678", Cipher.DECRYPT_MODE, encrypted, decrypted);
                System.out.println("Decryption Completed");
            } catch (InvalidAlgorithmParameterException | IOException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException
                    | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
       
        else if (chm.toUpperCase().equals("DELETE")) {
        System.out.println("Wich file do you want to delete, encrypted file or decrypted file?");
        String filee = input.nextLine();
        if(filee.equals("encrypted")) {
         System.out.println("Delete Complete");
         PrintWriter writer = new PrintWriter(encrypted);
                  writer.print("");
                  writer.close();
        }
        else {
         System.out.println("Delete Complete");
         PrintWriter writer = new PrintWriter(decrypted);
             writer.print("");
             writer.close();
        }
       
        }
       
    }

}
