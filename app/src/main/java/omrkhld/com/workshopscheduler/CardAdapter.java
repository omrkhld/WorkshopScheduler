package omrkhld.com.workshopscheduler;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Omar on 21/4/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Workshop> cardList;

    public CardAdapter(List<Workshop> cardList) {
        this.cardList = cardList;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, final int i) {
        final Workshop w = cardList.get(i);
        cardViewHolder.vName.setText(w.getName());
        String rating = "";
        String services = "";
        for (int j = 0; j < w.getRating(); j++) {
            rating += "*";
        }
        cardViewHolder.vRating.setText(rating);
        if (w.getTyreChange()) {
            services += "Tyre";
            if (w.getOilChange()) {
                services += ", Oil";
                if (w.getBattChange()) {
                    services += ", Battery";
                }
            } else {
                if (w.getBattChange()) {
                    services += ", Battery";
                }
            }
        } else {
            if (w.getOilChange()) {
                services += "Oil";
                if (w.getBattChange()) {
                    services += ", Battery";
                }
            } else {
                if (w.getBattChange()) {
                    services += "Battery";
                }
            }
        }

        cardViewHolder.vServices.setText(services);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_workshop, viewGroup, false);

        CardViewHolder viewHolder = new CardViewHolder(itemView);
        return viewHolder;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        protected CardView cardView;
        protected TextView vName;
        protected TextView vRating;
        protected TextView vServices;

        public CardViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            vName = (TextView) v.findViewById(R.id.name);
            vRating = (TextView) v.findViewById(R.id.ratingInput);
            vServices = (TextView) v.findViewById(R.id.servicesInput);
        }
    }
}
