import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Priority } from "app/enumeration/priority";
import { CommentDto } from "app/model/comment-dto.model";
import { MessageDto } from "app/model/message-dto.model";
import { TaskDto } from "app/model/task-dto.model";
import { TaskStatus } from "app/model/task-status";
import { TeamOKRDetails } from "app/model/team-okr-details.model";
import { UserDetailsModel } from "app/model/user-details.model";
import { CommentService } from "app/service/comment/comment.service";
import { TaskService } from "app/service/task/task.service";
import { TeamOkrKeyResultService } from "app/service/team-okr-key-result/team-okr-key-result.service";
import { UserService } from "app/service/user/user.service";
import { UtilsService } from "app/service/utils.service";
import { CKEditorComponent } from "ckeditor4-angular";
import { ToastrService } from "ngx-toastr";
import Swal from "sweetalert2";

@Component({
  selector: "tasks-management",
  templateUrl: "./tasks-management.component.html",
  styleUrls: ["./tasks-management.component.scss"],
})
export class TasksManagementComponent implements OnInit {
  @ViewChild("modalContent", { static: true }) modalContent:
    | TemplateRef<any>
    | undefined;
  @ViewChild("modalUpdateStatusContent", { static: true })
  modalUpdateStatusContent: TemplateRef<any> | undefined;
  @ViewChild("modalReportBugContent", { static: true })
  modalReportBugContent: TemplateRef<any> | undefined;
  @ViewChild("ckeditor", { static: false }) ckeditor: CKEditorComponent | any;

  modalTitle: string;

  keyResult: any;
  taskList: any[] = [];
  taskRequest: TaskDto = new TaskDto();
  isCollaborator: boolean = UtilsService.isCollaborator();

  taskPriorities = Object.keys(Priority).map((key) => ({
    key,
    name: Object(Priority)[key],
  }));
  taskStauts = Object.keys(TaskStatus).map((key) => ({
    key,
    name: Object(TaskStatus)[key],
    disabled: TaskStatus[key] === TaskStatus.FINALIZED && this.isCollaborator,
  }));
  config: {
    allowedContent: boolean;
    fullPage: boolean;
    defaultParagraphSeparator: string;
    toolbarGroups: { name: string; groups: string[] }[];
    removePlugins: string;
    removeButtons: string;
    resize_enabled: boolean;
  };
  userList: any[] = [];
  keyResultUuid: string;

  page: number = 1;
  pageSize: number = 5;
  collectionSize: number = 0;
  teamMembers: UserDetailsModel[] = [];
  messageBody;
  selectedUser;
  constructor(
    private modal: NgbModal,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private keyResultService: TeamOkrKeyResultService,
    private taskService: TaskService,
    private toastr: ToastrService,
    private commentService: CommentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.keyResultUuid = this.activatedRoute.snapshot.paramMap.get("uuid");
    this.getKeyResultDetails();
    this.getAllUser();
    this.getTasks();
    this.getUserTeamMembers();

    this.config = {
      allowedContent: true,
      fullPage: true,
      defaultParagraphSeparator: "p",
      toolbarGroups: [
        {
          name: "basicstyles",
          groups: [
            "Bold",
            "Italic",
            "Underline",
            "-",
            "NumberedList",
            "BulletedList",
            "-",
          ],
        },
        { name: "paragraph", groups: ["list"] },
      ],
      removePlugins: "exportpdf",

      removeButtons: "Styles,Font,FontSize,NewPage,Preview,Print,Save",
      resize_enabled: false,
    };
  }

  onAdd() {
    this.modalTitle = "Ajouter une Tâche";
    this.taskRequest = new TaskDto();
    this.modal.open(this.modalContent, { windowClass: "modal-width" });
  }
  onEdit(task) {
    localStorage.setItem("title", "Modifier une Tâche");
    this.router.navigateByUrl(`/task-details/${task.taskUuid}`);
    /* this.modalTitle = "Modifier une Tâche";
    this.taskRequest = task;
    this.modal.open(this.modalContent, { windowClass: "modal-width" }); */
  }

  getAllUser() {
    this.userService.getListUsers().subscribe((res: any) => {
      this.userList = res.filter((user) => user.userRole != "ADMINISTRATOR");
    });
  }

  onSave() {
    if (
      !this.isEmpty(this.taskRequest.taskLabel) &&
      !this.isEmpty(this.taskRequest.taskResponsibleUuid) &&
      !this.isEmpty(this.taskRequest.taskPriority)
    ) {
      this.taskService
        .save(this.taskRequest, this.keyResultUuid)
        .subscribe((res) => {
          this.getTasks();
          this.modal.dismissAll();
          this.showSuccess("Modification enregistrée avec succès");
        });
    }
  }

  getKeyResultDetails() {
    this.keyResultService.getKeyResult(this.keyResultUuid).subscribe((res) => {
      this.keyResult = res;
    });
  }

  getTasks() {
    this.taskService
      .getTasksByTeamKeyResult(this.keyResultUuid, this.pageSize, this.page - 1)
      .subscribe((res: any) => {
        this.taskList = res.items;
        this.collectionSize = res.count;
      });
  }

  onDelete(task) {
    Swal.fire({
      text: "Voulez vous supprimer cette Tâche ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.taskService.deleteTask(task.taskUuid).subscribe((res) => {
          this.getTasks();
          this.showSuccess("Tâche supprimée avec succès");
        });
      }
    });
  }
  onArchive(task) {
    Swal.fire({
      text: "Voulez vous archiver cette Tâche?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.value) {
        this.taskService.archiveTask(task.taskUuid).subscribe((res) => {
          this.getTasks();
          this.showSuccess("Tâche archivé avec succès");
        });
      }
    });
  }

  showSuccess(msg) {
    this.toastr.success(msg);
  }

  isEmpty(value) {
    return value == null || value.length === 0;
  }

  getStatusBadge(status: string): string {
    if (status.toString() == "TO_DO") {
      return "badge-style-to-do";
    }
    if (status.toString() == "IN_PROGRESS") {
      return "badge-style-pending";
    } else if (status.toString() == "FINALIZED") {
      return "badge-style-finalized";
    } else if (status.toString() == "CANCELED") {
      return "badge-style-canceled";
    }
    return "badge-style-archived";
  }
  getStatus(taskStatus: TaskStatus): string {
    const status = taskStatus.toString();
    return TaskStatus[status as keyof typeof TaskStatus];
  }

  getPriority(taskPriority: Priority): string {
    const priority = taskPriority.toString();
    return Priority[priority as keyof typeof Priority];
  }

  onUpdateStatus(task) {
    this.taskRequest = task;
    this.modal.open(this.modalUpdateStatusContent);
  }

  onUpdate(taskStatus) {
    this.taskRequest.taskStatus = taskStatus;
    this.taskService
      .save(this.taskRequest, this.keyResultUuid)
      .subscribe((res) => {
        this.modal.dismissAll();
        this.getTasks();
      });
  }

  getAbbriviation(firstName: any, lastName: any): string {
    let intials: string = "";
    const fullName = firstName + " " + lastName;
    intials = fullName
      .split(" ")
      .map((name) => name[0])
      .join("")
      .toUpperCase();

    return intials;
  }

  onAddComment(commentText) {
    let commentRequest = new CommentDto();
    commentRequest.commentText = commentText;
    this.commentService
      .save(commentRequest, this.taskRequest.taskUuid)
      .subscribe((res) => {
        this.getTasks();
        this.showSuccess("commentaire ajouté avec succès");
      });
  }

  onPbReport(task) {
    this.messageBody = null;
    this.selectedUser = null;
    this.modal.open(this.modalReportBugContent);
    this.taskRequest = task;
  }
  getUserTeamMembers() {
    this.userService.getUserTeamMembers().subscribe((res) => {
      this.teamMembers = res;
    });
  }

  onSendMsg(messageBody, selectedUser) {
    let messageDto: MessageDto = new MessageDto();
    messageDto.destinationUuid = selectedUser;
    messageDto.messageBody = messageBody;
    messageDto.taskLabel = this.taskRequest.taskLabel;
    this.taskService.reportPb(messageDto).subscribe((res) => {
      this.modal.dismissAll();
      this.showSuccess("Message envoyé avec succès");
    });
  }
}
