CREATE TABLE `oauth_client_details` (
                                        `client_id` varchar(256) NOT NULL,
                                        `resource_ids` varchar(256) DEFAULT NULL,
                                        `client_secret` varchar(256) DEFAULT NULL,
                                        `scope` varchar(256) DEFAULT NULL,
                                        `authorized_grant_types` varchar(256) DEFAULT NULL,
                                        `web_server_redirect_uri` varchar(256) DEFAULT NULL,
                                        `authorities` varchar(256) DEFAULT NULL,
                                        `access_token_validity` int(11) DEFAULT NULL,
                                        `refresh_token_validity` int(11) DEFAULT NULL,
                                        `additional_information` varchar(4096) DEFAULT NULL,
                                        `autoapprove` varchar(256) DEFAULT NULL,
                                        PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_permission` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                  `en_name` varchar(45) DEFAULT NULL,
                                  `name` varchar(45) DEFAULT NULL,
                                  `comment` varchar(400) DEFAULT NULL COMMENT '备注',
                                  `status` varchar(2) DEFAULT '00' COMMENT '00：正常/01：禁用/-1：删除',
                                  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人',
                                  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_user_id` int(11) DEFAULT NULL COMMENT '更新人',
                                  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_role` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `en_name` varchar(20) DEFAULT NULL,
                            `name` varchar(20) DEFAULT NULL,
                            `comment` varchar(400) DEFAULT NULL,
                            `status` varchar(2) DEFAULT '00' COMMENT '00：正常/01：禁用/-1：作废',
                            `create_user_id` int(11) DEFAULT NULL,
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                            `update_user_id` int(11) DEFAULT NULL,
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_role_permission` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                       `role_id` int(11) DEFAULT NULL,
                                       `permission_id` int(11) DEFAULT NULL,
                                       `create_user_id` int(11) DEFAULT NULL COMMENT '创建人',
                                       `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_user_id` int(11) DEFAULT NULL COMMENT '更新人',
                                       `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_user` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'UserID',
                            `user_name` varchar(45) NOT NULL COMMENT '账号',
                            `password` varchar(200) NOT NULL COMMENT '密码',
                            `name` varchar(45) DEFAULT NULL,
                            `header_img` varchar(200) DEFAULT NULL,
                            `comment` varchar(400) DEFAULT NULL COMMENT '备注',
                            `status` varchar(2) NOT NULL DEFAULT '00' COMMENT '00：正常/01：禁用/-1：删除',
                            `create_user_id` int(11) DEFAULT NULL COMMENT '创建人',
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_user_id` int(11) DEFAULT NULL COMMENT '更新人',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `sys_user_userName_uindex` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_user_role` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `user_id` int(11) DEFAULT NULL,
                                 `role_id` int(11) DEFAULT NULL,
                                 `create_user_id` int(11) DEFAULT NULL COMMENT '创建人',
                                 `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_user_id` int(11) DEFAULT NULL COMMENT '更新人',
                                 `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
