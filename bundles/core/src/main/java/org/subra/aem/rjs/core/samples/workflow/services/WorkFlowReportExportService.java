package org.subra.aem.rjs.core.samples.workflow.services;


import org.subra.aem.rjs.core.samples.workflow.beans.WorkFlowReportBean;

import java.util.List;

/**
 * @author RA324710
 */

public interface WorkFlowReportExportService {

    StringBuilder populateCSVContent(List<WorkFlowReportBean> reportList);

}
