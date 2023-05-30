import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Router } from "@angular/router";
import { UtilsService } from "../utils.service";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  AUTH_API = UtilsService.BASE_API_URL + "api/auth";

  constructor(private http: HttpClient, private router: Router) {}

  signIn(payload: any): Observable<any> {
    const reqHeader = new HttpHeaders({
      "Content-Type": "application/json",
      "No-Auth": "True",
    });

    return this.http.post<any>(`${this.AUTH_API}/signin`, payload, {
      headers: reqHeader,
    });
  }

  refreshToken(): Observable<string> {
    let headers: HttpHeaders = new HttpHeaders({
      "Content-Type": "application/json",
      authExempt: "true",
      "Refresh-token": localStorage.getItem("refreshToken") || "",
    });

    return this.http.post<string>(`${this.AUTH_API}/refresh-token`, null, {
      headers: headers,
    });
  }

  logOut() {
    localStorage.clear();
    this.router.navigateByUrl("/login");
  }
}
