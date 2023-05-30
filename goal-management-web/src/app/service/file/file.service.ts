import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Router } from "@angular/router";
import { UtilsService } from "../utils.service";

@Injectable({
  providedIn: "root",
})
export class FileService {
  FILE_API = UtilsService.BASE_API_URL + "api/file";
  constructor(private http: HttpClient, private router: Router) {}

  deleteProfilePhoto(uuid: any, context: any): Observable<any> {
    return this.http.get<any>(
      `${this.FILE_API}/delete-user-photo?user-uuid=${uuid}&context=${context}`
    );
  }
}
