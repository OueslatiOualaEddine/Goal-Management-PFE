import { Priority } from "app/enumeration/priority";
import { TaskStatus } from "app/enumeration/taskStatus";

export interface Task {
  email: string;
  userPassword: string;
  taskUuid: string;
  taskLabel: string;
  taskDescription: string;
  taskPriority: Priority;
  taskStatus: TaskStatus;
  taskResponsibleUuid: string;
  taskCreatorUuid: string;
  responsibleFullName: string;
  creatorFullName: string;
}
