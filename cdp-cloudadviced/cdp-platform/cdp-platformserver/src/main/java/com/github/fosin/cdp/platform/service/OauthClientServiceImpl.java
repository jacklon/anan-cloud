package com.github.fosin.cdp.platform.service;


import com.github.fosin.cdp.core.exception.CdpServiceException;
import com.github.fosin.cdp.jpa.repository.IJpaRepository;
import com.github.fosin.cdp.mvc.module.PageModule;
import com.github.fosin.cdp.mvc.result.Result;
import com.github.fosin.cdp.mvc.result.ResultUtils;
import com.github.fosin.cdp.platform.entity.OauthClientDetailsEntity;
import com.github.fosin.cdp.platform.repository.OauthClientRepository;
import com.github.fosin.cdp.platform.service.inter.IOauthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

/**
 * 2017/12/29.
 * Time:12:31
 *
 * @author fosin
 */
@Service
@Lazy
public class OauthClientServiceImpl implements IOauthClientService {

    @Autowired
    private OauthClientRepository oauthClientRepository;

    @Override
    public OauthClientDetailsEntity create(OauthClientDetailsEntity entity) throws CdpServiceException {
        Assert.notNull(entity, "传入了空对象!");
        String id = entity.getClientId();
        OauthClientDetailsEntity existsEntity = oauthClientRepository.findOne(id);
        Assert.isTrue(existsEntity == null, "该数据已存在，请重新设置客户端标识以区分");
        entity.setClientSecret(new BCryptPasswordEncoder().encode(entity.getClientSecret()));
        return oauthClientRepository.save(entity);
    }

    @Override
    public OauthClientDetailsEntity update(OauthClientDetailsEntity entity) throws CdpServiceException {
        Assert.notNull(entity, "传入了空对象!");
        String id = entity.getClientId();
        Assert.isTrue(StringUtils.hasText(id), "更新数据时ClientId不能为空!");
        OauthClientDetailsEntity existsEntity = oauthClientRepository.findOne(id);
        //如果密码与数据库中的不一致则需要加密
        if (!existsEntity.getClientSecret().equals(entity.getClientSecret())) {
            entity.setClientSecret(new BCryptPasswordEncoder().encode(entity.getClientSecret()));
        }
        return oauthClientRepository.save(entity);
    }

    @Override
    public Result findAllByPageSort(PageModule pageModule) {
        PageRequest pageable = new PageRequest(pageModule.getPageNumber() - 1, pageModule.getPageSize(), Sort.Direction.fromString(pageModule.getSortOrder()), pageModule.getSortName());
        String searchCondition = pageModule.getSearchText();

        Specification<OauthClientDetailsEntity> condition = new Specification<OauthClientDetailsEntity>() {
            @Override
            public Predicate toPredicate(Root<OauthClientDetailsEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> clientId = root.get("clientId");
                Path<String> clientSecret = root.get("clientSecret");

                if (StringUtils.isEmpty(searchCondition)) {
                    return query.getRestriction();
                }
                Predicate predicate = cb.or(cb.like(clientId, "%" + searchCondition + "%"), cb.like(clientSecret, "%" + searchCondition + "%"));
                return predicate;

            }
        };
        //分页查找
        Page<OauthClientDetailsEntity> page = oauthClientRepository.findAll(condition, pageable);

        return ResultUtils.success(page.getTotalElements(), page.getContent());
    }

    @Override
    public IJpaRepository<OauthClientDetailsEntity, String> getRepository() {
        return oauthClientRepository;
    }
}