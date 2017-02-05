package com.shinestudio.app.airway;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinestudio.app.airway.card.CountryCard;
import com.shinestudio.app.airway.widget.OnActivityBackPressListener;

import it.gmariotti.cardslib.library.internal.Card;

public class AirportSelectorFragment extends Fragment implements TextWatcher, OnActivityBackPressListener {

    private ImageButton btn_clear;
    private ImageButton btn_back;
    private ImageView iv_search;
    private EditText et_search;

    public final static int SEARCH_MODE_NORMAL = 0x0;
    public final static int SEARCH_MODE_INPUTING = 0x1;
    public final static int SEARCH_MODE_COUNTRY = 0x2;
    public final static int SEARCH_MODE_KEYWORD = 0x3;

    private final static String SAVEKEY_SEARCH_MODE = "mode";
    private final static String SAVEKEY_SEARCH_KEYWORD = "keyword";

    private String mSearchKeyWord;
    private int mSearchMode;

    private CountriesListFragment countriesListFragment;
    private AirportsListFragment airportsListFragment;

    private Card.OnCardClickListener onAirportCardClickListener;

    //输入法搜索按钮按下
    private TextView.OnEditorActionListener onIMESearchClickListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            mSearchKeyWord = et_search.getText().toString();
            setSearchMode(SEARCH_MODE_KEYWORD);
            return true;
        }
    };

    //按钮监听
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btn_clear) {
                searchClear();
            } else if (v == btn_back) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                setSearchMode(SEARCH_MODE_NORMAL);
            }
        }
    };

    //输入框焦点
    private View.OnFocusChangeListener onSearchFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                setSearchMode(SEARCH_MODE_INPUTING);
            }
        }
    };

    //点击国家卡片
    private Card.OnCardClickListener onCountryCardClickListener = new Card.OnCardClickListener() {

        @Override
        public void onClick(Card card, View view) {
            int type = card.getType();
            if (CountryCard.TYPE == type) {
                CountryCard countryCard = (CountryCard) card;
                mSearchKeyWord = countryCard.getCountry().getCountry();
                setSearchMode(SEARCH_MODE_COUNTRY);
            }
        }
    };

    public static AirportSelectorFragment newInstance() {
        AirportSelectorFragment fragment = new AirportSelectorFragment();
        return fragment;
    }

    public AirportSelectorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mSearchMode = savedInstanceState.getInt(SAVEKEY_SEARCH_MODE);
            mSearchKeyWord = savedInstanceState.getString(SAVEKEY_SEARCH_KEYWORD);
        } else {
            mSearchMode = SEARCH_MODE_NORMAL;
        }
        countriesListFragment = new CountriesListFragment();
        countriesListFragment.setOnCardClickListener(onCountryCardClickListener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_airport_selector, container, false);

        btn_clear = (ImageButton) fragmentView.findViewById(R.id.btn_clear);
        btn_clear.setVisibility(View.GONE);
        btn_clear.setOnClickListener(onClickListener);

        btn_back = (ImageButton) fragmentView.findViewById(R.id.btn_back);
        btn_back.setVisibility(View.GONE);
        btn_back.setOnClickListener(onClickListener);

        et_search = (EditText) fragmentView.findViewById(R.id.et_search);
        et_search.addTextChangedListener(this);
        et_search.setOnEditorActionListener(onIMESearchClickListener);
        et_search.setOnFocusChangeListener(onSearchFocusChangeListener);

        iv_search = (ImageView) fragmentView.findViewById(R.id.iv_search);
        setRetainInstance(true); // 重要! 设置fragment在activity重建时不在新建新fragment 不执行onCreate onCreateView
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSearchMode(mSearchMode);
    }

    private void showAirportList(int searchMode) {
        airportsListFragment = new AirportsListFragment();
        airportsListFragment.setOnAirportCardClickListener(onAirportCardClickListener);
        airportsListFragment.setKeyWord(mSearchKeyWord);
        airportsListFragment.setSearchMode(searchMode);
        setSearchModeText(mSearchKeyWord);
        getFragmentManager().beginTransaction().replace(R.id.content_view, airportsListFragment).commit();
    }

    private void setSearchBarAndContent() {
        if (et_search.getText().length() > 0) {
            btn_clear.setVisibility(View.VISIBLE);
        } else {
            btn_clear.setVisibility(View.GONE);
        }
    }

    public void searchReset() {
        searchClear();
        et_search.clearFocus();
        btn_clear.setVisibility(View.GONE);
        btn_back.setVisibility(View.GONE);
        iv_search.setVisibility(View.VISIBLE);
        this.getFragmentManager().popBackStack();
    }

    public void searchClear() {
        et_search.setText("");
        et_search.setEnabled(true);
        mSearchKeyWord = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVEKEY_SEARCH_MODE, mSearchMode);
        outState.putString(SAVEKEY_SEARCH_KEYWORD, mSearchKeyWord);
    }

    public void setSearchModeText(String text) {
        et_search.setText(text);
        et_search.clearFocus();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        // 在搜索框内容改变时
        if (mSearchMode == SEARCH_MODE_INPUTING) {
            setSearchBarAndContent();
        }
    }

    public void setOnAirportCardClickListener(Card.OnCardClickListener onAirportCardClickListener) {
        this.onAirportCardClickListener = onAirportCardClickListener;
    }

    private void showInputBackButton(boolean show) {
        if (show) {
            iv_search.setVisibility(View.INVISIBLE);
            btn_back.setVisibility(View.VISIBLE);
        } else {
            iv_search.setVisibility(View.VISIBLE);
            btn_back.setVisibility(View.INVISIBLE);
        }
    }

    private void showCountriesList() {
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.content_view, countriesListFragment).commit();
    }

    public void setSearchMode(int type) {
        mSearchMode = type;
        switch (type) {
            case SEARCH_MODE_NORMAL:
                showCountriesList();
                searchReset();
                break;
            case SEARCH_MODE_INPUTING:
                showInputBackButton(true);
                et_search.requestFocus();
                break;
            case SEARCH_MODE_COUNTRY:
                showInputBackButton(true);
                et_search.setEnabled(false);
                showAirportList(AirportsListFragment.SEARCH_MODE_BYCOUNTRY);
                break;
            case SEARCH_MODE_KEYWORD:
                showInputBackButton(true);
                et_search.setEnabled(true);
                showAirportList(AirportsListFragment.SEARCH_MODE_FUZZY);
                break;
            default:

        }
    }


    public int getSearchMode() {
        return mSearchMode;
    }

    @Override
    public boolean onBackPress() {
        if (mSearchMode != SEARCH_MODE_NORMAL) {
            setSearchMode(AirportSelectorFragment.SEARCH_MODE_NORMAL);
            return true;
        }
        return false;
    }
}
