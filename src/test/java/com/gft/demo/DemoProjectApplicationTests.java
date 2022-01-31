package com.gft.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	@DisplayName(" Hello Test with veronica as value")
	@Test
	void helloTestVeronica(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/hello?name=Veronica", String.class))
				.isEqualTo("Hello Veronica!");
	}

	@Autowired TestRestTemplate restTemplate;
	@ParameterizedTest
	@ValueSource(strings = {"Javier", "Arturo", "Rodriguez", "Veronica%20"})
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
				.isEqualTo("1");
	}

	@Test
	void canAddDecimal(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1.5&b=2", String.class))
				.isEqualTo("3.5");
	}

	@DisplayName("test multle input values")
	@ParameterizedTest(name="[{index})] ({arguments}) \"{0}\" ->\"{1}\" ")
	@CsvSource({
			"a, Hello a!",
			"b, Hello b!",
			", Hello null!",
			"'', Hello World!",
			"' ', Hello  !",
			"first+last, Hello first last!"
			//"first%20last, Hello first last!"
	})
		// @CsvFileSource add csv files.
	void helloParamNamesCsv(String name, String expected) {
		assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
				.isEqualTo(expected );
	}
	@DisplayName("test multle input values")
	@ParameterizedTest(name="[{index})] {0} + {1} = {2}")
	@CsvSource({
			"1,		2,		3",
			"1,		1,		2",
			"1.0,	1.0,	2",
			"1,		-2,		-1",
			"1.5,	2,		3.5",
			"1.5,	1.5,	3",
			"0,		2,		2",
			"'',	1,		1"
	})
	void addParamNamesCsv(String a, String b, String expected) {
		assertThat(restTemplate.getForObject("/add?a="+a+"&b="+b, String.class))
				.isEqualTo(expected);
	}
	@Test
	void addExceptionJsonString() {
		assertThat(restTemplate.getForObject("/ass?a=string&b=1", String.class).indexOf("Bad Requesst"));
	}
	@Test
	void addFloat(){
		assertThat(restTemplate.getForObject("/add?a=1.5&b=2", Float.class))
				.isEqualTo(3.5f);
	}

	@DisplayName("test multle input values")
	@ParameterizedTest(name="[{index})] {0} + {1} = {2}")
	@CsvSource({
			"1,		2,		3",
			"1,		1,		2",
			"1.0,	1.0,	2",
			"1,		-2,		-1",
			"1.5,	2,		3.5",
			"1.5,	1.5,	3",
			"0,		2,		2",
			"'',	1,		1"
	})
	void addParamNamesCsvFloat(String a, String b, String expected) {
		assertThat(restTemplate.getForObject("/add?a="+a+"&b="+b, Float.class))
				.isEqualTo(Float.parseFloat(expected));
	}

	@Test
	void addFloatException(){
		Exception thrown = assertThrows(RestClientException.class, () -> {
			restTemplate.getForObject("/add?a=hola&b=2", Float.class);
		});
	}

	/* Perdida de precision en el test, se define una clase integer y el valor esperado es un float, pete error bug...
	@Test
	void addInteger(){
		assertThat(restTemplate.getForObject("/add?a=1.5&b=2", Integer.class))
				.isEqualTo(3.5f);
	}*/

}
