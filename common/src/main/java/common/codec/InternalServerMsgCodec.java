package common.codec;

import common.constants.ProtocolConstants;
import common.enums.Msg;
import common.protocol.RequestBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import common.protocol.BaseMsg;
import common.protocol.Request;
import common.protocol.Header;

import java.util.HashMap;
import java.util.List;

/*

 */
public final class InternalServerMsgCodec  extends
        CombinedChannelDuplexHandler<InternalServerMsgCodec.Decoder, InternalServerMsgCodec.Encoder> {
    public InternalServerMsgCodec(Class<?> decoderClz,Class<?> encoderClz) {
        super.init(new Decoder(decoderClz), new Encoder(encoderClz));
    }

    static final class Decoder extends ByteToMessageDecoder {
        private Class<?> genericClass; // 待编码的对象类型

        public Decoder(Class<?> genericClass) {
            this.genericClass = genericClass;
        }

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
            if (in.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN) {
                return;
            }

            in.markReaderIndex();

            short magic = in.readShort();
            if (magic != ProtocolConstants.MAGIC) {
                throw new IllegalArgumentException("magic number is illegal, " + magic);
            }

            byte version = in.readByte();
            byte serializeType = in.readByte();
            byte msgType = in.readByte();
            long requestId = in.readLong();

            int dataLength = in.readInt();
            if (in.readableBytes() < dataLength) {
                in.resetReaderIndex();
                return;
            }
            RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(serializeType);
            byte[] data = new byte[dataLength];

            ByteBuf result = in.readBytes(data,0,dataLength);

            Class<? extends BaseMsg> clz = Msg.findMsgType(msgType);

            RequestBody requestBody = (RequestBody) rpcSerialization.deserialize(data, clz);
            Header header = new Header(magic,version,serializeType,msgType);
            header.setMsgLen(requestBody,serializeType);

            list.add(new Request(header,requestBody));
        }
    }
    static final class Encoder extends MessageToByteEncoder<Request>
    {
        private  Class<?> genericClass; // 待编码的对象类型
        public Encoder(Class<?> genericClass) {
            this.genericClass = genericClass;
        }
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Request request, ByteBuf byteBuf) throws Exception {
            if (genericClass.isInstance(request)) {
                Header header = request.getHeader();
                byteBuf.writeShort(header.getMagic());
                byteBuf.writeByte(header.getVersion());
                byteBuf.writeByte(header.getSerialization());
                byteBuf.writeByte(header.getMsgType());
                byteBuf.writeLong(header.getRequestId());
                //序列化工厂提供序列化实例
                RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(header.getSerialization());

                // 将对象序列化为字节数组
                byte[] data = rpcSerialization.serialize(request.getRequestBody());

                // 将消息体长度写入消息头
                byteBuf.writeInt(data.length);

                // 将数据写入消息体
                byteBuf.writeBytes(data);
            }
        }
    }
}
