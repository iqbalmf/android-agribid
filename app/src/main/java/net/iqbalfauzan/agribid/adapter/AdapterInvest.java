package net.iqbalfauzan.agribid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iqbalfauzan.agribid.R;
import net.iqbalfauzan.agribid.model.InvestModel;

import java.util.List;

public class AdapterInvest extends RecyclerView.Adapter<AdapterInvest.Holder> {
    private Context context;
    private List<InvestModel> invests;

    public AdapterInvest(Context context, List<InvestModel> invests) {
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

        holder.invest.setText(""+invests.get(i).getBiayaInvest());
    }

    @Override
    public int getItemCount() {
        return invests.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView invest;
        ImageView imageBackground;
        public Holder(@NonNull View itemView) {
            super(itemView);
            invest = itemView.findViewById(R.id.textBiaya);
            imageBackground = itemView.findViewById(R.id.imageBackground);
        }
    }
}
