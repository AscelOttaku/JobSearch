package kg.attractor.jobsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class JobSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSearchApplication.class, args);
    }

    @Bean
    public HiddenHttpMethodFilter filter() {
        return new HiddenHttpMethodFilter();
    }
}
