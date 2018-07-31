package com.au.siteminder.framework.encryption;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import static com.au.siteminder.framework.constant.SiteminderEmailServiceConstant.ENCRYPTION_ALGORITHM;
import static com.au.siteminder.framework.constant.SiteminderEmailServiceConstant.ENCRYPTION_KEY;

/**
 * Provides facility to encrypt and decrypt sensitive information
 */
public class JasyptEncrypter {

    public static String encrypt(String key) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(ENCRYPTION_KEY);
        encryptor.setAlgorithm(ENCRYPTION_ALGORITHM);
        return encryptor.encrypt(key);
    }

    public static String decrypt(String key) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(ENCRYPTION_KEY);
        encryptor.setAlgorithm(ENCRYPTION_ALGORITHM);
        return encryptor.decrypt(key);
    }

}
