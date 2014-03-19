/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author elf
 */
public class Id {

    private static final int ID_LENGTH = 10;

    public static String generateUniqueId() {
        return RandomStringUtils.randomNumeric(ID_LENGTH);
    }

}
