package com.mtech.dissertation.enterprisebatch.reader;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.mtech.dissertation.enterprisebatch.data.EnterpriseData;

public class EnterpriseItemReader implements ItemReader<EnterpriseData> {

	private List<EnterpriseData> enterpriseData;

	private Iterator<EnterpriseData> iterator;

	@Override
	public EnterpriseData read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {

		if (getIterator().hasNext()) {
			return getIterator().next();
		}
		return null;

	}

	public List<EnterpriseData> getEnterpriseData() {
		return enterpriseData;
	}

	public void setEnterpriseData(List<EnterpriseData> enterpriseData) {
		this.enterpriseData = enterpriseData;
	}

	public Iterator<EnterpriseData> getIterator() {
		return iterator;
	}

	public void setIterator(Iterator<EnterpriseData> iterator) {
		this.iterator = iterator;
	}

}
