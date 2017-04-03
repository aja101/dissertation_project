package com.mtech.dissertation.enterprisebatch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.mtech.dissertation.enterprisebatch.data.EnterpriseOutputData;

public class EnterpriseItemWriter implements ItemWriter<EnterpriseOutputData> {

	@Override
	public void write(List<? extends EnterpriseOutputData> enterpriseData) throws Exception {
		System.out.println("Persisting enteriseData " + enterpriseData);
	}

}
