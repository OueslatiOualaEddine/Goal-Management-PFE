import { RoleCode } from "./../enumeration/role-code";
export interface UserRequestModel {
  userEmail: string;
  userFirstName: string;
  userLastName: string;
  userPhoneNumber: string;
  userRole: RoleCode;
}
