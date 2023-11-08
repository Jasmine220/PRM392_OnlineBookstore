package com.example.onlinebookstore.RecycleViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinebookstore.Models.Book;
import com.example.onlinebookstore.Models.CartDetail;
import com.example.onlinebookstore.Models.CartDetails;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Response.CartDetailResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartDetailsAdapter extends RecyclerView.Adapter<CartDetailsAdapter.CartDetailsViewHolder> {
    private Context context;
    private ArrayList<CartDetailResponse> cartDetailsList;

    public CartDetailsAdapter(Context context, ArrayList<CartDetailResponse> cartDetailsList) {
        this.context = context;
        this.cartDetailsList = cartDetailsList;
    }

    @NonNull
    @Override
    public CartDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cart_details_item, parent, false);
        return new CartDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartDetailsViewHolder holder, int position) {
        CartDetailResponse cartDetails = cartDetailsList.get(position);
        Picasso.get().load(cartDetails.getBookImage()).into(holder.imvBook);
       holder.tvTitleBook.setText(cartDetails.getBookTitle());
       holder.tvPriceBook.setText("Đơn giá: " + formatCurrency(cartDetails.getBookPrice()));
       holder.tvCartDetailsQuantity.setText("Số lượng: " + String.valueOf(cartDetails.getAmount()));
    }

    @Override
    public int getItemCount() {
        return cartDetailsList.size();
    }
    private String formatCurrency(double price){
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedAmount = currencyFormat.format(price);

        return formattedAmount;
    }

    public class CartDetailsViewHolder extends RecyclerView.ViewHolder{
        private ImageView imvBook;
        private TextView tvTitleBook;
        private TextView tvPriceBook;
        private TextView tvCartDetailsQuantity;
        private LinearLayout layoutCartDetails;
        public CartDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            imvBook = itemView.findViewById(R.id.img_book);
            tvTitleBook = itemView.findViewById(R.id.tv_title_book);
            layoutCartDetails = itemView.findViewById(R.id.layout_cart_details);
            tvPriceBook = itemView.findViewById(R.id.tv_price);
            tvCartDetailsQuantity = itemView.findViewById(R.id.tv_quantity);
        }

        public ImageView getImvBook() {
            return imvBook;
        }

        public void setImvBook(ImageView imvBook) {
            this.imvBook = imvBook;
        }

        public TextView getTvTitleBook() {
            return tvTitleBook;
        }

        public void setTvTitleBook(TextView tvTitleBook) {
            this.tvTitleBook = tvTitleBook;
        }

        public TextView getTvPriceBook() {
            return tvPriceBook;
        }

        public void setTvPriceBook(TextView tvPriceBook) {
            this.tvPriceBook = tvPriceBook;
        }

        public TextView getTvCartDetailsQuantity() {
            return tvCartDetailsQuantity;
        }

        public void setTvCartDetailsQuantity(TextView tvCartDetailsQuantity) {
            this.tvCartDetailsQuantity = tvCartDetailsQuantity;
        }

        public LinearLayout getLayoutCartDetails() {
            return layoutCartDetails;
        }

        public void setLayoutCartDetails(LinearLayout layoutCartDetails) {
            this.layoutCartDetails = layoutCartDetails;
        }
    }
}
