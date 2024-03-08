package protocol;

import java.io.Serializable;

public class RequestContent implements Serializable {
    private RequestBody requestBody;
    private RequestHeader requestHeader;

    //释放
    public void free() {
        requestHeader = null;
        requestBody = null;
    }
     public RequestContent(RequestHeader requestHeader,RequestBody requestBody)
     {
       this.requestHeader =requestHeader;
       this.requestBody = requestBody;
     }




}
