package com.egemmerce.hc.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egemmerce.hc.auction.service.ReverseAuctionParticipantService;
import com.egemmerce.hc.item.service.ItemBuyService;
import com.egemmerce.hc.item.service.ItemService;
import com.egemmerce.hc.repository.dto.Item;
import com.egemmerce.hc.repository.dto.ItemBuy;
import com.egemmerce.hc.repository.dto.ReverseAuctionParticipant;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/itemBuy")
public class ItemBuyController {
	
	@Autowired
	private ItemBuyService itemBuyService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ReverseAuctionParticipantService reverseAuctionParticipantService;
	
	/* C :: 상품 등록 */
	@ApiOperation(value = "ib_auction_price,ib_category_main,ib_cool_price,ib_deal_address,ib_name,ib_start_date,ib_user_no,ib_end_date")
	@PostMapping("/regist")
	public ResponseEntity<String> createItem(@RequestBody ItemBuy itemBuy) throws Exception {
		Item item=itemService.insert(Item.builder().iType("Buy").build());
		itemBuy.setIbItemNo(item.getiNo());
		if(itemBuyService.insertItemBuy(itemBuy) != null)
			return new ResponseEntity<String>("상품 등록 성공", HttpStatus.OK);
		return new ResponseEntity<String>("상품 등록 실패", HttpStatus.NO_CONTENT);
	}
	
	/* R :: 상품 전체조회 */
	@GetMapping("/all")
	public ResponseEntity<Page<ItemBuy>> selectItemAll(Pageable pageable) throws Exception {
		return new ResponseEntity<Page<ItemBuy>>(itemBuyService.selectItemBuyAll(pageable), HttpStatus.OK);
	}
	
	
	/* R :: 상품명 조회 */
	@GetMapping("/name")
	public ResponseEntity<Page<ItemBuy>> selectItemByiName(String ibName,Pageable pageable) throws Exception {
		return new ResponseEntity<Page<ItemBuy>>(itemBuyService.selectItemBuyByibName(ibName,pageable), HttpStatus.OK);
	}
	
	
	
	/* U :: 상품 업데이트(거래완료) */
	@PutMapping("/updateDealCompleted")
	public ResponseEntity<String> updateItem(@RequestBody ItemBuy itemBuy) throws Exception {
		if(itemService.updateItemDealCompleted(itemBuy.getIbItemNo()) != null)
			return new ResponseEntity<String>("거래완료 처리 성공", HttpStatus.OK);
		return new ResponseEntity<String>("거래완료 처리 실패", HttpStatus.NO_CONTENT);
	}
	/* U :: 상품 업데이트 */
	@PutMapping("/update")
	public ResponseEntity<String> updateItemSell(@RequestBody ItemBuy itemBuy) throws Exception {
		if(itemBuyService.updateItemBuy(itemBuy) != null)
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		return new ResponseEntity<String>("Fail", HttpStatus.NO_CONTENT);
	}
	
	/* D :: 상품 삭제 */
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteItem(int ibItemNo) throws Exception {
		if(itemBuyService.deleteItemBuy(ibItemNo))
			return new ResponseEntity<String>("상품 삭제 성공", HttpStatus.OK);
		return new ResponseEntity<String>("상품 삭제 실패", HttpStatus.NO_CONTENT);
	}
	
	/* 경매 입찰 */
	@PutMapping("/auction")
	public ResponseEntity<String> updateReverseAuction(int ibUserNo, int ibItemNo, int ibAuctionPrice) throws Exception {
		ItemBuy itemBuy = itemBuyService.selectItemBuybyibItemNo(ibItemNo);
		if (itemBuy.getIbAuctionPrice() < ibAuctionPrice) {
			return new ResponseEntity<String>("기존 경매가보다 큽니다.", HttpStatus.OK);
		}
		if (itemBuyService.updateReverseAuctionPrice(
				ItemBuy.builder().ibItemNo(ibItemNo).ibAuctionPrice(ibAuctionPrice).build()) != null) {
			ReverseAuctionParticipant reverseAuctionParticipant = ReverseAuctionParticipant.builder().rapItemNo(ibItemNo).rapBid(ibAuctionPrice).rapUserNo(ibUserNo).build();
			reverseAuctionParticipant.generaterapDate();
			reverseAuctionParticipantService.insert(reverseAuctionParticipant);
			return new ResponseEntity<String>("역경매가 업데이트 성공.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("역경매가 업데이트 실패.", HttpStatus.OK);
	}
}