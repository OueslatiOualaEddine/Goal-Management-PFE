import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UtilsService } from "../utils.service";
import { CommentDto } from "app/model/comment-dto.model";

@Injectable({
  providedIn: "root",
})
export class CommentService {
  COMMENT_API = UtilsService.BASE_API_URL + "api/comment";

  constructor(private http: HttpClient) {}

  save(task: CommentDto, taskUuid): Observable<any> {
    return this.http.post<any>(`${this.COMMENT_API}?task=${taskUuid}`, task);
  }
}
