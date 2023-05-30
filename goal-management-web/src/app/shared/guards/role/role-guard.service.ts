import { Injectable } from "@angular/core";
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from "@angular/router";
import { UtilsService } from "app/service/utils.service";

@Injectable({
  providedIn: "root",
})
export class RoleGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (UtilsService.isConnected()) {
      const userRole = localStorage.getItem("current-user-role");
      // check if route is restricted by role
      if (route.data.role && route.data.role.indexOf(userRole) === -1) {
        // role not authorised so redirect to page not found
        this.router.navigate(["**"]);
        return false;
      }

      // authorised so return true
      return true;
    }
    this.router.navigate([UtilsService.FIRST_PATH]);
    return false;
  }

  // not logged in so redirect to login page with the return url
  /* this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
        return false; */
}
