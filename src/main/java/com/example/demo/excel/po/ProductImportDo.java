package com.example.demo.excel.po;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品批量导入基础数据类
 *
 * @author SongNuoHui
 * @date 2021/10/21 10:49
 */
@Data
public class ProductImportDo implements Serializable {

    //有三级，大 中 小
    @ApiModelProperty(value = "产品类目编码")
    @ExcelProperty(value = "产品类目编码", index = 0)
    private String producCategory;

    @ApiModelProperty(value = "产品名称")
    @ExcelProperty(value = "产品名称", index = 1)
    private String productName;

    //产品编号
    @ApiModelProperty(value = "产品编码")
    @ExcelProperty(value = "产品编码", index = 2)
    private String productCode;

    @ApiModelProperty(value = "产品条码")
    @ExcelProperty(value = "产品条码", index = 3)
    private String productBarcode;

    //base_product_sort表
    @ApiModelProperty(value = "产品分类")
    @ExcelProperty(value = "产品分类", index = 4)
    private String productSortName;

    @ApiModelProperty(value = "产品规格")
    @ExcelProperty(value = "产品规格", index = 5)
    private String productSpecificationsName;

    //型号等级
    @ApiModelProperty(value = "产品型号")
    @ExcelProperty(value = "产品型号", index = 6)
    private String productModel;


    //保质期的类型 保质期 0 质保期 1
    @ApiModelProperty(value = "保质期/质保期")
    @ExcelProperty(value = "保质期/质保期", index = 7)
    private String shelfLifeLabel;

    @ApiModelProperty(value = "(质保)天数")
    @ExcelProperty(value = "(质保)天数", index = 8)
    private String shelfLifeValue;

    @ApiModelProperty(value = "自定义字段名称")
    @ExcelProperty(value = "自定义字段名称", index = 9)
    private String filedName;

    @ApiModelProperty(value = "自定义字段内容")
    @ExcelProperty(value = "自定义字段内容", index = 10)
    private String filedText;

    //getProductUnitCode获取产品单位
    @ApiModelProperty(value = "最小单位")
    @ExcelProperty(value = "最小单位", index = 11)
    private String smallUnitName;

    @ApiModelProperty(value = "包装层级，不填默认是单品")
    @ExcelProperty(value = "包装层级", index = 12)
    private String packageLevel;

    @ApiModelProperty(value = "一级包装数")
    @ExcelProperty(value = "一级包装数", index = 13)
    private String firstPackageNum;

    @ApiModelProperty(value = "一级包装单位")
    @ExcelProperty(value = "一级包装单位", index = 14)
    private String firstPackageUnit;

    @ApiModelProperty(value = "二级包装数")
    @ExcelProperty(value = "二级包装数", index = 15)
    private String secondPackageNum;

    @ApiModelProperty(value = "二级包装单位")
    @ExcelProperty(value = "二级包装单位", index = 16)
    private String secondPackageUnit;

    @ApiModelProperty(value = "三级包装数")
    @ExcelProperty(value = "三级包装数", index = 17)
    private String thirdPackageNum;

    @ApiModelProperty(value = "三级包装单位")
    @ExcelProperty(value = "三级包装单位", index = 18)
    private String thirdPackageUnit;

    @ApiModelProperty(value = "嵌套控制，是否包装限制不填写保持原限制逻辑")
    @ExcelProperty(value = "嵌套控制", index = 19)
    private String packageRestricted;

    @ApiModelProperty(value = "进销存是否扫码入出库，不填默认是否")
    @ExcelProperty(value = "进销存是否扫码入出库", index = 20)
    private String sweepCodeSwitch;

    @ApiModelProperty(value = "成本价(元)")
    @ExcelProperty(value = "成本价(元)", index = 21)
    private String costPrice;

    @ApiModelProperty(value = "进货价(元)")
    @ExcelProperty(value = "进货价(元)", index = 22)
    private String buyPrice;

    @ApiModelProperty(value = "最低库存预警数量")
    @ExcelProperty(value = "最低库存预警", index = 23)
    private String warnStoreNum;

    @ApiModelProperty(value = "最低库存预警单位")
    @ExcelProperty(value = "最低库存预警单位", index = 24)
    private String warnUnitName;

    @ApiModelProperty(value = "错误原因")
    @ExcelProperty(value = "错误原因", index = 25)
    private String errorMessage;
}
