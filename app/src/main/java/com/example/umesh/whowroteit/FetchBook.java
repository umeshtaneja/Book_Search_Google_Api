package com.example.umesh.whowroteit;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by umesh on 5/12/2017.
 */

public class FetchBook extends AsyncTask<String, Void, String> {

    private TextView mTitleText;
    private TextView mAuthorText;

    public FetchBook(TextView mTitleText, TextView mAuthorText){
        this.mTitleText = mTitleText;
        this.mAuthorText = mAuthorText;
    }

    @Override
    protected String doInBackground(String... params) {

        return NetworkUtils.getBookInfo(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {

            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject book = itemsArray.getJSONObject(i);
                String title=null;
                String authors=null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }
                if (title != null && authors != null){
                    mTitleText.setText(title);
                    Log.d("fetch book", title);
                    mAuthorText.setText(authors);
                    Log.d("fetch book", authors);
                    return;
                }
            }
            mTitleText.setText("No Results Found");
            mAuthorText.setText("");
        } catch (Exception e){
            mTitleText.setText("No Results Found");
            mAuthorText.setText("");
            e.printStackTrace();
        }

    }
}
