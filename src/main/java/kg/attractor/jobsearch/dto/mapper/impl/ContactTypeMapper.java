package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.ContactTypeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.ContactType;
import org.springframework.stereotype.Service;

@Service
public class ContactTypeMapper implements Mapper<ContactTypeDto, ContactType> {

    @Override
    public ContactTypeDto mapToDto(ContactType entity) {
        return ContactTypeDto.builder()
                .contactTypeId(entity.getId())
                .type(entity.getType())
                .build();
    }

    @Override
    public ContactType mapToEntity(ContactTypeDto dto) {
        ContactType contact = new ContactType();
        contact.setId(dto.getContactTypeId());
        contact.setType(dto.getType());
        return contact;
    }
}
