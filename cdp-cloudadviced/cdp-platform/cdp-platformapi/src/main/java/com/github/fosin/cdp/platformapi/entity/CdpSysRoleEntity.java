package com.github.fosin.cdp.platformapi.entity;

import com.github.fosin.cdp.jpa.entity.AbstractOrganizIdJpaEntity;
import com.github.fosin.cdp.util.RegexUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 系统角色表(CdpSysRole)实体类
 *
 * @author fosin
 * @date 2018-10-27 09:38:39
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicUpdate
@Table(name = "cdp_sys_role")
@ApiModel(value = "系统角色表实体类", description = "表(cdp_sys_role)的对应的实体类")
public class CdpSysRoleEntity extends AbstractOrganizIdJpaEntity implements Serializable {
    private static final long serialVersionUID = 771122304639044684L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "角色ID", notes = "主键，系统自动生成,角色ID")
    private Long id;

    @Column(name = "name")
    @Basic
    @NotBlank
    @ApiModelProperty(value = "角色名称", notes = "角色名称")
    @Pattern(regexp = RegexUtil.SPECIAL, message = "名称不能包含特殊字符")
    private String name;

    @Column(name = "value")
    @Basic
    @Pattern(regexp = "[\\w]{1,40}", message = "角色标识只能大小写字母、数字、下杠(_)组合而成,长度不超过40位")
    @ApiModelProperty(value = "角色标识", notes = "角色标识")

    private String value;

    @Column(name = "tips")
    @Basic
    @ApiModelProperty(value = "角色说明", notes = "角色说明")
    private String tips;

    @Column(name = "status")
    @Basic
    @NotNull
    @Range(max = 1)
    @ApiModelProperty(value = "使用状态：0=启用，1=禁用，具体取值于字典表cdp_sys_dictionary.code=11", notes = "使用状态：0=启用，1=禁用，具体取值于字典表cdp_sys_dictionary.code=11")
    private Integer status;

    @Column(name = "built_in")
    @Basic
    @NotNull
    @Range(max = 1)
    @ApiModelProperty(value = "内置标志：是否是系统内置角色，内置角色不能被用户删除和修改，0=不是 1=是")
    private Integer builtIn;

}