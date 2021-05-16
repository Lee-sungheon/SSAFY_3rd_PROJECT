package com.egemmerce.hc.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egemmerce.hc.repository.dto.BreakdownBid;
import com.egemmerce.hc.repository.dto.ItemBuySet;
import com.egemmerce.hc.repository.dto.ItemSellSet;
import com.egemmerce.hc.repository.mapper.UserBreakdownMapper;

@Service
public class UserBreakdownServiceImpl implements UserBreakdownService {
	@Autowired
	private UserBreakdownMapper mapper;
	
	@Override
	public List<BreakdownBid> selectBidBreakdown(int uNo) throws Exception {
		return mapper.selectBidBreakdown(uNo);
	}
	
	@Override
	public List<ItemSellSet> selectSellMine(int uNo) throws Exception {
		return mapper.selectSellMine(uNo);
	}
	
	@Override
	public List<ItemBuySet> selectBuyMine(int uNo) throws Exception {
		return mapper.selectBuyMine(uNo);
	}
}