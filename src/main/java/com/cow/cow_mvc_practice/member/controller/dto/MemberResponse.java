package com.cow.cow_mvc_practice.member.controller.dto;

import com.cow.cow_mvc_practice.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
	private Long id;
	private String name;

	@Builder
	private MemberResponse(final Long id, final String name) {
		this.id = id;
		this.name = name;
	}

	public static MemberResponse of(final Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.name(member.getName())
			.build();
	}
}
