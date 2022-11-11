## (1)EC2インスタンスを起動してSpringを起動する

### SessionManagerでEC2へログイン

`sudo su - ec2-user` でユーザーを切り替える

### Javaをインストール

```shell
sudo yum install java-11-amazon-corretto -y
java -version
```

### SDKMANのインストール

Spring CLIのインストールに必要

ref: [Installation - SDKMAN! the Software Development Kit Manager](https://sdkman.io/install)

```shell
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

### Spring Bootのインストール

ref: [Getting Started](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.introducing-spring-boot)

```shell
sdk install springboot
```

### Spring Web プロジェクト作成

```shell
spring init --build=maven --java-version=11 --dependencies=web,thymeleaf aws-sample
cd aws-sample
ls -la
```

### Spring Controller を修正してWebからアクセスできるようにする

```shell
vim src/main/java/com/example/awssample/DemoApplication.java
```

```java
package com.example.awssample;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class DemoApplication {

    @RequestMapping("/")
    String hello() {
        return "Hello AWS!";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

### Spring 起動

```shell
sh mvnw spring-boot:run
```

初回は少し時間がかかるのでその間にEC2のパブリックIPアドレスをチェック


----

## (2)AWS Cloud 9 で Java 開発しながら Spring を起動する

Java を便利に開発するための機能を有効化するためにインスタンスタイプのスペックを上げておきましょう

### Java のバージョンを確認

すでにJavaがインストールされている

```shell
java --version
```

### (1)と同様に SDKMAN, Spring Boot をインストール

```shell
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install springboot
```

### (1)と同様に Spring Web プロジェクト作成

```shell
spring init --build=maven --java-version=11 --dependencies=web,thymeleaf aws-sample
cd aws-sample
ls -la
```

### IDE で Spring Controller を修正してWebからアクセスできるようにする

`aws-sample` ディレクトリのサンプルコードを参照

----

## (3)Spring Web Application を EC2 上にデプロイ

### EC2 を起動するユーザーデータに GitHub からソースコードを取得して Spring を起動するスクリプトを記述

```
#!/bin/bash
sudo yum update
sudo yum install git -y
sudo yum install java-11-amazon-corretto -y
git clone https://github.com/areph/miushinj-training-java-spring-sample.git
cd miushinj-training-java-spring-sample/aws-sample
sh mvnw spring-boot:run
```

`http://${EC2 のパブリックIPアドレス}/ec2:8080` へアクセス

## (4)Spring Web Application の可用性を高めるためにロードバランサー&オートスケーリング構成

- すでに AutoScaling が設定されているので、希望する容量を 4 に設定すると EC2 が 4 台起動する