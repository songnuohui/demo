package com.example.demo.excel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.demo.excel.po.Bu;
import com.example.demo.excel.po.ProductBatchDo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.excel.file.FileUtil.makeFile;

/**
 * @author SongNuoHui
 * @date 2021/10/21 16:51
 */
@Slf4j
public class ProductBatchWrite {

    public static void main(String[] args) {
        ExcelWriter build = null;
        WriteSheet sheet = null;
        try {
            String fileName = "a.xlsx";
            log.info("开始写入");
            ProductBatchWrite exportTest = new ProductBatchWrite();
            Bu bu = exportTest.buildErr(fileName);
            build = bu.getBuild();
            sheet = bu.getSheet();
            for (int i = 0; i < 5; i++) {
                build.write(buildList(), sheet);
            }
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
        ExcelWriter build = EasyExcel.write(filePath, ProductBatchDo.class).build();
        WriteSheet sheet = EasyExcel.writerSheet("错误数据").build();
        Bu bu = new Bu();
        bu.setBuild(build);
        bu.setSheet(sheet);
        return bu;
    }

    public static List<ProductBatchDo> buildList() {
        List<ProductBatchDo> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            ProductBatchDo po = new ProductBatchDo();
            po.setBatchName("name" + i);
            po.setProductCode("code" + i);
            po.setMarketDate(new Date());
            po.setProduceDate(new Date());
            po.setErrorMessage("错误原因" + i);
            list.add(po);
        }
        return list;
    }


    /////////


    public class EasyExcelUtils {
        private final Logger logger = LoggerFactory.getLogger(EasyExcelUtils.class);

        /**
         * @param fileName  文件路径名
         * @param sheetName sheet名
         * @param data      查询出来的数据
         * @param headList  传入的Excel头(例如：姓名，生日)
         * @param fileList  传入需要展示的字段(例如：姓名对应字段是name,生日对应字段是birthday)
         */

        public void noModelWrite(String fileName, String sheetName, List data, List headList, List fileList) {
            EasyExcel.write(fileName).head(head(headList)).sheet(sheetName).doWrite(dataList(data, fileList));

        }

        /**
         * 设置Excel头
         *
         * @param headList Excel头信息
         * @return
         */

        private List<List> head(List<String> headList) {
            List<List> list = new ArrayList<>();

            for (String value : headList) {
                List head = new ArrayList<>();

                head.add(value);

                list.add(head);

            }

            return list;

        }

        /**
         * 设置表格信息
         *
         * @param dataList 查询出的数据
         * @param fileList 需要显示的字段
         * @return
         */

        private List<List> dataList(List dataList, List<String> fileList) {
            List<List> list = new ArrayList<>();

            for (Object person : dataList) {
                List data = new ArrayList<>();

                for (String fieldName : fileList) {
                /**通过反射根据需要显示的字段，获取对应的属性值*/
                    data.add(getFieldValue(fieldName, person));

                }

                list.add(data);

            }

            return list;

        }

        /**
         * 根据传入的字段获取对应的get方法，如name,对应的getName方法
         *
         * @param fieldName 字段名
         * @param person    对象
         * @return
         */

        private Object getFieldValue(String fieldName, Object person) {
            try {
                String firstLetter = fieldName.substring(0, 1).toUpperCase();

                String getter = "get" + firstLetter + fieldName.substring(1);

                Method method = person.getClass().getMethod(getter);

                return method.invoke(person);

            } catch (Exception e) {
                e.printStackTrace();
                return null;

            }

        }

    }


   /* @RequestMapping(value = "/auth/downloadList",method = {RequestMethod.GET, RequestMethod.POST})

    public void downloadList(String filedNames,String filedCodes){
        List list = personServiceImpl.findPersonAll();

*//**Excel头，参数格式：姓名,生日*//*

        String[] head = filedNames.split(",");

        List headList = new ArrayList<>(Arrays.asList(head));

*//**Excel表格内容，参数格式：name,birthday*//*

        String[] file = filedCodes.split(",");

        List fileList = new ArrayList<>(Arrays.asList(file));

        EasyExcelUtils.noModelWrite("E:/data/测试例子.xls","测试例子",list,headList,fileList);

    }*/
}
