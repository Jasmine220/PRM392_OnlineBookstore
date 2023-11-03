//package com.example.onlinebookstore.Fragment;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.onlinebookstore.ChatAdapter;
//import com.example.onlinebookstore.Models.Message;
//import com.example.onlinebookstore.R;
//
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
////
////import io.socket.client.Socket;
////import io.socket.client.IO;
////import io.socket.emitter.Emitter;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ChatFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class ChatFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private ChatAdapter chatAdapter;
//    private List<Message> messages;
////    private Socket mSocket; // Đối tượng Socket.io
//    private int customerId; // Lấy từ Intent hoặc từ nơi nào đó
//    private int sellerId = 4; // Đây là sellerId (ví dụ: 4)
//    public void setCustomerId(int customerId) {
//        this.customerId = customerId;
//    }
//    public ChatFragment() {
//        // Required empty public constructor
//    }
//
//    public static ChatFragment newInstance() {
//        ChatFragment fragment = new ChatFragment();
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        messages = new ArrayList<>();
//        chatAdapter = new ChatAdapter(messages, requireContext());
//
////        try {
////            mSocket = IO.socket("http://10.33.33.15");
////            mSocket.connect();
////        } catch (URISyntaxException e) {
////            e.printStackTrace();
////        }
////
////        mSocket.on("newMessage", new Emitter.Listener() {
////            @Override
////            public void call(Object... args) {
////                final String receivedMessage = args[0].toString();
////                getActivity().runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        // Xử lý khi có tin nhắn mới đến
////                        // Thêm tin nhắn vào danh sách và cập nhật RecyclerView
////                        Message message = new Message();
////                        message.setMessageContent(receivedMessage);
////                        message.setCustomerId((long) customerId);
////                        message.setSellerId(sellerId);
////                        messages.add(message);
////                        chatAdapter.notifyDataSetChanged();
////                    }
////                });
////            }
////        });
//        Message messageToSend = new Message();
//        messageToSend.setMessageContent("Nội dung tin nhắn");
//        messageToSend.setCustomerId((long) customerId);
//        messageToSend.setSellerId(sellerId);
//
//// Gửi tin nhắn bằng Socket.io
////        mSocket.emit("sendMessage", messageToSend);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_chat, container, false);
////        recyclerView = view.findViewById(R.id.recyclerViewChat);
////        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
////        recyclerView.setAdapter(chatAdapter);
//
//        return view;
//    }
//
////    @Override
////    public void onDestroy() {
////        super.onDestroy();
////        // Ngắt kết nối Socket.io khi fragment bị hủy
////        if (mSocket != null) {
////            mSocket.disconnect();
////            mSocket.off("newMessage");
////        }
////    }
//}