package com.demo.configBean;

import java.io.Serializable;

/**
 * Created by chenxyz on 2020/5/29.
 */
public class BaseBeanConfig implements Serializable {
    private static final long serialVersionUID = 12345673642257L;

    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
