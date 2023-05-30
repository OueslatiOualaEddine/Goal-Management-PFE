import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { TeamOkrKeyResultDetails } from "app/model/team-okr-key-Result-Details.model";
import { Observable } from "rxjs";
import { UtilsService } from "../utils.service";

@Injectable({
  providedIn: "root",
})
export class TeamOkrKeyResultService {
  TEAM_OKR_KEY_RESULT_API =
    UtilsService.BASE_API_URL + "api/team-okr-key-result";

  constructor(private http: HttpClient) {}

  save(keyResultRequest: any): Observable<any> {
    return this.http.post<any>(
      `${this.TEAM_OKR_KEY_RESULT_API}`,
      keyResultRequest
    );
  }
  getKeyResultByTeamOkr(
    teamOkrUuid: string,
    offset: number,
    page: number
  ): Observable<TeamOkrKeyResultDetails[]> {
    let params = new HttpParams();
    if (offset !== undefined && offset !== -1) {
      params = params.set("offset", "" + offset);
    }
    if (page !== undefined && page !== -1) {
      params = params.set("page", "" + page);
    }
    params = params.set("team-okr-uuid", teamOkrUuid);
    return this.http.get<TeamOkrKeyResultDetails[]>(
      `${this.TEAM_OKR_KEY_RESULT_API}`,
      {
        params: params,
      }
    );
  }

  deleteKeyResult(uuid: any): Observable<any> {
    return this.http.delete<any>(
      `${this.TEAM_OKR_KEY_RESULT_API}?key-result-uuid=${uuid}`
    );
  }

  archiveKeyResult(uuid: any): Observable<any> {
    return this.http.get<any>(
      `${this.TEAM_OKR_KEY_RESULT_API}/archive?key-result-uuid=${uuid}`
    );
  }

  getKeyResultByManager(): Observable<any> {
    return this.http.get<any>(`${this.TEAM_OKR_KEY_RESULT_API}/list`);
  }

  getKeyResult(uuid: any): Observable<any> {
    return this.http.get<any>(
      `${this.TEAM_OKR_KEY_RESULT_API}/by-uuid?key-result-uuid=${uuid}`
    );
  }
}
