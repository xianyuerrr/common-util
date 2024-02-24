package com.xianyue.common.core.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @Title: BigDecimalSerializer
 * @Package: com.xianyue.common.core.serial
 * @Description: BigDecimal 类型对外序列化时不展示为科学计数法
 * @Author: xianyue
 * @Date: 2024/2/24 11:31
 */
public class BigDecimalSerializer extends StdSerializer<BigDecimal> {
    public static final BigDecimalSerializer instance = new BigDecimalSerializer();

    public BigDecimalSerializer() {
        this(null);
    }

    protected BigDecimalSerializer(Class<BigDecimal> t) {
        super(t);
    }

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (bigDecimal != null) {
            String val = bigDecimal.stripTrailingZeros().toPlainString();
            jsonGenerator.writeString(val);
        }
    }
}
