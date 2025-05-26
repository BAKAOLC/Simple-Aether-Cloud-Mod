# Simple Aether Cloud Mod

[English](README_EN.md) | 简体中文

一个简单的Minecraft模组，添加了多种云方块。原始思路来自The Aether模组，在此基础上添加了自己的特性。

- 添加了黄云、蓝云、红云、绿云和黑云方块
- 云方块可以像普通方块一样放置和破坏
- 云方块会免除摔落伤害
  
## 特性

### 黄云

- 无法穿过方块底部

### 蓝云

- 向指定方向弹射实体
- 潜行时阻止弹射并缓慢下沉
- 其他变种特性请在游戏中查看

### 红云

- 对实体造成伤害
- 潜行时免疫伤害

### 绿云

- 加速实体成长
- 加快繁殖冷却
- 吸引附近动物靠近
- 方块内的动物会定期尝试繁殖

### 黑云

- 阻挡或让实体穿过
- 被生物认作实心方块
- 阻挡状态下其上方可生成怪物
- 其他变种特性请在游戏中查看

## 安装要求

- Minecraft 1.21 ~ 1.21.1
- NeoForge 21.0.167 及以上

## 构建方法

1. 克隆仓库

```bash
git clone https://github.com/BAKAOLC/Simple-Aether-Cloud-Mod.git
```

2. 进入项目目录

```bash
cd Simple-Aether-Cloud-Mod
```

3. 构建项目

```bash
./gradlew build
```

构建完成后，你可以在 `build/libs` 目录下找到生成的模组文件。

## 许可证

本项目采用 LGPL-3.0 许可证。详见 [LICENSE](LICENSE) 文件。

## 致谢

- 部分思路参考于 [The Aether Mod](https://github.com/The-Aether-Team/The-Aether) 并重新实现
