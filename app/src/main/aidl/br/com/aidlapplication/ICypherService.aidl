package br.com.aidlapplication;

// Serviço AIDL de criptografia
interface ICypherService {
    String encrypt(String text, int key);
    String decrypt(String text, int key);
}
