package com.uniajc.schoolpickup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.main.web-application-type=reactive")
@AutoConfigureMockMvc
public class SchoolPickupApplicationTests {

  @Test
  public void contextLoads() {}
}
