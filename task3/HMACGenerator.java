import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMACGenerator {
    public static String generateHMAC(String move, SecretKey key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(key);

        byte[] bytes = hmac.doFinal(move.getBytes());

        StringBuilder hexStr = new StringBuilder();

        for (byte byt : bytes) {
            String hexCode = Integer.toHexString(0xff & byt);

            if (hexCode.length() == 1)
                hexStr.append('0');

            hexStr.append(hexCode);
        }

        return hexStr.toString();
    }
}
