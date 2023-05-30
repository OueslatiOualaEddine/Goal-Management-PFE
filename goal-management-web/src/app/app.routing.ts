import { Routes } from "@angular/router";

import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";
import { LoginComponent } from "./pages/auth/login/login.component";
import { AuthGuard } from "./shared/guards/auth/auth.guard";
import { RedirectGuard } from "./shared/guards/redirect/redirect.guard";

export const AppRoutes: Routes = [
  {
    path: "",
    redirectTo: "dashboard",
    pathMatch: "full",
  },
  {
    path: "login",
    component: LoginComponent,
    pathMatch: "full",
    canActivate: [RedirectGuard],
  },
  {
    path: "",
    component: AdminLayoutComponent,
    children: [
      {
        path: "",
        loadChildren: () =>
          import("./layouts/admin-layout/admin-layout.module").then(
            (x) => x.AdminLayoutModule
          ),
      },
    ],
    canActivate: [AuthGuard],
  },
  {
    path: "**",
    redirectTo: "dashboard",
  },
];
