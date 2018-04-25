package helpers;

public interface PasswordAuthentication {

    String hash(char[] password);

    boolean authenticate(char[] password, String token);

}
