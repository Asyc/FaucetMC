package org.faucetmc.network.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.math.BigInteger;
import java.security.*;

public class CryptUtils {

    public static byte[] encryptRSA(PublicKey key, byte[] in) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(in);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptAES(SecretKey key, IvParameterSpec parameterSpec, byte[] in) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            return cipher.doFinal(in);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decryptRSA(PrivateKey key, byte[] in) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(in);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decryptAES(SecretKey key, IvParameterSpec parameterSpec, byte[] in) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
            return cipher.doFinal(in);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static BigInteger toBigIntegerSHA1(byte[]... input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            for(byte[] in : input) {
                digest.update(in);
            }
            return new BigInteger(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
