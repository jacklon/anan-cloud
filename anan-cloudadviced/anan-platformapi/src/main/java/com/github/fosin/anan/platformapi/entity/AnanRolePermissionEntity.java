package com.github.fosin.anan.platformapi.entity;

import com.github.fosin.anan.jpa.entity.AbstractCreateJpaEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 系统角色权限表(AnanRolePermission)实体类
 *
 * @author fosin
 * @date 2019-01-27 19:35:04
 * @since 1.0.0
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@Table(name = "anan_role_permission")
@ApiModel(value = "系统角色权限表实体类", description = "表(anan_role_permission)的对应的实体类")
public class AnanRolePermissionEntity extends AbstractCreateJpaEntity<Long, Long> implements Serializable {
    private static final long serialVersionUID = 646285760537969078L;

    @Basic
    @ApiModelProperty(value = "角色ID", required = true)
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Basic
    @ApiModelProperty(value = "权限ID", required = true)
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

}
