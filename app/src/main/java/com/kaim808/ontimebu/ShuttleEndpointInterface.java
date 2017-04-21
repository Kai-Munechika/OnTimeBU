package com.kaim808.ontimebu;

import com.kaim808.ontimebu.model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by KaiM on 3/2/17.
 */

public interface ShuttleEndpointInterface {

    // livebus.json.php/";

    @GET("livebus.json.{format}/")
    Call<Root> getRoot(@Path("format") String format);

}
