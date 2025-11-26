package guru.springframework.creditcard.domain.services;

public interface EncryptionService {

    String encrypt(String freeText);

    String decrypt(String encryptredText);
}
