package com.leshiguang.arch.redissonx.server.service.impl;

import com.leshiguang.arch.redissonx.core.entity.gen.*;
import com.leshiguang.arch.redissonx.core.mapper.gen.CategoryMapper;
import com.leshiguang.arch.redissonx.core.mapper.gen.ClusterConnectMappingMapper;
import com.leshiguang.arch.redissonx.server.domain.category.CategoryVO;
import com.leshiguang.arch.redissonx.server.domain.request.CategoryQueryRequest;
import com.leshiguang.arch.redissonx.server.service.CategoryService;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import com.leshiguang.redissonx.common.base.RedissonxTable;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redissonx;
import org.redisson.RedissonxClient;
import org.redisson.api.RKeys;
import org.redisson.config.Config;
import org.redisson.config.RedissonxConfig;
import org.redisson.config.RedissonxConfigLoader;
import org.redisson.config.ZookeeperRedissonxConfigLoader;
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
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ClusterConnectMappingMapper clusterConnectMappingMapper;
    private RedissonxConfigLoader configLoader = new ZookeeperRedissonxConfigLoader();

    private Map<String, RedissonxClient> redissonxClientMap = new ConcurrentHashMap<String, RedissonxClient>();

    public RedissonxClient getClientByClusterName(String clusterName, String region) {
        String key = clusterName + "_" + region;
        if (!redissonxClientMap.containsKey(key)) {
            synchronized (this) {
                if (!redissonxClientMap.containsKey(key)) {
                    RedissonxConfig config = configLoader.getByClusterAndRegion(clusterName, region);
                    if (null == config) {
                        return null;
                    }
                    config.setAuthStrategys(false);
                    RedissonxClient redissonxClient = Redissonx.create(clusterName, config);
                    redissonxClientMap.put(key, redissonxClient);
                }
            }
        }
        return redissonxClientMap.get(key);
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
            criteria.andOwnerLike("%" + request.getUserId() + "%");
            criteria.andMemberLike("%" + request.getUserId() + "%");
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

    private CategoryVO toVO(Category source) {
        CategoryVO target = new CategoryVO();
        target.setClusterName(source.getClusterName());
        target.setCategory(source.getCategory());
        target.setVersion(source.getVersion());
        target.setDuration(source.getDuration());
        target.setHot(source.getIshot() > 0);
        target.setIndexTemplate(source.getIndexTemplate());
        if (StringUtils.isNotEmpty(source.getMember())) {
            target.setMemberList(Arrays.asList(StringUtils.split(source.getMember(), ",")));
        }
        if (StringUtils.isNotEmpty(source.getOwner())) {
            target.setOwnerList(Arrays.asList(StringUtils.split(source.getOwner(), ",")));
        }
        target.setCreateTime(source.getCreateTime());
        target.setUpdateTime(source.getUpdateTime());
        target.setStatus(source.getcStatus());
        return target;
    }

    private CategoryBO toBO(Category source) {
        CategoryBO target = new CategoryBO();
        target.setClusterName(source.getClusterName());
        target.setCategory(source.getCategory());
        target.setVersion(source.getVersion());
        target.setDuration(source.getDuration());
        target.setHot(source.getIshot() > 0);
        target.setIndexTemplate(source.getIndexTemplate());
        return target;
    }

    private Category toDBEntity(CategoryVO source) {
        Category target = new Category();
        target.setClusterName(source.getClusterName());
        target.setCategory(source.getCategory());
        target.setVersion(source.getVersion());
        target.setDuration(source.getDuration());
        target.setIshot(source.isHot() ? 1 : 0);
        target.setIndexTemplate(source.getIndexTemplate());
        if (CollectionUtils.isNotEmpty(source.getMemberList())) {
            target.setMember(StringUtils.join(source.getMemberList(), ","));
        }
        if (CollectionUtils.isNotEmpty(source.getOwnerList())) {
            target.setOwner(StringUtils.join(source.getOwnerList(), ","));
        }
        return target;
    }

    @Override
    public RedissonxResponse<Boolean> save(String clusterName, CategoryVO category, String operator) {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(category.getCategory());
        Category existCategory = categoryMapper.selectOneByCondition(condition);
        Boolean result = false;
        Category operationCategory = toDBEntity(category);
        if (null == existCategory) {
            operationCategory.setOwner(operator);
            operationCategory.setMember(operator);
            operationCategory.setCreateTime(new Date());
            operationCategory.setUpdateTime(new Date());
            operationCategory.setcStatus(1);
            int insertCount = categoryMapper.insertSelective(operationCategory);
            result = insertCount > 0;
        } else {
            operationCategory.setUpdateTime(new Date());
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
        statusCategory.setDeleteTime(new Date());
        int updateCount = categoryMapper.updateByConditionSelective(statusCategory, condition);
        boolean updateStatus = updateCount > 0;
        return RedissonxResponseBuilder.success(updateStatus);
    }

    @Override
    public RedissonxResponse<Boolean> hardDelete(String clusterName, String category, String operator) {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(category);
        int deleteCount = categoryMapper.deleteByCondition(condition);
        boolean deleteStatus = deleteCount > 0;
        return RedissonxResponseBuilder.success(deleteStatus);
    }

    @Override
    public RedissonxResponse<Boolean> reset(String clusterName, String category, String operator) {
        CategoryCondition condition = new CategoryCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCategoryEqualTo(category);
        Category statusCategory = new Category();
        statusCategory.setcStatus(1);
        statusCategory.setUpdateTime(new Date());
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
            statusCategory.setVersion(version);
            int updateCount = categoryMapper.updateByConditionSelective(statusCategory, condition);
            boolean updateStatus = updateCount > 0;
            if (updateStatus) {
                Category newestCategory = categoryMapper.selectOneByCondition(condition);
                CategoryBO toZkCategory = toBO(newestCategory);
                ClusterConnectMappingCondition mappingCondition = new ClusterConnectMappingCondition();
                mappingCondition.createCriteria().andClusterNameEqualTo(clusterName);
                List<ClusterConnectMapping> mappingList = clusterConnectMappingMapper.selectByCondition(mappingCondition);
                Set<String> regionList = new HashSet<>();
                for (ClusterConnectMapping clusterConnectMapping : mappingList) {
                    regionList.add(clusterConnectMapping.getRegion());
                }
                for (String region : regionList) {
                    ZookeeperClient zookeeperClient = ZookeeperClientFactory.getInstance(region);
                    zookeeperClient.setCategory(clusterName, toZkCategory);
                }
                result = true;
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
            statusCategory.setVersion("none");
            int updateCount = categoryMapper.updateByConditionSelective(statusCategory, condition);
            boolean updateStatus = updateCount > 0;
            if (updateStatus) {
                Category newestCategory = categoryMapper.selectOneByCondition(condition);
                CategoryBO toZkCategory = toBO(newestCategory);
                ClusterConnectMappingCondition mappingCondition = new ClusterConnectMappingCondition();
                mappingCondition.createCriteria().andClusterNameEqualTo(clusterName);
                List<ClusterConnectMapping> mappingList = clusterConnectMappingMapper.selectByCondition(mappingCondition);
                Set<String> regionList = new HashSet<>();
                for (ClusterConnectMapping clusterConnectMapping : mappingList) {
                    regionList.add(clusterConnectMapping.getRegion());
                }
                for (String region : regionList) {
                    ZookeeperClient zookeeperClient = ZookeeperClientFactory.getInstance(region);
                    zookeeperClient.setCategory(clusterName, toZkCategory);
                }
                result = true;
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
    public RedissonxResponse<List<String>> scan(String clusterName, String region, String category, String paramFormat, Integer tenantId, String operator) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isBlank(category)) {
            result = loadOnlineCategorys(clusterName);
        } else {
            RedissonxClient redissonxClient = getClientByClusterName(clusterName, region);
            if (null != redissonxClient) {
                RKeys rKeys = redissonxClient.getKeys();
                StringBuffer pattern = new StringBuffer();
                pattern.append(category);
                pattern.append("\\.");
                if (StringUtils.isNotBlank(paramFormat)) {
                    pattern.append(paramFormat);
                }
                pattern.append("*");
                if (null != tenantId && tenantId > 0) {
                    pattern.append("@t");
                    pattern.append(tenantId);
                }
                Iterable<String> keys = rKeys.getKeysByPattern(pattern.toString(), 50000);
                Iterator<String> iterator = keys.iterator();
                if (iterator != null) {
                    while (iterator.hasNext()) {
                        result.add(iterator.next());
                    }
                }
            }
        }
        return RedissonxResponseBuilder.success(result);
    }
}
