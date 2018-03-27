package footprint.mobile.com.footprint.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.adapter.AdapterFootPrintsList;
import footprint.mobile.com.footprint.base.BaseFragment;
import footprint.mobile.com.footprint.dto.Savings;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class FragmentViewFootPrintList extends BaseFragment {
    @Override
    public int getLayout() throws Exception {
        return R.layout.fragment_view_foor_print_list;
    }

    @Override
    public void onViewCreated(View view) throws Exception {

        final AdapterFootPrintsList adapterFootPrintsList = new AdapterFootPrintsList(Savings.getSavings().footPrintsList());

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapterFootPrintsList);

        adapterFootPrintsList.setOnFootPrintItemClickEvent(new AdapterFootPrintsList.OnFootPrintItemClickEvent() {
            @Override
            public void onFootPrintClick(String footPrintName) throws Exception {
                if (footPrintName.equalsIgnoreCase(getString(R.string.tv_create_foot_print))) {
                    //move to create foot print
                    addOrReplaceFragment(new FragmentCreateFootPrint(),R.id.frame_container);
                } else {
                    //move to main and set selected foot print name
                    setSelectFootPrintName(footPrintName);
                    addOrReplaceFragment(new FragmentMain(),R.id.frame_container);
                }
            }
        });

        final ImageView ivHome = view.findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addOrReplaceFragment(new FragmentMain(),R.id.frame_container);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Toolbar toolbar() throws Exception {
        return getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public int menuBarTitle() throws Exception {
        return R.string.view_foot_print_list;
    }

    @Override
    public int menuBarIcon() throws Exception {
        return R.drawable.ic_arrow_back;
    }

    @Override
    public boolean hasToolBar() throws Exception {
        return true;
    }

    @Override
    public boolean hasMenu() throws Exception {
        return false;
    }

    @Override
    public void onMenuClick() throws Exception {
        backNavigation();
    }

    @Override
    public void onBackButtonClick() throws Exception {
        backNavigation();
    }

    private void backNavigation() {
        try {
            addOrReplaceFragment(new FragmentMain(),R.id.frame_container);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
