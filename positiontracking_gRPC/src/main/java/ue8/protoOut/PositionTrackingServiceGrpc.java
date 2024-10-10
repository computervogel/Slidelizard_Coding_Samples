package ue8.protoOut;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Define the service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: position_tracking.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PositionTrackingServiceGrpc {

  private PositionTrackingServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "ue8.protoOut.PositionTrackingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ue8.protoOut.PositionTracking.PositionInfo,
      ue8.protoOut.PositionTracking.Ack> getSendPositionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendPosition",
      requestType = ue8.protoOut.PositionTracking.PositionInfo.class,
      responseType = ue8.protoOut.PositionTracking.Ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<ue8.protoOut.PositionTracking.PositionInfo,
      ue8.protoOut.PositionTracking.Ack> getSendPositionMethod() {
    io.grpc.MethodDescriptor<ue8.protoOut.PositionTracking.PositionInfo, ue8.protoOut.PositionTracking.Ack> getSendPositionMethod;
    if ((getSendPositionMethod = PositionTrackingServiceGrpc.getSendPositionMethod) == null) {
      synchronized (PositionTrackingServiceGrpc.class) {
        if ((getSendPositionMethod = PositionTrackingServiceGrpc.getSendPositionMethod) == null) {
          PositionTrackingServiceGrpc.getSendPositionMethod = getSendPositionMethod =
              io.grpc.MethodDescriptor.<ue8.protoOut.PositionTracking.PositionInfo, ue8.protoOut.PositionTracking.Ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendPosition"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ue8.protoOut.PositionTracking.PositionInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ue8.protoOut.PositionTracking.Ack.getDefaultInstance()))
              .setSchemaDescriptor(new PositionTrackingServiceMethodDescriptorSupplier("SendPosition"))
              .build();
        }
      }
    }
    return getSendPositionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ue8.protoOut.PositionTracking.PositionRequest,
      ue8.protoOut.PositionTracking.PositionInfo> getReceivePositionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReceivePosition",
      requestType = ue8.protoOut.PositionTracking.PositionRequest.class,
      responseType = ue8.protoOut.PositionTracking.PositionInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<ue8.protoOut.PositionTracking.PositionRequest,
      ue8.protoOut.PositionTracking.PositionInfo> getReceivePositionMethod() {
    io.grpc.MethodDescriptor<ue8.protoOut.PositionTracking.PositionRequest, ue8.protoOut.PositionTracking.PositionInfo> getReceivePositionMethod;
    if ((getReceivePositionMethod = PositionTrackingServiceGrpc.getReceivePositionMethod) == null) {
      synchronized (PositionTrackingServiceGrpc.class) {
        if ((getReceivePositionMethod = PositionTrackingServiceGrpc.getReceivePositionMethod) == null) {
          PositionTrackingServiceGrpc.getReceivePositionMethod = getReceivePositionMethod =
              io.grpc.MethodDescriptor.<ue8.protoOut.PositionTracking.PositionRequest, ue8.protoOut.PositionTracking.PositionInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReceivePosition"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ue8.protoOut.PositionTracking.PositionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ue8.protoOut.PositionTracking.PositionInfo.getDefaultInstance()))
              .setSchemaDescriptor(new PositionTrackingServiceMethodDescriptorSupplier("ReceivePosition"))
              .build();
        }
      }
    }
    return getReceivePositionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PositionTrackingServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PositionTrackingServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PositionTrackingServiceStub>() {
        @java.lang.Override
        public PositionTrackingServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PositionTrackingServiceStub(channel, callOptions);
        }
      };
    return PositionTrackingServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PositionTrackingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PositionTrackingServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PositionTrackingServiceBlockingStub>() {
        @java.lang.Override
        public PositionTrackingServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PositionTrackingServiceBlockingStub(channel, callOptions);
        }
      };
    return PositionTrackingServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PositionTrackingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PositionTrackingServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PositionTrackingServiceFutureStub>() {
        @java.lang.Override
        public PositionTrackingServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PositionTrackingServiceFutureStub(channel, callOptions);
        }
      };
    return PositionTrackingServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Define the service
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * RPC for sending positions
     * </pre>
     */
    default io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.PositionInfo> sendPosition(
        io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.Ack> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSendPositionMethod(), responseObserver);
    }

    /**
     * <pre>
     * RPC for receiving positions
     * </pre>
     */
    default io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.PositionRequest> receivePosition(
        io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.PositionInfo> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getReceivePositionMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PositionTrackingService.
   * <pre>
   * Define the service
   * </pre>
   */
  public static abstract class PositionTrackingServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PositionTrackingServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PositionTrackingService.
   * <pre>
   * Define the service
   * </pre>
   */
  public static final class PositionTrackingServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PositionTrackingServiceStub> {
    private PositionTrackingServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PositionTrackingServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PositionTrackingServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * RPC for sending positions
     * </pre>
     */
    public io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.PositionInfo> sendPosition(
        io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.Ack> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSendPositionMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * RPC for receiving positions
     * </pre>
     */
    public io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.PositionRequest> receivePosition(
        io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.PositionInfo> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getReceivePositionMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PositionTrackingService.
   * <pre>
   * Define the service
   * </pre>
   */
  public static final class PositionTrackingServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PositionTrackingServiceBlockingStub> {
    private PositionTrackingServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PositionTrackingServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PositionTrackingServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PositionTrackingService.
   * <pre>
   * Define the service
   * </pre>
   */
  public static final class PositionTrackingServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PositionTrackingServiceFutureStub> {
    private PositionTrackingServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PositionTrackingServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PositionTrackingServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SEND_POSITION = 0;
  private static final int METHODID_RECEIVE_POSITION = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_POSITION:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sendPosition(
              (io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.Ack>) responseObserver);
        case METHODID_RECEIVE_POSITION:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.receivePosition(
              (io.grpc.stub.StreamObserver<ue8.protoOut.PositionTracking.PositionInfo>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSendPositionMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              ue8.protoOut.PositionTracking.PositionInfo,
              ue8.protoOut.PositionTracking.Ack>(
                service, METHODID_SEND_POSITION)))
        .addMethod(
          getReceivePositionMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              ue8.protoOut.PositionTracking.PositionRequest,
              ue8.protoOut.PositionTracking.PositionInfo>(
                service, METHODID_RECEIVE_POSITION)))
        .build();
  }

  private static abstract class PositionTrackingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PositionTrackingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ue8.protoOut.PositionTracking.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PositionTrackingService");
    }
  }

  private static final class PositionTrackingServiceFileDescriptorSupplier
      extends PositionTrackingServiceBaseDescriptorSupplier {
    PositionTrackingServiceFileDescriptorSupplier() {}
  }

  private static final class PositionTrackingServiceMethodDescriptorSupplier
      extends PositionTrackingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PositionTrackingServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PositionTrackingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PositionTrackingServiceFileDescriptorSupplier())
              .addMethod(getSendPositionMethod())
              .addMethod(getReceivePositionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
