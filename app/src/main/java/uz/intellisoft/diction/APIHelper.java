package uz.intellisoft.diction;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIHelper {
    @POST("api/v1.5/tr.json/translate")
    abstract Call<TranslatedText> getTranslation(@Query("key") String APIKey,
                                                 @Query("text") String textToTranslate,
                                                 @Query("lang") String lang);
}
