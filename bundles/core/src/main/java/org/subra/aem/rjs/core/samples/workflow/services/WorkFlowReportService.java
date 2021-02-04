package org.subra.aem.rjs.core.samples.workflow.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.subra.aem.rjs.core.samples.workflow.beans.WorkFlowReportBean;

import javax.jcr.Session;
import java.util.List;


public interface WorkFlowReportService {

    List<WorkFlowReportBean> getReportList();

    JSONArray queryBuilder(Session session, String status, String startDate, String endDate) throws JSONException;

}
