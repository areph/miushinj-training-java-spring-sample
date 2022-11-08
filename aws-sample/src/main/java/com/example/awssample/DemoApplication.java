package com.example.awssample;

import java.io.*;
import java.net.*;
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

    @RequestMapping("/ec2")
    String ec2() {
        return "インスタンスID：" + getMetadata();
    }

    private String getMetadata() {
        String result = "";
        try {
            URL url = new URL("http://169.254.169.254/latest/meta-data/instance-id");
            InputStream inputStream = url.openStream();
            byte[] b = new byte[8];
            while (inputStream.read(b) != -1) {
                result += new String(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
