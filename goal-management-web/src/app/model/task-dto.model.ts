import { Priority } from "app/enumeration/priority";
import { CommentDto } from "./comment-dto.model";
import { TaskStatus } from "./task-status";

export class TaskDto {
  taskUuid: string;
  taskLabel: string;
  taskDescription: string;
  taskPriority: Priority;
  taskStatus: TaskStatus;
  taskResponsibleUuid: string;
  taskCreatorUuid: string;
  responsibleFullName: string;
  creatorFullName: string;
  comments: CommentDto[];
}
