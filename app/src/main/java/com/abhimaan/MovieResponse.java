
package com.abhimaan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse
{
    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("results")
    @Expose
    public ArrayList<Result> results = new ArrayList<Result>();
    @SerializedName("total_results")
    @Expose
    public int totalResults;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;


    @Override
    public String toString()
        {
            return "MovieResponse{" +
                    "page=" + page +
                    ", totalResults=" + totalResults +
                    ", totalPages=" + totalPages +
                    ", results=" + results +
                    '}';
        }
}
