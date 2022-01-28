package com.gft.demo;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

	@Autowired TestRestTemplate restTemplate;
	@ParameterizedTest
	@ValueSource(strings = {"Javier", "Arturo", "Rodriguez", "Veronica+1"})
	void helloTestParamNames(String name) {
		assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
				.isEqualTo("Hello " + name + "!" );
	}

	@Test
	void canAdd(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class))
				.isEqualTo("3");
	}

	@Test
	void canAddZero(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=0&b=2", String.class))
				.isEqualTo("2");
	}

	@Test
	void canAddNegative(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=-2", String.class))
				.isEqualTo("-1");
	}

	@Test
	void canAddNullA(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=&b=2", String.class))
				.isEqualTo("2");
	}

	@Test
	void canAddNullB(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=", String.class))
				.isEqualTo("2");
	}

	@Test
	void canAddDecimal(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class))
				.isEqualTo("2");
	}

}
