package encryption_decryption;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Encryption_Decryption{

    public SecretKey sameKey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        SecretKey myDesKey = kg.generateKey();
        return myDesKey;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);

        System.out.println("Which Chaining Mode do you Chose, ECB or CBC: ");
        String chm = input.nextLine();

        while (!(chm.toLowerCase().equals("ecb") || chm.toLowerCase().equals("cbc"))) {
            System.out.println("Please choose one of these option, ECB or CBC: ");
            chm = input.nextLine();
        }

       if (chm.toUpperCase().equals("ECB")) {

           Encryption_Decryption key = new Encryption_Decryption();
           SecretKey secretKey = key.sameKey();


           System.out.println("Please write a text: ");
           String text1 = input.nextLine();

           try {

               Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

               System.out.println("If you want to encrypt this text, please write \"encrypt\"?");
               String ed = input.nextLine();

               while (!(ed.toLowerCase().equals("encrypt"))) {
                   System.out.println("If you want to encrypt, please write encrypt");
                   ed = input.nextLine();
               }

               if (ed.toLowerCase().equals("encrypt")) {
                   cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                   byte[] text = text1.getBytes();
                   System.out.println("Text in bytes: " + text);
                   System.out.println("Text: " + new String(text));
                   byte[] textEnc = cipher.doFinal(text);
                   System.out.println("Text in bytes: " + textEnc);
                   System.out.println("Text Encrypted: " + new String(textEnc));

                   System.out.println("--------------------------------------------");

                   System.out.println("Do you want to Decrypt it? ");
                   ed = input.nextLine();
                   if (ed.equals("yes")) {
                       cipher.init(Cipher.DECRYPT_MODE, secretKey);
                       byte[] textDec = cipher.doFinal(textEnc);
                       System.out.println("Text in bytes: " + textDec);
                       System.out.println("Text Decrypted: " + new String(textDec));
                   } else
                       System.out.println("See you later, have fun!");
               }
           } catch (Exception e) {
               System.out.println("An exception happened");
           }
       }

       else if(chm.toUpperCase().equals("CBC")) {

           Encryption_Decryption key = new Encryption_Decryption();
           SecretKey secretKey = key.sameKey();


           System.out.println("Please write a text: ");
           String text1 = input.nextLine();

           try {

               Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

               System.out.println("If you want to encrypt this text, please write \"encrypt\"?");
               String ed = input.nextLine();

               while (!(ed.toLowerCase().equals("encrypt"))) {
                   System.out.println("If you want to encrypt, please write encrypt");
                   ed = input.nextLine();
               }
               
                if (ed.toLowerCase().equals("encrypt")) {

                   cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                   byte[] text = text1.getBytes();
                   System.out.println("Text in bytes: " + text);
                   System.out.println("Text: " + new String(text));
                   byte[] textEnc = cipher.doFinal(text);
                   System.out.println("Text in bytes: " + textEnc);
                   System.out.println("Text Encrypted: " + new String(textEnc));
                   System.out.println("--------------------------------------------");

                   System.out.println("Do you want to Decrypt it? ");
                   ed = input.nextLine();
                   if (ed.equals("yes")) {
                       byte iv[] = cipher.getIV();
                       IvParameterSpec dps = new IvParameterSpec(iv);
                       cipher.init(Cipher.DECRYPT_MODE, secretKey, dps);
                       byte[] textDec = cipher.doFinal(textEnc);
                       System.out.println("Text Decrypted: " + new String(textDec));
                   } else
                       System.out.println("See you later, have fun!");
               }
           }catch (Exception e) {
               System.out.println("An exception happened");
           }
       }
    }
}
