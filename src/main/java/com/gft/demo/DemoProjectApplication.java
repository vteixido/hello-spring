package com.gft.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@SpringBootApplication
@RestController
public class DemoProjectApplication {

	@Generated(value="org.springframework.boot")
	public static void main(String[] args) {
		SpringApplication.run(DemoProjectApplication.class, args);
	}

	@GetMapping("/")
	public String init() {
		return "Welcome to Hello-spring";
	}
	
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/add")
	public Object add(@RequestParam(value="a", defaultValue = "0") Float a,  @RequestParam(value="b", defaultValue = "0") Float b ) {

		float sum = a+b;
		float decimal = sum - (int) sum;

		if(decimal  !=  0) {
			return sum;
		}else{
			return (int) sum;
		}
	}

	@GetMapping("/multiply")
	public Object multiply(@RequestParam(value="a", defaultValue = "0") Float a,  @RequestParam(value="b", defaultValue = "0") Float b ) {

		float product = a*b;
		float decimal = product - (int) product;

		if(decimal  !=  0) {
			return product;
		}else{
			return (int) product;
		}
	}

	@GetMapping("/sub")
	public Object subtract(@RequestParam(value="a", defaultValue = "0") Float a, @RequestParam(value="b", defaultValue = "0") Float b ) {

		float subtract = a - b;
		float decimal = subtract - (int) subtract;

		if(decimal  !=  0) {
			return subtract;
		}else{
			return (int) subtract;
		}
	}

	@GetMapping("/divide")
	public BigDecimal divide(@RequestParam(value="a", defaultValue = "0") BigDecimal a, @RequestParam(value="b", defaultValue = "0") BigDecimal b ) {

		return a.divide(b,2, RoundingMode.HALF_DOWN);

	}

	@GetMapping("/sqrt")
	public BigDecimal sqrt(@RequestParam(value="a", defaultValue = "0") BigDecimal a) {

		return a.sqrt( new MathContext(10)).setScale(2,RoundingMode.HALF_DOWN );
	}

}
