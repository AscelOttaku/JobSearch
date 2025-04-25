package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ContactInfoDto;
import kg.attractor.jobsearch.dto.mapper.impl.ContactInfoMapper;
import kg.attractor.jobsearch.repository.ContactInfoRepository;
import kg.attractor.jobsearch.service.ContactInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;
    private final ContactInfoMapper contactInfoMapper;

    @Override
    public void createContactInfo(ContactInfoDto contactInfoDto) {
        if (contactInfoDto.getValue() == null || contactInfoDto.getValue().isBlank()) {
            if (contactInfoDto.getContactInfoId() != null)
                deleteContactInfoById(contactInfoDto.getContactInfoId());

            return;
        }

        contactInfoRepository.save(contactInfoMapper.mapToEntity(contactInfoDto));
    }

    public void deleteContactInfoById(Long contactInfoId) {
        contactInfoRepository.deleteById(contactInfoId);
    }
}
