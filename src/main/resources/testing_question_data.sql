/*
 Navicat Premium Data Transfer

 Source Server         : xinerzi
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : 119.27.167.18:3306
 Source Schema         : fitness-testing

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : 65001

 Date: 17/08/2018 10:22:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Records of testing_question
-- ----------------------------
INSERT INTO `testing_question` VALUES ('您手脚发凉吗？', 1, 'YANGINSUFFICIENCY', '2018-08-17 02:44:41');
INSERT INTO `testing_question` VALUES ('您胃脘部、背部或腰膝部怕冷吗？', 2, 'YANGINSUFFICIENCY', '2018-08-17 02:44:49');
INSERT INTO `testing_question` VALUES ('您感到怕冷、衣服比别人穿得多吗？', 3, 'YANGINSUFFICIENCY', '2018-08-17 02:44:52');
INSERT INTO `testing_question` VALUES ('您比一般人受不了寒冷（冬天的寒冷，夏天的冷空调、电扇等。', 4, 'YANGINSUFFICIENCY', '2018-08-17 02:44:53');
INSERT INTO `testing_question` VALUES ('您比别人容易患感冒吗？', 5, 'YANGINSUFFICIENCY', '2018-08-17 02:45:56');
INSERT INTO `testing_question` VALUES ('您吃（喝）凉的东西会感到不舒服或者怕吃（喝）凉东西吗？', 6, 'YANGINSUFFICIENCY', '2018-08-17 02:46:26');
INSERT INTO `testing_question` VALUES ('你受凉或吃（喝）凉的东西后，容易腹泻（拉肚子）吗？', 7, 'YANGINSUFFICIENCY', '2018-08-17 02:46:37');
INSERT INTO `testing_question` VALUES ('您感到手脚心发热吗？', 8, 'YINDEFICIENCY', '2018-08-17 02:49:17');
INSERT INTO `testing_question` VALUES ('您感觉身体、脸上发热吗？', 9, 'YINDEFICIENCY', '2018-08-17 02:49:34');
INSERT INTO `testing_question` VALUES ('您皮肤或口唇干吗？', 10, 'YINDEFICIENCY', '2018-08-17 02:49:41');
INSERT INTO `testing_question` VALUES ('您口唇的颜色比一般人红吗？', 11, 'YINDEFICIENCY', '2018-08-17 02:49:50');
INSERT INTO `testing_question` VALUES ('您容易便秘或大便干燥吗？', 12, 'YINDEFICIENCY', '2018-08-17 02:49:57');
INSERT INTO `testing_question` VALUES ('您面部两潮红或偏红吗？', 13, 'YINDEFICIENCY', '2018-08-17 02:50:10');
INSERT INTO `testing_question` VALUES ('您感到眼睛干涩吗？', 14, 'YINDEFICIENCY', '2018-08-17 02:50:17');
INSERT INTO `testing_question` VALUES ('您活动量稍大就容易出虚汗吗？', 15, 'YINDEFICIENCY', '2018-08-17 02:50:23');
INSERT INTO `testing_question` VALUES ('你容易疲乏吗？', 16, 'FAINTPHYSICAL', '2018-08-17 02:50:46');
INSERT INTO `testing_question` VALUES ('您容易气短（呼吸短促，接不上气吗？', 17, 'FAINTPHYSICAL', '2018-08-17 02:51:06');
INSERT INTO `testing_question` VALUES ('您容易心慌吗？', 18, 'FAINTPHYSICAL', '2018-08-17 02:51:15');
INSERT INTO `testing_question` VALUES ('您容易头晕或站起时晕眩吗？', 19, 'FAINTPHYSICAL', '2018-08-17 02:51:21');
INSERT INTO `testing_question` VALUES ('您比别人容易患感冒吗？', 20, 'FAINTPHYSICAL', '2018-08-17 02:51:27');
INSERT INTO `testing_question` VALUES ('您喜欢安静、懒得说话吗？', 21, 'FAINTPHYSICAL', '2018-08-17 02:51:35');
INSERT INTO `testing_question` VALUES ('您说话声音无力吗？', 22, 'FAINTPHYSICAL', '2018-08-17 02:51:42');
INSERT INTO `testing_question` VALUES ('您一活动就容易出虚汗吗？', 23, 'FAINTPHYSICAL', '2018-08-17 02:51:49');
INSERT INTO `testing_question` VALUES ('您感到胸闷或腹部胀满吗？', 24, 'PHLEGMDAMPNESS', '2018-08-17 02:52:11');
INSERT INTO `testing_question` VALUES ('您感到身体不轻松或不爽快吗？', 25, 'PHLEGMDAMPNESS', '2018-08-17 02:52:17');
INSERT INTO `testing_question` VALUES ('您腹部肥满松软吗？', 26, 'PHLEGMDAMPNESS', '2018-08-17 02:52:27');
INSERT INTO `testing_question` VALUES ('您有额部油脂分泌多的现象吗？', 27, 'PHLEGMDAMPNESS', '2018-08-17 02:52:35');
INSERT INTO `testing_question` VALUES ('您上眼睑比别人肿（仍轻微隆起的现象）吗？', 28, 'PHLEGMDAMPNESS', '2018-08-17 02:52:47');
INSERT INTO `testing_question` VALUES ('您嘴里有黏黏的感觉吗？', 29, 'PHLEGMDAMPNESS', '2018-08-17 02:52:59');
INSERT INTO `testing_question` VALUES ('您平时痰多，特别是咽喉部总感到有痰堵着吗？', 30, 'PHLEGMDAMPNESS', '2018-08-17 02:53:07');
INSERT INTO `testing_question` VALUES ('您舌苔厚腻或有舌苔厚厚的感觉吗？', 31, 'PHLEGMDAMPNESS', '2018-08-17 02:53:14');
INSERT INTO `testing_question` VALUES ('您面部或鼻部有油腻感或者油亮发光吗？', 32, 'DAMPNESSHEAT', '2018-08-17 02:54:07');
INSERT INTO `testing_question` VALUES ('你容易生痤疮或疮疖吗？', 33, 'DAMPNESSHEAT', '2018-08-17 02:54:12');
INSERT INTO `testing_question` VALUES ('您感到口苦或嘴里有异味吗？', 34, 'DAMPNESSHEAT', '2018-08-17 02:54:18');
INSERT INTO `testing_question` VALUES ('您大使黏滞不爽、有解不尽的感觉吗？', 35, 'DAMPNESSHEAT', '2018-08-17 02:54:23');
INSERT INTO `testing_question` VALUES ('您小便时尿道有发热感、尿色浓（深）吗？', 36, 'DAMPNESSHEAT', '2018-08-17 02:54:35');
INSERT INTO `testing_question` VALUES ('您带下色黄（白带颜色发黄）吗？（限女性回答）', 37, 'DAMPNESSHEAT', '2018-08-17 02:54:47');
INSERT INTO `testing_question` VALUES ('您的阴囊部位潮湿吗？（男性回答）', 38, 'DAMPNESSHEAT', '2018-08-17 02:54:55');
INSERT INTO `testing_question` VALUES ('您的皮肤在不知不觉中会出现青紫瘀斑（皮下出血）吗？', 39, 'BLOODSTASIS', '2018-08-17 02:55:16');
INSERT INTO `testing_question` VALUES ('您两颧部有细微红丝吗？', 40, 'BLOODSTASIS', '2018-08-17 02:55:23');
INSERT INTO `testing_question` VALUES ('您身体上有哪里疼痛吗？', 41, 'BLOODSTASIS', '2018-08-17 02:55:30');
INSERT INTO `testing_question` VALUES ('您面色晦黯或容易出现褐斑吗？', 42, 'BLOODSTASIS', '2018-08-17 02:55:36');
INSERT INTO `testing_question` VALUES ('您容易有黑眼圈吗？', 43, 'BLOODSTASIS', '2018-08-17 02:55:46');
INSERT INTO `testing_question` VALUES ('您容易忘事（健忘）吗？', 44, 'BLOODSTASIS', '2018-08-17 02:55:57');
INSERT INTO `testing_question` VALUES ('您口唇颜色偏黯吗？', 45, 'BLOODSTASIS', '2018-08-17 02:56:04');
INSERT INTO `testing_question` VALUES ('您没有感冒时也会打喷嚏吗？', 46, 'TEBING', '2018-08-17 02:56:20');
INSERT INTO `testing_question` VALUES ('您没有感冒时也会鼻塞、流鼻涕吗？', 47, 'TEBING', '2018-08-17 02:56:27');
INSERT INTO `testing_question` VALUES ('您有因季节变化、温度变化或异味等原因而咳喘的现象吗？', 48, 'TEBING', '2018-08-17 02:56:34');
INSERT INTO `testing_question` VALUES ('您容易过敏（对药物、食物、气味、花粉或在季节交替、气候变化时）吗？', 49, 'TEBING', '2018-08-17 02:56:40');
INSERT INTO `testing_question` VALUES ('您的皮肤容易起荨麻疹（风团、风疹块、风疙瘩）吗？', 50, 'TEBING', '2018-08-17 02:56:51');
INSERT INTO `testing_question` VALUES ('您的因过敏出现过紫癜（紫红色瘀点、瘀斑）吗？', 51, 'TEBING', '2018-08-17 02:56:58');
INSERT INTO `testing_question` VALUES ('您的皮肤一抓就红，并出现抓痕吗？', 52, 'TEBING', '2018-08-17 02:57:05');
INSERT INTO `testing_question` VALUES ('您感到闷闷不乐吗？', 53, 'QISTAGNATION', '2018-08-17 02:57:23');
INSERT INTO `testing_question` VALUES ('您容易精神紧张、焦虑不安吗？', 54, 'QISTAGNATION', '2018-08-17 02:57:29');
INSERT INTO `testing_question` VALUES ('您多愁善感、感情脆弱吗？', 55, 'QISTAGNATION', '2018-08-17 02:57:54');
INSERT INTO `testing_question` VALUES ('您容易感到害怕或受到惊吓吗？', 56, 'QISTAGNATION', '2018-08-17 03:00:14');
INSERT INTO `testing_question` VALUES ('您胁肋部或乳房腹痛吗？', 57, 'QISTAGNATION', '2018-08-17 03:00:28');
INSERT INTO `testing_question` VALUES ('您无缘无故叹气吗？', 58, 'QISTAGNATION', '2018-08-17 03:00:37');
INSERT INTO `testing_question` VALUES ('您咽喉部有异物感，且吐之不出、咽之不下吗？', 59, 'QISTAGNATION', '2018-08-17 03:00:43');
INSERT INTO `testing_question` VALUES ('您精力充沛吗？', 60, 'MILDPHYSICAL', '2018-08-17 03:01:39');
INSERT INTO `testing_question` VALUES ('您容易疲乏吗？', 61, 'MILDPHYSICAL', '2018-08-17 03:01:45');
INSERT INTO `testing_question` VALUES ('您说话声音无力吗？', 62, 'MILDPHYSICAL', '2018-08-17 03:01:54');
INSERT INTO `testing_question` VALUES ('您感到闷闷不乐吗？', 63, 'MILDPHYSICAL', '2018-08-17 03:02:00');
INSERT INTO `testing_question` VALUES ('您比一般 人耐受不了寒冷（冬天的寒冷，夏天的冷空调、电扇）吗？*', 64, 'MILDPHYSICAL', '2018-08-17 03:02:07');
INSERT INTO `testing_question` VALUES ('您能适应外界自然和社会环境的变化吗？', 65, 'MILDPHYSICAL', '2018-08-17 03:02:15');
INSERT INTO `testing_question` VALUES ('您容易失眠吗？', 66, 'MILDPHYSICAL', '2018-08-17 03:02:21');
INSERT INTO `testing_question` VALUES ('您容易忘事（健忘）吗？', 67, 'MILDPHYSICAL', '2018-08-17 03:02:28');

SET FOREIGN_KEY_CHECKS = 1;
