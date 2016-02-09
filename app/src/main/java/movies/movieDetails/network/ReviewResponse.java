
package movies.movieDetails.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import movies.movieDetails.ReviewModel;

public class ReviewResponse {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("results")
    @Expose
    public ArrayList<ReviewModel> reviewModels = new ArrayList<ReviewModel>();
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;

    @Override
    public String toString()
        {
            return "ReviewResponse{" +
                    "id=" + id +
                    ", page=" + page +
                    ", reviewModels=" + reviewModels +
                    ", totalPages=" + totalPages +
                    ", totalResults=" + totalResults +
                    '}';
        }
}
