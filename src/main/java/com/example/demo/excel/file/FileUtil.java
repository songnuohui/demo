package com.example.demo.excel.file;

import java.io.File;
import java.io.IOException;

/**
 * @author SongNuoHui
 * @date 2021/10/21 16:49
 */
public class FileUtil {

    /**
     * 创建文件
     */
    public static String makeFile(String fileName) throws IOException {
        System.out.println("File.separator:" + File.separator);
        File testFile = new File("D:" + File.separator + "my" + File.separator + "ddd" + File.separator + fileName);
        File fileParent = testFile.getParentFile();//返回的是File类型,可以调用exsit()等方法
        String fileParentPath = testFile.getParent();//返回的是String类型
        System.out.println("fileParent:" + fileParent);
        System.out.println("fileParentPath:" + fileParentPath);
        if (!fileParent.exists()) {
            fileParent.mkdirs();// 能创建多级目录
        }
        if (!testFile.exists())
            testFile.createNewFile();//有路径才能创建文件
        System.out.println(testFile);

        String path = testFile.getPath();
        String absolutePath = testFile.getAbsolutePath();//得到文件/文件夹的绝对路径
        String getFileName = testFile.getName();//得到文件/文件夹的名字
        System.out.println("path:" + path);
        System.out.println("absolutePath:" + absolutePath);
        System.out.println("getFileName:" + getFileName);
        return path;
    }

    /**
     * 删除文件
     */
    public static void removeFile() {
        String fileName = "D:" + File.separator + "my" + File.separator + "ddd" + File.separator + "1634698577932.xlsx";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("文件" + fileName + "不存在，删除失败！");
        } else {
            if (file.exists()) {
                file.delete();
                System.out.println("删除成功！");
            }
        }
    }

}
