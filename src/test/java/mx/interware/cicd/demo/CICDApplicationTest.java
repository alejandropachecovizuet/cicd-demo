package mx.interware.cicd.demo;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import mx.interware.cicd.demo.CICDApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CICDApplicationTest {

	@Test
	public void contextLoads() {
		ApplicationContext ctx = SpringApplication.run(CICDApplication.class);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
}
