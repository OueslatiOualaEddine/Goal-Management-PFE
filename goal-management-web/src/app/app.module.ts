import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ToastrModule } from "ngx-toastr";

import { SidebarModule } from "./sidebar/sidebar.module";
import { FooterModule } from "./shared/footer/footer.module";
import { NavbarModule } from "./shared/navbar/navbar.module";
import { FixedPluginModule } from "./shared/fixedplugin/fixedplugin.module";

import { AppComponent } from "./app.component";
import { AppRoutes } from "./app.routing";

import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";
import { UsersManagementComponent } from "./pages/users-management/users-management.component";
import { FormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { TeamsManagmentComponent } from "./pages/teams-managment/teams-managment.component";
import { NgxPaginationModule } from "ngx-pagination";
import { AddMembersComponent } from "./pages/teams-managment/add-members/add-members.component";
import { SweetAlert2Module } from "@sweetalert2/ngx-sweetalert2";
import { LoginComponent } from "./pages/auth/login/login.component";
import { EnterpriseOKRComponent } from "./pages/enterprise-okr/enterprise-okr.component";
import { OkrManagmentComponent } from "./pages/enterprise-okr/okr-managment/okr-managment.component";
import { TeamOKRComponent } from "./pages/team-okr/team-okr.component";
import { AccordionModule } from "primeng/accordion";
import { InterceptorService } from "./shared/interceptor/interceptor.service";
import { TeamOkrManagmentComponent } from "./pages/team-okr/team-okr-managment/team-okr-managment.component";
import { CompanyOkrKeyResultsComponent } from "./pages/team-okr/company-okr-key-results/company-okr-key-results.component";
import { TeamOkrKeyResultsComponent } from "./pages/team-okr/team-okr-key-results/team-okr-key-results.component";
import { MultiSelectModule } from "primeng/multiselect";
import { TasksManagementComponent } from "./pages/task/tasks-management/tasks-management.component";
import { CKEditorModule } from "ckeditor4-angular";
import { TaskDetailsComponent } from './pages/task/task-details/task-details.component';
import { NewPasswordComponent } from './pages/auth/new-password/new-password.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminLayoutComponent,
    UsersManagementComponent,
    TeamsManagmentComponent,
    AddMembersComponent,
    LoginComponent,
    EnterpriseOKRComponent,
    OkrManagmentComponent,
    TeamOKRComponent,
    TeamOkrManagmentComponent,
    CompanyOkrKeyResultsComponent,
    TeamOkrKeyResultsComponent,
    TasksManagementComponent,
    TaskDetailsComponent,
    NewPasswordComponent,
    UserProfileComponent,
  ],
  imports: [
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(AppRoutes, {
      useHash: true,
    }),
    SidebarModule,
    NavbarModule,
    ToastrModule.forRoot(),
    FooterModule,
    FixedPluginModule,
    ToastrModule.forRoot({ timeOut: 3000, positionClass: "toast-top-right" }),
    SweetAlert2Module.forRoot(),
    NgxPaginationModule,
    AccordionModule,
    MultiSelectModule,
    CKEditorModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
