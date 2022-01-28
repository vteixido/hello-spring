package com.gft.demo;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void rooTest(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/", String.class))
				.isEqualTo("Welcome to Hello-spring");
	}

	@Test
	void helloTest(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/hello", String.class))
				.isEqualTo("Hello World!");
	}

	@Test
	void helloTestVeronica(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/hello?name=Veronica", String.class))
				.isEqualTo("Hello Veronica!");
	}

	@Test
	void canAdd(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class))
				.isEqualTo("3");
	}

}
