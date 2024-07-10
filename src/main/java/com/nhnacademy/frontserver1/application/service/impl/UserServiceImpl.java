package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UserAddressResponse;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAdaptor userAdaptor;

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
    public Page<UserAddressResponse> getUserAddresses(Long userId, Pageable pageable) {
        UsersResponse user = getUserById(userId);
        List<UserAddressResponse> addresses = user.addresses();

        if (addresses == null || addresses.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), addresses.size());
        List<UserAddressResponse> pagedAddresses = addresses.subList(start, end);

        return new PageImpl<>(pagedAddresses, pageable, addresses.size());
    }



    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    public List<FindUserResponse> findAllUserEmailByUserNameByUserPhone(String name, String phone, Pageable pageable) {
        try {
            FindEmailRequest request = FindEmailRequest.builder()
                    .name(name)
                    .phone(phone)
                    //.pageable(pageable)
                    .build();
            logger.info("프론트 Request: " + request.toString());
            return userAdaptor.findByEmail(request, pageable);

        } catch (Exception e) {

            logger.severe("프론트 Error in findAllUserEmailByUserNameByUserPhone: " + e.getMessage());
            e.printStackTrace();
            throw e;


        }

    }

    @Override
    public Page<CouponBoxResponse> getStateCouponBox(String couponState, Pageable pageable) {
        return userAdaptor.getStateCouponBox(couponState, pageable);
    }

    @Override
    public boolean findUserPasswordByEmailByName(FindPasswordRequest request) {
        return userAdaptor.findUserPasswordByEmailByName(request);
    }

    // /users/addressList



//    @Override
//    public boolean setUserPasswordByUserId(Long userId, UpdatePasswordRequest request) {
//        return userAdaptor.setUserPasswordByUserId(userId, request);
//    }



    @Override
    public void sendEmail(String recipient) {
        // 구글메일로 전송
        String host = "smtp.gmail.com";
        // amuge0705가 여기서 yes255의 관리자 계정 역할을 합니다
        final String sender = "amuge0705@gmail.com"; // Your email address
        // 실제 비밀번호가 아닌 이 기능 구현을 위해 따로 발급받은 2중보안 앱 비밀번호입니다.
        final String password = "lxcm tulr nnme uimf"; // Your app password (not your Google account password)

        Properties properties = System.getProperties();
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
            // 링크는 배포 상태에 알맞게 추후 수정할 예정입니다
            message.setText("비밀번호를 새로 설정하기 위해서는 이 링크를 클릭하세요: http://localhost:8040/reset-password/"+recipient);


            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    //public record UpdatePasswordRequest(String password, String confirmPassword) {
    //}
    @Override
    public boolean setUserPasswordByEmail(String email, UpdatePasswordRequest request) {
        if (request.password().equals(request.confirmPassword())) {
            return true;
        } else {return false;}



        // 여기에 비밀번호를 재설정하는 로직을 구현합니다.
        //return true; // 실제 로직으로 대체
    }


    //  public Page<PointLogResponse> getPointLogs(Pageable pageable) {
    //        return userAdaptor.getUserPointLogs(pageable);
    //    }
    @Override
    public Page<UserAddressResponse> findAllUserAddress(Pageable pageable){
        return userAdaptor.findAllUserAddresses(pageable);
    }


//  @GetMapping("/addressList")
//    Page<UserAddressResponse> findAllUserAddresses(Long userId, Pageable pageable);

    //public record UserAddressResponse(Long userAddressId, Long addressId, String addressZip, String addressRaw, String addressName,
    //                                  String addressDetail, boolean addressBased, Long userId) {
    //}
}