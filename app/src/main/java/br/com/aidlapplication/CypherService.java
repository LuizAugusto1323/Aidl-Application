package br.com.aidlapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CypherService extends Service {

    // Cria stub do serviço de criptografia, com duas funções, uma para criptografar e outra para descriptografar
    private final ICypherService.Stub binder = new ICypherService.Stub() {
        @Override
        public String encrypt(String text, int key) {
            return cypher(text, key);
        }

        @Override
        public String decrypt(String text, int key) {
            return cypher(text, 26 - key); // para descriptografar, basta inverter a chave
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    // Algoritmo de criptografia, baseado na cifra de César
    private String cypher(String text, int key) {
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);

            if (Character.isUpperCase(character)) {
                char newCharacter = (char) (((character - 'A' + key) % 26) + 'A');
                encryptedText.append(newCharacter);
            }
            else if (Character.isLowerCase(character)) {
                char newCharacter = (char) (((character - 'a' + key) % 26) + 'a');
                encryptedText.append(newCharacter);
            } else {
                encryptedText.append(character);
            }
        }
        return encryptedText.toString();
    }
}
