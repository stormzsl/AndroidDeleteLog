####### AndroidDeleteLog 基于AST抽象语法树实现删除java/android代码中的Log.*输出,主要运用在apk发布阶段

###### AST(Abstract syntax tree)抽象语法树
```text
AST是javac编译器阶段对源代码进行词法语法分析之后，语义分析之前进行的操作。
用一个树形的结构表示源代码，源代码的每个元素映射到树上的节点。
```
###### java的编译过程可以分为三个阶段:
![java编译过程](https://upload-images.jianshu.io/upload_images/751860-9add9ded278480d3.png?imageMogr2/auto-orient/strip|imageView2/2/w/600/format/webp)

```text

Java源文件->词法，语法分析-> 生成AST ->语义分析 -> 编译字节码，二进制文件。

通过操作AST可以实现java源代码的功能。

1.所有源文件会被解析成语法树。

2.调用注解处理器。如果注解处理器产生了新的源文件，新文件也要进行编译。

3.最后，语法树会被分析并转化成类文件。
```
![AST修改源文件时机](https://upload-images.jianshu.io/upload_images/751860-903e2766be21750d.png?imageMogr2/auto-orient/strip|imageView2/2/w/870/format/webp)