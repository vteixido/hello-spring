package com.gft.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class DemoProjectApplication {

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

		Float sum = a+b;
		Float decimal = sum - sum.intValue();

		if(decimal  !=  0) {
			return sum;
		}else{
			return Integer.valueOf(sum.intValue());
		}
	}

	@GetMapping("/multiply")
	public Object multiply(@RequestParam(value="a", defaultValue = "0") Float a,  @RequestParam(value="b", defaultValue = "0") Float b ) {

		Float product = a*b;
		Float decimal = product - product.intValue();

		if(decimal  !=  0) {
			return product;
		}else{
			return Integer.valueOf(product.intValue());
		}
	}
}
