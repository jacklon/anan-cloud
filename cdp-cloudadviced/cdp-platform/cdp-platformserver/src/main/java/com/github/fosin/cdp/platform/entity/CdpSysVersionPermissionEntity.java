package com.github.fosin.cdp.platform.entity;

import java.util.Date;

import com.github.fosin.cdp.jpa.entity.AbstractCreateJpaEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.github.fosin.cdp.util.DateTimeUtil;

/**
 * 系统版本权限表(CdpSysVersionPermission)实体类
 *
 * @author fosin
 * @date 2018-11-18 17:28:24
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicUpdate
@Table(name = "cdp_sys_version_permission")
@ApiModel(value = "系统版本权限表实体类", description = "表(cdp_sys_version_permission)的对应的实体类")
public class CdpSysVersionPermissionEntity extends AbstractCreateJpaEntity implements Serializable {
    private static final long serialVersionUID = 154442175110999677L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "主键，系统自动生成,版本权限ID")
    private Long id;

    @Basic
    @NotNull
    @Column(name = "version_id")
    @ApiModelProperty(value = "版本ID")
    private Long versionId;

    @Basic
    @NotNull
    @Column(name = "permission_id")
    @ApiModelProperty(value = "权限ID")
    private Long permissionId;
}