/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.jfeiq.mq;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hyberbin
 */
public class SimpleFeiqMq<T> extends LinkedList<T>{
    
    private static final Logger log=Logger.getLogger(SimpleFeiqMq.class.getName());

    /**最大的队列大小 如果超出这个大小队列将被清空*/
    private final int maxQueSize;

    public SimpleFeiqMq(int maxQueSize) {
        this.maxQueSize=maxQueSize;
    }

    

    @Override
    public boolean add(T e) {
        if(size()>maxQueSize){
            log.log(Level.SEVERE, "已经超出队列的大小,未发送出去的消息将被清空!");
            clear();
        }
        return super.add(e); 
    }
    
    
}
