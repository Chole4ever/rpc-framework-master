package transport;

import codec.Decoder;
import codec.Encoder;
import io.netty.channel.CombinedChannelDuplexHandler;

/*
使用protopuf
 */
public final class InternalServerMsgCodec  extends
        CombinedChannelDuplexHandler<InternalServerMsgCodec.Decoder, InternalServerMsgCodec.Encoder> {
    public InternalServerMsgCodec() {
        super.init(new Decoder(), new Encoder());
    }


}
