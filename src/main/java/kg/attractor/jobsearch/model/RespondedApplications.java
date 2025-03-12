package kg.attractor.jobsearch.model;

public class RespondedApplications {
    private Resume resume;
    private Vacancy vacancy;
    private boolean confirmation;

    public RespondedApplications(Resume resume, Vacancy vacancy) {
        this.resume = resume;
        this.vacancy = vacancy;
    }
}
