import { environment } from "./../../environments/environment";
import { Injectable } from "@angular/core";
import { RoleCode } from "app/enumeration/role-code";

@Injectable({
  providedIn: "root",
})
export class UtilsService {
  public static BASE_API_URL = environment.baseApiUrl;
  public static FIRST_PATH = "users-management";

  constructor() {}

  public static isConnected() {
    if (localStorage.getItem("token")) {
      return true;
    } else {
      return false;
    }
  }

  public static isManager(): boolean {
    if (localStorage.getItem("current-user-role"))
      if (localStorage.getItem("current-user-role") === RoleCode.MANAGER)
        return true;

    return false;
  }
  public static isAdministrator(): boolean {
    if (localStorage.getItem("current-user-role"))
      if (localStorage.getItem("current-user-role") === RoleCode.ADMINISTRATOR)
        return true;

    return false;
  }
  public static isCollaborator(): boolean {
    if (localStorage.getItem("current-user-role"))
      if (localStorage.getItem("current-user-role") === RoleCode.COLLABORATOR)
        return true;

    return false;
  }
}
