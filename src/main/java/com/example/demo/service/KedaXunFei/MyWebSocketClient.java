package com.example.demo.service.KedaXunFei;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.exception.SuperCodeException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import static com.example.demo.service.KedaXunFei.VoiceErrorEnum.getMsgByCode;

/**
 * @author SongNuoHui
 * @date 2021/11/22 17:11
 */
@Slf4j
public class MyWebSocketClient extends WebSocketClient {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS");

    private final CountDownLatch handshakeSuccess;
    private final CountDownLatch connectClose;
    private final VoiceCovertDTO voiceCovertDTO;

    public MyWebSocketClient(URI serverUri, Draft protocolDraft, CountDownLatch handshakeSuccess, CountDownLatch connectClose) {
        super(serverUri, protocolDraft);
        this.handshakeSuccess = handshakeSuccess;
        this.connectClose = connectClose;
        if (serverUri.toString().contains("wss")) {
            trustAllHosts(this);
        }
        this.voiceCovertDTO= new VoiceCovertDTO();
    }

    public VoiceCovertDTO getVoiceCovertDTO() {
        return voiceCovertDTO;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        log.info(getCurrentTimeStr() + "\t连接建立成功！");
    }

    @SneakyThrows
    @Override
    public void onMessage(String msg) {
        JSONObject msgObj = JSON.parseObject(msg);
        String action = msgObj.getString("action");
        if (StringUtils.equals("started", action)) {
            // 握手成功
            log.info(getCurrentTimeStr() + "\t握手成功！sid: " + msgObj.getString("sid"));
            handshakeSuccess.countDown();
        } else if (StringUtils.equals("result", action)) {
            log.info("开始转写结果");
            // 转写结果
            String code = msgObj.getString("code");
            String desc = msgObj.getString("desc");
            String data = getContent(msgObj.getString("data"));
            String message = getMsgByCode(code);
            this.voiceCovertDTO.setCode(code);
            this.voiceCovertDTO.setData(data);
            this.voiceCovertDTO.setDesc(desc);
            this.voiceCovertDTO.setMessage(message);
            log.info(voiceCovertDTO.toString());
        } else if (StringUtils.equals("error", action)) {
            // 连接发生错误
            log.error("Error" + msg);
            String code = msgObj.getString("code");
            String message = getMsgByCode(code);
            throw new SuperCodeException(message, 500);
        }
    }

    @SneakyThrows
    @Override
    public void onError(Exception e) {
        log.error(getCurrentTimeStr() + "\t连接发生错误：" + e.getMessage() + ", " + new Date());
        throw new SuperCodeException(e.getMessage(), 500);
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        log.info(getCurrentTimeStr() + "\t链接关闭");
        connectClose.countDown();
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        try {
            log.info(getCurrentTimeStr() + "\t服务端返回：" + new String(bytes.array(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void trustAllHosts(MyWebSocketClient appClient) {
        //System.out.println("wss");
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            appClient.setSocket(sc.getSocketFactory().createSocket());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getCurrentTimeStr() {
        return sdf.format(new Date());
    }

    // 把转写结果解析为句子
    public static String getContent(String message) {
        StringBuffer resultBuilder = new StringBuffer();
        try {
            JSONObject messageObj = JSON.parseObject(message);
            JSONObject cn = messageObj.getJSONObject("cn");
            JSONObject st = cn.getJSONObject("st");
            JSONArray rtArr = st.getJSONArray("rt");
            for (int i = 0; i < rtArr.size(); i++) {
                JSONObject rtArrObj = rtArr.getJSONObject(i);
                JSONArray wsArr = rtArrObj.getJSONArray("ws");
                for (int j = 0; j < wsArr.size(); j++) {
                    JSONObject wsArrObj = wsArr.getJSONObject(j);
                    JSONArray cwArr = wsArrObj.getJSONArray("cw");
                    for (int k = 0; k < cwArr.size(); k++) {
                        JSONObject cwArrObj = cwArr.getJSONObject(k);
                        String wStr = cwArrObj.getString("w");
                        resultBuilder.append(wStr);
                    }
                }
            }
        } catch (Exception e) {
            return message;
        }

        return resultBuilder.toString();
    }
}
