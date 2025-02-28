package com.coresat.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class RandomImageGenerator {

    public static byte[] generateRandomImage(int width, int height) throws IOException {
        // 创建一个 BufferedImage 对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取 Graphics 对象
        Graphics2D graphics = image.createGraphics();

        // 填充背景颜色为白色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // 随机绘制一些内容
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            // 设置随机颜色
            graphics.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            // 绘制随机矩形
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int w = random.nextInt(width / 2);
            int h = random.nextInt(height / 2);
            graphics.fillRect(x, y, w, h);
        }

        // 释放资源
        graphics.dispose();

        // 将 BufferedImage 写入 ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        // 返回字节数组
        return outputStream.toByteArray();
    }

    public static void main(String[] args) {
        try {
            // 调用方法生成图片字节数组
            byte[] imageBytes = generateRandomImage(400, 300);
            System.out.println("Generated image with byte array size: " + imageBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
