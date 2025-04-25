package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ContactTypeDto;

import java.util.List;

public interface ContactTypeService {
    ContactTypeDto findContactByType(String contactType);

    boolean isContactTypeExist(String contactType);

    List<ContactTypeDto> findAllContactTypes();
}
