package com.sap.cap.esmapi.vhelps.srv.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sap.cap.esmapi.vhelps.pojos.TY_CatgCountryMappingList;
import com.sap.cap.esmapi.vhelps.pojos.TY_KeyValue;
import com.sap.cap.esmapi.vhelps.srv.intf.IF_FilterDDLB4VHelp;

@Service("LSO_CountryByCatgFilter")
public class CL_FilterLSO_Country4Catg implements IF_FilterDDLB4VHelp
{

    @Autowired
    private TY_CatgCountryMappingList catgCountryList;

    @Override
    public List<TY_KeyValue> filterValueHelpbyCriteria(Object[] criteria, List<TY_KeyValue> ddlbVals)
    {
        if (criteria == null)
        {
            return ddlbVals;
        }
        else
        {
            List<TY_KeyValue> fltdVals = null;
            if (StringUtils.hasText(criteria[0].toString()))
            {
                // Grab the Current Category
                String catgCurr = criteria[0].toString().toUpperCase();
                if (StringUtils.hasText(catgCurr)
                        && CollectionUtils.isNotEmpty(catgCountryList.getCatgCountryMappingsList()))
                {
                    // Get List of Countries for Current Category

                    // Check if each thus obtained exists in ddlbVals

                    // --- If So Append to fltdVals
                }

            }

            return fltdVals;
        }
    }

}
