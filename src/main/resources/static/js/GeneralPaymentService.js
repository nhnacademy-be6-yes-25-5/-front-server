class GeneralPaymentService extends PaymentService{
  constructor(clientKey) {
    super();
    this.clientKey = clientKey;
  }

  requestPayment({orderId, totalAmount, cardInfo}) {
    console.log('General payment requested', orderId, totalAmount, cardInfo);
    return Promise.resolve();
  }
}