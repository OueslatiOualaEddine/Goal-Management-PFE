import { Task } from "./../../model/task.model";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { KeyResultDetails } from "app/model/key-result-details.model";
import { Observable } from "rxjs";
import { UtilsService } from "../utils.service";
import { TaskDto } from "app/model/task-dto.model";
import { MessageDto } from "app/model/message-dto.model";

@Injectable({
  providedIn: "root",
})
export class TaskService {
  TASK_API = UtilsService.BASE_API_URL + "api/task";

  constructor(private http: HttpClient) {}

  save(task: TaskDto, keyResultuuid): Observable<any> {
    return this.http.post<any>(
      `${this.TASK_API}?team-key-result=${keyResultuuid}`,
      task
    );
  }
  update(task: TaskDto): Observable<any> {
    return this.http.post<any>(`${this.TASK_API}`, task);
  }
  getTasksByTeamKeyResult(
    keyResultuuid: string,
    offset: number,
    page: number
  ): Observable<any[]> {
    let params = new HttpParams();
    if (offset !== undefined && offset !== -1) {
      params = params.set("offset", "" + offset);
    }
    if (page !== undefined && page !== -1) {
      params = params.set("page", "" + page);
    }
    params = params.set("team-key-result", keyResultuuid);
    return this.http.get<any[]>(`${this.TASK_API}/list`, {
      params: params,
    });
  }

  deleteTask(uuid: string): Observable<any> {
    return this.http.delete<any>(`${this.TASK_API}/${uuid}`);
  }

  archiveTask(uuid: any): Observable<any> {
    return this.http.put<any>(`${this.TASK_API}/archive/${uuid}`, null);
  }

  getTaskDetails(uuid): Observable<any> {
    return this.http.get<any>(
      `${this.TASK_API}/task-details?task-uuid=${uuid}`
    );
  }
  reportPb(meddageDto: MessageDto) {
    return this.http.post<any>(`${this.TASK_API}/report-problem`, meddageDto);
  }
}
