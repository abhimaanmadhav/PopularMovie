
package movies.movieDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailerModel
{

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("iso_639_1")
    @Expose
    public String iso6391;
    @SerializedName("key")
    @Expose
    public String key;


    @SerializedName("name")
    @Expose
    public String name = "Official Red Band 2 Trailer";
    @SerializedName("site")
    @Expose
    public String site;
    @SerializedName("size")
    @Expose
    public Integer size;
    @SerializedName("type")
    @Expose
    public String type;

    public String getName()
        {
            return name;
        }

    public void setName(String name)
        {
            this.name = name;
        }

    public String getId()
        {
            return id;
        }

    public void setId(String id)
        {
            this.id = id;
        }

    public String getKey()
        {
             return "https://www.youtube.com/watch?v=" + key;

        }
    public String getKeyId()
        {
            return   key;

        }
    public void setKey(String key)
        {
            this.key = key;
        }

    public Integer getSize()
        {
            return size;
        }

    public void setSize(Integer size)
        {
            this.size = size;
        }

    public String getSite()
        {
            return site;
        }

    public void setSite(String site)
        {
            this.site = site;
        }

    public String getType()
        {
            return type;
        }

    public void setType(String type)
        {
            this.type = type;
        }

    public String getIso6391()
        {
            return iso6391;
        }

    public void setIso6391(String iso6391)
        {
            this.iso6391 = iso6391;
        }

    @Override
    public String toString()
        {
            return "TrailerModel{" +
                    "id='" + id + '\'' +
                    ", iso6391='" + iso6391 + '\'' +
                    ", key='" + key + '\'' +
                    ", name='" + name + '\'' +
                    ", site='" + site + '\'' +
                    ", size=" + size +
                    ", type='" + type + '\'' +
                    '}';
        }

}
