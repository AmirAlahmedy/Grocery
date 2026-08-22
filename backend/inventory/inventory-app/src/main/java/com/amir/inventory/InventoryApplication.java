package com.amir.inventory;

import com.amir.inventory.reactive.controller.ProductController;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;


@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Bean
	public ApplicationRunner inspectBeans(ApplicationContext ctx) {
		return args -> {
			// Check if ProductController bean exists
			boolean controllerPresent = ctx.getBeanNamesForType(ProductController.class).length > 0;
			System.out.println("[DIAG] ProductController bean present: " + controllerPresent);

			// List handler-mapping related beans and their classes
			String[] interesting = Arrays.stream(ctx.getBeanDefinitionNames())
				.filter(n -> n.toLowerCase().contains("handlermapping") || n.toLowerCase().contains("handleradapter") || n.toLowerCase().contains("requestmapping"))
				.toArray(String[]::new);
			System.out.println("[DIAG] Handler-mapping beans: " + Arrays.toString(interesting));
			for (String name : interesting) {
				Object b = ctx.getBean(name);
				System.out.println("[DIAG] " + name + " -> " + b.getClass().getName());
			}

			// Print all request mappings registered by RequestMappingHandlerMapping
			try {
				RequestMappingHandlerMapping mapping = ctx.getBean(RequestMappingHandlerMapping.class);
				mapping.getHandlerMethods().forEach((info, method) -> {
					System.out.println("[DIAG-MAP] " + info + " -> " + method.toString());
				});
			} catch (Exception e) {
				System.out.println("[DIAG] Could not obtain RequestMappingHandlerMapping: " + e.getMessage());
			}
		};
	}

}
