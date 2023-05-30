import { RoleCode } from "./../enumeration/role-code";
export class UserDetailsModel {
  userUuid: string;
  userEmail: string;
  userFirstName: string;
  userLastName: string;
  userPhoneNumber: string;
  userRole: RoleCode;
  userProfilePicture: any;
}
