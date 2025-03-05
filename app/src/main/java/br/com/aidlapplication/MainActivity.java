package br.com.aidlapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private IEncryption encryptionService;
    private boolean isBound = false;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            encryptionService = IEncryption.Stub.asInterface(service);
            isBound = true;
            Toast.makeText(MainActivity.this, "Serviço conectado", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            encryptionService = null;
            isBound = false;
            Toast.makeText(MainActivity.this, "Serviço desconectado", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputText = findViewById(R.id.inputText);
        EditText inputKey = findViewById(R.id.inputKey);
        Button encryptButton = findViewById(R.id.encryptButton);
        Button decryptButton = findViewById(R.id.decryptButton);
        TextView resultText = findViewById(R.id.resultText);

        encryptButton.setOnClickListener(v -> {
            if (isBound && encryptionService != null) {
                try {
                    String text = inputText.getText().toString();
                    int key = Integer.parseInt(inputKey.getText().toString());
                    String encrypted = encryptionService.encrypt(text, key);
                    resultText.setText("Criptografado: " + encrypted);
                } catch (RemoteException | NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Erro na criptografia", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decryptButton.setOnClickListener(v -> {
            if (isBound && encryptionService != null) {
                try {
                    String text = inputText.getText().toString();
                    int key = Integer.parseInt(inputKey.getText().toString());
                    String decrypted = encryptionService.decrypt(text, key);
                    resultText.setText("Descriptografado: " + decrypted);
                } catch (RemoteException | NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Erro na descriptografia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, EncryptionService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }
}
