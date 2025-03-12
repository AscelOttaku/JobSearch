package kg.attractor.jobsearch.model;

import kg.attractor.jobsearch.model.enums.ContactTypes;

public class ContactInfo {
    private ContactTypes type;
    private Resume resume;
    private String value;

    public ContactInfo(ContactTypes type, Resume resume) {
        this.type = type;
        this.resume = resume;
    }
}
