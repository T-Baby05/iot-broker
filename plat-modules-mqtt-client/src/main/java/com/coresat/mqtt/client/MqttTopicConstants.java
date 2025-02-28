package com.coresat.mqtt.client;

/**
 * MqttTopicConstants
 *
 * @author Created by wangzaihong on 2024/08/09
 */
public class MqttTopicConstants {
    public static final String DEVICE_BROADCAST_TOPIC = "device/%s/broadcast";//device/A034BB00102/broadcast
    public static final String DEVICE_OPERATE_BROADCAST_TOPIC = "device/%s/operate_broadcast";

    public static final String DEVICE_BROADCAST_VOLUME_TOPIC = "device/%s/broadcast_volume";

    public static final String DEVICE_IMAGE_UPLOAD_TOPIC = "device/%s/image_upload";
    public static final String DEVICE_RESTART_TOPIC = "device/%s/dev_restart";
    public static final String DEVICE_KU_SETTINGS_TOPIC = "device/%s/ku_settings";
    public static final String DEVICE_KU_STATUS_TOPIC = "device/%s/ku_query";
    public static final String DEVICE_VOLUME_CTRL_TOPIC = "device/%s/volume/ctrl";
    public static final String DEVICE_CAMERA_ROTATION_TOPIC = "device/%s/camera_rotation";

    public static final String DEVICE_OTA_UPGRADE_TOPIC = "device/%s/ota_upgrade";

    public static final String DEVICE_SETTINGS_TOPIC = "device/%s/settings";
    public static final String DEVICE_PUBLISH_DATAS = "device/%s/datas";
    public static final String DEVICE_COMMAND_CONFIRM = "device/%s/command_status";
    public static final String DEVICE_LOCATION_INFO = "device/%s/dev_location";
    public static final String DEVICE_KU_INFO = "device/%s/ku_info";

     // 下发流ACK
    public static final String DEVICE_STREAM_ACK = "device/%s/stream_ack";
    // 下发设备证书查询指令
    public static final String DEVICE_CERT_QUERY = "device/%s/cert_query";
    // 上报设备证书
    public static final String DEVICE_CERT_REPORT = "device/cert_report";
    // 下发平台证书
    public static final String DEVICE_CERT_SEND = "device/cert_send";
    // 上传流主题
    public static final String DEVICE_REPORT_STREAM = "device/stream";
    public static final String DEVICE_UPGRADE_STATUS = "device/upgrade/status";
    public static final String DEVICE_VERSION_REPORT = "device/version/report";

    // 下发服务器配置
    public static final String DEVICE_SERVER_CONFIG = "device/%s/server_config";
    // 配置回传频次
    public static final String DEVICE_REPORT_FREQUENCY = "device/%s/report_frequency";
    // L波段配置
    public static final String DEVICE_L_SETTINGS_TOPIC = "device/%s/l_settings";
    // L波段查询
    public static final String DEVICE_L_STATUS_TOPIC = "device/%s/l_query";
    // L波段查询返回
    public static final String DEVICE_L_INFO = "device/%s/l_info";
}
