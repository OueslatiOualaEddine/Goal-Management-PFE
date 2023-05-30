import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { TeamRequest } from "app/model/team-request.model";
import { UserDetailsModel } from "app/model/user-details.model";
import { TeamService } from "app/service/team/team.service";
import { UserService } from "app/service/user/user.service";
import { UtilsService } from "app/service/utils.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "add-members",
  templateUrl: "./add-members.component.html",
  styleUrls: ["./add-members.component.scss"],
})
export class AddMembersComponent implements OnInit {
  teamUuid: string = "";
  team: TeamRequest = new TeamRequest();
  collaboratorList: UserDetailsModel[] = [];
  selectedMembers: UserDetailsModel[] = [];
  page: number = 0;
  urlUserPhoto = UtilsService.BASE_API_URL + "api";
  constructor(
    private activatedRoute: ActivatedRoute,
    private teamService: TeamService,
    private userService: UserService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.teamUuid = this.activatedRoute.snapshot.paramMap.get("uuid");
    this.getTeamDetails();
    this.getAllUser();
  }

  getTeamDetails() {
    this.selectedMembers = [];
    this.teamService.getTeamByUuid(this.teamUuid).subscribe((res) => {
      this.team = res;
      this.selectedMembers = this.team.members;
    });
  }

  getAllUser() {
    this.userService.getListUsers().subscribe((res: any) => {
      this.collaboratorList = res.filter(
        (user) => user.userRole === "COLLABORATOR"
      );
    });
  }

  isSelected(option: any) {
    return this.selectedMembers.indexOf(option) >= 0;
  }

  toggleOption(user) {
    const index = this.selectedMembers
      .map((member) => member.userUuid)
      .indexOf(user.userUuid);
    if (index >= 0) {
      this.selectedMembers.splice(index, 1);
    } else if (index == -1) {
      this.selectedMembers.push(user);
    }
  }

  onSaveTeam() {
    let teamRequest = new TeamRequest();
    teamRequest.members = this.selectedMembers;
    teamRequest.teamUuid = this.teamUuid;
    this.teamService.updateTeamMembers(teamRequest).subscribe((res) => {
      this.showSuccess("Modification enregistrée avec succès");
    });
  }

  onPageChange(event) {
    this.page = event;
  }
  cancel() {
    this.router.navigateByUrl("/teams-management");
  }

  getDefaultUserImage(user: any): string {
    let intials: string = "";
    if (user.userProfilePicture == null) {
      const fullName = user.userFirstName + " " + user.userLastName;
      intials = fullName
        .split(" ")
        .map((name) => name[0])
        .join("")
        .toUpperCase();
    }
    return intials;
  }

  isChecked(collaborator): boolean {
    return this.team.members.some(
      (user) => user.userUuid === collaborator.userUuid
    );
  }

  showSuccess(msg) {
    this.toastr.success(msg);
  }
}
