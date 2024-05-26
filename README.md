# Assignment 1

## Restful API development with Spring boot and Spring MVC

### 目录

1. 完成情况
2. 系统整体设计
    1. 用例图
    2. 接口设计
        - 默认环境
        - UserHandler
        - ItemHandler
    3. 数据库设计
3. 测试截图
    1. JUnit 
        - 单元测试
        - 集成测试
    2. Postman
4. 系统截图&功能实现
    - 管理员
    - 普通用户
    - 会话控制
    - 日志
    - 缓存
    - 速率限制
    - OpenAPI文档
    - Spring WebFlux API 实现

---

### 1. 完成情况

**Basic Requirements:**

-	Define and document a set of Restful APIs which can either be in Json or OpenAPI doc.
-	Implement the API in Java with Spring MVC and boot.
-	Implement the data repository layer with at least RDBMS.
-	Testing with boot testing framework and Web API testing.
-	API authentication and if necessary additional web UIs.


**Credit Implementations:**

- Caching
- Session Control
- Log
- Rate Limiting

本次作业上述基本要求和额外要求均已完成。
本次作业采用前后端分离技术，前端使用Vue技术，后端使用Spring技术，目标是设计并实现一套基于RESTful架构的API，通过OpenAPI文档进行定义和记录。
我们使用Java语言配合Spring MVC和Spring Boot框架完成了API的开发，确保了系统的高效运行和易于维护。数据仓库层面，我们选择了关系型数据库管理系统（MySQL）来存储和管理数据。在测试方面，我们利用Spring Boot的测试框架和Web API进行了全面的测试，具体使用Spring Boot的测试框架JUnit进行单元测试和集成测试，Web API测试使用Postman进行调试，确保了API的可靠性和稳定性。对于API authentication部分，我们通过使用Spring Security的鉴权功能，使普通用户（USER）和管理员（ADMIN）登录后进入不同界面，API的安全性通过认证机制得到了加强。我们通过Vue添加了额外的Web界面来提供更多的交互功能。
此外，为了提升系统性能和用户体验，结合上课所学内容，我们还增加了一些高级功能，如使用Redis进行数据缓存，提高了系统性能；使用JSON Web Token（JWT）实现会话控制功能，包括认证身份令牌、会话过期验证等；增加日志功能，使用SLF4J（Simple Logging Facade for Java）对系统内数据的增删改查等一切操作进行记录，并且在filter内获取token内含有的用户id信息（username）进行session id的认证，实现用户级的信息监控，在控制台输出操作信息并存储到本地文件夹；增加每个用户对API的访问频率限制，使用 Bucket4j 库实现的速率限制（Rate Limiting）拦截器，用于本系统的应用中。这些额外功能实现不仅提高了API的响应速度和并发处理能力，也增强了系统的安全性和可监控性，同时也加深了我们对课上所学知识的理解。


### 2. 系统整体设计

#### 2.1 用例图

本仓库管理系统满足两类用户的需求：管理员和普通用户。系统允许用户登录，并在登录过程中遇到问题时显示错误信息。登录后，用户可以浏览物品列表以获得库存概览，并可以查看单个物品的详细信息。此外，管理员可以更新物品详情，以反映库存变动或信息更正。如果有必要，管理员还可以删除物品记录。为了库存的扩充，管理员也能够创建新的物品记录。管理员在拥有普通用户所有功能的基础上，还具有用户管理的特权，包括添加新用户、修改现有用户权限和删除用户等功能。这些功能使管理员能够保持系统用户的有效管理，确保每个用户都能够访问对应的系统功能。通过这样的系统设计，管理员和用户能够确保库存数据的准确性，满足日常的仓库管理需求。
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/4a03bad2-c2b0-4e0a-bee5-d05f8f41a74b)

#### 2.2 接口设计

**默认环境**

| 参数名 | 字段值 |
|-------|--------|
| baseUrl | http://localhost:8080 |

##### 2.2.1 UserHandler

1. 获取所有用户

    **GET /users**

    **请求:**

    | 方法 | 路径 | 参数 | 描述 |
    |------|------|------|------|
    | GET | /users | 无 | 获取系统中的所有用户列表 |

    **响应体：**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     | FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

2. 创建新用户

    **POST /users**

    **请求:**

    | 方法 | 路径 | 参数 | 描述 |
    |------|------|------|------|
    | POST | /users | User 对象 | 在系统中创建一个新用户 |

    **响应体**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     | FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

3. 删除用户

    **DELETE /users/{id}**

    **请求:**

    | 方法 | 路径 | 参数 | 描述 |
    |------|------|------|------|
    | DELETE | /users/{id} | id (路径变量) | 删除指定ID的用户 |

    **响应体**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     | FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

4. 更新用户信息

    **PUT /users/{id}**

    **请求:**

    | 方法 | 路径 | 参数 | 描述 |
    |------|------|------|------|
    | PUT | /users/{id} | id (路径变量) User 对象 | 更新指定ID的用户信息 |

    **响应体**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     | FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

5. 用户登录

    **POST /users/login**

    **请求:**

    | 方法 | 路径 | 参数 | 描述 |
    |------|------|------|------|
    | POST | /users/login | LoginRequest 对象 | 用户登录，验证凭证 |

    **响应体**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     | FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

##### 2.2.2 ItemHandler

1. 获取所有商品

    **GET /items**

    **请求:**

    | 方法 | URL | 参数 | 描述 |
    |------|------|------|------|
    | GET | /items | 无 | 获取所有商品的列表 |

    **响应体**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     |

 FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

2. 创建新商品

    **POST /items**

    **请求:**

    | 方法 | URL | 参数 | 描述 |
    |------|------|------|------|
    | POST | /items | Item 对象 | 创建新的商品记录 |

    **响应体**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     | FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

3. 删除商品

    **DELETE /items/{id}**

    **请求:**

    | 方法 | URL | 参数 | 描述 |
    |------|------|------|------|
    | DELETE | /items/{id} | id (路径变量) | 删除指定ID的商品记录 |

    **响应体**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     | FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

4. 更新商品信息

    **PUT /items/{id}**

    **请求:**

    | 方法 | URL | 参数 | 描述 |
    |------|------|------|------|
    | PUT | /items/{id} | id (路径变量) Item 对象 | 更新指定ID的商品信息 |

    **响应体**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     | FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

5. 根据ID获取商品

    **GET /items/{id}**

    **请求:**

    | 方法 | URL | 参数 | 描述 |
    |------|------|------|------|
    | GET | /items/{id} | id (路径变量) | 获取指定ID的商品详细信息 |

    **响应体**

    200 响应数据格式：JSON

    | 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
    |----------|------|---------|---------|------|
    | data     | object |     | FALSE |      |
    | flag     | boolean |     | FALSE |      |
    | msg      | string  |     | FALSE |      |
    | statusCode | int32 | 200 | FALSE | the http code 200、404 and so on |

#### 2.3 数据库设计

**（1）物理模型：**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/c0f41928-ac81-435b-9e9c-05cbad7f86fe)

**（2）逻辑模型：**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/bcb60a98-72be-45ba-8ae6-641a48457892)

---

### 3. 测试截图

#### 3.1 JUnit

**3.1.1 单元测试**

ItemServiceTest：测试用例全部通过
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/d6202834-4c09-44c9-b946-9e28f0022034)

UserServiceTest：测试用例全部通过
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/c539d332-d94f-436b-9469-874a38243af2)

**3.1.2 集成测试**

ItemHandlerTest：测试用例全部通过
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/8d68ae72-1ca5-43c3-a750-107a29a908f4)

UserHandlerTest：测试用例全部通过
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/5315dd6f-b9ca-48a5-a58e-4d02ac873832)

#### 3.2 Postman

**(1) 登录功能**

登录成功：
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/472197dc-d72f-41fa-856b-c6dd46054b30)

登录失败：
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/db9f4cea-2931-4277-91ac-6cb1b7dbccc3)

**(2) 查看物品清单**

登录成功并获得token后，进行查看物品清单：
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/28acc5c3-d639-43fd-acbd-66538330e1d3)

没有获得token时获取不到信息：
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/6262b7ca-37e1-44c3-a07d-4cff4f6d9334)

**(3) 测试增加物品**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/e69832f1-1863-47bc-b526-fb410f679243)
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/6241a187-3fc7-47a4-b8a4-1cb16c93adc3)

**(4) 测试删除刚刚添加的信息**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/4459fefd-71d2-4f8c-816e-b0bc899a9fac)
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/008ccf64-c822-44b1-852a-d0e90d05bb28)

**(5) 测试修改某条数据信息**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/532fcaab-c1d6-4e2b-b13e-4503fbc0c6f1)
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/c05c6c7d-5b3e-46e0-a32b-9698e864f03d)

**(6) 查找某条具体信息**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/1cce5063-b7f6-40ed-b497-399a72d662cd)

**(7) 权限测试**

该系统为普通用户和管理员用户设置了不同的权限，增删改功能均只对管理员开放，查找所有和单个物品信息则开放给全体用户，如果普通用户尝试进行增删改操作，则会被系统拒绝：
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/41cbfb45-a36b-4d2b-9ba6-de8de56b9bff)

但是可以进行查看信息：
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/84a8f4d0-e54e-4f40-90ac-1be9012e61ae)

**(8) 测试速率限制**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/8203fedd-8242-4f5c-b56b-62546c50ff40)

当请求超过一定次数时，会限制访问
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/3ac3ebd5-72a6-43fb-81f4-ed88eda8d7e4)

---

### 4. 系统截图&功能实现

#### 4.1 管理员

**（1）管理员登录：**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/14af909e-da21-4d7f-8805-693209b99784)

**（2）管理员界面：（可以进行增删改）**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/5ed97b2f-8e2e-4514-947e-15e707bd2fbe)

**（3）增加商品：**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/f320ff55-5cea-43fb-8ddf-22136e2a1c07)

**（4）查看单个信息：**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/bcbd3ff7-2a06-4412-a6d7-fd4525e5742c)

**（5）更改单个信息：**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/fefd13a6-4045-45f5-bfa0-c1f02fe95632)

#### 4.2 普通用户

**（1）普通用户登录：**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/cf1ef3f8-4e36-46d1-a490-5afc85d1eefe)

**（2）只能查看信息：**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/d60edcbb-2fb8-42fa-97bd-e6ee4207e4ae)
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/0911ebe6-17ee-48f8-99a4-eae09d533ad2)

#### 4.3 会话控制

当用户在设定的token有效期内没有操作时，系统会提示用户重新登录（会话过期）
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/5943a21c-01bb-4b90-b867-e12ad75d7713)

#### 4.4 日志

系统设置了Log来记录对数据的操作，将所有信息输出到控制台中同时保存到log日志文件内包括：访问时间，对访问接口的记录，登陆用户的信息、权限记录，访问接口的返回结果
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/5e26f791-ff2d-4242-b2f9-1cc15b11f1ac)
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/90679db9-4696-4e6a-9884-4e40c06ec84c)

#### 4.5 缓存

当用户首次登录时，会从数据库中获取所有物品信息，并将allItems存在缓存中；当查看某一物品信息时，会将该物品的查看信息(item:id)存在缓存；当删除某一物品信息时，会删除缓存中的allItems和其对应的item:id；当创建物品时，会删除缓存中的allItems并讲该物品信息(item:id)存在缓存；当更新某一物品信息时，会删除allItems同时更新缓存中对应的的item:id；当用户切换账号登录时，缓存内容会被清空
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/01365c56-76fb-499b-96e5-c38766d8acad)
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/8f1225e6-47f1-4c94-a6d0-f249bafe9c2b)

#### 4.6 速率限制

使用拦截器并利用Bucket4j库实现速率限制，当对某一接口的请求超过一定次数时，会限制访问
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/06947678-fffa-4718-a68f-79ae2935a7fc)

#### 4.7 OpenAPI文档

**（1） Item API**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/d2702b90-2514-4282-836d-8d5c22270a28)

**（2） User API**
![image](https://github.com/ieeemass2024/warehouse/assets/108800596/de28ecc7-1c32-4604-b887-b11dc2bb87d4)

