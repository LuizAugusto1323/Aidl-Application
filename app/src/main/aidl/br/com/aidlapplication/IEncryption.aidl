// IEncryption.aidl
package br.com.aidlapplication;

// Declare any non-default types here with import statements

interface IEncryption {
    String encrypt(String text, int key);
    String decrypt(String text, int key);
}
