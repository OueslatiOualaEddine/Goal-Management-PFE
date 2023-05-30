import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { KeyResultType } from "app/enumeration/key-result-type";
import { CompanyOKRDetails } from "app/model/company-okr-details.model";
import { KeyResultDetails } from "app/model/key-result-details.model";
import { TeamRequest } from "app/model/team-request.model";
import { CompanyOKRService } from "app/service/company-okr/company-okr.service";
import { KeyResultService } from "app/service/key-result/key-result.service";
import { TeamService } from "app/service/team/team.service";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";
@Component({
  selector: "company-okr-key-results",
  templateUrl: "./company-okr-key-results.component.html",
  styleUrls: ["./company-okr-key-results.component.scss"],
})
export class CompanyOkrKeyResultsComponent implements OnInit {
  @ViewChild("modalContent", { static: true }) modalContent:
    | TemplateRef<any>
    | undefined;
  companyOkrUuid: string;
  companyOkr: CompanyOKRDetails = new CompanyOKRDetails();
  modalTitle: string;

  keyResultRequest: KeyResultDetails = new KeyResultDetails();
  keyResultTypes = Object.keys(KeyResultType).map((key) => ({
    key,
    name: Object(KeyResultType)[key],
  }));
  teamList: TeamRequest[] = [];

  page: number = 1;
  teamsPage: number = 1;
  pageSize: number = 5;
  teamsPageSize: number = 4;
  collectionSize: number = 0;
  teamsCollectionSize: number = 0;

  selectedTeams: TeamRequest[] = [];
  keyResultList: KeyResultDetails[] = [];
  constructor(
    private companyOkrService: CompanyOKRService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private keyResultService: KeyResultService
  ) {}

  ngOnInit(): void {
    this.companyOkrUuid = this.activatedRoute.snapshot.paramMap.get("uuid");
    this.getCompanyOKRDetails();
    this.getKeyResults();
  }
  getCompanyOKRDetails() {
    this.companyOkrService
      .getCompanyOkrByUuid(this.companyOkrUuid)
      .subscribe((res) => {
        this.companyOkr = res;
      });
  }

  onTeamOkrManagement(keyResult) {
    localStorage.setItem("title", "Mes objectifs");
    this.router.navigateByUrl(`/team-okr-details/${keyResult.keyResultUuid}`);
  }

  onPageChange(event) {
    this.page = event;
    this.getKeyResults();
  }

  getKeyResults() {
    this.keyResultService
      .getKeyResultByManagerTeam(this.pageSize, this.page - 1)
      .subscribe((res: any) => {
        this.keyResultList = res.items;
        this.collectionSize = res.count;
      });
  }
}
