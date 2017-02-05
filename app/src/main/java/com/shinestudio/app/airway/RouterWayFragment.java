package com.shinestudio.app.airway;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.shinestudio.app.airway.card.RouterWayCard;
import com.shinestudio.app.airway.extdata.RouterFinderParser;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

public class RouterWayFragment extends Fragment {
    private CardListView cardListView;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private CardArrayAdapter myCardArrayAdapter;
    private MyTask online;

    public static RouterWayFragment newInstance() {
        RouterWayFragment fragment = new RouterWayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_cardview, container, false);
        cardListView = (CardListView) fragmentView.findViewById(R.id.card_list_view);
        myCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        cardListView.setAdapter(myCardArrayAdapter);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        online = new MyTask();
        online.execute();
    }

    public void addRouteWayCard(ArrayList<String> data) {
        RouterWayCard card = new RouterWayCard(RouterWayFragment.this.getActivity(), data);
        cards.add(card);
        myCardArrayAdapter.notifyDataSetChanged();
    }


    private class MyTask extends AsyncTask<String, Void, Void> {
        private ArrayList<String> routeWayData;
        private ProgressBar progressBar;

        private String function2() throws IOException {
            HttpPost post = new HttpPost("http://rfinder.asalink.net/free/autoroute_rtx.php");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id1", "ZBAA"));
            params.add(new BasicNameValuePair("id2", "ZSSS"));
            params.add(new BasicNameValuePair("minalt", "FL330"));
            params.add(new BasicNameValuePair("maxalt", "FL330"));
            params.add(new BasicNameValuePair("lvl", "B"));
            params.add(new BasicNameValuePair("dbid", "1503"));
            params.add(new BasicNameValuePair("usesid", "Y"));
            params.add(new BasicNameValuePair("usestar", "Y"));
            params.add(new BasicNameValuePair("easet", "Y"));
            params.add(new BasicNameValuePair("rnav", "Y"));

            HttpResponse httpResponse = null;
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse.getEntity());
                return result;
            }
            return null;
        }

        private String function1() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(RouterWayFragment.this.getActivity().getAssets().open("sdata.txt")));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPreExecute() {
            progressBar = new ProgressBar(RouterWayFragment.this.getActivity());
            cardListView.addFooterView(progressBar);
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String html = function2();
                RouterFinderParser parser = new RouterFinderParser(html);
                routeWayData = parser.getRouteWay();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (cardListView != null) {
                cardListView.removeFooterView(progressBar);
            }
            if (!isCancelled() && routeWayData != null) {
                addRouteWayCard(routeWayData);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        online.cancel(true);
    }
}
