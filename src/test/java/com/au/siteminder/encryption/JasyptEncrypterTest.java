package com.au.siteminder.encryption;

import com.au.siteminder.framework.encryption.JasyptEncrypter;
import org.junit.Assert;
import org.junit.Test;

public class JasyptEncrypterTest {

    private static final String KEY = "randomness17==";
    private static final String YOUR_KEY = "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";

    @Test
    public void testEncryption(){
        String encryptedKey = JasyptEncrypter.encrypt(KEY);
        Assert.assertEquals(KEY, JasyptEncrypter.decrypt(encryptedKey));
    }

    /* Use this method to encrypt your keys */
    @Test
    public void encryption_tool(){
        String encryptedKey = JasyptEncrypter.encrypt(YOUR_KEY);
        System.out.println(encryptedKey);
        System.out.println(JasyptEncrypter.decrypt(encryptedKey));
    }

}
