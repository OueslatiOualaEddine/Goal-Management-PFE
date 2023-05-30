import { KeyResultType } from "app/enumeration/key-result-type";
import { TeamRequest } from "./team-request.model";

export class KeyResultDetails {
  keyResultUuid: string;
  keyResultLabel: string;
  keyResultStartValue: number;
  keyResultTargetValue: number;
  keyResultType: KeyResultType;
  teamList?: TeamRequest[];
  teamOkrNumber: number;
  companyOKRUuid: string;
}
