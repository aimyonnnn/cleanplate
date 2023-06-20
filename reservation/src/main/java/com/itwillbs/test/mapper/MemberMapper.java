package com.itwillbs.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.test.vo.MemberVO;
import com.itwillbs.test.vo.ReservationVO;
import com.itwillbs.test.vo.RestaurantVO;

@Mapper
public interface MemberMapper {
	
	// 개인회원 등록
	int insertMember(MemberVO member);
	
	// 임시비밀번호 업데이트
	int updatePassword(MemberVO membervo);

	// 맴버 정보 수정을 위한 아이디 및 패스워드 조회
	MemberVO isCorrectMember(@Param("id") String id, @Param("passwd") String passwd);

	// 멤버 정보 수정
	int updateMember(MemberVO member);

	// 멤버 정보 조회
	MemberVO selectMember(@Param("id") String id);
	
	// 양도 결제시 필요한 고객 정보
	MemberVO selectMemberInfo(String sId);

	// 카카오 로그인 시 회원 email을 조회 후 m_id를 리턴받아 세션id에 저장
	MemberVO selectMemberId(String email);

	List<ReservationVO> selectMemberReservation(String sId);

	List<RestaurantVO> selectMemberRestaurant(String sId);

}
