package hr.maps.goalmanagment.controller;

import hr.maps.goalmanagment.dtos.CompanyOKRDetails;
import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.service.CompanyOKRService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rokaya
 * @Date 09/04/2023
 */
@RestController
@RequestMapping("company-okr") 
@RequiredArgsConstructor
@CrossOrigin
public class CompanyOKRController {
  private final CompanyOKRService companyOKRService;

  @GetMapping()
  public PageDto<CompanyOKRDetails> getPagedUsers(
      @RequestParam(name = "page",required = false)
          Integer page,
      @RequestParam(name = "offset",required = false)
          Integer offset) {
    return this.companyOKRService.getPagedCompanyOKR(page,offset);
  }

  @PostMapping(value = "add_new_companyokr")
  public void saveOrUpdate(@RequestBody CompanyOKRDetails companyOKRDetails) {
    this.companyOKRService.saveCompanyOKR(companyOKRDetails);
  }

  @DeleteMapping
  public void deleteCompanyOkr(@RequestParam(value = "company-okr-uuid") UUID companyOkrUuid){
    companyOKRService.deleteCompanyOKR(companyOkrUuid);
  }

  @GetMapping(value = "archive")
  public void archiveCompanyOkr(@RequestParam(value = "company-okr-uuid") UUID companyOkrUuid){
    companyOKRService.archiveCompanyOKR(companyOkrUuid);
  }

  @GetMapping("/company-okr-by-uuid")
  public CompanyOKRDetails getCompanyByUuid(@RequestParam("company-okr-uuid") UUID companyOkrUuid) {
    return this.companyOKRService.findCompanyOKRByUUID(companyOkrUuid);
  }

}
