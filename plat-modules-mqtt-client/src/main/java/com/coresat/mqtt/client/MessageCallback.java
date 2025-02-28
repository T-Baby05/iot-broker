package com.coresat.mqtt.client;

import com.alibaba.fastjson.JSON;
import com.coresat.protocolv2.forward.*;
import com.coresat.protocolv2.reverse.*;
import com.coresat.utils.RandomImageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


/**
 * MessageCallback
 *
 * @author Created by wangzaihong on 2024/08/09
 */
@Slf4j
public class MessageCallback implements MqttCallbackExtended {

    private final MqttClient client;

    public MessageCallback(MqttClient client) {
        this.client = client;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        throwable.printStackTrace();
        log.info("connect lost, cause: {}", throwable.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("接收消息主题 : " + topic);
//        BaseCommandV2 commandV2 = JSON.parseObject(message.getPayload(), BaseCommandV2.class);
        log.info("接收到主题数据信息：{}", new String(message.getPayload()));

//        if (Pattern.compile("^device/[^/]+/broadcast$").matcher(topic).matches()) {
//            BroadcastCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), BroadcastCommandV2.class);
//            log.info("receive BroadcastCommandV2: {}", commandV2Rel);
//            handleBroadcastCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/camera_rotation$").matcher(topic).matches()) {
//            CameraRotationCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), CameraRotationCommandV2.class);
//            log.info("receive CameraRotationCommandV2: {}", commandV2Rel);
//            handleCameraRotationCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/settings$").matcher(topic).matches()) {
//            DeviceSettingsCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), DeviceSettingsCommandV2.class);
//            log.info("receive DeviceSettingsCommandV2: {}", commandV2Rel);
//            handleDeviceSettingsCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/cert_send$").matcher(topic).matches()) {
//            CertCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), CertCommandV2.class);
//            log.info("receive CertCommandV2: {}", commandV2Rel);
//            handleCertCommandV2(commandV2Rel);
//        } else if (commandV2 instanceof DataCollectFreqCommandV2) {//开发环境还未设计该信令
//            DataCollectFreqCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), DataCollectFreqCommandV2.class);
//            log.info("receive DataCollectFreqCommandV2: {}", commandV2Rel);
//            handleDataCollectFreqCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/ku_settings$").matcher(topic).matches()) {
//            DevKuSettingsCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), DevKuSettingsCommandV2.class);
//            log.info("receive DevKuSettingsCommandV2: {}", commandV2Rel);
//            handleDevKuSettingsCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/dev_restart$").matcher(topic).matches()) {
//            DevRestartCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), DevRestartCommandV2.class);
//            log.info("receive DevRestartCommandV2: {}", commandV2Rel);
//            handleDevRestartCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/volume/ctrl$").matcher(topic).matches()) {
//            DevVolumeCtrlCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), DevVolumeCtrlCommandV2.class);
//            log.info("receive DevVolumeCtrlCommandV2: {}", commandV2Rel);
//            handleDevVolumeCtrlCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/image_upload$").matcher(topic).matches()) {
//            ImageUploadCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), ImageUploadCommandV2.class);
//            log.info("receive ImageUploadCommandV2: {}", commandV2Rel);
//            handleImageUploadCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/ku_query$").matcher(topic).matches()) {
//            KuStatusQueryCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), KuStatusQueryCommandV2.class);
//            log.info("receive KuStatusQueryCommandV2: {}", commandV2Rel);
//            handleKuStatusQueryCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/ota_upgrade$").matcher(topic).matches()) {
//            OtaUpgradeCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), OtaUpgradeCommandV2.class);
//            log.info("receive OtaUpgradeCommandV2: {}", commandV2Rel);
//            handleOtaUpgradeCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/stream_ack$").matcher(topic).matches()) {
//            StreamAckCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), StreamAckCommandV2.class);
//            log.info("receive StreamAckCommandV2: {}", commandV2Rel);
//            handleStreamAckCommandV2(commandV2Rel);
//        } else if (Pattern.compile("^device/[^/]+/l_query$").matcher(topic).matches()){
//            LStatusQueryCommandV2 commandV2Rel = JSON.parseObject(message.getPayload(), LStatusQueryCommandV2.class);
//            log.info("receive LStatusQueryCommandV2: {}", commandV2Rel);
//            handleLStatusQueryCommandV2(commandV2Rel);
//        } else {
//            log.info("unsupported command: {}", JSON.toJSON(commandV2));
//        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        // 订阅
        String clientId = client.getClientId();
        try {
            client.subscribe(String.format(MqttTopicConstants.DEVICE_BROADCAST_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_OPERATE_BROADCAST_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_BROADCAST_VOLUME_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_IMAGE_UPLOAD_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_RESTART_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_KU_SETTINGS_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_KU_STATUS_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_VOLUME_CTRL_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_CAMERA_ROTATION_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_OTA_UPGRADE_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_SETTINGS_TOPIC, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_STREAM_ACK, clientId));
            client.subscribe(MqttTopicConstants.DEVICE_CERT_SEND);
            client.subscribe(String.format(MqttTopicConstants.DEVICE_CERT_QUERY, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_SERVER_CONFIG, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_REPORT_FREQUENCY, clientId));
            client.subscribe(String.format(MqttTopicConstants.DEVICE_L_STATUS_TOPIC, clientId));

            // ************************************测试topic*************************************************
//            String broadcast = "broadcasting-test";
//            String unicast = "unicast/" + clientId + "/test";
//            client.subscribe(broadcast);
//            client.subscribe(unicast);
            reportDefaultData();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    private void reportDefaultData() throws MqttException {
        String clientId = client.getClientId();
//        StatusReport report = StatusReport.builder().cid(clientId).g4Num("").bdNum("").lon(103.6F).lat(30.4F).soc(64F).voltage(13.2F).coma("1")
//                .d4G(16).dBD(16).srKU(7.0).srL(7.0).lockStatus(1).kuNetStatus(1).build();
//        String topic = String.format(MqttTopicConstants.DEVICE_PUBLISH_DATAS, clientId);
//        client.publish(topic, new MqttMessage(JSON.toJSONBytes(report)));
//        log.info("send status report success....");
//        VersionReport versionReport = VersionReport.builder().cid(clientId).lSoftVersion("1.0.1").lFirmVersion("1.0.0")
//                .gatewaySoftVersion("1.0.0").gatewayFirmVersion("1.0.0").routerFirmVersion("1.0.0").routerSoftVersion("1.0.0")
//                .mcuFirmVersion("1.0.0").mcuSoftVersion("1.0.0").rdssFirmVersion("1.0.0").rdssSoftVersion("1.0.1").build();
//        client.publish(MqttTopicConstants.DEVICE_VERSION_REPORT, new MqttMessage(JSON.toJSONBytes(versionReport)));
        String payload = "test" + clientId;
        client.publish("mqtt-test", new MqttMessage(payload.getBytes(StandardCharsets.UTF_8)));
        log.info("send version report success....");
    }

    private void handleBroadcastCommandV2(BroadcastCommandV2 commandV2) throws MqttException, InterruptedException {
        String clientId = client.getClientId();
        CommandExeStatus startReport = new CommandExeStatus(commandV2.getBid(), "BFKS");
        String topic = String.format(MqttTopicConstants.DEVICE_COMMAND_CONFIRM, clientId);
        client.publish(topic, new MqttMessage(JSON.toJSONBytes(startReport)));
        Thread.sleep(1000);
        CommandExeStatus endReport = new CommandExeStatus(commandV2.getBid(), "BFJS");
        client.publish(topic, new MqttMessage(JSON.toJSONBytes(endReport)));
    }

    private void handleCameraRotationCommandV2(CameraRotationCommandV2 commandV2) {
        log.info("handleCameraRotationCommandV2 method executed");
    }

    private void handleCertCommandV2(CertCommandV2 commandV2) {
        log.info("handleCertCommandV2 method executed");
    }

    private void handleDataCollectFreqCommandV2(DataCollectFreqCommandV2 commandV2) {
        log.info("handleDataCollectFreqCommandV2 method executed");
    }

    private void handleDeviceSettingsCommandV2(DeviceSettingsCommandV2 commandV2) {
        log.info("handleDeviceSettingsCommandV2 method executed");
    }

    private void handleDevKuSettingsCommandV2(DevKuSettingsCommandV2 commandV2) {
        log.info("handleDevKuSettingsCommandV2 method executed");
    }

    private void handleDevRestartCommandV2(DevRestartCommandV2 commandV2) {
        log.info("handleDevRestartCommandV2 method executed");
    }

    private void handleDevVolumeCtrlCommandV2(DevVolumeCtrlCommandV2 commandV2) {
        log.info("handleDevVolumeCtrlCommandV2 method executed");
    }

    private void handleImageUploadCommandV2(ImageUploadCommandV2 commandV2) throws IOException, MqttException {
        log.info("handleImageUploadCommandV2 method executed");
        long bid = commandV2.getBid();
        String clientId = client.getClientId();
        byte[] imageBytes = RandomImageGenerator.generateRandomImage(400, 300);
        // 先发送md5数据
        byte[] md5Bytes = buildHead(clientId, bid, imageBytes);
        byte[] md5Payload = Base64Utils.encode(md5Bytes);
        client.publish(MqttTopicConstants.DEVICE_REPORT_STREAM, new MqttMessage(md5Payload));
        // 再发送image数据
        byte[] payload = buildPayload(clientId, bid, imageBytes);
        byte[] encodePayload = Base64Utils.encode(payload);
        client.publish(MqttTopicConstants.DEVICE_REPORT_STREAM, new MqttMessage(encodePayload));
    }

    private static byte[] buildHead(String clientId, long bid, byte[] imageBytes) {
        byte[] md5Bytes = DigestUtils.md5Digest(imageBytes);
        int total = 37 + md5Bytes.length;
        ByteBuffer buffer = ByteBuffer.allocate(total);
        // ver
        buffer.put("V".getBytes());
        buffer.put("1".getBytes());
        // seq
        buffer.putInt(imageBytes.length);
        // dev_code
        buffer.put(clientId.getBytes(), 0, clientId.getBytes().length);
        // total
        buffer.putInt(total);
        // package
        buffer.putShort((short) 1);
        // length
        buffer.putShort((short) imageBytes.length);
        // type
        buffer.put((byte) 0);
        // number
        buffer.put((byte) 0);
        //retrans
        buffer.put((byte) 1);
        // over
        buffer.put((byte) 1);
        // task_id
        buffer.putLong(bid);
        // data
        buffer.put(md5Bytes, 0, md5Bytes.length);
        return buffer.array();
    }

    private static byte[] buildPayload(String clientId, long bid, byte[] imageBytes) {
        int total = 37 + imageBytes.length;
        ByteBuffer buffer = ByteBuffer.allocate(total);
        // ver
        buffer.put("V".getBytes());
        buffer.put("1".getBytes());
        // seq
        buffer.putInt(imageBytes.length);
        // dev_code
        buffer.put(clientId.getBytes(), 0, clientId.getBytes().length);
        // total
        buffer.putInt(total);
        // package
        buffer.putShort((short) 1);
        // length
        buffer.putShort((short) imageBytes.length);
        // type
        buffer.put((byte) 1);
        // number
        buffer.put((byte) 1);
        //retrans
        buffer.put((byte) 1);
        // over
        buffer.put((byte) 1);
        // task_id
        buffer.putLong(bid);
        // data
        buffer.put(imageBytes, 0, imageBytes.length);
        return buffer.array();
    }

    private void handleKuStatusQueryCommandV2(KuStatusQueryCommandV2 commandV2) throws MqttException {
        log.info("handleKuStatusQueryCommandV2 method executed");
        String clientId = client.getClientId();
        KuStatusResponse report = new KuStatusResponse(clientId, 10000000L, 100000L, 1, 1, 1);
        String topic = String.format(MqttTopicConstants.DEVICE_KU_INFO, clientId);
        client.publish(topic, new MqttMessage(JSON.toJSONBytes(report)));
        log.info("command response: {}", report);
    }

    private void handleLStatusQueryCommandV2(LStatusQueryCommandV2 commandV2) throws MqttException {
        log.info("handleLStatusQueryCommandV2 method executed");
        String clientId = client.getClientId();
        LStatusResponse report = new LStatusResponse(10000000L, 100000L, 1);
        String topic = String.format(MqttTopicConstants.DEVICE_L_INFO, clientId);
        client.publish(topic, new MqttMessage(JSON.toJSONBytes(report)));
        log.info("command response: {}", report);
    }

    private void handleOtaUpgradeCommandV2(OtaUpgradeCommandV2 commandV2) throws MqttException {
        log.info("handleOtaUpgradeCommandV2 method executed");
        String clientId = client.getClientId();
        Long imageId = null == commandV2.getImageId() ? 1L : commandV2.getImageId();
        UpgradeReport report = new UpgradeReport(clientId, imageId, (short) 2, "1.0.1");
        client.publish(MqttTopicConstants.DEVICE_UPGRADE_STATUS, new MqttMessage(JSON.toJSONBytes(report)));
        log.info("command response: {}", report);
    }

    private void handleStreamAckCommandV2(StreamAckCommandV2 commandV2) {
        log.info("handleStreamAckCommandV2 method executed");
    }
}