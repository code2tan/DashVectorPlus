# DashVectorPlus

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5+-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

DashVectorPlus 是基于阿里云 DashVector Java SDK 的增强封装，提供更便捷的 Spring Boot 集成和注解驱动的开发体验。通过简单的注解配置，即可快速构建向量数据库应用。

## ✨ 特性

- **Spring Boot 自动配置** - 零配置快速集成
- **注解驱动开发** - 使用注解定义 Collection 和字段
- **自动初始化** - 应用启动时自动创建 Collection 和 Partition
- **便捷工具类** - 丰富的工具类简化开发
- **缓存机制** - Collection 本地缓存管理
- **重试机制** - 内置重试和异常处理
- **监控支持** - 可配置的日志和性能监控

## 📦 模块结构

```
DashVectorPlus/
├── dash-vector-plus-parent/ # 父模块，管理依赖版本
├── dash-vector-plus-core/ # 核心功能模块
├── dash-vector-plus-boot-starter/ # Spring Boot 启动器
└── dash-vector-plus-example/ # 使用示例
```

## 🚀 快速开始

### 1. 添加依赖

在你的 Spring Boot 项目中添加依赖：

```xml
<dependency>
    <groupId>code2t.com</groupId>
    <artifactId>dash-vector-plus-boot-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```

### 2. 配置参数

在 `application.yml` 中配置 DashVector 连接信息：

```yaml
dvp:
  banner: true
  api-key: your-api-key
  endpoint: your-endpoint
  packages-scan:
    - your.package.domain
```

### 3. 定义数据模型

使用注解定义你的 Collection：

```java
@Data
@DVCollection(name = "user_collection", dimension = 1024)
@DVPartition(name = {"default"})
public class User {
    
    @DVFieldSchema(name = "user_id")
    private Long userId;
    
    @DVFieldSchema(name = "user_name")
    private String userName;
    
    @DVFieldSchema(name = "user_vector")
    private List<Float> userVector;
    
    @DVFieldSchema(name = "create_time")
    private Long createTime;
}
```

### 4. 注入服务

在你的服务类中使用：

```java
@Service
public class UserService {
    
    @Resource
    private DashVectorInit dashVectorInit;
    
    @Resource
    private CollectionManageService collectionManageService;
    
    @Resource
    private PartitionManageService partitionManageService;
}
```

## 📚 注解说明

### @DVCollection

用于定义 Collection 的基本信息：

```java
@DVCollection(
    name = "collection_name",           // Collection 名称
    dimension = 1024,                   // 向量维度
    dataType = DataType.FLOAT,          // 数据类型
    metric = Metric.cosine,             // 距离度量
    timeout = -1,                       // 超时时间
    embeddingModel = "text-embedding",  // 嵌入模型
    enableDynamicField = true           // 是否启用动态字段
)
```

### @DVFieldSchema

用于定义字段的 Schema：

```java
@DVFieldSchema(
    name = "field_name",                // 字段名称
    fieldType = FieldType.STRING,       // 字段类型
    require = true                      // 是否必填
)
```

### @DVPartition

用于定义 Partition：

```java
@DVPartition(name = {"partition1", "partition2"})
```

## 🛠️ 配置选项

| 配置项 | 类型 | 默认值 | 说明 |
|--------|------|--------|------|
| `dvp.banner` | boolean | true | 是否显示启动横幅 |
| `dvp.api-key` | string | - | DashVector API Key |
| `dvp.endpoint` | string | - | DashVector 服务端点 |
| `dvp.packages-scan` | list | - | 需要扫描的包路径 |

## 🛠️ 核心服务

### CollectionManageService

Collection 管理服务，提供 Collection 的增删改查操作：

```java
// 创建 Collection
Boolean createCollection(String name, int dimension);

// 删除 Collection
Boolean deleteCollection(String name);

// 查询 Collection
CollectionInfo describeCollection(String name);

// 列出所有 Collection
List<CollectionInfo> listCollections();
```

### PartitionManageService

Partition 管理服务，提供 Partition 的增删改查操作：

```java
// 创建 Partition
Boolean createPartition(String collectionName, String partitionName);

// 删除 Partition
Boolean deletePartition(String collectionName, String partitionName);

// 查询 Partition
PartitionInfo describePartition(String collectionName, String partitionName);
```

## 📚 使用示例

完整的使用示例请参考 `dash-vector-plus-example` 模块：

```java
@SpringBootApplication
public class DashVectorExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(DashVectorExampleApplication.class, args);
    }
}
```

## 🔍 开发计划

### ✅ 已实现功能

- Spring Boot 自动配置
- 注解驱动开发
- Collection 和 Partition 管理
- 基础工具类和缓存机制

### 🚧 开发中功能

- Document 操作服务
- 高级查询功能
- 批量操作优化

### 🔜 计划功能

- 错误处理和重试机制
- 性能监控和指标收集
- 完整的 API 文档

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系方式

- 项目主页：[GitHub Repository](https://github.com/your-username/DashVectorPlus)
- 问题反馈：[Issues](https://github.com/your-username/DashVectorPlus/issues)

---

⭐ 如果这个项目对你有帮助，请给它一个星标！

