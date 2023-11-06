//package com.example.onlinebookstore.RecycleViewAdapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.onlinebookstore.Models.CartDetails;
//
//import java.util.List;
//
//public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
//    private List<CartDetails> cartList;
//    private Context context;
//
//    public CartAdapter(List<CartDetails> cartList, Context context) {
//        this.cartList = cartList;
//        this.context = context;
//    }
//
//    public CartAdapter() {
//    }
//
//    @NonNull
//    @Override
//    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        //tao va khoi dong viewHolder, chua gan du lieu
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_details_item, parent, false);
//        return new CartViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//        //gan du lieu
//        CartDetails cartDetails = cartList.get(position);
////        holder.getImvCartDetails().setImageResource(cartDetails.getBook().getImage());
////        holder.getTvCartDetails().setText(cartDetails.getBook().getName());
//        holder.getTvCartDetailsQuantity().setText( String.valueOf(cartDetails.getQuantity()));
//
////        holder.layoutCartDetails.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                System.out.println("CART_DETAILS: " + cartDetails);
////                Intent intent = new Intent(context, Cart.class);
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("cart_details" , (Serializable) cartDetails);
////                intent.putExtras(bundle);
////                context.startActivity(intent);
////
////            }
////        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return cartList.size();
//    }
//
//    public class CartViewHolder extends RecyclerView.ViewHolder{
//        private ImageView imvCartDetails;
//        private TextView tvCartDetails;
//        private LinearLayout layoutCartDetails;
//        private TextView tvCartDetailsQuantity;
//
//        public CartViewHolder(@NonNull View itemView) {
//            super(itemView);
////            imvCartDetails = itemView.findViewById(R.id.imv_cart_details);
////            tvCartDetails = itemView.findViewById(R.id.tv_name_cart_details);
////            layoutCartDetails = itemView.findViewById(R.id.layout_cart_details);
////            tvCartDetailsQuantity = itemView.findViewById(R.id.tv_cart_details_quantity);
//        }
//
//        public TextView getTvCartDetailsQuantity() {
//            return tvCartDetailsQuantity;
//        }
//
//        public void setTvCartDetailsQuantity(TextView tvCartDetailsQuantity) {
//            this.tvCartDetailsQuantity = tvCartDetailsQuantity;
//        }
//
//        public LinearLayout getLayoutCartDetails() {
//            return layoutCartDetails;
//        }
//
//        public void setLayoutCartDetails(LinearLayout layoutCartDetails) {
//            this.layoutCartDetails = layoutCartDetails;
//        }
//
//        public ImageView getImvCartDetails() {
//            return imvCartDetails;
//        }
//
//        public void setImvCartDetails(ImageView imvCartDetails) {
//            this.imvCartDetails = imvCartDetails;
//        }
//
//        public TextView getTvCartDetails() {
//            return tvCartDetails;
//        }
//
//        public void setTvCartDetails(TextView tvCartDetails) {
//            this.tvCartDetails = tvCartDetails;
//        }
//    }
//}
