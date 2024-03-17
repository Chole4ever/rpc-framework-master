package common.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangyike
 */
@Data
public class ResponseBody extends BaseMsg implements Serializable {

    private Exception exception;
    private Object result;

    public ResponseBody(Exception exception,Object result)
    {
        this.exception = exception;
        this.result =result;
    }

}
