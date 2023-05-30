import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { MediaContext } from "app/enumeration/media-context";
import { RoleCode } from "app/enumeration/role-code";
import { UserDetailsModel } from "app/model/user-details.model";
import { FileService } from "app/service/file/file.service";
import { UserService } from "app/service/user/user.service";
import { UtilsService } from "app/service/utils.service";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";

@Component({
  selector: "users-management",
  templateUrl: "./users-management.component.html",
  styleUrls: ["./users-management.component.scss"],
})
export class UsersManagementComponent implements OnInit {
  @ViewChild("modalContent", { static: true }) modalContent:
    | TemplateRef<any>
    | undefined;

  accountTypes = Object.keys(RoleCode).map((key) => ({
    key,
    name: Object(RoleCode)[key],
  }));
  urlUserPhoto: any = null;
  userPhoto: any;
  userRequest: UserDetailsModel = new UserDetailsModel();
  userList: any[] = [];
  addOrUpdateMode: number = -1;
  modalTitle = "";

  page: number = 1;
  pageSize: number = 5;
  collectionSize: number = 0;
  userType: RoleCode = null;
  constructor(
    private modal: NgbModal,
    private userService: UserService,
    private toastr: ToastrService,
    private fileService: FileService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllUser();
  }

  onAdd() {
    this.modalTitle = "Ajouter un Utilisateur";
    this.addOrUpdateMode = 0;
    this.userRequest = new UserDetailsModel();
    this.modal.open(this.modalContent);
  }
  getAllUser() {
    this.userService
      .getAllUsers(this.userType, this.pageSize, this.page - 1)
      .subscribe((res: any) => {
        this.userList = res.items;
        this.collectionSize = res.count;
      });
  }
  onSelectFile(event: any) {
    if (event.target.files && event.target.files[0]) {
      window.URL = window.URL || window.webkitURL;
      const img = new Image();
      img.src = window.URL.createObjectURL(event.target.files[0]);
      const reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]); // read file as data url
      reader.onload = (e) => {
        window.URL.revokeObjectURL(img.src);
        if (e.target) {
          this.userPhoto = event.target.files[0];
          this.urlUserPhoto = e.target.result;
        }
      };
    } else {
      this.urlUserPhoto = null;
    }
  }

  onSaveUser() {
    if (
      !this.isEmpty(this.userRequest.userEmail) &&
      !this.isEmpty(this.userRequest.userFirstName) &&
      !this.isEmpty(this.userRequest.userLastName) &&
      !this.isEmpty(this.userRequest.userRole)
    ) {
      const formdata = new FormData();
      formdata.append("userEmail", this.userRequest.userEmail);
      formdata.append("userPhoneNumber", this.userRequest.userPhoneNumber);
      formdata.append("userFirstName", this.userRequest.userFirstName);
      formdata.append("userLastName", this.userRequest.userLastName);
      formdata.append("role", this.userRequest.userRole);
      if (this.userPhoto) {
        formdata.append("profilePicture", this.userPhoto);
      }
      if (this.addOrUpdateMode == 0) {
        this.userService.saveUser(formdata).subscribe((res) => {
          this.modal.dismissAll();
          this.getAllUser();
          this.showSuccess("Utilisateur ajouté avec succès");
        });
      } else if (this.addOrUpdateMode == 1) {
        formdata.append("userUuid", this.userRequest.userUuid);
        this.userService.updateUser(formdata).subscribe((res) => {
          if (
            this.userRequest.userProfilePicture != null &&
            this.urlUserPhoto === null
          ) {
            this.removePicture(
              this.userRequest.userUuid,
              MediaContext[MediaContext.PICTURE_PROFIL]
            );
          }
          this.getAllUser();
          this.modal.dismissAll();
          this.showSuccess("Utilisateur modifié avec succès");
        });
      }
    }
  }

  onEdit(user) {
    this.urlUserPhoto = null;
    this.modalTitle = "Modifier un Utilisateur";
    this.addOrUpdateMode = 1;
    this.userRequest = user;
    if (user.userProfilePicture != null) {
      this.urlUserPhoto =
        UtilsService.BASE_API_URL +
        "api" +
        this.userRequest.userProfilePicture.mediaUrl;
    }
    this.modal.open(this.modalContent);
  }

  deletePhoto() {
    this.urlUserPhoto = null;
  }

  showSuccess(msg) {
    this.toastr.success(msg);
  }

  onDelete(user) {
    Swal.fire({
      text: "Voulez vous supprimer cet Utilisateur ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.userService.deleteUser(user.userUuid).subscribe(
          (res) => {
            this.getAllUser();
            this.showSuccess("Utilisateur supprimé avec succès");
          },
          (error) => {
            this.showError(
              "La suppression de cet Utilisateur est non abouti. il est fort probable qui'il appartient à une équipe ou lié à une tache"
            );
          }
        );
      }
    });
  }
  showError(msg) {
    this.toastr.error(msg);
  }
  onPageChange(event) {
    this.page = event;
    this.getAllUser();
  }

  isEmpty(value) {
    return value == null || value.length === 0;
  }

  removePicture(uuid: string, context: any) {
    this.fileService.deleteProfilePhoto(uuid, context).subscribe(
      (response) => {
        this.getAllUser();
      },
      (error) => {}
    );
  }

  getFunction(userRole: String): string {
    if (userRole === "ADMINISTRATOR") {
      return "Administrateur";
    } else if (userRole === "COLLABORATOR") {
      return "Collaborateur";
    } else {
      return "Manager";
    }
  }
}
