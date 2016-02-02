package movies.movieDetails;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhimaan.Result;

import movies.abhimaan.com.popularmovies1.R;
import movies.abhimaan.com.popularmovies1.databinding.FragmentMovieDetailBinding;
import utility.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "details";
    private static final String ARG_PARAM2 = "param2";
    Result moveDetailsObj;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    public static MovieDetailFragment newInstance(Result movie)
        {
            MovieDetailFragment fragment = new MovieDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable(ARG_PARAM1, movie);
            fragment.setArguments(args);
            return fragment;
        }

    @Override
    public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            moveDetailsObj = getArguments().getParcelable(ARG_PARAM1);
            Logger.debug(this,moveDetailsObj);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
        {
            FragmentMovieDetailBinding mBinding = DataBindingUtil.inflate(inflater, R.layout
                    .fragment_movie_detail, container, false);
            mBinding.setMoveDetailsObj(moveDetailsObj);
            return mBinding.getRoot();
        }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
        {
            if (mListener != null)
                {
                    mListener.onFragmentInteraction(uri);
                }
        }

    @Override
    public void onAttach(Context context)
        {
            super.onAttach(context);
            if (context instanceof OnFragmentInteractionListener)
                {
                    mListener = (OnFragmentInteractionListener) context;
                } else
                {
                    throw new RuntimeException(context.toString()
                            + " must implement OnFragmentInteractionListener");
                }
        }

    @Override
    public void onDetach()
        {
            super.onDetach();
            mListener = null;
        }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}