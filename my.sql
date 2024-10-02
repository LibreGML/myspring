/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : jbj

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 15/01/2024 20:16:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_base
-- ----------------------------
DROP TABLE IF EXISTS `sys_base`;
CREATE TABLE `sys_base`  (
  `field_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `field_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`field_name`) USING BTREE,
  INDEX `role_menus`(`field_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单与角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_base
-- ----------------------------
INSERT INTO `sys_base` VALUES ('123pan', '{\"uploadAddress\":\"https://open-api.123pan.com\",\"accessing\":\"https://jytc.jubijia.com\",\"clientId\":\"b2c6a5c7c67d48398efa75219481d653\",\"clientSecret\":\"32023b6addd344c3aea520f6b94d7abe\",\"open\":true}');
INSERT INTO `sys_base` VALUES ('1739667496462897154', '\"\"');
INSERT INTO `sys_base` VALUES ('1739988409301483521', '\"\"');
INSERT INTO `sys_base` VALUES ('1739989249860976642', '\"\"');
INSERT INTO `sys_base` VALUES ('1740000760092311554', '\"\"');
INSERT INTO `sys_base` VALUES ('1740000920172118018', '\"\"');
INSERT INTO `sys_base` VALUES ('bottom', '{\"QRCode\":[\"https://vip.123pan.cn/1813894330/JBJ/1f37be67bd5b4651bfe52b09900fc514.jpg\"],\"formInline1\":{\"title\":\"采用天然\",\"title1\":\"斯蒂芬\",\"title2\":\"斯蒂芬\",\"title3\":\"斯蒂芬\",\"title4\":\"斯蒂芬\",\"titleUrl\":\"http://localhost:1234/#/website\",\"title1Url\":\"http://localhost:1234/#/website\",\"title3Url\":\"http://localhost:1234/#/website\",\"title2Url\":\"http://localhost:1234/#/website\",\"title4Url\":\"http://localhost:1234/#/website\"},\"formInline2\":{\"title\":\"采用天然\",\"title1\":\"发生的\",\"title2\":\"收费的\",\"title3\":\"\",\"title4\":\"的身上\",\"titleUrl\":\"http://localhost:1234/#/website\",\"title1Url\":\"http://localhost:1234/#/website\",\"title3Url\":\"http://localhost:1234/#/website\",\"title2Url\":\"http://localhost:1234/#/website\",\"title4Url\":\"http://localhost:1234/#/website\"},\"formInline3\":{\"title\":\"采用天然\",\"title1\":\"收费的\",\"title2\":\"收费的\",\"title3\":\"收费的\",\"title4\":\"收费的\",\"titleUrl\":\"http://localhost:1234/#/website\",\"title1Url\":\"http://localhost:1234/#/website\",\"title3Url\":\"http://localhost:1234/#/website\",\"title2Url\":\"http://localhost:1234/#/website\",\"title4Url\":\"http://localhost:1234/#/website\"},\"formInline4\":{\"right\":\"双方都是反对犯得上\",\"left\":\"随风倒随风倒随风倒\",\"leftUrl\":\"http://localhost:1234/#/website\",\"rightUrl\":\"http://localhost:1234/#/website\"}}');
INSERT INTO `sys_base` VALUES ('carousel', '[{\"url\":\"https://img0.baidu.com/it/u=2136891175,1458697858&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1697562000&t=b2c080556e29ca1d8d7f85664629827d\",\"tip\":\"\"},{\"url\":\"https://img0.baidu.com/it/u=2136891175,1458697858&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1697562000&t=b2c080556e29ca1d8d7f85664629827d\",\"tip\":\"dffdf的地方反对法地方\"}]');
INSERT INTO `sys_base` VALUES ('class1', '{\"name\":\"订单\",\"category\":[[\"1727704218912325634\"],[\"1727704218912325634\",\"1727704356577771521\"],[\"1727704218912325634\",\"1727704356577771521\",\"1742017154615681026\"],[\"1727704218912325634\",\"1727704356577771521\",\"1742017055151955970\"],[\"1727677497970434050\"],[\"1727677497970434050\",\"1742017375877799937\"],[\"1727677497970434050\",\"1742017375877799937\",\"1742017610347782145\"],[\"1727677497970434050\",\"1742017375877799937\",\"1742017498003349505\"]]}');
INSERT INTO `sys_base` VALUES ('class2', '{\"name\":\"都反对\",\"category\":[[\"1727704218912325634\"],[\"1727704218912325634\",\"1727704356577771521\"],[\"1727677497970434050\"]]}');
INSERT INTO `sys_base` VALUES ('jion', '[\"https://jytc.jubijia.com/JBJ/946cb90f32d645e8b2a7ab938bffd3de.png\"]');
INSERT INTO `sys_base` VALUES ('mian-logo', '[\"https://vip.123pan.cn/1813894330/JBJ/70e618a1ed054114a59d9aefa6cfa432.png\"]');
INSERT INTO `sys_base` VALUES ('mianBody1', '[{\"title1\":\"还有他\",\"title2\":\"会员\",\"content1\":\"货源还有\",\"content2\":\"以后\",\"picture\":[{\"status\":\"success\",\"name\":\"01 (1).png\",\"size\":49901,\"percentage\":0,\"uid\":1703687145656,\"raw\":{\"uid\":1703687145656},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/60512b381c714fdda0bdf5e5e8a26e24.png\",\"response\":{\"fileName\":\"60512b381c714fdda0bdf5e5e8a26e24.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/60512b381c714fdda0bdf5e5e8a26e24.png\"}}]},{\"title1\":\"而反对士大夫\",\"title2\":\"犯得上广泛共识的是法国的\",\"content1\":\"收费的公司股份的\",\"content2\":\"所覆盖的高富帅大使馆犯得上广泛的是法国\",\"picture\":[{\"status\":\"success\",\"name\":\"bak.jpg\",\"size\":87248,\"percentage\":0,\"uid\":1703687154196,\"raw\":{\"uid\":1703687154196},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/d06066c925ef48cd8a9a5200d5941641.jpg\",\"response\":{\"fileName\":\"d06066c925ef48cd8a9a5200d5941641.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/d06066c925ef48cd8a9a5200d5941641.jpg\"}}]}]');
INSERT INTO `sys_base` VALUES ('mianBody2', '[{\"title1\":\"时代的\",\"picture\":[{\"status\":\"success\",\"name\":\"01 (1).png\",\"size\":49901,\"percentage\":0,\"uid\":1703765975585,\"raw\":{\"uid\":1703765975585},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/335aa482224b45be9a2802ea70334671.png\",\"response\":{\"fileName\":\"335aa482224b45be9a2802ea70334671.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/335aa482224b45be9a2802ea70334671.png\"}}]},{\"title1\":\"色的色的\",\"picture\":[{\"status\":\"success\",\"name\":\"微信图片_20231022210837_pixian_ai.png\",\"size\":1952664,\"percentage\":0,\"uid\":1703765984946,\"raw\":{\"uid\":1703765984946},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/003f88ae38904a34a6e6f4625bedee4e.png\",\"response\":{\"fileName\":\"003f88ae38904a34a6e6f4625bedee4e.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/003f88ae38904a34a6e6f4625bedee4e.png\"}}]},{\"title1\":\"erythrocyte\",\"picture\":[{\"status\":\"success\",\"name\":\"bak1.jpg\",\"size\":173935,\"percentage\":0,\"uid\":1703765996467,\"raw\":{\"uid\":1703765996467},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/860ad2d55d394292a8913efcaf82b8cb.jpg\",\"response\":{\"fileName\":\"860ad2d55d394292a8913efcaf82b8cb.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/860ad2d55d394292a8913efcaf82b8cb.jpg\"}}]},{\"title1\":\"的色如法国\",\"picture\":[{\"status\":\"success\",\"name\":\"微信图片_20231022210831_pixian_ai.png\",\"size\":2000359,\"percentage\":0,\"uid\":1703766395476,\"raw\":{\"uid\":1703766395476},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/8001799ea8c144f88d01006b0cbfbb61.png\",\"response\":{\"fileName\":\"8001799ea8c144f88d01006b0cbfbb61.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/8001799ea8c144f88d01006b0cbfbb61.png\"}}]},{\"title1\":\"而我却\",\"picture\":[{\"status\":\"success\",\"name\":\"bak.jpg\",\"size\":87248,\"percentage\":0,\"uid\":1703766404606,\"raw\":{\"uid\":1703766404606},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/75c911f3cffc47bd9e48cbfc2d044382.jpg\",\"response\":{\"fileName\":\"75c911f3cffc47bd9e48cbfc2d044382.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/75c911f3cffc47bd9e48cbfc2d044382.jpg\"}}]},{\"title1\":\"热点购房人得分\",\"picture\":[{\"status\":\"success\",\"name\":\"01.png\",\"size\":49901,\"percentage\":0,\"uid\":1703766412749,\"raw\":{\"uid\":1703766412749},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/3fd4ca22720f43acb5c09c12ebf3475d.png\",\"response\":{\"fileName\":\"3fd4ca22720f43acb5c09c12ebf3475d.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/3fd4ca22720f43acb5c09c12ebf3475d.png\"}}]},{\"title1\":\"听音乐太\",\"picture\":[{\"status\":\"success\",\"name\":\"bak.jpg\",\"size\":87248,\"percentage\":0,\"uid\":1703766448385,\"raw\":{\"uid\":1703766448385},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/763c9a8d9bdc48a0b2885df27d6f5782.jpg\",\"response\":{\"fileName\":\"763c9a8d9bdc48a0b2885df27d6f5782.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/763c9a8d9bdc48a0b2885df27d6f5782.jpg\"}}]},{\"title1\":\"uiiuiui\",\"picture\":[{\"status\":\"success\",\"name\":\"bak1.jpg\",\"size\":173935,\"percentage\":0,\"uid\":1703766458444,\"raw\":{\"uid\":1703766458444},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/65f9774798724edd947d4e8d13bc58b1.jpg\",\"response\":{\"fileName\":\"65f9774798724edd947d4e8d13bc58b1.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/65f9774798724edd947d4e8d13bc58b1.jpg\"}}]}]');
INSERT INTO `sys_base` VALUES ('mianBody3', '{\"tableData\":[{\"title1\":\"二点四分\",\"picture\":[{\"status\":\"success\",\"name\":\"01 (1).png\",\"size\":49901,\"percentage\":0,\"uid\":1703768425707,\"raw\":{\"uid\":1703768425707},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/7aecfbfb11b847aabe306d83ca55287d.png\",\"response\":{\"fileName\":\"7aecfbfb11b847aabe306d83ca55287d.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/7aecfbfb11b847aabe306d83ca55287d.png\"}}]},{\"title1\":\"改好以后给\",\"picture\":[{\"status\":\"success\",\"name\":\"微信图片_20231022210831_pixian_ai.png\",\"size\":2000359,\"percentage\":0,\"uid\":1703768432867,\"raw\":{\"uid\":1703768432867},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/65da5a5904da4b56a58eead9ab64d701.png\",\"response\":{\"fileName\":\"65da5a5904da4b56a58eead9ab64d701.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/65da5a5904da4b56a58eead9ab64d701.png\"}}]},{\"title1\":\"发帖供奉天后\",\"picture\":[{\"status\":\"success\",\"name\":\"微信图片_20231022210837_pixian_ai.png\",\"size\":1952664,\"percentage\":0,\"uid\":1703768440180,\"raw\":{\"uid\":1703768440180},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/e2b31607f8fc4939b6877de3b8f894be.png\",\"response\":{\"fileName\":\"e2b31607f8fc4939b6877de3b8f894be.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/e2b31607f8fc4939b6877de3b8f894be.png\"}}]},{\"title1\":\"热带扰动\",\"picture\":[{\"status\":\"success\",\"name\":\"bak.jpg\",\"size\":87248,\"percentage\":0,\"uid\":1703768455543,\"raw\":{\"uid\":1703768455543},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/f855880710d944e5b7e560b1331dfcc6.jpg\",\"response\":{\"fileName\":\"f855880710d944e5b7e560b1331dfcc6.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/f855880710d944e5b7e560b1331dfcc6.jpg\"}}]}],\"other\":{\"title1\":\"的非官方更好的风格和结果返回集合\",\"content1\":\"赴德国和法国都会觉得根据各地房价的风格赴德国和法国都会觉得根据各地房价的风格赴德国和法国都会觉得根据各地房价的风格赴德国和法国都会觉得根据各地房价的风格赴德国和法国都会觉得根据各地房价的风格赴德国和法国都会觉得根据各地房价的风格赴德国和法国都会觉得根据各地房价的风格\"}}');
INSERT INTO `sys_base` VALUES ('mianBody4', '{\"tableData\":[{\"title1\":\"的风格风格\",\"picture\":[{\"status\":\"success\",\"name\":\"01.png\",\"size\":49901,\"percentage\":0,\"uid\":1703769146508,\"raw\":{\"uid\":1703769146508},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/12c95e67382b4aac9020bb373df72e83.png\",\"response\":{\"fileName\":\"12c95e67382b4aac9020bb373df72e83.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/12c95e67382b4aac9020bb373df72e83.png\"}}]},{\"title1\":\"的分公司\",\"picture\":[{\"status\":\"success\",\"name\":\"bak.jpg\",\"size\":87248,\"percentage\":0,\"uid\":1703769152220,\"raw\":{\"uid\":1703769152220},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/557d8e2b9b0b4620ae7050ef364abddd.jpg\",\"response\":{\"fileName\":\"557d8e2b9b0b4620ae7050ef364abddd.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/557d8e2b9b0b4620ae7050ef364abddd.jpg\"}}]},{\"title1\":\"电风扇犯得上\",\"picture\":[{\"status\":\"success\",\"name\":\"微信图片_20231022210831_pixian_ai.png\",\"size\":2000359,\"percentage\":0,\"uid\":1703769673591,\"raw\":{\"uid\":1703769673591},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/5d0957a95b2b4791962ab4f51a0b36a5.png\",\"response\":{\"fileName\":\"5d0957a95b2b4791962ab4f51a0b36a5.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/5d0957a95b2b4791962ab4f51a0b36a5.png\"}}]}],\"other\":{\"title1\":\"\",\"content1\":\"\",\"picture\":[{\"status\":\"success\",\"name\":\"bak1.jpg\",\"size\":173935,\"percentage\":0,\"uid\":1703769266194,\"raw\":{\"uid\":1703769266194},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/2a7cee6689654319a40f88cbd2e8dd17.jpg\",\"response\":{\"fileName\":\"2a7cee6689654319a40f88cbd2e8dd17.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/2a7cee6689654319a40f88cbd2e8dd17.jpg\"}}]}}');
INSERT INTO `sys_base` VALUES ('mianBody5', '[{\"title1\":\"阿文速度\",\"title2\":\"的股份认购范德萨\",\"picture\":[{\"status\":\"success\",\"name\":\"01 (1).png\",\"size\":49901,\"percentage\":0,\"uid\":1703770110900,\"raw\":{\"uid\":1703770110900},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/27fc432fd63a455592c2850ba0025e43.png\",\"response\":{\"fileName\":\"27fc432fd63a455592c2850ba0025e43.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/27fc432fd63a455592c2850ba0025e43.png\"}}]},{\"title1\":\"而无色输入法\",\"title2\":\"电费滚滚的夫人\",\"picture\":[{\"status\":\"success\",\"name\":\"微信图片_20231022211922.jpg\",\"size\":82056,\"percentage\":0,\"uid\":1703770122014,\"raw\":{\"uid\":1703770122014},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/1e2c1c3f758042e482d2c85c747823f8.jpg\",\"response\":{\"fileName\":\"1e2c1c3f758042e482d2c85c747823f8.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/1e2c1c3f758042e482d2c85c747823f8.jpg\"}}]},{\"title2\":\"热热\",\"title1\":\"er热热发\",\"picture\":[{\"status\":\"success\",\"name\":\"bak.jpg\",\"size\":87248,\"percentage\":0,\"uid\":1703770503125,\"raw\":{\"uid\":1703770503125},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/7227af8e74d04b95af0fb7611f613c18.jpg\",\"response\":{\"fileName\":\"7227af8e74d04b95af0fb7611f613c18.jpg\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/7227af8e74d04b95af0fb7611f613c18.jpg\"}}]},{\"title1\":\"额我热\",\"title2\":\"如果特\",\"picture\":[{\"status\":\"success\",\"name\":\"微信图片_20231022210837_pixian_ai.png\",\"size\":1952664,\"percentage\":0,\"uid\":1703770533955,\"raw\":{\"uid\":1703770533955},\"url\":\"https://vip.123pan.cn/1813894330/JBJ/2c8e4a97e8bb440f86773a1efa371450.png\",\"response\":{\"fileName\":\"2c8e4a97e8bb440f86773a1efa371450.png\",\"url\":\"https://vip.123pan.cn/1813894330/JBJ/2c8e4a97e8bb440f86773a1efa371450.png\"}}]}]');
INSERT INTO `sys_base` VALUES ('partners', '[\"https://vip.123pan.cn/1813894330/JBJ/758031d0e4934a9690828a992b5876bc.png\",\"https://vip.123pan.cn/1813894330/JBJ/ce0c417e0501492e935860e98685b86a.jpg\"]');
INSERT INTO `sys_base` VALUES ('source-hardl', 'https://img0.baidu.com/it/u=56109659,3345510515&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1697562000&t=9acbb3eef6f7582d2920eb8c4f4f252f');
INSERT INTO `sys_base` VALUES ('source-hardr', 'https://img0.baidu.com/it/u=2136891175,1458697858&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1697562000&t=b2c080556e29ca1d8d7f85664629827d');
INSERT INTO `sys_base` VALUES ('twe', '{\"twel\":\"https://img0.baidu.com/it/u=56109659,3345510515&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1697562000&t=9acbb3eef6f7582d2920eb8c4f4f252f\",\"twer\":\"https://img0.baidu.com/it/u=56109659,3345510515&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1697562000&t=9acbb3eef6f7582d2920eb8c4f4f252f\",\"equity\":\"https://vip.123pan.cn/1813894330/JBJ/7703fdcd71254763bbbf7696648561dd.jpg\"}');
INSERT INTO `sys_base` VALUES ('wallet', 'http://jubijia.com/');
INSERT INTO `sys_base` VALUES ('withdrawal', '{\"busfree\":0.05,\"subfree\":0.08}');
INSERT INTO `sys_base` VALUES ('yinghua', '{\"host\":\"https://yhmzf.com\",\"pid\":\"795\",\"key\":\"7LuuL2A2HmRUlqVL3L\"}');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单id',
  `parent_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '菜单父ID, 一级菜单父ID为1',
  `menu_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `menu_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `menu_icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `route_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单（路由）地址',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE,
  INDEX `ind_id`(`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1715693983242866689', '1', '首页', 'index', 'el-icon-s-home', '/index', 1);
INSERT INTO `sys_menu` VALUES ('1715693983242866690', '1', '账号管理', 'account', 'el-icon-user-solid', '/account', 2);
INSERT INTO `sys_menu` VALUES ('1715693983242866691', '1', '商家管理', 'business', 'el-icon-s-shop', '/business', 3);
INSERT INTO `sys_menu` VALUES ('1715693983242866692', '1', '供应商管理', 'supplier', 'el-icon-s-data', '/supplier', 4);
INSERT INTO `sys_menu` VALUES ('1715693983242866693', '1', '公告管理', 'notice', 'el-icon-message-solid', '/notice', 7);
INSERT INTO `sys_menu` VALUES ('1715693983242866694', '1', '提现管理', 'withdrawal', 'el-icon-s-ticket', '/withdrawal', 6);
INSERT INTO `sys_menu` VALUES ('1715693983242866695', '1', '插队商品管理', 'jump-queue', 'el-icon-s-management', '/jump-queue', 5);
INSERT INTO `sys_menu` VALUES ('1715693983242866696', '1', '订单管理', 'order', 'el-icon-s-open', '/order', 2);
INSERT INTO `sys_menu` VALUES ('1715693983242866697', '1', '售后管理', 'sales', 'el-icon-s-shop', '/sales', 6);
INSERT INTO `sys_menu` VALUES ('1715693983242866698', '1', '特殊模块', '', 'el-icon-s-marketing', '', 10);
INSERT INTO `sys_menu` VALUES ('1715693983242866699', '1715693983242866698', '官网数据', 'website', 'el-icon-s-flag', '/website', 1);
INSERT INTO `sys_menu` VALUES ('1715693983242866700', '1715693983242866698', '会员定价', 'price', 'el-icon-s-finance', '/price', 2);
INSERT INTO `sys_menu` VALUES ('1715693983242866701', '1', '资金管理', 'fund', 'el-icon-s-marketing', '/fund', 5);
INSERT INTO `sys_menu` VALUES ('1715693983242866702', '1', '日志记录', 'log', 'el-icon-s-marketing', '/log', 11);
INSERT INTO `sys_menu` VALUES ('1715693983242866703', '1', '货源管理', 'source', 'el-icon-s-marketing', '/source', 2);
INSERT INTO `sys_menu` VALUES ('1715693983242866755', '1715693983242866698', '分类管理', 'category', 'el-icon-s-finance', '/category', 2);
INSERT INTO `sys_menu` VALUES ('1715693983242866799', '1715693983242866698', '后台数据', 'backgroundk', 'el-icon-s-flag', '/background', 1);
INSERT INTO `sys_menu` VALUES ('1715693983242866800', '1', '个人中心', 'my-user', 'el-icon-user-solid', '/my-user', 1);
INSERT INTO `sys_menu` VALUES ('1715693983242866801', '1', '消息中心', 'message', 'el-icon-s-comment', '/message', 7);

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role`  (
  `role_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `menu_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单ID',
  INDEX `role_menus`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单与角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866689');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866690');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866691');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866692');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866693');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866694');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866695');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866696');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866697');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866698');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866699');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866700');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866703');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866755');
INSERT INTO `sys_menu_role` VALUES ('1', '1715693983242866799');
INSERT INTO `sys_menu_role` VALUES ('2', '1715693983242866689');
INSERT INTO `sys_menu_role` VALUES ('2', '1715693983242866694');
INSERT INTO `sys_menu_role` VALUES ('2', '1715693983242866696');
INSERT INTO `sys_menu_role` VALUES ('2', '1715693983242866697');
INSERT INTO `sys_menu_role` VALUES ('2', '1715693983242866701');
INSERT INTO `sys_menu_role` VALUES ('2', '1715693983242866702');
INSERT INTO `sys_menu_role` VALUES ('2', '1715693983242866800');
INSERT INTO `sys_menu_role` VALUES ('2', '1715693983242866801');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866689');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866694');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866695');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866696');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866697');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866701');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866702');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866703');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866800');
INSERT INTO `sys_menu_role` VALUES ('3', '1715693983242866801');

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '操作类型',
  `accept_param` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '接受参数',
  `response_param` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '响应参数',
  `role_type` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '角色类型（1站长，2商家，3供应商）',
  `role_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '角色id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_rol`(`role_id`) USING BTREE,
  INDEX `ind_tpy`(`role_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 735 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_source
-- ----------------------------
DROP TABLE IF EXISTS `sys_source`;
CREATE TABLE `sys_source`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '货源系统名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_menus`(`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单与角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_source
-- ----------------------------
INSERT INTO `sys_source` VALUES (1, '小储云商城');
INSERT INTO `sys_source` VALUES (2, '彩虹商城');
INSERT INTO `sys_source` VALUES (3, '卡卡云商城');

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `head_pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `account` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `role_type` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '角色类型（1站长，2商家，3供应商）',
  `role_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '角色id',
  `is_del` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_rol`(`role_id`) USING BTREE,
  INDEX `ind_tpy`(`role_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1039 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES (1000, 'https://ebui-cdn.bj.bcebos.com/yiyan-logo.png', 'jbj', '$2a$10$naaTQpyjlg1KXavsAWyYue8i5J.7t8L/ovpfW.QMSsqQrvelaB55u', '1', NULL, '0', '2023-10-21 18:56:31', '2023-10-21 19:43:30');

-- ----------------------------
-- Table structure for t_aftersales
-- ----------------------------
DROP TABLE IF EXISTS `t_aftersales`;
CREATE TABLE `t_aftersales`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `order_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '订单ID',
  `issue` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '问题描述',
  `reply` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '回复内容',
  `status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '售后状态',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_odr`(`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '售后表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_business
-- ----------------------------
DROP TABLE IF EXISTS `t_business`;
CREATE TABLE `t_business`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '商家名称',
  `level` int(0) NULL DEFAULT NULL COMMENT '会员等级',
  `expiration_time` date NULL DEFAULT NULL COMMENT '到期时间',
  `quota` decimal(50, 0) NULL DEFAULT NULL COMMENT '额度包',
  `wallet` decimal(50, 2) NULL DEFAULT NULL COMMENT '钱包',
  `abutment_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '对接密匙',
  `is_del` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '商家表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `parent_id` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '父id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '分类名称',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '图片路径',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_fund
-- ----------------------------
DROP TABLE IF EXISTS `t_fund`;
CREATE TABLE `t_fund`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_type` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '角色类型（1站长，2商家，3供应商）',
  `role_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '角色id',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `is_del` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `amount` decimal(50, 2) NULL DEFAULT NULL COMMENT '金额',
  `balance` decimal(50, 2) NULL DEFAULT NULL COMMENT '余额',
  `info` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_rol`(`role_id`) USING BTREE,
  INDEX `ind_tpy`(`role_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 99 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `info` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '信息',
  `limitb` int(0) NULL DEFAULT NULL COMMENT '额度包',
  `price` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '价钱',
  `grade` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '等级',
  `duration` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '时长',
  `is_del` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_member_order
-- ----------------------------
DROP TABLE IF EXISTS `t_member_order`;
CREATE TABLE `t_member_order`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `order_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `member_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '会员id',
  `member_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '会员名',
  `payment_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `payment_method` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '付款方式',
  `merchant_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '商家ID',
  `status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '订单状态',
  `order_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_oder`(`order_number`) USING BTREE,
  INDEX `ind_m`(`merchant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '会员订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '公告内容',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '公告类型',
  `wechat_group_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '微信群码',
  `platform_friend_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '平台站长好友码',
  `after_sales_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '售后码',
  `custom_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '自定义码',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_del` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_notice_read
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_read`;
CREATE TABLE `t_notice_read`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `notice_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '公告id',
  `account_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '账号id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '公告阅读表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `order_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `product_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '商品名',
  `order_info` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '下单信息',
  `quantity` int(0) NULL DEFAULT NULL COMMENT '份数',
  `payment_amount` decimal(20, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `payment_method` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '付款方式',
  `merchant_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '商家ID',
  `supplier_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '供应商ID',
  `integration_response` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '对接返回信息',
  `status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '订单状态',
  `merchant_balance` decimal(20, 2) NULL DEFAULT NULL COMMENT '商家账户余额',
  `merchant_quota_balance` decimal(20, 2) NULL DEFAULT NULL COMMENT '商家额度包余额',
  `order_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单时间',
  `attach` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '附加信息',
  `km_data` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '卡密信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_oder`(`order_number`) USING BTREE,
  INDEX `ind_sup`(`supplier_id`) USING BTREE,
  INDEX `ind_bus`(`merchant_id`) USING BTREE,
  INDEX `ind_pro`(`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `supplier_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '供应商id',
  `source_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '货源id',
  `product_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '货源原商品id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '价格',
  `category_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '分类ID',
  `details` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '详情',
  `status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '审核状态',
  `remarks` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `image` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL COMMENT '图片路径',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `reason` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '理由',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_sup`(`supplier_id`) USING BTREE,
  INDEX `ind_sou`(`source_id`) USING BTREE,
  INDEX `ind_cat`(`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_source
-- ----------------------------
DROP TABLE IF EXISTS `t_source`;
CREATE TABLE `t_source`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `supplier_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '供应商id',
  `source_system` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '货源系统',
  `source_website` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '货源网站地址',
  `docking_account` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '对接账号',
  `docking_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '对接秘钥',
  `connectivity` tinyint(1) NULL DEFAULT NULL COMMENT '连通性',
  `apply_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '申请理由',
  `examine` int(0) NULL DEFAULT 1 COMMENT '审核状态（1待审合，2通过，3驳回）',
  `examine_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '审核结果',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_sup`(`supplier_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '货源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_supplier
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '商家名称',
  `total_orders` int(0) NULL DEFAULT 0 COMMENT '总订单量',
  `examine` int(0) NULL DEFAULT 1 COMMENT '审核状态（1待审合，2通过，3驳回）',
  `examine_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '审核结果',
  `balance` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '余额剩余',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '供应商表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for t_withdrawal
-- ----------------------------
DROP TABLE IF EXISTS `t_withdrawal`;
CREATE TABLE `t_withdrawal`  (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `user_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '提现用户ID',
  `withdrawal_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '提现金额',
  `account_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '到账金额',
  `status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '提现状态',
  `remarks` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `image_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NULL DEFAULT NULL COMMENT '收款码',
  `free` decimal(10, 2) NULL DEFAULT NULL COMMENT '费率',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_user`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci COMMENT = '提现表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
