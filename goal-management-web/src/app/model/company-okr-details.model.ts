import { OkrStatus } from "app/enumeration/okr-status";

export class CompanyOKRDetails {
  campanyOkrUuid: string;
  campanyOkrName: string;
  campanyOkrDescription: string;
  startDate: string;
  endDate: string;
  campanyOkrStatus: OkrStatus;
}
