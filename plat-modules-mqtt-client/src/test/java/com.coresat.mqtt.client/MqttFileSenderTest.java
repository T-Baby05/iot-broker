package com.coresat.mqtt.client;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MqttFileSenderTest {
    private static final String BROKER = "tcp://192.168.23.5:1883";  // 替换为实际的MQTT Broker地址
    private static final String TOPIC = "device/stream";
    private static final String CLIENT_ID = "860881078259563";
    private static final String USERNAME = "coresat";
    private static final String PASSWORD = "84b636627d651bbe0ca98d3481f38ebc75e91bebe624df2c6f480bc3a8b24a7a";
    private static final int CHUNK_SIZE = 4096;  // 1KB大小的块

    public static void main(String[] args) {
//        try {

//            MqttClient client = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
////            client.connect();
//
//            client = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
//            MqttConnectOptions options = new MqttConnectOptions();
//            options.setCleanSession(true);
//            options.setUserName(USERNAME);
//            options.setPassword(PASSWORD.toCharArray());
//            options.setConnectionTimeout(30000);
//            options.setKeepAliveInterval(100);
//            MqttCustomerClient.setClient(client);
//            try {
////                client.setCallback(messageCallback);
//                client.connect(options);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            // 读取文件并发送
//            ClassLoader loader = Thread.currentThread().getContextClassLoader();
//            String fileName = "/Users/apple/IdeaProjects/iot-platform/plat-modules/plat-modules-mqtt-client/src/test/resources/picture.jpg";
////            InputStream resourceAsStream = loader.getResourceAsStream(fileName);
////            File file = new File("picture.jpg");
//            try (FileInputStream fis = new FileInputStream(fileName)) {
//                byte[] buffer = new byte[CHUNK_SIZE];
//                int bytesRead;
//                int partNumber = 0;
//
//                while ((bytesRead = fis.read(buffer)) != -1) {
//                    // 如果最后一块小于CHUNK_SIZE，调整数据大小
//
//                    byte[] encode = Base64Utils.encode(buffer);
//                    byte[] dataChunk = new byte[encode.length + 32];
//                    byte[] bytes = buildHead();
//                    System.arraycopy(bytes, 0, dataChunk, 0, bytes.length);
//                    System.arraycopy(encode, 0, dataChunk, bytes.length, encode.length);
//
////                    Base64Utils.encode(dataChunk)
//                    MqttMessage message = new MqttMessage(dataChunk);
//                    message.setQos(1);  // 确保消息有一次送达
//                    message.setRetained(false);
//                    client.publish(TOPIC, message);
//                    partNumber++;
//                }
//
//                System.out.println("File sent successfully in " + partNumber + " parts.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            client.disconnect();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
    }

    private static byte[] buildHead() {

        byte[] headSeq = {(byte) 0, (byte) 0, (byte) 0, (byte) 1};
        byte[] headDevCode = {(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1};
        byte[] headPackages = {(byte) 0, (byte) 0, (byte) 0, (byte) 5};

        byte[] headTotal = ByteBuffer.allocate(2).putShort((short) 44).array();//{(byte)0, (byte)6, (byte)203, (byte)2};
        byte[] headLength = {(byte) 0, (byte) 8}; //1024
        byte[] headType = {(byte) 1};
        byte[] headNumber = {(byte) 0};
        byte[] headReTrans = {(byte) 0};
        byte[] headReOver = {(byte) 0};
        byte[] headRe = {(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 5};


        byte[] head = new byte[32];
        System.arraycopy(headSeq, 0, head, 0, headSeq.length);
        System.arraycopy(headDevCode, 0, head, headSeq.length, headDevCode.length);

        System.arraycopy(headPackages, 0, head, headSeq.length + headDevCode.length, headPackages.length);
        System.arraycopy(headTotal, 0, head, headSeq.length + headDevCode.length + headPackages.length, headTotal.length);
        System.arraycopy(headLength, 0, head, headTotal.length + headDevCode.length + headPackages.length + headSeq.length, headLength.length);
        System.arraycopy(headType, 0, head, headTotal.length + headDevCode.length + headPackages.length + headSeq.length + headLength.length, headType.length);
        System.arraycopy(headNumber, 0, head, headTotal.length + headDevCode.length + headPackages.length + headSeq.length + headLength.length + headType.length, headNumber.length);
        System.arraycopy(headReTrans, 0, head, headTotal.length + headDevCode.length + headPackages.length + headSeq.length + headLength.length + headType.length + headNumber.length, headReTrans.length);
        System.arraycopy(headReOver, 0, head, headTotal.length + headDevCode.length + headPackages.length + headSeq.length + headLength.length + headType.length + headNumber.length + headReTrans.length, headReOver.length);
        System.arraycopy(headRe, 0, head, headTotal.length + headDevCode.length + headPackages.length + headSeq.length + headLength.length + headType.length + headNumber.length + headReTrans.length + headReOver.length, headRe.length);

        return head;

    }

}
