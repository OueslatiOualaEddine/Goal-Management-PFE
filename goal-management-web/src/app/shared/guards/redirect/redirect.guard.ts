import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from "@angular/router";
import { UtilsService } from "app/service/utils.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class RedirectGuard implements CanActivate {
  constructor(public router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (UtilsService.isConnected()) {
      this.router.navigate(["/user-calendar"], {
        queryParams: route.queryParams,
      });
      return false;
    }
    return true;
  }
}
