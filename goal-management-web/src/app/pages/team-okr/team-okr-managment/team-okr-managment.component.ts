import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { KeyResultType } from "app/enumeration/key-result-type";
import { OkrStatus } from "app/enumeration/okr-status";
import { KeyResultDetails } from "app/model/key-result-details.model";
import { TeamOKRDetails } from "app/model/team-okr-details.model";
import { TeamRequest } from "app/model/team-request.model";
import { KeyResultService } from "app/service/key-result/key-result.service";
import { TeamOkrService } from "app/service/team-okr/team-okr.service";
import { UtilsService } from "app/service/utils.service";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";

@Component({
  selector: "team-okr-managment",
  templateUrl: "./team-okr-managment.component.html",
  styleUrls: ["./team-okr-managment.component.scss"],
})
export class TeamOkrManagmentComponent implements OnInit {
  @ViewChild("modalContent", { static: true }) modalContent:
    | TemplateRef<any>
    | undefined;
  companyOkrUuid: string;
  teamOkr: TeamOKRDetails = new TeamOKRDetails();
  modalTitle: string;
  keyResultList = [];
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
  keyResultUuid: string = "";
  keyResult: any;
  selectedTeam;
  partnerTeamOkr: any[] = [];
  selectedTeamOkr: any[] = [];
  addTeamOkr: boolean = false;
  isManager: boolean = UtilsService.isManager();

  constructor(
    private modal: NgbModal,
    private keyResultService: KeyResultService,
    private activatedRoute: ActivatedRoute,
    private teamOkrService: TeamOkrService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.keyResultUuid = this.activatedRoute.snapshot.paramMap.get("uuid");
    this.getKeyResultDetails();
  }
  onAdd() {
    this.addTeamOkr = false;
    this.partnerTeamOkr = [];
    this.teamOkr = new TeamOKRDetails();
    this.modalTitle = "Ajouter un Objectif d'Équipe";
    this.modal.open(this.modalContent, { windowClass: "modal-width" });
  }
  onSave() {
    if (
      !this.isEmpty(this.teamOkr.teamOkrLabel) &&
      !this.isEmpty(this.teamOkr.startDate) &&
      !this.isEmpty(this.teamOkr.endDate)
    ) {
      this.teamOkr.keyResultUuid = this.keyResult.keyResultUuid;
      this.teamOkr.partnerTeamOKRDetails = this.selectedTeamOkr;
      this.teamOkrService.save(this.teamOkr).subscribe((res) => {
        this.showSuccess("Modification enregistrée avec succès");
        this.modal.dismissAll();
        this.getKeyResultDetails();
      });
    }
  }
  getKeyResultDetails() {
    this.keyResultService.getKeyResult(this.keyResultUuid).subscribe((res) => {
      this.keyResult = res;
    });
  }
  onChange(team) {
    this.teamOkrService
      .getByKeyResultAndTeam(this.keyResultUuid, team.teamUuid)
      .subscribe((res) => {
        this.partnerTeamOkr = res;
      });
  }

  isEmpty(value) {
    return value == null || value.length === 0;
  }
  showSuccess(msg) {
    this.toastr.success(msg);
  }

  getStatusBadge(status: string): string {
    if (status.toString() == "IN_PROGRESS") {
      return "badge-style-pending";
    } else if (status.toString() == "FINALIZED") {
      return "badge-style-finalized";
    } else if (status.toString() == "CANCELED") {
      return "badge-style-canceled";
    }
    return "badge-style-archived";
  }
  getStatus(okrStatus: OkrStatus): string {
    const status = okrStatus.toString();
    return OkrStatus[status as keyof typeof OkrStatus];
  }
  onEdit(teamOKR) {
    this.addTeamOkr = false;
    this.partnerTeamOkr = [];
    this.selectedTeamOkr = teamOKR.partnerTeamOKRDetails;
    this.modalTitle = "Modifier un Objectif d'Équipe";
    this.teamOkr = teamOKR;
    this.modal.open(this.modalContent, { windowClass: "modal-width" });
  }

  onDelete(teamOkr) {
    Swal.fire({
      text: "Voulez vous supprimer cet objectif d'équipe ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.teamOkrService.deleteTeamOkr(teamOkr.teamOkrUuid).subscribe(
          (res) => {
            this.getKeyResultDetails;
            this.showSuccess("Objectif d'équipe supprimé avec succès");
          },
          (error) => {
            // if (error.status === 500) {
            Swal.fire(
              "",
              "La suppression de cet objectif d'équipe est non abouti. il est fort probable qu'il est lié à un autre objectif d'équipe",
              "error"
            );
            //}
          }
        );
      }
    });
  }

  onArchive(teamOKR) {
    Swal.fire({
      text: "Voulez vous archiver cet objectif d'équipe ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.teamOkrService
          .archiveTeamOkr(teamOKR.teamOkrUuid)
          .subscribe((res) => {
            this.getKeyResultDetails();
            this.showSuccess("objectif d'équipe archivé avec succès");
          });
      }
    });
  }

  onKeyManagment(teamOkr) {
    localStorage.setItem("title", "Mes résultats clés");
    this.router.navigateByUrl(`/team-okr-key-results/${teamOkr.teamOkrUuid}`);
  }

  onDeletePartnerTeamOkr(index) {
    this.selectedTeamOkr.splice(index, 1);
  }
  onPageChange(event) {
    this.page = event;
  }
}
