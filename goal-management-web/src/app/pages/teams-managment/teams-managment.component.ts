import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TeamRequest } from "app/model/team-request.model";
import { UserDetailsModel } from "app/model/user-details.model";
import { TeamService } from "app/service/team/team.service";
import { UserService } from "app/service/user/user.service";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";

@Component({
  selector: "teams-managment",
  templateUrl: "./teams-managment.component.html",
  styleUrls: ["./teams-managment.component.scss"],
})
export class TeamsManagmentComponent implements OnInit {
  @ViewChild("modalContent", { static: true }) modalContent:
    | TemplateRef<any>
    | undefined;
  managerList: any[] = [];
  teamRequest: any;
  modalTitle = "";

  page: number = 1;
  pageSize: number = 5;
  collectionSize: number = 0;
  addOrUpdateMode: number;

  teamList: TeamRequest[] = [];
  constructor(
    private router: Router,
    private modal: NgbModal,
    private userService: UserService,
    private teamService: TeamService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.getAllUser();
    this.getAllTeams();
  }
  onAdd() {
    //this.router.navigateByUrl("/new-team");
    this.modalTitle = "Ajouter une Équipe";
    this.addOrUpdateMode = 0;
    this.teamRequest = new TeamRequest();
    this.modal.open(this.modalContent);
  }

  getAllUser() {
    this.userService.getListUsers().subscribe((res: any) => {
      this.managerList = res.filter((user) => user.userRole === "MANAGER");
    });
  }

  onSaveTeam() {
    if (
      !this.isEmpty(this.teamRequest.teamName) &&
      !this.isEmpty(this.teamRequest.manager)
    ) {
      this.teamService.saveTeam(this.teamRequest).subscribe((res) => {
        this.modal.dismissAll();
        this.getAllTeams();
        if (this.addOrUpdateMode == 0) {
          this.showSuccess("Équipe ajoutée avec succès");
        } else if (this.addOrUpdateMode == 1) {
          this.showSuccess("Équipe modifiée avec succès");
        }
      });
    }
  }

  showSuccess(msg) {
    this.toastr.success(msg);
  }

  getAllTeams() {
    this.teamService
      .getAllTeams(this.pageSize, this.page - 1)
      .subscribe((res: any) => {
        this.teamList = res.items;
        this.collectionSize = res.count;
      });
  }
  onPageChange(event) {
    this.page = event;
    this.getAllTeams();
  }

  onEdit(team) {
    this.modalTitle = "Modifier une Équipe";
    this.addOrUpdateMode = 1;
    this.teamRequest = team;

    this.modal.open(this.modalContent);
  }
  onAddMembers(team) {
    this.router.navigateByUrl(`/team-members/${team.teamUuid}`);
  }

  compareManager(item: UserDetailsModel, selected: UserDetailsModel) {
    if (item && selected) {
      return item && selected
        ? item.userUuid === selected.userUuid
        : item === selected;
    }
    return false;
  }

  onDelete(team) {
    Swal.fire({
      text: "Voulez vous supprimer cet équipe ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.teamService.deleteTeam(team.teamUuid).subscribe((res) => {
          this.getAllTeams();
          this.showSuccess("Équipe supprimée avec succès");
        });
      }
    });
  }

  isEmpty(value) {
    return value == null || value.length === 0;
  }
}
