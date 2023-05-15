package org.cloud.sonic.controller.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cloud.sonic.controller.models.base.TypeConverter;
import org.cloud.sonic.controller.models.domain.Projects;

import java.io.Serializable;
import java.util.Date;

@Schema(name = "项目DTO 模型")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectsDTO implements Serializable, TypeConverter<ProjectsDTO, Projects> {

    @Schema(description = "id", example = "1")
    Integer id;

    @NotBlank
    @Schema(description = "项目名称", required = true, example = "test")
    String projectName;

    @Schema(description = "项目描述", required = true, example = "Sonic项目描述")
    String projectDes;

    @Schema(description = "机器人类型", required = true, example = "1")
    Integer robotType;

    @Schema(description = "机器人token", required = true, example = "http://dingTalk.com?token=*****")
    String robotToken;

    @Schema(description = "机器人加签密钥", required = false, example = "qwe***")
    String robotSecret;

    @Schema(description = "项目图标", required = true, example = "http://img.jpg")
    String projectImg;

    @Schema(description = "最后修改日期", example = "2021-08-15 11:23:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date editTime;

    @Schema(description = "创建人", required = true, example = "liulijun")
    Integer createBy;
}
