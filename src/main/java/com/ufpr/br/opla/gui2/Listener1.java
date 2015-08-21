/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.gui2;

import logs.log_log.Listener;
import logs.log_log.LogLogData;

/**
 *
 * @author edipo
 * Temporario para logs ate resolver o problema de apresentar os logs na view
 * 
 */
public class Listener1 implements Listener {

    @Override
    public void message() {
	System.out.println("--->>"+LogLogData.printLog());
    }

}