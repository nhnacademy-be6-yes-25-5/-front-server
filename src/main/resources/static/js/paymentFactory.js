function createPaymentService(serviceType) {
  let clientKey = 'test_ck_5OWRapdA8dJYOKZkAR06Vo1zEqZK';

  if (serviceType === 'toss') {
    console.log('토스페이먼츠 실행');
    return new TossPaymentService(clientKey);
  }

  if (serviceType === 'naver') {
    alert('현재 개발중입니다. 다른 결제 수단을 사용해주세요.');
    throw new Error("Naver Pay service is currently under development");
  }

  if (serviceType === 'kakao') {
    alert('현재 개발중입니다. 다른 결제 수단을 사용해주세요.');
    throw new Error("Kakao Pay service is currently under development");
  }

  if (serviceType === 'general') {
    alert('현재 개발중입니다. 다른 결제 수단을 사용해주세요.');
    return new GeneralPaymentService(clientKey);
  }

  throw new Error("Unknown payment service type");
}