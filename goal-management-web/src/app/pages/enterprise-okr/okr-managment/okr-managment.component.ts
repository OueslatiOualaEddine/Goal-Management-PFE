import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
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
  selector: "okr-managment",
  templateUrl: "./okr-managment.component.html",
  styleUrls: ["./okr-managment.component.scss"],
})
export class OkrManagmentComponent implements OnInit {
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
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute,
    private modal: NgbModal,
    private teamService: TeamService,
    private keyResultService: KeyResultService
  ) {}

  ngOnInit(): void {
    this.companyOkrUuid = this.activatedRoute.snapshot.paramMap.get("uuid");
    this.getCompanyOKRDetails();
    this.getAllTeams();
    this.getKeyResults();
  }
  getCompanyOKRDetails() {
    this.companyOkrService
      .getCompanyOkrByUuid(this.companyOkrUuid)
      .subscribe((res) => {
        this.companyOkr = res;
      });
  }
  onAdd() {
    this.modalTitle = "Ajouter un Résultat Clé";
    this.selectedTeams = [];
    this.modalTitle = "Ajouter un Résultat Clé";
    this.keyResultRequest = new KeyResultDetails();
    this.modal.open(this.modalContent);
  }
  getAllTeams() {
    this.teamService
      .getAllTeams(this.teamsPageSize, this.teamsPage - 1)
      .subscribe((res: any) => {
        this.teamList = res.items;
        this.teamsCollectionSize = res.count;
      });
  }
  isChecked(selectedTeam): boolean {
    if (this.keyResultRequest?.teamList != null) {
      return this.keyResultRequest?.teamList.some(
        (team) => team.teamUuid === selectedTeam.teamUuid
      );
    }
  }
  onTeamsPageChange(event) {
    this.teamsPage = event;
    this.getAllTeams();
  }

  onPageChange(event) {
    this.page = event;
    this.getKeyResults();
  }

  toggleOption(selectedTeam) {
    const index = this.selectedTeams
      .map((team) => team.teamUuid)
      .indexOf(selectedTeam.teamUuid);
    if (index >= 0) {
      this.selectedTeams.splice(index, 1);
    } else if (index == -1) {
      this.selectedTeams.push(selectedTeam);
    }
  }
  onSave() {
    if (
      !this.isEmpty(this.keyResultRequest.keyResultLabel) &&
      !this.isEmpty(this.keyResultRequest.keyResultStartValue) &&
      !this.isEmpty(this.keyResultRequest.keyResultTargetValue) &&
      !this.isEmpty(this.keyResultRequest.keyResultType)
      // && this.selectedTeams.length > 0
    ) {
      this.keyResultRequest.companyOKRUuid = this.companyOkrUuid;
      this.keyResultRequest.teamList = this.selectedTeams;
      this.keyResultService.save(this.keyResultRequest).subscribe((res) => {
        this.showSuccess("Modification enregistrée avec succès");
        this.modal.dismissAll();
        this.getKeyResults();
      });
    }
  }
  showSuccess(msg) {
    this.toastr.success(msg);
  }
  getKeyResults() {
    this.keyResultService
      .getKeyResultByCompanyOkr(
        this.companyOkrUuid,
        this.pageSize,
        this.page - 1
      )
      .subscribe((res: any) => {
        this.keyResultList = res.items;
        this.collectionSize = res.count;
      });
  }
  onEdit(keyResult) {
    this.selectedTeams = keyResult.teamList;
    this.modalTitle = "Modifier un Résultat Clé";
    this.keyResultRequest = keyResult;
    this.modal.open(this.modalContent);
  }

  onDelete(keyResult) {
    Swal.fire({
      text: "Voulez vous supprimer ce résultat clé ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.keyResultService
          .deleteKeyResult(keyResult.keyResultUuid)
          .subscribe((res) => {
            this.getKeyResults();
            this.showSuccess("résultat clé supprimé avec succès");
          });
      }
    });
  }
  onArchive(keyResult) {
    Swal.fire({
      text: "Voulez vous archiver ce résultat clé ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.keyResultService
          .archiveKeyResult(keyResult.keyResultUuid)
          .subscribe((res) => {
            this.getKeyResults();
            this.showSuccess("résultat clé archivé avec succès");
          });
      }
    });
  }
  isEmpty(value) {
    return value == null || value.length === 0;
  }
}
