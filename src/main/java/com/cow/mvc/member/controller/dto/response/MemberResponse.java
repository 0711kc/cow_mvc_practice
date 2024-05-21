package com.cow.mvc.member.controller.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.cow.mvc.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
	private final Long id;
	private final String name;
	private final List<String> posts;

	@Builder
	private MemberResponse(final Long id, final String name, final List<String> posts) {
		this.id = id;
		this.name = name;
		this.posts = posts;
	}

	public static MemberResponse from(final Member member) {
		List<String> posts = new ArrayList<>();
		member.getPosts().forEach((p) -> posts.add(p.getTitle()));
		return MemberResponse.builder()
			.id(member.getId())
			.name(member.getName())
			.posts(posts)
			.build();
	}
}