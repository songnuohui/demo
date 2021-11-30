package com.example.demo.service.KedaXunFei;

import com.example.demo.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import static com.example.demo.service.KedaXunFei.MyWebSocketClient.getCurrentTimeStr;

/**
 * 科大讯飞语音转文字
 *
 * @author SongNuoHui
 * @date 2021/11/22 9:46
 */
/*@Service*/
@Slf4j
public class VoiceToTextService {

    // appid
    private static final String APPID = "科大讯飞";

    // appid对应的secret_key
    private static final String SECRET_KEY = "科大讯飞";

    // 请求地址
    private static final String HOST = "rtasr.xfyun.cn/v1/ws";

    private static final String BASE_URL = "wss://" + HOST;

    private static final String ORIGIN = "https://" + HOST;

    // 音频文件路径
    private static final String AUDIO_PATH = "C:\\Users\\dell\\Desktop\\测试mp3音频.pcm";

    // 每次发送的数据大小 1280 字节
    private static final int CHUNCKED_SIZE = 1280;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS");


    private static final String QINIU_UPLOAD_DOMAIN = "http://filetest.cjm.so";

    //private static final String QINIU_UPLOAD_DOMAIN = "http://filetest.cjm.so/c8fb6483c4f14aa7b8ef157e6da21207?attname=测试pcm音频.pcm";


    /*public void voiceToText() throws URISyntaxException, InterruptedException */

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        byte[] oBytes = restTemplate.getForObject(QINIU_UPLOAD_DOMAIN + "/" + "c8fb6483c4f14aa7b8ef157e6da21207", byte[].class);
        File file = genFile(oBytes);

        while (true) {
            URI url = new URI(BASE_URL + getHandShakeParams(APPID, SECRET_KEY));
            DraftWithOrigin draft = new DraftWithOrigin(ORIGIN);
            CountDownLatch handshakeSuccess = new CountDownLatch(1);
            CountDownLatch connectClose = new CountDownLatch(1);
            MyWebSocketClient client = new MyWebSocketClient(url, draft, handshakeSuccess, connectClose);

            client.connect();

            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                log.info(getCurrentTimeStr() + "\t连接中");
                Thread.sleep(1000);
            }

            // 等待握手成功
            handshakeSuccess.await();
            log.info(sdf.format(new Date()) + " 开始发送音频数据");

            // 发送音频
            byte[] bytes = new byte[CHUNCKED_SIZE];

            try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                int len = -1;
                long lastTs = 0;
                while ((len = raf.read(bytes)) != -1) {
                    if (len < CHUNCKED_SIZE) {
                        send(client, Arrays.copyOfRange(bytes, 0, len));
                        break;
                    }

                    long curTs = System.currentTimeMillis();
                    if (lastTs == 0) {
                        lastTs = System.currentTimeMillis();
                    } else {
                        long s = curTs - lastTs;
                        if (s < 40) {
                            log.info("error time interval: " + s + " ms");
                        }
                    }
                    send(client, bytes);
                    // 每隔40毫秒发送一次数据
                    Thread.sleep(40);
                }

                // 发送结束标识
                send(client, "{\"end\": true}".getBytes());

                log.info(getCurrentTimeStr() + "\t发送结束标识完成");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.info("开始删除文件");
                String absolutePath = file.getAbsolutePath();
                log.info("文件路径>>>" + absolutePath);
                if (file.delete()) {
                    log.info("文件删除成功");
                } else {
                    log.info("文件删除失败");
                }
            }
            /*try {
                int length = oBytes == null ? 0 : oBytes.length;
                int tLen = 1;
                int i = 1;
                while (tLen < length) {
                    int sub = CHUNCKED_SIZE;
                    if ((tLen + CHUNCKED_SIZE) > length) {
                        sub = length - tLen;
                    }
                    System.arraycopy(oBytes, tLen - 1, bytes, 0, sub);
                    send(client, bytes);
                    tLen = i * CHUNCKED_SIZE;
                    i++;
                    Thread.sleep(40);
                }
                // 发送结束标识
                send(client, "{\"end\": true}".getBytes());
                log.info(getCurrentTimeStr() + "\t发送结束标识完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
*/
            // 等待连接关闭
            connectClose.await();
            log.info("最终结果>>>" + client.getVoiceCovertDTO());
            break;
        }

    }

    /**
     * 生成握手参数
     */
    public static String getHandShakeParams(String appId, String secretKey) {
        String ts = System.currentTimeMillis() / 1000 + "";
        String signa = "";
        try {
            signa = EncryptUtil.HmacSHA1Encrypt(Objects.requireNonNull(EncryptUtil.MD5(appId + ts)), secretKey);
            return "?appid=" + appId + "&ts=" + ts + "&signa=" + URLEncoder.encode(signa, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void send(WebSocketClient client, byte[] bytes) {
        if (client.isClosed()) {
            throw new RuntimeException("client connect closed!");
        }

        client.send(bytes);
    }


    public static File genFile(byte[] bytes) {
        String filePath = "C:\\Users\\dell\\Desktop";
        String fileName = System.currentTimeMillis() + ".pcm";
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) { //判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;

    }
}
