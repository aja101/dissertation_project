package com.mtech.dissertation.enterprisebatch.extractandaggregate;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;

import com.mtech.dissertation.enterprisebatch.data.EnterpriseOutputData;

public class EntepriseExtractor extends
		BeanWrapperFieldExtractor<EnterpriseOutputData> {

	@Override
	public Object[] extract(EnterpriseOutputData enteriseData) {
		Object[] output = super.extract(enteriseData);
		return output;
	}

}
