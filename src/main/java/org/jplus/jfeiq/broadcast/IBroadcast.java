/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.jfeiq.broadcast;

import org.jplus.jfeiq.feiq.IPMSGData;

/**
 * UDP广播
 * @author hyberbin
 */
public interface IBroadcast {
    IPMSGData broadcast(String msg);
}
