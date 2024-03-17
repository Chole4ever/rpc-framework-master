package common.codec.impl;

import common.codec.RpcSerialization;
import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;
import org.apache.commons.lang3.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerialization implements RpcSerialization {
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        if (object == null) {
            throw new NullPointerException();
        }

        byte[] results;

        HessianSerializerOutput hessianOutput;

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            hessianOutput = new HessianSerializerOutput(os);

            hessianOutput.writeObject(object);

            hessianOutput.flush();

            results = os.toByteArray();

        } catch (Exception e) {

            throw new SerializationException(e);

        }

        return results;

    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        if (data == null) {
            throw new NullPointerException();
        }

        T result;

        try (ByteArrayInputStream in = new ByteArrayInputStream(data)) {

            HessianSerializerInput hessianInput = new HessianSerializerInput(in);

            result = (T) hessianInput.readObject(clz);

        } catch (Exception e) {

            throw new SerializationException(e);

        }

        return result;

    }
}

