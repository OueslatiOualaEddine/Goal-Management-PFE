import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { OkrStatus } from "app/enumeration/okr-status";
import { CompanyOKRDetails } from "app/model/company-okr-details.model";
import { CompanyOKRService } from "app/service/company-okr/company-okr.service";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";

@Component({
  selector: "enterprise-okr",
  templateUrl: "./enterprise-okr.component.html",
  styleUrls: ["./enterprise-okr.component.scss"],
})
export class EnterpriseOKRComponent implements OnInit {
  @ViewChild("modalContent", { static: true }) modalContent:
    | TemplateRef<any>
    | undefined;

  companyOKRList: any[] = [];
  modalTitle: string;
  companyOKR: CompanyOKRDetails = new CompanyOKRDetails();
  page: number = 1;
  pageSize: number = 5;
  collectionSize: number = 0;
  companyOkrRequest: any;
  constructor(
    private modal: NgbModal,
    private companyOkrService: CompanyOKRService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllCompanyOKR();
  }
  onAdd() {
    this.modalTitle = "Ajouter un Objectif d'Entreprise";
    // this.addOrUpdateMode = 0;
    //this.userRequest = new UserDetailsModel();
    this.companyOKR = new CompanyOKRDetails();

    this.modal.open(this.modalContent);
  }
  onSave() {
    if (
      !this.isEmpty(this.companyOKR.campanyOkrName) &&
      !this.isEmpty(this.companyOKR.startDate) &&
      !this.isEmpty(this.companyOKR.endDate)
    ) {
      this.companyOkrService.save(this.companyOKR).subscribe((res) => {
        this.showSuccess("Modification enregistrée avec succès");
        this.modal.dismissAll();
        this.getAllCompanyOKR();
      });
    }
  }

  showSuccess(msg) {
    this.toastr.success(msg);
  }

  getAllCompanyOKR() {
    this.companyOkrService
      .getAllUsers(this.pageSize, this.page - 1)
      .subscribe((res: any) => {
        this.companyOKRList = res.items;
        this.collectionSize = res.count;
      });
  }
  onPageChange(event) {
    this.page = event;
    this.getAllCompanyOKR();
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
  getStatus(campanyStatus: OkrStatus): string {
    const status = campanyStatus.toString();
    return OkrStatus[status as keyof typeof OkrStatus];
  }

  onEdit(companyOKR) {
    this.modalTitle = "Modifier un Objectif d'Entreprise";
    this.companyOKR = companyOKR;
    this.modal.open(this.modalContent);
  }

  onDelete(team) {
    Swal.fire({
      text: "Voulez vous supprimer cet objectif entreprise ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.companyOkrService
          .deleteCompanyOkr(team.campanyOkrUuid)
          .subscribe((res) => {
            this.getAllCompanyOKR();
            this.showSuccess("Objectif entreprise supprimé avec succès");
          });
      }
    });
  }
  onArchive(companyOkr) {
    Swal.fire({
      text: "Voulez vous archiver cet objectif entreprise ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.companyOkrService
          .archiveCompanyOkr(companyOkr.campanyOkrUuid)
          .subscribe((res) => {
            this.getAllCompanyOKR();
            this.showSuccess("Objectif entreprise archivé avec succès");
          });
      }
    });
  }

  isEmpty(value) {
    return value == null || value.length === 0;
  }
  onKeyManagment(copanyOkr) {
    localStorage.setItem("title", "Résultats clés");
    this.router.navigateByUrl(
      `/enterprise-okr-details/${copanyOkr.campanyOkrUuid}`
    );
  }
}
