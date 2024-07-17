package com.sap.cap.esmapi.events.handlers;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sap.cap.esmapi.catg.pojos.TY_CatgCus;
import com.sap.cap.esmapi.catg.pojos.TY_CatgCusItem;
import com.sap.cap.esmapi.events.event.EV_CaseConfirmSubmit;
import com.sap.cap.esmapi.events.event.EV_CaseReplySubmit;
import com.sap.cap.esmapi.events.event.EV_LogMessage;
import com.sap.cap.esmapi.exceptions.EX_ESMAPI;
import com.sap.cap.esmapi.utilities.enums.EnumMessageType;
import com.sap.cap.esmapi.utilities.enums.EnumStatus;
import com.sap.cap.esmapi.utilities.pojos.TY_AttachmentResponse;
import com.sap.cap.esmapi.utilities.pojos.TY_Attachment_CaseCreate;
import com.sap.cap.esmapi.utilities.pojos.TY_CaseDetails;
import com.sap.cap.esmapi.utilities.pojos.TY_CasePatchInfo;
import com.sap.cap.esmapi.utilities.pojos.TY_CaseReplyNote;
import com.sap.cap.esmapi.utilities.pojos.TY_Case_SrvCloud_Reply;
import com.sap.cap.esmapi.utilities.pojos.TY_Message;
import com.sap.cap.esmapi.utilities.pojos.TY_NotesCreate;
import com.sap.cap.esmapi.utilities.srvCloudApi.destination.pojos.TY_DestinationProps;
import com.sap.cap.esmapi.utilities.srvCloudApi.srv.intf.IF_SrvCloudAPI;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EV_HDLR_CaseConfirmSubmit
{
    @Autowired
    private MessageSource msgSrc;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private IF_SrvCloudAPI srvCloudApiSrv;

    @Autowired
    private TY_CatgCus config;

    @Async
    @EventListener
    public void handleCaseConfirmSubmission(EV_CaseConfirmSubmit evCaseCnfSubmit)
    {
        if (evCaseCnfSubmit != null && config != null)
        {
            if (StringUtils.hasText(evCaseCnfSubmit.getPayload().getCaseGuid())
                    && StringUtils.hasText(evCaseCnfSubmit.getPayload().getETag())
                    && evCaseCnfSubmit.getPayload().getDesProps() != null)
            {

                // prepare the Payload for PATCH operation for the case

                // Initialize PAyload
                TY_Case_SrvCloud_Reply caseReplyPayload = new TY_Case_SrvCloud_Reply();

                // if (caseReplyPayload != null)
                // {
                // // Invoke Srv cloud API to Patch/Update the Case
                // if (srvCloudApiSrv
                // .updateCasewithReply(
                // new TY_CasePatchInfo(caseDetails.getCaseGuid(),
                // evCaseReply.getPayload().getCaseReply().getCaseDetails()
                // .getCaseId(),
                // caseDetails.getETag()),
                // caseReplyPayload, desProps))
                // {
                // handleCaseSuccUpdated(
                // evCaseReply.getPayload().getCaseReply().getCaseDetails().getCaseId(),
                // evCaseReply.getPayload().getSubmGuid(), evCaseReply);
                // }
                // }
            }
            // catch (EX_ESMAPI | IOException e)
            // {
            // // Handle Case Details Fetch Error
            // handleCaseDetailsFetchError(evCaseReply, e);
            // }

        }

    }

    private void handleCaseSuccUpdated(String string, String submId, EV_CaseReplySubmit evCaseReply)
    {
        String msg;
        // Reply for Case with id - {0} updated successfully for Submission id - {1}.
        msg = msgSrc.getMessage("SUCC_CASE_REPLY_UPDATE", new Object[]
        { string, submId }, Locale.ENGLISH);

        log.info(msg);
        TY_Message logMsg = new TY_Message(evCaseReply.getPayload().getUserId(), Timestamp.from(Instant.now()),
                EnumStatus.Success, EnumMessageType.SUCC_CASE_REPL_SAVE, evCaseReply.getPayload().getSubmGuid(), msg);

        // Instantiate and Fire the Event
        EV_LogMessage logMsgEvent = new EV_LogMessage((Object) evCaseReply.getPayload().getSubmGuid(), logMsg);
        applicationEventPublisher.publishEvent(logMsgEvent);
    }

    private void handleCaseDetailsFetchError(EV_CaseReplySubmit evCaseReply, Exception e)
    {
        String msg;
        // Error Fetching Case Details for Case ID - {0} for Case Reply Submission id -
        // {1} for User - {2}! Details - {3} .
        msg = msgSrc.getMessage("ERR_CASE_DET_FETCH_REPL_SUBM", new Object[]
        { evCaseReply.getPayload().getCaseReply().getCaseDetails().getCaseId(), evCaseReply.getPayload().getSubmGuid(),
                evCaseReply.getPayload().getUserId(), e.getLocalizedMessage(), }, Locale.ENGLISH);

        log.error(msg);
        TY_Message logMsg = new TY_Message(evCaseReply.getPayload().getUserId(), Timestamp.from(Instant.now()),
                EnumStatus.Error, EnumMessageType.ERR_CASE_REPL_SAVE, evCaseReply.getPayload().getSubmGuid(), msg);

        // Instantiate and Fire the Event
        EV_LogMessage logMsgEvent = new EV_LogMessage((Object) evCaseReply.getPayload().getSubmGuid(), logMsg);
        applicationEventPublisher.publishEvent(logMsgEvent);

        // Should be handled Centrally via Aspect
        throw new EX_ESMAPI(msg);
    }
}
