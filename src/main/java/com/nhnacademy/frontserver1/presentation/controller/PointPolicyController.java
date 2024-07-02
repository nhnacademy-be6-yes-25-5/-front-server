package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.PointPolicyService;
import com.nhnacademy.frontserver1.presentation.dto.request.user.PointPolicyRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointPolicyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/point-policies")
public class PointPolicyController {

    final PointPolicyService pointPolicyService;        // todo : 장바구니 프론트 구현

    @GetMapping
    public String getPointPolicies(@RequestParam(defaultValue = "0") int page, // todo : @PageableDefault 변경 예정
                                   @RequestParam(defaultValue = "5") int size,
                                   Model model) {
        Page<PointPolicyResponse> pointPolicies = pointPolicyService.getPointPolicies(PageRequest.of(page, size));

        model.addAttribute("pointPolicies", pointPolicies);

        return "admin/point/admin-point";
    }

    @PostMapping
    public String createPointPolicy(@ModelAttribute PointPolicyRequest pointPolicyRequest) {

        pointPolicyService.createPointPolicy(pointPolicyRequest);

        return "redirect:/admin/point-policies";
    }

    @PutMapping("/{pointPolicyId}")
    public ResponseEntity<Void> updatePointPolicy(@PathVariable Long pointPolicyId,
                                    @RequestBody PointPolicyRequest pointPolicyRequest) {

        pointPolicyService.updatePointPolicy(pointPolicyId, pointPolicyRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{pointPolicyId}")
    public ResponseEntity<PointPolicyResponse> deletePointPolicy(@PathVariable Long pointPolicyId) {

        pointPolicyService.deletePointPolicy(pointPolicyId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{pointPolicyId}")
    public ResponseEntity<PointPolicyResponse> getPointPolicy(@PathVariable Long pointPolicyId) {

        PointPolicyResponse pointPolicy = pointPolicyService.getPointPolicyById(pointPolicyId);

        return ResponseEntity.ok(pointPolicy);
    }
}
