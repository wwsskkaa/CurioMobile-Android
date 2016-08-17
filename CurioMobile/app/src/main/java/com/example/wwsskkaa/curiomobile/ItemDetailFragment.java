package com.example.wwsskkaa.curiomobile;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wwsskkaa.curiomobile.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    CollapsingToolbarLayout appBarLayout;
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    public DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            Activity activity = this.getActivity();
           // CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {

                appBarLayout.setTitle(mItem.content);
                appBarLayout.setBackground(new BitmapDrawable(mItem.backgroundbit));
                //THE TITLE OF EACH PAGE
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
/*
A project header image displaying along the top bar behind the project title.
An about section displaying a description about the project.
A research questions section displaying one or more research questions that the project aims to explore.
A team section displaying the information about the team members, including the avatar images, who are involved in a specific project.
*/


        // Show the dummy content as text in a TextView.
        if (mItem != null) {

            appBarLayout = (CollapsingToolbarLayout) this.getActivity().findViewById(R.id.toolbar_layout);

                appBarLayout.setTitle(mItem.content);
                appBarLayout.setBackground(new BitmapDrawable(mItem.backgroundbit));
                //THE TITLE OF EACH PAGE
            LinearLayout layout=(LinearLayout) rootView.findViewById(R.id.detaillayout);

            String detail="BRIEF\n________\n\n"+mItem.getBrief()+"\n\nABOUT\n________\n\n"+mItem.details+"\n\nRESEARCH QUESTION\n_________________________\n\n"+mItem.conti;

            detail +="\n" +
                    "\n" +
                    "TEAM\n" +
                    "________\n" ;
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(detail);
            for(int i=0;i<mItem.tm.size();i++)
            {
                DummyContent.DummyItem.Teammember tm = mItem.tm.get(i);
                TextView teampart=new TextView(container.getContext());

                teampart.setText(tm.toString());

                ImageView photo=new ImageView(container.getContext());
                photo.setBackground(new BitmapDrawable(tm.photo));
                photo.setLayoutParams(new LinearLayout.LayoutParams(300,300));

                if(layout!=null) {
                    layout.addView(photo);
                    layout.addView(teampart);

                }
            }

            //rootView.findViewById(R.id.pictures).setBackgroundColor(Color.BLACK);

          //  ((TextView) rootView.findViewById(R.id.item_detail)).setBackground(new BitmapDrawable(mItem.backgroundbit));
        }

        return rootView;
    }
}
