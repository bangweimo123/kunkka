DROP database IF EXISTS `kunkka`;
CREATE database `kunkka` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
DROP USER IF EXISTS kunkka@'10.%.%.%';
CREATE USER kunkka@'10.%.%.%' IDENTIFIED BY 'kunkka@qwert12345';
GRANT SELECT ,UPDATE ,INSERT,DELETE ON kunkka.* TO kunkka@'10.%.%.%' IDENTIFIED BY 'kunkka@qwert12345';
flush privileges;
USE `kunkka`;
/**
 * 连接实例表
 */
DROP TABLE IF EXISTS `connect`;
CREATE TABLE `connect` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `connect_name` varchar(64) NOT NULL DEFAULT '' COMMENT '连接名',
  `region` varchar(64) NOT NULL DEFAULT '' COMMENT '所属区域',
  `host_name` varchar(64) NOT NULL DEFAULT '' COMMENT '主机地址',
  `port` int(8) NOT NULL COMMENT '端口号',
  `c_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  COMMENT='连接实例信息';
/**
 * 集群配置表
 */
DROP TABLE IF EXISTS `cluster`;
CREATE TABLE `cluster` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `cluster_name` varchar(64) NOT NULL DEFAULT '' COMMENT '集群名',
  `cluster_mode` varchar(8) NOT NULL DEFAULT '' COMMENT '集群模式',
  `cluster_engine` varchar(8) NOT NULL COMMENT '集群引擎',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_status` tinyint(4) NOT NULL COMMENT '状态',
  `owner_list` varchar(256) NOT NULL DEFAULT '' COMMENT '所属人',
  `member_list` varchar(256) NOT NULL DEFAULT '' COMMENT '成员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  COMMENT='集群信息';

/**
 * 集群关联关系表
 */
DROP TABLE IF EXISTS `cluster_region_mapping`;
CREATE TABLE `cluster_region_mapping` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `region` varchar(64) NOT NULL DEFAULT '' COMMENT '区域',
  `cluster_name` varchar(64) NOT NULL DEFAULT '' COMMENT '集群名',
  PRIMARY KEY (`id`),
  KEY `uk_cluster_region` (`cluster_name`,`region`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  COMMENT='集群区域关系表';

/**
 * 集群连接关系表
 */
DROP TABLE IF EXISTS `cluster_connect_mapping`;
CREATE TABLE `cluster_connect_mapping` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `cluster_name` varchar(64) NOT NULL COMMENT '集群名',
  `region` varchar(64) NOT NULL DEFAULT '' COMMENT '区域',
  `master_node` varchar(64) NOT NULL DEFAULT '' COMMENT '主节点',
  `slave_nodes` varchar(512) NOT NULL DEFAULT '' COMMENT '子节点',
  `db` int(4) NOT NULL COMMENT '数据库',
  `password_mode` tinyint(4) NOT NULL DEFAULT '0' COMMENT '密码模式',
  `password` varchar(256) NOT NULL DEFAULT '' COMMENT '密码',
  `connect_params` varchar(512) NOT NULL DEFAULT '' COMMENT '连接参数',
  PRIMARY KEY (`id`),
  KEY `uk_cluster_region` (`cluster_name`,`region`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4   COMMENT='集群连接关系表';
/**
 * 集群权限关系表
 */
DROP TABLE IF EXISTS `cluster_auth_mapping`;
CREATE TABLE `cluster_auth_mapping` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `cluster_name` varchar(64) NOT NULL COMMENT '集群名',
  `field` varchar(32) NOT NULL DEFAULT '' COMMENT '使用的类型',
  `operate` varchar(64) NOT NULL DEFAULT '' COMMENT '操作',
  `data` varchar(256) NOT NULL DEFAULT '' COMMENT '数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='集群权限关系表';
/**
 * Category信息表
 */
DROP TABLE IF EXISTS `category`;
CREATE TABLE  IF NOT EXISTS `category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增',
  `cluster_name` varchar(64) NOT NULL DEFAULT '' COMMENT '集群名',
  `category` varchar(128) NOT NULL DEFAULT '' COMMENT 'category',
  `duration` varchar(8) NOT NULL DEFAULT '' COMMENT '有效时间',
  `index_template` varchar(32) NOT NULL DEFAULT '' COMMENT '参数模版',
  `hot` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为热key',
  `c_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_type` varchar(32) NOT NULL DEFAULT '' COMMENT '使用的类型',
  `multi_tenant` tinyint(4) NOT NULL COMMENT '是否为多租户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='category信息';


/**
 * 操作记录表
 */
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `operator` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人',
  `c_type` varchar(32) NOT NULL DEFAULT '' COMMENT '操作类型',
  `content` varchar(512) NOT NULL DEFAULT '' COMMENT '内容',
  `op_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `rel_type` varchar(16) NOT NULL DEFAULT '' COMMENT '关联类型',
  `rel_key` varchar(64) NOT NULL DEFAULT '' COMMENT '关联key',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';
/**
 * 用户权限表
 */
DROP TABLE IF EXISTS `user_role_mapping`;
 CREATE TABLE `user_role_mapping` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user` varchar(64) NOT NULL DEFAULT '' COMMENT '用户',
  `role` varchar(32) NOT NULL DEFAULT '' COMMENT '角色',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='用户权限表';
/**
 * 区域配置表
 */
DROP TABLE IF EXISTS `region_config`;
 CREATE TABLE `region_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `region` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '区域',
  `zk_address` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'zk地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4  COMMENT='用户权限表';
