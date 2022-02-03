package com.gft.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoProjectApplicationTests {
	private static final String bParamString = "&b=";
	@Autowired transient TestRestTemplate restTemplate;

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

	@DisplayName("test multle input values Hello method")
	@ParameterizedTest(name="{displayName} [{index})] ({arguments}) \"{0}\" ->\"{1}\" ")
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
	@DisplayName("test multle input values add method ")
	@ParameterizedTest(name="{displayName} [{index})] {0} + {1} = {2}")
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
		assertThat(restTemplate.getForObject("/add?a="+a+ bParamString +b, String.class))
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

	@DisplayName("test multle input values add mehod Float")
	@ParameterizedTest(name="{displayName} [{index})] {0} + {1} = {2}")
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
		assertThat(restTemplate.getForObject("/add?a="+a+ bParamString +b, Float.class))
				.isEqualTo(Float.parseFloat(expected));
	}

	@Test
	void addFloatException(){
		assertThrows(RestClientException.class, () -> restTemplate.getForObject("/add?a=hola&b=2", Float.class));
	}

	/* Perdida de precision en el test, se define una clase integer y el valor esperado es un float, pete error bug...
	@Test
	void addInteger(){
		assertThat(restTemplate.getForObject("/add?a=1.5&b=2", Integer.class))
				.isEqualTo(3.5f);
	}*/

	@Autowired
	private transient DemoProjectApplication app;

	@Test
	void appCanAddReturnInteger(){
		assertThat(app.add(1f,2f)).isEqualTo(3);
	}

	@Test
	void appCanAddReturnFloat(){
		assertThat(app.add(1.5f,2f)).isEqualTo(3.5f);
	}
	//Clase anidada, para indicar a junit que dentro de la clase anidad tmb hay test que pasar usamos la anotacion @nesd
	@Nested
	@DisplayName("Test in nested class AppTest")
	class AppTest {

		@Test
		void appCanAddReturnInteger(){
			assertThat(app.add(1f,2f)).isEqualTo(3);
		}

		@Test
		void appCanAddReturnFloat(){
			assertThat(app.add(1.5f,2f)).isEqualTo(3.5f);
		}

		@Test
		//Deberia lanzau un null point exception
		void appCanAddNull(){
			Exception thrown = assertThrows(NullPointerException.class, () -> app.add(null, 2f));
			assertTrue(thrown.toString().contains("NullPointerException"));
		}

	}

	@Nested
	class MultiplicationTests{

		@DisplayName("test app multiple input values multiply method")
		@ParameterizedTest(name="{displayName} [{index})] {0} * {1} = {2}")
		@CsvSource({
				"1,		2,		2",
				"1,		1,		1",
				"1.0,	1.0,	1",
				"-1,	-2,		2",
				"1.5,	2,		3",
				"1.5,	1.5,	2.25",
				"0,		2,		0"
				//"'',	1,		0" solo se realiza en el framwork del springboot
		})
		void appCanMultiplyParamNamesCsv(Float a, Float b, String expected) {
			assertThat(app.multiply(a, b).toString()).isEqualTo(expected);
		}

		@DisplayName("test multiple input values multiply method")
		@ParameterizedTest(name="{displayName} [{index})] {0} * {1} = {2}")
		@CsvSource({
				"1,		2,		2",
				"1,		1,		1",
				"1.0,	1.0,	1",
				"-1,	-2,		2",
				"1.5,	2,		3",
				"1.5,	1.5,	2.25",
				"0,		2,		0",
				"'',	1,		0"
		})
		void multiplyParamNamesCsv(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/multiply?a=" + a + bParamString + b, String.class))
					.isEqualTo(expected);
		}
	}

	@Nested
	class DivideTests {

		@DisplayName("test app multiple input values divide method")
		@ParameterizedTest(name="{displayName} [{index})] {0} / {1} = {2}")
		@CsvSource({
				"1,		2,		0.50",
				"1,		1,		1.00",
				"1.0,	1.0,	1.00",
				"-1,	-2,		0.50",
				"4,		-2,		-2.00",
				"1.5,	1.5,	1.00",
				"0,		2,		0.00"
				//"'',	1,		0" solo se realiza en el framwork del springboot
		})
		void appCanDivideParamNamesCsv(BigDecimal a, BigDecimal b, String expected) {
			assertThat(app.divide(a, b).toString()).isEqualTo(expected);
		}

		@DisplayName("test multiple input values multiply method")
		@ParameterizedTest(name="{displayName} [{index})] {0} / {1} = {2}")
		@CsvSource({
				"1,		2,		0.50",
				"1,		1,		1.00",
				"1.0,	1.0,	1.00",
				"-1,	-2,		0.50",
				"4,		-2,		-2.00",
				"1.5,	1.5,	1.00",
				"0,		2,		0.00",
				"'',	1,		0.00"
		})
		void divideParamNamesCsv(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/divide?a=" + a + bParamString + b, String.class))
					.isEqualTo(expected);
		}

		@DisplayName("Divide exception ")
		@Test
		void appCanDivideBy0(){
			Exception thrown = assertThrows(ArithmeticException.class, () -> app.divide(new BigDecimal(1), new BigDecimal(0)));
			assertTrue(thrown.toString().contains("ArithmeticException"));
		}

		@DisplayName("Divide exception by restemplate")
		@Test
		void addFloatException(){
			assertThrows(RestClientException.class, () -> restTemplate.getForObject("/divide?a=1&b=0", BigDecimal.class));
		}

	}

	@Nested
	class SubtractTests{

		@DisplayName("test app multiple input values subtract method")
		@ParameterizedTest(name="{displayName} [{index})] {0} - {1} = {2}")
		@CsvSource({
				"1,		2,		-1",
				"1,		1,		0",
				"1.0,	1.0,	0",
				"-1,	-2,		1",
				"1.5,	2,		-0.5",
				"1.5,	1.5,	0",
				"0,		2,		-2"
				//"'',	1,		0" solo se realiza en el framwork del springboot
		})
		void appCanSubtractParamNamesCsv(Float a, Float b, String expected) {
			assertThat(app.subtract(a, b).toString()).isEqualTo(expected);
		}

		@DisplayName("test multiple input values subtract method")
		@ParameterizedTest(name="{displayName} [{index})] {0} - {1} = {2}")
		@CsvSource({
				"1,		2,		-1",
				"1,		1,		0",
				"1.0,	1.0,	0",
				"-1,	-2,		1",
				"1.5,	2,		-0.5",
				"1.5,	1.5,	0",
				"0,		2,		-2",
				"'',	1,		-1"
		})
		void subtractParamNamesCsv(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/sub?a=" + a + bParamString + b, String.class))
					.isEqualTo(expected);
		}
	}

	@Nested
	class SqrtTest{

		@DisplayName("test app multiple input values sqrt method")
		@ParameterizedTest(name="{displayName} [{index})] sqrt{0}  = {2}")
		@CsvSource({
				"0,				0.00",
				"2.5,			1.58",
				"15258.5,		123.53"
				//"'',	1,		0" solo se realiza en el framwork del springboot
		})
		void appCanSqrtParamNamesCsv(BigDecimal a, BigDecimal expected) {
			assertThat(app.sqrt(a)).isEqualTo(expected);
		}

		@DisplayName("test web multiple input values sqrt method")
		@ParameterizedTest(name="{displayName} [{index})] sqrt{0}  = {2}")
		@CsvSource({
				"0,				0.00",
				"2.5,			1.58",
				"15258.5,		123.53",
				"'',			0.00"
				// solo se realiza en el framwork del springboot
		})
		void sqrtParamNamesCsv(String a,  String expected) {
			assertThat(restTemplate.getForObject("/sqrt?a=" + a ,  String.class))
					.isEqualTo(expected);
		}

		@DisplayName("Sqrt exception app ")
		@Test
		void appCanSqrtNegative(){
			Exception thrown = assertThrows(ArithmeticException.class, () -> app.sqrt(new BigDecimal(-4)));
			assertTrue(thrown.toString().contains("ArithmeticException"));
		}

		@DisplayName("Sqrt exception by restemplate")
		@Test
		void addFloatException(){
			assertThrows(RestClientException.class, () -> restTemplate.getForObject("/sqrt?a=-4", BigDecimal.class));
		}

	}

}
