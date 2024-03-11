package protocol;

import lombok.Data;

import java.io.Serializable;


@Data
public class Request extends BaseMsg implements Serializable{

    private RequestBody requestBody;

    private RequestHeader requestHeader;

    //释放
    public void free() {
        requestHeader = null;
        requestBody = null;
    }
     public Request(RequestHeader requestHeader, RequestBody requestBody)
     {
       this.requestHeader =requestHeader;
       this.requestBody = requestBody;
     }




}
