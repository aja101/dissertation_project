package com.mtech.dissertation.enterprisebatch.extractandaggregate;

import org.springframework.batch.item.file.transform.DelimitedLineAggregator;

import com.mtech.dissertation.enterprisebatch.data.EnterpriseOutputData;

public class EnterpiseAggregator extends
		DelimitedLineAggregator<EnterpriseOutputData> {

	@Override
	public String doAggregate(Object[] input) {
		return super.doAggregate(input);
	}

}
