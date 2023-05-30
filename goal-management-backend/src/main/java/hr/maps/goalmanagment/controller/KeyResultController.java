package hr.maps.goalmanagment.controller;

import hr.maps.goalmanagment.dtos.KeyResultDetails;
import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.response.CompanyOKRResponse;
import hr.maps.goalmanagment.dtos.response.KeyResultResponse;
import hr.maps.goalmanagment.service.KeyResultService;
import java.util.List;
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
 * @Date 14/04/2023
 */
@RestController
@RequestMapping("key-result")
@RequiredArgsConstructor
@CrossOrigin
public class KeyResultController {
  private final KeyResultService keyResultService;

  @PostMapping
  public void saveOrUpdate(@RequestBody KeyResultDetails keyResultDetails) {
    this.keyResultService.saveKeyResult(keyResultDetails);
  }


  @GetMapping()
  public PageDto<KeyResultDetails> getPagedTeams(
      @RequestParam(name = "company-okr-uuid")
          UUID companyOkrUuid,
      @RequestParam(name = "page",required = false)
          Integer page,
      @RequestParam(name = "offset",required = false)
          Integer offset) {
    return this.keyResultService.getPagedKeyResultByCompanyOkrUuid(companyOkrUuid,page,offset);
  }
  @DeleteMapping
  public void deleteCompanyOkr(@RequestParam(value = "key-result-uuid") UUID companyOkrUuid){
    keyResultService.deleteCompanyOKR(companyOkrUuid);
  }

  @GetMapping(value = "archive")
  public void archiveCompanyOkr(@RequestParam(value = "key-result-uuid") UUID companyOkrUuid){
    keyResultService.archiveCompanyOKR(companyOkrUuid);
  }

  @GetMapping(value = "list")
  public List<CompanyOKRResponse> getKeyResultByManager(){
    return keyResultService.getKeyResultByManager();
  }


  @GetMapping(value = "key-result-by-uuid")
  public KeyResultResponse getKeyResultByManager(@RequestParam(value = "key-result-uuid") UUID keyResultUuid){
    return keyResultService.getKeyResultByUuid(keyResultUuid);
  }
  @GetMapping(value = "by-manager-team")
  public PageDto<KeyResultDetails> getKeyResultByManagerTeam(@RequestParam(name = "page",required = false)
      Integer page,
      @RequestParam(name = "offset",required = false)
          Integer offset){
    return keyResultService.getKeyResultByManagerTeam(page,offset);
  }
}
