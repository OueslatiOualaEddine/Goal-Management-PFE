import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { KeyResultType } from "app/enumeration/key-result-type";
import { TeamOKRDetails } from "app/model/team-okr-details.model";
import { TeamOkrKeyResultDetails } from "app/model/team-okr-key-Result-Details.model";
import { TeamRequest } from "app/model/team-request.model";
import { TeamOkrKeyResultService } from "app/service/team-okr-key-result/team-okr-key-result.service";
import { TeamOkrService } from "app/service/team-okr/team-okr.service";
import { UtilsService } from "app/service/utils.service";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";
@Component({
  selector: "team-okr-key-results",
  templateUrl: "./team-okr-key-results.component.html",
  styleUrls: ["./team-okr-key-results.component.scss"],
})
export class TeamOkrKeyResultsComponent implements OnInit {
  @ViewChild("modalContent", { static: true }) modalContent:
    | TemplateRef<any>
    | undefined;
  teamOkr: TeamOKRDetails = new TeamOKRDetails();
  modalTitle: string;

  keyResultRequest: TeamOkrKeyResultDetails = new TeamOkrKeyResultDetails();
  keyResultTypes = Object.keys(KeyResultType).map((key) => ({
    key,
    name: Object(KeyResultType)[key],
  }));
  teamList: TeamRequest[] = [];
  isManager: boolean = UtilsService.isManager();
  page: number = 1;
  teamsPage: number = 1;
  pageSize: number = 5;
  teamsPageSize: number = 4;
  collectionSize: number = 0;
  teamsCollectionSize: number = 0;

  selectedTeams: TeamRequest[] = [];
  keyResultList: TeamOkrKeyResultDetails[] = [];
  teamOkrUuid: string;
  constructor(
    private teamOkrService: TeamOkrService,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute,
    private modal: NgbModal,
    private keyResultService: TeamOkrKeyResultService,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.teamOkrUuid = this.activatedRoute.snapshot.paramMap.get("uuid");
    this.getTeamOKRDetails();
    this.getKeyResults();
  }
  getTeamOKRDetails() {
    this.teamOkrService.getTeamOkrByUuid(this.teamOkrUuid).subscribe((res) => {
      this.teamOkr = res;
    });
  }
  onAdd() {
    this.modalTitle = "Ajouter un Résultat Clé";
    this.selectedTeams = [];
    this.modalTitle = "Ajouter un Résultat Clé";
    this.keyResultRequest = new TeamOkrKeyResultDetails();
    this.modal.open(this.modalContent);
  }

  onPageChange(event) {
    this.page = event;
    this.getKeyResults();
  }

  onSave() {
    if (
      !this.isEmpty(this.keyResultRequest.keyResultLabel) &&
      !this.isEmpty(this.keyResultRequest.keyResultStartValue) &&
      !this.isEmpty(this.keyResultRequest.keyResultTargetValue) &&
      !this.isEmpty(this.keyResultRequest.keyResultType)
    ) {
      this.keyResultRequest.teamOKRUuid = this.teamOkrUuid;
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
      .getKeyResultByTeamOkr(this.teamOkrUuid, this.pageSize, this.page - 1)
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

  onTaskManagement(keyResult) {
    localStorage.setItem("title", "Liste des Tâches");
    this.router.navigateByUrl(`/key-result-tasks/${keyResult.keyResultUuid}`);
  }
}
