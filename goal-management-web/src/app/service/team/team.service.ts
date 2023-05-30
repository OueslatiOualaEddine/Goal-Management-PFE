import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { TeamRequest } from "app/model/team-request.model";
import { catchError, map, Observable, throwError } from "rxjs";
import { UtilsService } from "../utils.service";

@Injectable({
  providedIn: "root",
})
export class TeamService {
  TEAM_API = UtilsService.BASE_API_URL + "api/team";

  constructor(private http: HttpClient) {}

  /*  getListUsers(): Observable<UserDetailsModel[]> {
    return this.http.get<UserDetailsModel[]>(`${this.USER_API}/list`);
  }
 */
  saveTeam(teamRequest: any): Observable<any> {
    return this.http.post<any>(`${this.TEAM_API}/add-new-team`, teamRequest);
  }

  getAllTeams(offset: number, page: number): Observable<TeamRequest[]> {
    let params = new HttpParams();
    if (offset !== undefined && offset !== -1) {
      params = params.set("offset", "" + offset);
    }
    if (page !== undefined && page !== -1) {
      params = params.set("page", "" + page);
    }
    return this.http.get<TeamRequest[]>(`${this.TEAM_API}`, {
      params: params,
    });
  }

  deleteTeam(uuid: any): Observable<any> {
    return this.http.delete<any>(`${this.TEAM_API}?team-uuid=${uuid}`);
  }

  getTeamByUuid(teamUuid: string): Observable<any> {
    return this.http.get<any>(
      `${this.TEAM_API}/team-by-uuid?team-uuid=${teamUuid}`
    );
  }

  updateTeamMembers(payload: any): Observable<any> {
    return this.http.patch<any>(`${this.TEAM_API}/add-members`, payload);
  }
}
