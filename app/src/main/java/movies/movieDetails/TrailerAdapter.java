package movies.movieDetails;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import movies.abhimaan.com.popularmovies1.BR;
import movies.abhimaan.com.popularmovies1.R;
import utility.Logger;

/**
 * Created by Abhimaan on 08/02/16.
 */
public class TrailerAdapter extends RecyclerView.Adapter
{
    ArrayList<TrailerModel> mTrailers;
    LayoutInflater layoutInflater;

    public TrailerAdapter(Context context, ArrayList<TrailerModel> list)
        {
            mTrailers = list;
            layoutInflater = LayoutInflater.from(context);
        }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_trailers, parent, false);

            BindingHolder holder = new BindingHolder(v);

            return holder;
        }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            final TrailerModel trailerModel = mTrailers.get(position);
            ((TrailerAdapter.BindingHolder) holder).getBinding().setVariable(BR.trailerModel,
                    trailerModel);
            ((BindingHolder) holder).getBinding().executePendingBindings();
        }

    @Override
    public int getItemCount()
        {
            return mTrailers.size();
        }

    public void addTrailers(ArrayList<TrailerModel> list)
        {
            mTrailers.addAll(list);
            notifyDataSetChanged();

        }

    public void setTrailers(ArrayList<TrailerModel> list)
        {
            mTrailers.clear();
            mTrailers.addAll(list);
            notifyDataSetChanged();

        }

    public void clearTrailers()
        {
            mTrailers = new ArrayList<TrailerModel>(0);
            notifyDataSetChanged();
        }


    public class BindingHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener
    {
        private ViewDataBinding binding;

        public BindingHolder(View rowView)
            {
                super(rowView);
                binding = DataBindingUtil.bind(rowView);
                rowView.setOnClickListener(this);
            }

        public ViewDataBinding getBinding()
            {
                return binding;
            }

        @Override
        public void onClick(View v)
            {
                if (getAdapterPosition() != RecyclerView.NO_POSITION)
                    {
                        Logger.debug(this, "url " + mTrailers.get(getAdapterPosition()).getKey());

                        layoutInflater.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse
                                        (mTrailers.get(getAdapterPosition()).getKey())));
                    }
            }
    }

}
