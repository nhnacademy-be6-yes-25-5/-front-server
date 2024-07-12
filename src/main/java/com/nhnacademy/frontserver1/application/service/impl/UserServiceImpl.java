package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UsersAddressResponse;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAdaptor userAdaptor;

    @Value("${SMTP_HOST}")
    private String host;

    @Value("${SMTP_USERNAME}")
    private String sender;

    @Value("${SMTP_PASSWORD}")
    private String password;

    @Override
    public UserResponse signUp(CreateUserRequest userRequest) {

        return userAdaptor.signUp(userRequest);
    }

    @Override
    public UserResponse findByUser() {
        return userAdaptor.findByUserId();
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest userRequest) {
        return userAdaptor.updateUser(userRequest);
    }

    @Override
    public void deleteUser(DeleteUserRequest userRequest) {
        userAdaptor.deleteUser(userRequest);
    }

//    @Override
//    public PointPolicyResponse getPointPolicies(PointPolicyRequest pointPolicyRequest) {
//        return null;
//    }

    @Override
    public UserGradeResponse getUserGrade() {
        return userAdaptor.getUserGrade();
    }

    @Override
    public Page<PointLogResponse> getPointLogs(Pageable pageable) {
        return userAdaptor.getUserPointLogs(pageable);
    }

    public ReadUserInfoResponse getUserPointsAndGrade() {
        return userAdaptor.getUserPointsAndGrade().getBody();
    }

    // fixme. 해당 기능 구현하지 않아 주석처리하였습니다.
    @Override
    public UsersResponse getUserById(Long id) {
//        UserResponse user = userAdaptor.getUserById(id);
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//        return user;

        return null;
    }

    @Override
    public Boolean isEmailDuplicate(String email) {
        return userAdaptor.checkEmail(email);
    }

    @Override
    public Page<UsersAddressResponse> getUserAddresses(Long userId, Pageable pageable) {
        UsersResponse user = getUserById(userId);
        List<UsersAddressResponse> addresses = user.addresses();

        if (addresses == null || addresses.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), addresses.size());
        List<UsersAddressResponse> pagedAddresses = addresses.subList(start, end);

        return new PageImpl<>(pagedAddresses, pageable, addresses.size());
    }

    public List<FindUserResponse> findAllUserEmailByUserNameByUserPhone(String name, String phone, Pageable pageable) {
        try {
            FindEmailRequest request = FindEmailRequest.builder()
                    .name(name)
                    .phone(phone)
                    .build();
            return userAdaptor.findByEmail(request, pageable);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Page<CouponBoxResponse> getStateCouponBox(String couponState, Pageable pageable) {
        return userAdaptor.getStateCouponBox(couponState, pageable);
    }

    @Override
    public Page<UserAddressResponse> getAllUserAddresses(Pageable pageable) {
        return userAdaptor.findAllUserAddresses(pageable);
    }

    @Override
    public void updateAddressBased(Long userAddressId, UpdateAddressBasedRequest request) {
        userAdaptor.updateAddressBased(userAddressId, request);
    }

    @Override
    public CreateUserAddressResponse createUserAddresses(CreateUserAddressRequest userRequest) {
        return userAdaptor.createUserAddress(userRequest);
    }

    @Override
    public boolean findUserPasswordByEmailByName(FindPasswordRequest request) {
        return userAdaptor.findUserPasswordByEmailByName(request);
    }

//    @Override
//    public boolean setUserPasswordByEmail(String email, UpdatePasswordRequest request) {
//        return false;
//    }

    @Override
    public void sendEmail(String recipient) {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("[Yes25.5] 비밀번호를 새로 설정하겠습니다.");

            message.setText("비밀번호를 새로 설정하기 위해서는 이 링크를 클릭하세요: https://yes25-5.shop/reset-password/" + recipient);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }



    @Override
    public void UpdateUserPasswordByEmail(String email, UpdatePasswordRequest request) {
        userAdaptor.updatePassword(email, request);
    }

}