import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { OkrStatus } from "app/enumeration/okr-status";
import { KeyResultDetails } from "app/model/key-result-details.model";
import { TeamOKRDetails } from "app/model/team-okr-details.model";
import { KeyResultService } from "app/service/key-result/key-result.service";
import { TeamOkrService } from "app/service/team-okr/team-okr.service";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";

@Component({
  selector: "team-okr",
  templateUrl: "./team-okr.component.html",
  styleUrls: ["./team-okr.component.scss"],
})
export class TeamOKRComponent implements OnInit {
  @ViewChild("modalContent", { static: true }) modalContent:
    | TemplateRef<any>
    | undefined;

  companyOkrList: any[] = [];
  page: number = 1;
  pageSize: number = 5;
  collectionSize: number = 0;
  modalTitle: string;
  teamOKR: TeamOKRDetails = new TeamOKRDetails();
  currentKeyResult: KeyResultDetails = new KeyResultDetails();

  constructor(
    private keyResultService: KeyResultService,
    private modal: NgbModal,
    private teamOkrTeamService: TeamOkrService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getKeyResultByManager();
  }

  getKeyResultByManager() {
    this.keyResultService.getKeyResultByManager().subscribe((res) => {
      this.companyOkrList = res;
    });
  }

  onAdd(keyResult) {
    this.modalTitle = "Ajouter un Objectif d'Entreprise";
    // this.addOrUpdateMode = 0;
    //this.userRequest = new UserDetailsModel();
    this.currentKeyResult = keyResult;
    this.modal.open(this.modalContent);
  }

  onSave() {
    if (
      !this.isEmpty(this.teamOKR.teamOkrLabel) &&
      !this.isEmpty(this.teamOKR.startDate) &&
      !this.isEmpty(this.teamOKR.endDate)
    ) {
      this.teamOKR.keyResultUuid = this.currentKeyResult.keyResultUuid;
      this.teamOkrTeamService.save(this.teamOKR).subscribe((res) => {
        this.showSuccess("Modification enregistrée avec succès");
        this.modal.dismissAll();
        this.getKeyResultByManager();
      });
    }
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
  /*   onEdit(teamOKR) {
    this.modalTitle = "Modifier un OKR équipe";
    this.teamOKR = teamOKR;
    this.modal.open(this.modalContent);
  } */

  onDelete(teamOkr) {
    Swal.fire({
      text: "Voulez vous supprimer cet objectif d'équipe ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.teamOkrTeamService
          .deleteTeamOkr(teamOkr.teamOkrUuid)
          .subscribe((res) => {
            this.getKeyResultByManager();
            this.showSuccess("objectif d'équipe supprimé avec succès");
          });
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
        this.teamOkrTeamService
          .archiveTeamOkr(teamOKR.teamOkrUuid)
          .subscribe((res) => {
            this.getKeyResultByManager();
            this.showSuccess("objectif d'équipe archivé avec succès");
          });
      }
    });
  }

  onKeyManagment(copanyOkr) {
    localStorage.setItem("title", "Résultats clés");
    this.router.navigateByUrl(
      `/enterprise-okr-key-results/${copanyOkr.campanyOkrUuid}`
    );
  }
}
