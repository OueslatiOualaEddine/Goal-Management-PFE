import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { MediaContext } from "app/enumeration/media-context";
import { UserDetailsModel } from "app/model/user-details.model";
import { FileService } from "app/service/file/file.service";
import { UserService } from "app/service/user/user.service";
import { UtilsService } from "app/service/utils.service";
import { ToastrService } from "ngx-toastr";


@Component({
  selector: "user-profile",
  templateUrl: "./user-profile.component.html",
  styleUrls: ["./user-profile.component.scss"],
})
export class UserProfileComponent implements OnInit {
  urlUserPhoto: any = null;
  userRequest: UserDetailsModel = new UserDetailsModel();
  userPhoto: any;

  constructor(
    private userService: UserService,
    private toastr: ToastrService,
    private router: Router,
    private fileService: FileService
  ) {}

  ngOnInit(): void {
    this.getUserProfie();
  }
  deletePhoto() {
    this.urlUserPhoto = null;
  }
  getUserProfie() {
    this.userService.getUserInfo().subscribe((res) => {
      console.log(res);
      this.userRequest = res;
      if (this.userRequest.userProfilePicture != null) {
        this.urlUserPhoto =
          UtilsService.BASE_API_URL +
          "api" +
          this.userRequest.userProfilePicture.mediaUrl;
      }
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
      !this.isEmpty(this.userRequest.userLastName)
    ) {
      const formdata = new FormData();
      formdata.append("userEmail", this.userRequest.userEmail);
      formdata.append("userPhoneNumber", this.userRequest.userPhoneNumber);
      formdata.append("userFirstName", this.userRequest.userFirstName);
      formdata.append("userLastName", this.userRequest.userLastName);
      if (this.userPhoto) {
        formdata.append("profilePicture", this.userPhoto);
      }

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
        this.getUserProfie();
        this.showSuccess("Profil modifié avec succès");
      });
    }
  }
  showSuccess(msg) {
    this.toastr.success(msg);
  }
  isEmpty(value) {
    return value == null || value.length === 0;
  }

  updatePassword() {
    this.router.navigateByUrl("reset-password");
  }

  removePicture(uuid: string, context: any) {
    this.fileService.deleteProfilePhoto(uuid, context).subscribe(
      (response) => {
        this.getUserProfie();
      },
      (error) => {}
    );
  }
}
