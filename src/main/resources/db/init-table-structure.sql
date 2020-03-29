
create TABLE IF NOT EXISTS `article_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255)  NOT NULL,
  `title` varchar(255)  NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `content` text,
  `pj` json DEFAULT NULL,
  `record_status` int(11) DEFAULT '0',
  `remarks` varchar(255) DEFAULT NULL,
  `sort_priority` int(11) DEFAULT '0',
  `version` int(11) DEFAULT '0',
  `date_created` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章表';


create TABLE IF NOT EXISTS `person_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `record_status` int(11) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `sort_priority` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `date_created` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10000001 DEFAULT CHARSET=utf8 COMMENT='person表';

create TABLE IF NOT EXISTS `sequence_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sequence_name` varchar(50) DEFAULT NULL COMMENT 'key值',
  `current_value` bigint(20) NOT NULL DEFAULT '1' COMMENT '当前值',
  `version` int(11) NOT NULL DEFAULT '0',
  `record_status` int(11) NOT NULL DEFAULT '0',
  `sort_priority` int(11) DEFAULT '0',
  `remark` varchar(255) DEFAULT NULL,
  `date_created` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sequence_name` (`sequence_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='序列表';

create TABLE IF NOT EXISTS `information_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `summary` varchar(266)  COMMENT '摘要',
  `url` varchar(266)  COMMENT 'url',
  `url_hash_value` int(11)  DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '0',
  `record_status` int(11) NOT NULL DEFAULT '0',
  `sort_priority` int(11) DEFAULT '0',
  `remark` varchar(255) DEFAULT NULL,
  `date_created` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_url_hash_value` (`url_hash_value`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='信息表';
