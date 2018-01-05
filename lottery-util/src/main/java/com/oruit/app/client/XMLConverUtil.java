package com.oruit.app.client;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * XML 数据接收对象转换工具类
 *
 * @author LiYi
 *
 */
public class XMLConverUtil {

    private static Map<Class<?>, Unmarshaller> uMap = new HashMap<>();
    private static Map<Class<?>, Marshaller> mMap = new HashMap<>();

    /**
     * XML to Object
     *
     * @param <T>
     * @param clazz
     * @param xml
     * @return
     */
    public static <T> T convertToObject(Class<T> clazz, String xml) {
        return convertToObject(clazz, new StringReader(xml));
    }

    /**
     * XML to Object
     *
     * @param <T>
     * @param clazz
     * @param inputStream
     * @return
     */
    public static <T> T convertToObject(Class<T> clazz, InputStream inputStream) {
        return convertToObject(clazz, new InputStreamReader(inputStream));
    }

    /**
     * XML to Object
     *
     * @param <T>
     * @param clazz
     * @param reader
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(Class<T> clazz, Reader reader) {
        try {
            if (!uMap.containsKey(clazz)) {
                JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                uMap.put(clazz, unmarshaller);
            }
            return (T) uMap.get(clazz).unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object to XML
     *
     * @param object
     * @return
     */
    public static String convertToXML(Object object) {
        try {
            if (!mMap.containsKey(object.getClass())) {
                JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                    @Override
                    public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
                        writer.write(ac, i, j);
                    }
                });
                mMap.put(object.getClass(), marshaller);
            }
            StringWriter stringWriter = new StringWriter();
            mMap.get(object.getClass()).marshal(object, stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*public static String convertToXMLGBK(Object object) {
        try {
            if (!mMap.containsKey(object.getClass())) {
                JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
                Marshaller marshaller = jaxbContext.createMarshaller();
                //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                    @Override
                    public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
                        writer.write(ac, i, j);
                    }
                });
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
                mMap.put(object.getClass(), marshaller);
            }
            StringWriter stringWriter = new StringWriter();
            mMap.get(object.getClass()).marshal(object, stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }*/
    public static String convertToXMLGBK(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // xml格式化
            //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 去掉生成xml的默认报文头
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            // 不进行转义字符的处理
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                @Override
                public void escape(char[] ch, int start,int length, boolean isAttVal, Writer writer) throws IOException {
                    writer.write(ch, start, length);
                }
            });
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
