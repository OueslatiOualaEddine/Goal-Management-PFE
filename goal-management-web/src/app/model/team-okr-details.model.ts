import { OkrStatus } from "app/enumeration/okr-status";

export class TeamOKRDetails {
  teamOkrUuid: string;
  teamOkrLabel: string;
  teamOkrDescription: string;
  startDate: string;
  endDate: string;
  teamOkrStatus: OkrStatus;
  keyResultUuid: string;
  partnerTeamOKRDetails: TeamOKRDetails[];
  advancement: number;
}
