package com.example.demo.excel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.demo.excel.po.Bu;
import com.example.demo.excel.po.ProductImportDo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.excel.file.FileUtil.makeFile;

/**
 * @author SongNuoHui
 * @date 2021/10/21 16:56
 */
@Slf4j
public class ProductImportWrite {

    public static void main(String[] args) {
        ExcelWriter build = null;
        WriteSheet sheet = null;
        try {
            String fileName = "产品导入.xlsx";
            log.info("开始写入");
            ProductImportWrite exportTest = new ProductImportWrite();
            Bu bu = exportTest.buildErr(fileName);
            build = bu.getBuild();
            sheet = bu.getSheet();
            /*for (int i = 0; i < 2; i++) {
                build.write(buildList(), sheet);
            }*/
            build.write(buildList(), sheet);
            log.info("写入完成");
        } catch (IOException e) {
            log.warn(e.getMessage());
        } finally {
            if (build != null) {
                build.finish();
            }
        }
    }


    private Bu buildErr(String fileName) throws IOException {
        String filePath = makeFile(fileName);
        log.info("开始写入错误的数据>>>>>>");
        ExcelWriter build = EasyExcel.write(filePath, ProductImportDo.class).build();
        WriteSheet sheet = EasyExcel.writerSheet("错误数据").build();
        Bu bu = new Bu();
        bu.setBuild(build);
        bu.setSheet(sheet);
        return bu;
    }

    public static List<ProductImportDo> buildList() {
        List<ProductImportDo> list = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            ProductImportDo po = new ProductImportDo();
            po.setProducCategory("010105");
            po.setProductName("产品名称" + i);
            po.setProductCode("产品编码" + i);
            po.setProductBarcode("0101" + i);
            po.setProductSortName("产品分类2");
            po.setProductSpecificationsName("产品规格" + i);
            po.setProductModel("产品型号" + i);
            po.setShelfLifeLabel("保质期");
            po.setShelfLifeValue("" + i);
            po.setFiledName("自定义字段名称" + i);
            po.setFiledText("自定义字段内容" + i);
            po.setSmallUnitName("盒");
            po.setPackageLevel("三级包装");
            po.setFirstPackageNum(i + "");
            po.setFirstPackageUnit("袋");
            po.setSecondPackageNum("" + i);
            po.setSecondPackageUnit("包");
            po.setThirdPackageNum("" + i);
            po.setThirdPackageUnit("箱");
            po.setPackageRestricted("开放");
            po.setSweepCodeSwitch("是");
            po.setCostPrice("" + i);
            po.setBuyPrice("" + i);
            po.setWarnStoreNum("" + i);
            po.setWarnUnitName("包");
            list.add(po);
        }
        return list;
    }

}
