package com.egemmerce.hc.repository.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(of = "crNo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ChatRoom implements Comparable<ChatRoom> {
	@Id
	private int crNo;

	private String crName;
	private int crItemNo;
	private int crUserNoOne;
	private int crUserNoTwo;
	private String crUserOneProfile;
	private String crUserTwoProfile;
	private String crUserOneName;
	private String crUserTwoName;
	private String crLatestMessage;
	private LocalDateTime crLatestMessageTime;

	@Override
	public int compareTo(ChatRoom cr) {
		if (this.crLatestMessageTime.isBefore(cr.crLatestMessageTime)) {
			return -1;
		} else if (this.crLatestMessageTime.isAfter(cr.crLatestMessageTime)) {
			return 1;
		}
		return 0;
	}

	public int getCrNo() {
		return crNo;
	}

	public void setCrNo(int crNo) {
		this.crNo = crNo;
	}

	public String getCrName() {
		return crName;
	}

	public void setCrName(String crName) {
		this.crName = crName;
	}

	public int getCrItemNo() {
		return crItemNo;
	}

	public void setCrItemNo(int crItemNo) {
		this.crItemNo = crItemNo;
	}

	public int getCrUserNoOne() {
		return crUserNoOne;
	}

	public void setCrUserNoOne(int crUserNoOne) {
		this.crUserNoOne = crUserNoOne;
	}

	public int getCrUserNoTwo() {
		return crUserNoTwo;
	}

	public void setCrUserNoTwo(int crUserNoTwo) {
		this.crUserNoTwo = crUserNoTwo;
	}

	public String getCrUserOneProfile() {
		return crUserOneProfile;
	}

	public void setCrUserOneProfile(String crUserOneProfile) {
		this.crUserOneProfile = crUserOneProfile;
	}

	public String getCrUserTwoProfile() {
		return crUserTwoProfile;
	}

	public void setCrUserTwoProfile(String crUserTwoProfile) {
		this.crUserTwoProfile = crUserTwoProfile;
	}

	public String getCrUserOneName() {
		return crUserOneName;
	}

	public void setCrUserOneName(String crUserOneName) {
		this.crUserOneName = crUserOneName;
	}

	public String getCrUserTwoName() {
		return crUserTwoName;
	}

	public void setCrUserTwoName(String crUserTwoName) {
		this.crUserTwoName = crUserTwoName;
	}

	public String getCrLatestMessage() {
		return crLatestMessage;
	}

	public void setCrLatestMessage(String crLatestMessage) {
		this.crLatestMessage = crLatestMessage;
	}

	public LocalDateTime getCrLatestMessageTime() {
		return crLatestMessageTime;
	}

	public void setCrLatestMessageTime(LocalDateTime crLatestMessageTime) {
		this.crLatestMessageTime = crLatestMessageTime;
	}

}