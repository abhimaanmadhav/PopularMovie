
package movies.movieDetails.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import movies.movieDetails.TrailerModel;

public class TrailerResponse
{

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("results")
    @Expose
    public ArrayList<TrailerModel> trailerModels = new ArrayList<TrailerModel>();

    @Override
    public String toString()
        {
            return "TrailerResponse{" +
                    "id=" + id +
                    ", trailerModels=" + trailerModels +
                    '}';
        }
}
