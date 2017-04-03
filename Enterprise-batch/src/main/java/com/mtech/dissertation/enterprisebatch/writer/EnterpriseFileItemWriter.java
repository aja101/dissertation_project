package com.mtech.dissertation.enterprisebatch.writer;

import java.util.List;

import org.springframework.batch.item.file.FlatFileItemWriter;

import com.mtech.dissertation.enterprisebatch.data.EnterpriseOutputData;

public class EnterpriseFileItemWriter extends FlatFileItemWriter<EnterpriseOutputData> {

	@Override
	public void write(List<? extends EnterpriseOutputData> arg0) throws Exception {
		
		super.write(arg0);
	}

}
