
/*

Created By : Himanshu Prajapati
Github : hrp03
Date : 24th June, 2017

*/

package com.hp.encrypt;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class HpEncrypt
{

    private String iv = "0p9o8i7u6y5t4r3e"; // change your vector
    private String SecretKey = "/.,;p]-09ijh654f";// change your key

    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;
    private Cipher cipher;



    public HpEncrypt()
    {
        ivspec = new IvParameterSpec(iv.getBytes());
        keyspec = new SecretKeySpec(SecretKey.getBytes(), "AES");

        try
        {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
    }

    public String encrypt(String text) throws Exception
    {
        if(text == null || text.length() == 0)
            throw new Exception("Empty string");

        byte[] encrypted = null;

        try
        {
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            encrypted = cipher.doFinal(padString(text).getBytes());
        }
        catch (Exception e)
        {
            throw new Exception("[encrypt] " + e.getMessage());
        }


        if (encrypted == null)
        {
            return null;
        }

        int len = encrypted.length;
        String str = "";
        for (int i=0; i<len; i++)
        {
            if ((encrypted[i]&0xFF)<16)
                str = str + "0" + java.lang.Integer.toHexString(encrypted[i]&0xFF);
            else
                str = str + java.lang.Integer.toHexString(encrypted[i]&0xFF);
        }
        return str;
    }

    public byte[] decrypt(String code) throws Exception
    {
        if(code == null || code.length() == 0)
            throw new Exception("Empty string");

        byte[] decrypted = null;

        try
        {
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            decrypted = cipher.doFinal(hexToBytes(code));
        }
        catch (Exception e)
        {
            throw new Exception("[decrypt] " + e.getMessage());
        }
        return decrypted;
    }

    public static String bytesToHex(byte[] data)
    {
        if (data==null)
        {
            return null;
        }

        int len = data.length;
        String str = "";
        for (int i=0; i<len; i++)
        {
            if ((data[i]&0xFF)<16)
                str = str + "0" + java.lang.Integer.toHexString(data[i]&0xFF);
            else
                str = str + java.lang.Integer.toHexString(data[i]&0xFF);
        }
        return str;
    }

    public static byte[] hexToBytes(String str)
    {
        if (str == null)
        {
            return null;
        }
        else if (str.length() < 2)
        {
            return null;
        }
        else
        {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i=0; i<len; i++)
            {
                buffer[i] = (byte) Integer.parseInt(str.substring(i*2,i*2+2),16);
            }
            return buffer;
        }
    }

    private static String padString(String source)
    {
        char paddingChar = ' ';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;

        for (int i = 0; i < padLength; i++)
        {
            source += paddingChar;
        }

        return source;
    }

}

/*
Usage :

HpEncrypt hpEncrypt = new HpEncrypt();
String e = hpEncrypt.encrypt(“plaintext”);
String d = new String(hpEncrypt.decrypt(e));

*/
