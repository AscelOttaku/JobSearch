package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ContactTypeDto;

public interface ContactTypeService {
    ContactTypeDto findContactByType(String contactType);

    boolean isContactTypeExist(String contactType);

    Long createContactType(ContactTypeDto contactTypeDto);
}
