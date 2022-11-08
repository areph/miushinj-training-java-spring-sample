### SDKMANのインストール

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
spring init --build=maven --java-version=11 --dependencies=web aws-sample
```

### Spring Controller 作成

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
        return "Hello World!";
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
```

### Spring 起動

```shell
cd aws-sample
sh mvnw spring-boot:run
```