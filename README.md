## smallchat

这是源于 Java 课程大作业而设计的一个聊天软件. 在完成任务的同时, 对实现做了较好的设计, 实现了看起来较为优雅的解耦.

### 使用到的 Java 库

- JDBC:sqlite
- swing
- Socket
- Thread
- Reflection

### 设计思路

首先考虑的是将底层的网络通信与高层的业务逻辑解耦. 因为简单的聊天软件所需的网络通信是几近于重复而固定的编码, 我们最好将其剥离出来.

因此我们设计了 Client 类, 这个类封装了 Socket 和 ObjectStream, 提供 send() 方法实现发送对象, receive() 方法阻塞地接收对象.
事实上, 我们通过 ReceivingThread 来轮询 receive(), 并提供回调接口实现 onReceive 和 onClose 事件.

我们又同时设计了 RequestHandler 类, 封装了具体的业务逻辑, 并通过 Client 提供的回调接口和反射将请求映射到具体方法.

此时, 我们的 Server 便能够简单地封装 ServerSocket 和 accept() 方法, 十分清爽.

当 Client 对象通过登录流程, 它将得到 User 对象的引用, 即可实现你所想要的业务逻辑.

至此, 服务器端设计结束了, 我们开始设计客户端, 因为要求使用 GUI, 我们想做到 Model 和 View 的分离, 但放在具体场景下, Login/Register/Main 界面我们并未如此处理.
对私聊(Chat)和群聊(Room), 我们做了相应的分离, 以私聊为例, 实现了 Chat 和 FrmChat 类, 分别是我们心目中的 Model 和 View.

我们在客户端也复用了 Client 类, 因为我们没必要重复编码它.

服务器端和客户端交互所用的数据类均继承自 Action 类, Action 类实现了 Serializable 接口. 具体内容与业务逻辑有关, 不赘述.