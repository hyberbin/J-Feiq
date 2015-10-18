/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.jfeiq;

import org.jplus.jfeiq.feiq.FeiqServer;

/**
 *
 * @author hyberbin
 */
public class Test {
    public static void main(String[] args) {
        FeiqServer feiqServer = new FeiqServer();
        feiqServer.setServerName("Hybebrin");
        feiqServer.start();
        feiqServer.tellAll("Hybebrin已经启动!");
    }
}
