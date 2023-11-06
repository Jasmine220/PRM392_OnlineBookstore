package com.example.onlinebookstore.RecycleViewAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Models.CartDetail;
import com.example.onlinebookstore.Response.CartDetailResponse;
import com.example.onlinebookstore.Service.ApiService;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRecycleView extends RecyclerView.Adapter<CartRecycleView.MyViewHolder>{
    List<CartDetailResponse> cartDetailList;
    Context context;
    ApiService apiService;
    List<Boolean> checkList = new ArrayList<>();
    List<CartDetailResponse> chosenList = new ArrayList<>();
    public CartRecycleView( Context context, List<CartDetailResponse> cartDetailList){
        this.cartDetailList = cartDetailList;
        this.context = context;
        apiService = ApiClient.getClient().create(ApiService.class);
        cartDetailList.stream().forEach(cartDetail -> checkList.add(false));

    }

    public void setCartDetailList(List<CartDetailResponse> cartDetailList){
        this.cartDetailList = cartDetailList;
    }

    public void setCheckList(Boolean value){
        this.checkList.stream().forEach(aBoolean -> {
           checkList.set(checkList.indexOf(aBoolean), value);
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_detail_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(cartDetailList.get(position).getBookImage()).into(holder.imageView);
        holder.title.setText(cartDetailList.get(position).getBookTitle());
        holder.amount.setText(String.valueOf(cartDetailList.get(position).getAmount()));
        holder.unitPrice.setText("Đơn giá: " + String.valueOf(cartDetailList.get(position).getBookPrice()) + "đ");
        holder.payment.setText(String.valueOf("Thành tiền: "+cartDetailList.get(position).getAmount() * cartDetailList.get(position).getBookPrice() + "đ" ));
        CartDetailResponse cartDetail = cartDetailList.get(position);

        holder.checkBox.setChecked(checkList.get(position));
        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            checkList.set(position, b);
        });
        if(checkList.get(position) == true){
            chosenList.add(cartDetail);
        }else{
            if(chosenList.contains(cartDetail)){
                chosenList.remove(position);
            }
        }

        holder.plusBtn.setOnClickListener(view -> {
            cartDetail.setAmount(cartDetail.getAmount() + 1);
            cartDetailList.set(position, cartDetail);
            notifyDataSetChanged();
            updateItem(cartDetail);
        });

        holder.subBtn.setOnClickListener(view -> {
            if(cartDetail.getAmount() == 1){
                deleteItem(cartDetail);
            }else{
                cartDetail.setAmount(cartDetail.getAmount() - 1);
                cartDetailList.set(position, cartDetail);
                notifyDataSetChanged();
               updateItem(cartDetail);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(cartDetail);
            }
        });

    }

    public List<CartDetailResponse> getChosenList() {
        return chosenList;
    }

    void updateItem(CartDetailResponse cartDetail){
        Call<CartDetail> call = apiService.updateCartDetail(cartDetail.getCartDetailId(), cartDetail.getAmount());
        call.enqueue(new Callback<CartDetail>() {
            @Override
            public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                CartDetail response1 = response.body();
                Log.d("CartUpdate", "successful");
                Log.d("CartUpdate", "cartDetail: CartDetailId" + response1.getCartDetailsId() + ", amount: " +response1.getQuantity());
            }

            @Override
            public void onFailure(Call<CartDetail> call, Throwable t) {
                Log.e("CartUpdate", "error: " + t.getMessage());
                t.printStackTrace();

            }
        });
    }

    void deleteItem(CartDetailResponse cartDetail){
        cartDetailList.remove(cartDetail);
        notifyDataSetChanged();
        Call<Void> call = apiService.deleteCartDetail(cartDetail.getCartDetailId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("CartDelete", "successful");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("CartDelete", "error: " + t.getMessage());
                t.printStackTrace();
            }
        });

    }


    @Override
    public int getItemCount() {
        return cartDetailList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView unitPrice;
        EditText amount;
        TextView payment;
        CheckBox checkBox;
        ImageButton  plusBtn;
        ImageButton subBtn;

        ImageButton deleteBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            unitPrice = itemView.findViewById(R.id.unitPrice);
            amount = itemView.findViewById(R.id.editAmount);
            payment = itemView.findViewById(R.id.payment);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            checkBox = itemView.findViewById(R.id.checkBox);
            plusBtn = itemView.findViewById(R.id.increaseBtn);
            subBtn = itemView.findViewById(R.id.decreaseBtn);
        }
    }
}
