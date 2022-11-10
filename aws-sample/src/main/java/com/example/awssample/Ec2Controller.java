package com.example.awssample;

import java.io.*;
import java.net.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Ec2Controller {

  @RequestMapping("/ec2")
  String ec2(Model model) {
    String ec2Id = getMetadata();
    System.out.println(ec2Id);
    model.addAttribute("ec2id", getMetadata());
    return "ec2";
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
}
