import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Priority } from "app/enumeration/priority";
import { CommentDto } from "app/model/comment-dto.model";
import { TaskDto } from "app/model/task-dto.model";
import { CommentService } from "app/service/comment/comment.service";
import { TaskService } from "app/service/task/task.service";
import { UserService } from "app/service/user/user.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "task-details",
  templateUrl: "./task-details.component.html",
  styleUrls: ["./task-details.component.scss"],
})
export class TaskDetailsComponent implements OnInit {
  taskUuid: any;
  taskRequest: TaskDto = new TaskDto();
  taskPriorities = Object.keys(Priority).map((key) => ({
    key,
    name: Object(Priority)[key],
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
  comment: any;
  userList: any[] = [];
  constructor(
    private taskService: TaskService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private commentService: CommentService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.taskUuid = this.activatedRoute.snapshot.paramMap.get("uuid");
    this.getTaskDetails();
    this.getAllUser();
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
  getTaskDetails() {
    this.taskService.getTaskDetails(this.taskUuid).subscribe((res) => {
      this.taskRequest = res;
    });
  }
  getAllUser() {
    this.userService.getListUsers().subscribe((res: any) => {
      this.userList = res.filter((user) => user.userRole != "ADMINISTRATOR");
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
  onSave() {
    if (
      !this.isEmpty(this.taskRequest.taskLabel) &&
      !this.isEmpty(this.taskRequest.taskResponsibleUuid) &&
      !this.isEmpty(this.taskRequest.taskPriority)
    ) {
      this.taskService.update(this.taskRequest).subscribe((res) => {
        this.getTaskDetails();
        this.showSuccess("Modification enregistrée avec succès");
      });
    }
  }

  onAddComment(commentText) {
    if (!this.isEmpty(commentText)) {
      let commentRequest = new CommentDto();
      commentRequest.commentText = commentText;
      this.commentService
        .save(commentRequest, this.taskRequest.taskUuid)
        .subscribe((res) => {
          this.getTaskDetails();
          this.comment = null;
          this.showSuccess("commentaire ajouté avec succès");
        });
    }
  }
  showSuccess(msg) {
    this.toastr.success(msg);
  }
  isEmpty(value) {
    return value == null || value.length === 0;
  }
}
