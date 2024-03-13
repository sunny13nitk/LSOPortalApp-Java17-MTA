package com.sap.cap.esmapi.events.event;

import org.springframework.context.ApplicationEvent;

import com.sap.cap.esmapi.ui.pojos.TY_CaseFormAsync;

import lombok.Getter;

@Getter
public class EV_CaseFormSubmit extends ApplicationEvent
{
    private TY_CaseFormAsync payload;

    public EV_CaseFormSubmit(Object source, TY_CaseFormAsync payload)
    {
        super(source);
        this.payload = payload;

    }

}
