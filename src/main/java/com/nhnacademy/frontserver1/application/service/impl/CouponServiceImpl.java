package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.CouponAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyBookRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.BookDetailCouponResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyBookResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyCategoryResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponAdaptor couponAdaptor;
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Override
    public Page<CouponPolicyResponseDTO> findAllCouponPolicies(Pageable pageable) {
        try {
            List<CouponPolicyResponseDTO> coupons = couponAdaptor.findAll();
            if (coupons == null) {
                logger.warn("Received null response for coupons");
                coupons = Collections.emptyList();
            }
            logger.info("Coupons received: {}", coupons);

            return getPage(coupons, pageable);
        } catch (HttpServerErrorException e) {
            logger.error("Error occurred while fetching coupons: {}", e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching coupons", e);
            throw e;
        }
    }

    @Override
    public void createCoupon(CouponPolicyRequestDTO createCouponRequest) {
        couponAdaptor.create(createCouponRequest);
    }

    @Override
    public void createCouponPolicyBook(CouponPolicyBookRequestDTO createCouponPolicyBookRequest) {
        couponAdaptor.create(createCouponPolicyBookRequest);
    }

    @Override
    public Page<CouponPolicyBookResponseDTO> findAllBookCouponPolicies(Pageable pageable) {
        try {
            List<CouponPolicyBookResponseDTO> bookCoupons = couponAdaptor.findAllBooks();
            if (bookCoupons == null) {
                logger.warn("Received null response for book coupons");
                bookCoupons = Collections.emptyList();
            }
            logger.info("Book Coupons received: {}", bookCoupons);

            return getPage(bookCoupons, pageable);
        } catch (HttpServerErrorException e) {
            logger.error("Error occurred while fetching book coupons: {}", e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching book coupons", e);
            throw e;
        }
    }

    @Override
    public void createCouponPolicyCategory(CouponPolicyCategoryRequestDTO createCouponPolicyCategoryRequest) {
        couponAdaptor.create(createCouponPolicyCategoryRequest);
    }

    @Override
    public Page<CouponPolicyCategoryResponseDTO> findAllCategoryCouponPolicies(Pageable pageable) {
        try {
            List<CouponPolicyCategoryResponseDTO> categoryCoupons = couponAdaptor.findAllCategories();
            if (categoryCoupons == null) {
                logger.warn("Received null response for category coupons");
                categoryCoupons = Collections.emptyList();
            }
            logger.info("Category Coupons received: {}", categoryCoupons);

            return getPage(categoryCoupons, pageable);
        } catch (HttpServerErrorException e) {
            logger.error("Error occurred while fetching category coupons: {}", e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching category coupons", e);
            throw e;
        }
    }

    private <T> Page<T> getPage(List<T> list, Pageable pageable) {
        if (list == null) {
            return Page.empty(pageable);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<T> pagedList;
        if (start > end) {
            pagedList = Collections.emptyList();
        } else {
            pagedList = list.subList(start, end);
        }

        return new PageImpl<>(pagedList, pageable, list.size());
    }

    public List<BookDetailCouponResponseDTO> getCoupons(Long bookId, List<Long> categoryIds) {
        return couponAdaptor.getCouponsByBookIdAndCategoryIds(bookId, categoryIds);
    }
}