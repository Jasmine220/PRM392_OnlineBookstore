package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinebookstore.ChatAdapter;
import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.Models.Message;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Service.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Message> messages;
    private Socket mSocket; // Đối tượng Socket.io
    {
        try {
            mSocket = IO.socket("http://192.168.0.3:3000");
            Log.i("Ngu", "" + mSocket);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private int customerId; // Lấy từ Intent hoặc từ nơi nào đó
    private int sellerId = 4; // Đây là sellerId (ví dụ: 4)
    private EditText editTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        customerId = getIntent().getIntExtra("customerId", 0);
        int sellId = getIntent().getIntExtra("sellerId", 0);
        if(sellId == 4) customerId = 2;
        Log.d("ChatActivity", "customerId: " + customerId);
        getChatMessages();
        recyclerView = findViewById(R.id.recyclerViewChat);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(messages, this);
        recyclerView.setAdapter(chatAdapter);

        editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSend = findViewById(R.id.buttonSend);

        // Khởi tạo kết nối Socket.io


        // Xử lý khi nhấn nút "Gửi"
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = editTextMessage.getText().toString();
                if (!messageContent.isEmpty()) {
                    // Tạo một đối tượng Message mới
                    Message message = new Message();
                    message.setCustomerId((long) customerId);
                    message.setSellerId(sellerId);
                    message.setMessageContent(messageContent);
                    message.setMessageDatetime(new Date());

                    // Gửi tin nhắn thông qua Socket.io
                    sendMessageToServer(message);

                    // Hiển thị tin nhắn trong RecyclerView
                    messages.add(message);
                    chatAdapter.notifyDataSetChanged();
                    // Xoá nội dung đã nhập trong EditText
                    editTextMessage.setText("");
                }
            }
        });

        // Lắng nghe tin nhắn từ Socket.io và hiển thị chúng trong RecyclerView
//        mSocket.on("message", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                if (args.length > 0) {
//                    try {
//                        JSONObject data = (JSONObject) args[0];
//                        Message receivedMessage = new Message();
//                        receivedMessage.setCustomerId(data.getLong("customerId"));
//                        receivedMessage.setSellerId(data.getInt("sellerId"));
//                        receivedMessage.setMessageContent(data.getString("messageContent"));
//                        receivedMessage.setMessageDatetime(new Date(data.getLong("messageDatetime")));
//
//                        // Hiển thị tin nhắn nhận được trong RecyclerView
//                        Log.d("SocketTest", "Received message: " + receivedMessage.getMessageContent());
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                messages.add(receivedMessage);
//                                chatAdapter.notifyDataSetChanged();
//                            }
//                        });
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
        mSocket.connect();
    }

    private void sendMessageToServer(Message message) {
        try {
            JSONObject data = new JSONObject();
            data.put("customerId", message.getCustomerId());
            data.put("sellerId", message.getSellerId());
            data.put("messageContent", message.getMessageContent());
            data.put("messageDatetime", message.getMessageDatetime().getTime());
            Log.d("SocketTest", "Sending message: " + message.getMessageContent());
            mSocket.emit("sendMessage", data);
            saveMessageToServer(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveMessageToServer(Message message) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Message messageWithoutDatetime = new Message();
        messageWithoutDatetime.setCustomerId(message.getCustomerId());
        messageWithoutDatetime.setSellerId(message.getSellerId());
        messageWithoutDatetime.setMessageContent(message.getMessageContent());
        // Sử dụng Retrofit để gọi API /send
        Call<Void> call = apiService.sendMessage(messageWithoutDatetime);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi API /send thành công
                    showToast("Send Success");
                } else {
                    showToast("Send failed");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast("Not connected to api chat");
            }
        });
    }
    private void getChatMessages() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<List<Message>> call = apiService.getChatMessages((long) customerId, sellerId);
        Log.d("ChatActivity", "customerId" + customerId);
        Log.d("ChatActivity", "sellerId" + sellerId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> chatHistory = response.body();
                    // Hiển thị lịch sử chat trong RecyclerView
                    messages.addAll(chatHistory);
                    chatAdapter.notifyDataSetChanged();
                    Log.d("ChatActivity", "Chat history loaded successfully");
                } else {
                    Log.e("ChatActivity", "Failed to retrieve chat history. Response code: " + response.code());
                    showToast("Failed to retrieve chat history");
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("ChatActivity", "Failed to connect to chat API. Error: " + t.getMessage());
                showToast("Failed to connect to chat API");
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}