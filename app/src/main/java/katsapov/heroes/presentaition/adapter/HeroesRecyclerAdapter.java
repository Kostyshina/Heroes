package katsapov.heroes.presentaition.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import katsapov.heroes.R;
import katsapov.heroes.data.entity.Hero;


public class HeroesRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = R.layout.item_hero;
    private static final int VIEW_TYPE_LOADING = R.layout.item_loading;

    public interface OnHeroClickListener{
        void onHeroClick(Hero hero);
    }

    private boolean isLoaderVisible = false;
    private List<Hero> mHeroItems;
    private OnHeroClickListener heroRecyclerLister;


    public HeroesRecyclerAdapter(OnHeroClickListener listener){
        mHeroItems = new ArrayList<>();
        heroRecyclerLister = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return new ProgressHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hero, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mHeroItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public  int getItemCount() {
        return mHeroItems == null ? 0 : mHeroItems.size();
    }

    public void addItems(List<Hero> heroItems) {
        mHeroItems.addAll(heroItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        mHeroItems.add(new Hero());
        notifyItemInserted(mHeroItems.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        if (getItemCount() == 0) {
            return;
        }
        int position = mHeroItems.size() - 1;
        Hero item = getItem(position);
        if (item != null) {
            mHeroItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        mHeroItems.clear();
        notifyDataSetChanged();
    }

    private Hero getItem(int position) {
        return mHeroItems.get(position);
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvGender;
        private int holderPosition;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvGender = itemView.findViewById(R.id.tvGender);
        }

        protected void clear() {
        }

        public void onBind(int position) {
            super.onBind(position);
            holderPosition = position;
            Hero item = mHeroItems.get(position);

            itemView.setOnClickListener(this);
            tvName.setText(item.getName());
            tvGender.setText(item.getGender());
        }

        @Override
        public void onClick(View view) {
            heroRecyclerLister.onHeroClick(mHeroItems.get(holderPosition));
        }
    }

    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {
        }
    }
}