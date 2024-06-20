package com.cow.cow_mvc_practice.member.controller.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.cow.cow_mvc_practice.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
	private final Long id;
	private final String name;
	private final String profileImage;
	private final List<String> posts;

	@Builder
	private MemberResponse(final Long id, final String name, final String profileImage, final List<String> posts) {
		this.id = id;
		this.name = name;
		this.profileImage = profileImage;
		this.posts = posts;
	}

	public static MemberResponse from(final Member member) {
		List<String> posts = new ArrayList<>();
		member.getPosts().forEach((p) -> posts.add(p.getTitle()));
		return MemberResponse.builder()
			.id(member.getId())
			.name(member.getName())
			.profileImage(member.getProfileImage())
			.posts(posts)
			.build();
	}
}
