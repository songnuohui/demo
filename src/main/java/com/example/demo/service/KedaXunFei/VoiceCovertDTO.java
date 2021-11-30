package com.example.demo.service.KedaXunFei;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SongNuoHui
 * @date 2021/11/22 17:38
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoiceCovertDTO {
    @ApiModelProperty(value = "实时语音转写错误码，0:成功")
    private String code;
    @ApiModelProperty(value = "实时语音转写后的数据")
    private String data;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "信息说明")
    private String message;
}
