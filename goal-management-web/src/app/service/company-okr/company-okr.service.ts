import { UserDetailsModel } from "./../../model/user-details.model";
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
  HttpParams,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, map, Observable, throwError } from "rxjs";
import { UtilsService } from "../utils.service";

@Injectable({
  providedIn: "root",
})
export class CompanyOKRService {
  COMPANY_OKR_API = UtilsService.BASE_API_URL + "api/company-okr";

  constructor(private http: HttpClient) {}

  getListUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.COMPANY_OKR_API}/list`);
  }

  getAllUsers(offset: number, page: number): Observable<UserDetailsModel[]> {
    let params = new HttpParams();
    if (offset !== undefined && offset !== -1) {
      params = params.set("offset", "" + offset);
    }
    if (page !== undefined && page !== -1) {
      params = params.set("page", "" + page);
    }
    return this.http.get<UserDetailsModel[]>(`${this.COMPANY_OKR_API}`, {
      params: params,
    });
  }

  save(companyOKRRequest: any): Observable<any> {
    return this.http.post<any>(
      `${this.COMPANY_OKR_API}/add`,
      companyOKRRequest
    );
    /* .pipe(
        map((res) => res.data),
        catchError((error: HttpErrorResponse) => {
          return throwError(error);
        }) 
      );*/
  }
  deleteCompanyOkr(uuid: any): Observable<any> {
    return this.http.delete<any>(
      `${this.COMPANY_OKR_API}?company-okr-uuid=${uuid}`
    );
  }

  archiveCompanyOkr(uuid: any): Observable<any> {
    return this.http.get<any>(
      `${this.COMPANY_OKR_API}/archive?company-okr-uuid=${uuid}`
    );
  }

  getCompanyOkrByUuid(companyOkr: string): Observable<any> {
    return this.http.get<any>(
      `${this.COMPANY_OKR_API}/company-okr-by-uuid?company-okr-uuid=${companyOkr}`
    );
  }
}
