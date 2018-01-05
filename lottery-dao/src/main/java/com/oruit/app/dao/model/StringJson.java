package com.oruit.app.dao.model;

/**
 * @author wangyt
 * @version V1.0
 * @Package ${com.oruit.app.dao.model}
 * @date 2017-11-24 18:02
 */
public class StringJson {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                "content='" + content + '\'' +
                '}';
    }
}
