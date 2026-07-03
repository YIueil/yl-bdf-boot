# 项目协作指南

## 项目定位

`yl-bdf-boot` 是一个面向 Spring Boot 应用的 Maven 多模块基础框架，统一坐标为
`cc.yiueil:*:0.0.1-SNAPSHOT`。仓库主体提供公共模型与工具、Web 返回约定、JPA
查询、安全认证、文件存储、OpenAPI，以及将这些能力组装起来的 Spring Boot
Starter；`apps/` 下是独立的示例或业务应用。

核心技术基线：

- Java 17
- Spring Boot 2.7.6
- Maven reactor
- `javax.persistence` / Spring Data JPA（不是 Jakarta Persistence）
- Springdoc OpenAPI 1.8.0、Knife4j 4.5.0
- Auth0 Java JWT 4.2.1
- Lombok
- PostgreSQL 为多数示例应用的数据源

## 仓库结构

根 `pom.xml` 只聚合以下框架模块，不聚合 `apps/`：

- `yl-bdf-boot-common`：公共工具、转换器、异常、DTO/VO、领域接口，以及
  `yl_acc` 账户权限域的 JPA 实体。
- `yl-bdf-boot-web`：MVC 公共层，包括 `ResultVo`、控制器基类、全局异常处理、
  AOP 日志/耗时切面和 `/bdf-boot/version` 接口。
- `yl-bdf-boot-swagger`：Springdoc 和 Knife4j 集成。
- `yl-bdf-boot-query`
  - `yl-bdf-boot-jpa`：基于 `EntityManager` 的通用 DAO、批处理、GUID/时间填充、
    原生 SQL 查询及自定义方言。
  - `yl-bdf-boot-jpa-query`：读取 `resources/dynamicsql/*.xml` 的动态查询能力，
    暴露 `/bdf-boot/query` 接口。
- `yl-bdf-boot-security`
  - `yl-bdf-boot-security-common`：`bdf.security` 和 `bdf.jwt` 配置模型。
  - `yl-bdf-boot-security-jwt`：JWT 拦截器、账户/角色/组织/权限服务与
    `/bdf-boot/orup` 接口。
- `yl-bdf-boot-product/yl-bdf-boot-storage`
  - `yl-bdf-boot-storage-core`：文件实体、上传下载结果、文件和 SM.MS 图床抽象。
  - `yl-bdf-boot-storage-web`：文件服务实现、仓库和 `/bdf-boot/resource` 接口。
- `yl-bdf-boot-starter`：自动配置和 Starter 的聚合模块，分为 core、query、web、
  security 与 all 五组。

`apps/` 中的应用需要单独构建：

- `yl-acc`：账户控制中心示例，使用 all starter、PostgreSQL、P6Spy 和动态 SQL。
- `yl-act/yl-act-modeler`：Activiti 6 在线流程设计器，包含较大的静态前端资源。
- `yl-dap`：all starter 的最小应用骨架。
- `yl-excalibur`：礼品码业务示例。
- `yl-pokemon`：直接组合 common/web/JPA/swagger/storage 的旧式示例。
- `yl-test`：只引用 core starter 的最小测试应用。

## 依赖和自动配置链路

推荐消费入口是 `yl-bdf-spring-boot-starter`。它直接组合 core、web、query 和
security 的 autoconfigure 模块。单项 Starter 采用
`*-spring-boot-starter` + `*-spring-boot-autoconfigure` 两层结构。

自动配置通过各 autoconfigure 模块中的
`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
注册。新增、移动或重命名配置类时，必须同步更新这个文件，否则打包成功也不会
自动生效。

主要自动配置行为：

- core 注册 `SpringContextUtils`。
- query 注册 `JpaBaseDao`、动态查询配置池、解析器、服务和控制器。
- web 注册统一异常处理、日志/耗时切面、版本接口及文件存储相关 Bean。
- security 总是加载安全配置属性；只有
  `bdf.security.type=jwt` 时才加载 JWT 拦截器和 ORUP 服务。
- 大多数框架 Bean 使用 `@ConditionalOnMissingBean`，应用可通过提供同类型 Bean
  覆盖默认实现。扩展时应保留这一可替换性。

Web starter 会传递引入 storage-web，storage-web 又依赖 JPA；security
autoconfigure 会传递引入 JWT 和动态查询模块。修改 Starter 依赖时要检查这种
传递影响，避免让轻量 Starter 意外引入数据库或 Web 能力。

## 代码和接口约定

- 主包名是 `cc.yiueil`。部分目录实际使用 `src/main/java/cc.yiueil/...`，
  Java 文件内仍声明 `package cc.yiueil...`；这是现有布局，不要在无关改动中
  批量移动。
- 框架接口通常以 `/bdf-boot` 为根路径，常量位于
  `yl-bdf-boot-common/.../general/RestUrl.java`。
- Web 接口优先返回 `ResultVo` 约定结构。旧控制器通过 `BaseController` 将其
  序列化为字符串，新代码应先保持所在模块的现有风格，避免一次改动混用两套响应
  方式。
- 公共 JPA 实体位于 `cc.yiueil.entity`，仓库接口位于
  `cc.yiueil.repository`。应用主包不在 `cc.yiueil` 下时，需要显式配置
  `@EntityScan` 和 `@EnableJpaRepositories`。
- 项目仍基于 Spring Boot 2.7 和 `javax.*` API。不要局部迁移到 Spring Boot 3
  或 `jakarta.*`；这种升级必须作为跨模块迁移处理。
- 配置属性前缀为 `bdf.storage`、`bdf.security` 和 `bdf.jwt`。新增属性时同步
  更新 `@ConfigurationProperties` 类型，并保留
  `spring-boot-configuration-processor`。
- 动态查询定义放在应用的 `src/main/resources/dynamicsql/*.xml`。SQL 使用命名
  参数，结构参考 `yl-bdf-boot-jpa-query` 内的 `example.xml`。

## 构建与验证

仓库没有 Maven Wrapper，开发环境必须提供 JDK 17 和 Maven。框架全量验证：

```bash
mvn clean verify
```

仅验证一个模块及其 reactor 依赖：

```bash
mvn -pl yl-bdf-boot-query/yl-bdf-boot-jpa-query -am test
```

在构建独立应用前，先把框架快照安装到本地仓库：

```bash
mvn clean install
mvn -f apps/yl-acc/pom.xml clean verify
mvn -f apps/yl-act/pom.xml clean verify
```

本地文件仓库发布使用根 POM 的 `snapshot` profile；`release` profile 指向 GitHub
Packages。未经明确要求不要执行部署：

```bash
mvn deploy -Psnapshot
```

当前框架 reactor 内没有 `src/test` 测试；仅 `yl-act-modeler` 和
`yl-excalibur` 有测试类。因此修改公共模块至少应完成相关模块的编译，并为新增的
非平凡行为补充单元测试。涉及数据库、文件上传、JWT 或应用启动的改动还需要相应
集成验证。

## 已知约束和风险

- `apps/yl-excalibur` 与 `apps/yl-pokemon` 的父 POM
  `cc.yiueil:yl-bdf-boot-application:0.0.1-SNAPSHOT` 不在当前仓库中；除非该父
  POM 已存在于本地/远程仓库，否则它们不能独立解析。
- `apps/yl-pokemon` 的 Spring Boot Maven Plugin `mainClass` 指向
  `cc.yiueil.DapBootApplication`，但实际入口是 `cc.yiueil.BootApplication`。
- `yl-bdf-core-spring-boot-starter` 当前 POM 没有连接到 core autoconfigure；
  不要假设单独引用它会启用 `CoreConfiguration`。
- `SimpleConfigResolver` 通过拼接配置片段组装 SQL，源码也明确标注存在 SQL 注入
  风险。不得把请求输入直接当作 SQL、列名、排序或过滤片段；值必须继续使用命名
  参数绑定。涉及该模块的安全修复应优先增加恶意输入测试。
- `CustomExceptionHandler` 会把异常栈写入 `ResultVo.stackTrace`。面向生产环境的
  改动需评估信息泄露，不能在新增接口中主动返回密钥、SQL、令牌或完整异常细节。
- 示例应用的 YAML 包含环境和数据库配置。不要提交真实口令、JWT 私钥、API Key
  或本机绝对路径；新增敏感值应从环境变量或外部配置注入。
- 测试覆盖率很低，README 也较简略。判断行为应以 POM、源码和自动配置 imports
  为准，不能只依据 README。

## 修改检查清单

1. 先确认改动属于公共库、具体能力模块、autoconfigure、Starter 还是独立应用，
   避免把业务逻辑放入 Starter。
2. 修改跨模块 API 时，用 `rg` 查找所有调用者，并检查 all starter 的传递依赖。
3. 新增自动配置 Bean 时提供合理条件注解；新增配置类时更新
   `AutoConfiguration.imports`。
4. 修改实体或 repository 时检查 `yl_acc`、`yl_inst` schema、实体扫描范围和
   数据库迁移影响。
5. 修改 REST 行为时保持 `/bdf-boot` 路径和 `ResultVo` 兼容，必要时同步 OpenAPI
   注解。
6. 运行最小相关模块的 `mvn -pl ... -am test`；提交前优先运行
   `mvn clean verify`。如果环境缺少 JDK/Maven 或外部数据库，应明确说明未执行的
   验证，而不是将静态检查描述为测试通过。
7. 保留用户已有工作区改动，不提交 `target/`、IDE 文件、凭据或运行期上传文件。
