import { KeyResultType } from "app/enumeration/key-result-type";

export class TeamOkrKeyResultDetails {
  keyResultUuid: string;
  keyResultLabel: string;
  keyResultStartValue: number;
  keyResultTargetValue: number;
  keyResultType: KeyResultType;
  teamOKRUuid: string;
  advancement: number;
}
