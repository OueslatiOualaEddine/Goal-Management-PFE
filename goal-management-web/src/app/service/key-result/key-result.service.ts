import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { KeyResultDetails } from "app/model/key-result-details.model";
import { Observable } from "rxjs";
import { UtilsService } from "../utils.service";

@Injectable({
  providedIn: "root",
})
export class KeyResultService {
  KEY_RESULT_API = UtilsService.BASE_API_URL + "api/key-result";

  constructor(private http: HttpClient) {}

  save(keyResultRequest: any): Observable<any> {
    return this.http.post<any>(`${this.KEY_RESULT_API}`, keyResultRequest);
  }
  getKeyResultByCompanyOkr(
    companyOkrUuid: string,
    offset: number,
    page: number
  ): Observable<KeyResultDetails[]> {
    let params = new HttpParams();
    if (offset !== undefined && offset !== -1) {
      params = params.set("offset", "" + offset);
    }
    if (page !== undefined && page !== -1) {
      params = params.set("page", "" + page);
    }
    params = params.set("company-okr-uuid", companyOkrUuid);
    return this.http.get<KeyResultDetails[]>(`${this.KEY_RESULT_API}`, {
      params: params,
    });
  }

  deleteKeyResult(uuid: any): Observable<any> {
    return this.http.delete<any>(
      `${this.KEY_RESULT_API}?key-result-uuid=${uuid}`
    );
  }

  archiveKeyResult(uuid: any): Observable<any> {
    return this.http.get<any>(
      `${this.KEY_RESULT_API}/archive?key-result-uuid=${uuid}`
    );
  }

  getKeyResultByManager(): Observable<any> {
    return this.http.get<any>(`${this.KEY_RESULT_API}/list`);
  }

  getKeyResult(uuid: any): Observable<any> {
    return this.http.get<any>(
      `${this.KEY_RESULT_API}/key-result-by-uuid?key-result-uuid=${uuid}`
    );
  }

  getKeyResultByManagerTeam(
    offset: number,
    page: number
  ): Observable<KeyResultDetails[]> {
    let params = new HttpParams();
    if (offset !== undefined && offset !== -1) {
      params = params.set("offset", "" + offset);
    }
    if (page !== undefined && page !== -1) {
      params = params.set("page", "" + page);
    }
    return this.http.get<KeyResultDetails[]>(
      `${this.KEY_RESULT_API}/by-manager-team`,
      {
        params: params,
      }
    );
  }
}
