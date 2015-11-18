package com.obobod.cache.redis;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerializeUtil {
    private static final Logger log = Logger.getLogger(SerializeUtil.class);

    public static byte[] serialize(Object object) {

        ObjectOutputStream oos = null;

        ByteArrayOutputStream baos = null;

        try {

            // 序列化

            baos = new ByteArrayOutputStream();

            oos = new ObjectOutputStream(baos);

            oos.writeObject(object);

            byte[] bytes = baos.toByteArray();

            return bytes;

        } catch (Exception e) {
            log.error("serialize error：" + e.getMessage());
        }

        return null;

    }

    public static Object unserialize(byte[] bytes) {

        ByteArrayInputStream bais = null;

        try {

            // 反序列化

            bais = new ByteArrayInputStream(bytes);

            ObjectInputStream ois = new ObjectInputStream(bais);

            return ois.readObject();

        } catch (Exception e) {
            log.error("unserialize error：" + e.getMessage());
        }

        return null;

    }

}
