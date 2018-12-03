package tabbardemo.com.materialdesigntabs_demo.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import tabbardemo.com.materialdesigntabs_demo.R;
import tabbardemo.com.materialdesigntabs_demo.pojo.Example;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<ViewHolder> {
    private List<Example> arrayList;
    private Context context;


    public RecyclerViewAdapter(Context context,
                               List<Example> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }


    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder,
                                 int position) {


        final ViewHolder mainHolder = (ViewHolder) holder;
        //Setting text over textview
        mainHolder.name_txt.setText(""+arrayList.get(position).getName());
        mainHolder.email_txt.setText(""+arrayList.get(position).getEmail());
        mainHolder.body_txt.setText(""+arrayList.get(position).getBody());
        mainHolder.postId_txt.setText("postId : "+arrayList.get(position).getPostId());
        mainHolder.id.setText(""+arrayList.get(position).getId());

    }

    @Override
    public ViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_row, viewGroup, false);
        ViewHolder mainHolder = new ViewHolder(mainGroup) {
            @Override
            public String toString() {
                return super.toString();
            }
        };


        return mainHolder;

    }


}