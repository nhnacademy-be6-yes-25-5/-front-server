package com.nhnacademy.frontserver1.common.converter;


import com.nhnacademy.frontserver1.domain.PaymentProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPaymentProviderConverter implements Converter<String, PaymentProvider> {

    @Override
    public PaymentProvider convert(String source) {
        return PaymentProvider.from(source);
    }
}
