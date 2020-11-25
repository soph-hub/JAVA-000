## 用户表，用户资金账户，用户收获地址表
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`
(
    `id`            int(11)                          NOT NULL AUTO_INCREMENT COMMENT '用户编号',
    `nickname`      varchar(255) COLLATE utf8mb4_bin          DEFAULT NULL COMMENT '昵称',
    `avatar`        varchar(255) COLLATE utf8mb4_bin          DEFAULT NULL COMMENT '头像',
    `status`        tinyint(4)                       NOT NULL COMMENT '状态',
    `mobile`        varchar(11) COLLATE utf8mb4_bin  NOT NULL COMMENT '手机号',
    `password`      varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '加密后的密码',
    `password_salt` varchar(64) COLLATE utf8mb4_bin  NOT NULL COMMENT '密码的盐',
    `register_ip`   varchar(32) COLLATE utf8mb4_bin  NOT NULL COMMENT '注册 IP',
    `create_time`   datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       tinyint(1)                       NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_mobile` (`mobile`) USING BTREE COMMENT '手机号'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='会员基础信息表';

DROP TABLE IF EXISTS `member_funds_account`;
CREATE TABLE `member_funds_account`
(
    `id`             int(11)                          NOT NULL AUTO_INCREMENT COMMENT '会员资金账户 id',
    `member_id`      int(11)                          NOT NULL COMMENT '会员 id',
    `balance`           varchar(10) COLLATE utf8mb4_bin  NOT NULL COMMENT '账户余额',
    `create_time`    datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted`        tinyint(1)                       NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_memberId` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='会员资金账户表';

DROP TABLE IF EXISTS `member_address`;
CREATE TABLE `member_address`
(
    `id`             int(11)                          NOT NULL AUTO_INCREMENT COMMENT '收件地址编号',
    `member_id`      int(11)                          NOT NULL COMMENT '用户编号',
    `name`           varchar(10) COLLATE utf8mb4_bin  NOT NULL COMMENT '收件人名称',
    `mobile`         varchar(20) COLLATE utf8mb4_bin  NOT NULL COMMENT '手机号',
    `area_code`      int(11)                          NOT NULL COMMENT '地区编码',
    `detail_address` varchar(250) COLLATE utf8mb4_bin NOT NULL COMMENT '收件详细地址',
    `type`           tinyint(4)                       NOT NULL COMMENT '地址类型',
    `create_time`    datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted`        tinyint(1)                       NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_memberId` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='会员收件地址表';

##商品品牌信息、商品类别信息、商品规格键、商品规格值、商品 SPU、商品 SKU
DROP TABLE IF EXISTS `product_brand`;
CREATE TABLE `product_brand`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT COMMENT '品牌编号',
    `name`        varchar(50) NOT NULL COMMENT '品牌名称',
    `description` varchar(255)         DEFAULT NULL COMMENT '品牌描述',
    `pic_url`     varchar(1024)        DEFAULT NULL COMMENT '品牌名图片',
    `status`      tinyint(4)  NOT NULL COMMENT '状态 1开启 2禁用',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted`     tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品品牌信息表';

DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT COMMENT '分类编号',
    `pid`         int(11)     NOT NULL COMMENT '父分类编号',
    `name`        varchar(16) NOT NULL COMMENT '分类名称',
    `description` varchar(255)         DEFAULT NULL COMMENT '分类描述',
    `pic_url`     varchar(255)         DEFAULT NULL COMMENT '分类图片',
    `sort`        int(11)     NOT NULL COMMENT '分类排序',
    `status`      tinyint(4)  NOT NULL COMMENT '状态',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted`     tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品类别信息表';

DROP TABLE IF EXISTS `product_attr_key`;
CREATE TABLE `product_attr_key`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT COMMENT '规格键编号',
    `name`        varchar(50) NOT NULL DEFAULT '' COMMENT '规格键名称',
    `status`      tinyint(4)  NOT NULL DEFAULT '1' COMMENT '状态     * 1-开启     * 2-禁用',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted`     tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品规格键';

DROP TABLE IF EXISTS `product_attr_value`;
CREATE TABLE `product_attr_value`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT COMMENT '规格值编号',
    `attr_key_id` int(11)     NOT NULL COMMENT '规格键编号',
    `name`        varchar(50) NOT NULL DEFAULT '' COMMENT '规格值名字',
    `status`      tinyint(4)  NOT NULL DEFAULT '1' COMMENT '状态    *     * 1-开启     * 2-禁用',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted`     tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品规格值';

DROP TABLE IF EXISTS `product_spu`;
CREATE TABLE `product_spu`
(
    `id`          int(11)       NOT NULL AUTO_INCREMENT COMMENT 'SPU 编号',
    `name`        varchar(50)   NOT NULL DEFAULT '' COMMENT 'SPU 名字',
    `sell_point`  varchar(50)   NOT NULL DEFAULT '' COMMENT '卖点',
    `description` text          NOT NULL COMMENT '描述',
    `cid`         int(11)       NOT NULL DEFAULT '-1' COMMENT '分类编号',
    `pic_urls`    varchar(1024) NOT NULL DEFAULT '' COMMENT '商品主图地址     * 数组，以逗号分隔     * 建议尺寸：800*800像素，你可以拖拽图片调整顺序，最多上传15张',
    `visible`     tinyint(4)    NOT NULL DEFAULT '0' COMMENT '是否上架商品（是否可见）。    * true 为已上架    * false 为已下架',
    `sort`        int(11)       NOT NULL DEFAULT '0' COMMENT '排序字段',
    `price`       int(11)       NOT NULL COMMENT '价格',
    `quantity`    int(11)       NOT NULL COMMENT '库存数量',
    `create_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted`     tinyint(1)    NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品 SPU';

DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT COMMENT 'sku 编号',
    `spu_id`      int(11)     NOT NULL DEFAULT '-1' COMMENT '商品编号',
    `status`      int(11)     NOT NULL DEFAULT '-1' COMMENT '状态    * 1-正常     * 2-禁用',
    `pic_url`     varchar(50)          DEFAULT '' COMMENT '图片地址',
    `attrs`       varchar(50) NOT NULL DEFAULT '' COMMENT '规格值({@link ProductAttrDO})数组     * 数组，以逗号分隔',
    `price`       int(11)     NOT NULL DEFAULT '-1' COMMENT '价格，单位：分',
    `quantity`    int(11)     NOT NULL DEFAULT '-1' COMMENT '库存数量',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted`     tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品 SKU';

##订单表、订单明细表、订单物流信息表、订单物流信息详情表、收货信息表、订单取消信息表、换货信息表、退货/退款信息表、会员购物车信息表
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`                  int(11)     NOT NULL AUTO_INCREMENT COMMENT 'Id，自增长',
    `member_id`           int(11)     NOT NULL COMMENT '会员id',
    `order_no`            varchar(50) NOT NULL COMMENT '订单单号',
    `buy_price`           int(11)     NOT NULL COMMENT '订单金额(总金额)',
    `discount_price`      int(11)     NOT NULL COMMENT '优惠总金额，单位：分',
    `logistics_price`     int(11)     NOT NULL COMMENT '物流金额',
    `present_price`       int(11)     NOT NULL COMMENT '最终金额，单位：分',
    `pay_amount`          int(10)     NOT NULL COMMENT '金额(分)',
    `payment_time`        datetime             DEFAULT NULL COMMENT '付款时间',
    `delivery_time`       datetime             DEFAULT NULL COMMENT '发货时间',
    `receiver_time`       datetime             DEFAULT NULL COMMENT '收货时间',
    `closing_time`        datetime             DEFAULT NULL COMMENT '成交时间',
    `has_return_exchange` smallint(6) NOT NULL COMMENT '是否退换货',
    `remark`              varchar(255)         DEFAULT NULL COMMENT '备注',
    `status`              smallint(2) NOT NULL COMMENT '状态(如果有多个商品分开发货需要全部商品发完才会改变状态) 0、待付款 1、待发货 2、待收货 3、已完成 4、已关闭',
    `create_time`         datetime             DEFAULT NULL COMMENT '订单创建时间',
    `update_time`         datetime             DEFAULT NULL COMMENT '更新时间',
    `deleted`             tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT '订单表'
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`
(
    `id`                  int(11)      NOT NULL AUTO_INCREMENT COMMENT 'id自增长',
    `order_id`            int(11)      NOT NULL COMMENT '订单编号',
    `order_no`            varchar(50)  NOT NULL COMMENT '订单号',
    `order_logistics_id`  int(11)               DEFAULT NULL COMMENT '物流id',
    `sku_id`              int(11)      NOT NULL COMMENT '商品id',
    `sku_name`            varchar(50)  NOT NULL COMMENT '商品名字',
    `sku_image`           varchar(250) NOT NULL COMMENT '图片名字',
    `quantity`            int(3)       NOT NULL COMMENT '商品数量',
    `origin_price`        int(11)      NOT NULL COMMENT '原始单价，单位：分',
    `buy_price`           int(11)      NOT NULL COMMENT '购买单价，单位：分',
    `present_price`       int(11)      NOT NULL COMMENT '最终价格，单位：分',
    `buy_total`           int(11)      NOT NULL COMMENT '购买总金额，单位：分',
    `discount_total`      int(11)      NOT NULL COMMENT '优惠总金额，单位：分',
    `present_total`       int(11)      NOT NULL COMMENT '最终总金额，单位：分',
    `payment_time`        datetime              DEFAULT NULL COMMENT '付款时间',
    `delivery_time`       datetime              DEFAULT NULL COMMENT '发货时间',
    `receiver_time`       datetime              DEFAULT NULL COMMENT '收货时间',
    `closing_time`        datetime              DEFAULT NULL,
    `has_return_exchange` int(11)               DEFAULT NULL COMMENT '是否退换货',
    `remark`              varchar(250)          DEFAULT NULL COMMENT '备注',
    `delivery_type`       int(2)       NOT NULL COMMENT '发送方式',
    `status`              smallint(2)  NOT NULL COMMENT '状态：0、代发货 1、已发货 2、已收货 20、换货中 21、换货成功 40、退货中 41、已退货',
    `create_time`         datetime     NOT NULL COMMENT '创建时间',
    `update_time`         datetime              DEFAULT NULL COMMENT '更新时间',
    `deleted`             tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT '订单明细表'
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `order_logistics`;
CREATE TABLE `order_logistics`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT COMMENT 'id自增长',
    `area_no`      varchar(10)  NOT NULL COMMENT '地区编号',
    `name`         varchar(20)  NOT NULL COMMENT '名称',
    `mobile`       varchar(20)  NOT NULL COMMENT '手机号',
    `address`      varchar(255) NOT NULL COMMENT '详细地址',
    `logistics`    int(2)       NOT NULL COMMENT '物流商家',
    `logistics_no` varchar(20)  NOT NULL COMMENT '物流单号',
    `create_time`  datetime     NOT NULL COMMENT '创建时间',
    `update_time`  datetime              DEFAULT NULL COMMENT '更新时间',
    `deleted`      tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT '订单物流信息表'
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `order_logistics_detail`;
CREATE TABLE `order_logistics_detail`
(
    `id`                    int(11)     NOT NULL AUTO_INCREMENT COMMENT 'id自增长',
    `order_logistics_id`    int(11)     NOT NULL COMMENT '物流编号',
    `logistics_time`        datetime    NOT NULL COMMENT '物流时间',
    `logistics_information` varchar(20) NOT NULL COMMENT '物流信息',
    `create_time`           datetime    NOT NULL COMMENT '创建时间',
    `update_time`           datetime             DEFAULT NULL COMMENT '更新时间',
    `deleted`               tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT '订单物流信息详情表'
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `order_recipient`;
CREATE TABLE `order_recipient`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `order_id`    int(11)      NOT NULL COMMENT '订单id',
    `area_no`     varchar(20)  NOT NULL COMMENT '区域编号',
    `name`        varchar(20)  NOT NULL COMMENT '收件人名称',
    `mobile`      varchar(20)  NOT NULL COMMENT '手机号',
    `type`        int(2)       NOT NULL COMMENT '快递方式',
    `address`     varchar(250) NOT NULL COMMENT '地址详细',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_time` datetime              DEFAULT NULL COMMENT '更新时间',
    `deleted`     tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT '收货信息表'
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `order_cancel`;
CREATE TABLE `order_cancel`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `order_id`     int(11)     NOT NULL COMMENT '订单id',
    `order_no`     varchar(50) NOT NULL COMMENT '订单编号',
    `reason`       int(2)      NOT NULL COMMENT '取消原因',
    `other_reason` varchar(100)         DEFAULT NULL COMMENT '其他原因',
    `create_time`  datetime    NOT NULL COMMENT '创建时间',
    `update_time`  datetime             DEFAULT NULL COMMENT '更新时间',
    `deleted`      tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
    COMMENT '订单取消信息表'
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `order_exchange`;
CREATE TABLE `order_exchange`
(
    `id`                          int(11)     NOT NULL AUTO_INCREMENT,
    `order_id`                    int(11)     NOT NULL,
    `order_no`                    varchar(50) NOT NULL,
    `sku_id`                      int(11)     NOT NULL,
    `exchange_sku_id`             int(11)     NOT NULL COMMENT '换货商品id',
    `exchange_order_logistics_id` int(11)     NOT NULL COMMENT '换货物流id',
    `receiver_order_logistics_id` int(11)     NOT NULL COMMENT '收件地址',
    `order_reason_id`             int(11)              DEFAULT NULL COMMENT '换货原因',
    `reason`                      varchar(255)         DEFAULT NULL COMMENT '换货原因 (其他的时候)',
    `payment_time`                datetime             DEFAULT NULL COMMENT '付款时间',
    `delivery_time`               datetime             DEFAULT NULL COMMENT '发货时间',
    `receiver_time`               datetime             DEFAULT NULL COMMENT '收货时间',
    `closing_time`                datetime             DEFAULT NULL COMMENT '成交时间',
    `order_type`                  int(2)               DEFAULT NULL COMMENT '订单类型 0、为 Order 订单 1、为 OrderItem 订单',
    `status`                      int(2)               DEFAULT NULL COMMENT '状态 申请换货、申请成功、申请失败、换货中、换货成功',
    `create_time`                 datetime             DEFAULT NULL COMMENT '创建时间',
    `update_time`                 datetime             DEFAULT NULL COMMENT '更新时间',
    `deleted`                     tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
    COMMENT '换货信息表'
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `order_return`;
CREATE TABLE `order_return`
(
    `id`                 int(11)     NOT NULL AUTO_INCREMENT COMMENT 'id自增长',
    `service_number`     varchar(50) NOT NULL COMMENT '服务号',
    `order_id`           int(11)     NOT NULL COMMENT '订单编号',
    `order_no`           varchar(50) NOT NULL COMMENT '订单号',
    `order_logistics_id` int(11)              DEFAULT NULL COMMENT '物流 id',
    `refund_price`       int(11)     NOT NULL COMMENT '退回金额',
    `order_reason_id`             int(11)              DEFAULT NULL COMMENT '退货原因',
    `reason`                      varchar(255)         DEFAULT NULL COMMENT '退货原因 (其他的时候)',
    `status`             int(2)      NOT NULL COMMENT '状态 申请换货、申请成功、申请失败、退货中、退货成功',
    `approval_time`      datetime             DEFAULT NULL COMMENT '同意时间',
    `refuse_time`        datetime             DEFAULT NULL COMMENT '拒绝时间',
    `logistics_time`     datetime             DEFAULT NULL COMMENT '物流时间（填写物流单号时间）',
    `receiver_time`      datetime             DEFAULT NULL COMMENT '收货时间',
    `closing_time`       datetime             DEFAULT NULL COMMENT '成交时间',
    `service_type`       int(2)               DEFAULT NULL COMMENT ' 1、退货 2、退款',
    `create_time`        datetime    NOT NULL COMMENT '创建时间',
    `update_time`        datetime             DEFAULT NULL COMMENT '更新时间',
    `deleted`            tinyint(1)  NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT '退货/退款信息表'
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item`
(
    `id`                int(11)    NOT NULL AUTO_INCREMENT COMMENT '编号，唯一自增。',
    `status`            tinyint(4) NOT NULL DEFAULT '-1' COMMENT '状态   * 1-正常     * 2-主动删除     * 3-下单删除',
    `delete_time`       timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '商品在购物车中的删除时间',
    `selected`          bit(1)     NOT NULL COMMENT '是否选中',
    `member_id`         int(11)    NOT NULL COMMENT '会员id',
    `spu_id`            int(11)    NOT NULL COMMENT '商品 SPU 编号',
    `sku_id`            int(11)    NOT NULL COMMENT '商品 SKU 编号',
    `quantity`          int(11)    NOT NULL COMMENT '商品购买数量',
    `order_id`          int(11)             DEFAULT NULL COMMENT '订单编号',
    `order_create_time` timestamp  NULL     DEFAULT NULL COMMENT '订单创建时间',
    `create_time`       timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted`           tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT '会员购物车信息表'
  DEFAULT CHARSET = utf8mb4 COMMENT ='cart_item';