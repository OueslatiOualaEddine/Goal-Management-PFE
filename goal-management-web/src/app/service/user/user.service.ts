import { RoleCode } from "./../../enumeration/role-code";
import { UserDetailsModel } from "./../../model/user-details.model";
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, map, Observable, throwError } from "rxjs";
import { UtilsService } from "../utils.service";
import { ResetPasswordNewModel } from "app/model/reset-password-new.model";

@Injectable({
  providedIn: "root",
})
export class UserService {
  USER_API = UtilsService.BASE_API_URL + "api/user";

  constructor(private http: HttpClient) {}

  getListUsers(): Observable<UserDetailsModel[]> {
    return this.http.get<UserDetailsModel[]>(`${this.USER_API}/list`);
  }

  getAllUsers(
    userType: RoleCode,
    offset: number,
    page: number
  ): Observable<UserDetailsModel[]> {
    let params = new HttpParams();
    if (offset !== undefined && offset !== -1) {
      params = params.set("offset", "" + offset);
    }
    if (page !== undefined && page !== -1) {
      params = params.set("page", "" + page);
    }
    if (userType !== undefined && userType !== null) {
      params = params.set("userType", "" + userType);
    }
    return this.http.get<UserDetailsModel[]>(`${this.USER_API}`, {
      params: params,
    });
  }

  saveUser(userRequest: any): Observable<any> {
    return this.http.post<any>(`${this.USER_API}/add-new-user`, userRequest);
    /* .pipe(
        map((res) => res.data),
        catchError((error: HttpErrorResponse) => {
          return throwError(error);
        }) 
      );*/
  }

  updateUser(payload: any): Observable<any> {
    return this.http.patch<any>(`${this.USER_API}/update-user`, payload);
  }

  deleteUser(uuid: any): Observable<any> {
    return this.http.delete<any>(`${this.USER_API}?user-uuid=${uuid}`).pipe(
      map((res) => res.data),
      catchError((error: HttpErrorResponse) => {
        return throwError(error);
      })
    );
  }

  newPasswordReset(payload: ResetPasswordNewModel): Observable<any> {
    return this.http.post<any>(`${this.USER_API}/reset-password`, payload);
  }

  getUserTeamMembers(): Observable<UserDetailsModel[]> {
    return this.http.get<UserDetailsModel[]>(
      `${this.USER_API}/user-team-member`
    );
  }

  getUserInfo(): Observable<UserDetailsModel> {
    return this.http.get<UserDetailsModel>(`${this.USER_API}/user-profile`);
  }
}
