package com.example.demo.excel.po;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.Data;

/**
 * @author SongNuoHui
 * @date 2021/10/21 16:48
 */
@Data
public class Bu {
    private ExcelWriter build;
    private WriteSheet sheet;
}

