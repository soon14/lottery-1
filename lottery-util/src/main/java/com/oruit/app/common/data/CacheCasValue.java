/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.common.data;

/**
 *
 * @author hanfeng
 */
public class CacheCasValue {
    private long cas;
    private Object value;

    public long getCas() {
        return cas;
    }

    public void setCas(long cas) {
        this.cas = cas;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CasValue{" + "cas=" + cas + ", value=" + value + '}';
    }
}
