import { UsersManagementComponent } from "./../../pages/users-management/users-management.component";
import { Routes } from "@angular/router";

import { DashboardComponent } from "../../pages/dashboard/dashboard.component";
import { UserComponent } from "../../pages/user/user.component";
import { TableComponent } from "../../pages/table/table.component";
import { TypographyComponent } from "../../pages/typography/typography.component";
import { IconsComponent } from "../../pages/icons/icons.component";
import { MapsComponent } from "../../pages/maps/maps.component";
import { NotificationsComponent } from "../../pages/notifications/notifications.component";
import { UpgradeComponent } from "../../pages/upgrade/upgrade.component";
import { TeamsManagmentComponent } from "app/pages/teams-managment/teams-managment.component";
import { AddMembersComponent } from "app/pages/teams-managment/add-members/add-members.component";
import { EnterpriseOKRComponent } from "app/pages/enterprise-okr/enterprise-okr.component";
import { OkrManagmentComponent } from "app/pages/enterprise-okr/okr-managment/okr-managment.component";
import { TeamOKRComponent } from "app/pages/team-okr/team-okr.component";
import { TeamOkrManagmentComponent } from "app/pages/team-okr/team-okr-managment/team-okr-managment.component";
import { CompanyOkrKeyResultsComponent } from "app/pages/team-okr/company-okr-key-results/company-okr-key-results.component";
import { TeamOkrKeyResultsComponent } from "app/pages/team-okr/team-okr-key-results/team-okr-key-results.component";
import { TasksManagementComponent } from "app/pages/task/tasks-management/tasks-management.component";
import { TaskDetailsComponent } from "app/pages/task/task-details/task-details.component";
import { NewPasswordComponent } from "app/pages/auth/new-password/new-password.component";
import { UserProfileComponent } from "app/pages/user-profile/user-profile.component";

export const AdminLayoutRoutes: Routes = [
  { path: "dashboard", component: DashboardComponent },
  { path: "user", component: UserComponent },
  { path: "table", component: TableComponent },
  { path: "typography", component: TypographyComponent },
  { path: "icons", component: IconsComponent },
  { path: "maps", component: MapsComponent },
  { path: "notifications", component: NotificationsComponent },
  { path: "upgrade", component: UpgradeComponent },
  { path: "users-management", component: UsersManagementComponent },
  { path: "teams-management", component: TeamsManagmentComponent },
  { path: "team-members/:uuid", component: AddMembersComponent },
  { path: "enterprise-okr", component: EnterpriseOKRComponent },
  { path: "enterprise-okr-details/:uuid", component: OkrManagmentComponent },
  { path: "team-okr", component: TeamOKRComponent },
  { path: "team-okr-details/:uuid", component: TeamOkrManagmentComponent },
  {
    path: "enterprise-okr-key-results/:uuid",
    component: CompanyOkrKeyResultsComponent,
  },
  {
    path: "team-okr-key-results/:uuid",
    component: TeamOkrKeyResultsComponent,
  },
  {
    path: "key-result-tasks/:uuid",
    component: TasksManagementComponent,
  },
  {
    path: "task-details/:uuid",
    component: TaskDetailsComponent,
  },
  {
    path: "reset-password",
    component: NewPasswordComponent,
  },
  {
    path: "user-profile",
    component: UserProfileComponent,
  },
];
