package com.example.demo.service.KedaXunFei;

import com.example.demo.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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

    /////
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


    public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {
        File file = new File(AUDIO_PATH);

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

            try {
                FileInputStream stream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);
                byte[] bytes = new byte[CHUNCKED_SIZE];
                int sLen;
                long lastTs = 0;
                while (true) {
                    sLen = bufferedInputStream.read(bytes);
                    if (sLen < CHUNCKED_SIZE) {
                        send(client, bytes);
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
                    Thread.sleep(40);
                }
                // 发送结束标识
                send(client, "{\"end\": true}".getBytes());

                log.info(getCurrentTimeStr() + "\t发送结束标识完成");

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            // 等待连接关闭
            connectClose.await();

            log.warn("最终结果>>>" + client.getVoiceCovertDTO());
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

    /*
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
                    log.error("文件删除成功");
                } else {
                    log.error("文件删除失败");
                }
            }*/
}
