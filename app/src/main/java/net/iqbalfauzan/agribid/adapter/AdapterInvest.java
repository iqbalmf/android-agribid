package net.iqbalfauzan.agribid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.iqbalfauzan.agribid.R;

import java.util.List;

public class AdapterInvest extends RecyclerView.Adapter<AdapterInvest.Holder> {
    private Context context;
    private List<String> invests;

    public AdapterInvest(Context context, List<String> invests) {
        this.context = context;
        this.invests = invests;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_invest, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

        holder.invest.setText(invests.get(i));
    }

    @Override
    public int getItemCount() {
        return invests.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView invest;
        public Holder(@NonNull View itemView) {
            super(itemView);
            invest = itemView.findViewById(R.id.invest);
        }
    }
}
