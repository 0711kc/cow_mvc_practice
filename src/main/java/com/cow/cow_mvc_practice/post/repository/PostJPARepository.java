package com.cow.cow_mvc_practice.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cow.cow_mvc_practice.post.entity.Post;

public interface PostJPARepository extends JpaRepository<Post, Long> {
	List<Post> findAllByMemberId(Long memberId);
}
