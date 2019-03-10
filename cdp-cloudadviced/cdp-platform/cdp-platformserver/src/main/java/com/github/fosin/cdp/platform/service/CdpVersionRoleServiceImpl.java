package com.github.fosin.cdp.platform.service;

import com.github.fosin.cdp.mvc.module.PageModule;
import com.github.fosin.cdp.mvc.result.Result;
import com.github.fosin.cdp.mvc.result.ResultUtils;
import com.github.fosin.cdp.platform.dto.request.CdpVersionRoleCreateDto;
import com.github.fosin.cdp.platform.dto.request.CdpVersionRoleUpdateDto;
import com.github.fosin.cdp.platform.entity.CdpVersionRoleEntity;
import com.github.fosin.cdp.platform.repository.CdpVersionRoleRepository;
import com.github.fosin.cdp.platform.service.inter.ICdpVersionRoleService;
import com.github.fosin.cdp.platformapi.constant.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.Path;

/**
 * 系统版本角色表(cdp_version_role)表服务实现类
 *
 * @author fosin
 * @date 2018-11-18 17:26:40
 */
@Service
@Lazy
public class CdpVersionRoleServiceImpl implements ICdpVersionRoleService {
    @Autowired
    private CdpVersionRoleRepository cdpSysVersionRoleRepository;

    /**
     * 获取DAO
     */
    @Override
    public CdpVersionRoleRepository getRepository() {
        return cdpSysVersionRoleRepository;
    }

    /**
     * 通过实体类创建新数据
     *
     * @param entity 系统版本角色表 实体对象
     * @return entity 实例对象
     */
    @Override
    public CdpVersionRoleEntity create(CdpVersionRoleCreateDto entity) {
        Assert.notNull(entity, "创建数据的实体对象不能为空!");
        Assert.isTrue(!entity.getValue().equals(SystemConstant.SUPER_USER_CODE), "角色标识不能为:" + SystemConstant.SUPER_USER_CODE);
        CdpVersionRoleEntity createEntity = new CdpVersionRoleEntity();
        BeanUtils.copyProperties(entity, createEntity);
        return getRepository().save(createEntity);
    }

    /**
     * 修改数据
     *
     * @param entity 系统版本角色表 实体对象
     * @return entity 实例对象
     */
    @Override
    public CdpVersionRoleEntity update(CdpVersionRoleUpdateDto entity) {
        Assert.notNull(entity, "更新数据的实体对象不能为空!");
        Long id = entity.getId();
        Assert.isTrue(id != null && id > 0, "传入的主键无效!");
        Assert.isTrue(!entity.getValue().equals(SystemConstant.SUPER_USER_CODE), "角色标识不能为:" + SystemConstant.SUPER_USER_CODE);
        CdpVersionRoleEntity createEntity = getRepository().findById(id).orElse(null);
        Assert.notNull(createEntity, "更新数据的实体对象不能为空!");
        BeanUtils.copyProperties(entity, createEntity);
        return getRepository().save(createEntity);
    }

    /**
     * 根据查询条件查询分页排序数据集
     *
     * @param pageModule 分页排序条件
     * @return Result结果集
     */
    @Override
    public Result findAllByPageSort(PageModule pageModule) {
        PageRequest pageable = PageRequest.of(pageModule.getPageNumber() - 1, pageModule.getPageSize(), Sort.Direction.fromString(pageModule.getSortOrder()), pageModule.getSortName());
        String searchCondition = pageModule.getSearchText();
        Specification<CdpVersionRoleEntity> condition = (root, query, cb) -> {
            Path<String> roleName = root.get("name");
            Path<String> roleValue = root.get("value");
            if (StringUtils.isBlank(searchCondition)) {
                return query.getRestriction();
            }
            return cb.or(cb.like(roleName, "%" + searchCondition + "%"), cb.like(roleValue, "%" + searchCondition + "%"));
        };

        //分页查找
        Page<CdpVersionRoleEntity> page = getRepository().findAll(condition, pageable);

        return ResultUtils.success(page.getTotalElements(), page.getContent());
    }
}