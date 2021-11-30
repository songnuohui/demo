package com.example.demo.utils;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 音频格式转换
 *
 * @author SongNuoHui
 * @date 2021/11/22 14:44
 */
public class VoiceConvertUtil {

    /**
     * MP3转换PCM文件方法
     *
     * @param mp3FilePath 原始文件路径
     * @param pcmFilePath 转换文件的保存路径
     */
    public static boolean convertMP32Pcm(String mp3FilePath, String pcmFilePath) {
        try {
            //获取文件的音频流，pcm的格式
            AudioInputStream audioInputStream = getPcmAudioInputStream(mp3FilePath);
            //将音频转化为  pcm的格式保存下来
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(pcmFilePath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 机能概要:获取文件的音频流
     *
     * @param mp3FilePath mp3文件路径
     */
    private static AudioInputStream getPcmAudioInputStream(String mp3FilePath) {
        File mp3 = new File(mp3FilePath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat;
        try {
            AudioInputStream in;

            //读取音频文件的类
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3);
            AudioFormat baseFormat = in.getFormat();

            //设定输出格式为pcm格式的音频文件
            targetFormat = new AudioFormat(baseFormat.getSampleRate(), 16,
                    1, true, false);

            //输出到音频
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }

    /**
     * MP3转换PCM文件方法
     *
     * @param mp3FilePath 原始文件路径
     * @param pcmFilePath 转换文件的保存路径
     * @throws Exception
     */
    public static void mp3ConvertToPcm(String mp3FilePath, String pcmFilePath) throws Exception {
        File mp3 = new File(mp3FilePath);
        File pcm = new File(pcmFilePath);
        //原MP3文件转AudioInputStream
        AudioInputStream mp3audioStream = AudioSystem.getAudioInputStream(mp3);
        //将AudioInputStream MP3文件 转换为PCM AudioInputStream
        AudioInputStream pcmaudioStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, mp3audioStream);
        //准备转换的流输出到OutputStream
        OutputStream os = new FileOutputStream(pcm);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = pcmaudioStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        pcmaudioStream.close();
    }
}
