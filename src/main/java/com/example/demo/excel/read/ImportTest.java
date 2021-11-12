package com.example.demo.excel.read;

import com.alibaba.excel.EasyExcel;
import com.example.demo.excel.listener.ProductBatchListener;
import com.example.demo.excel.po.ProductBatchDo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SongNuoHui
 * @date 2021/10/20 10:57
 */
public class ImportTest {
    public static void main(String[] args) {
        /*String fileName = "D:" + File.separator + "my" + File.separator + "ddd" + File.separator + "1634698577932.xlsx";
        EasyExcel.read(fileName, ProductBatchDo.class,new ProductBatchListener()).sheet().doRead();
        System.out.println("完成");*/
        String s = "cdcc";
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        List<String> list= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("0");
        }
        System.out.println(list);

        if (!s.equals("sss")&&!s.equals("bbb")&&!s.equals("ccc")){
            System.out.println("检查");
        }else {
            System.out.println("不检查");
        }


    }

}
