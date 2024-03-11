import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hachage {
	public static byte[] hashByteArray(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(input);
        return hashedBytes;
    }

    public static void main(String[] args) {
        try {
            byte[] inputArray = {1, 2, 3, 4, 5}; // Exemple d'un tableau de bytes
            byte[] hashedArray = hashByteArray(inputArray);
            System.out.println("Hashed Array: " + bytesToHex(hashedArray));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Méthode utilitaire pour convertir un tableau de bytes en une chaîne hexadécimale
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
