package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.dtos.CompanyOKRDetails;
import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.enumeration.OkrStatus;
import hr.maps.goalmanagment.mappers.CompanyOkrMapper;
import hr.maps.goalmanagment.persistence.entities.CompanyOKR;
import hr.maps.goalmanagment.persistence.repositories.CompanyOKRRepository;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author Rokaya
 * @Date 09/04/2023
 */
@Service
@RequiredArgsConstructor
public class CompanyOKRService {
  private final CompanyOKRRepository companyOKRRepository;
  private final CompanyOkrMapper companyOkrMapper;


  public PageDto<CompanyOKRDetails> getPagedCompanyOKR(int page,int offset) {
    Pageable pageable = PageRequest.of(page, offset, Sort.by("createdAt").ascending());
    Page<CompanyOKR> companyOKRS = this.companyOKRRepository.findAll(pageable);
    return new PageDto<CompanyOKRDetails>(companyOKRS.getContent().stream().map(companyOkrMapper::toCompanyOKRDetails).collect(Collectors
        .toList()),companyOKRS.getTotalElements());
  }

  public void saveCompanyOKR(CompanyOKRDetails companyOKRRequest) {
    CompanyOKR companyOKR;
    if (companyOKRRequest.getCampanyOkrUuid()==null) {
      companyOKR = new CompanyOKR();
      companyOKR.setCampanyOkrDescription(companyOKRRequest.getCampanyOkrDescription());
      companyOKR.setCampanyOkrName(companyOKRRequest.getCampanyOkrName());
      companyOKR.setStartDate(companyOKRRequest.getStartDate());
      companyOKR.setEndDate(companyOKRRequest.getEndDate());
      companyOKR.setCompanyOkrStatus(OkrStatus.IN_PROGRESS);


    }else{
      companyOKR=companyOKRRepository.findByUuid(companyOKRRequest.getCampanyOkrUuid()).orElse(null) ;
      companyOKR.setCampanyOkrDescription(companyOKRRequest.getCampanyOkrDescription());
      companyOKR.setCampanyOkrName(companyOKRRequest.getCampanyOkrName());
      companyOKR.setStartDate(companyOKRRequest.getStartDate());
      companyOKR.setEndDate(companyOKRRequest.getEndDate());

    }
    companyOKRRepository.save(companyOKR);

  }
  public CompanyOKR getCompanyOKRByUUID(UUID companyOkrUuid) {
    return this.companyOKRRepository.findByUuid(companyOkrUuid).orElse(null);
  }
  public void deleteCompanyOKR(UUID companyOkrUuid) {
    CompanyOKR  companyOKR = getCompanyOKRByUUID(companyOkrUuid);
    this.companyOKRRepository.deleteById(companyOKR.getId());
  }

  public void archiveCompanyOKR(UUID companyOkrUuid) {
    CompanyOKR  companyOKR = getCompanyOKRByUUID(companyOkrUuid);
    companyOKR.setCompanyOkrStatus(OkrStatus.ARCHIVED);
    this.companyOKRRepository.save(companyOKR);
  }

  public CompanyOKRDetails findCompanyOKRByUUID(UUID companyOkrUuid) {
    return companyOkrMapper.toCompanyOKRDetails(getCompanyOKRByUUID(companyOkrUuid));
  }
}
