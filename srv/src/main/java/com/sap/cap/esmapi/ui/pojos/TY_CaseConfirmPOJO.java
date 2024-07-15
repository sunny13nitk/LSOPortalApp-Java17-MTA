package com.sap.cap.esmapi.ui.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TY_CaseConfirmPOJO
{
    private String caseId;
    private String caseGuid;
    private String caseType;
    private String origin;
    private String status;
    private String eTag;
    private String cnfStatusCode;
}
