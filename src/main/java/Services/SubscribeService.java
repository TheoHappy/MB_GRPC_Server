package Services;

import Storages.Message;
import Storages.MessageStorage;
import Storages.User;
import Storages.UserStorage;
import com.example.grpc.Service;
import com.example.grpc.subscribeServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SubscribeService extends subscribeServiceGrpc.subscribeServiceImplBase {
    UserStorage userStorage = UserStorage.getInstance();
    MessageStorage messageStorage = MessageStorage.getInstance();
    @Override
    public void subscribe(Service.subscribeRequest request, StreamObserver<Service.subscribeResponse> responseObserver)
    {
        SendMessageService sendMessageService = new SendMessageService();
        System.out.println("New Connection: " + request+"\n");
        userStorage.addUser(new User(request.getAddress() , request.getTopic()));
        Service.subscribeResponse.Builder responseBuilder = Service.subscribeResponse.newBuilder();

        for (Message mes : messageStorage.messages)
        {
            if (mes.getTopic().equals(request.getTopic()))
            {
                responseBuilder.addMessage(Service.receivedMessage.newBuilder()
                        .setMessage(mes.getMessageBody()).build());
            }
        }

        Service.subscribeResponse response = responseBuilder.setIsSuccess("Success").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}
