package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.ContactInfoDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.ContactInfo;
import kg.attractor.jobsearch.model.ContactType;
import kg.attractor.jobsearch.model.Resume;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoMapper implements Mapper<ContactInfoDto, ContactInfo> {

    private final ContactTypeMapper contactTypeMapper;

    public ContactInfoMapper(ContactTypeMapper contactTypeMapper) {
        this.contactTypeMapper = contactTypeMapper;
    }

    @Override
    public ContactInfoDto mapToDto(ContactInfo entity) {
        return ContactInfoDto.builder()
                .contactInfoId(entity.getId())
                .contactType(contactTypeMapper.mapToDto(entity.getContactType()))
                .value(entity.getContactValue())
                .resumeId(entity.getResume().getId())
                .build();
    }

    @Override
    public ContactInfo mapToEntity(ContactInfoDto dto) {
        Resume resume = new Resume();
        resume.setId(dto.getResumeId());

        ContactType contactType = new ContactType();
        contactType.setId(dto.getContactType().getContactTypeId());

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(dto.getContactInfoId());
        contactInfo.setContactType(contactType);
        contactInfo.setContactValue(dto.getValue());
        contactInfo.setResume(resume);
        return contactInfo;
    }
}
