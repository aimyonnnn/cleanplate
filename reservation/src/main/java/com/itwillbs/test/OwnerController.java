package com.itwillbs.test;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.itwillbs.test.service.*;
import com.itwillbs.test.vo.*;

@Controller
public class OwnerController {
	
	@Autowired
	private RestaurantService service;
	@Autowired
	private ReservationService resService;
	@Autowired
	private CeoService ceoService;
	
	// 매출관리
	@GetMapping("storeDashBoard")
	public String storeDashBoard() {
		return "owner/storeDashBoard";
	}
	
	/* 예약관리 페이지 */
	//owner의 예약관리 페이지 이동 Mapping
	@GetMapping("restaurantReservation")
	public String restaurantReservation(Model model, HttpSession session) {
		
		System.out.println((String)session.getAttribute("cId"));
		
		List<ReservationVO> resList = service.getReservationList((String)session.getAttribute("cId"));
		List<RestaurantVO> restaurantList = resService.getOwnerRestaurantList((String)session.getAttribute("cId"));
		
		System.out.println(resList);
		
		model.addAttribute("restaurantList", restaurantList);
		model.addAttribute("resList",resList);
		
		return "owner/restaurantReservation";
	}
	
	/* 매출관리 페이지 */
	//그래프
	@GetMapping("ownerSalesGraph")
	public String ownerSalesGraph(){
		return "owner/ownerSalesGraph";
	}
	
	/* 식당관리 페이지 */
	//owner의 식당추가로 이동 Mapping
	@GetMapping("restaurantInsertPage")
	public String restaurantInsertPage(HttpSession session, Model model) {
		// 미로그인, 점주 회원이 아닐 경우 
		String c_Id = (String)session.getAttribute("cId");
		if(c_Id == null) {
			model.addAttribute("msg", "로그인 해주세요");
			return "fail_back";
		}
		return "owner/restaurantInsertPage";
	}
	
	// 가게 추가 클릭시 가게등록 작업
	@PostMapping("restaurantInsertPro")
	public String restaurantInsertPro(RestaurantVO restaurant, Model model, HttpSession session) {
		System.out.println(restaurant);
		// 미로그인, 점주 회원이 아닐 경우
		String c_Id = (String)session.getAttribute("cId");
		if(c_Id == null) {
			model.addAttribute("msg", "로그인 해주세요");
			return "fail_back";
		}
		
		// ---------------------------------------------------------------
		// 업로드(upload) 폴더 생성 
		// 실제 업로드 경로
		String uploadDir = "/resources/upload";
		String saveDir = session.getServletContext().getRealPath(uploadDir);
		System.out.println("실제 업로드 경로 : "+ saveDir);
		
		String subDir = ""; // 서브디렉토리(날짜 구분)
		
		// 날짜별 디렉토리 자동 분류 
		try {
			// 1. Date 객체 생성(기본 생성자 호출하여 시스템 날짜 정보 활용)
			Date date = new Date(); 

			// 2. SimpleDateFormat 클래스 활용
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			// 3. 기존 업로드 경로에 날짜 경로 결합하여 저장
			saveDir += "/" + sdf.format(date);
			saveDir += subDir;
			
			// java.nio.file.Paths 클래스의 get() 메서드 호출
			Path path = Paths.get(saveDir);
			
			// Files 클래스의 createDirectories() 메서드 호출
			Files.createDirectories(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ---------------------------------------------------------------
		// VO MultipartFile 객체 꺼내기
		
		
		
		// 파일명 중복 방지
		String uuid = UUID.randomUUID().toString();
		
		
		// c_idx 가져오기 위해 VO에 저장 
		restaurant.setC_id(c_Id);
		// 사업자등록번호 중복시 실패 => api로 한댔음
		int insertCount = service.registStore(restaurant);
		
		// 성공시 storeInsertSucess 리다이렉트 
		// 실패시 fail_back.jsp 가게등록 실패 출력
		if(insertCount > 0) { // 성공
			return "redirect:/restaurantList";
		} else { // 실패
			model.addAttribute("msg", "가게 등록 실패!");
			return "fail_back";
		}
		
	}
	
//	// 가게 등록 성공시 StoreList로 리다이렉트
//	@GetMapping("restaurantInsertSucess")
//	public String restaurantInsertSucess() {
//		return "redirect:/restaurantList";
//	}
	
	//owner의 식당리스트로 이동 Mapping
	// 가게 목록 조회
	@GetMapping("restaurantList")
	public String restaurantList(Model model) {
		List<RestaurantVO> restaurantList = service.getRestaurantList();
		model.addAttribute("restaurantList", restaurantList);
		return "owner/restaurantList";
	}
	
	//owner의 가게 수정 페이지로 이동 Mapping
	@GetMapping("restaurantUpdatePage")
	public String restaurantUpdatePage(@RequestParam int res_idx, Model model) {
		
		// 가게 정보 불러오기
		RestaurantVO restaurant = service.getRestaurantInfo(res_idx);
		
//		model.addAttribute("res_idx", res_idx);
		model.addAttribute("restaurant", restaurant);
		return "owner/restaurantUpdatePage";
	}
	// 가게 정보 수정 작업
	@PostMapping("restaurantUpdate")
	public String restaurantUpdate(RestaurantVO restaurant, Model model) {
		// 가게 정보 수정 작업
		int updateCount = service.ModifyRestaurant(restaurant);
		// 성공시  success_forward.jsp 로 이동 가게 정보 수정 완료 출력
		// 실패시 가게 정보 수정 실패! 출력
		if(updateCount > 0) {
			model.addAttribute("msg", "가게 정보 수정 완료");
			model.addAttribute("targetURL", "restaurantList");
			return "success_forward";
			
		} else {
			model.addAttribute("msg", "가게 정보 수정 실패!");
			return "fail_back";
			
		}
	}
	
	//owner의 식당마이페이지에서 수정후 이동 Mapping
	@PostMapping("StoreMypagePro")
	public String StoreMypagePro() {
		return "owner/ownerList";
	}
	
	/* 점주마이페이지 */
	//owner의 점주마이페이지로 이동 Mapping
	@GetMapping("ownerMypage")
	public String CeoMypage() {
		return "owner/ownerMypage";
	}
	
	//owner의 점주마이페이지에서 수정후 이동 Mapping
	@PostMapping("CeoMypagePro")
	public String CeoMypagePro() {
		return "owner/ownerMypage";
	}
	
	//owner의 점주회원탈퇴 페이지로 이동 Mapping
	@GetMapping("ownerWithdrawal")
	public String ownerWithdrawal() {
		return "owner/ownerWithdrawal";
	}
	
	@PostMapping("ownerWithdrawalPro")
	public String ownerWithdrawalPro(HttpSession session, @RequestParam(defaultValue = "") String c_passwd, Model model) {
		
		if(c_passwd.equals("")) {
			model.addAttribute("msg","비밀번호를 입력해주세요.");
			return "fail_back";
		}
		
		String id = (String)session.getAttribute("cId");
		
		CeoVO ceo = ceoService.SelectCeo(id);
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if(!passwordEncoder.matches(c_passwd, ceo.getC_passwd())) {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			return "fail_back";
		}
		
		int deleteCount = ceoService.secessionCeo(ceo.getC_idx());
		
		if(deleteCount <= 0) {
			model.addAttribute("msg","회원탈퇴를 실패하였습니다.");
			return "fail_back";
		}
		
		session.invalidate();
		
		model.addAttribute("success","회원탈퇴 되었습니다.\\n이후에 다시 뵙길 기원하겠습니다.");
		return "secession_success";
	}

	
	//owner의 점주정보 페이지로 이동 Mapping
	@PostMapping("ownerInfo")
	public String ownerInfo() {
		return "owner/ownerInfo";
	}
	

	
	
}
