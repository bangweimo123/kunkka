package com.leshiguang.arch.kunkka.web.service.impl;

import com.leshiguang.arch.kunkka.client.configure.IConfigureClient;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.ConfigureClientFactory;
import com.leshiguang.arch.kunkka.common.entity.category.CategoryBO;
import com.leshiguang.arch.kunkka.common.enums.RedisKeyType;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import com.leshiguang.arch.kunkka.db.entity.gen.Category;
import com.leshiguang.arch.kunkka.db.entity.gen.CategoryCondition;
import com.leshiguang.arch.kunkka.db.entity.gen.ClusterConnectMapping;
import com.leshiguang.arch.kunkka.db.entity.gen.ClusterConnectMappingCondition;
import com.leshiguang.arch.kunkka.db.mapper.gen.CategoryMapper;
import com.leshiguang.arch.kunkka.db.mapper.gen.ClusterConnectMappingMapper;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryQueryReq;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryVO;
import com.leshiguang.arch.kunkka.web.exception.ServerErrorCode;
import com.leshiguang.arch.kunkka.web.operate.log.OperateLogBuilder;
import com.leshiguang.arch.kunkka.web.service.CategoryService;
import com.leshiguang.arch.kunkka.web.service.ClusterService;
import com.leshiguang.arch.kunkka.web.service.enums.StatusEnum;
import com.leshiguang.arch.kunkka.web.utils.Transfer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:30
 * @Modify
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ClusterConnectMappingMapper clusterConnectMappingMapper;
    @Resource
    private ConfigureClientFactory kunkkaWebConfigureClientFactory;
    @Resource
    private ClusterService clusterService;
    private Transfer.CategoryTransfer TRS = new Transfer.CategoryTransfer() {
        @Override
        public CategoryVO BO2VO(CategoryBO core) {
            CategoryVO target = new CategoryVO();
            target.setCategory(core.getCategory());
            target.setClusterName(core.getClusterName());
            target.setIndexTemplate(core.getIndexTemplate());
            target.setHot(core.isHot());
            target.setDuration(core.getDuration());
            target.setCType(core.getCType());
            target.setMultiTenant(core.isMultiTenant());
            return target;
        }

        @Override
        public Category BO2PO(CategoryBO core) {
            Category target = new Category();
            target.setCategory(core.getCategory());
            target.setClusterName(core.getClusterName());
            target.setIndexTemplate(core.getIndexTemplate());
            target.setHot(core.isHot() ? 1 : 0);
            target.setDuration(core.getDuration());
            target.setcType(core.getCType());
            target.setMultiTenant(core.isMultiTenant() ? 1 : 0);
            return target;
        }

        @Override
        public CategoryVO PO2VO(Category core) {
            CategoryVO target = new CategoryVO();
            target.setCategory(core.getCategory());
            target.setClusterName(core.getClusterName());
            target.setIndexTemplate(core.getIndexTemplate());
            target.setHot(core.getHot() > 0);
            target.setDuration(core.getDuration());
            target.setCType(core.getcType());
            target.setMultiTenant(core.getMultiTenant() > 0);
            target.setCreateTime(core.getCreateTime());
            target.setUpdateTime(core.getUpdateTime());
            target.setCategoryId(core.getId());
            target.setStatus(core.getcStatus());
            return target;
        }

        @Override
        public Category VO2PO(CategoryVO core) {
            Category target = new Category();
            target.setCategory(core.getCategory());
            target.setClusterName(core.getClusterName());
            target.setIndexTemplate(core.getIndexTemplate());
            target.setHot(core.isHot() ? 1 : 0);
            target.setDuration(core.getDuration());
            target.setcType(core.getCType());
            target.setMultiTenant(core.isMultiTenant() ? 1 : 0);
            target.setId(core.getCategoryId());
            return target;
        }

        @Override
        public CategoryBO PO2BO(Category core) {
            CategoryBO target = new CategoryBO();
            target.setCategory(core.getCategory());
            target.setClusterName(core.getClusterName());
            target.setIndexTemplate(core.getIndexTemplate());
            target.setHot(core.getHot() > 0);
            target.setDuration(core.getDuration());
            target.setCType(core.getcType());
            target.setMultiTenant(core.getMultiTenant() > 0);
            return target;
        }

        @Override
        public List<CategoryVO> BO2VO(List<CategoryBO> cores) {
            List<CategoryVO> targetList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(cores)) {
                for (CategoryBO core : cores) {
                    CategoryVO target = this.BO2VO(core);
                    targetList.add(target);
                }
            }
            return targetList;
        }

        @Override
        public List<CategoryVO> PO2VO(List<Category> cores) {
            List<CategoryVO> targetList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(cores)) {
                for (Category core : cores) {
                    CategoryVO target = this.PO2VO(core);
                    targetList.add(target);
                }
            }
            return targetList;
        }
    };

    @Override
    public Page<CategoryVO> query(CategoryQueryReq queryRequest, PageRequest page) throws KunkkaException {
        CategoryCondition condition = new CategoryCondition();
        condition.setMysqlOffset(Long.valueOf(page.getOffset()).intValue());
        condition.setMysqlLength(page.getPageSize());
        buildCriteria(condition.createCriteria(), queryRequest);
        long counts = categoryMapper.countByCondition(condition);
        Page<CategoryVO> result = null;
        if (counts > 0) {
            List<Category> categoryList = categoryMapper.selectByCondition(condition);
            List<CategoryVO> resultList = TRS.PO2VO(categoryList);
            result = new PageImpl<CategoryVO>(resultList, page, counts);
        } else {
            result = new PageImpl<CategoryVO>(new ArrayList<>(), page, counts);
        }
        return result;
    }

    @Override
    public Boolean save(CategoryVO category, String operator) throws KunkkaException {
        Boolean result = false;
        if (StringUtils.isBlank(category.getCategory())) {
            throw new KunkkaException(ServerErrorCode.PARAM_CHECK_ERROR, "category", category.getCategory());
        }
        if (StringUtils.isBlank(category.getClusterName())) {
            throw new KunkkaException(ServerErrorCode.PARAM_CHECK_ERROR, "clusterName", category.getClusterName());
        }
        if (StringUtils.isBlank(category.getCType()) || RedisKeyType.parse(category.getCType()) == null) {
            throw new KunkkaException(ServerErrorCode.PARAM_CHECK_ERROR, "cType", category.getCType());

        }
        if (null == category.getCategoryId()) {
            CategoryCondition condition = new CategoryCondition();
            condition.createCriteria().andClusterNameEqualTo(category.getClusterName()).andCategoryEqualTo(category.getCategory());
            long existCount = categoryMapper.countByCondition(condition);
            if (existCount > 0) {
                throw new KunkkaException(ServerErrorCode.CATEGORY_HAS_EXIST);
            }
            Category operationCategory = TRS.VO2PO(category);
            operationCategory.setcStatus(StatusEnum.CREATED.getCode());
            int insertCount = categoryMapper.insertSelective(operationCategory);
            OperateLogBuilder.createOpt().of(OperateLogBuilder.RelationType.CATEGORY, operationCategory.getId()).with(operator).content("新建").log();
            result = insertCount > 0;
        } else {
            Category existCategory = categoryMapper.selectById(category.getCategoryId());
            Category operationCategory = TRS.VO2PO(category);
            operationCategory.setUpdateTime(new Date());
            operationCategory.setCreateTime(existCategory.getCreateTime());
            operationCategory.setId(existCategory.getId());
            int updateCount = categoryMapper.updateByIdSelective(operationCategory);
            OperateLogBuilder.modifyOpt().of(OperateLogBuilder.RelationType.CATEGORY, category.getCategoryId()).with(operator).content("修改").log();
            result = updateCount > 0;
        }
        return result;
    }

    @Override
    public CategoryVO load(String clusterName, String category, String operator) throws KunkkaException {
        if (StringUtils.isBlank(category)) {
            throw new KunkkaException(ServerErrorCode.PARAM_CHECK_ERROR, "category", category);
        }
        if (StringUtils.isBlank(clusterName)) {
            throw new KunkkaException(ServerErrorCode.PARAM_CHECK_ERROR, "clusterName", clusterName);
        }
        if (StringUtils.isBlank(operator)) {
            operator = "bangwei.mo";
        }
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(category);
        Category existCategory = categoryMapper.selectOneByCondition(condition);
        if (null != existCategory) {
            CategoryVO categoryVO = TRS.PO2VO(existCategory);
            categoryVO.setRegionList(clusterService.loadRegions(clusterName));
            Boolean hasPrivilege = clusterService.hasPrivilege(clusterName, operator);
            categoryVO.setHasPrivilege(hasPrivilege);
            return categoryVO;
        } else {
            throw new KunkkaException(ServerErrorCode.CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public Boolean delete(Integer categoryId, String operator) throws KunkkaException {
        Boolean deleteStatus = changeStatus(categoryId, StatusEnum.DELETED.getCode(), Arrays.asList(StatusEnum.CREATED.getCode(), StatusEnum.READY.getCode()));
        OperateLogBuilder.deleteOpt().of(OperateLogBuilder.RelationType.CATEGORY, categoryId).with(operator).content("删除").log();
        return deleteStatus;
    }

    @Override
    public Boolean hardDelete(Integer categoryId, String operator) throws KunkkaException {
        Integer deleteCount = categoryMapper.deleteById(categoryId);
        return deleteCount > 0;
    }

    @Override
    public Boolean reset(Integer categoryId, String operator) throws KunkkaException {
        Boolean resetStatus = changeStatus(categoryId, StatusEnum.CREATED.getCode(), Arrays.asList(StatusEnum.DELETED.getCode()));
        OperateLogBuilder.resetOpt().of(OperateLogBuilder.RelationType.CATEGORY, categoryId).with(operator).content("重置").log();
        return resetStatus;
    }

    @Override
    public Boolean publish(Integer categoryId, String operator) throws KunkkaException {
        Category category = categoryMapper.selectById(categoryId);
        if (null == category) {
            throw new KunkkaException(ServerErrorCode.CATEGORY_NOT_EXISTS);
        }
        CategoryBO toZkCategory = TRS.PO2BO(category);
        Set<String> regionList = getRegions(category);
        for (String region : regionList) {
            IConfigureClient zookeeperClient = kunkkaWebConfigureClientFactory.getInstance(region);
            boolean publishCategoryResult = zookeeperClient.setCategory(toZkCategory.getClusterName(), toZkCategory);
            if (!publishCategoryResult) {
                throw new KunkkaException(ServerErrorCode.CATEGORY_PUBLISH_ERROR);
            }
        }
        Boolean publishStatus = changeStatus(categoryId, StatusEnum.ONLINE.getCode(), Arrays.asList(StatusEnum.CREATED.getCode(), StatusEnum.READY.getCode()));
        OperateLogBuilder.onlineOpt().of(OperateLogBuilder.RelationType.CATEGORY, categoryId).with(operator).content("发布").log();
        return publishStatus;
    }

    @Override
    public Boolean publish(String clusterName, String categoryName, String region, String operator) throws KunkkaException {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(categoryName).andCStatusEqualTo(StatusEnum.ONLINE.getCode());
        Category category = categoryMapper.selectOneByCondition(condition);
        if (null == category) {
            throw new KunkkaException(ServerErrorCode.CATEGORY_NOT_EXISTS);
        }
        CategoryBO toZkCategory = TRS.PO2BO(category);
        IConfigureClient zookeeperClient = kunkkaWebConfigureClientFactory.getInstance(region);
        boolean publishCategoryResult = zookeeperClient.setCategory(toZkCategory.getClusterName(), toZkCategory);
        OperateLogBuilder.onlineOpt().of(OperateLogBuilder.RelationType.CATEGORY, category.getId()).with(operator).content("发布特定区域:" + region).log();
        return publishCategoryResult;
    }

    @Override
    public Boolean offline(Integer categoryId, String operator) throws KunkkaException {
        Category category = categoryMapper.selectById(categoryId);
        if (null == category) {
            throw new KunkkaException(ServerErrorCode.CATEGORY_NOT_EXISTS);
        }
        Set<String> regionList = getRegions(category);
        for (String region : regionList) {
            IConfigureClient zookeeperClient = kunkkaWebConfigureClientFactory.getInstance(region);
            boolean deleteStatus = zookeeperClient.deleteCategory(category.getClusterName(), category.getCategory());
            if (!deleteStatus) {
                throw new KunkkaException(ServerErrorCode.CATEGORY_OFFLINE_ERROR);
            }
        }
        Boolean offlineStatus = changeStatus(categoryId, StatusEnum.READY.getCode(), Arrays.asList(StatusEnum.ONLINE.getCode()));
        OperateLogBuilder.offlineOpt().of(OperateLogBuilder.RelationType.CATEGORY, categoryId).with(operator).content("下线").log();
        return offlineStatus;


    }

    @Override
    public List<String> loadOnlineCategorys(String clusterName) {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCStatusEqualTo(StatusEnum.ONLINE.getCode());
        List<Category> onlineCategoryList = categoryMapper.selectByCondition(condition);
        List<String> onlineCategorys = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(onlineCategoryList)) {
            onlineCategoryList.forEach(categoryItem -> {
                onlineCategorys.add(categoryItem.getCategory());
            });
        }
        return onlineCategorys;
    }

    private Set<String> getRegions(Category category) {
        ClusterConnectMappingCondition mappingCondition = new ClusterConnectMappingCondition();
        mappingCondition.createCriteria().andClusterNameEqualTo(category.getClusterName());
        List<ClusterConnectMapping> connectMappingList = clusterConnectMappingMapper.selectByCondition(mappingCondition);
        Set<String> regionList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(connectMappingList)) {
            for (ClusterConnectMapping clusterConnectMapping : connectMappingList) {
                regionList.add(clusterConnectMapping.getRegion());
            }
        }
        return regionList;
    }

    private boolean changeStatus(Integer categoryId, Integer status, List<Integer> preValidStatus) {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andIdEqualTo(categoryId).andCStatusIn(preValidStatus);
        Category statusEntity = new Category();
        statusEntity.setcStatus(status);
        statusEntity.setUpdateTime(new Date());
        int updateCount = categoryMapper.updateByConditionSelective(statusEntity, condition);
        return updateCount > 0;
    }

    private void buildCriteria(CategoryCondition.Criteria criteria, CategoryQueryReq request) {
        if (StringUtils.isNotEmpty(request.getKeyword())) {
            criteria.andCategoryLikeInsensitive("%" + request.getKeyword() + "%");
        }
        if (StringUtils.isNotEmpty(request.getClusterName())) {
            criteria.andClusterNameEqualTo(request.getClusterName());
        }
        if (CollectionUtils.isNotEmpty(request.getStatusList())) {
            criteria.andCStatusIn(request.getStatusList());
        }
        if (null != request.getShowHotKey()) {
            criteria.andHotEqualTo(1);
        }
    }
}
