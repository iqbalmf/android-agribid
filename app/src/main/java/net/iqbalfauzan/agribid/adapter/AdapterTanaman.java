package net.iqbalfauzan.agribid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iqbalfauzan.agribid.R;
import net.iqbalfauzan.agribid.TanamanListDetail;
import net.iqbalfauzan.agribid.model.TanamanModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterTanaman extends RecyclerView.Adapter<AdapterTanaman.Holder> {
    private Context context;
    private List<TanamanModel> tanamanModels;

    public AdapterTanaman(Context context, List<TanamanModel> tanamanModels) {
        this.context = context;
        this.tanamanModels = tanamanModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_tanaman, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final TanamanModel model = tanamanModels.get(i);
        Locale locale = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        holder.biaya.setText(numberFormat.format(model.getHargaPasar()));
        holder.textNamaLatin.setText(model.getNamaLatin());
        holder.textNama.setText(model.getNama());
        holder.imageBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, TanamanListDetail.class).putExtra("tanaman", model.getNama()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tanamanModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView biaya, textNamaLatin, textNama;
        ImageView imageBackground;
        public Holder(@NonNull View itemView) {
            super(itemView);
            biaya = itemView.findViewById(R.id.textBiaya);
            textNama = itemView.findViewById(R.id.textNama);
            textNamaLatin = itemView.findViewById(R.id.textNamaLatin);
            imageBackground = itemView.findViewById(R.id.imageBackground);
        }
    }
}
