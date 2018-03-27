package footprint.mobile.com.footprint.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.dto.MainItems;
import footprint.mobile.com.footprint.framework.OnMainItemsClickListener;

/**
 * Created by Suman Pula on 3/1/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class MainViewsAdapter extends RecyclerView.Adapter {
    private MainItems mainItems;
    private OnMainItemsClickListener onMainItemsClickListener;

    public MainViewsAdapter(MainItems mainItems) {
        this.mainItems = mainItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.textView.setText(mainItems.getMainItems()[position]);
        myViewHolder.imageView.setBackgroundResource(mainItems.getMainIcons()[position]);
    }

    @Override
    public int getItemCount() {
        return mainItems.getMainItems().length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(final View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card);
            textView = itemView.findViewById(R.id.tv);
            imageView = itemView.findViewById(R.id.iv);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (mainItems.getMainItems()[getAdapterPosition()].equalsIgnoreCase(itemView.getContext().getString(R.string.take_foot_print))) {
                            onMainItemsClickListener.onTakeFootPrintClick();
                        } else if (mainItems.getMainItems()[getAdapterPosition()].equalsIgnoreCase(itemView.getContext().getString(R.string.view_foot_print))) {
                            onMainItemsClickListener.onViewFootPrintClick();
                        } else if (mainItems.getMainItems()[getAdapterPosition()].equalsIgnoreCase(itemView.getContext().getString(R.string.amend_details))) {
                            onMainItemsClickListener.onAmendDetailsClick();
                        }  else if (mainItems.getMainItems()[getAdapterPosition()].equalsIgnoreCase(itemView.getContext().getString(R.string.edit_foot_print))) {
                            onMainItemsClickListener.onEditFootPrintClick();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setOnMainItemsClickListener(OnMainItemsClickListener onMainItemsClickListener) {
        this.onMainItemsClickListener = onMainItemsClickListener;
    }
}
