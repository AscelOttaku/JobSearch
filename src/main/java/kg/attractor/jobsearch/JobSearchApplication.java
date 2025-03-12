package kg.attractor.jobsearch;

import kg.attractor.jobsearch.model.*;
import kg.attractor.jobsearch.model.enums.ContactTypes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JobSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSearchApplication.class, args);
    }

    @Bean
    protected RespondedApplications createRespondedApplications(Resume resume, Vacancy vacancy) {
        return new RespondedApplications(resume, vacancy);
    }

    @Bean
    protected ContactInfo createContactInfo(Resume resume) {
        ContactTypes contactType = ContactTypes.TEST;
        return new ContactInfo(contactType, resume);
    }
}
