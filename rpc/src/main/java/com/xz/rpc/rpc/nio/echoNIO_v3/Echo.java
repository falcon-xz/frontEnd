package com.xz.rpc.rpc.nio.echoNIO_v3;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * falcon -- 2016/11/6.
 */
class Echo implements Serializable {
    private String name;
    private Date date;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Echo{")
                .append("name:").append(name).append(",");
        if (date != null) {
            sb.append("date:").append(new SimpleDateFormat("yyyyMMdd").format(date)).append(",");
        }
        sb.append("}");
        return sb.toString();
    }
}
