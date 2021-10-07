package Services;

import Storages.Message;
import Storages.MessageStorage;
import com.example.grpc.Service;
import com.example.grpc.messageReceiverGrpc;
import io.grpc.stub.StreamObserver;

public class ReceiveMessageService extends messageReceiverGrpc.messageReceiverImplBase
{
    MessageStorage messageStorage = MessageStorage.getInstance();
    SendMessageService sendMessageService = new SendMessageService();
    @Override
    public void receive(Service.receivedMessage request, StreamObserver<Service.newResponse> responseObserver)
    {
        messageStorage.addMessage(new Message(request.getMessage(),request.getTopic()));
        Service.newResponse.Builder responseBuilder = Service.newResponse.newBuilder();
        Service.newResponse response = responseBuilder.setIsSuccess("Success").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        sendMessageService.broadcastMessage(new Message(request.getMessage() , request.getTopic()));
    }


}
