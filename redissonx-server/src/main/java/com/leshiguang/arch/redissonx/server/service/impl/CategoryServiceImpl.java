package com.leshiguang.arch.redissonx.server.service.impl;

import com.leshiguang.arch.redissonx.core.entity.gen.Category;
import com.leshiguang.arch.redissonx.core.entity.gen.CategoryCondition;
import com.leshiguang.arch.redissonx.core.mapper.gen.CategoryMapper;
import com.leshiguang.arch.redissonx.server.domain.category.CategoryVO;
import com.leshiguang.arch.redissonx.server.service.CategoryService;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import com.leshiguang.redissonx.common.base.RedissonxTable;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.entity.category.HotKeyStrategyBO;
import com.leshiguang.redissonx.common.entity.request.CategoryQueryRequest;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.RedissonxConfigLoader;
import org.redisson.config.ZookeeperRedissonxConfigLoader;
import org.redisson.Redissonx;
import org.redisson.RedissonxClient;
import org.redisson.api.RKeys;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 20:30
 * @Modify
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private ZookeeperClient zookeeperClient = new ZookeeperClientImpl();
    @Resource
    private CategoryMapper categoryMapper;
    private RedissonxConfigLoader configLoader = new ZookeeperRedissonxConfigLoader();

    private Map<String, RedissonxClient> redissonxClientMap = new ConcurrentHashMap<String, RedissonxClient>();

    public RedissonxClient getClientByClusterName(String clusterName) {
        if (!redissonxClientMap.containsKey(clusterName)) {
            synchronized (this) {
                if (!redissonxClientMap.containsKey(clusterName)) {
                    Config config = configLoader.getByCluster(clusterName);
                    RedissonxClient redissonxClient = Redissonx.create(clusterName, config);
                    redissonxClientMap.put(clusterName, redissonxClient);
                }
            }
        }
        return redissonxClientMap.get(clusterName);
    }

    @Override
    public RedissonxResponse<RedissonxTable<CategoryVO>> query(String clusterName, CategoryQueryRequest queryRequest, RedissonxPaging paging) {
        CategoryCondition condition = new CategoryCondition();
        condition.setMysqlOffset((paging.getPageIndex() - 1) * paging.getPageSize());
        condition.setMysqlLength(paging.getPageSize());
        buildCriteria(condition.createCriteria(), queryRequest);
        List<Category> categoryList = categoryMapper.selectByCondition(condition);
        long counts = categoryMapper.countByCondition(condition);
        List<CategoryVO> resultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categoryList)) {
            for (Category category : categoryList) {
                CategoryVO categoryVO = toVO(category);
                resultList.add(categoryVO);
            }
        }
        RedissonxTable<CategoryVO> result = new RedissonxTable<>(paging.getPageIndex(), paging.getPageSize(), resultList, counts);
        return RedissonxResponseBuilder.success(result);
    }

    private void buildCriteria(CategoryCondition.Criteria criteria, CategoryQueryRequest request) {
        if (StringUtils.isNotEmpty(request.getUserId())) {
            criteria.andCreatorEqualTo(request.getUserId());
        }
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
            criteria.andIshotEqualTo(1);
        }
    }

    private CategoryVO toVO(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setClusterName(category.getClusterName());
        categoryVO.setCategory(category.getCategory());
        categoryVO.setVersion(category.getVersion());
        categoryVO.setDuration(category.getDuration());
        categoryVO.setHot(category.getIshot() > 0);
        categoryVO.setIndexTemplate(category.getIndexTemplate());
        categoryVO.setCreator(category.getCreator());
        categoryVO.setOperator(category.getOperator());
        categoryVO.setCreateTime(category.getCreateTime());
        categoryVO.setUpdateTime(category.getUpdateTime());
        categoryVO.setStatus(category.getcStatus());
        return categoryVO;
    }

    private CategoryBO toBO(Category category) {
        CategoryBO categoryBO = new CategoryBO();
        categoryBO.setClusterName(category.getClusterName());
        categoryBO.setCategory(category.getCategory());
        categoryBO.setVersion(category.getVersion());
        categoryBO.setDuration(category.getDuration());
        categoryBO.setHot(category.getIshot() > 0);
        categoryBO.setIndexTemplate(category.getIndexTemplate());
        return categoryBO;
    }

    private Category toCategory(CategoryBO categoryBO) {
        Category category = new Category();
        category.setClusterName(categoryBO.getClusterName());
        category.setCategory(categoryBO.getCategory());
        category.setVersion(categoryBO.getVersion());
        category.setDuration(categoryBO.getDuration());
        category.setIshot(categoryBO.isHot() ? 1 : 0);
        category.setIndexTemplate(categoryBO.getIndexTemplate());
        return category;
    }

    @Override
    public RedissonxResponse<Boolean> save(String clusterName, CategoryBO category, String operator) {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(category.getCategory());
        Category existCategory = categoryMapper.selectOneByCondition(condition);
        Boolean result = false;
        Category operationCategory = toCategory(category);
        if (null == existCategory) {
            operationCategory.setCreator(operator);
            operationCategory.setOperator(operator);
            operationCategory.setCreateTime(new Date());
            operationCategory.setUpdateTime(new Date());
            operationCategory.setcStatus(1);
            int insertCount = categoryMapper.insertSelective(operationCategory);
            result = insertCount > 0;
        } else {
            operationCategory.setOperator(operator);
            operationCategory.setUpdateTime(new Date());
            operationCategory.setCreator(existCategory.getCreator());
            operationCategory.setCreateTime(existCategory.getCreateTime());
            operationCategory.setId(existCategory.getId());
            int updateCount = categoryMapper.updateByIdSelective(operationCategory);
            result = updateCount > 0;
        }
        return RedissonxResponseBuilder.success(result);
    }

    @Override
    public RedissonxResponse<CategoryVO> get(String clusterName, String category) {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(category);
        Category existCategory = categoryMapper.selectOneByCondition(condition);
        if (null != existCategory) {
            CategoryVO categoryVO = toVO(existCategory);
            return RedissonxResponseBuilder.success(categoryVO);
        } else {
            return RedissonxResponseBuilder.fail(405, "category not exist in db!");
        }
    }

    @Override
    public RedissonxResponse<Boolean> delete(String clusterName, String category, String operator) {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(category);
        Category statusCategory = new Category();
        statusCategory.setcStatus(4);
        statusCategory.setUpdateTime(new Date());
        statusCategory.setOperator(operator);
        statusCategory.setDeleteTime(new Date());
        int updateCount = categoryMapper.updateByConditionSelective(statusCategory, condition);
        boolean updateStatus = updateCount > 0;
        return RedissonxResponseBuilder.success(updateStatus);
    }

    @Override
    public RedissonxResponse<Boolean> publish(String clusterName, String category, String version, String operator) {
        Boolean result = false;
        try {
            //同步到zk
            //修改状态
            CategoryCondition condition = new CategoryCondition();
            condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(category);
            Category statusCategory = new Category();
            statusCategory.setcStatus(2);
            statusCategory.setUpdateTime(new Date());
            statusCategory.setOperator(operator);
            statusCategory.setVersion(version);
            int updateCount = categoryMapper.updateByConditionSelective(statusCategory, condition);
            boolean updateStatus = updateCount > 0;
            if (updateStatus) {
                Category newestCategory = categoryMapper.selectOneByCondition(condition);
                CategoryBO toZkCategory = toBO(newestCategory);
                boolean setZkStatus = zookeeperClient.setCategory(clusterName, toZkCategory);
                if (!setZkStatus) {
                    //rollback
                    result = false;
                }
                result = setZkStatus;
            } else {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("exception for publish category", e);
            result = false;
        }
        return RedissonxResponseBuilder.success(result);
    }

    @Override
    public RedissonxResponse<Boolean> offline(String clusterName, String category, String operator) {
        Boolean result = false;
        try {
            //同步到zk
            //修改状态
            CategoryCondition condition = new CategoryCondition();
            condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(category);
            Category statusCategory = new Category();
            statusCategory.setcStatus(3);
            statusCategory.setUpdateTime(new Date());
            statusCategory.setOperator(operator);
            statusCategory.setVersion("none");
            int updateCount = categoryMapper.updateByConditionSelective(statusCategory, condition);
            boolean updateStatus = updateCount > 0;
            if (updateStatus) {
                Category newestCategory = categoryMapper.selectOneByCondition(condition);
                CategoryBO toZkCategory = toBO(newestCategory);
                boolean setZkStatus = zookeeperClient.setCategory(clusterName, toZkCategory);
                if (!setZkStatus) {
                    //rollback
                    result = false;
                }
                result = setZkStatus;
            } else {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("exception for offline category", e);
            result = false;
        }
        return RedissonxResponseBuilder.success(result);
    }

    private List<String> loadOnlineCategorys(String clusterName) {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCStatusEqualTo(2);
        List<Category> onlineCategoryList = categoryMapper.selectByCondition(condition);
        List<String> onlineCategorys = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(onlineCategoryList)) {
            onlineCategoryList.forEach(categoryItem -> {
                onlineCategorys.add(categoryItem.getCategory());
            });
        }
        return onlineCategorys;
    }

    @Override
    public RedissonxResponse<List<String>> scan(String clusterName, String category, String paramFormat, Integer tenantId, String operator) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isBlank(category)) {
            result = loadOnlineCategorys(clusterName);
        } else {
            RedissonxClient redissonxClient = getClientByClusterName(clusterName);
            if (null != redissonxClient) {
                RKeys rKeys = redissonxClient.getKeys();
                StringBuffer pattern = new StringBuffer();
                pattern.append(category);
                if (StringUtils.isNotBlank(paramFormat)) {
                    pattern.append("\\.");
                    pattern.append(paramFormat);
                }
                pattern.append("*");
                if (null != tenantId && tenantId > 0) {
                    pattern.append("@t");
                    pattern.append(tenantId);
                }
                Iterable<String> keys = rKeys.getKeysByPattern(pattern.toString());
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    result.add(iterator.next());
                }
            }
        }
        return RedissonxResponseBuilder.success(result);
    }
}
