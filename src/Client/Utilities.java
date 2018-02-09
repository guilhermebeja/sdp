package Client;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import Server.Exceptions.RequestNotValidException;
import Server.ServerRequest;
import org.apache.commons.codec.binary.Base64;



public class Utilities {
    public static int generateConversationID(String usernames){
        int result = 0;
        for(int i=0; i < usernames.length(); i++){
            result += usernames.charAt(i);
        }
        return result;
    }

    public static byte[] encrypt(Object obj) {
        byte[] key = (new String("guilherme beja")).getBytes();
        try {
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(obj);
            byte[] encrypted = cipher.doFinal(baos.toByteArray());

            return Base64.encodeBase64(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static Object decrypt(byte[] encrypted) {
        byte[] key = (new String("guilherme beja")).getBytes();
        try {
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);


            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            ByteArrayInputStream bois = new ByteArrayInputStream(original);
            ObjectInputStream ois = new ObjectInputStream(bois);

            return ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) throws RequestNotValidException {
        String test = "Hello World";

        ServerRequest rs = new ServerRequest("GET /metal", "123", 98);
        byte[] e = encrypt(rs);


        ServerRequest d = (ServerRequest)decrypt(e);
        System.out.println(d);
    }
}

