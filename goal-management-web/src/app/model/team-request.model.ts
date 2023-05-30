import { UserDetailsModel } from "./user-details.model";

export class TeamRequest {
  teamUuid: string;
  teamName: string;
  members: UserDetailsModel[];
  manager: UserDetailsModel;
}
