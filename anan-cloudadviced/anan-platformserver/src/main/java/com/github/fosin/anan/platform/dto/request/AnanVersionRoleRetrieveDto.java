package com.github.fosin.anan.platform.dto.request;

import com.github.fosin.anan.util.DateTimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统版本角色表(AnanVersionRole)查询DTO
 *
 * @author fosin
 * @date 2019-02-19 18:14:31
 * @since 1.0.0
 */
@Data
@ApiModel(value = "系统版本角色表查询DTO", description = "表(anan_version_role)的对应的查询DTO")
public class AnanVersionRoleRetrieveDto implements Serializable {
    private static final long serialVersionUID = -49705835224748973L;

    @ApiModelProperty(value = "角色ID, 主键")
    private Long id;

    @ApiModelProperty(value = "版本ID")
    private Long versionId;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色标识")
    private String value;

    @ApiModelProperty(value = "角色说明")
    private String tips;

    @ApiModelProperty(value = "使用状态：0=启用，1=禁用，具体取值于字典表anan_dictionary.code=11")
    private Integer status;

    @DateTimeFormat(pattern = DateTimeUtil.DATETIME_PATTERN)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人，该值由后台维护，更改数据时前端不需要关心，取值于anan_user.id")
    private Long createBy;

    @DateTimeFormat(pattern = DateTimeUtil.DATETIME_PATTERN)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人，该值由后台维护，更改数据时前端不需要关心，取值于anan_user.id")
    private Long updateBy;

}
