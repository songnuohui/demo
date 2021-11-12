package com.example.demo.excel.po;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author SongNuoHui
 * @date 2021/10/21 16:42
 */
@Data
public class ProductBatchDo {

    @ApiModelProperty(value = "批次名称")
    @ExcelProperty(value = "批次名称", index = 0)
    private String batchName;

    @ApiModelProperty(value = "所属产品编号")
    @ExcelProperty(value = "所属产品编号", index = 1)
    private String productCode;

    @ApiModelProperty(value = "上市时间")
    @ExcelProperty(value = "上市时间", index = 2)
    @DateTimeFormat("yyyy-MM-dd")
    private Date marketDate;

    @ApiModelProperty(value = "生产时间")
    @ExcelProperty(value = "生产时间", index = 3)
    @DateTimeFormat("yyyy-MM-dd")
    private Date produceDate;

    @ApiModelProperty(value = "错误原因")
    @ExcelProperty(value = "错误原因", index = 4)
    private String errorMessage;

}
