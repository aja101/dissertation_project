package com.mtech.dissertation.enterprisebatch.processor;

import java.sql.Date;

import org.springframework.batch.item.ItemProcessor;

import com.mtech.dissertation.enterprisebatch.data.EnterpriseData;
import com.mtech.dissertation.enterprisebatch.data.EnterpriseOutputData;

public class EnterpriseItemProcessor implements
		ItemProcessor<EnterpriseData, EnterpriseOutputData> {

	@Override
	public EnterpriseOutputData process(final EnterpriseData enterpriseData)
			throws Exception {
		EnterpriseOutputData outputdata = new EnterpriseOutputData();
		outputdata.setId(enterpriseData.getId());
		outputdata.setFirstName(enterpriseData.getFirstName());
		outputdata.setLastName(enterpriseData.getLastName());
		outputdata.setGender(enterpriseData.getGender());
		outputdata.setEmail(enterpriseData.getEmail());
		outputdata.setAge(enterpriseData.getAge());
		outputdata.setCompanytenure(enterpriseData.getCompanytenure());
		outputdata.setTodaydate(new Date(new java.util.Date().getTime()));
		outputdata.setWorkinghour(enterpriseData.getWorkinghour());

		if (enterpriseData.getWorkinghour() <= 6) {
			outputdata.setDefaulter(true);
		}
		else{
			outputdata.setDefaulter(false);
		}
			
		return outputdata;
	}

}