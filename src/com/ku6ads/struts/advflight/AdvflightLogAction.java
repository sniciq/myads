package com.ku6ads.struts.advflight;

import java.io.PrintWriter;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.AdvFlight;
import com.ku6ads.services.iface.advflight.AdvFlightService;
import com.ku6ads.struts.basic.BaseAction;

public class AdvflightLogAction extends BaseAction {

	private static final long serialVersionUID = -2484043631568581129L;

	private AdvFlightService advflightService;

	public void getFlightList() {
		try {
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			List<AdvFlight> flightList = advflightService.getFlightList();
			for (AdvFlight advFlight : flightList) {
				StringBuffer sb = new StringBuffer();
				sb.append(advFlight.getId() + ",");
				sb.append(advFlight.getChannelId() + ",");
				sb.append(advFlight.getAdvposId() + ",");
				sb.append(advFlight.getAdvbarId() + ",");
				sb.append(advFlight.getAdvActiveId() + ",");
				sb.append(advFlight.getAdvId() + ",");
				sb.append(advFlight.getProjectId() + ",");
				sb.append(advFlight.getBookId() + ",");
				sb.append(advFlight.getPriceId() + ",");
				sb.append(advFlight.getFormat() + ",");
				sb.append(advFlight.getSaleType() + ",");
				sb.append(advFlight.getBookPackageId() + ",");
				sb.append(advFlight.getScrBPackageId());
				out.println(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AdvFlightService getAdvflightService() {
		return advflightService;
	}

	public void setAdvflightService(AdvFlightService advflightService) {
		this.advflightService = advflightService;
	}

}
