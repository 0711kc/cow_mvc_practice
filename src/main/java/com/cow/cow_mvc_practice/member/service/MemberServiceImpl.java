package com.cow.cow_mvc_practice.member.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cow.cow_mvc_practice.member.controller.dto.request.MemberRequest;
import com.cow.cow_mvc_practice.member.controller.dto.request.UpdateMemberRequest;
import com.cow.cow_mvc_practice.member.controller.dto.response.MemberResponse;
import com.cow.cow_mvc_practice.member.entity.Member;
import com.cow.cow_mvc_practice.member.repository.MemberJPARepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
	private final MemberJPARepository memberRepository;

	@Override
	public ResponseEntity<MemberResponse> join(MemberRequest memberRequest) {
		Member member = memberRequest.toEntity();
		Member savedMember = memberRepository.save(member);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(MemberResponse.from(savedMember));
	}

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<MemberResponse> findOne(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(MemberResponse.from(member));
	}

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<List<MemberResponse>> findAll() {
		List<Member> members = memberRepository.findAll();
		List<MemberResponse> memberResponses = members.stream()
			.map(MemberResponse::from)
			.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(memberResponses);
	}

	@Override
	public ResponseEntity<Void> delete(Long memberId) {
		boolean isExistMember = memberRepository.existsById(memberId);
		if (!isExistMember) {
			throw new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다.");
		}
		memberRepository.deleteById(memberId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<MemberResponse> updateById(Long memberId, UpdateMemberRequest updateMemberRequest) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		member.updateMemberName(updateMemberRequest.getName());
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(MemberResponse.from(member));
	}

	@Override
	public ResponseEntity<MemberResponse> updateImageById(Long memberId, String profileImage) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		profileImage = Optional.ofNullable(profileImage).orElseThrow(IllegalArgumentException::new);
		member.updateProfileImage(profileImage);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(MemberResponse.from(member));
	}
}
