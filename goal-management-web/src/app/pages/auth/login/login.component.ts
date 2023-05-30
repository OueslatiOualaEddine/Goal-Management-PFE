import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { RoleCode } from "app/enumeration/role-code";
import { LoginRequest } from "app/model/auth.model";
import { AuthService } from "app/service/auth/auth.service";
import { UserService } from "app/service/user/user.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  auth: LoginRequest = {} as LoginRequest;
  redirectUrl: string = "";
  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private authService: AuthService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    /*const auth = {
      email: "test@gmail.com",
      userPassword: "azerty1230++",
    };
    this.login(auth);*/
  }

  onFormSubmit() {
    const auth = {
      email: this.auth.email,
      userPassword: this.auth.userPassword,
    };
    this.login(auth);
  }

  private login(auth: LoginRequest) {
    this.authService.signIn(auth).subscribe(
      (response) => {
        localStorage.setItem("userFullName", response.userFullName);
        localStorage.setItem("refreshToken", response.refreshToken);
        localStorage.setItem("token", response.token);
        localStorage.setItem("current-user-role", response.roles[0]);
        if (response.roles[0] === RoleCode.ADMINISTRATOR)
          this.redirectUrl = `users-management`;
        else if (response.roles[0] === RoleCode.MANAGER)
          this.redirectUrl = `team-okr`;
        else if (response.roles[0] === RoleCode.COLLABORATOR)
          this.redirectUrl = `team-okr`;
        this.router.navigateByUrl(this.redirectUrl).then(() => {
          window.location.reload();
        });
      },
      (error) => {
        this.showError(
          "Authentification non aboutie! merci de vérifier vos identifiants de connexion!"
        );
      }
    );
  }
  showError(msg) {
    this.toastr.error(msg);
  }
}
