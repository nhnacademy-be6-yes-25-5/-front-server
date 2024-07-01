package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.PointPolicyService;
import com.nhnacademy.frontserver1.presentation.dto.request.user.PointPolicyRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointPolicyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/point-policies")
public class PointPolicyController {

    final PointPolicyService pointPolicyService;

    @GetMapping
    public String getPointPolicies(@RequestParam(defaultValue = "0") int page,
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
    public String updatePointPolicy(@PathVariable Long pointPolicyId,
                                    @RequestBody PointPolicyRequest pointPolicyRequest) {

        pointPolicyService.updatePointPolicy(pointPolicyId, pointPolicyRequest);

        return "redirect:/admin/point-policies";
    }

    @DeleteMapping("/{pointPolicyId}")
    public String deletePointPolicy(@PathVariable Long pointPolicyId) {

        pointPolicyService.deletePointPolicy(pointPolicyId);

        return "redirect:/admin/point-policies";
    }
}
