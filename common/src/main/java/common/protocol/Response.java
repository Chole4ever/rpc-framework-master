package common.protocol;

import lombok.Data;

@Data
public class Response extends BaseMsg{

    private ResponseBody responseBody;
    private Header header;

    public Response(Header header,ResponseBody responseBody)
    {
        this.header = header;
        this.responseBody =responseBody;
    }
}
