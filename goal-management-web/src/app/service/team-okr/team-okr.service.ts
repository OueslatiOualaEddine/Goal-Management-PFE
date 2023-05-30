import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UserDetailsModel } from "app/model/user-details.model";
import { UtilsService } from "../utils.service";
import { catchError, map, Observable, throwError } from "rxjs";
@Injectable({
  providedIn: "root",
})
export class TeamOkrService {
  TEAM_OKR_API = UtilsService.BASE_API_URL + "api/team-okr";

  constructor(private http: HttpClient) {}

  getListUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.TEAM_OKR_API}/list`);
  }

  getByKeyResult(
    keyResultUuid: any,
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
    params.set("key-result-uuid", keyResultUuid);
    return this.http.get<UserDetailsModel[]>(`${this.TEAM_OKR_API}`, {
      params: params,
    });
  }

  save(teamOKRRequest: any): Observable<any> {
    return this.http.post<any>(`${this.TEAM_OKR_API}/add`, teamOKRRequest);
  }
  deleteTeamOkr(uuid: any): Observable<any> {
    return this.http
      .delete<any>(`${this.TEAM_OKR_API}?team-okr-uuid=${uuid}`)
      .pipe(
        map((res) => res.data),
        catchError((error: HttpErrorResponse) => {
          return throwError(error);
        })
      );
  }

  archiveTeamOkr(uuid: any): Observable<any> {
    return this.http.get<any>(
      `${this.TEAM_OKR_API}/archive?team-okr-uuid=${uuid}`
    );
  }

  getByKeyResultAndTeam(keyResultUuid, teamUuid) {
    return this.http.get<any>(
      `${this.TEAM_OKR_API}?key-result-uuid=${keyResultUuid}&team-uuid=${teamUuid}`
    );
  }
  getByKeyResultUuid(keyResultUuid) {
    return this.http.get<any>(
      `${this.TEAM_OKR_API}/by-key-result?key-result-uuid=${keyResultUuid}`
    );
  }

  getTeamOkrByUuid(teamOkrUuid: string): Observable<any> {
    return this.http.get<any>(
      `${this.TEAM_OKR_API}/team-okr-by-uuid?team-okr-uuid=${teamOkrUuid}`
    );
  }
}
