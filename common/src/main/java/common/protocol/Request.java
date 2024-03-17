package common.protocol;

import lombok.Data;

import java.io.Serializable;


@Data
public class Request extends BaseMsg{

    private RequestBody requestBody;
    private Header header;

     public Request(Header requestHeader, RequestBody requestBody)
     {
       this.header =requestHeader;
       this.requestBody = requestBody;
     }
}
