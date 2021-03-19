/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniajc.schoolpickup.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Encryption {

    public static String encrypt(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }
}
