package com.itwillbs.test;

import java.util.List;

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
	public String restaurantInsertPage() {
		return "owner/restaurantInsertPage";
	}
	
	// 가게 추가 클릭시 가게등록 작업
	@PostMapping("restaurantInsertPro")
	public String restaurantInsertPro(RestaurantVO restaurant, Model model, HttpSession session) {
		System.out.println(restaurant);
		// 점주 회원인 경우에만 동작
		String c_id = (String)session.getAttribute("cId");
		restaurant.setC_id(c_id);
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
