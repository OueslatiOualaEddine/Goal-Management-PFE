import { HttpErrorResponse } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ResetPasswordNewModel } from "app/model/reset-password-new.model";
import { UserService } from "app/service/user/user.service";
import { ToastrModule, ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";

@Component({
  selector: "new-password",
  templateUrl: "./new-password.component.html",
  styleUrls: ["./new-password.component.scss"],
})
export class NewPasswordComponent implements OnInit {
  newPasswordReset: ResetPasswordNewModel = new ResetPasswordNewModel();
  requestUuid = "";
  message = "Code non valide";
  isSuccess: boolean = false;
  viewPassword: boolean = false;
  viewConfirmPassword: boolean = false;

  showAlertError: boolean = false;
  showAlertMatchPassword = false;
  viewOldPassword: boolean = false;
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private toastr: ToastrService
  ) {}
  ngOnInit(): void {
    //this.requestUuid = this.activatedRoute.snapshot.paramMap.get("id_reset");
  }
  ngAfterViewInit(): void {
    /*  this.activatedRoute.queryParams.subscribe((params) => {
      this.requestUuid = params["id_reset"];
    }); */
  }

  onFormSubmit() {
    if (
      this.newPasswordReset.password !== this.newPasswordReset.confirmPassword
    ) {
      Swal.fire("", "Les deux mot de passe ne sont pas identique", "warning");
    }
    if (!this.showAlertMatchPassword) {
      const newPassword = new ResetPasswordNewModel();
      newPassword.oldPassword = this.newPasswordReset.oldPassword;
      newPassword.password = this.newPasswordReset.password;
      newPassword.confirmPassword = this.newPasswordReset.confirmPassword;
      this.newPassword(newPassword);
    }
  }

  private newPassword(newPassword: ResetPasswordNewModel) {
    this.userService.newPasswordReset(newPassword).subscribe(
      (response) => {
        if (response === true) {
          this.showSuccess("Mot de passe modifié avec succès");
          this.router.navigateByUrl(`/login`);
        } else {
          Swal.fire("", "L'ancien mot de passe est incorrecte", "warning");
        }
      },
      (error: HttpErrorResponse) => {
        Swal.fire("", "Une erreur s'est produite", "error");
      }
    );
  }
  showSuccess(msg) {
    this.toastr.success(msg);
  }
  matchPassword(event: any) {
    if (
      event.target.value !== "" &&
      event.target.value !== this.newPasswordReset.password
    ) {
      this.showAlertMatchPassword = true;
    } else {
      this.showAlertMatchPassword = false;
    }
  }
  changeViewOldPassword() {
    this.viewOldPassword = !this.viewOldPassword;
  }
  changeViewPassword() {
    this.viewPassword = !this.viewPassword;
  }

  changeViewConfirmPassword() {
    this.viewConfirmPassword = !this.viewConfirmPassword;
  }
}
