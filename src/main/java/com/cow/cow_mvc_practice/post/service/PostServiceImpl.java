package com.cow.cow_mvc_practice.post.service;

import com.cow.cow_mvc_practice.member.entity.Member;
import com.cow.cow_mvc_practice.member.repository.MemberJPARepository;
import com.cow.cow_mvc_practice.post.controller.dto.request.PostRequest;
import com.cow.cow_mvc_practice.post.controller.dto.response.PostResponse;
import com.cow.cow_mvc_practice.post.entity.Post;
import com.cow.cow_mvc_practice.post.repository.PostJPARepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

  private final PostJPARepository postRepository;

  @Autowired
  private final MemberJPARepository memberRepository;

  @Override
  public PostResponse join(Long memberId, PostRequest postRequest) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
    Post post = Post.of(postRequest.getTitle(), postRequest.getContent(), member, postRequest.getDate());
    postRepository.save(post);
    return PostResponse.from(post);
  }

  @Transactional(readOnly = true)
  @Override
  public PostResponse findOne(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
    return PostResponse.from(post);
  }

  @Override
  public List<PostResponse> findAll() {
    List<Post> posts = postRepository.findAll();
    return posts.stream()
        .map(PostResponse::from)
        .collect(Collectors.toList());
  }
}