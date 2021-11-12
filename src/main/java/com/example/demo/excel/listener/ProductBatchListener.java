package com.example.demo.excel.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.example.demo.excel.po.ProductBatchDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.excel.file.FileUtil.makeFile;

/**
 * @author SongNuoHui
 * @date 2021/10/20 11:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductBatchListener extends AnalysisEventListener<ProductBatchDo> {
    private static final Logger logger = LoggerFactory.getLogger(ProductBatchDo.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<ProductBatchDo> list = new ArrayList<>(BATCH_COUNT);

    @Override
    public void invoke(ProductBatchDo productBatchDo, AnalysisContext analysisContext) {
        logger.info("解析到一条数据:{}", JSON.toJSONString(productBatchDo));
        list.add(productBatchDo);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list = new ArrayList<>(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        logger.info("所有数据解析完成！");
    }

    void saveData() {
        try {
            String fileName = System.currentTimeMillis() + ".xlsx";
            String filePath = makeFile(fileName);
            EasyExcel.write(filePath, ProductBatchDo.class).sheet("错误的数据").doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
