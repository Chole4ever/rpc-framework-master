package utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public final class ByteUtil {

    public static byte[] toByteArray(Object obj){
        if(obj == null){
            return new byte[0];
        }

        ObjectOutputStream oos = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            oos = new ObjectOutputStream(out);
            oos.writeObject(obj);
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.reset();
                out.close();
                if(null != oos){
                    oos.reset();
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    private ByteUtil(){}

}
