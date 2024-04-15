package a5_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Application class is the entry point for the Spring Boot application for this project
 * It initializes and runs the application using Spring Boot's SpringApplication.run method
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}