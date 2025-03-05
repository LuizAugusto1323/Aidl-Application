package br.com.aidlapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class EncryptionService extends Service {
    private final IEncryption.Stub binder = new IEncryption.Stub() {
        @Override
        public String encrypt(String text, int key) throws RemoteException {
            StringBuilder encryptedText = new StringBuilder();

            // Iterate over each character in the text
            for (int i = 0; i < text.length(); i++) {
                char character = text.charAt(i);

                // Check if the character is an uppercase letter
                if (Character.isUpperCase(character)) {
                    char newCharacter = (char) (((character - 'A' + key) % 26) + 'A');
                    encryptedText.append(newCharacter);
                }
                // Check if the character is a lowercase letter
                else if (Character.isLowerCase(character)) {
                    char newCharacter = (char) (((character - 'a' + key) % 26) + 'a');
                    encryptedText.append(newCharacter);
                } else {
                    // If it's not a letter, append the character without changes
                    encryptedText.append(character);
                }
            }
            return encryptedText.toString();
        }

        @Override
        public String decrypt(String text, int key) throws RemoteException {
            return encrypt(text, 26 - key);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
