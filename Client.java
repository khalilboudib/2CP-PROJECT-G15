import java.sql.Date;

public class Client {

    private long cardNumber ;
    private String expiringDate ;
    private String firstName;

    private String lastName;

    private String userAdress ;


    private byte[] publicKey;

    public Client (Long cardNumber , String expiringDate , String firstName , String lastName , String userAdress) {
        this.cardNumber = cardNumber ;
        this.expiringDate = expiringDate ;
        this.firstName = firstName ;
        this.lastName = lastName ;
        this.userAdress = userAdress ;
        publicKey = new byte[256];
        for (int i = 0; i < publicKey.length; i++) publicKey[i] = (byte) i;
    }





    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getCardNumber() {
        return cardNumber ;
    }

    public void setExpiringDate(String expiringDate) {
        this.expiringDate = expiringDate;
    }

    public String getExpiringDate() {
        return expiringDate ;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }

    public String getUserAdress() {
        return userAdress;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }


    public byte[] getPublicKey() {
        return publicKey;
    }

}
