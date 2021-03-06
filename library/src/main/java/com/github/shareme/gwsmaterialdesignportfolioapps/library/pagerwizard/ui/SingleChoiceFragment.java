package com.github.shareme.gwsmaterialdesignportfolioapps.library.pagerwizard.ui;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.shareme.gwsmaterialdesignportfolioapps.library.R;
import com.github.shareme.gwsmaterialdesignportfolioapps.library.pagerwizard.model.Page;
import com.github.shareme.gwsmaterialdesignportfolioapps.library.pagerwizard.model.SingleFixedChoicePage;

import java.util.ArrayList;
import java.util.List;

/**
 * SingleChoiceFragment
 * Created by fgrott on 2/16/2016.
 */
@SuppressWarnings("unused")
public class SingleChoiceFragment extends ListFragment {
  private static final String ARG_KEY = "key";


  private PageFragmentCallbacks mCallbacks;
  private List<String> mChoices;
  private String mKey;
  private Page mPage;

  public static SingleChoiceFragment create(String key) {
    Bundle args = new Bundle();
    args.putString(ARG_KEY, key);

    SingleChoiceFragment fragment = new SingleChoiceFragment();
    fragment.setArguments(args);
    return fragment;
  }

  public SingleChoiceFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();
    mKey = args.getString(ARG_KEY);
    mPage = mCallbacks.onGetPage(mKey);

    SingleFixedChoicePage fixedChoicePage = (SingleFixedChoicePage) mPage;
    mChoices = new ArrayList<>();
    for (int i = 0; i < fixedChoicePage.getOptionCount(); i++) {
      mChoices.add(fixedChoicePage.getOptionAt(i));
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_page, container, false);
    ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

    final ListView listView = (ListView) rootView.findViewById(android.R.id.list);
    setListAdapter(new ArrayAdapter<>(getActivity(),
            android.R.layout.simple_list_item_single_choice,
            android.R.id.text1,
            mChoices));
    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    // Pre-select currently selected item.
    new Handler().post(new Runnable() {
      @Override
      public void run() {
        String selection = mPage.getData().getString(Page.SIMPLE_DATA_KEY);
        for (int i = 0; i < mChoices.size(); i++) {
          if (mChoices.get(i).equals(selection)) {
            listView.setItemChecked(i, true);
            break;
          }
        }
      }
    });

    return rootView;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (!(activity instanceof PageFragmentCallbacks)) {
      throw new ClassCastException("Activity must implement PageFragmentCallbacks");
    }

    mCallbacks = (PageFragmentCallbacks) activity;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mCallbacks = null;
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    mPage.getData().putString(Page.SIMPLE_DATA_KEY,
            getListAdapter().getItem(position).toString());
    mPage.notifyDataChanged();
  }
}
