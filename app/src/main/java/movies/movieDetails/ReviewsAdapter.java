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
public class ReviewsAdapter extends RecyclerView.Adapter
{
    ArrayList<ReviewModel> mReview;
    LayoutInflater layoutInflater;

    public ReviewsAdapter(Context context, ArrayList<ReviewModel> list)
        {
            mReview = list;
            layoutInflater = LayoutInflater.from(context);
        }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_review, parent, false);

            BindingHolder holder = new BindingHolder(v);

            return holder;
        }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            final ReviewModel reviewModel = mReview.get(position);
            ((ReviewsAdapter.BindingHolder) holder).getBinding().setVariable(BR.reviewModel,
                    reviewModel);
            ((BindingHolder) holder).getBinding().executePendingBindings();
        }

    @Override
    public int getItemCount()
        {
            return mReview.size();
        }

    public void addReviews(ArrayList<ReviewModel> list)
        {
            mReview.addAll(list);
            notifyDataSetChanged();

        }

    public void setReviews(ArrayList<ReviewModel> list)
        {
            mReview.clear();
            mReview.addAll(list);
            notifyDataSetChanged();

        }

    public void clearReviews()
        {
            mReview = new ArrayList<ReviewModel>(0);
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
                        Logger.debug(this, "url " + mReview.get(getAdapterPosition()).getUrl());
                        layoutInflater.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse
                                        (mReview.get(getAdapterPosition()).getUrl())));
                    }
            }
    }

}
